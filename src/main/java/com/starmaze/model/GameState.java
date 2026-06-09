package com.starmaze.model;

import com.starmaze.audio.SoundEffect;
import com.starmaze.audio.SoundEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public final class GameState {
    public static final int PHASE_MAX = GameConfig.PHASE_MAX;
    public static final int PHASE_COST = GameConfig.PHASE_COST;
    public static final int REWIND_MAX = GameConfig.REWIND_MAX;

    private final Random random = new Random();
    private final SaveData saveData;
    private final SoundEngine soundEngine;
    private final EnemyController enemyController = new EnemyController();
    private final LevelPopulator levelPopulator = new LevelPopulator();
    private final RewindHistory rewindHistory = new RewindHistory();
    private final VisualEventQueue visualEvents = new VisualEventQueue();

    private GameMode mode = GameMode.TITLE;
    private Level level;
    private Position player;
    private final List<Enemy> enemies = new ArrayList<>();
    private final Set<Position> crystals = new LinkedHashSet<>();
    private final Set<Position> phaseCells = new LinkedHashSet<>();
    private final Set<Position> rewindCells = new LinkedHashSet<>();

    private int levelIndex = 1;
    private int score;
    private int moves;
    private int tick;
    private int crystalsCollected;
    private int crystalsNeeded;
    private int phaseMeter = GameConfig.PHASE_INITIAL;
    private int phaseTicks;
    private int rewindCharges = GameConfig.REWIND_INITIAL_CHARGES;
    private int messageTicks;
    private String message = GameMessages.START_HINT;

    public GameState(SaveData saveData, SoundEngine soundEngine) {
        this.saveData = saveData;
        this.soundEngine = soundEngine;
    }

    public SaveData saveData() {
        return saveData;
    }

    public GameMode mode() {
        return mode;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }

    public Level level() {
        return level;
    }

    public Position player() {
        return player;
    }

    public List<Enemy> enemies() {
        return Collections.unmodifiableList(enemies);
    }

    public Set<Position> crystals() {
        return Collections.unmodifiableSet(crystals);
    }

    public Set<Position> phaseCells() {
        return Collections.unmodifiableSet(phaseCells);
    }

    public Set<Position> rewindCells() {
        return Collections.unmodifiableSet(rewindCells);
    }

    public int levelIndex() {
        return levelIndex;
    }

    public int score() {
        return score;
    }

    public int moves() {
        return moves;
    }

    public int tick() {
        return tick;
    }

    public int crystalsCollected() {
        return crystalsCollected;
    }

    public int crystalsNeeded() {
        return crystalsNeeded;
    }

    public int phaseMeter() {
        return phaseMeter;
    }

    public int phaseTicks() {
        return phaseTicks;
    }

    public int rewindCharges() {
        return rewindCharges;
    }

    public String message() {
        return message;
    }

    public int messageTicks() {
        return messageTicks;
    }

    public boolean isPhaseActive() {
        return phaseTicks > 0;
    }

    public boolean isExitOpen() {
        return crystalsCollected >= crystalsNeeded;
    }

    public SoundEngine soundEngine() {
        return soundEngine;
    }

    public List<VisualEffectEvent> drainVisualEffects() {
        return visualEvents.drain();
    }

    public void startNewGame() {
        levelIndex = 1;
        score = 0;
        moves = 0;
        phaseMeter = GameConfig.PHASE_INITIAL;
        rewindCharges = GameConfig.REWIND_INITIAL_CHARGES;
        buildLevel();
        mode = GameMode.PLAYING;
        soundEngine.play(SoundEffect.MENU);
    }

    public void restartLevel() {
        penalizeScore(LevelProgressRules.restartPenalty());
        phaseMeter = PhaseRules.restartFloor(phaseMeter);
        buildLevel();
        mode = GameMode.PLAYING;
        soundEngine.play(SoundEffect.MENU);
    }

    public void nextLevel() {
        levelIndex++;
        score += LevelProgressRules.nextLevelEntryBonus(levelIndex);
        addPhaseMeter(LevelProgressRules.nextLevelPhaseGain());
        addRewindCharge();
        buildLevel();
        mode = GameMode.PLAYING;
        soundEngine.play(SoundEffect.WIN);
    }

    private void buildLevel() {
        level = LevelGenerator.generate(levelIndex, random);
        player = level.start();
        resetLevelRuntimeState();

        LevelPopulation population = levelPopulator.populate(level, levelIndex, random);
        applyPopulation(population);

        showMessage(GameMessages.levelStart(levelIndex, crystalsNeeded));
        recordSnapshot();
    }

    private void resetLevelRuntimeState() {
        enemies.clear();
        crystals.clear();
        phaseCells.clear();
        rewindCells.clear();
        rewindHistory.clear();
        visualEvents.clear();
        tick = 0;
        phaseTicks = 0;
        crystalsCollected = 0;
    }

    private void applyPopulation(LevelPopulation population) {
        crystals.addAll(population.crystals());
        phaseCells.addAll(population.phaseCells());
        rewindCells.addAll(population.rewindCells());
        enemies.addAll(population.enemies());
        crystalsNeeded = population.crystalsNeeded();
    }

    public void update() {
        tick++;
        if (messageTicks > 0) {
            messageTicks--;
        }
        if (mode != GameMode.PLAYING || level == null) {
            return;
        }
        updatePhaseState();
        if (shouldMoveEnemies()) {
            moveEnemies();
        }
        if (shouldRecordRewindSnapshot()) {
            recordSnapshot();
        }
    }

    private boolean shouldMoveEnemies() {
        return tick % GameConfig.ENEMY_MOVE_INTERVAL_TICKS == 0;
    }

    private boolean shouldRecordRewindSnapshot() {
        return tick % GameConfig.REWIND_SNAPSHOT_INTERVAL_TICKS == 0;
    }

    private void updatePhaseState() {
        if (phaseTicks > 0) {
            phaseTicks--;
            if (phaseTicks == 0 && level.tileAt(player) == TileType.WALL) {
                player = level.nearestWalkable(player);
                showMessage(GameMessages.PHASE_ENDED_SAFE);
            }
        } else {
            phaseMeter = PhaseRules.recharge(phaseMeter, tick);
        }
    }

    public void movePlayer(Direction direction) {
        if (mode != GameMode.PLAYING || level == null) {
            return;
        }
        Position target = player.translate(direction);
        if (!canEnter(target)) {
            bumpWall();
            return;
        }
        if (level.tileAt(target) == TileType.WALL && isPhaseActive()) {
            score += ScoreRules.phaseWallStep();
        }
        player = target;
        moves++;
        penalizeScore(ScoreRules.moveCost());
        soundEngine.play(SoundEffect.MOVE);
        collectAtPlayer();
        handleRift();
        checkEnemyCollision();
        checkExit();
        recordSnapshot();
    }

    private boolean canEnter(Position target) {
        return level.contains(target) && (isPhaseActive() || level.tileAt(target) != TileType.WALL);
    }

    private void bumpWall() {
        penalizeScore(ScoreRules.moveCost());
        showMessage(GameMessages.WALL_BLOCKED);
        soundEngine.play(SoundEffect.BUMP);
    }

    private void collectAtPlayer() {
        if (crystals.remove(player)) {
            collectCrystal();
        }
        if (phaseCells.remove(player)) {
            collectPhaseCell();
        }
        if (rewindCells.remove(player)) {
            collectRewindCell();
        }
    }

    private void collectCrystal() {
        crystalsCollected++;
        score += ScoreRules.crystalPickup(levelIndex);
        addPhaseMeter(GameConfig.PHASE_GAIN_CRYSTAL);
        showMessage(GameMessages.crystalCollected(crystalsCollected, crystalsNeeded));
        soundEngine.play(SoundEffect.COLLECT);
    }

    private void collectPhaseCell() {
        addPhaseMeter(GameConfig.PHASE_GAIN_CELL);
        score += ScoreRules.phaseCellPickup();
        showMessage(GameMessages.PHASE_CELL_COLLECTED);
        soundEngine.play(SoundEffect.POWER);
    }

    private void collectRewindCell() {
        addRewindCharge();
        score += ScoreRules.rewindCellPickup();
        showMessage(GameMessages.REWIND_CELL_COLLECTED);
        soundEngine.play(SoundEffect.POWER);
    }

    private void handleRift() {
        if (level.tileAt(player) != TileType.RIFT) {
            return;
        }
        switch (RiftRules.rollOutcome(random)) {
            case WARP -> warpThroughRift();
            case PHASE_GAIN -> convertRiftToPhaseEnergy();
        }
        score += ScoreRules.riftVisit();
        soundEngine.play(SoundEffect.PHASE);
    }

    private void warpThroughRift() {
        player = level.randomFloorFarFrom(player, random, RiftRules.teleportMinDistance());
        emitEffect(VisualEffectType.RIFT_WARP, player);
        showMessage(GameMessages.RIFT_WARP);
    }

    private void convertRiftToPhaseEnergy() {
        addPhaseMeter(RiftRules.phaseGain());
        emitEffect(VisualEffectType.RIFT_WARP, player);
        showMessage(GameMessages.RIFT_PHASE_GAIN);
    }

    public void activatePhase() {
        if (mode != GameMode.PLAYING) {
            return;
        }
        if (!canActivatePhase()) {
            return;
        }
        phaseMeter -= PHASE_COST;
        phaseTicks = GameConfig.PHASE_DURATION_TICKS;
        score += ScoreRules.phaseActivation();
        emitEffect(VisualEffectType.PHASE_BURST, player);
        showMessage(GameMessages.PHASE_ACTIVATED);
        soundEngine.play(SoundEffect.PHASE);
    }

    private boolean canActivatePhase() {
        if (phaseTicks > 0) {
            showMessage(GameMessages.PHASE_ALREADY_ACTIVE);
            return false;
        }
        if (!PhaseRules.canAfford(phaseMeter)) {
            showMessage(GameMessages.PHASE_NOT_ENOUGH);
            soundEngine.play(SoundEffect.BUMP);
            return false;
        }
        return true;
    }

    public void rewind() {
        if (mode != GameMode.PLAYING) {
            return;
        }
        if (!canRewind()) {
            return;
        }
        RewindSnapshot snapshot = rewindHistory.lookBack();
        player = snapshot.player();
        restoreEnemiesFromSnapshot(snapshot);
        phaseTicks = PhaseRules.afterRewind(snapshot.phaseTicks());
        rewindCharges = RewindRules.spendCharge(rewindCharges);
        penalizeScore(ScoreRules.rewindPenalty());
        rewindHistory.clear();
        recordSnapshot();
        emitEffect(VisualEffectType.REWIND_WAVE, player);
        showMessage(GameMessages.REWIND_DONE);
        soundEngine.play(SoundEffect.REWIND);
    }

    private boolean canRewind() {
        if (!RewindRules.hasCharge(rewindCharges)) {
            showMessage(GameMessages.REWIND_EMPTY);
            soundEngine.play(SoundEffect.BUMP);
            return false;
        }
        if (!RewindRules.hasEnoughHistory(rewindHistory.size())) {
            showMessage(GameMessages.REWIND_NOT_READY);
            return false;
        }
        return true;
    }

    private void restoreEnemiesFromSnapshot(RewindSnapshot snapshot) {
        for (int i = 0; i < enemies.size() && i < snapshot.enemyPositions().size(); i++) {
            Enemy enemy = enemies.get(i);
            enemy.setPosition(snapshot.enemyPositions().get(i));
            enemy.stun(GameConfig.REWIND_ENEMY_STUN_TICKS);
            emitEffect(VisualEffectType.ENEMY_STUN, enemy.position());
        }
    }

    private void moveEnemies() {
        Set<Position> occupied = occupiedEnemyPositions();
        for (Enemy enemy : enemies) {
            moveEnemy(enemy, occupied);
        }
        checkEnemyCollision();
    }

    private void moveEnemy(Enemy enemy, Set<Position> occupied) {
        enemyController.advanceEnemy(level, levelIndex, player, isPhaseActive(), enemy, occupied, random);
    }

    private Set<Position> occupiedEnemyPositions() {
        Set<Position> occupied = new HashSet<>();
        for (Enemy enemy : enemies) {
            occupied.add(enemy.position());
        }
        return occupied;
    }

    private void checkEnemyCollision() {
        for (Enemy enemy : enemies) {
            if (!enemy.position().equals(player)) {
                continue;
            }
            if (isPhaseActive()) {
                disruptEnemyWithPhase(enemy);
            } else {
                gameOver(GameMessages.ENEMY_CAPTURED_PLAYER);
            }
        }
    }

    private void disruptEnemyWithPhase(Enemy enemy) {
        enemy.stun(GameConfig.ENEMY_PHASE_STUN_TICKS);
        score += ScoreRules.phaseEnemyStun();
        emitEffect(VisualEffectType.ENEMY_STUN, enemy.position());
        showMessage(GameMessages.ENEMY_PHASE_STUN);
        soundEngine.play(SoundEffect.POWER);
    }

    private void checkExit() {
        if (!player.equals(level.exit())) {
            return;
        }
        if (isExitOpen()) {
            clearLevel();
        } else {
            rejectLockedExit();
        }
    }

    private void clearLevel() {
        int levelBonus = ScoreRules.levelClearBonus(moves, phaseMeter, rewindCharges);
        score += levelBonus;
        mode = GameMode.LEVEL_CLEAR;
        showMessage(GameMessages.levelClear(levelBonus));
        saveData.rememberScore(score);
        soundEngine.play(SoundEffect.WIN);
    }

    private void rejectLockedExit() {
        showMessage(GameMessages.exitLocked(crystalsNeeded - crystalsCollected));
        soundEngine.play(SoundEffect.BUMP);
    }

    private void gameOver(String reason) {
        mode = GameMode.GAME_OVER;
        showMessage(reason);
        saveData.recordGame(score, false);
        soundEngine.play(SoundEffect.LOSE);
    }

    public void completeRun() {
        saveData.recordGame(score, true);
    }

    public void togglePause() {
        if (mode == GameMode.PLAYING) {
            mode = GameMode.PAUSED;
        } else if (mode == GameMode.PAUSED) {
            mode = GameMode.PLAYING;
        }
    }

    public void toggleSound() {
        saveData.setSoundEnabled(!saveData.isSoundEnabled());
        soundEngine.setEnabled(saveData.isSoundEnabled());
        saveData.save();
        if (saveData.isSoundEnabled()) {
            soundEngine.play(SoundEffect.MENU);
        }
    }

    private void showMessage(String message) {
        this.message = message;
        this.messageTicks = GameConfig.MESSAGE_DURATION_TICKS;
    }

    private void addPhaseMeter(int amount) {
        phaseMeter = PhaseRules.gain(phaseMeter, amount);
    }

    private void addRewindCharge() {
        rewindCharges = RewindRules.gainCharge(rewindCharges);
    }

    private void penalizeScore(int amount) {
        score = Math.max(0, score - amount);
    }

    private void emitEffect(VisualEffectType type, Position position) {
        visualEvents.emit(type, position, tick);
    }

    private void recordSnapshot() {
        if (level == null || player == null) {
            return;
        }
        rewindHistory.record(player, enemies, phaseTicks);
    }

    public int runRank() {
        return ScoreRules.runRank(score);
    }

}

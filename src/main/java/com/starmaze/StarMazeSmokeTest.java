package com.starmaze;

import com.starmaze.audio.SoundEffect;
import com.starmaze.audio.SoundEngine;
import com.starmaze.model.Direction;
import com.starmaze.model.Enemy;
import com.starmaze.model.EnemyController;
import com.starmaze.model.GameConfig;
import com.starmaze.model.GameMessages;
import com.starmaze.model.GameMode;
import com.starmaze.model.GameState;
import com.starmaze.model.Level;
import com.starmaze.model.LevelGenerator;
import com.starmaze.model.LevelPopulation;
import com.starmaze.model.LevelPopulator;
import com.starmaze.model.LevelProgressRules;
import com.starmaze.model.PathFinder;
import com.starmaze.model.PhaseRules;
import com.starmaze.model.Position;
import com.starmaze.model.RewindHistory;
import com.starmaze.model.RewindRules;
import com.starmaze.model.RewindSnapshot;
import com.starmaze.model.RiftOutcome;
import com.starmaze.model.RiftRules;
import com.starmaze.model.SaveData;
import com.starmaze.model.ScoreRules;
import com.starmaze.model.TileType;
import com.starmaze.model.VisualEventQueue;
import com.starmaze.model.VisualEffectEvent;
import com.starmaze.model.VisualEffectType;
import com.starmaze.ui.ActiveEffect;
import com.starmaze.ui.ActiveEffectFactory;
import com.starmaze.ui.ActiveEffectList;
import com.starmaze.ui.BoardMetrics;
import com.starmaze.ui.BoardMetricsCalculator;
import com.starmaze.ui.BoardFrameRenderer;
import com.starmaze.ui.BoardLayerRenderer;
import com.starmaze.ui.BoardRenderer;
import com.starmaze.ui.BoardRiftRenderer;
import com.starmaze.ui.BoardStaticCacheRenderer;
import com.starmaze.ui.BoardTileRenderer;
import com.starmaze.ui.BackgroundRenderer;
import com.starmaze.ui.EffectRenderer;
import com.starmaze.ui.EffectRenderDispatcher;
import com.starmaze.ui.EffectRenderKind;
import com.starmaze.ui.EffectRenderSpec;
import com.starmaze.ui.EffectLayer;
import com.starmaze.ui.EffectProgress;
import com.starmaze.ui.EnemyRenderer;
import com.starmaze.ui.EnemyVisualStyle;
import com.starmaze.ui.EntityLayerRenderer;
import com.starmaze.ui.EntityRenderOrder;
import com.starmaze.ui.EntityRenderer;
import com.starmaze.ui.ExitRenderer;
import com.starmaze.ui.ExitVisualStyle;
import com.starmaze.ui.FooterRenderer;
import com.starmaze.ui.GameLoopController;
import com.starmaze.ui.GamePanelInputBinder;
import com.starmaze.ui.GamePanelConfigurator;
import com.starmaze.ui.GamePanel;
import com.starmaze.ui.GamePanelTimer;
import com.starmaze.ui.GamePanelRenderer;
import com.starmaze.ui.GameSceneRenderer;
import com.starmaze.ui.GameViewRenderer;
import com.starmaze.ui.GameplayLayerRenderer;
import com.starmaze.ui.HelpMenuContentRenderer;
import com.starmaze.ui.HelpMenuContentSpec;
import com.starmaze.ui.HudLayout;
import com.starmaze.ui.HudMessageRenderer;
import com.starmaze.ui.HudMessageSelector;
import com.starmaze.ui.HudMeterGeometry;
import com.starmaze.ui.HudMeterRenderer;
import com.starmaze.ui.HudPanelRenderer;
import com.starmaze.ui.HudPhaseColorSelector;
import com.starmaze.ui.HudRewindDot;
import com.starmaze.ui.HudRewindRenderer;
import com.starmaze.ui.HudRenderer;
import com.starmaze.ui.HudStatItem;
import com.starmaze.ui.HudStatsRenderer;
import com.starmaze.ui.HudStatsSpec;
import com.starmaze.ui.HudText;
import com.starmaze.ui.InputActions;
import com.starmaze.ui.InputController;
import com.starmaze.ui.MenuActionHandler;
import com.starmaze.ui.MenuActions;
import com.starmaze.ui.MenuButton;
import com.starmaze.ui.MenuButtonCache;
import com.starmaze.ui.MenuButtonGroupRenderer;
import com.starmaze.ui.MenuButtonRenderer;
import com.starmaze.ui.MenuChromeRenderer;
import com.starmaze.ui.MenuLabels;
import com.starmaze.ui.MenuLayout;
import com.starmaze.ui.MenuMetrics;
import com.starmaze.ui.MenuMouseController;
import com.starmaze.ui.MenuOverlayDispatcher;
import com.starmaze.ui.MenuOverlayMode;
import com.starmaze.ui.MenuOverlayRenderer;
import com.starmaze.ui.MenuOverlaySpec;
import com.starmaze.ui.MenuOverlayStyle;
import com.starmaze.ui.MenuPanel;
import com.starmaze.ui.MenuText;
import com.starmaze.ui.PickupRenderer;
import com.starmaze.ui.PickupShape;
import com.starmaze.ui.PickupVisualStyle;
import com.starmaze.ui.PlayerRenderer;
import com.starmaze.ui.PlayerVisualStyle;
import com.starmaze.ui.RenderQuality;
import com.starmaze.ui.RenderClock;
import com.starmaze.ui.ResultMenuContentRenderer;
import com.starmaze.ui.ResultMenuContentSpec;
import com.starmaze.ui.RingEffectRenderer;
import com.starmaze.ui.RingEffectStyle;
import com.starmaze.ui.ScreenShakeOffset;
import com.starmaze.ui.ScreenShakeRenderer;
import com.starmaze.ui.ScreenShakeRules;
import com.starmaze.ui.SimpleMenuContentRenderer;
import com.starmaze.ui.SimpleMenuContentSpec;
import com.starmaze.ui.StunEffectRenderer;
import com.starmaze.ui.StunEffectStyle;
import com.starmaze.ui.StarField;
import com.starmaze.ui.TitleMenuContentRenderer;
import com.starmaze.ui.TitleMenuContentSpec;
import com.starmaze.ui.VisualConfig;

import java.nio.file.Files;
import java.nio.file.Path;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public final class StarMazeSmokeTest {
    private StarMazeSmokeTest() {
    }

    public static void main(String[] args) {
        SaveData saveData = SaveData.loadDefault();
        SoundEngine soundEngine = new SoundEngine(false);
        GameState state = new GameState(saveData, soundEngine);
        state.startNewGame();
        require(state.mode() == GameMode.PLAYING, "game should enter PLAYING mode");
        require(state.message().contains("星核"), "game state messages should be readable UTF-8 text");
        Level level = state.level();
        require(level != null, "level should be created");
        require(level.width() >= 19 && level.height() >= 15, "level dimensions should be playable");
        require(level.floorPositions().size() > 30, "level should contain enough floor tiles");
        require(state.crystals().size() >= state.crystalsNeeded(), "enough crystals should exist");
        require(!state.enemies().isEmpty(), "enemies should be spawned");
        require(PathFinder.findStepToward(level, state.enemies().get(0).position(), state.player(), Set.of()) != null,
                "A* pathfinder should find a first step toward the player");
        require(PathFinder.findStepToward(createPathfinderFixture(), new Position(1, 1), new Position(3, 1), Set.of())
                        == Direction.RIGHT,
                "A* pathfinder should choose the shortest first step in a fixed corridor");
        require(PathFinder.findStepToward(createOccupiedPathfinderFixture(), new Position(1, 1), new Position(3, 1),
                        Set.of(new Position(2, 1))) == Direction.DOWN,
                "A* pathfinder should route around occupied cells");
        require(PathFinder.findStepToward(createPathfinderFixture(), new Position(1, 1), new Position(3, 1),
                        Set.of(new Position(3, 1))) == Direction.RIGHT,
                "A* pathfinder should allow stepping toward an occupied target cell");
        require(PathFinder.findStepToward(createPathfinderFixture(), new Position(1, 1), new Position(1, 1), Set.of()) == null,
                "A* pathfinder should return no step when already at target");
        require(PathFinder.findStepToward(createBlockedPathfinderFixture(), new Position(1, 1), new Position(3, 1),
                        Set.of()) == null,
                "A* pathfinder should return no step when target is unreachable");
        require(createPathfinderFixture().nearestWalkable(new Position(0, 0)).equals(new Position(1, 1)),
                "nearest walkable should recover from a wall cell");
        verifyLevelNavigationHelpers();
        verifyEnemyController();
        verifyLevelPopulation();
        verifyRewindHistory();
        verifyVisualEventQueue();
        verifySaveDataRoundTrip();
        verifyScoreRules();
        verifyPhaseRules();
        verifyRewindRules();
        verifyLevelProgressRules();
        verifyRiftRules();
        verifyDeterministicLevelClearFlow();
        verifyDeterministicEnemyCollisionFlow();
        verifyDeterministicRewindFlow();
        verifyDeterministicPhaseRecoveryFlow();
        verifyGameMessages();
        verifyHudText();
        verifyHudLayout();
        verifyHudMessageSelector();
        verifyHudPhaseColorSelector();
        verifyHudMeterGeometry();
        verifyHudMeterRenderer();
        verifyHudRewindDot();
        verifyHudRewindRenderer();
        verifyHudMessageRenderer();
        verifyHudStatsSpec();
        verifyHudStatsRenderer();
        verifyHudPanelRenderer();
        verifyHudRendererComposition();
        verifyCadenceConfig();
        verifyActiveEffectFactory();
        verifyActiveEffectList();
        verifyEffectLayer();
        verifyEffectProgress();
        verifyEffectRenderSpec();
        verifyEffectRenderDispatcher();
        verifyScreenShakeRules();
        verifyScreenShakeRenderer();
        verifyEffectRendererComposition();
        verifyRingEffectStyle();
        verifyRingEffectRenderer();
        verifyStunEffectStyle();
        verifyStunEffectRenderer();
        verifyInputActions();
        verifyInputControllerBindings();
        verifyGamePanelInputBinder();
        verifyGamePanelConfigurator();
        verifyMenuActionHandler();
        verifyMenuMouseController();
        verifyMenuLabels();
        verifyMenuText();
        verifyMenuLayout();
        verifyMenuMetrics();
        verifyMenuButtonCache();
        verifyMenuButtonRenderer();
        verifyMenuButtonGroupRenderer();
        verifyMenuChromeRenderer();
        verifyMenuOverlayMode();
        verifyMenuOverlaySpec();
        verifyMenuOverlayStyle();
        verifyMenuOverlayDispatcher();
        verifyMenuOverlayRenderer();
        verifyHelpMenuContentSpec();
        verifyHelpMenuContentRenderer();
        verifyResultMenuContentSpec();
        verifyResultMenuContentRenderer();
        verifyTitleMenuContentSpec();
        verifyTitleMenuContentRenderer();
        verifySimpleMenuContentSpec();
        verifySimpleMenuContentRenderer();
        verifyRenderClock();
        verifyGameLoopController();
        verifyGamePanelTimer();
        verifyRenderQuality();
        verifyBoardMetricsCalculator();
        verifyBoardFrameRenderer();
        verifyBoardTileRenderer();
        verifyBoardStaticCacheRenderer();
        verifyBoardRiftRenderer();
        verifyBoardRendererBoundaries();
        verifyBoardLayerRendererCache();
        verifyBackgroundRenderer();
        verifyStarField();
        verifyPickupVisualStyle();
        verifyPickupRenderer();
        verifyExitVisualStyle();
        verifyExitRenderer();
        verifyPlayerVisualStyle();
        verifyPlayerRenderer();
        verifyEnemyVisualStyle();
        verifyEnemyRenderer();
        verifyEntityRendererFacade();
        verifyEntityRenderOrder();
        verifyEntityLayerRenderer();
        verifyGameSceneRendererCache();
        verifyGameplayLayerRenderer();
        verifyGameViewRenderer();
        verifyGamePanelRenderer();
        verifyFooterRenderer();
        verifyOffscreenRendering();
        verifyGeneratedLevels();

        int beforeTicks = state.tick();
        for (int i = 0; i < 20; i++) {
            state.update();
        }
        require(state.tick() > beforeTicks, "timer updates should advance ticks");

        int beforePhase = state.phaseMeter();
        state.activatePhase();
        require(state.phaseMeter() < beforePhase || beforePhase < GameState.PHASE_COST, "phase should spend energy when available");

        for (Direction direction : Direction.CARDINALS) {
            state.movePlayer(direction);
        }
        require(state.moves() >= 0, "movement counter should remain valid");
        soundEngine.shutdown();
        verifySoundEngineBoundaries();
        System.out.println("StarMaze smoke test passed");
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }

    private static Level createPathfinderFixture() {
        TileType[][] tiles = {
                {TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL},
                {TileType.WALL, TileType.FLOOR, TileType.FLOOR, TileType.FLOOR, TileType.WALL},
                {TileType.WALL, TileType.WALL, TileType.WALL, TileType.FLOOR, TileType.WALL},
                {TileType.WALL, TileType.FLOOR, TileType.FLOOR, TileType.FLOOR, TileType.WALL},
                {TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL}
        };
        return new Level(5, 5, tiles, new Position(1, 1), new Position(3, 1), collectFloors(tiles));
    }

    private static Level createOccupiedPathfinderFixture() {
        TileType[][] tiles = {
                {TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL},
                {TileType.WALL, TileType.FLOOR, TileType.FLOOR, TileType.FLOOR, TileType.WALL},
                {TileType.WALL, TileType.FLOOR, TileType.FLOOR, TileType.FLOOR, TileType.WALL},
                {TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL}
        };
        return new Level(5, 4, tiles, new Position(1, 1), new Position(3, 1), collectFloors(tiles));
    }

    private static Level createBlockedPathfinderFixture() {
        TileType[][] tiles = {
                {TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL},
                {TileType.WALL, TileType.FLOOR, TileType.WALL, TileType.FLOOR, TileType.WALL},
                {TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL}
        };
        return new Level(5, 3, tiles, new Position(1, 1), new Position(3, 1), collectFloors(tiles));
    }

    private static List<Position> collectFloors(TileType[][] tiles) {
        List<Position> floors = new ArrayList<>();
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                if (tiles[y][x] != TileType.WALL) {
                    floors.add(new Position(x, y));
                }
            }
        }
        return floors;
    }

    private static void verifyLevelNavigationHelpers() {
        Level fixture = createPathfinderFixture();
        require(fixture.nearestWalkable(new Position(-4, -3)).equals(new Position(1, 1)),
                "nearest walkable should clamp out-of-bounds origins before searching");
        Position far = fixture.randomFloorFarFrom(new Position(1, 1), new Random(3L), 3);
        require(far.manhattanDistance(new Position(1, 1)) >= 3 && !far.equals(fixture.exit()),
                "random far floor should respect distance and avoid exit");
        require(fixture.randomFloorFarFrom(new Position(1, 1), new Random(3L), 100).equals(fixture.start()),
                "random far floor should fall back to start when no candidates exist");
    }

    private static void verifyGeneratedLevels() {
        Random random = new Random(20260609L);
        for (int levelIndex = 1; levelIndex <= 8; levelIndex++) {
            Level generated = LevelGenerator.generate(levelIndex, random);
            require(generated.width() % 2 == 1 && generated.height() % 2 == 1,
                    "generated levels should use odd dimensions");
            require(generated.floorPositions().contains(generated.start()), "start should be walkable");
            require(generated.floorPositions().contains(generated.exit()), "exit should be walkable");
            require(isReachable(generated, generated.start(), generated.exit()), "exit should be reachable");
            verifyRiftPlacement(generated);
        }
        Level late = LevelGenerator.generate(30, new Random(20260610L));
        require(late.width() <= GameConfig.LEVEL_MAX_WIDTH && late.height() <= GameConfig.LEVEL_MAX_HEIGHT,
                "late generated levels should respect configured maximum dimensions");
        verifyRiftPlacement(late);
    }

    private static void verifyRiftPlacement(Level level) {
        int rifts = 0;
        for (Position position : level.floorPositions()) {
            if (level.tileAt(position) == TileType.RIFT) {
                rifts++;
                require(!position.equals(level.start()) && !position.equals(level.exit()),
                        "rifts should not replace start or exit");
                require(position.manhattanDistance(level.start()) >= GameConfig.LEVEL_RIFT_MIN_START_DISTANCE,
                        "rifts should keep configured distance from start");
            }
        }
        require(rifts <= GameConfig.LEVEL_RIFT_MAX_COUNT, "rift count should respect configured maximum");
    }

    private static void verifyLevelPopulation() {
        Random random = new Random(20260609L);
        Level level = LevelGenerator.generate(4, random);
        LevelPopulation population = new LevelPopulator().populate(level, 4, random);
        require(population.crystals().size() >= population.crystalsNeeded(), "population should place enough crystals");
        require(population.crystals().size() <= GameConfig.CRYSTAL_MAX_COUNT,
                "population should respect crystal count cap");
        require(population.phaseCells().size() <= GameConfig.PHASE_CELL_MAX_COUNT,
                "population should respect phase-cell count cap");
        require(population.rewindCells().size() <= GameConfig.REWIND_CELL_MAX_COUNT,
                "population should respect rewind-cell count cap");
        require(population.enemies().size() <= GameConfig.ENEMY_MAX_COUNT,
                "population should respect enemy count cap");
        require(disjoint(population.crystals(), population.phaseCells()), "crystals and phase cells should not overlap");
        require(disjoint(population.crystals(), population.rewindCells()), "crystals and rewind cells should not overlap");
        require(disjoint(population.phaseCells(), population.rewindCells()), "phase cells and rewind cells should not overlap");
        verifyPickupsAreOnSafeFloor(level, population.crystals());
        verifyPickupsAreOnSafeFloor(level, population.phaseCells());
        verifyPickupsAreOnSafeFloor(level, population.rewindCells());
        for (Enemy enemy : population.enemies()) {
            require(distance(enemy.position(), level.start()) >= 8, "enemies should not spawn too close to start");
            require(distance(enemy.position(), level.exit()) >= 4, "enemies should not spawn too close to exit");
            require(level.isWalkableForEnemy(enemy.position()), "enemies should spawn on enemy-walkable tiles");
        }
    }

    private static void verifyPickupsAreOnSafeFloor(Level level, Set<Position> pickups) {
        for (Position pickup : pickups) {
            require(level.floorPositions().contains(pickup), "pickups should be placed on floor positions");
            require(!pickup.equals(level.exit()), "pickups should not be placed on exit");
            require(level.tileAt(pickup) != TileType.RIFT, "pickups should not be placed on rifts");
        }
    }

    private static void verifyRewindHistory() {
        RewindHistory history = new RewindHistory();
        List<Enemy> enemies = List.of(new Enemy(new Position(2, 2), Direction.DOWN));
        for (int i = 0; i < 150; i++) {
            history.record(new Position(i, 1), enemies, i);
        }
        require(history.size() == 130, "rewind history should cap stored snapshots");
        RewindSnapshot snapshot = history.lookBack();
        require(snapshot.player().equals(new Position(108, 1)), "rewind history should select configured lookback snapshot");
        require(snapshot.enemyPositions().size() == 1, "rewind history should capture enemy positions");
        history.clear();
        require(history.size() == 0, "rewind history should clear snapshots");
    }

    private static void verifyVisualEventQueue() {
        VisualEventQueue queue = new VisualEventQueue();
        queue.emit(VisualEffectType.PHASE_BURST, new Position(1, 1), 7);
        List<VisualEffectEvent> firstDrain = queue.drain();
        require(firstDrain.size() == 1, "visual event queue should drain emitted events");
        require(firstDrain.get(0).tick() == 7, "visual event queue should preserve event tick");
        require(queue.drain().isEmpty(), "visual event queue should be empty after drain");
        queue.emit(VisualEffectType.REWIND_WAVE, new Position(2, 2), 8);
        queue.clear();
        require(queue.drain().isEmpty(), "visual event queue should clear pending events");
    }

    private static void verifySaveDataRoundTrip() {
        try {
            Path file = tempSavePath();
            SaveData first = SaveData.loadFrom(file);
            first.setSoundEnabled(false);
            first.recordGame(1234, true);

            SaveData second = SaveData.loadFrom(file);
            require(second.highScore() == 1234, "save data should reload high score");
            require(second.gamesPlayed() == 1, "save data should reload games played");
            require(second.victories() == 1, "save data should reload victories");
            require(!second.isSoundEnabled(), "save data should reload sound setting");

            Path damagedFile = tempSavePath();
            Files.createDirectories(damagedFile.getParent());
            Files.writeString(damagedFile, "highScore=oops\ngamesPlayed=bad\nvictories=nope\nsoundEnabled=maybe\n");
            SaveData damaged = SaveData.loadFrom(damagedFile);
            require(damaged.highScore() == 0, "damaged save data should fall back for high score");
            require(damaged.gamesPlayed() == 0, "damaged save data should fall back for games played");
            require(damaged.victories() == 0, "damaged save data should fall back for victories");
            require(!damaged.isSoundEnabled(), "damaged boolean save value should parse consistently");
            damaged.recordGame(77, true);
            SaveData recovered = SaveData.loadFrom(damagedFile);
            require(recovered.highScore() == 77 && recovered.gamesPlayed() == 1 && recovered.victories() == 1,
                    "save data should recover by overwriting damaged values");
        } catch (Exception ex) {
            throw new IllegalStateException("save data round trip should succeed", ex);
        }
    }

    private static void verifyScoreRules() {
        require(ScoreRules.levelClearBonus(0, GameConfig.PHASE_MAX, GameConfig.REWIND_MAX)
                        == GameConfig.SCORE_LEVEL_BONUS_BASE
                        + GameConfig.PHASE_MAX * GameConfig.SCORE_LEVEL_BONUS_PHASE_MULTIPLIER
                        + GameConfig.REWIND_MAX * GameConfig.SCORE_LEVEL_BONUS_REWIND_MULTIPLIER,
                "level-clear bonus should reward saved resources");
        require(ScoreRules.levelClearBonus(10_000, 0, 0) == GameConfig.SCORE_LEVEL_BONUS_MIN,
                "level-clear bonus should keep configured minimum");
        require(ScoreRules.crystalPickup(3)
                        == GameConfig.SCORE_CRYSTAL_BASE + 3 * GameConfig.SCORE_CRYSTAL_PER_LEVEL,
                "crystal pickup score should scale by level");
        require(ScoreRules.phaseCellPickup() == GameConfig.SCORE_PHASE_CELL,
                "phase-cell pickup score should come from config");
        require(ScoreRules.rewindCellPickup() == GameConfig.SCORE_REWIND_CELL,
                "rewind-cell pickup score should come from config");
        require(ScoreRules.riftVisit() == GameConfig.SCORE_RIFT, "rift score should come from config");
        require(ScoreRules.phaseActivation() == GameConfig.SCORE_PHASE_ACTIVATE,
                "phase activation score should come from config");
        require(ScoreRules.rewindPenalty() == GameConfig.SCORE_REWIND_PENALTY,
                "rewind penalty should come from config");
        require(ScoreRules.phaseEnemyStun() == GameConfig.SCORE_PHASE_STUN,
                "phase stun score should come from config");
        require(ScoreRules.moveCost() == GameConfig.SCORE_MOVE_COST, "move cost should come from config");
        require(ScoreRules.phaseWallStep() == GameConfig.SCORE_PHASE_WALL_STEP,
                "phase wall-step score should come from config");
        require(ScoreRules.runRank(GameConfig.RANK_FOUR_SCORE) == 5, "rank rules should award top rank at threshold");
        require(ScoreRules.runRank(GameConfig.RANK_THREE_SCORE) == 4, "rank rules should award rank four at threshold");
        require(ScoreRules.runRank(GameConfig.RANK_TWO_SCORE) == 3, "rank rules should award rank three at threshold");
        require(ScoreRules.runRank(GameConfig.RANK_ONE_SCORE) == 2, "rank rules should award rank two at threshold");
        require(ScoreRules.runRank(GameConfig.RANK_TWO_SCORE - 1) == 2,
                "rank rules should keep scores below rank three threshold at rank two");
        require(ScoreRules.runRank(GameConfig.RANK_THREE_SCORE - 1) == 3,
                "rank rules should keep scores below rank four threshold at rank three");
        require(ScoreRules.runRank(GameConfig.RANK_FOUR_SCORE - 1) == 4,
                "rank rules should keep scores below rank five threshold at rank four");
        require(ScoreRules.runRank(GameConfig.RANK_ONE_SCORE - 1) == 1, "rank rules should keep low scores at rank one");
    }

    private static void verifyPhaseRules() {
        require(PhaseRules.gain(GameConfig.PHASE_MAX - 1, GameConfig.PHASE_GAIN_CELL) == GameConfig.PHASE_MAX,
                "phase gain should cap at the configured maximum");
        require(PhaseRules.restartFloor(0) == GameConfig.PHASE_RESTART_MIN,
                "restart should preserve the configured phase minimum");
        require(PhaseRules.recharge(10, GameConfig.PHASE_RECHARGE_INTERVAL_TICKS)
                        == 10 + GameConfig.PHASE_RECHARGE_AMOUNT,
                "phase recharge should happen on the configured cadence");
        require(PhaseRules.recharge(GameConfig.PHASE_MAX, GameConfig.PHASE_RECHARGE_INTERVAL_TICKS)
                        == GameConfig.PHASE_MAX,
                "phase recharge should not exceed the maximum");
        require(PhaseRules.afterRewind(9) == 4, "rewind should halve restored phase time");
        require(PhaseRules.canAfford(GameConfig.PHASE_COST), "phase should be affordable at the exact cost");
    }

    private static void verifyRewindRules() {
        require(RewindRules.gainCharge(GameConfig.REWIND_MAX) == GameConfig.REWIND_MAX,
                "rewind charge gain should cap at the configured maximum");
        require(RewindRules.spendCharge(0) == 0, "rewind charge spending should not go negative");
        require(RewindRules.hasCharge(1), "rewind should be available with at least one charge");
        require(!RewindRules.hasCharge(0), "rewind should be unavailable without charges");
        require(RewindRules.hasEnoughHistory(GameConfig.REWIND_MIN_SNAPSHOTS),
                "rewind should be ready at the exact history threshold");
        require(!RewindRules.hasEnoughHistory(GameConfig.REWIND_MIN_SNAPSHOTS - 1),
                "rewind should wait below the history threshold");
    }

    private static void verifyLevelProgressRules() {
        require(LevelProgressRules.nextLevelEntryBonus(2)
                        == GameConfig.SCORE_LEVEL_CLEAR_BASE + 2 * GameConfig.SCORE_LEVEL_CLEAR_PER_LEVEL,
                "next-level entry bonus should follow configured progression");
        require(LevelProgressRules.nextLevelPhaseGain() == GameConfig.PHASE_GAIN_NEXT_LEVEL,
                "next-level phase gain should come from configured progression");
        require(LevelProgressRules.restartPenalty() == GameConfig.SCORE_RESTART_PENALTY,
                "restart penalty should come from configured progression");
    }

    private static void verifyRiftRules() {
        Set<RiftOutcome> outcomes = new HashSet<>();
        Random random = new Random(7);
        for (int i = 0; i < 12; i++) {
            outcomes.add(RiftRules.rollOutcome(random));
        }
        require(outcomes.contains(RiftOutcome.WARP), "rift rules should be able to roll warp");
        require(outcomes.contains(RiftOutcome.PHASE_GAIN), "rift rules should be able to roll phase gain");
        require(RiftRules.phaseGain() == GameConfig.PHASE_GAIN_RIFT, "rift phase gain should come from config");
        require(RiftRules.teleportMinDistance() == GameConfig.RIFT_TELEPORT_MIN_DISTANCE,
                "rift teleport distance should come from config");
    }

    private static void verifyDeterministicLevelClearFlow() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        TileType[][] tiles = {
                {TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL},
                {TileType.WALL, TileType.FLOOR, TileType.FLOOR, TileType.EXIT, TileType.WALL},
                {TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL}
        };
        Level level = new Level(5, 3, tiles, new Position(1, 1), new Position(3, 1), collectFloors(tiles));
        GameStateFixture fixture = new GameStateFixture(state);
        fixture.installLevel(level, level.start());
        fixture.clearPickupsAndEnemies();
        fixture.crystals().add(new Position(2, 1));
        fixture.setInt("crystalsNeeded", 1);
        fixture.setInt("crystalsCollected", 0);

        state.movePlayer(Direction.RIGHT);
        require(state.crystalsCollected() == 1 && state.isExitOpen(), "flow should collect required crystal");
        state.movePlayer(Direction.RIGHT);
        require(state.mode() == GameMode.LEVEL_CLEAR, "flow should clear level after entering open exit");
        require(state.score() > 0, "flow should award score during deterministic level clear");
        audio.shutdown();
    }

    private static void verifyDeterministicEnemyCollisionFlow() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        Level level = createCollisionFlowLevel();
        GameStateFixture fixture = new GameStateFixture(state);
        fixture.installLevel(level, level.start());
        fixture.clearPickupsAndEnemies();
        fixture.enemies().add(new Enemy(new Position(2, 1), Direction.LEFT));
        fixture.setInt("crystalsNeeded", 1);

        state.movePlayer(Direction.RIGHT);
        require(state.mode() == GameMode.GAME_OVER, "collision flow should end game when phase is inactive");

        GameState phased = new GameState(SaveData.loadFrom(tempSavePath()), audio);
        GameStateFixture phasedFixture = new GameStateFixture(phased);
        phasedFixture.installLevel(level, level.start());
        phasedFixture.clearPickupsAndEnemies();
        Enemy enemy = new Enemy(new Position(2, 1), Direction.LEFT);
        phasedFixture.enemies().add(enemy);
        phasedFixture.setInt("crystalsNeeded", 1);
        phased.activatePhase();
        phased.movePlayer(Direction.RIGHT);
        require(phased.mode() == GameMode.PLAYING, "phase collision should keep game playing");
        require(enemy.stunnedTicks() == GameConfig.ENEMY_PHASE_STUN_TICKS,
                "phase collision should stun the enemy");
        audio.shutdown();
    }

    private static Level createCollisionFlowLevel() {
        TileType[][] tiles = {
                {TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL},
                {TileType.WALL, TileType.FLOOR, TileType.FLOOR, TileType.WALL},
                {TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL}
        };
        return new Level(4, 3, tiles, new Position(1, 1), new Position(2, 1), collectFloors(tiles));
    }

    private static void verifyDeterministicRewindFlow() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        Level level = createRewindFlowLevel();
        GameStateFixture fixture = new GameStateFixture(state);
        fixture.installLevel(level, new Position(3, 1));
        fixture.clearPickupsAndEnemies();
        List<Enemy> enemies = fixture.enemies();
        enemies.add(new Enemy(new Position(3, 2), Direction.LEFT));
        fixture.setInt("rewindCharges", 1);
        fixture.setInt("phaseTicks", 8);
        RewindHistory history = fixture.rewindHistory();
        history.clear();
        for (int i = 0; i < GameConfig.REWIND_MIN_SNAPSHOTS; i++) {
            enemies.get(0).setPosition(new Position(2, 2));
            history.record(new Position(1, 1), enemies, 8);
        }
        enemies.get(0).setPosition(new Position(3, 2));

        state.rewind();
        require(state.player().equals(new Position(1, 1)), "rewind flow should restore player position");
        require(state.rewindCharges() == 0, "rewind flow should spend one charge");
        require(state.phaseTicks() == 4, "rewind flow should restore half snapshot phase time");
        require(state.enemies().get(0).position().equals(new Position(2, 2)),
                "rewind flow should restore enemy position");
        require(state.enemies().get(0).stunnedTicks() == GameConfig.REWIND_ENEMY_STUN_TICKS,
                "rewind flow should stun restored enemies");
        require(!state.drainVisualEffects().isEmpty(), "rewind flow should emit visual effects");
        audio.shutdown();
    }

    private static Level createRewindFlowLevel() {
        TileType[][] tiles = {
                {TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL},
                {TileType.WALL, TileType.FLOOR, TileType.FLOOR, TileType.FLOOR, TileType.WALL},
                {TileType.WALL, TileType.FLOOR, TileType.FLOOR, TileType.FLOOR, TileType.WALL},
                {TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL}
        };
        return new Level(5, 4, tiles, new Position(1, 1), new Position(3, 1), collectFloors(tiles));
    }

    private static void verifyDeterministicPhaseRecoveryFlow() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        TileType[][] tiles = {
                {TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL},
                {TileType.WALL, TileType.FLOOR, TileType.WALL, TileType.WALL},
                {TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL}
        };
        Level level = new Level(4, 3, tiles, new Position(1, 1), new Position(1, 1), collectFloors(tiles));
        GameStateFixture fixture = new GameStateFixture(state);
        fixture.installLevel(level, new Position(2, 1));
        fixture.setInt("phaseTicks", 1);
        state.update();
        require(state.player().equals(new Position(1, 1)), "phase recovery should move player to nearest walkable tile");
        require(state.message().equals(GameMessages.PHASE_ENDED_SAFE),
                "phase recovery should show safe ending message");
        audio.shutdown();
    }

    private static void verifyGameMessages() {
        require(GameMessages.START_HINT.contains("星核"), "start hint should remain readable Chinese");
        require(GameMessages.levelStart(3, 8).contains("第 3 层"), "level start message should include level index");
        require(GameMessages.crystalCollected(2, 5).endsWith("2/5"), "crystal message should include progress");
        require(GameMessages.exitLocked(4).contains("4 枚星核"), "exit lock message should include remaining crystals");
    }

    private static void verifyHudText() {
        require(HudText.TITLE.contains("星轨"), "HUD title text should remain readable Chinese");
        require(HudText.DEFAULT_HINT.contains("WASD"), "HUD default hint should keep keyboard controls");
        require(HudText.COMPACT_HINT.contains("帮助"), "HUD compact hint should remain useful");
    }

    private static void verifyHudLayout() {
        HudLayout layout = HudLayout.standard();
        require(layout.baseline() == 54, "HUD layout should keep the shared text baseline");
        require(layout.phaseMeterX() == 570 && layout.phaseMeterY() == 35,
                "HUD layout should keep phase meter position");
        require(layout.phaseMeterW() == 150 && layout.phaseMeterH() == 16,
                "HUD layout should keep phase meter dimensions");
        require(layout.rewindX() == 750 && layout.rewindY() == 43,
                "HUD layout should keep rewind charge position");
    }

    private static void verifyHudMessageSelector() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        state.startNewGame();
        HudMessageSelector selector = new HudMessageSelector();
        setIntField(state, "messageTicks", 0);
        require(selector.select(state).equals(HudText.DEFAULT_HINT),
                "HUD message selector should use default hint without active message");
        setField(state, "message", "HUD selector smoke");
        setIntField(state, "messageTicks", 5);
        require(selector.select(state).equals("HUD selector smoke"),
                "HUD message selector should use active state message while ticks remain");
        audio.shutdown();
    }

    private static void verifyHudPhaseColorSelector() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        state.startNewGame();
        HudPhaseColorSelector selector = new HudPhaseColorSelector();
        require(selector.select(state).equals(VisualConfig.CYAN),
                "HUD phase color selector should use cyan outside phase");
        state.activatePhase();
        require(selector.select(state).equals(VisualConfig.PINK),
                "HUD phase color selector should use pink during active phase");
        audio.shutdown();
    }

    private static void verifyHudMeterRenderer() {
        HudMeterRenderer renderer = new HudMeterRenderer();
        BufferedImage image = new BufferedImage(260, 80, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, 24, 34, 160, 16, 60, 100, VisualConfig.CYAN, HudText.PHASE_LABEL);
        graphics.dispose();
        require(countVisibleSamplePixels(image) > 5, "HUD meter renderer should draw visible meter pixels");
    }

    private static void verifyHudMeterGeometry() {
        HudMeterGeometry half = HudMeterGeometry.from(24, 34, 160, 16, 50, 100);
        HudMeterGeometry over = HudMeterGeometry.from(24, 34, 160, 16, 120, 100);
        HudMeterGeometry under = HudMeterGeometry.from(24, 34, 160, 16, -5, 100);
        HudMeterGeometry zeroMax = HudMeterGeometry.from(24, 34, 160, 16, 50, 0);
        require(half.trackX() == 58 && half.trackY() == 34, "HUD meter geometry should offset track after label");
        require(half.fillW() == 80, "HUD meter geometry should scale fill width from value");
        require(over.fillW() == 160, "HUD meter geometry should clamp overfilled values");
        require(under.fillW() == 0, "HUD meter geometry should clamp negative values");
        require(zeroMax.fillW() == 0, "HUD meter geometry should handle zero max safely");
    }

    private static void verifyHudRewindRenderer() {
        HudRewindRenderer renderer = new HudRewindRenderer();
        BufferedImage image = new BufferedImage(180, 80, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, 18, 42, 2);
        graphics.dispose();
        require(countVisibleSamplePixels(image) > 3, "HUD rewind renderer should draw visible charge dots");
    }

    private static void verifyHudRewindDot() {
        HudRewindDot first = HudRewindDot.from(18, 42, 0, 2);
        HudRewindDot third = HudRewindDot.from(18, 42, 2, 2);
        require(first.x() == 56 && first.y() == 33, "HUD rewind dot should offset from label origin");
        require(third.x() - first.x() == 40, "HUD rewind dots should keep configured spacing");
        require(first.size() == third.size(), "HUD rewind dots should keep consistent size");
        require(first.filled(), "HUD rewind dots below charge count should be filled");
        require(!third.filled(), "HUD rewind dots at or above charge count should be empty");
        require(!first.fillColor().equals(third.fillColor()), "filled and empty rewind dots should use different colors");
    }

    private static void verifyHudMessageRenderer() {
        HudMessageRenderer renderer = new HudMessageRenderer();
        BufferedImage image = new BufferedImage(980, 80, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, HudText.DEFAULT_HINT, 980, 42);
        graphics.dispose();
        require(countNonTransparentPixels(image) > 20, "HUD message renderer should draw visible hint text");
    }

    private static void verifyHudStatsRenderer() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        state.startNewGame();
        HudStatsRenderer renderer = new HudStatsRenderer();
        BufferedImage image = new BufferedImage(560, 90, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, state, 50);
        graphics.dispose();
        audio.shutdown();
        require(countNonTransparentPixels(image) > 80, "HUD stats renderer should draw visible run stats text");
    }

    private static void verifyHudStatsSpec() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        state.startNewGame();
        HudStatItem title = HudStatsSpec.titleItem();
        require(title.text(state).equals(HudText.TITLE), "HUD stats title item should use HUD title text");
        require(HudStatsSpec.statItems().size() == 4, "HUD stats spec should keep level, score, high score, and crystal items");
        require(HudStatsSpec.statTexts(state).get(0).contains(String.valueOf(state.levelIndex())),
                "HUD stats level text should include level index");
        require(HudStatsSpec.statTexts(state).get(1).contains(String.valueOf(state.score())),
                "HUD stats score text should include score");
        require(HudStatsSpec.statTexts(state).get(2).contains(String.valueOf(state.saveData().highScore())),
                "HUD stats high-score text should include high score");
        require(HudStatsSpec.statTexts(state).get(3).contains("/"),
                "HUD stats crystal text should include collected/needed separator");
        require(HudStatsSpec.statItems().get(0).x() < HudStatsSpec.statItems().get(1).x(),
                "HUD stats spec should keep stats ordered from left to right");
        audio.shutdown();
    }

    private static void verifyHudPanelRenderer() {
        HudPanelRenderer renderer = new HudPanelRenderer();
        BufferedImage image = new BufferedImage(420, 90, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, 420);
        graphics.dispose();
        require(countNonTransparentPixels(image) > 4_000, "HUD panel renderer should draw visible panel chrome");
    }

    private static void verifyHudRendererComposition() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        state.startNewGame();
        HudRenderer renderer = new HudRenderer();
        BufferedImage image = new BufferedImage(VisualConfig.WINDOW_DEFAULT_WIDTH, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, state, VisualConfig.WINDOW_DEFAULT_WIDTH);
        setField(state, "message", "HUD smoke message");
        setIntField(state, "messageTicks", 5);
        renderer.paint(graphics, state, VisualConfig.WINDOW_DEFAULT_WIDTH);
        graphics.dispose();
        audio.shutdown();
        require(countNonTransparentPixels(image) > 8_000, "HUD renderer should compose panel, stats, meters, and message");
    }

    private static void verifyCadenceConfig() {
        require(GameConfig.ENEMY_MOVE_INTERVAL_TICKS > 0, "enemy movement cadence should be positive");
        require(GameConfig.REWIND_SNAPSHOT_INTERVAL_TICKS > 0, "rewind snapshot cadence should be positive");
        require(GameConfig.PHASE_RECHARGE_INTERVAL_TICKS > 0, "phase recharge cadence should be positive");
        require(GameConfig.REWIND_SNAPSHOT_INTERVAL_TICKS < GameConfig.REWIND_MIN_SNAPSHOTS,
                "rewind snapshots should accumulate before the minimum snapshot threshold becomes unreachable");
    }

    private static void verifyActiveEffectFactory() {
        VisualEffectEvent event = new VisualEffectEvent(VisualEffectType.REWIND_WAVE, new Position(2, 3), 99);
        ActiveEffect effect = new ActiveEffectFactory().fromEvent(event, 42);
        require(effect.type() == event.type(), "active effect factory should preserve event type");
        require(effect.position().equals(event.position()), "active effect factory should preserve event position");
        require(effect.startFrame() == 42, "active effect factory should use the render frame as start frame");
    }

    private static void verifyActiveEffectList() {
        ActiveEffectList effects = new ActiveEffectList();
        for (int i = 0; i < VisualConfig.MAX_EFFECTS + 3; i++) {
            effects.add(new ActiveEffect(VisualEffectType.PHASE_BURST, new Position(i, 0), i));
        }
        require(effects.view().size() == VisualConfig.MAX_EFFECTS, "active effect list should trim to capacity");
        require(effects.view().get(0).startFrame() == 3, "active effect list should discard oldest effects first");
        effects.pruneExpired(VisualConfig.MAX_EFFECTS + 3 + VisualConfig.EFFECT_LIFETIME_FRAMES + 1);
        require(effects.view().isEmpty(), "active effect list should prune expired effects");
    }

    private static void verifyEffectLayer() {
        EffectLayer layer = new EffectLayer();
        List<VisualEffectEvent> events = new ArrayList<>();
        for (int i = 0; i < VisualConfig.MAX_EFFECTS + 5; i++) {
            events.add(new VisualEffectEvent(VisualEffectType.PHASE_BURST, new Position(1, 1), i));
        }
        layer.advance(events, 10);
        require(layer.effects().size() == VisualConfig.MAX_EFFECTS, "effect layer should cap active effects");
        layer.advance(List.of(), 10 + VisualConfig.EFFECT_LIFETIME_FRAMES + 1);
        require(layer.effects().isEmpty(), "effect layer should prune expired effects");
    }

    private static void verifyEffectProgress() {
        require(EffectProgress.fromFrames(10, 5) == 0.0, "effect progress should clamp negative age to zero");
        require(EffectProgress.fromFrames(10, 10) == 0.0, "effect progress should start at zero");
        require(EffectProgress.fromFrames(0, VisualConfig.EFFECT_LIFETIME_FRAMES / 2) > 0.0,
                "effect progress should advance during lifetime");
        require(EffectProgress.fromFrames(0, VisualConfig.EFFECT_LIFETIME_FRAMES * 2) == 1.0,
                "effect progress should clamp past lifetime to one");
    }

    private static void verifyEffectRenderDispatcher() {
        EffectRenderDispatcher dispatcher = new EffectRenderDispatcher();
        BufferedImage image = new BufferedImage(260, 220, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        dispatcher.paint(graphics, VisualEffectType.PHASE_BURST, 60, 60, 0.25, 42, 8);
        dispatcher.paint(graphics, VisualEffectType.RIFT_WARP, 140, 60, 0.35, 42, 9);
        dispatcher.paint(graphics, VisualEffectType.REWIND_WAVE, 60, 140, 0.45, 42, 10);
        dispatcher.paint(graphics, VisualEffectType.ENEMY_STUN, 140, 140, 0.2, 42, 11);
        graphics.dispose();
        require(countNonTransparentPixels(image) > 60, "effect render dispatcher should draw all effect types");
    }

    private static void verifyEffectRenderSpec() {
        EffectRenderSpec phase = EffectRenderSpec.forType(VisualEffectType.PHASE_BURST);
        EffectRenderSpec rift = EffectRenderSpec.forType(VisualEffectType.RIFT_WARP);
        EffectRenderSpec rewind = EffectRenderSpec.forType(VisualEffectType.REWIND_WAVE);
        EffectRenderSpec stun = EffectRenderSpec.forType(VisualEffectType.ENEMY_STUN);
        require(phase.kind() == EffectRenderKind.RING, "phase burst should render as ring");
        require(rift.kind() == EffectRenderKind.RING, "rift warp should render as ring");
        require(rewind.kind() == EffectRenderKind.RING, "rewind wave should render as ring");
        require(stun.kind() == EffectRenderKind.STUN, "enemy stun should render as stun markers");
        require(phase.ringColor().equals(VisualConfig.CYAN), "phase burst ring should use cyan");
        require(rewind.ringColor().equals(VisualConfig.GOLD), "rewind wave ring should use gold");
        require(rift.ringScale() > phase.ringScale(), "rift ring should be larger than phase ring");
        require(rewind.ringScale() > rift.ringScale(), "rewind ring should be the largest ring effect");
    }

    private static void verifyScreenShakeRenderer() {
        ScreenShakeRenderer renderer = new ScreenShakeRenderer();
        BufferedImage image = new BufferedImage(120, 120, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        AffineTransform before = graphics.getTransform();
        renderer.apply(graphics, List.of(new ActiveEffect(VisualEffectType.PHASE_BURST, new Position(1, 1), 0)), 8);
        require(graphics.getTransform().equals(before), "screen shake should ignore non-shaking effect types");
        renderer.apply(graphics, List.of(new ActiveEffect(VisualEffectType.RIFT_WARP, new Position(1, 1), 0)), 8);
        require(!graphics.getTransform().equals(before), "screen shake should translate graphics for rift or rewind effects");
        graphics.dispose();
    }

    private static void verifyScreenShakeRules() {
        require(!ScreenShakeRules.shakesScreen(VisualEffectType.PHASE_BURST),
                "phase burst should not shake the screen");
        require(ScreenShakeRules.shakesScreen(VisualEffectType.RIFT_WARP),
                "rift warp should shake the screen");
        require(ScreenShakeRules.shakesScreen(VisualEffectType.REWIND_WAVE),
                "rewind wave should shake the screen");
        ScreenShakeOffset ignored = ScreenShakeRules.offset(
                List.of(new ActiveEffect(VisualEffectType.ENEMY_STUN, new Position(1, 1), 0)), 8);
        ScreenShakeOffset active = ScreenShakeRules.offset(
                List.of(new ActiveEffect(VisualEffectType.RIFT_WARP, new Position(1, 1), 0)), 8);
        ScreenShakeOffset expired = ScreenShakeRules.offset(
                List.of(new ActiveEffect(VisualEffectType.RIFT_WARP, new Position(1, 1),
                        -VisualConfig.EFFECT_LIFETIME_FRAMES)), 8);
        require(!ignored.active(), "non-shaking effects should not produce screen offset");
        require(active.active(), "active rift effect should produce screen offset");
        require(!expired.active(), "expired rift effect should not produce screen offset");
    }

    private static void verifyEffectRendererComposition() {
        EffectRenderer renderer = new EffectRenderer();
        BoardMetrics metrics = new BoardMetrics(20, 20, 240, 240, 48);
        List<ActiveEffect> effects = List.of(
                new ActiveEffect(VisualEffectType.PHASE_BURST, new Position(1, 1), 0),
                new ActiveEffect(VisualEffectType.RIFT_WARP, new Position(2, 1), 0),
                new ActiveEffect(VisualEffectType.REWIND_WAVE, new Position(1, 2), 0),
                new ActiveEffect(VisualEffectType.ENEMY_STUN, new Position(2, 2), 0));
        BufferedImage image = new BufferedImage(320, 320, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        renderer.applyScreenShake(graphics, effects, 8);
        renderer.paint(graphics, effects, metrics, 8);
        graphics.dispose();
        require(countNonTransparentPixels(image) > 80, "effect renderer should compose rings, stun markers, and shake safely");
    }

    private static void verifyRingEffectRenderer() {
        RingEffectRenderer renderer = new RingEffectRenderer();
        BufferedImage image = new BufferedImage(180, 180, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, 90, 90, 0.25, VisualConfig.CYAN, 48, 1.6);
        graphics.dispose();
        require(countVisibleSamplePixels(image) > 4, "ring effect renderer should draw visible pixels");
    }

    private static void verifyRingEffectStyle() {
        RingEffectStyle early = RingEffectStyle.fromProgress(0.0, 48, 1.6);
        RingEffectStyle late = RingEffectStyle.fromProgress(0.75, 48, 1.6);
        RingEffectStyle expired = RingEffectStyle.fromProgress(1.0, 48, 1.6);
        require(early.visible(), "early ring effect style should be visible");
        require(!expired.visible(), "expired ring effect style should be hidden");
        require(late.radius() > early.radius(), "ring effect radius should expand with progress");
        require(late.alpha() < early.alpha(), "ring effect alpha should fade with progress");
        require(late.strokeWidth() <= early.strokeWidth(), "ring effect stroke should narrow with progress");
        require(early.strokeColor(VisualConfig.CYAN).getAlpha() == early.alpha(),
                "ring effect stroke color should apply alpha");
        require(early.fillColor(VisualConfig.CYAN).getAlpha() == early.alpha() / 4,
                "ring effect fill color should apply softer alpha");
    }

    private static void verifyStunEffectRenderer() {
        StunEffectRenderer renderer = new StunEffectRenderer();
        BufferedImage image = new BufferedImage(180, 180, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, 90, 90, 0.2, 48, 12);
        graphics.dispose();
        require(countNonTransparentPixels(image) > 20, "stun effect renderer should draw visible pixels");
    }

    private static void verifyStunEffectStyle() {
        StunEffectStyle early = StunEffectStyle.fromProgress(0.0, 48);
        StunEffectStyle late = StunEffectStyle.fromProgress(0.75, 48);
        StunEffectStyle expired = StunEffectStyle.fromProgress(1.0, 48);
        require(early.visible(), "early stun effect style should be visible");
        require(!expired.visible(), "expired stun effect style should be hidden");
        require(early.dotCount() == 3, "stun effect should keep three orbiting dots");
        require(late.orbit() > early.orbit(), "stun effect orbit should expand with progress");
        require(late.alpha() < early.alpha(), "stun effect alpha should fade with progress");
        require(early.dotColor().getAlpha() == early.alpha(), "stun effect dot color should apply alpha");
        require(!early.dotCenter(90, 90, 12, 0).equals(early.dotCenter(90, 90, 13, 0)),
                "stun effect dot center should advance with render frame");
    }

    private static void verifyInputActions() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        InputActions.handleEnter(state);
        require(state.mode() == GameMode.PLAYING, "enter should start from title");
        InputActions.handleEscape(state);
        require(state.mode() == GameMode.PAUSED, "escape should pause during play");
        InputActions.handleEscape(state);
        require(state.mode() == GameMode.PLAYING, "escape should resume from pause");
        state.setMode(GameMode.HELP);
        InputActions.handleEscape(state);
        require(state.mode() == GameMode.TITLE, "escape should return menu screens to title");
        audio.shutdown();
    }

    private static void verifyInputControllerBindings() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        JPanel panel = new JPanel();
        int[] repaintCalls = {0};
        new InputController(state, panel, () -> repaintCalls[0]++).install();
        require(boundAction(panel, "pressed W") != null, "input controller should bind W movement");
        require(boundAction(panel, "pressed UP") != null, "input controller should bind arrow movement");
        require(boundAction(panel, "pressed SPACE") != null, "input controller should bind phase activation");
        require(boundAction(panel, "pressed R") != null, "input controller should bind rewind");
        require(boundAction(panel, "pressed ESCAPE") != null, "input controller should bind escape");
        boundAction(panel, "pressed H").actionPerformed(null);
        require(state.mode() == GameMode.HELP, "input controller help binding should update mode");
        require(repaintCalls[0] == 1, "input controller actions should request repaint");
        audio.shutdown();
    }

    private static Action boundAction(JComponent component, String keyStroke) {
        Object actionKey = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).get(KeyStroke.getKeyStroke(keyStroke));
        return actionKey == null ? null : component.getActionMap().get(actionKey);
    }

    private static void verifyGamePanelInputBinder() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        JPanel panel = new JPanel();
        panel.setSize(VisualConfig.WINDOW_DEFAULT_WIDTH, VisualConfig.WINDOW_DEFAULT_HEIGHT);
        MenuButtonCache buttons = new MenuButtonCache();
        int[] focusCalls = {0};
        int[] repaintCalls = {0};
        new GamePanelInputBinder().install(state, panel, buttons, () -> focusCalls[0]++, () -> repaintCalls[0]++);
        require(boundAction(panel, "pressed ENTER") != null, "panel input binder should install keyboard actions");
        require(panel.getMouseListeners().length > 0, "panel input binder should install menu mouse listener");
        require(panel.getMouseMotionListeners().length > 0,
                "panel input binder should install menu mouse motion listener");
        boundAction(panel, "pressed ENTER").actionPerformed(null);
        require(state.mode() == GameMode.PLAYING, "panel input binder should route keyboard actions");
        require(repaintCalls[0] == 1, "panel input binder keyboard action should request repaint");
        require(focusCalls[0] == 0, "panel input binder keyboard action should not request menu focus");
        audio.shutdown();
    }

    private static void verifyGamePanelConfigurator() {
        JPanel panel = new JPanel();
        GamePanelConfigurator.apply(panel);
        require(panel.isFocusable(), "panel configurator should make the game panel focusable");
        require(panel.isDoubleBuffered(), "panel configurator should enable Swing double buffering");
        require(VisualConfig.BACK_TOP.equals(panel.getBackground()),
                "panel configurator should apply the shared background color");
    }

    private static void verifyMenuActionHandler() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        MenuActionHandler handler = new MenuActionHandler();
        handler.handle(state, MenuActions.START);
        require(state.mode() == GameMode.PLAYING, "menu start action should start gameplay");
        handler.handle(state, MenuActions.HELP);
        require(state.mode() == GameMode.HELP, "menu help action should open help");
        boolean before = saveData.isSoundEnabled();
        handler.handle(state, MenuActions.SOUND);
        require(saveData.isSoundEnabled() != before, "menu sound action should toggle sound");
        audio.shutdown();
    }

    private static void verifyMenuMouseController() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        state.setMode(GameMode.TITLE);
        JPanel panel = new JPanel();
        panel.setSize(VisualConfig.WINDOW_DEFAULT_WIDTH, VisualConfig.WINDOW_DEFAULT_HEIGHT);
        MenuButtonCache buttons = new MenuButtonCache();
        int[] focusCalls = {0};
        int[] repaintCalls = {0};
        new MenuMouseController(state, panel, buttons, new MenuActionHandler(), () -> focusCalls[0]++,
                () -> repaintCalls[0]++).install();

        MenuButton start = new MenuLayout().layout(GameMode.TITLE, panel.getWidth(), panel.getHeight()).get(0);
        int x = start.x() + start.w() / 2;
        int y = start.y() + start.h() / 2;
        panel.dispatchEvent(new MouseEvent(panel, MouseEvent.MOUSE_MOVED, 1L, 0, x, y, 0, false));
        require(panel.getCursor().getType() == Cursor.HAND_CURSOR,
                "menu mouse hover should use hand cursor over buttons");
        panel.dispatchEvent(new MouseEvent(panel, MouseEvent.MOUSE_CLICKED, 2L, 0, x, y, 1, false));
        require(state.mode() == GameMode.PLAYING, "menu mouse click should dispatch button action");
        require(focusCalls[0] == 1, "menu mouse click should request focus after action");
        require(repaintCalls[0] == 1, "menu mouse click should request repaint after action");
        audio.shutdown();
    }

    private static void verifyMenuLayout() {
        MenuLayout layout = new MenuLayout();
        require(actions(layout.layout(GameMode.TITLE, 1100, 780)).equals(Set.of(MenuActions.START, MenuActions.HELP, MenuActions.SETTINGS)),
                "title menu should expose start, help, and settings");
        require(actions(layout.layout(GameMode.PAUSED, 1100, 780)).equals(Set.of(MenuActions.RESUME, MenuActions.RESTART_LEVEL, MenuActions.TITLE)),
                "pause menu should expose resume, restart, and title");
        require(actions(layout.layout(GameMode.HELP, 1100, 780)).equals(Set.of(MenuActions.TITLE)),
                "help menu should expose title return");
        require(actions(layout.layout(GameMode.SETTINGS, 1100, 780)).equals(Set.of(MenuActions.SOUND, MenuActions.TITLE)),
                "settings menu should expose sound and title");
        require(actions(layout.layout(GameMode.LEVEL_CLEAR, 1100, 780)).equals(Set.of(MenuActions.NEXT, MenuActions.TITLE)),
                "level-clear menu should expose next and title");
        require(actions(layout.layout(GameMode.GAME_OVER, 1100, 780)).equals(Set.of(MenuActions.START, MenuActions.TITLE)),
                "game-over menu should expose restart and title");
        require(layout.layout(GameMode.PLAYING, 1100, 780).isEmpty(), "playing mode should not expose menu buttons");
    }

    private static void verifyMenuButtonCache() {
        MenuButtonCache cache = new MenuButtonCache();
        List<MenuButton> first = cache.update(GameMode.TITLE, 1100, 780);
        List<MenuButton> second = cache.update(GameMode.TITLE, 1100, 780);
        require(first == second, "menu button cache should reuse unchanged layouts");
        MenuButton start = cache.hit(first.get(0).x() + 1, first.get(0).y() + 1);
        require(start != null && MenuActions.START.equals(start.action()), "menu button cache should hit visible buttons");
        cache.update(GameMode.PLAYING, 1100, 780);
        require(cache.hit(first.get(0).x() + 1, first.get(0).y() + 1) == null, "menu button cache should clear hits in playing mode");
    }

    private static void verifyMenuButtonRenderer() {
        MenuButtonRenderer renderer = new MenuButtonRenderer();
        BufferedImage image = new BufferedImage(220, 90, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, List.of(new MenuButton(MenuActions.START, 30, 20, 160, 44)),
                List.of("START"));
        graphics.dispose();
        require(countVisibleSamplePixels(image) > 8, "menu button renderer should draw a visible button");
    }

    private static void verifyMenuButtonGroupRenderer() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        state.setMode(GameMode.TITLE);
        MenuButtonGroupRenderer renderer = new MenuButtonGroupRenderer();
        BufferedImage image = new BufferedImage(420, 150, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, state, List.of(
                new MenuButton(MenuActions.START, 40, 24, 180, 44),
                new MenuButton(MenuActions.HELP, 40, 78, 180, 44)));
        graphics.dispose();
        audio.shutdown();
        require(countVisibleSamplePixels(image) > 16, "menu button group renderer should draw labeled buttons");
    }

    private static void verifyMenuChromeRenderer() {
        MenuChromeRenderer renderer = new MenuChromeRenderer();
        BufferedImage image = new BufferedImage(420, 260, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        renderer.paintOverlay(graphics, 420, 260, 0.6f);
        renderer.paintPanel(graphics, new MenuPanel(70, 44, 280, 150));
        graphics.dispose();
        require(countNonTransparentPixels(image) > 20_000, "menu chrome renderer should draw overlay and panel chrome");
    }

    private static void verifyMenuOverlayMode() {
        require(MenuOverlayMode.from(GameMode.TITLE).orElseThrow() == MenuOverlayMode.TITLE,
                "title mode should map to title overlay");
        require(MenuOverlayMode.from(GameMode.PAUSED).orElseThrow() == MenuOverlayMode.PAUSED,
                "pause mode should map to pause overlay");
        require(MenuOverlayMode.from(GameMode.HELP).orElseThrow() == MenuOverlayMode.HELP,
                "help mode should map to help overlay");
        require(MenuOverlayMode.from(GameMode.SETTINGS).orElseThrow() == MenuOverlayMode.SETTINGS,
                "settings mode should map to settings overlay");
        require(MenuOverlayMode.from(GameMode.LEVEL_CLEAR).orElseThrow() == MenuOverlayMode.LEVEL_CLEAR,
                "level-clear mode should map to level-clear overlay");
        require(MenuOverlayMode.from(GameMode.GAME_OVER).orElseThrow() == MenuOverlayMode.GAME_OVER,
                "game-over mode should map to game-over overlay");
        require(MenuOverlayMode.from(GameMode.PLAYING).isEmpty(), "playing mode should not map to a menu overlay");
    }

    private static void verifyMenuOverlayDispatcher() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        state.startNewGame();
        MenuButtonCache buttons = new MenuButtonCache();
        MenuOverlayDispatcher dispatcher = new MenuOverlayDispatcher();
        BufferedImage image = new BufferedImage(900, 620, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        for (MenuOverlayMode mode : MenuOverlayMode.values()) {
            state.setMode(GameMode.valueOf(mode.name()));
            buttons.update(state.mode(), 900, 620);
            dispatcher.paint(graphics, state, buttons, 900, 620, mode);
        }
        graphics.dispose();
        audio.shutdown();
        require(countVisibleSamplePixels(image) > 160, "menu overlay dispatcher should draw all visible overlays");
    }

    private static void verifyMenuOverlaySpec() {
        for (MenuOverlayMode mode : MenuOverlayMode.values()) {
            require(MenuOverlaySpec.forMode(mode).name().equals(mode.name()),
                    "menu overlay spec should mirror overlay mode names");
        }
        require(MenuOverlaySpec.values().length == MenuOverlayMode.values().length,
                "menu overlay spec should cover every overlay mode");
    }

    private static void verifyMenuOverlayStyle() {
        for (MenuOverlayMode mode : MenuOverlayMode.values()) {
            MenuOverlayStyle style = MenuOverlayStyle.forMode(mode);
            require(style.name().equals(mode.name()), "menu overlay style should mirror overlay mode names");
            require(style.overlayAlpha() > 0.0f && style.overlayAlpha() < 1.0f,
                    "menu overlay alpha should remain translucent");
        }
        require(MenuOverlayStyle.values().length == MenuOverlayMode.values().length,
                "menu overlay style should cover every overlay mode");
    }

    private static void verifyMenuOverlayRenderer() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        state.startNewGame();
        MenuButtonCache buttons = new MenuButtonCache();
        MenuOverlayRenderer renderer = new MenuOverlayRenderer();
        BufferedImage menuImage = new BufferedImage(900, 620, BufferedImage.TYPE_INT_ARGB);
        Graphics2D menuGraphics = menuImage.createGraphics();
        for (GameMode mode : List.of(GameMode.TITLE, GameMode.PAUSED, GameMode.HELP, GameMode.SETTINGS,
                GameMode.LEVEL_CLEAR, GameMode.GAME_OVER)) {
            state.setMode(mode);
            buttons.update(state.mode(), 900, 620);
            renderer.paint(menuGraphics, state, buttons, 900, 620);
        }
        menuGraphics.dispose();
        state.setMode(GameMode.PLAYING);
        buttons.update(state.mode(), 900, 620);
        BufferedImage playingImage = new BufferedImage(900, 620, BufferedImage.TYPE_INT_ARGB);
        Graphics2D playingGraphics = playingImage.createGraphics();
        renderer.paint(playingGraphics, state, buttons, 900, 620);
        playingGraphics.dispose();
        audio.shutdown();
        require(countVisibleSamplePixels(menuImage) > 160, "menu overlay renderer should draw all visible menu overlays");
        require(countNonTransparentPixels(playingImage) == 0, "menu overlay renderer should not draw during gameplay");
    }

    private static void verifyHelpMenuContentRenderer() {
        HelpMenuContentRenderer renderer = new HelpMenuContentRenderer();
        BufferedImage image = new BufferedImage(640, 420, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, new MenuPanel(40, 30, 560, 340));
        graphics.dispose();
        require(countVisibleSamplePixels(image) > 40, "help menu content renderer should draw visible help text");
    }

    private static void verifyHelpMenuContentSpec() {
        HelpMenuContentSpec spec = HelpMenuContentSpec.standard();
        require(spec.title().equals(MenuText.HELP_TITLE), "help menu content spec should use help title");
        require(spec.lines().size() == MenuText.HELP_LINES.length, "help menu content spec should include every help line");
        require(spec.lineBaseline(1) - spec.lineBaseline(0) == spec.lineGap(),
                "help menu content spec should space help lines by configured gap");
        require(spec.dotColor().getAlpha() > 0 && spec.dotColor().getAlpha() < 255,
                "help menu content spec should use translucent dots");
        require(spec.textXOffset() > spec.dotSize(), "help menu text should sit to the right of its dot");
    }

    private static void verifyResultMenuContentRenderer() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        state.startNewGame();
        ResultMenuContentRenderer renderer = new ResultMenuContentRenderer();
        BufferedImage image = new BufferedImage(520, 260, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        MenuPanel panel = new MenuPanel(30, 24, 460, 180);
        renderer.paintLevelClear(graphics, state, panel);
        state.setMode(GameMode.GAME_OVER);
        renderer.paintGameOver(graphics, state, panel);
        graphics.dispose();
        audio.shutdown();
        require(countVisibleSamplePixels(image) > 30, "result menu content renderer should draw visible result text");
    }

    private static void verifyResultMenuContentSpec() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        state.startNewGame();
        require(ResultMenuContentSpec.LEVEL_CLEAR.lines().size() == 3,
                "level-clear result content should keep title, summary, and rank lines");
        require(ResultMenuContentSpec.GAME_OVER.lines().size() == 3,
                "game-over result content should keep title, message, and score lines");
        require(ResultMenuContentSpec.LEVEL_CLEAR.lines().get(0).text(state).equals(MenuText.LEVEL_CLEAR_TITLE),
                "level-clear result title should use level-clear text");
        require(ResultMenuContentSpec.GAME_OVER.lines().get(0).text(state).equals(MenuText.GAME_OVER_TITLE),
                "game-over result title should use game-over text");
        require(ResultMenuContentSpec.LEVEL_CLEAR.lines().get(1).text(state).contains(String.valueOf(state.score())),
                "level-clear result summary should include score");
        require(ResultMenuContentSpec.GAME_OVER.lines().get(2).text(state).contains(String.valueOf(state.score())),
                "game-over result score line should include score");
        require(ResultMenuContentSpec.LEVEL_CLEAR.lines().get(0).baselineOffset()
                        < ResultMenuContentSpec.LEVEL_CLEAR.lines().get(1).baselineOffset(),
                "level-clear result summary should sit below title");
        audio.shutdown();
    }

    private static void verifyTitleMenuContentRenderer() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        TitleMenuContentRenderer renderer = new TitleMenuContentRenderer();
        BufferedImage image = new BufferedImage(780, 420, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, state, 390, 36);
        graphics.dispose();
        audio.shutdown();
        require(countVisibleSamplePixels(image) > 50, "title menu content renderer should draw visible title text");
    }

    private static void verifyTitleMenuContentSpec() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        require(TitleMenuContentSpec.STANDARD.lines().size() == 4,
                "title menu content spec should keep title, subtitle, hint, and stats lines");
        require(TitleMenuContentSpec.STANDARD.lines().get(0).text(state).equals(MenuText.TITLE),
                "title menu first line should use title text");
        require(TitleMenuContentSpec.STANDARD.lines().get(1).text(state).equals(MenuText.SUBTITLE),
                "title menu second line should use subtitle text");
        require(TitleMenuContentSpec.STANDARD.lines().get(2).text(state).equals(MenuText.TITLE_HINT),
                "title menu third line should use hint text");
        require(TitleMenuContentSpec.STANDARD.lines().get(3).text(state).contains(MenuText.HIGH_SCORE),
                "title menu stats line should include high-score label");
        require(TitleMenuContentSpec.STANDARD.lines().get(0).baselineOffset()
                        < TitleMenuContentSpec.STANDARD.lines().get(1).baselineOffset(),
                "title menu subtitle should sit below title");
        audio.shutdown();
    }

    private static void verifySimpleMenuContentRenderer() {
        SimpleMenuContentRenderer renderer = new SimpleMenuContentRenderer();
        BufferedImage image = new BufferedImage(520, 260, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        MenuPanel panel = new MenuPanel(30, 24, 460, 180);
        renderer.paintPause(graphics, panel);
        renderer.paintSettings(graphics, panel);
        graphics.dispose();
        require(countVisibleSamplePixels(image) > 25, "simple menu content renderer should draw visible text");
    }

    private static void verifySimpleMenuContentSpec() {
        require(SimpleMenuContentSpec.PAUSE.lines().size() == 2,
                "pause simple menu content should keep title and hint lines");
        require(SimpleMenuContentSpec.SETTINGS.lines().size() == 2,
                "settings simple menu content should keep title and hint lines");
        require(SimpleMenuContentSpec.PAUSE.lines().get(0).text().equals(MenuText.PAUSED),
                "pause simple menu title should use paused text");
        require(SimpleMenuContentSpec.SETTINGS.lines().get(0).text().equals(MenuText.SETTINGS_TITLE),
                "settings simple menu title should use settings text");
        require(SimpleMenuContentSpec.PAUSE.lines().get(0).baselineOffset()
                        < SimpleMenuContentSpec.PAUSE.lines().get(1).baselineOffset(),
                "pause simple menu hint should sit below title");
        require(SimpleMenuContentSpec.SETTINGS.lines().get(0).baselineOffset()
                        < SimpleMenuContentSpec.SETTINGS.lines().get(1).baselineOffset(),
                "settings simple menu hint should sit below title");
    }

    private static void verifyMenuMetrics() {
        MenuPanel pause = MenuMetrics.pausePanel(1100, 780);
        require(pause.w() == MenuMetrics.PAUSE_W && pause.h() == MenuMetrics.PAUSE_H,
                "pause menu metrics should preserve configured size");
        require(pause.centerX() == 550, "menu panel center should align with viewport center");
        MenuButton resume = new MenuLayout().layout(GameMode.PAUSED, 1100, 780).get(0);
        require(resume.x() > pause.x() && resume.x() + resume.w() < pause.x() + pause.w(),
                "menu buttons should remain inside the shared panel metrics");
        verifyButtonsInsidePanel(new MenuLayout().layout(GameMode.HELP, 1100, 780),
                MenuMetrics.helpPanel(1100, 780));
        verifyButtonsInsidePanel(new MenuLayout().layout(GameMode.SETTINGS, 1100, 780),
                MenuMetrics.settingsPanel(1100, 780));
        verifyButtonsInsidePanel(new MenuLayout().layout(GameMode.LEVEL_CLEAR, 1100, 780),
                MenuMetrics.levelClearPanel(1100, 780));
        verifyButtonsInsidePanel(new MenuLayout().layout(GameMode.GAME_OVER, 1100, 780),
                MenuMetrics.gameOverPanel(1100, 780));
    }

    private static void verifyButtonsInsidePanel(List<MenuButton> buttons, MenuPanel panel) {
        for (MenuButton button : buttons) {
            require(button.x() >= panel.x() && button.x() + button.w() <= panel.x() + panel.w(),
                    "menu button should fit inside panel width");
            require(button.y() >= panel.y() && button.y() + button.h() <= panel.y() + panel.h(),
                    "menu button should fit inside panel height");
        }
    }

    private static void verifyMenuLabels() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        require(MenuLabels.forAction(state, MenuActions.START).contains("开始游戏"),
                "start label should describe starting from title");
        state.setMode(GameMode.GAME_OVER);
        require(MenuLabels.forAction(state, MenuActions.START).contains("重新开始"),
                "start label should describe restart from game over");
        state.setMode(GameMode.HELP);
        require(MenuLabels.forAction(state, MenuActions.TITLE).contains("Esc"),
                "help title label should include Esc hint");
        saveData.setSoundEnabled(false);
        require(MenuLabels.forAction(state, MenuActions.SOUND).contains("关闭"),
                "sound label should reflect disabled state");
        audio.shutdown();
    }

    private static void verifyMenuText() {
        require(MenuText.TITLE.contains("星轨"), "menu title text should remain readable Chinese");
        require(MenuText.HELP_LINES.length == 6, "menu help should keep expected line count");
        require(MenuText.levelClearSummary(2, 300).contains("第 2 层"), "level-clear summary should include level");
    }

    private static Set<String> actions(List<MenuButton> buttons) {
        Set<String> result = new HashSet<>();
        for (MenuButton button : buttons) {
            result.add(button.action());
        }
        return result;
    }

    private static void verifyBoardMetricsCalculator() {
        Level level = createPathfinderFixture();
        BoardMetricsCalculator calculator = new BoardMetricsCalculator();
        BoardMetrics metrics = calculator.compute(level, 120, 100);
        require(metrics.tileSize() >= VisualConfig.TILE_MIN_SIZE,
                "board metrics calculator should keep minimum tile size");
        require(metrics.width() == level.width() * metrics.tileSize(),
                "board metrics calculator should scale width from level size");
        require(metrics.height() == level.height() * metrics.tileSize(),
                "board metrics calculator should scale height from level size");
        BoardMetrics largeMetrics = calculator.compute(level, 2_000, 1_600);
        require(largeMetrics.tileSize() <= VisualConfig.TILE_MAX_SIZE,
                "board metrics calculator should keep maximum tile size");
        require(largeMetrics.x() == (2_000 - largeMetrics.width()) / 2,
                "board metrics calculator should center the board horizontally");
    }

    private static void verifyBoardFrameRenderer() {
        BoardFrameRenderer renderer = new BoardFrameRenderer();
        BufferedImage image = new BufferedImage(260, 180, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, new BoardMetrics(50, 42, 120, 96, 24));
        graphics.dispose();
        require(countNonTransparentPixels(image) > 10_000, "board frame renderer should draw visible frame chrome");
    }

    private static void verifyBoardTileRenderer() {
        BoardTileRenderer renderer = new BoardTileRenderer();
        BufferedImage image = new BufferedImage(180, 80, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, 0, 0, TileType.FLOOR, 10, 10, 32, 0);
        renderer.paint(graphics, 1, 0, TileType.WALL, 10, 10, 32, 0);
        renderer.paint(graphics, 2, 0, TileType.EXIT, 10, 10, 32, 0);
        renderer.paint(graphics, 3, 0, TileType.RIFT, 10, 10, 32, 17);
        graphics.dispose();
        require(countNonTransparentPixels(image) > 500, "board tile renderer should draw floor, wall, exit, and rift tiles");
    }

    private static void verifyBoardStaticCacheRenderer() {
        Level level = createRiftBoardFixture();
        BoardMetrics metrics = new BoardMetrics(0, 0, 96, 96, 32);
        BoardStaticCacheRenderer renderer = new BoardStaticCacheRenderer();
        BufferedImage cache = renderer.build(level, metrics);
        require(cache.getWidth() == metrics.width(), "board static cache renderer should match metrics width");
        require(cache.getHeight() == metrics.height(), "board static cache renderer should match metrics height");
        require(countNonTransparentPixels(cache) > 1_000,
                "board static cache renderer should draw static floor and wall tiles");
    }

    private static void verifyBoardRiftRenderer() {
        Level level = createRiftBoardFixture();
        BoardMetrics metrics = new BoardMetrics(10, 10, 96, 96, 32);
        BoardRiftRenderer renderer = new BoardRiftRenderer();
        BufferedImage image = new BufferedImage(140, 140, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, level, metrics, 21);
        graphics.dispose();
        require(countNonTransparentPixels(image) > 40, "board rift renderer should draw visible dynamic rift tiles");
    }

    private static void verifyBoardRendererBoundaries() {
        Level level = createPathfinderFixture();
        BoardRenderer renderer = new BoardRenderer();
        BoardMetrics metrics = renderer.computeMetrics(level, 120, 100);
        require(metrics.tileSize() >= VisualConfig.TILE_MIN_SIZE, "board metrics should keep minimum tile size");
        require(metrics.width() > 0 && metrics.height() > 0, "board metrics should produce positive cache dimensions");
        require(metrics.centerX(new Position(1, 1)) == metrics.tileX(1) + metrics.tileSize() / 2,
                "board metrics should convert tile positions into stable screen centers");
        BufferedImage cache = renderer.buildStaticCache(level, metrics);
        require(cache.getWidth() == metrics.width(), "board cache width should match metrics");
        require(countNonTransparentPixels(cache) > 200, "board static cache should draw floor and wall tiles");

        Level riftLevel = createRiftBoardFixture();
        BoardMetrics riftMetrics = renderer.computeMetrics(riftLevel, 260, 180);
        BufferedImage image = new BufferedImage(260, 180, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        renderer.paintFrame(graphics, riftMetrics);
        renderer.paintRifts(graphics, riftLevel, riftMetrics, 31);
        graphics.dispose();
        require(countNonTransparentPixels(image) > 1_000, "board renderer should draw frame and animated rift tiles");
    }

    private static void verifyBoardLayerRendererCache() {
        Level level = createRiftBoardFixture();
        BoardLayerRenderer renderer = new BoardLayerRenderer();
        BufferedImage image = new BufferedImage(320, 220, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        BoardMetrics metrics = renderer.paintBaseLayer(graphics, level, 320, 220);
        require(renderer.cacheBuilds() == 1, "board layer should build a static cache on first paint");
        renderer.paintDynamicTiles(graphics, level, metrics, 12);
        renderer.paintBaseLayer(graphics, level, 320, 220);
        require(renderer.cacheBuilds() == 1, "board layer should reuse the static cache for unchanged metrics");
        renderer.paintBaseLayer(graphics, level, 360, 220);
        require(renderer.cacheBuilds() == 2, "board layer should rebuild the static cache when metrics change");
        graphics.dispose();
        require(countNonTransparentPixels(image) > 1_000, "board layer should draw a visible board layer");
    }

    private static Level createRiftBoardFixture() {
        TileType[][] tiles = {
                {TileType.WALL, TileType.WALL, TileType.WALL},
                {TileType.WALL, TileType.RIFT, TileType.WALL},
                {TileType.WALL, TileType.WALL, TileType.WALL}
        };
        return new Level(3, 3, tiles, new Position(1, 1), new Position(1, 1), collectFloors(tiles));
    }

    private static void verifyBackgroundRenderer() {
        BackgroundRenderer renderer = new BackgroundRenderer();
        BufferedImage image = new BufferedImage(420, 260, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, 420, 260, 37);
        graphics.dispose();
        require(countVisibleSamplePixels(image) > 400, "background renderer should draw gradient, stars, and grid");
    }

    private static void verifyStarField() {
        StarField field = new StarField(32);
        BufferedImage image = new BufferedImage(160, 90, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        field.paint(graphics, 160, 90, 45);
        field.paint(graphics, 0, -5, 45);
        graphics.dispose();
        require(countNonTransparentPixels(image) > 20, "star field should draw stars and tolerate unsafe sizes");
    }

    private static void verifyPickupRenderer() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        state.startNewGame();
        PickupRenderer renderer = new PickupRenderer();
        BoardMetrics metrics = new BoardMetrics(20, 20, 360, 240, 24);
        BufferedImage image = new BufferedImage(420, 300, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, state, metrics);
        graphics.dispose();
        audio.shutdown();
        require(countNonTransparentPixels(image) > 200, "pickup renderer should draw visible crystals and cells");
    }

    private static void verifyPickupVisualStyle() {
        require(PickupVisualStyle.CRYSTAL.bodyColor().equals(VisualConfig.GOLD),
                "crystal pickup style should use gold");
        require(PickupVisualStyle.PHASE_CELL.bodyColor().equals(VisualConfig.CYAN),
                "phase-cell pickup style should use cyan");
        require(PickupVisualStyle.REWIND_CELL.bodyColor().equals(VisualConfig.PINK),
                "rewind-cell pickup style should use pink");
        require(PickupVisualStyle.CRYSTAL.shape() == PickupShape.DIAMOND,
                "crystal pickup style should use diamond geometry");
        require(PickupVisualStyle.PHASE_CELL.shape() == PickupShape.ROUNDED_CELL,
                "phase-cell pickup style should use rounded-cell geometry");
        require(PickupVisualStyle.REWIND_CELL.shape() == PickupShape.CROSS_RING,
                "rewind-cell pickup style should use cross-ring geometry");
        require(PickupVisualStyle.CRYSTAL.glowColor().getAlpha() > 0,
                "pickup style glow should remain visible");
    }

    private static void verifyExitRenderer() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        state.startNewGame();
        BoardRenderer boardRenderer = new BoardRenderer();
        BoardMetrics metrics = boardRenderer.computeMetrics(state.level(), VisualConfig.WINDOW_DEFAULT_WIDTH,
                VisualConfig.WINDOW_DEFAULT_HEIGHT);
        ExitRenderer renderer = new ExitRenderer();
        BufferedImage image = createPreviewImage();
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, state, metrics);
        setIntField(state, "crystalsCollected", state.crystalsNeeded());
        renderer.paint(graphics, state, metrics);
        graphics.dispose();
        audio.shutdown();
        require(countNonTransparentPixels(image) > 200, "exit renderer should draw locked and open exit states");
    }

    private static void verifyExitVisualStyle() {
        ExitVisualStyle locked = ExitVisualStyle.forOpenState(false);
        ExitVisualStyle open = ExitVisualStyle.forOpenState(true);
        require(locked.lockVisible(), "locked exit style should show lock mark");
        require(!open.lockVisible(), "open exit style should hide lock mark");
        require(open.ringColor().equals(VisualConfig.MINT), "open exit style should use mint ring color");
        require(!locked.ringColor().equals(open.ringColor()), "locked exit style should use distinct ring color");
        require(locked.fillColor().getAlpha() == locked.fillAlpha(), "locked exit fill should apply alpha");
        require(open.fillColor().getAlpha() == open.fillAlpha(), "open exit fill should apply alpha");
    }

    private static void verifyPlayerRenderer() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        state.startNewGame();
        BoardRenderer boardRenderer = new BoardRenderer();
        BoardMetrics metrics = boardRenderer.computeMetrics(state.level(), VisualConfig.WINDOW_DEFAULT_WIDTH,
                VisualConfig.WINDOW_DEFAULT_HEIGHT);
        PlayerRenderer renderer = new PlayerRenderer();
        BufferedImage image = createPreviewImage();
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, state, metrics);
        state.activatePhase();
        renderer.paint(graphics, state, metrics);
        graphics.dispose();
        audio.shutdown();
        require(countNonTransparentPixels(image) > 120, "player renderer should draw normal and phase player states");
    }

    private static void verifyPlayerVisualStyle() {
        PlayerVisualStyle normal = PlayerVisualStyle.forPhaseState(false);
        PlayerVisualStyle phase = PlayerVisualStyle.forPhaseState(true);
        require(normal.bodyColor().equals(VisualConfig.MINT), "normal player style should use mint body color");
        require(phase.bodyColor().equals(VisualConfig.CYAN), "phase player style should use cyan body color");
        require(normal.glowAlpha() > 0 && phase.glowAlpha() > 0, "player style glow alpha should stay visible");
        require(normal.glowColor().getAlpha() == normal.glowAlpha(), "normal player glow should apply alpha");
        require(phase.glowColor().getAlpha() == phase.glowAlpha(), "phase player glow should apply alpha");
    }

    private static void verifyEnemyRenderer() {
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy(new Position(1, 1), Direction.UP));
        enemies.add(new Enemy(new Position(3, 1), Direction.DOWN));
        enemies.add(new Enemy(new Position(1, 3), Direction.LEFT));
        Enemy stunned = new Enemy(new Position(3, 3), Direction.RIGHT);
        stunned.stun(3);
        enemies.add(stunned);
        EnemyRenderer renderer = new EnemyRenderer();
        BoardMetrics metrics = new BoardMetrics(20, 20, 160, 160, 32);
        BufferedImage image = new BufferedImage(220, 220, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, enemies, metrics);
        graphics.dispose();
        require(countNonTransparentPixels(image) > 300, "enemy renderer should draw all directions and stunned enemies");
    }

    private static void verifyEnemyVisualStyle() {
        EnemyVisualStyle patrol = EnemyVisualStyle.forStunState(false);
        EnemyVisualStyle stunned = EnemyVisualStyle.forStunState(true);
        require(patrol.bodyColor().equals(VisualConfig.PINK), "patrolling enemy style should use pink body color");
        require(!stunned.bodyColor().equals(patrol.bodyColor()), "stunned enemy style should change body color");
        require(patrol.glowAlpha() == stunned.glowAlpha(), "enemy glow alpha should stay consistent across states");
        require(patrol.glowColor().getAlpha() == patrol.glowAlpha(), "patrolling enemy glow should apply alpha");
        require(stunned.glowColor().getAlpha() == stunned.glowAlpha(), "stunned enemy glow should apply alpha");
    }

    private static void verifyEntityRendererFacade() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        state.startNewGame();
        EntityRenderer renderer = new EntityRenderer();
        BoardRenderer boardRenderer = new BoardRenderer();
        BoardMetrics metrics = boardRenderer.computeMetrics(state.level(), VisualConfig.WINDOW_DEFAULT_WIDTH,
                VisualConfig.WINDOW_DEFAULT_HEIGHT);
        BufferedImage image = createPreviewImage();
        Graphics2D graphics = image.createGraphics();
        renderer.paintPickups(graphics, state, metrics);
        renderer.paintExit(graphics, state, metrics);
        renderer.paintEnemies(graphics, state.enemies(), metrics);
        renderer.paintPlayer(graphics, state, metrics);
        graphics.dispose();
        require(countVisibleSamplePixels(image) > 20, "entity renderer facade should paint visible entities");
        audio.shutdown();
    }

    private static void verifyEntityRenderOrder() {
        require(EntityRenderOrder.gameplayOrder().equals(List.of(
                EntityRenderOrder.PICKUPS,
                EntityRenderOrder.EXIT,
                EntityRenderOrder.ENEMIES,
                EntityRenderOrder.PLAYER)),
                "entity render order should keep pickups, exit, enemies, then player");
    }

    private static void verifyEntityLayerRenderer() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        state.startNewGame();
        EntityLayerRenderer renderer = new EntityLayerRenderer();
        BoardRenderer boardRenderer = new BoardRenderer();
        BoardMetrics metrics = boardRenderer.computeMetrics(state.level(), VisualConfig.WINDOW_DEFAULT_WIDTH,
                VisualConfig.WINDOW_DEFAULT_HEIGHT);
        BufferedImage image = createPreviewImage();
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, state, metrics);
        graphics.dispose();
        audio.shutdown();
        require(countVisibleSamplePixels(image) > 20, "entity layer should paint a visible ordered entity pass");
    }

    private static void verifyGameSceneRendererCache() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        state.startNewGame();
        GameSceneRenderer renderer = new GameSceneRenderer();
        BufferedImage image = createPreviewImage();
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, state, VisualConfig.WINDOW_DEFAULT_WIDTH, VisualConfig.WINDOW_DEFAULT_HEIGHT);
        require(renderer.boardCacheBuilds() == 1, "scene renderer should build the board cache on first paint");
        renderer.paint(graphics, state, VisualConfig.WINDOW_DEFAULT_WIDTH, VisualConfig.WINDOW_DEFAULT_HEIGHT);
        require(renderer.boardCacheBuilds() == 1, "scene renderer should reuse the board cache for unchanged metrics");
        renderer.paint(graphics, state, VisualConfig.WINDOW_DEFAULT_WIDTH - 120, VisualConfig.WINDOW_DEFAULT_HEIGHT);
        require(renderer.boardCacheBuilds() == 2, "scene renderer should rebuild board cache when metrics change");
        graphics.dispose();
        audio.shutdown();
    }

    private static void verifyGameplayLayerRenderer() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        state.startNewGame();
        GameplayLayerRenderer renderer = new GameplayLayerRenderer();
        EffectLayer effects = new EffectLayer();
        effects.advance(List.of(new VisualEffectEvent(VisualEffectType.PHASE_BURST, state.player(), 0)), 0);
        BufferedImage image = createPreviewImage();
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, state, effects, VisualConfig.WINDOW_DEFAULT_WIDTH,
                VisualConfig.WINDOW_DEFAULT_HEIGHT, 0);
        renderer.paint(graphics, state, effects, VisualConfig.WINDOW_DEFAULT_WIDTH,
                VisualConfig.WINDOW_DEFAULT_HEIGHT, 1);
        graphics.dispose();
        audio.shutdown();
        require(renderer.boardCacheBuilds() == 1, "gameplay layer should reuse its board cache across paints");
        require(countVisibleSamplePixels(image) > 200, "gameplay layer should draw HUD, scene, effects, and footer");
    }

    private static void verifyGameViewRenderer() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        state.startNewGame();
        GameViewRenderer renderer = new GameViewRenderer();
        EffectLayer effects = new EffectLayer();
        MenuButtonCache buttons = new MenuButtonCache();
        buttons.update(state.mode(), VisualConfig.WINDOW_DEFAULT_WIDTH, VisualConfig.WINDOW_DEFAULT_HEIGHT);
        BufferedImage image = createPreviewImage();
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, state, effects, buttons, VisualConfig.WINDOW_DEFAULT_WIDTH,
                VisualConfig.WINDOW_DEFAULT_HEIGHT, 0);
        renderer.paint(graphics, state, effects, buttons, VisualConfig.WINDOW_DEFAULT_WIDTH,
                VisualConfig.WINDOW_DEFAULT_HEIGHT, 1);
        graphics.dispose();
        require(renderer.boardCacheBuilds() == 1, "view renderer should reuse its scene cache across paints");
        require(countVisibleSamplePixels(image) > 200, "view renderer should produce a nonblank game frame");
        audio.shutdown();
    }

    private static void verifyGamePanelRenderer() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        state.startNewGame();
        GamePanelRenderer renderer = new GamePanelRenderer();
        EffectLayer effects = new EffectLayer();
        MenuButtonCache buttons = new MenuButtonCache();
        buttons.update(state.mode(), VisualConfig.WINDOW_DEFAULT_WIDTH, VisualConfig.WINDOW_DEFAULT_HEIGHT);
        BufferedImage image = createPreviewImage();
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, state, effects, buttons, VisualConfig.WINDOW_DEFAULT_WIDTH,
                VisualConfig.WINDOW_DEFAULT_HEIGHT, 0);
        renderer.paint(graphics, state, effects, buttons, VisualConfig.WINDOW_DEFAULT_WIDTH,
                VisualConfig.WINDOW_DEFAULT_HEIGHT, 1);
        graphics.dispose();
        audio.shutdown();
        require(renderer.boardCacheBuilds() == 1, "panel renderer should reuse its view cache across paints");
        require(countVisibleSamplePixels(image) > 200, "panel renderer should produce a nonblank frame");
    }

    private static void verifyFooterRenderer() {
        FooterRenderer renderer = new FooterRenderer();
        BufferedImage image = new BufferedImage(720, 120, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        renderer.paint(graphics, 720, 120);
        graphics.dispose();
        require(countNonTransparentPixels(image) > 120, "footer renderer should draw visible keyboard hint text");
    }

    private static void verifyRenderClock() {
        RenderClock clock = new RenderClock();
        require(clock.frame() == 0, "render clock should start at frame zero");
        clock.advance();
        require(!clock.shouldUpdateLogic(), "render clock should skip logic on first render frame");
        clock.advance();
        require(clock.shouldUpdateLogic(), "render clock should update logic on configured divisor");
    }

    private static void verifyGameLoopController() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        state.startNewGame();
        EffectLayer effects = new EffectLayer();
        MenuButtonCache buttons = new MenuButtonCache();
        GameLoopController controller = new GameLoopController(state, effects, buttons);
        int beforeTick = state.tick();
        controller.advanceFrame(VisualConfig.WINDOW_DEFAULT_WIDTH, VisualConfig.WINDOW_DEFAULT_HEIGHT);
        require(controller.frame() == 1, "game loop controller should advance render frames");
        controller.advanceFrame(VisualConfig.WINDOW_DEFAULT_WIDTH, VisualConfig.WINDOW_DEFAULT_HEIGHT);
        require(state.tick() > beforeTick, "game loop controller should update logic on configured cadence");
        state.setMode(GameMode.PAUSED);
        controller.advanceFrame(VisualConfig.WINDOW_DEFAULT_WIDTH, VisualConfig.WINDOW_DEFAULT_HEIGHT);
        require(!buttons.buttons().isEmpty(), "game loop controller should keep menu button layout current");
        audio.shutdown();
    }

    private static void verifyGamePanelTimer() {
        GamePanelTimer timer = new GamePanelTimer(() -> {
        });
        require(!timer.isRunning(), "panel timer should start stopped");
        timer.start();
        require(timer.isRunning(), "panel timer should report running after start");
        timer.stop();
        require(!timer.isRunning(), "panel timer should report stopped after stop");
    }

    private static void verifyRenderQuality() {
        BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        RenderQuality.apply(graphics);
        require(RenderingHints.VALUE_ANTIALIAS_ON.equals(
                graphics.getRenderingHint(RenderingHints.KEY_ANTIALIASING)),
                "render quality should enable shape antialiasing");
        require(RenderingHints.VALUE_TEXT_ANTIALIAS_ON.equals(
                graphics.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING)),
                "render quality should enable text antialiasing");
        graphics.dispose();
    }

    private static void verifyOffscreenRendering() {
        SaveData saveData = SaveData.loadFrom(tempSavePath());
        SoundEngine audio = new SoundEngine(false);
        GameState state = new GameState(saveData, audio);
        state.startNewGame();
        GamePanel panel = new GamePanel(state);
        require(panel.isLoopRunning(), "game panel loop should start running after construction");
        require(panel.isFocusable(), "game panel should remain focusable for keyboard play");
        require(panel.isDoubleBuffered(), "game panel should keep Swing double buffering enabled");
        require(boundAction(panel, "pressed W") != null, "game panel should expose movement key bindings");
        require(boundAction(panel, "pressed ENTER") != null, "game panel should expose command key bindings");
        require(panel.getMouseListeners().length > 0, "game panel should install menu mouse listener");
        require(panel.getMouseMotionListeners().length > 0, "game panel should install menu mouse motion listener");
        panel.setSize(VisualConfig.WINDOW_DEFAULT_WIDTH, VisualConfig.WINDOW_DEFAULT_HEIGHT);
        BufferedImage image = createPreviewImage();
        Graphics2D graphics = image.createGraphics();
        panel.paint(graphics);
        graphics.dispose();
        panel.stopLoop();
        require(!panel.isLoopRunning(), "game panel loop should stop after explicit shutdown");
        audio.shutdown();

        int visiblePixels = countVisibleSamplePixels(image);
        require(visiblePixels > 200, "offscreen rendering should produce a nonblank game frame");
    }

    private static BufferedImage createPreviewImage() {
        return new BufferedImage(VisualConfig.WINDOW_DEFAULT_WIDTH, VisualConfig.WINDOW_DEFAULT_HEIGHT,
                BufferedImage.TYPE_INT_ARGB);
    }

    private static int countVisibleSamplePixels(BufferedImage image) {
        int visiblePixels = 0;
        for (int y = 0; y < image.getHeight(); y += 8) {
            for (int x = 0; x < image.getWidth(); x += 8) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;
                if (r + g + b > 80) {
                    visiblePixels++;
                }
            }
        }
        return visiblePixels;
    }

    private static int countNonTransparentPixels(BufferedImage image) {
        int visiblePixels = 0;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (((image.getRGB(x, y) >> 24) & 0xff) > 0) {
                    visiblePixels++;
                }
            }
        }
        return visiblePixels;
    }

    private static Path tempSavePath() {
        try {
            return Files.createTempDirectory("starmaze-save-test").resolve("save.properties");
        } catch (Exception ex) {
            throw new IllegalStateException("temporary save path should be available", ex);
        }
    }

    private static final class GameStateFixture {
        private final GameState state;

        private GameStateFixture(GameState state) {
            this.state = state;
        }

        private void installLevel(Level level, Position player) {
            setField(state, "level", level);
            setField(state, "player", player);
            state.setMode(GameMode.PLAYING);
        }

        private void clearPickupsAndEnemies() {
            crystals().clear();
            phaseCells().clear();
            rewindCells().clear();
            enemies().clear();
        }

        @SuppressWarnings("unchecked")
        private Set<Position> crystals() {
            return (Set<Position>) getField(state, "crystals");
        }

        @SuppressWarnings("unchecked")
        private Set<Position> phaseCells() {
            return (Set<Position>) getField(state, "phaseCells");
        }

        @SuppressWarnings("unchecked")
        private Set<Position> rewindCells() {
            return (Set<Position>) getField(state, "rewindCells");
        }

        @SuppressWarnings("unchecked")
        private List<Enemy> enemies() {
            return (List<Enemy>) getField(state, "enemies");
        }

        private RewindHistory rewindHistory() {
            return (RewindHistory) getField(state, "rewindHistory");
        }

        private void setInt(String fieldName, int value) {
            setIntField(state, fieldName, value);
        }
    }

    private static void setIntField(Object target, String fieldName, int value) {
        try {
            java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.setInt(target, value);
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException("test field should be adjustable: " + fieldName, ex);
        }
    }

    private static void setField(Object target, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException("test field should be adjustable: " + fieldName, ex);
        }
    }

    private static Object getField(Object target, String fieldName) {
        try {
            java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(target);
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException("test field should be readable: " + fieldName, ex);
        }
    }

    private static void verifySoundEngineBoundaries() {
        SoundEngine disabled = new SoundEngine(false);
        require(!disabled.isClosed(), "disabled sound engine should start open");
        for (SoundEffect effect : SoundEffect.values()) {
            disabled.play(effect);
        }
        disabled.shutdown();
        require(disabled.isClosed(), "sound engine should report closed after shutdown");
        disabled.play(SoundEffect.MENU);

        SoundEngine enabled = new SoundEngine(true);
        require(enabled.isEnabled(), "enabled sound engine should report enabled state");
        require(enabled.isAudioAvailable(), "sound engine should start audio-available");
        for (int i = 0; i < 40; i++) {
            enabled.play(SoundEffect.MOVE);
        }
        enabled.setEnabled(false);
        require(!enabled.isEnabled(), "sound engine should report disabled state after toggle");
        enabled.play(SoundEffect.WIN);
        enabled.shutdown();
        require(enabled.isClosed(), "enabled sound engine should close cleanly");
        enabled.play(SoundEffect.LOSE);
    }

    private static boolean disjoint(Set<Position> first, Set<Position> second) {
        for (Position position : first) {
            if (second.contains(position)) {
                return false;
            }
        }
        return true;
    }

    private static int distance(Position a, Position b) {
        return a.manhattanDistance(b);
    }

    private static void verifyEnemyController() {
        EnemyController controller = new EnemyController();
        Level fixture = createOccupiedPathfinderFixture();
        Enemy enemy = new Enemy(new Position(1, 1), Direction.DOWN);
        Direction chase = controller.chooseDirection(fixture, 1, new Position(3, 1), false, enemy, Set.of(), new Random(7L));
        require(chase == Direction.RIGHT, "enemy controller should chase the player inside alert range");

        Direction phasePatrol = controller.chooseDirection(fixture, 1, new Position(3, 1), true, enemy, Set.of(),
                new Random(7L) {
                    @Override
                    public double nextDouble() {
                        return 0.1;
                    }
                });
        require(phasePatrol == Direction.DOWN, "enemy controller should patrol instead of chasing while phase is active");

        Enemy stunned = new Enemy(new Position(1, 1), Direction.RIGHT);
        stunned.stun(2);
        Set<Position> stunnedOccupied = new HashSet<>(Set.of(stunned.position()));
        controller.advanceEnemy(fixture, 1, new Position(3, 1), false, stunned, stunnedOccupied, new Random(3L));
        require(stunned.position().equals(new Position(1, 1)), "stunned enemy should not move during cooldown");
        require(stunned.stunnedTicks() == 1, "stunned enemy should cool down when advanced");

        Enemy mover = new Enemy(new Position(1, 1), Direction.RIGHT);
        Set<Position> occupied = new HashSet<>(Set.of(mover.position()));
        controller.advanceEnemy(fixture, 1, new Position(3, 1), false, mover, occupied, new Random(5L));
        require(mover.position().equals(new Position(2, 1)), "enemy advance should move along the chosen direction");
        require(occupied.contains(mover.position()), "enemy advance should refresh occupied positions");

        Enemy distant = new Enemy(new Position(1, 1), Direction.DOWN);
        Direction patrol = controller.chooseDirection(fixture, 1, new Position(20, 20), false, distant, Set.of(),
                new Random(11L) {
                    @Override
                    public double nextDouble() {
                        return 0.1;
                    }
                });
        require(patrol == Direction.DOWN, "enemy controller should keep patrol direction outside alert range");

        Enemy trapped = new Enemy(new Position(2, 1), Direction.RIGHT);
        Set<Position> blocked = Set.of(new Position(1, 1), new Position(3, 1), new Position(2, 2));
        Direction fallback = controller.chooseDirection(fixture, 1, new Position(20, 20), false, trapped, blocked,
                new Random(17L));
        require(fallback == Direction.LEFT, "enemy controller should reverse direction when no moves are available");
    }

    private static boolean isReachable(Level level, Position from, Position to) {
        Queue<Position> queue = new ArrayDeque<>();
        Set<Position> seen = new HashSet<>();
        queue.add(from);
        seen.add(from);
        while (!queue.isEmpty()) {
            Position current = queue.remove();
            if (current.equals(to)) {
                return true;
            }
            for (Direction direction : Direction.CARDINALS) {
                Position next = current.translate(direction);
                if (level.contains(next) && level.isWalkableForEnemy(next) && seen.add(next)) {
                    queue.add(next);
                }
            }
        }
        return false;
    }
}

# Changelog

## 2026-06-09 - Optimization Loop 1

- Added `GameConfig` to centralize gameplay constants such as phase cost, rewind snapshot cadence, enemy alert range, scoring, resource spawning, and level generation limits.
- Added `PathFinder` and moved enemy chase routing from local BFS inside `GameState` to A* search with Manhattan heuristic, reducing node expansion pressure on larger generated maps.
- Kept BFS only where it still matches the job: nearest safe tile recovery in `Level` and farthest-exit discovery in `LevelGenerator`.
- Added `VisualEffectEvent` / `VisualEffectType` as a model-to-view event boundary, so gameplay logic emits effects without drawing them directly.
- Added `VisualConfig` and switched the Swing panel to a 16 ms repaint timer while preserving the existing logic pacing through a frame divisor.
- Added lightweight neon effects for phase burst, rift warp, rewind wave, enemy stun, and screen shake using bounded effect lifetimes.
- Improved `SoundEngine` with precomputed tone buffers, a bounded audio task queue, and short duplicate-trigger suppression to avoid audio backlog during bursty gameplay.
- Extended `StarMazeSmokeTest` to verify the A* pathfinder can find a first step from an enemy toward the player.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 2

- Added a cached static board layer in `GamePanel` using `BufferedImage`, so wall/floor rendering is rebuilt only when the level or board metrics change.
- Kept rift tiles out of the static cache and rendered them dynamically each frame, preserving their pulse animation while reducing per-frame tile work.
- Introduced `BoardMetrics` to wrap board sizing and placement calculations instead of scattering layout arithmetic through `paintBoard`.
- Separated menu hit-area layout from button painting with `layoutMenuButtons`, reducing `paintComponent` side effects and making mouse hit testing independent from the last paint pass.
- Verified the cached renderer through the headless smoke test and offscreen Swing preview generation.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 3

- Extracted `MenuButton` and `BoardMetrics` from `GamePanel` into small UI-layer records, reducing internal type clutter in the main panel.
- Kept menu hit-area layout separated from button painting while preserving existing click actions and visual placement.
- Strengthened `StarMazeSmokeTest` with a deterministic 5x5 A* pathfinding fixture, proving the pathfinder chooses the expected first step in a fixed corridor.
- Re-ran the packaged jar smoke test after each structural change to keep the optimization loop regression-safe.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 4

- Added `UiFonts` to cache Swing font instances and reduce repeated `new Font(...)` calls during repaint.
- Extracted keyboard bindings into `InputController`, keeping movement, pause, help, sound, phase, rewind, and Enter/Esc behavior unchanged.
- Strengthened `StarMazeSmokeTest` with fixed-seed generated-level reachability checks across the first eight levels.
- Added deterministic A* occupied-cell coverage, verifying that the pathfinder routes around blocked cells.
- Added `StarField` so background star parameters are prepared once instead of rebuilt inside `paintBackground`.
- Added `UiColors.withAlpha` to centralize alpha color creation for effects and actors.
- Hardened `SoundEngine` with closed-state protection and rejected-task handling during shutdown.
- Added a smoke-test assertion that playing a sound after shutdown is safe.
- Added `MenuActions` constants to remove repeated menu action string literals from layout and click handling.
- Added a student-style homework report at `docs/作业报告.md` describing what changed and what still feels imperfect.

Validation after each sub-loop:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 5

- Extracted board rendering into `BoardRenderer`, including board metrics calculation, static tile cache creation, frame painting, and animated rift painting.
- Extracted background and footer drawing into `BackgroundRenderer` and `FooterRenderer`, leaving `GamePanel` focused on frame orchestration.
- Removed stale board coordinate fields and panel-local text helpers after renderer extraction.
- Added `MenuLayout` to separate menu hit-area calculation from mouse handling and rendering.
- Cached menu button layouts by mode and panel size, avoiding needless per-frame list allocation during normal repaint ticks.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 6

- Added `EnemyController` and moved enemy direction selection out of `GameState`, preserving A* chase, phase-safe patrol behavior, occupied-cell avoidance, and random patrol fallback.
- Kept `GameState` responsible for applying enemy movement and collision results, while the controller now owns the decision rule.
- Extended `StarMazeSmokeTest` with deterministic enemy-controller coverage for chase behavior and phase-mode patrol behavior.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 7

- Added `LevelPopulator` and `LevelPopulation` to move crystal, phase-cell, rewind-cell, and enemy spawn placement out of `GameState.buildLevel`.
- Kept `GameState` responsible for applying the generated population and resetting runtime state, while spawn rules now live in one focused model service.
- Extended `StarMazeSmokeTest` with deterministic population checks for crystal sufficiency, non-overlapping pickup sets, and enemy spawn distance rules.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 8

- Consolidated repeated menu colors and corner radii into named `MenuRenderer` constants, keeping the existing neon visual style while making future style tuning safer.
- Reused `UiColors.withAlpha` for overlay alpha handling instead of constructing throwaway alpha colors inline.
- Regenerated the offscreen Swing preview at `docs/game-preview.png` and verified it is nonblank with a pixel-count check.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 9

- Consolidated repeated HUD colors, radii, baselines, meter offsets, and rewind-dot sizing into named `HudRenderer` constants.
- Added a compact right-side HUD message fallback so narrow windows do not draw long status text over the resource area.
- Regenerated and pixel-checked the offscreen Swing preview after the HUD changes.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 10

- Added default and minimum window dimensions to `VisualConfig` instead of hardcoding them in `GameFrame`.
- Updated `RenderPreview` to reuse the same default window dimensions as the real game window, keeping preview generation aligned with release behavior.
- Rebuilt the release jar, ran the packaged smoke test, and regenerated `docs/game-preview.png`.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 11

- Updated `build.ps1` to regenerate `dist/run.bat` on every build, keeping the published jar folder self-contained after clean rebuilds.
- Preserved the existing `java -jar StarMaze.jar` launch behavior while making release packaging less dependent on a previously checked-in batch file.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 12

- Added `SaveData.loadFrom(Path)` so tests and future tooling can load a save file from an isolated location instead of always using the user home directory.
- Extended `StarMazeSmokeTest` with a temporary-file save round trip covering high score, games played, victories, and sound setting persistence.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 13

- Updated `tools/generate_project_doc.py` and regenerated `docs/项目说明.doc` / `docs/项目说明.docx` so the project explanation now matches the current A* pathfinding implementation.
- Added the extracted renderer modules, `EnemyController`, `PathFinder`, and `LevelPopulator` to the documented class/module structure.
- Verified the generated DOC/DOCX text structurally; visual DOCX render QA could not run because the local LibreOffice/soffice converter was unavailable.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 14

- Added `Position.manhattanDistance(Position)` and replaced duplicated Manhattan-distance formulas across pathfinding, level generation, population rules, enemy AI, game state, and smoke tests.
- Kept behavior unchanged while making distance-based gameplay rules easier to audit and tune.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 15

- Extended `StarMazeSmokeTest` with sound-engine boundary coverage for disabled playback, rapid repeated playback, enabled-state toggling, shutdown, and post-shutdown play calls.
- Confirmed the audio API remains non-throwing under burst and shutdown scenarios, even in headless environments.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 16

- Cached the shared Java Sound `AudioFormat` in `SoundEngine`, reducing repeated object creation inside individual playback tasks.
- Re-ran the expanded sound boundary smoke coverage after the implementation change.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 17

- Added an early return to `PathFinder.findStepToward` when the source and target positions are identical, avoiding unnecessary A* setup work.
- Extended `StarMazeSmokeTest` to lock down the same-position pathfinding boundary case.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 18

- Added smoke-test coverage for `Level.nearestWalkable` so phase-end recovery from an invalid wall cell remains protected.
- This strengthens the BFS-backed safety behavior used when phase walk ends inside a wall.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 19

- Added `EffectLayer` to own active visual-effect lifecycle management, including event ingestion, max-count trimming, and lifetime pruning.
- Simplified `GamePanel` so it now forwards drained model events to the effect layer and passes the current active effects to `EffectRenderer`.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 20

- Added smoke-test coverage for `EffectLayer`, verifying active-effect count capping and expired-effect pruning.
- This protects the visual event pipeline after extracting effect lifecycle management from `GamePanel`.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 21

- Added `MenuActionHandler` to move menu command dispatch out of `GamePanel`.
- Extended `StarMazeSmokeTest` with menu-action coverage for starting gameplay, opening help, and toggling sound settings.
- Reused a temporary save-path helper in smoke tests to keep persistence checks isolated from the real user profile.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 22

- Extended `StarMazeSmokeTest` with `BoardRenderer` boundary coverage for very small panel sizes.
- Verified computed board metrics preserve positive cache dimensions and minimum tile sizing before building the static board cache.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 23

- Updated `MenuRenderer` so visual button drawing now uses the same `MenuButton` list generated by `MenuLayout`, eliminating duplicated button coordinates between rendering and mouse hit testing.
- Restored menu text in `MenuRenderer` to readable UTF-8 Chinese while preserving the existing screen structure.
- Regenerated and pixel-checked the offscreen Swing preview after the menu rendering change.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 24

- Extended `StarMazeSmokeTest` with `MenuLayout` coverage for every `GameMode`, verifying that each menu exposes the expected action set.
- This protects the new shared layout/rendering button pipeline from silently losing menu commands.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 25

- Added `MenuLabels` to separate menu action-to-label mapping from `MenuRenderer`.
- Extended `StarMazeSmokeTest` with dynamic menu-label coverage for title start, game-over restart, help Esc hint, and sound enabled-state text.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 26

- Added `GameSceneRenderer` to own board cache management, board layer painting, rift animation, and dynamic entity rendering.
- Simplified `GamePanel` so it now delegates scene painting and only forwards the returned `BoardMetrics` to `EffectRenderer`.
- Regenerated and pixel-checked the offscreen Swing preview after the scene-renderer extraction.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 27

- Added `MenuButtonCache` to own cached menu-button layouts and hit testing, removing menu layout cache fields from `GamePanel`.
- Extended `StarMazeSmokeTest` with cache reuse, hit detection, and playing-mode clear checks.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 28

- Split `GamePanel.paintComponent` into explicit gameplay-scene and menu-overlay helpers, making the panel's paint order easier to audit.
- Kept the rendering sequence unchanged: background first, shaken gameplay scene/HUD/effects/footer second, menu overlay last.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 29

- Added `RewindHistory` and `RewindSnapshot` to move rewind snapshot storage, capacity trimming, and lookback selection out of `GameState`.
- Kept `GameState.rewind()` responsible for applying the selected snapshot, stunning enemies, scoring, and visual/audio feedback.
- Extended `StarMazeSmokeTest` with rewind-history coverage for snapshot cap, configured lookback selection, enemy-position capture, and clearing.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 30

- Removed the now-unused `GameState.distance` helper after Manhattan-distance logic was centralized on `Position`.
- Confirmed the expanded smoke suite still passes after the model cleanup.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 31

- Tidied `StarMazeSmokeTest` imports after the smoke suite expanded across model, UI, persistence, audio, and rendering helpers.
- Re-ran the packaged smoke test to keep the test harness cleanup regression-safe.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 32

- Rewrote `EntityRenderer` as clean UTF-8 source while preserving entity rendering behavior.
- Fixed the locked-exit glyph so the UI now displays the readable Chinese character `锁` instead of mojibake.
- Rebuilt, ran the packaged smoke test, regenerated the offscreen preview, and pixel-checked the result.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 33

- Rewrote `HudRenderer` as clean UTF-8 source and restored readable Chinese HUD labels for title, level, score, high score, crystals, phase, rewind, and compact help text.
- Replaced the mojibake README with a readable Chinese project summary, build instructions, and controls list.
- Rebuilt, ran the packaged smoke test, regenerated the offscreen preview, and pixel-checked the result.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 34

- Added a small `SearchGrid` record inside `PathFinder` to wrap A* closed-set, best-cost, and first-step arrays.
- Kept A* behavior unchanged while making `findStepToward` easier to read and audit.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 35

- Updated `tools/generate_project_doc.py` and regenerated `docs/项目说明.doc` / `docs/项目说明.docx` so the architecture section now includes `GameSceneRenderer`, `MenuButtonCache`, and `RewindHistory`.
- Structurally verified the regenerated DOCX contains the new architecture names and A* description.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 36

- Added `VisualEventQueue` to own model-to-view visual effect event emission, draining, and clearing.
- Simplified `GameState` so it delegates visual event list management to the queue.
- Extended `StarMazeSmokeTest` with queue coverage for emit/drain, tick preservation, post-drain emptiness, and clear behavior.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 37

- Added a smoke-test assertion that the runtime game message text remains readable UTF-8 Chinese.
- This protects HUD-facing gameplay feedback from future encoding regressions after cleaning up visible UI text.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 38

- Added `MenuMouseController` so menu click handling and hover cursor updates live beside the other input controllers instead of inside `GamePanel`.
- Reduced `GamePanel`'s direct Swing event-listener responsibilities while preserving menu action dispatch and focus repaint behavior.
- Regenerated and pixel-checked the offscreen preview after the controller extraction.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 39

- Updated `tools/generate_project_doc.py` and regenerated `docs/项目说明.doc` / `docs/项目说明.docx` so the architecture section includes `InputController` and `MenuMouseController`.
- Structurally verified the regenerated DOCX contains the current input and rendering helper classes.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 40

- Added `RenderClock` to own render-frame counting and logic-frame cadence checks.
- Updated `GamePanel` to consume the render clock instead of directly managing raw frame arithmetic.
- Extended `StarMazeSmokeTest` with render-clock coverage for initial frame state and configured logic update cadence.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 41

- Updated `tools/generate_project_doc.py` and regenerated `docs/项目说明.doc` / `docs/项目说明.docx` so the architecture section includes `RenderClock`.
- Structurally verified the regenerated DOCX contains `RenderClock`, `MenuMouseController`, and `GameSceneRenderer`.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 42

- Added shared `MenuMetrics` and `MenuPanel` geometry helpers for menu panel dimensions, centering, and button sizing.
- Updated `MenuLayout` and `MenuRenderer` to use the same panel metrics so clickable button regions stay aligned with the rendered menu panels.
- Extended the smoke test with menu metric assertions covering panel dimensions, center alignment, and button containment.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 43

- Added tile coordinate conversion helpers to `BoardMetrics` for tile origins and screen centers.
- Updated entity and effect rendering to use shared board coordinate conversion instead of repeating offset arithmetic.
- Extended board renderer smoke coverage to verify stable tile-to-screen center calculations.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 44

- Centralized board frame, floor, wall, and rift visual colors and spacing values as `BoardRenderer` constants.
- Reworked board frame and tile drawing branches to consume named visual parameters instead of inline magic numbers.
- Preserved the existing static board-cache behavior while making future neon style tuning safer.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 45

- Added `GamePanel.stopLoop()` so offscreen tools and tests can cleanly stop the Swing timer after rendering.
- Updated `tools/RenderPreview.java` to stop the game loop explicitly after generating the preview image.
- Added a headless offscreen rendering smoke test that paints `GamePanel` into a `BufferedImage` and verifies the frame is nonblank.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 46

- Updated `tools/generate_project_doc.py` and regenerated `docs/项目说明.doc` / `docs/项目说明.docx` to include `MenuMetrics`, `MenuPanel`, and the offscreen `GamePanel` render check.
- Verified the regenerated project documents structurally using the bundled Python runtime and Unicode-safe token checks.
- Rebuilt the release jar after the document sync and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 47

- Added `ScoreRules` to isolate level-clear bonus and run-rank calculations from `GameState`.
- Updated `GameState` to delegate score bonus and rank decisions to the new rules helper.
- Extended smoke coverage for score bonus resource rewards, minimum bonus clamping, and rank thresholds.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 48

- Added `GameMessages` to centralize user-facing gameplay messages and small message-formatting helpers.
- Updated `GameState` to consume named message constants instead of scattering visible Chinese strings through gameplay branches.
- Extended smoke coverage for message readability and formatted level, crystal, and locked-exit messages.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 49

- Added `MenuText` to centralize static menu titles, help lines, stats labels, and result summaries.
- Updated `MenuRenderer` to consume menu text constants and iterate over help lines instead of embedding visible copy inside drawing branches.
- Extended smoke coverage for readable menu title text, help-line count, and level-clear summary formatting.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 50

- Updated `tools/generate_project_doc.py` and regenerated `docs/项目说明.doc` / `docs/项目说明.docx` so the class structure now includes `ScoreRules`, `GameMessages`, and `MenuText`.
- Structurally verified the regenerated project documents contain the new score-rule and text-catalog modules.
- Rebuilt the release jar and reran the jar-contained smoke test after the documentation sync.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 51

- Added `HudText` to centralize HUD labels, default control hints, and compact fallback text.
- Moved HUD horizontal positions and meter coordinates into named `HudRenderer` constants.
- Extended smoke coverage for readable HUD title text, default movement hints, and compact help fallback text.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 52

- Updated `tools/generate_project_doc.py` and regenerated `docs/项目说明.doc` / `docs/项目说明.docx` so the text-catalog architecture includes `HudText`.
- Structurally verified the regenerated documents contain `HudText`.
- Rebuilt the release jar and reran the jar-contained smoke test after the document sync.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 53

- Added `InputActions` to isolate keyboard-driven Enter/Esc state transitions from Swing key binding code.
- Updated `InputController` to delegate Enter and Escape behavior while keeping direct movement and skill bindings unchanged.
- Extended smoke coverage for keyboard start, pause, resume, and title-return flows.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 54

- Updated `tools/generate_project_doc.py` and regenerated `docs/项目说明.doc` / `docs/项目说明.docx` so the input architecture includes `InputActions`.
- Structurally verified the regenerated project documents contain `InputActions`.
- Rebuilt the release jar and reran the jar-contained smoke test after the documentation sync.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 55

- Split `InputController.install()` into movement and command binding installers.
- Added a `bindMovement` helper so WASD and arrow-key bindings share one direction-binding path.
- Preserved all existing keyboard shortcuts while making future input expansion easier to scan.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 56

- Added `SoundEngine` read-only status accessors for closed, enabled, and audio-available state.
- Strengthened sound smoke coverage to assert shutdown state, enabled toggling, and post-shutdown play safety.
- Kept the existing bounded single-thread audio queue and effect throttling behavior unchanged.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 57

- Regenerated the offscreen gameplay preview after the HUD, menu text, input, and sound-engine refactors.
- Pixel-checked `docs/game-preview.png` for nonblank rendering health: 1100x780, 2014 unique colors, 346394 non-dark pixels.
- Reran the jar-contained smoke test after preview generation to keep visual and runtime checks aligned.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 58

- Split `GameState.buildLevel()` into clearer level-runtime reset and population-application steps.
- Kept level generation, pickup placement, enemy placement, visual-event clearing, and rewind reset behavior unchanged.
- Reran the jar-contained smoke test to cover generated levels, enemy spawning, crystal counts, and runtime tick behavior after the flow cleanup.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 59

- Extracted `GameState.canEnter` and `GameState.bumpWall` from the player movement flow.
- Reduced movement-branch noise while preserving wall blocking, phase-wall entry, score penalty, bump messaging, and sound behavior.
- Reran the jar-contained smoke test to cover movement, phase activation, and timer updates after the refactor.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 60

- Split crystal, phase-cell, and rewind-cell pickup rewards into dedicated `GameState` helper methods.
- Kept `collectAtPlayer` focused on detecting which pickup exists at the player position.
- Reran the jar-contained smoke test to cover crystal counts, phase skill behavior, rewind state, and movement after the pickup-flow cleanup.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 61

- Split rift handling into explicit `warpThroughRift` and `convertRiftToPhaseEnergy` branches.
- Preserved shared rift score reward, visual effect emission, and phase sound behavior in the outer rift flow.
- Reran the jar-contained smoke test to cover phase behavior, visual-event queues, and generated-level stability after the rift-flow cleanup.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 62

- Extracted `GameState.canActivatePhase` to centralize active-phase and insufficient-energy rejection paths.
- Kept the successful phase activation path focused on meter spending, duration setup, score reward, effect emission, messaging, and sound.
- Reran the jar-contained smoke test to cover phase activation and timer-driven phase state behavior after the refactor.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 63

- Extracted `GameState.canRewind` to centralize rewind-charge and minimum-history rejection paths.
- Kept the rewind success path focused on applying the selected snapshot, spending charges, clearing history, and emitting effects.
- Reran the jar-contained smoke test to cover rewind history, visual events, phase behavior, and movement after the refactor.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 64

- Extracted `GameState.restoreEnemiesFromSnapshot` from the rewind success path.
- Kept enemy position restoration, rewind stun application, and stun-effect emission together in one focused helper.
- Reran the jar-contained smoke test to cover rewind history, enemy state, and visual-event behavior after the refactor.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 65

- Extracted `GameState.occupiedEnemyPositions` from the enemy movement loop.
- Kept the occupied-cell snapshot behavior unchanged before per-enemy movement decisions begin.
- Reran the jar-contained smoke test to cover A* occupied-cell routing, enemy controller decisions, and generated-level stability.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 66

- Extracted per-enemy movement into `GameState.moveEnemy`.
- Kept stunned cooldown, occupied-cell removal/addition, A* direction choice, movement validation, and fallback turning in one focused helper.
- Reran the jar-contained smoke test to cover enemy controller behavior, occupied-cell pathing, and generated-level reachability.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 67

- Extracted `GameState.disruptEnemyWithPhase` from enemy collision handling.
- Kept phase collision stun, score reward, stun visual effect, feedback message, and power sound together in one helper.
- Reran the jar-contained smoke test to cover enemy behavior, visual-event queues, phase state, and movement after the collision-flow cleanup.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 68

- Split exit handling into `GameState.clearLevel` and `GameState.rejectLockedExit`.
- Kept level-clear score bonus, mode transition, high-score persistence, win sound, locked-exit message, and bump sound behavior unchanged.
- Reran the jar-contained smoke test to cover scoring rules, save-data round trips, generated levels, and movement after the exit-flow cleanup.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 69

- Centralized `EntityRenderer` colors for locked exits, player core/stroke, crystal highlight, stunned enemies, and enemy stroke.
- Replaced the inline locked-exit glyph with a named `LOCKED_EXIT_TEXT` constant.
- Rebuilt the jar, reran smoke tests, regenerated the offscreen preview, and pixel-checked the result: 1100x780, 1898 unique colors, 347540 non-dark pixels.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 70

- Centralized `EffectRenderer` constants for rift color, ring scales, alpha values, stroke widths, stun-orbit dots, and screen-shake wave parameters.
- Kept phase burst, rift warp, rewind wave, enemy stun, and screen-shake rendering behavior visually equivalent while making effect tuning safer.
- Rebuilt the jar, reran smoke tests, regenerated the offscreen preview, and pixel-checked the result: 1100x780, 1922 unique colors, 343410 non-dark pixels.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 71

- Inspected `dist/StarMaze.jar` to confirm the newer helper classes are included in the published artifact.
- Verified `ScoreRules`, `GameMessages`, `MenuText`, `HudText`, `InputActions`, `MenuMetrics`, `MenuPanel`, `RenderClock`, and `StarMazeSmokeTest` are present in the jar.
- Reran the jar-contained smoke test after the release-contents check.

Validation:

- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 72

- Updated `docs/作业报告.md` so the student-style report reflects the newer renderer, text-catalog, input-action, score-rule, and message-catalog refactors.
- Preserved the intentionally imperfect report tone while changing the main remaining flaw from `GamePanel` size to the still-large `GameState` gameplay responsibility.
- Verified the report contains the expected new class names, then rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 73

- Added `GameState` helpers for capped phase-meter gain, capped rewind-charge gain, and nonnegative score penalties.
- Replaced repeated `Math.min` / `Math.max` resource clamps across restart, next-level, movement, pickups, rifts, and rewind spending.
- Reran the jar-contained smoke test to cover scoring, phase activation, rewind behavior, and movement after the resource-helper cleanup.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 74

- Extracted `GameState.updatePhaseState` from the main timer update loop.
- Kept phase countdown, wall-safe recovery after phase expiry, and natural phase recharge together in one focused helper.
- Reran the jar-contained smoke test to cover timer ticks, phase activation, and generated-level behavior after the update-loop cleanup.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 75

- Added named `GameState.shouldMoveEnemies` and `GameState.shouldRecordRewindSnapshot` timer cadence helpers.
- Updated the main update loop so enemy movement and rewind snapshot scheduling read as explicit gameplay beats.
- Reran the jar-contained smoke test to cover timer ticks, enemy behavior, rewind history, and phase state after the cadence cleanup.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 76

- Added smoke coverage for positive gameplay cadence configuration values.
- Verified enemy movement, rewind snapshot, and phase recharge intervals remain valid after the update-loop helper extraction.
- Kept cadence validation focused on stable configuration invariants instead of brittle random-map movement observations.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 77

- Added a read-only `GameSceneRenderer.boardCacheBuilds` counter so board-cache reuse can be verified directly.
- Extended smoke coverage to prove the scene renderer builds the static board cache once, reuses it for unchanged level/metrics, and rebuilds when metrics change.
- Strengthened the rendering-performance safety net around Swing self-painting and static board caching.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 78

- Updated `tools/generate_project_doc.py` and regenerated `docs/项目说明.doc` / `docs/项目说明.docx` to mention the `GameSceneRenderer` static board-cache responsibility.
- Added the board-cache reuse check to the project explanation's test-results wording.
- Structurally verified the regenerated documents contain `GameSceneRenderer` and the chessboard-cache wording, then rebuilt and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 79

- Regenerated `docs/game-preview.png` after the scene-cache instrumentation and project-document refresh.
- Pixel-checked the preview for nonblank rendering health: 1100x780, 1917 unique colors, 350219 non-dark pixels.
- Reran the jar-contained smoke test after preview generation.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 80

- Centralized `MenuRenderer` overlay alpha values for title, pause, help, settings, level-clear, and game-over screens.
- Moved help-line dot size, baseline offset, text offset, and line gap into named constants.
- Reran the jar-contained smoke test to cover menu text, menu layout, menu metrics, and offscreen rendering after the menu visual-parameter cleanup.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 81

- Inspected `dist/StarMaze.jar` to confirm the current renderer classes and smoke-test entry are present in the release artifact.
- Verified `GameSceneRenderer`, `MenuRenderer`, `HudRenderer`, `EffectRenderer`, `EntityRenderer`, and `StarMazeSmokeTest` are included in the jar.
- Reran the jar-contained smoke test after the release-contents check.

Validation:

- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 82

- Centralized `FooterRenderer` font size and bottom-baseline margin into named constants.
- Removed an unused `Color` import from the footer renderer.
- Reran the jar-contained smoke test to cover offscreen rendering and core runtime behavior after the footer cleanup.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 83

- Centralized `BackgroundRenderer` grid color and stroke width.
- Centralized `StarField` star placement, scroll, alpha pulse, minimum alpha, pulse speed, and star-size parameters.
- Rebuilt the jar, reran smoke tests, regenerated the offscreen preview, and pixel-checked the result: 1100x780, 1919 unique colors, 343524 non-dark pixels.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 84

- Centralized remaining `HudRenderer` right-message and rewind-dot offsets into named constants.
- Kept the HUD layout visually equivalent while removing the last inline right-margin and rewind-dot offset values from the paint flow.
- Reran the jar-contained smoke test to cover HUD text, offscreen rendering, and core runtime behavior after the HUD cleanup.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 85

- Centralized additional `EntityRenderer` visual parameters for exit stroke/radius/alpha, locked-exit text sizing, actor radius, player glow, crystal pulse, pickup strokes, and enemy glow.
- Kept player, pickup, exit, and enemy visuals equivalent while moving more tuning values out of drawing branches.
- Rebuilt the jar, reran smoke tests, regenerated the offscreen preview, and pixel-checked the result: 1100x780, 2017 unique colors, 347772 non-dark pixels.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 86

- Centralized `BoardRenderer` rift pulse parameters for base alpha, amplitude, coordinate phase factors, pulse speed, and oval sizing.
- Kept rift floor animation visually equivalent while removing more inline math constants from the tile-rendering branch.
- Rebuilt the jar, reran smoke tests, regenerated the offscreen preview, and pixel-checked the result: 1100x780, 1892 unique colors, 353025 non-dark pixels.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 87

- Updated `docs/作业报告.md` so the student-style report reflects the newer renderer split and visual-parameter cleanup work.
- Kept the report intentionally informal while adding `GameSceneRenderer`, `EntityRenderer`, `EffectRenderer`, `InputActions`, and magic-number cleanup details.
- Verified the report tokens, rebuilt the release jar, and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 88

- Extracted `GamePanel.advanceFrame` from the Swing timer lambda.
- Kept render-clock advancement, throttled game-state updates, effect-layer advancement, menu-button layout refresh, and repaint scheduling together in one frame-step helper.
- Reran the jar-contained smoke test to cover timer updates, offscreen rendering, and core runtime behavior after the panel loop cleanup.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 89

- Regenerated `docs/game-preview.png` after the latest `GamePanel` and renderer cleanups.
- Pixel-checked the preview for nonblank rendering health: 1100x780, 1900 unique colors, 351678 non-dark pixels.
- Inspected the release jar for `GamePanel`, core renderer classes, and `StarMazeSmokeTest`, then reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 90

- Moved pause, help, settings, level-clear, and game-over button offsets from `MenuLayout` into `MenuMetrics`.
- Kept menu button positions unchanged while making panel-relative layout tuning live in one geometry class.
- Reran the jar-contained smoke test to cover menu layout, menu metrics, button cache behavior, and offscreen rendering after the layout cleanup.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 91

- Strengthened `StarMazeSmokeTest` menu-geometry coverage after moving button offsets into `MenuMetrics`.
- Added reusable button-in-panel assertions for settings, level-clear, and game-over menu layouts.
- Reran the jar-contained smoke test to verify the expanded menu geometry checks.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 92

- Added a `StarMazeSmokeTest.createPreviewImage` helper for shared offscreen preview image creation.
- Reused the helper in scene-cache and full-panel offscreen rendering checks to reduce duplicate test setup.
- Reran the jar-contained smoke test to verify the test-maintenance cleanup.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 93

- Rebuilt the release jar and regenerated `docs/game-preview.png` after the latest renderer and smoke-test maintenance changes.
- Verified `dist/StarMaze.jar`, `dist/run.bat`, and `docs/game-preview.png` exist and are nonempty.
- Pixel-checked the preview for nonblank rendering health: 1100x780, 1892 unique colors, 347506 non-dark pixels, then reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 94

- Extended menu geometry smoke coverage to include the help screen's title-return button.
- Reused the button-in-panel assertion helper for the help menu after moving panel-relative offsets into `MenuMetrics`.
- Reran the jar-contained smoke test to verify the expanded menu geometry coverage.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 95

- Updated `GameFrame` window-closing handling to stop the `GamePanel` timer before shutting down audio.
- Aligned the real window lifecycle with the offscreen preview/test lifecycle that already calls `GamePanel.stopLoop`.
- Reran the jar-contained smoke test after the lifecycle cleanup.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 96

- Added `GamePanel.isLoopRunning` so the Swing timer lifecycle can be verified without exposing the timer itself.
- Extended offscreen rendering smoke coverage to assert the panel loop starts after construction and stops after explicit shutdown.
- Reran the jar-contained smoke test to verify the lifecycle assertions.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 97

- Updated `tools/generate_project_doc.py` and regenerated `docs/项目说明.doc` / `docs/项目说明.docx` to mention `GamePanel` timer shutdown and loop-state testing.
- Updated the project explanation's smoke-test wording to include timer start/stop checks alongside offscreen rendering and scene-cache reuse.
- Structurally verified the regenerated documents, rebuilt the release jar, and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 98

- Regenerated `docs/game-preview.png` after the timer-lifecycle documentation update and latest renderer cleanup.
- Pixel-checked the preview for nonblank rendering health: 1100x780, 1865 unique colors, 336569 non-dark pixels.
- Reran the jar-contained smoke test after preview generation.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 99

- Added the jar-contained smoke-test command to `README.md` so release self-checking is documented beside build/run instructions.
- Kept the README concise while making the headless regression command easier to find after packaging.
- Rebuilt the release jar and reran the jar-contained smoke test after the README update.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 100

- Performed a milestone release audit across static artifacts, jar contents, preview rendering, and runtime smoke testing.
- Verified README, changelog, release jar, run script, preview image, project documents, and homework report are present and nonempty.
- Confirmed the jar contains `StarMazeApp`, `StarMazeSmokeTest`, and `GamePanel`, pixel-checked the preview, and reran the jar-contained smoke test.

Validation:

- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 101

- Added `GameViewRenderer` as a rendering facade for background, gameplay scene, effects, footer, and menu overlay composition.
- Updated `GamePanel` to delegate paint orchestration to `GameViewRenderer`, leaving the panel focused on Swing lifecycle, input, frame advancement, effect state, and menu hit-layout updates.
- Extended smoke coverage for the new view renderer, including nonblank offscreen output and scene-cache reuse across repeated paints.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 102

- Updated `tools/generate_project_doc.py` and regenerated `docs/项目说明.doc` / `docs/项目说明.docx` so the architecture section includes `GameViewRenderer`.
- Added the view-renderer facade check to the project explanation's smoke-test coverage wording.
- Structurally verified the regenerated documents contain `GameViewRenderer`, rebuilt the release jar, and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 103

- Regenerated `docs/game-preview.png` after introducing the `GameViewRenderer` facade.
- Pixel-checked the preview for nonblank rendering health: 1100x780, 1934 unique colors, 351561 non-dark pixels.
- Inspected the release jar for `GameViewRenderer`, `GamePanel`, and `StarMazeSmokeTest`, then reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 104

- Updated `docs/作业报告.md` so the student-style report mentions the new `GameViewRenderer` facade.
- Added `GameViewRenderer.java` to the report's changed-file list while preserving the intentionally informal tone.
- Verified the report contains the new renderer facade name, rebuilt the release jar, and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 105

- Consolidated menu renderer typography, baseline offsets, panel stroke width, and button text offset into named constants.
- Kept the visual layout unchanged while making title, pause, help, settings, result, and game-over overlays easier to tune safely.
- Rebuilt the release jar and reran the jar-contained smoke test after the menu-rendering cleanup.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 106

- Extracted pickup rendering into `PickupRenderer` for crystals, phase cells, and rewind cells.
- Kept `EntityRenderer.paintPickups` as a stable wrapper while moving the drawing details into the focused renderer.
- Removed the old pickup drawing code from `EntityRenderer`, rebuilt the release jar, and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 107

- Extracted exit rendering into `ExitRenderer`, including locked/open color state, portal rings, lock text sizing, and stroke settings.
- Preserved the `EntityRenderer.paintExit` wrapper so `GameSceneRenderer` did not need a broader call-site change.
- Cleaned obsolete exit constants from `EntityRenderer`, rebuilt the release jar, and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 108

- Extracted player drawing into `PlayerRenderer`, covering phase glow, player body, core, and outline stroke.
- Left `EntityRenderer.paintPlayer` as the wrapper used by `GameSceneRenderer`, keeping the scene renderer API stable.
- Removed obsolete player imports and constants from `EntityRenderer`, rebuilt the release jar, and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 109

- Extracted enemy drawing into `EnemyRenderer`, including stunned color, glow, and direction-shaped patrol body rendering.
- Reduced `EntityRenderer` into a lightweight facade over pickup, exit, enemy, and player renderers.
- Rebuilt the release jar, reran the jar-contained smoke test, regenerated `docs/game-preview.png`, and pixel-checked the preview.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 110

- Updated `tools/generate_project_doc.py` so the architecture section mentions the new entity-renderer split.
- Regenerated `docs/项目说明.doc` and `docs/项目说明.docx`, then verified the project doc and homework report mention `PickupRenderer`, `ExitRenderer`, `PlayerRenderer`, and `EnemyRenderer`.
- Rebuilt the release jar and reran the jar-contained smoke test after the documentation sync.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 111

- Audited `dist/StarMaze.jar` after the entity-renderer split to confirm the new renderer classes are packaged.
- Verified the jar contains `PickupRenderer`, `ExitRenderer`, `PlayerRenderer`, `EnemyRenderer`, `EntityRenderer`, and `GameViewRenderer`.
- Reran the jar-contained smoke test from the audited release jar.

Validation:

- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 112

- Added direct smoke coverage for the `EntityRenderer` facade after splitting pickup, exit, player, and enemy rendering.
- The new offscreen check paints pickups, exit, enemies, and player through the facade and verifies visible pixels are produced.
- Rebuilt the release jar and reran the jar-contained smoke test with the expanded rendering coverage.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 113

- Added `PhaseRules` as a pure rules helper for phase gain caps, restart floor, passive recharge cadence, rewind restoration, and affordability checks.
- Updated `GameState` to delegate phase-number calculations to `PhaseRules` while keeping gameplay flow and public state access unchanged.
- Added smoke assertions for `PhaseRules`, rebuilt the release jar, and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 114

- Added `RewindRules` as a pure rules helper for gaining, spending, and checking rewind charges and history readiness.
- Updated `GameState` to use `RewindRules` for charge bounds and rewind availability while preserving snapshot restore behavior.
- Added smoke assertions for rewind-rule boundaries, rebuilt the release jar, and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 115

- Updated the project-document generator so the architecture section includes `PhaseRules` and `RewindRules`.
- Updated the student-style homework report to mention the new gameplay-rule helpers and list their source files.
- Regenerated `docs/项目说明.doc` and `docs/项目说明.docx`, verified the new rule names, rebuilt the release jar, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 116

- Audited the release jar after the gameplay-rule extraction and renderer split.
- Confirmed `PhaseRules`, `RewindRules`, `PickupRenderer`, `ExitRenderer`, `PlayerRenderer`, and `EnemyRenderer` are packaged in `dist/StarMaze.jar`.
- Reran the jar-contained smoke test from the audited release jar.

Validation:

- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 117

- Added `LevelProgressRules` for restart penalty, next-level entry bonus, and next-level phase gain calculations.
- Updated `GameState` to delegate level progression arithmetic to the new rules helper while preserving level-build flow.
- Added smoke assertions for level-progress rules, rebuilt the release jar, and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 118

- Updated the project-document generator to include `LevelProgressRules` alongside the other gameplay-rule helpers.
- Updated the homework report so the newly extracted level-progress rules are described and listed.
- Regenerated `docs/项目说明.doc` and `docs/项目说明.docx`, verified `LevelProgressRules` appears in docs, rebuilt the release jar, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 119

- Audited the release jar after adding `LevelProgressRules`.
- Confirmed `LevelProgressRules`, `PhaseRules`, and `RewindRules` are packaged in `dist/StarMaze.jar`.
- Reran the jar-contained smoke test from the audited release jar.

Validation:

- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 120

- Ran a structural line-count audit after the renderer and gameplay-rule refactors.
- Confirmed the UI renderer split is now compact (`EntityRenderer` 22 lines, `GamePanel` 58 lines) while `GameState` remains the largest model class at 469 lines.
- Reordered recent changelog entries back into numeric loop order so ongoing optimization notes remain readable.

Validation:

- `rg -n "Optimization Loop 10[5-9]|Optimization Loop 11[0-9]" CHANGELOG.md`
- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 121

- Moved crystal, phase-cell, and rewind-cell pickup score calculations into `ScoreRules`.
- Updated `GameState` pickup handling to use the centralized score helpers while keeping collection flow unchanged.
- Added smoke assertions for pickup scoring, rebuilt the release jar, and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 122

- Moved rift visit, phase activation, rewind penalty, and phase-stun score values into `ScoreRules`.
- Updated `GameState` to use the centralized action-score helpers while preserving the surrounding gameplay flow.
- Added smoke assertions for the new action-score helpers, rebuilt the release jar, and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 123

- Updated the project-document generator so `ScoreRules` is described as covering pickup and action scoring in addition to level-clear/rank scoring.
- Updated the homework report to mention the expanded `ScoreRules` responsibility in the same informal style.
- Regenerated `docs/项目说明.doc` and `docs/项目说明.docx`, verified score-rule wording, rebuilt the release jar, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 124

- Audited `dist/StarMaze.jar` to confirm `ScoreRules`, `LevelProgressRules`, `PhaseRules`, and `RewindRules` are packaged.
- Searched `GameState` and confirmed the remaining `GameConfig.SCORE_*` references now live inside dedicated rule helpers.
- Reran the jar-contained smoke test from the audited release jar.

Validation:

- `jar tf dist\StarMaze.jar`
- `rg -n "GameConfig\\.SCORE_|score \\+=|penalizeScore\\(" src\main\java\com\starmaze\model`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 125

- Added named config values for movement score cost and phase-wall-step score.
- Added `ScoreRules.moveCost` and `ScoreRules.phaseWallStep`, then updated `GameState` to use them instead of bare `1` score adjustments.
- Added smoke assertions for the new score helpers, rebuilt the release jar, and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 126

- Audited scoring references after centralizing movement and phase-wall-step score values.
- Confirmed `GameState` no longer contains direct `GameConfig.SCORE_*` references or bare numeric score adjustments.
- Checked the release jar for `ScoreRules`, `GameConfig`, and `StarMazeSmokeTest`, then reran the jar-contained smoke test.

Validation:

- `rg -n "score \\+= [0-9]|penalizeScore\\([0-9]|GameConfig\\.SCORE_" src\main\java\com\starmaze\model`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 127

- Added `RiftOutcome` and `RiftRules` to name the quantum-rift outcomes and centralize rift phase gain / teleport-distance rules.
- Updated `GameState` rift handling to switch on `RiftOutcome` instead of directly branching on `Random.nextBoolean`.
- Added smoke assertions for rift outcome coverage and rift config values, rebuilt the release jar, and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 128

- Updated the project-document generator and homework report to include `RiftRules` and `RiftOutcome`.
- Regenerated `docs/项目说明.doc` and `docs/项目说明.docx`, then verified the new rift rule names appear in the generated docs and report.
- Rebuilt the release jar and reran the jar-contained smoke test after the documentation sync.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 129

- Audited the release jar after the rift-rule extraction.
- Confirmed `RiftRules`, `RiftOutcome`, `GameState`, and `StarMazeSmokeTest` are packaged in `dist/StarMaze.jar`.
- Reran the jar-contained smoke test and checked source/changelog references for the rift-rule split.

Validation:

- `jar tf dist\StarMaze.jar`
- `rg -n "RiftRules|RiftOutcome" CHANGELOG.md src\main\java\com\starmaze`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 130

- Added `EnemyController.advanceEnemy` so stunned cooldown, movement application, fallback facing, and occupied-position refresh live with enemy AI control.
- Reduced `GameState.moveEnemy` to a delegation wrapper, leaving `GameState` responsible for enemy-loop timing and collision outcomes.
- Added smoke assertions for stunned enemy cooldown and normal enemy advancement, rebuilt the release jar, and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 131

- Extracted menu button drawing into `MenuButtonRenderer`, including button fill, stroke, typography, and centered-label rendering.
- Reduced `MenuRenderer` to page-level overlay/panel/text composition while delegating button painting through generated action labels.
- Rebuilt the release jar, reran the jar-contained smoke test, regenerated `docs/game-preview.png`, and pixel-checked the preview.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 132

- Made `MenuButtonRenderer` a public renderer module so it can be directly covered by smoke tests.
- Added an offscreen `MenuButtonRenderer` smoke check that paints a button and verifies visible pixels.
- Rebuilt the release jar and reran the jar-contained smoke test with the new direct button-renderer coverage.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 133

- Audited the release jar to confirm `MenuButtonRenderer` is packaged with `MenuRenderer` and `StarMazeSmokeTest`.
- Updated the project-document generator and homework report so the menu button renderer split is documented.
- Regenerated `docs/项目说明.doc` and `docs/项目说明.docx`, verified `MenuButtonRenderer` appears in docs, rebuilt the release jar, and reran the jar-contained smoke test.

Validation:

- `jar tf dist\StarMaze.jar`
- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 134

- Extracted help menu title and bullet-list drawing into `HelpMenuContentRenderer`.
- Reduced `MenuRenderer.paintHelp` to overlay, panel, help-content, and button composition while keeping layout and text unchanged.
- Rebuilt the release jar, reran the jar-contained smoke test, regenerated `docs/game-preview.png`, and pixel-checked the preview.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 135

- Made `HelpMenuContentRenderer` public so the help-content drawing module can be directly smoke-tested.
- Added an offscreen smoke check for help menu content rendering and verified it produces visible pixels.
- Updated project documentation and the homework report to include `HelpMenuContentRenderer`, regenerated docs, rebuilt the release jar, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 136

- Audited the release jar to confirm `HelpMenuContentRenderer`, `MenuButtonRenderer`, `MenuRenderer`, and `StarMazeSmokeTest` are packaged.
- Ran a renderer line-count audit showing `MenuRenderer` is now 149 lines after the button/help-content split.
- Reran the jar-contained smoke test from the audited release jar.

Validation:

- `jar tf dist\StarMaze.jar`
- `Get-ChildItem src\main\java\com\starmaze\ui\*Renderer.java`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 137

- Extracted level-clear and game-over result text drawing into `ResultMenuContentRenderer`.
- Reduced `MenuRenderer` result-screen methods to overlay, panel, result-content, and button composition while preserving existing text and layout.
- Rebuilt the release jar, reran the jar-contained smoke test, regenerated `docs/game-preview.png`, and pixel-checked the preview.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 138

- Made `ResultMenuContentRenderer` public so result-screen text rendering can be directly smoke-tested.
- Added an offscreen smoke check that paints both level-clear and game-over result content and verifies visible pixels.
- Rebuilt the release jar and reran the jar-contained smoke test with the new direct result-renderer coverage.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 139

- Updated the project-document generator and homework report to include `ResultMenuContentRenderer`.
- Regenerated `docs/项目说明.doc` and `docs/项目说明.docx`, then verified the result renderer is mentioned in generated docs and report.
- Rebuilt the release jar, confirmed `ResultMenuContentRenderer` is packaged, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 140

- Extracted title-screen text and save-stat drawing into `TitleMenuContentRenderer`.
- Reduced `MenuRenderer.paintTitle` to overlay, title-content, and button composition while keeping title layout and stats unchanged.
- Rebuilt the release jar, reran the jar-contained smoke test, regenerated `docs/game-preview.png`, and pixel-checked the preview.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 141

- Made `TitleMenuContentRenderer` public so title-screen content rendering can be directly smoke-tested.
- Added an offscreen smoke check for title content rendering and verified generated documents mention the new renderer.
- Regenerated project docs, rebuilt the release jar, confirmed `TitleMenuContentRenderer` is packaged, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 142

- Extracted pause and settings panel text drawing into `SimpleMenuContentRenderer`.
- Reduced `MenuRenderer.paintPause` and `MenuRenderer.paintSettings` to overlay, panel, simple-content, and button composition.
- Rebuilt the release jar, reran the jar-contained smoke test, regenerated `docs/game-preview.png`, and pixel-checked the preview.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 143

- Made `SimpleMenuContentRenderer` public and added direct offscreen smoke coverage for pause/settings content rendering.
- Updated project documentation and the homework report to include the pause/settings content renderer split.
- Regenerated docs, rebuilt the release jar, confirmed `SimpleMenuContentRenderer` is packaged, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 144

- Ran a menu-renderer line-count audit after the title/simple/help/result/button content splits.
- Confirmed `MenuRenderer` is now 85 lines and mainly coordinates overlays, panels, content renderers, and button rendering.
- Audited the release jar for all menu content renderers and reran the jar-contained smoke test.

Validation:

- `Get-ChildItem src\main\java\com\starmaze\ui\*Menu*Renderer.java`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 145

- Extracted expanding ring effect drawing into `RingEffectRenderer`.
- Reduced `EffectRenderer` so phase burst, rift warp, and rewind wave delegate ring geometry while `EffectRenderer` keeps effect dispatch and stun drawing.
- Rebuilt the release jar, reran the jar-contained smoke test, regenerated `docs/game-preview.png`, and pixel-checked the preview.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 146

- Made `RingEffectRenderer` public and added direct offscreen smoke coverage for ring-effect drawing.
- Updated project documentation and the homework report to mention the ring-effect renderer split.
- Regenerated docs, rebuilt the release jar, confirmed `RingEffectRenderer` is packaged, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 147

- Extracted enemy stun dot-orbit drawing into `StunEffectRenderer`.
- Reduced `EffectRenderer` so it now delegates both ring effects and stun effects while retaining effect dispatch and screen shake.
- Rebuilt the release jar, reran the jar-contained smoke test, regenerated `docs/game-preview.png`, and pixel-checked the preview.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 148

- Made `StunEffectRenderer` public and added direct offscreen smoke coverage for stun-dot effect drawing.
- Added a precise nontransparent-pixel helper for tiny visual effects whose dots can miss the coarse preview sampler.
- Updated project documentation and the homework report, regenerated docs, confirmed effect renderers are packaged, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 149

- Ran an effect-renderer line-count audit after splitting ring and stun effects.
- Confirmed `EffectRenderer` is now 48 lines and mainly handles screen shake plus effect-type dispatch.
- Audited the release jar for `EffectRenderer`, `RingEffectRenderer`, and `StunEffectRenderer`, then reran the jar-contained smoke test.

Validation:

- `Get-ChildItem src\main\java\com\starmaze\ui\*EffectRenderer.java`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 150

- Ran a renderer-wide structure audit across the Swing UI layer.
- Confirmed `GamePanel` is 58 lines, `GameViewRenderer` is 46 lines, `MenuRenderer` is 85 lines, `EntityRenderer` is 22 lines, and `EffectRenderer` is 48 lines after the rendering-responsibility split.
- Audited the release jar for the main view, HUD/menu/entity/effect renderers, then reran the jar-contained smoke test.

Validation:

- `Get-ChildItem src\main\java\com\starmaze\ui\*Renderer.java`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 151

- Extracted HUD phase-meter drawing into `HudMeterRenderer`.
- Reduced `HudRenderer` so it delegates the meter fill, label, clamping, and stroke drawing while keeping HUD layout composition.
- Rebuilt the release jar, reran the jar-contained smoke test, regenerated `docs/game-preview.png`, and pixel-checked the preview.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 152

- Made `HudMeterRenderer` public and added direct offscreen smoke coverage for HUD meter drawing.
- Updated project documentation and the homework report to include the HUD meter renderer split.
- Regenerated docs, rebuilt the release jar, confirmed `HudMeterRenderer` is packaged, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 153

- Extracted HUD rewind-charge dot drawing into `HudRewindRenderer`.
- Reduced `HudRenderer` so it delegates rewind label, filled/empty dots, and dot outlines while keeping HUD layout composition.
- Rebuilt the release jar, reran the jar-contained smoke test, regenerated `docs/game-preview.png`, and pixel-checked the preview.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 154

- Made `HudRewindRenderer` public and added direct offscreen smoke coverage for rewind-charge dot drawing.
- Updated project documentation and the homework report to include the HUD rewind renderer split.
- Regenerated docs, rebuilt the release jar, confirmed `HudRewindRenderer` is packaged, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 155

- Extracted HUD right-side hint/message fitting and right-aligned drawing into `HudMessageRenderer`.
- Reduced `HudRenderer` to HUD panel composition, stat labels, meter delegation, rewind delegation, and message selection.
- Rebuilt the release jar, reran the jar-contained smoke test, regenerated `docs/game-preview.png`, and pixel-checked the preview.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 156

- Made `HudMessageRenderer` public and added direct offscreen smoke coverage for right-side HUD hint drawing.
- Synced the generated project说明 and homework-style report with the new HUD message renderer split.
- Regenerated documentation, rebuilt the release jar, confirmed the renderer is packaged, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 157

- Extracted HUD title, level, score, high-score, and crystal-progress text into `HudStatsRenderer`.
- Reduced `HudRenderer` to panel drawing plus delegation to stats, phase meter, rewind charges, and right-side message renderers.
- Rebuilt the release jar, reran the jar-contained smoke test, regenerated `docs/game-preview.png`, and pixel-checked the preview.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 158

- Made `HudStatsRenderer` public and added direct offscreen smoke coverage for HUD title/stat text drawing.
- Synced the generated project说明 and homework-style report with the HUD stats renderer split.
- Regenerated documentation, rebuilt the release jar, confirmed the renderer is packaged, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 159

- Extracted HUD rounded panel fill/stroke drawing into `HudPanelRenderer`.
- Reduced `HudRenderer` to HUD layout constants plus delegation to panel, stats, phase meter, rewind, and message renderers.
- Rebuilt the release jar, reran the jar-contained smoke test, regenerated `docs/game-preview.png`, and pixel-checked the preview.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 160

- Made `HudPanelRenderer` public and added direct offscreen smoke coverage for HUD panel chrome drawing.
- Synced the generated project说明 and homework-style report with the HUD panel renderer split.
- Regenerated documentation, rebuilt the release jar, confirmed the renderer is packaged, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 161

- Added direct offscreen smoke coverage for `FooterRenderer` bottom keyboard hint drawing.
- Synced the generated project说明 test coverage description with the footer renderer check.
- Regenerated documentation, rebuilt the release jar, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 162

- Extracted menu-mode overlay dispatch from `GameViewRenderer` into `MenuOverlayRenderer`.
- Reduced `GameViewRenderer` to background, gameplay scene, and menu overlay pipeline orchestration.
- Rebuilt the release jar, reran the jar-contained smoke test, regenerated `docs/game-preview.png`, and pixel-checked the preview.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 163

- Made `MenuOverlayRenderer` public and added direct offscreen smoke coverage for title/pause overlay dispatch.
- Synced the generated project说明 and homework-style report with the menu overlay renderer split.
- Regenerated documentation, rebuilt the release jar, confirmed the renderer is packaged, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 164

- Extracted menu overlay tint and rounded panel chrome drawing into `MenuChromeRenderer`.
- Reduced `MenuRenderer` so it delegates visual chrome while keeping mode-specific content and button composition.
- Rebuilt the release jar, reran the jar-contained smoke test, regenerated `docs/game-preview.png`, and pixel-checked the preview.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 165

- Made `MenuChromeRenderer` public and added direct offscreen smoke coverage for menu overlay tint and panel chrome drawing.
- Synced the generated project说明 and homework-style report with the menu chrome renderer split.
- Regenerated documentation, rebuilt the release jar, confirmed the renderer is packaged, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 166

- Extracted menu button label assembly and button-list painting into `MenuButtonGroupRenderer`.
- Reduced `MenuRenderer` to menu overlay/content sequencing while delegating button group rendering.
- Rebuilt the release jar, reran the jar-contained smoke test, regenerated `docs/game-preview.png`, and pixel-checked the preview.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 167

- Made `MenuButtonGroupRenderer` public and added direct offscreen smoke coverage for labeled menu button groups.
- Synced the generated project说明 and homework-style report with the menu button group renderer split.
- Regenerated documentation, rebuilt the release jar, confirmed the renderer is packaged, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 168

- Added direct offscreen smoke coverage for `BackgroundRenderer` gradient, starfield, and grid drawing.
- Synced the generated project说明 test coverage description with the background renderer check.
- Regenerated documentation, rebuilt the release jar, confirmed background classes are packaged, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 169

- Added direct smoke coverage for `StarField` star drawing and unsafe width/height tolerance.
- Synced the generated project说明 test coverage description with the starfield boundary check.
- Regenerated documentation, rebuilt the release jar, confirmed `StarField` is packaged, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 170

- Strengthened `BoardRenderer` smoke coverage to verify static tile-cache pixels, frame drawing, and animated rift drawing.
- Added a tiny deterministic rift-board fixture for direct board renderer checks.
- Rebuilt the release jar, reran the jar-contained smoke test, regenerated `docs/game-preview.png`, and pixel-checked the preview.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`
- `java "-Djava.awt.headless=true" -cp "build\classes;build\tools" RenderPreview`

## 2026-06-09 - Optimization Loop 171

- Made `PickupRenderer` public and added direct offscreen smoke coverage for pickup glyph drawing.
- Synced the generated project说明 test coverage description with the pickup renderer check.
- Regenerated documentation, rebuilt the release jar, confirmed `PickupRenderer` is packaged, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 172

- Made `ExitRenderer` public and added direct offscreen smoke coverage for locked and open exit drawing.
- Synced the generated project说明 test coverage description with the exit renderer check.
- Regenerated documentation, rebuilt the release jar, confirmed `ExitRenderer` is packaged, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 173

- Made `PlayerRenderer` public and added direct offscreen smoke coverage for normal and phase-active player drawing.
- Synced the generated project说明 test coverage description with the player renderer check.
- Regenerated documentation, rebuilt the release jar, confirmed `PlayerRenderer` is packaged, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 174

- Made `EnemyRenderer` public and added direct offscreen smoke coverage for all enemy directions plus stunned coloring.
- Synced the generated project说明 test coverage description with the enemy renderer check.
- Regenerated documentation, rebuilt the release jar, confirmed `EnemyRenderer` is packaged, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 175

- Added headless Swing smoke coverage for `MenuMouseController` hover cursor and click dispatch behavior.
- Verified menu mouse clicks request focus, request repaint, and trigger the selected menu action.
- Synced the generated project说明 test coverage description, regenerated documentation, rebuilt the release jar, and reran smoke testing.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 176

- Added headless Swing smoke coverage for `InputController` key binding installation.
- Verified movement, phase, rewind, escape, and help bindings are registered and that bound actions request repaint.
- Synced the generated project说明 test coverage description, regenerated documentation, rebuilt the release jar, and reran smoke testing.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `jar tf dist\StarMaze.jar`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 177

- Strengthened `SaveData` smoke coverage for damaged save files with invalid numeric and boolean values.
- Verified damaged saves fall back to safe values and can be recovered by writing a new valid game record.
- Synced the generated project说明 test coverage description, regenerated documentation, rebuilt the release jar, and reran smoke testing.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 178

- Strengthened `ScoreRules` smoke coverage for every run-rank threshold and just-below-threshold boundary.
- Synced the generated project说明 test coverage description with the score/rank boundary checks.
- Regenerated documentation, rebuilt the release jar, and reran the jar-contained smoke test.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 179

- Strengthened generated-level smoke coverage for late-level maximum dimensions, rift count limits, and rift start/exit exclusion.
- Added a shared rift-placement verifier for deterministic level generation checks.
- Synced the generated project说明 test coverage description, regenerated documentation, rebuilt the release jar, and reran smoke testing.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 180

- Strengthened `LevelPopulator` smoke coverage for item/enemy count caps and safe placement rules.
- Added pickup placement checks to ensure crystals and cells stay on floor positions, away from exits and rifts.
- Synced the generated project说明 test coverage description, regenerated documentation, rebuilt the release jar, and reran smoke testing.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 181

- Added smoke coverage for `Level` navigation helpers used by phase recovery and rift teleporting.
- Verified nearest-walkable search clamps out-of-bounds origins, far-floor selection respects distance, and no-candidate fallback returns start.
- Synced the generated project说明 test coverage description, regenerated documentation, rebuilt the release jar, and reran smoke testing.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 182

- Strengthened `PathFinder` A* smoke coverage for occupied target cells and unreachable targets.
- Added a deterministic blocked pathfinding fixture to protect no-path behavior.
- Synced the generated project说明 test coverage description, regenerated documentation, rebuilt the release jar, and reran smoke testing.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 183

- Strengthened `EnemyController` smoke coverage for patrol retention outside alert range and no-move reverse fallback.
- Kept existing chase, phase-patrol, stunned cooldown, and occupied-position refresh checks intact.
- Synced the generated project说明 test coverage description, regenerated documentation, rebuilt the release jar, and reran smoke testing.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 184

- Added a deterministic end-to-end gameplay smoke flow for collecting the required crystal and clearing a level.
- Used a tiny injected test level to exercise `GameState.movePlayer`, pickup collection, exit opening, level-clear mode, and scoring together.
- Synced the generated project说明 test coverage description, regenerated documentation, rebuilt the release jar, and reran smoke testing.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 185

- Added a deterministic enemy-collision gameplay smoke flow for both normal capture and phase-active counterplay.
- Verified inactive-phase collision enters `GAME_OVER`, while phase collision keeps gameplay active and stuns the enemy.
- Synced the generated project说明 test coverage description, regenerated documentation, rebuilt the release jar, and reran smoke testing.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 186

- Added a deterministic `GameState.rewind()` gameplay smoke flow.
- Verified rewind restores player/enemy positions, spends a charge, halves restored phase time, stuns restored enemies, and emits visual effects.
- Synced the generated project说明 test coverage description, regenerated documentation, rebuilt the release jar, and reran smoke testing.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 187

- Added a deterministic phase-ending recovery smoke flow for the player standing inside a wall.
- Verified `GameState.update()` moves the player to the nearest walkable tile and shows the safe phase-ending message.
- Synced the generated project说明 test coverage description, regenerated documentation, rebuilt the release jar, and reran smoke testing.

Validation:

- `python tools\generate_project_doc.py`
- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 188

- Added a `GameStateFixture` helper inside `StarMazeSmokeTest` to centralize deterministic `GameState` setup.
- Replaced scattered reflection calls in deterministic gameplay flow tests with fixture methods for level injection, pickup cleanup, enemy access, rewind history access, and integer field setup.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 189

- Added `BoardLayerRenderer` to own board metric calculation, static board cache lifetime, frame painting, and dynamic rift tile painting.
- Slimmed `GameSceneRenderer` so it now composes board and entity layers instead of carrying board cache state directly.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 190

- Added `EntityLayerRenderer` to own gameplay entity layer ordering for pickups, exit, enemies, and player.
- Slimmed `GameSceneRenderer` again so it composes board and entity layers without knowing individual entity draw calls.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 191

- Added `GameplayLayerRenderer` to own the gameplay view composition of screen shake, HUD, scene, board effects, and footer.
- Slimmed `GameViewRenderer` so it now coordinates background, gameplay layer, and menu overlay instead of managing gameplay render internals.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 192 - Rendering Architecture Stable v1.0

- Added direct smoke coverage for `BoardLayerRenderer`, `EntityLayerRenderer`, and `GameplayLayerRenderer`.
- Verified board layer cache reuse/rebuild behavior, ordered entity layer painting, and gameplay layer composition with HUD, scene, effects, and footer.
- Closed the rendering architecture stabilization stage with `GamePanel`, `GameViewRenderer`, and `GameSceneRenderer` now focused on layer orchestration.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 193

- Strengthened `MenuOverlayRenderer` smoke coverage across title, pause, help, settings, level-clear, and game-over overlays.
- Added a gameplay-mode guard proving the menu overlay renderer leaves the frame untouched while the game is actively playing.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 194

- Added direct `HudRenderer` composition smoke coverage for the full HUD panel, stats, phase meter, rewind charges, and message area.
- Verified both default hint rendering and active message rendering without exposing new production APIs.
- Rebuilt the release jar and reran the jar-contained smoke test after fixing the test to use existing reflection helpers for private message state.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 195

- Added direct `EffectRenderer` composition smoke coverage for phase burst, rift warp, rewind wave, enemy stun, and screen shake.
- Verified the effect layer facade can paint the combined effect pass safely on an offscreen image.
- Rebuilt the release jar and reran the jar-contained smoke test after adding the missing `ActiveEffect` test import.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 196

- Added `RenderQuality` to centralize Swing `Graphics2D` antialiasing setup.
- Updated `GamePanel` and the board static-cache renderer to use the shared quality helper instead of setting rendering hints inline.
- Added smoke coverage proving the shared render-quality helper enables shape and text antialiasing.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 197

- Added `GamePanelRenderer` to own the `Graphics2D` copy, render-quality setup, view-renderer invocation, and graphics disposal for panel paints.
- Slimmed `GamePanel.paintComponent()` so the Swing component now delegates rendering entry details to the new renderer.
- Added direct smoke coverage for `GamePanelRenderer`, including nonblank offscreen rendering and board-cache reuse across paints.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 198

- Added `GameLoopController` to own render-frame advancement, cadence-based logic updates, effect-layer advancement, and menu-button layout refresh.
- Slimmed `GamePanel` so its timer callback now delegates loop advancement and only requests repaint afterward.
- Added smoke coverage for `GameLoopController`, including frame advancement, logic cadence, and paused-menu layout refresh.
- Rebuilt the release jar and reran the jar-contained smoke test after correcting the test expectation for empty gameplay-mode menu buttons.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 199

- Added `GamePanelInputBinder` to centralize keyboard input and menu mouse-controller installation for the Swing panel.
- Slimmed `GamePanel` so constructor input wiring delegates to the new binder while keeping existing focus and repaint callbacks.
- Added smoke coverage proving the binder installs keyboard actions, mouse listeners, and repaint routing without triggering menu focus from keyboard actions.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 200

- Strengthened the `GamePanel` offscreen smoke test into a panel integration guard.
- Verified the panel keeps focusability, Swing double buffering, keyboard bindings, mouse listeners, loop startup/shutdown, and nonblank offscreen rendering after the renderer/input/loop extraction work.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 201

- Added `GamePanelConfigurator` to centralize basic Swing panel setup for focusability, double buffering, and background color.
- Slimmed `GamePanel` construction so it delegates static component configuration before binding input and starting the timer.
- Added smoke coverage proving the configurator applies the expected focus, buffering, and background settings.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 202

- Added `GamePanelTimer` to wrap Swing `Timer` creation, startup, shutdown, and running-state queries for the panel loop.
- Slimmed `GamePanel` so it no longer imports or constructs Swing `Timer` directly.
- Added smoke coverage proving the timer wrapper reports stopped, running, and stopped states correctly.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 203

- Added `BoardTileRenderer` to own floor, wall, exit, and animated rift tile drawing details.
- Slimmed `BoardRenderer` so it keeps board metrics, static-cache construction, frame drawing, and rift traversal while delegating individual tile painting.
- Added smoke coverage for the tile renderer across floor, wall, exit, and rift tile types.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 204

- Added `BoardMetricsCalculator` to own board layout sizing, tile-size clamping, and horizontal centering.
- Slimmed `BoardRenderer.computeMetrics()` so the board renderer delegates metrics calculation while preserving its existing public facade.
- Added smoke coverage for minimum tile size, maximum tile size, scaled board dimensions, and horizontal centering.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 205

- Added `BoardFrameRenderer` to own board frame chrome colors, padding, radius, fill, and stroke drawing.
- Slimmed `BoardRenderer.paintFrame()` so the board renderer keeps its public facade while delegating frame drawing details.
- Added smoke coverage proving the frame renderer draws visible board chrome on an offscreen image.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 206

- Added `BoardStaticCacheRenderer` to own static board-cache image creation, render-quality setup, and floor/wall cache tile traversal.
- Slimmed `BoardRenderer.buildStaticCache()` so the board renderer keeps its public facade while delegating cache construction details.
- Added smoke coverage proving the static cache renderer matches metrics dimensions and draws visible static board tiles.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 207

- Added `BoardRiftRenderer` to own dynamic rift tile traversal and animated rift painting.
- Slimmed `BoardRenderer.paintRifts()` so the board renderer keeps its public facade while delegating dynamic rift details.
- Added smoke coverage proving the rift renderer draws visible dynamic rift tiles on an offscreen image.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 208

- Added `EntityRenderOrder` to make gameplay entity layer ordering explicit.
- Updated `EntityLayerRenderer` to iterate the ordered layer list before dispatching pickups, exit, enemies, and player drawing.
- Added smoke coverage proving the entity order remains pickups, exit, enemies, then player.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 209

- Added `HudLayout` to centralize HUD composition coordinates for the shared baseline, phase meter, and rewind charge display.
- Updated `HudRenderer` to use the layout object instead of carrying those positioning constants directly.
- Added smoke coverage proving the standard HUD layout preserves the existing baseline, phase meter, and rewind charge positions.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 210

- Added `MenuOverlayMode` to centralize mapping from `GameMode` to visible menu overlay types.
- Updated `MenuOverlayRenderer` so it asks the mapping helper whether a mode should draw an overlay before dispatching to `MenuRenderer`.
- Added smoke coverage proving all menu modes map to the expected overlay and `PLAYING` maps to no overlay.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 211

- Added `MenuOverlayDispatcher` to own dispatch from `MenuOverlayMode` to the concrete `MenuRenderer` paint method.
- Slimmed `MenuOverlayRenderer` so it now only resolves the current overlay mode and delegates actual menu drawing.
- Added smoke coverage proving the dispatcher draws all visible menu overlays.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 212

- Added `EffectRenderDispatcher` to own dispatch from `VisualEffectType` to ring and stun effect drawing.
- Slimmed `EffectRenderer.paint()` so it now iterates active effects, computes progress/positions, and delegates type-specific drawing.
- Added smoke coverage proving the dispatcher renders phase burst, rift warp, rewind wave, and enemy stun effects.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 213

- Added `ScreenShakeRenderer` to own shake-eligible effect detection, shake strength calculation, and `Graphics2D` translation.
- Slimmed `EffectRenderer.applyScreenShake()` so it delegates screen-shake behavior while keeping the existing facade.
- Added smoke coverage proving non-shaking effects leave the transform unchanged and rift/rewind effects translate the graphics context.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 214

- Added `EffectProgress` to centralize visual-effect lifetime progress calculation and clamping.
- Slimmed `EffectRenderer.paint()` so it delegates progress calculation before dispatching type-specific drawing.
- Added smoke coverage for negative-age, starting, mid-lifetime, and expired progress values.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 215

- Added `ActiveEffectList` to own active visual-effect storage, oldest-first capacity trimming, immutable views, and expired-effect pruning.
- Slimmed `EffectLayer` so it converts visual events into active effects and delegates list management details.
- Added smoke coverage for effect-list capacity trimming, oldest-effect removal, and expiry pruning.
- Rebuilt the release jar and reran the jar-contained smoke test after correcting the expiry test frame to outlive the newest retained effect.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 216

- Added `ActiveEffectFactory` to own conversion from model-layer `VisualEffectEvent` values into UI-layer `ActiveEffect` instances.
- Slimmed `EffectLayer` so it delegates event-to-active-effect creation before adding effects to the active list.
- Added smoke coverage proving the factory preserves effect type and position while using the current render frame as the active effect start frame.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 217

- Added `HudMessageSelector` to own the HUD rule for showing the active state message while message ticks remain, otherwise falling back to the default hint.
- Slimmed `HudRenderer` so it delegates message selection before painting the HUD message area.
- Added smoke coverage for default-hint selection and active-message selection.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 218

- Added `HudPhaseColorSelector` to own the HUD phase-meter color rule.
- Slimmed `HudRenderer` so it delegates phase-meter color selection instead of checking phase state inline.
- Added smoke coverage proving normal HUD phase color remains cyan and active-phase HUD color switches to pink.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 219

- Added `PlayerVisualStyle` to own player body color and glow alpha selection for normal and active-phase states.
- Slimmed `PlayerRenderer` so it delegates visual style selection before drawing player geometry.
- Added smoke coverage proving normal and phase player styles keep the expected colors and alpha-applied glow colors.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 220

- Added `EnemyVisualStyle` to own enemy body color and glow alpha selection for patrolling and stunned states.
- Slimmed `EnemyRenderer` so it delegates state-based visual style selection before drawing enemy ship geometry.
- Added smoke coverage proving patrolling enemies keep the pink style while stunned enemies switch to a distinct body color with alpha-applied glow.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 221

- Added `PickupShape` and `PickupVisualStyle` to name pickup geometry and color choices for crystals, phase cells, and rewind cells.
- Slimmed `PickupRenderer` so it delegates pickup style selection instead of passing magic shape numbers through the draw path.
- Added smoke coverage proving each pickup type keeps its expected color, geometry role, and visible glow alpha.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 222

- Added `ExitVisualStyle` to own locked/open exit ring color, fill alpha, and lock visibility.
- Slimmed `ExitRenderer` so it delegates exit state styling before drawing the portal rings and lock mark.
- Added smoke coverage proving locked exits show the lock mark, open exits use mint, and both fill colors apply alpha correctly.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 223

- Added `RingEffectStyle` to own ring-effect alpha, radius, stroke width, and derived colors from progress.
- Slimmed `RingEffectRenderer` so it delegates visual formula calculation before drawing the stroke and soft fill.
- Added smoke coverage proving ring effects expand, fade, narrow their stroke, and hide once expired.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 224

- Added `StunEffectStyle` to own stun-effect alpha, orbit radius, dot sizing, dot count, and frame-based dot placement.
- Slimmed `StunEffectRenderer` so it delegates orbit formula calculation before drawing each stun marker.
- Added smoke coverage proving stun markers fade, expand their orbit, keep three dots, and move as render frames advance.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 225

- Added `EffectRenderKind` and `EffectRenderSpec` to name effect rendering modes and centralize type-to-ring-style mapping.
- Slimmed `EffectRenderDispatcher` so it delegates effect color, scale, and renderer-kind selection before dispatching.
- Added smoke coverage proving phase, rift, rewind, and stun effects keep their intended render kinds, colors, and relative scales.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 226

- Added `ScreenShakeRules` and `ScreenShakeOffset` to own shake-trigger selection and frame-based offset calculation.
- Slimmed `ScreenShakeRenderer` so it delegates pure shake math before applying the graphics translation.
- Added smoke coverage proving only rift and rewind effects shake the screen, active shakes produce offsets, and expired shakes stop.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 227

- Added `MenuOverlaySpec` to own the mapping from overlay modes to concrete menu rendering calls.
- Slimmed `MenuOverlayDispatcher` so it delegates overlay paint selection instead of keeping a direct switch over menu modes.
- Added smoke coverage proving the overlay spec mirrors and covers every menu overlay mode.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 228

- Added `MenuOverlayStyle` to own per-overlay translucency settings for title, pause, help, settings, level-clear, and game-over screens.
- Slimmed `MenuRenderer` so it delegates overlay alpha selection instead of carrying a row of inline alpha constants.
- Added smoke coverage proving every overlay style mirrors an overlay mode and keeps a translucent alpha.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 229

- Added `SimpleMenuContentLine` and `SimpleMenuContentSpec` to own the text, color, font, and baseline configuration for pause and settings menu content.
- Slimmed `SimpleMenuContentRenderer` so it iterates content specs instead of hard-coding each title and hint line.
- Added smoke coverage proving pause and settings content keep their expected title/hint lines and vertical ordering.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 230

- Added `ResultMenuContentLine` and `ResultMenuContentSpec` to own result-screen title, summary, rank, and score line configuration.
- Slimmed `ResultMenuContentRenderer` so it iterates result specs instead of hard-coding level-clear and game-over lines inline.
- Added smoke coverage proving level-clear and game-over result specs keep expected line counts, titles, score text, and vertical ordering.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 231

- Added `HelpMenuContentSpec` to own help-menu title text, help lines, colors, dot styling, offsets, and line spacing.
- Slimmed `HelpMenuContentRenderer` so it follows the help content spec instead of carrying inline layout constants and line-loop math.
- Added smoke coverage proving the help spec keeps every help line, configured line spacing, translucent dots, and text-to-dot positioning.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 232

- Added `TitleMenuContentLine` and `TitleMenuContentSpec` to own title-screen text, dynamic stats text, colors, font styles, font sizes, and baseline offsets.
- Slimmed `TitleMenuContentRenderer` so it iterates the title content spec instead of hard-coding title, subtitle, hint, and stats lines inline.
- Added smoke coverage proving the title spec keeps four lines, expected title/subtitle/hint text, stats text, and vertical ordering.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 233

- Added `HudMeterGeometry` to own HUD meter track placement and clamped fill-width calculation.
- Slimmed `HudMeterRenderer` so it delegates meter geometry and value scaling before drawing the label, track, fill, and stroke.
- Added smoke coverage proving HUD meter geometry offsets the track, scales half values, clamps over/under values, and handles zero max safely.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 234

- Added `HudRewindDot` to own rewind-charge dot geometry, filled-state selection, and fill-color choice.
- Slimmed `HudRewindRenderer` so it delegates dot placement and filled/empty styling before drawing each charge indicator.
- Added smoke coverage proving rewind dots keep expected offsets, spacing, size, filled-state behavior, and distinct filled/empty colors.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

## 2026-06-09 - Optimization Loop 235

- Added `HudStatItem` and `HudStatsSpec` to own HUD title/stat text providers, x positions, and font sizing.
- Slimmed `HudStatsRenderer` so it iterates title and stat specs instead of hard-coding each HUD stat string and coordinate inline.
- Added smoke coverage proving HUD stats keep level, score, high-score, and crystal text while preserving left-to-right ordering.
- Rebuilt the release jar and reran the jar-contained smoke test.

Validation:

- `.\build.ps1`
- `java "-Djava.awt.headless=true" -cp dist\StarMaze.jar com.starmaze.StarMazeSmokeTest`

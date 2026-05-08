# JustLeveling Architectury migration context

Branch/worktree state:

- Architectury folder initialized as a git repo.
- Remote `origin` set to `https://github.com/Senior-S/JustLeveling-Fork`.
- Current local branch is `Architecture-1.21.1`.
- Original Fabric project folder was not modified.

What was migrated:

- Copied all Java sources from `JustLeveling/src/main/java` into `common/src/main/java`.
- Copied common assets/data/pack metadata into `common/src/main/resources`.
- Copied `fabric.mod.json` into `fabric/src/main/resources`.
- Copied `justlevelingfork.mixins.json` into `common/src/main/resources`.
- Updated root project metadata from the template example values to `justlevelingfork`.
- Added the Fabric-side dependency coordinates from the original Fabric build.
- Added `.gitignore` for Gradle/build/run/editor outputs.
- Verified `./gradlew.bat :fabric:build --no-daemon --info --stacktrace` succeeds after the initial canceled Gradle cache lock was cleared.
- Verified `./gradlew.bat :neoforge:build --no-daemon --stacktrace` succeeds.
- Removed the stale template `just_leveling_fork.mixins.json`; active metadata uses `justlevelingfork.mixins.json`.
- Split loader entrypoints:
  - Common `JustLevelingFork` is no longer a Fabric `ModInitializer`; it exposes loader-neutral `init()`.
  - Fabric entrypoint is now `com.seniors.justlevelingfork.fabric.JustLevelingForkFabric`.
  - NeoForge entrypoint is now `com.seniors.justlevelingfork.neoforge.JustLevelingForkNeoForge`.
- Moved Fabric-only command, server event, and client tooltip registration classes into the Fabric source set.
- Replaced direct `FabricLoader` usage in common config/mod checks with Architectury `Platform`.
- Replaced Fabric custom registry builders for aptitudes/passives/skills/titles with vanilla `MappedRegistry` instances.
- Removed Fabric environment annotations from FTB Quest integration classes.
- Replaced Fabric networking in `ServerNetworking` with Architectury `NetworkManager`; packet encode/decode tables remain common.
- Added initial NeoForge runtime hooks:
  - Common/network init during NeoForge mod construction.
  - Server started/stopped tracking.
  - Player login config/capability sync.
  - Command registration.
  - Custom player attribute registration.
  - Item/block/entity lock checks for interaction events.
  - Attack effect handling, living death handling, block break treasure handling, and server tick passive/title/drop updates.
- Extracted shared gameplay event logic into `RegistryGameplayEvents` for NeoForge reuse.
- Refactored Fabric server gameplay callbacks so `RegistryFabricEvents` only binds Fabric callbacks and delegates behavior to `RegistryGameplayEvents`.
- Converted vanilla static registrations that broke on NeoForge into Architectury deferred registrations:
  - Items in `RegistryItems`.
  - Custom attributes in `RegistryAttributes`.
  - Sound events in `RegistrySounds`.
  - Command argument types in `RegistryArgumentTypes`.
- Updated passives so custom attributes are resolved lazily instead of during passive class initialization.
- Re-verified `./gradlew.bat :fabric:build :neoforge:build --no-daemon --stacktrace` succeeds after these migration steps.
- Fabric server smoke reached the expected server startup gate/config generation path in dev.
- NeoForge server smoke reaches successful dedicated server startup: `Done (...)! For help, type "help"`.

Minecraft MCP validation:

- `analyze_mixin` was run against Minecraft `1.21.1` with Mojang mappings.
- Result: 15 of 16 mixin classes validated against Minecraft sources.
- `MixTargetFinder` is the only invalid target in the Minecraft-only analysis because it targets Better Combat's `net.bettercombat.client.collision.TargetFinder`, not a Minecraft class. This is expected for an optional mod integration mixin and must be guarded by mixin plugin/conditional config when supporting both loaders.

Important current limitation:

The migrated code now builds for both loaders and reaches server startup on NeoForge, but it still needs in-game parity validation before release:

- NeoForge gameplay hooks are a first pass and need in-game validation against Fabric behavior.
- NeoForge block break treasure handling currently runs from `BlockEvent.BreakEvent`, not a perfect Fabric `AFTER` equivalent.
- FTB Quests integrations still use Fabric-side CurseMaven artifacts.
- Trinkets is guarded to Fabric; NeoForge likely needs Curios support or a disabled-equivalent path.

Recommended next steps:

1. Validate and complete NeoForge hooks:
   - Run NeoForge client/server.
   - Compare lock checks, passive updates, title sync, packet sync, and treasure drops against Fabric.
   - Adjust block break/drop hook timing if needed.

2. Handle mod integrations per platform:
   - Trinkets is Fabric-only; NeoForge likely needs Curios or a disabled integration.
   - Better Combat mixin must be optional and loader-aware.
   - FTB Quests dependencies must be resolved for each loader or isolated to Fabric.
   - TacZ and MIAPI integrations need separate dependency availability checks.

3. Fix metadata and mixin configs:
   - Fabric metadata already points to `justlevelingfork.mixins.json`.
   - NeoForge metadata now points to the same config, but the mixin set likely needs loader-specific filtering.
   - Consider separate common/fabric/neoforge mixin configs if conditional loading is cleaner.

4. Runtime verification target:
   - Build tasks pass for both `:fabric` and `:neoforge`.
   - NeoForge dedicated server startup passes in dev.
   - Before release, run both loaders in-game. A successful server startup does not prove all gameplay hooks and optional integrations behave identically.
   - Only after both loaders launch and core gameplay works should the branch be pushed/released.

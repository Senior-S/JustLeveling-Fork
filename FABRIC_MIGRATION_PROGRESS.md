# Fabric 1.21.1 Migration Progress

Branch: `fabric-1.21.1`

## Current Status

The project has been moved from ForgeGradle / Forge 1.20.1 to Fabric Loom / Fabric 1.21.1 and currently passes:

```powershell
.\gradlew.bat build
```

The buildable state is a migration checkpoint, not a complete functional port. Core Forge-only sources have been removed from the active tree; the remaining work is mostly runtime smoke testing and optional cleanup of compatibility layers.

## Completed

- Replaced ForgeGradle with Fabric Loom.
- Updated Minecraft target to `1.21.1`.
- Added Fabric Loader and Fabric API dependencies.
- Replaced Curios dependency with Trinkets dependency.
- Added `src/main/resources/fabric.mod.json`.
- Removed Forge-specific `mods.toml` from the active loader path by using Fabric metadata.
- Converted main mod entrypoint to `ModInitializer`.
- Converted client entrypoint to `ClientModInitializer`.
- Registered keybind and HUD rendering through Fabric client APIs.
- Converted basic item, sound, attribute, aptitude, passive, skill, and title registration to Fabric/vanilla registry APIs.
- Added lightweight registry reference/view helpers to preserve existing `.get()` style call sites.
- Updated several Minecraft 1.21.1 API changes:
  - `ResourceLocation.fromNamespaceAndPath(...)`
  - holder-based attributes/effects
  - screen render and mouse scroll signatures
  - advancement holder lookup
  - tag creation through `TagKey.create(...)`
- Removed Forge-only active mixins from `justlevelingfork.mixins.json`.
- Removed optional third-party PointBlank gun mixin from the active vanilla Fabric mixin path.
- Replaced some Forge registry lookups with `BuiltInRegistries`.
- Replaced initial Forge capability access with a temporary Fabric-side player data map.
- Added Fabric play custom payload registration for the migrated packet set.
- Replaced `ServerNetworking.sendToServer`, `sendToPlayer`, and `sendToAllClients` no-op stubs with Fabric networking sends.
- Registered clientbound and serverbound packet handlers through Fabric client/server networking APIs while preserving the existing packet class encode/decode/handle methods.
- Restored basic Fabric server lifecycle tracking for broadcast packet sends.
- Added Fabric-side player aptitude persistence through player NBT save/load mixins.
- Restored aptitude data copy when Minecraft replaces a `ServerPlayer` during death/dimension-change flows.
- Restored player join sync for lock-item config, common config, dynamic config, and aptitude data.
- Restored command registration through Fabric command callbacks.
- Restored server-start old config migration through Fabric server lifecycle callbacks.
- Restored initial Fabric item lock checks for item use, block use, entity use, attacking, block breaking, and locked held-item dropping.
- Expanded Fabric lock checks to validate projectile ammo for projectile weapons and drop locked armor from equipped slots.
- Restored locked item inventory movement prevention for armor/offhand placement paths through a targeted `AbstractContainerMenu.clicked` mixin.
- Re-enabled and ported `MixShulkerBullet` to 1.21.1 so Turtle Shield prevents shulker bullet levitation again.
- Re-enabled and ported `MixLivingEntity` to 1.21.1 for Magic Resist, Lion Heart, Alchemy Manipulation, and Stealth Mastery behavior.
- Re-enabled and ported `MixItemStack` to the 1.21.1 enchantment data component tooltip path so Scholar hides enchantment names again.
- Reviewed 1.21.1 enchantment tooltip behavior and preserved `ItemEnchantments.showInTooltip` while hiding names for locked Scholar.
- Re-enabled and ported `MixPlayerRenderer` to the 1.21.1 name tag render signature so selected titles render above player names again.
- Removed stale Gradle source exclusions for re-enabled mixins so `MixLivingEntity`, `MixItemStack`, `MixPlayerRenderer`, and `MixShulkerBullet` are compiled into the Fabric jar.
- Retired `MixForgeGui`; vanilla 1.21.1 `Gui` already renders air bubbles through `FluidTags.WATER` and current HUD sprites, replacing the old ForgeGui-specific override.
- Re-enabled and ported `MixTargetFinder` for Better Combat 2.0.3 on Fabric, using vanilla `Attributes.ENTITY_INTERACTION_RANGE`, Fabric mod detection, and the 1.21 `AttributeModifier` accessors.
- Restored locked item pickup prevention through a targeted `ItemEntity.playerTouch` mixin.
- Restored `<droppable>` lock-item parsing and runtime handling so explicitly droppable locked items are forced out of hands/armor even when global locked-item dropping is disabled.
- Restored Convergence crafting bonus drops through a targeted `ResultSlot.onTake` mixin.
- Restored Treasure Hunter dirt-block bonus drops through Fabric block break callbacks.
- Restored Treasure Hunter configured custom NBT as 1.21.1 `CUSTOM_DATA` item data components.
- Restored Treasure Hunter legacy `Damage` item data as the 1.21.1 `minecraft:damage` component while preserving other unmapped legacy NBT as `CUSTOM_DATA`.
- Restored player head profile rendering data for the inventory tab through the 1.21.1 `minecraft:profile` item component.
- Restored Fabric server tick maintenance for passive attributes, One Handed, Diamond Skin, Cat Eyes, and counter-attack timeout cleanup.
- Restored Limit Breaker attack bonus damage and sound through Fabric attack callbacks.
- Restored kill/death skill effects through Fabric living-entity death events for Fighting Spirit, Life Eater, and Lucky Drop.
- Restored arrow hit projectile damage, Stealth Mastery ranged damage, and Quick Reposition hit effects through a targeted `AbstractArrow.onHitEntity` mixin.
- Ported item aptitude requirement tooltips to Fabric `ItemTooltipCallback`.
- Replaced the excluded Forge/Curios handler with `HandlerTrinkets` and restored active compilation for it.
- Restored server-side locked Trinkets enforcement after equip and during server tick equipment checks.
- Updated user-facing dependency docs from Curios to Trinkets.
- Validated mixin sources against Minecraft 1.21.1 with the Minecraft dev MCP; active mixins are valid, while disabled Forge/optional integration mixins still need later target ports.
- Replaced the temporary `WeakHashMap<Player, AptitudeCapability>` runtime access layer with an attached player interface implemented by the existing `Player` mixin.
- Kept aptitude data persistence on player NBT using the attached player capability instance.
- Added aptitude capability broadcast sync after `/aptitudesreload`.
- Revalidated the updated `MixPlayer` save/load injections against Minecraft 1.21.1 with the Minecraft dev MCP.
- Restored Break Speed passive and Obsidian Smasher mining speed behavior through the existing `Player.getDestroySpeed` mixin.
- Revalidated the updated `MixPlayer` mining/save/load injections against Minecraft 1.21.1 with the Minecraft dev MCP.
- Restored Counter Attack activation when a server player is hurt by a living attacker through the existing `LivingEntity.hurt` mixin.
- Restored Safe Port ender pearl self-damage prevention through a targeted `ThrownEnderpearl.onHit` mixin.
- Validated the updated `MixLivingEntity` and new `MixThrownEnderpearl` sources against Minecraft 1.21.1 with the Minecraft dev MCP.
- Restored critical-hit damage behavior for the Critical Damage passive, Berserker forced criticals, and Critical Roll messages/modifiers through the existing `Player.attack` mixin.
- Revalidated the updated `MixPlayer` mining, critical-hit, and save/load injections against Minecraft 1.21.1 with the Minecraft dev MCP.
- Removed the old modular-tool integration implementation and compile-only dependencies; module-tier item locks are no longer a migration target.
- Removed stale Forge `META-INF/mods.toml` metadata now that Fabric uses `fabric.mod.json`.
- Restored `displayTitlesAsPrefix` behavior through the existing `Player.getDisplayName` mixin.
- Revalidated the updated `MixPlayer` display-name, mining, critical-hit, and save/load injections against Minecraft 1.21.1 with the Minecraft dev MCP.
- Replaced the old TacZ compile-only dependency with TaCZ: Refabricated (`curse.maven:tacz-refabricated-1334246:7770600`) and migrated the integration mod-loaded check to Fabric Loader.
- Removed MrCrayfish gun mod, Scorched Guns, Iron's Spells, and L2 Tabs integration sources/dependencies from the active migration target.
- Removed the inactive PointBlank mixin source and compile-only dependency; PointBlank is no longer an active Fabric migration target.
- Removed KubeJS integration sources, plugin metadata, dependency wiring, and aptitude level-up cancellation hook from the active migration target.
- Removed the L2 Tabs inventory-screen guard so JustLeveling's own Fabric inventory tabs always render through the existing mixin.
- Validated TaCZ: Refabricated metadata with the Minecraft dev MCP; the jar is Fabric, mod id `tacz`, and targets Minecraft `1.21.1`.
- Restored TaCZ: Refabricated gun-fire lock handling through the Fabric `GunFireEvent.CALLBACK` API, guarded behind Fabric mod detection.
- Validated the TaCZ gun-fire callback surface against the decompiled TaCZ: Refabricated jar with the Minecraft dev MCP.
- Extended Treasure Hunter configured drops to accept native 1.21.1 item component syntax through vanilla `ItemParser`, while preserving legacy `{Damage:...}` config compatibility.
- Validated the 1.21.1 `ItemParser` / `ItemInput` component parsing path against Minecraft source with the Minecraft dev MCP.
- Restored server-tick title requirement/custom-name sync through the Fabric server tick event bridge.
- Removed stale Forge-only excluded sources: `LazyAptitudeCapability`, `RegistryCapabilities`, `RegistryCommonEvents`, and `MixForgeGui`.
- Removed the Gradle source exclusions that only existed for those deleted Forge-only files.
- Fixed the migrated counter-attack packet handler shape so it works with the current Fabric networking bridge.
- Restored Counter Attack activation and consumption fully server-side through the Fabric hurt/attack paths, with capability sync after state changes.
- Removed the stale clientbound `counter_attack` registration; Counter Attack no longer depends on a server-to-client packet to mutate server state.
- Replaced deprecated update-check URL construction in `JustLevelingFork` with `URI.create(...).toURL()`.
- Fixed the 1.21.1 `CraftingMenu.slotChangedCraftingGrid` mixin descriptor so Convergence crafting bonus handling applies against the current vanilla method signature.
- Validated the updated `CraftingMenu` injection target against Minecraft 1.21.1 source with the Minecraft dev MCP.
- Fixed `pack.mcmeta` for Fabric / Minecraft 1.21.1 by replacing stale Forge metadata with valid resource/data pack format metadata.
- Validated Minecraft 1.21.1 resource/data pack format constants against Minecraft source with the Minecraft dev MCP.
- Server smoke test reaches Minecraft 1.21.1 startup completion (`Done`) with the migrated Fabric mod set.
- Clean-world server smoke test reaches Minecraft 1.21.1 startup completion (`Done`) without the stale Forge/Caelus saved-world attribute warnings.
- Fabric client smoke test reaches resource-manager startup with the migrated Fabric mod set and no crash before shutdown.
- Revalidated the updated `MixCraftingMenu` source with the Minecraft dev MCP after runtime smoke testing.
- Replaced the remaining active client option use of ForgeConfigSpec with a Fabric-side JSON client config while preserving existing UI `.get()` / `.set()` call sites.
- Removed unreachable ForgeConfigSpec passive value handling now that passive config values are plain Fabric/YACL numbers.
- Validated the inventory screen tab mixin against Minecraft 1.21.1 with the Minecraft dev MCP before continuing interactive client migration work.
- Re-ran client startup smoke after the client config migration; it reached resource-manager startup and generated `justleveling-fork.client.json` with expected defaults.
- Removed the remaining legacy ForgeConfigSpec common-config source and old Forge TOML migration path.
- Removed the stale local `logSpellIds` generated config entry from the dev run config.
- Replaced the reflection-based packet compatibility bridge with an explicit `JustLevelingPacket` interface, typed packet decoders, and direct Fabric client/server thread dispatch.
- Re-ran cleanup validation: full build passes, server reaches `Done`, and client reaches resource-manager startup.
- Fixed the production mixin refmap filename mismatch by pointing `justlevelingfork.mixins.json` at Loom's generated `justlevelingfork-refmap.json`.
- Revalidated `MixPlayer` against Minecraft 1.21.1 with the Minecraft dev MCP after the production refmap crash report.
- Declared YACL (`yet_another_config_lib_v3`) and Cloth Config as required Fabric dependencies because config handlers are loaded during the main entrypoint.
- Registered custom Brigadier argument serializers for `AptitudeArgument` and `TitleArgument` through Fabric's `ArgumentTypeRegistry` so command tree sync does not disconnect players with `Invalid player data`.
- Removed the aptitude screen's manual Minecraft blur pass so the non-pausing GUI opens with only its transparent overlay and custom UI.
- Kept explicit framebuffer rebind and nearest-neighbor filtering for aptitude GUI textures.
- Re-ran validation after the aptitude GUI blur fix: full build passes, client reaches resource-manager startup, and server reaches `Done`.

## Temporarily Stubbed Or Disabled

These areas compile but are not fully ported yet:

- Packet classes now share an explicit `JustLevelingPacket` interface and direct handler dispatch; they still serialize through the existing byte-buffer payload format.
- Player aptitude data now uses an attached player interface and persists through player NBT.
- Common Fabric event bridges and targeted mixins are active for the migrated core event surface.
- Some legacy NBT/data component behavior may still need follow-up if new cases are found during smoke testing.

## Remaining Work

### Networking

- Smoke test client-to-server aptitude/passive/title packets in an interactive Fabric client.
- Smoke test server-to-client config, aptitude sync, title overlay, aptitude overlay, and player message packets.
- Consider replacing the compatibility bridge with explicit typed Fabric payload records after higher-risk systems are restored.

### Player Data

- Smoke test player data persistence across logout/login.
- Smoke test clone/death/dimension-change persistence behavior.
- Smoke test aptitude data sync after `/aptitudesreload`.

### Trinkets

- Smoke test Trinkets equip attempts using aptitude item restrictions in a Fabric client.
- Smoke test equipped Trinkets recheck when player game mode or aptitude data changes.

### Events

- Smoke test aptitude requirement tooltip rendering in a Fabric client.
- Smoke test restored Fabric command registration in a running world.
- Smoke test restored server lifecycle config migration.
- Smoke test restored pickup, drop, equipment, projectile, `<droppable>`, and Convergence crafting behavior.
- Smoke test restored inventory movement prevention for locked armor/offhand placement.
- Smoke test restored Treasure Hunter drops, including configured custom data.
- Smoke test restored passive attributes/effects and counter-attack timeout behavior.
- Smoke test restored per-tick title requirement and custom-name sync.
- Smoke test restored Counter Attack activation when hurt by a living attacker and consumption on the next attack.
- Smoke test restored Break Speed passive and Obsidian Smasher mining behavior.
- Smoke test restored Safe Port ender pearl self-damage prevention.
- Smoke test restored Critical Damage passive, Berserker forced criticals, and Critical Roll overlays/damage modifiers.
- Smoke test restored Limit Breaker combat behavior.
- Smoke test restored Fighting Spirit, Life Eater, and Lucky Drop kill behavior.
- Smoke test restored projectile damage, Stealth Mastery ranged damage, and Quick Reposition behavior.
- Expand restored item lock checks for optional integration-specific IDs such as gun items.

### Mixins

- Smoke test Better Combat entity reach passive behavior.
- Smoke test Turtle Shield behavior against shulker bullet levitation.
- Smoke test Magic Resist, Lion Heart, Alchemy Manipulation, and Stealth Mastery behavior.
- Smoke test Scholar enchantment tooltip hiding.
- Smoke test player title rendering above name tags in a Fabric client.
- Smoke test title prefix formatting in chat, player lists, and command display names when `displayTitlesAsPrefix` is enabled.

### Integrations

- Smoke test TaCZ: Refabricated gun-fire lock handling in a Fabric client with TaCZ installed.

### 1.21.1 Data Components

- Smoke test Treasure Hunter configured drops using native item component syntax such as `minecraft:wooden_pickaxe[minecraft:damage=59]`.
- Continue replacing any newly found legacy item stack NBT logic with 1.21.1 data component APIs.

### Validation

- Run `.\gradlew.bat build` after each restored subsystem.
- Run a Fabric client and verify:
  - keybind opens aptitude screen
  - registries initialize
  - configs load
  - aptitude leveling works
  - passives apply attributes
  - titles render/sync
  - item locks work
  - Trinkets item restrictions work
- Run server smoke test after networking and persistence are restored.

## Known Build Notes

- `.\gradlew.bat build` currently passes.
- Current compile output is clean.
- `.\gradlew.bat runServer --args nogui` reached `Done` in the current dev run after fixing the runtime blockers.
- Current server smoke logs still warn about unknown legacy attributes such as `forge:entity_gravity`, `forge:step_height_addition`, `forge:swim_speed`, and `caelus:fall_flying`; these appear to come from stale saved-world/mod data in `run/world`, not from a startup crash.
- A clean-world server smoke run also reached `Done` and did not reproduce those legacy attribute warnings.
- `.\gradlew.bat runClient` reached client resource-manager startup without a crash; deeper interactive UI/network testing is still pending.
- Client config now writes to `config/JLFork/justleveling-fork.client.json` during Fabric client initialization.
- Source search no longer finds active `ForgeConfigSpec`, `net.minecraftforge`, `FabricNetworkContext`, `usingNewConfig`, or `logSpellIds` references.
- Cleanup smoke logs no longer report unknown YACL config fields from `usingNewConfig` or `logSpellIds`.
- The remapped jar now contains `justlevelingfork-refmap.json`, and `justlevelingfork.mixins.json` references that exact file name.
- The packaged `fabric.mod.json` now requires YACL and Cloth Config instead of listing YACL as optional.
- Custom command argument types are registered before command registration to support vanilla command tree serialization during login.
- The aptitude screen no longer calls full `Screen.renderBackground(...)`; it calls `renderBlurredBackground(...)` directly before drawing the custom UI.
- Aptitude UI rendering now flushes before/after `renderBlurredBackground(...)`, rebinds the main render target, resets shader color, and disables linear filtering on custom page/background textures.

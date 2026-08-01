# JustLeveling Fork

JustLeveling Fork is a community-maintained fork of [JustLeveling](https://www.curseforge.com/minecraft/mc-mods/just-leveling), providing configurable aptitudes, skills, passives, titles, and item requirements.

## Supported version and loaders

Release `1.2.9` supports **Minecraft Java Edition 1.21.1** on:

- Fabric
- NeoForge

It does not support Minecraft 1.21.11. Download the jar matching both your loader and Minecraft 1.21.1.

## Installation

Install JustLeveling Fork and its required dependencies for the loader you use.

### Fabric

- Fabric Loader `0.16.14` or newer
- [Fabric API](https://modrinth.com/mod/fabric-api)
- [Architectury API](https://modrinth.com/mod/architectury-api) `13.0.8` or newer
- [Trinkets](https://modrinth.com/mod/trinkets) `3.10.0` or newer
- [YetAnotherConfigLib v3](https://modrinth.com/mod/yacl) `3.6.6` or newer
- [Cloth Config API](https://modrinth.com/mod/cloth-config)

### NeoForge

- NeoForge `21.1.215` or a compatible `21.1.x` release for Minecraft 1.21.1
- [Architectury API](https://modrinth.com/mod/architectury-api) `13.0.8` or newer
- [YetAnotherConfigLib v3](https://modrinth.com/mod/yacl) `3.6.6` or newer
- [Cloth Config API](https://modrinth.com/mod/cloth-config) `15.0.140` or newer

Optional integrations are available for FTB Quests and Questlog when their compatible loader-specific versions are installed. Better Combat integration is available on Fabric and uses Better Combat's Player Animator dependency.

Downloads are published on [CurseForge](https://www.curseforge.com/minecraft/mc-mods/justleveling-fork) and [Modrinth](https://modrinth.com/mod/justleveling-fork).

## Configuration

The mod writes its configuration under the instance's `config/JLFork` directory. Existing `justleveling-fork.common.json5` and `justleveling-fork.lockItems.json5` files are migrated into that directory on startup. Back up server configuration before upgrading a production instance.

## Building

Use a 64-bit JDK 21. Build both distribution jars with:

```powershell
.\gradlew.bat clean :fabric:build :neoforge:build
```

The release jars are created at:

- `fabric/build/libs/justlevelingfork-fabric-<version>.jar`
- `neoforge/build/libs/justlevelingfork-neoforge-<version>.jar`

## Releasing

The GitHub Actions **Release** workflow accepts a mod version, release channel, changelog, and a dry-run option. A dry run builds and verifies both jars without publishing. A publish run requires the `MODRINTH_TOKEN` and `CURSEFORGE_TOKEN` secrets plus the matching project-ID repository variables.

Before starting a release, keep `gradle.properties` (`mod_version`) and `VERSION` synchronized, run the build command above, and smoke-test both loaders in client and dedicated-server environments with their required dependencies installed.

## Contributing

Report issues or open pull requests at [Senior-S/JustLeveling-Fork](https://github.com/Senior-S/JustLeveling-Fork). The original project was created by DPlayend; this fork exists to maintain and improve it with community contributions.

## License

This repository is licensed under the [Apache License 2.0](LICENSE).

# Release Automation

This project publishes release builds through the GitHub Actions workflow in
`.github/workflows/release.yml`.

## One-time GitHub Setup

Create these repository secrets:

- `MODRINTH_TOKEN`: Modrinth personal access token with version upload permission.
- `CURSEFORGE_TOKEN`: CurseForge API token with upload permission.

Create these repository variables:

- `MODRINTH_PROJECT_ID`: the Modrinth project slug or ID.
- `CURSEFORGE_PROJECT_ID`: the numeric CurseForge project ID.

The workflow uses the built-in `GITHUB_TOKEN` for GitHub releases, so no extra
GitHub secret is needed.

## Release Flow

1. Finish and test the feature set locally.
2. Update `mod_version` in `gradle.properties` if you want the repository to
   carry the new version after the release.
3. Push the release commit to GitHub.
4. Open GitHub Actions, select `Release`, then `Run workflow`.
5. Enter:
   - `version`: for example `1.0.1`
   - `release_type`: `release`, `beta`, or `alpha`
   - `changelog`: public release notes
   - `dry_run`: `true` for build-only validation, `false` to publish

Run once with `dry_run=true` before publishing. It builds both loader jars and
uploads them as a workflow artifact without touching Modrinth, CurseForge, or
GitHub Releases.

## Published Files

The workflow publishes:

- `fabric/build/libs/justlevelingfork-fabric-<version>.jar`
- `neoforge/build/libs/justlevelingfork-neoforge-<version>.jar`

Modrinth receives both files under one version tagged for Fabric and NeoForge.
CurseForge receives separate Fabric and NeoForge file uploads so each file is
tagged with the correct loader.

## Local Version Override

CI builds with:

```bash
./gradlew clean :fabric:build :neoforge:build -Pmod_version="<version>"
```

On PowerShell, quote the whole Gradle property:

```powershell
.\gradlew.bat clean :fabric:build :neoforge:build "-Pmod_version=<version>"
```

That means the workflow can publish a version supplied from the GitHub Actions
form even if `gradle.properties` has not been updated yet. Keeping
`gradle.properties` in sync is still recommended.

# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/) and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [Unreleased]

---

## [0.1.2] - 2026-04-05

### Added

* RCNA Core now automatically installs default `InGameInfo.xml` and `ingameinfoxml.cfg` into the config folder on first launch if they do not already exist. This ensures the HUD layout and scale/offset settings are applied out of the box without manual setup.

### Fixed

* InGame Info XML config files were not being shipped with the mod, requiring players to manually configure the HUD layout.

---

## [0.1.1] - 2026-04-05

### Added

* InGame Info XML custom tag providers:
  * `weathertime` — ticks until next weather change, formatted as M:SS (singleplayer only).
  * `chunkcount` — number of chunks currently loaded on the client.
  * `issnowing` — returns `true` if it is raining and the player's position would produce snow based on biome temperature, correctly detecting snow biomes (e.g. Extreme Hills) instead of using a temperature threshold.
  * `tcvis`, `tcflux`, `tcwarp`, `tcwarptemp`, `tcwarpperm` — Thaumcraft aura and warp tags.
  * `bmlp`, `bmlpmax`, `bmtier` — Blood Magic soul network LP and tier tags.
  * `season`, `seasonsub` — Serene Seasons current season and sub-season tags.
  * `tantemp`, `tanthirst`, `tanhydration` — Tough as Nails body temperature, thirst, and hydration tags.
  * `gtorchunk` — returns `true` if the current chunk is the center of a GregTech CEu ore vein grid with ores present.
  * `gtdeadvein` — returns `true` if the current chunk is a GT ore vein grid center with no ores spawned. Only triggers at the center of large ore vein grids (3x3 chunk grid), matching the same veins visible in the GregTech Ore Prospector. Unlike VisualOres which marks individual ore blocks regardless of vein type, this detection is specific to the GT large vein system — it identifies grid centers where a vein was supposed to generate but did not spawn any ores.
  * `gtvoltage` — returns the highest GregTech voltage tier name found in the player's inventory.
* Thaumcraft integration for InGame Info XML — aura vis, flux, and warp tracking.
* Blood Magic integration for InGame Info XML — soul network LP, max LP, and orb tier.
* Serene Seasons integration for InGame Info XML — current season and sub-season display.
* Tough as Nails integration for InGame Info XML — body temperature, thirst level, and hydration.
* GregTech CEu integration for InGame Info XML — ore chunk detection, dead vein detection, and voltage tier (requires CodeChickenLib).
* Full HUD layout for top-left (info) and bottom-right (active potion effects).

### Changed

* HUD layout reorganized — weather moved below biome line, moon phase merged into weather line, XYZ/chunk/entities merged into one line.
* All HUD labels are gold, values are white or color-coded, separators are dark gray.
* Weather state now correctly detects snow vs rain using biome data rather than a temperature threshold.
* Lunar cycle phase, temperatures, biome, season, thirst, and GT voltage tier are all color-coded.

---

## [0.1.0] - 2026-04-01

### Added

* Custom Discord IPC client (`DiscordIPCClient`) — communicates directly with the Discord desktop client over a named pipe without any external libraries.
* Discord RPC manager (`DiscordRPCManager`) — handles connection, presence updates, and shutdown on a dedicated background thread.
* Discord RPC event handler (`RPCHandler`) — tracks game state and updates presence for:
  * Main menu, world selection, server selection, and settings screens.
  * In-game singleplayer and multiplayer sessions.
  * Current dimension, biome, health, world/server name, and gamemode.
* Forge config system for Discord settings (`appId`, `largeImageKey`, `largeImageText`, `enabled`).
* GitHub issue templates (Bug Report, Feature Request, General Issue).
* Updated README to reflect RCNA Core's purpose, features, and project structure.

### Changed

* Project converted from RCNA 1.12.2 development template to **RCNA Core** — the central mod for the RunicCraft: New Ascension modpack.
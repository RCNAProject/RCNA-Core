# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/) and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [Unreleased]

### Added

* Custom Discord IPC client (`DiscordIPCClient`) — communicates directly with the Discord desktop client over a named pipe without any external libraries.
* Discord RPC manager (`DiscordRPCManager`) — handles connection, presence updates, and shutdown on a dedicated background thread.
* Discord RPC event handler (`RPCHandler`) — tracks game state and updates presence for:
  * Main menu, world selection, server selection, and settings screens
  * In-game singleplayer and multiplayer sessions
  * Current dimension, biome, health, world/server name, and gamemode
* GitHub issue templates (Bug Report, Feature Request, General Issue).
* Updated README to reflect RCNA Core's purpose, features, and project structure.

### Changed

* Project converted from RCNA 1.12.2 development template to **RCNA Core** — the central mod for the RunicCraft: New Ascension modpack.

---

## [1.0.0] - 2026-04-01

### Added

* CleanroomMC 1.12.2 development environment.
* Gradle configuration for modern 1.12.2 mod development.
* Default project structure for RCNA mods.
* RetroFuturaGradle, Forge 1.12.2, Mixins, and Coremods support.

### Changed

* Project renamed to `rcna-1.12.2-template`.
* Updated README for RCNA development usage.

### Removed

* Unnecessary example assets from the original Cleanroom template.
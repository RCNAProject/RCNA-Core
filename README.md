
<p align="center">
  <img src="docs/RCNAProject.png" width="200">
</p>
<h1 align="center">RCNA Core</h1>
<p align="center">
Core mod for the <b>RunicCraft: New Ascension</b> modpack.
</p>
<p align="center">

![Version](https://img.shields.io/badge/Version-0.1.0-blue)
![Minecraft](https://img.shields.io/badge/Minecraft-1.12.2-brightgreen)
![Forge](https://img.shields.io/badge/Forge-14.23.5.2847-orange)
![Java](https://img.shields.io/badge/Java-25-blue)
![License](https://img.shields.io/badge/License-MIT-green)

</p>

---

# Overview

**RCNA Core** is the central mod for the RunicCraft: New Ascension modpack.

It centralizes pack-specific functionality that would otherwise be scattered across configs, scripts, or multiple small mods — ensuring consistent behavior and compatibility across all modpack components.

---

# Features

### Discord Rich Presence
Custom Discord IPC implementation that displays real-time game state in Discord:
- Main menu navigation (Main Menu, Selecting a World, Selecting a Server, Settings)
- In-game status (Singleplayer / Multiplayer, current dimension, biome, health, world/server name)
- In-game settings detection
- Gamemode indicator (Survival, Creative, Adventure, Spectator)

### Pack-Specific Tweaks
Tweaks and adjustments tailored to the RCNA modpack experience. *(Expanded as the mod list is finalized.)*

### Mod Integrations
Integrations between mods in the pack to ensure consistent and compatible behavior. *(Expanded alongside pack development.)*

### Forked Mod Support
Internal systems for managing and supporting forked versions of mods when modifications are required for the pack. *(In development.)*

---

# Toolchain

| Component         | Version      |
| ----------------- | ------------ |
| Minecraft         | 1.12.2       |
| Forge             | 14.23.5.2847 |
| Java              | 25           |
| Gradle            | 9.2.1        |
| RetroFuturaGradle | 2.0.2        |

---

# Development Workflow

### Run the Minecraft client

```
gradlew runClient
```

### Run the dedicated server

```
gradlew runServer
```

### Build the mod jar

```
gradlew build
```

The compiled mod will be located in:

```
build/libs/
```

---

# Project Structure

```
src/
 └ main/
    ├ java/
    │  └ net/zaltren/rcna
    │     ├ RCNACoreMod.java
    │     └ client/
    │        └ discord/
    │           ├ DiscordIPCClient.java
    │           ├ DiscordRPCManager.java
    │           └ RPCHandler.java
    └ resources/
       ├ assets/
       ├ mixins.rcna.json
       └ rcna.info
```

---

# Notes

Dependency configuration:

```
gradle/scripts/dependencies.gradle
```

Publishing configuration:

```
gradle/scripts/publishing.gradle
```

For mixin development in IntelliJ it is recommended to use:

https://github.com/eigenraven/MinecraftDev/releases

---

# Credits

Built on the CleanroomMC development template. Special thanks to the CleanroomMC team for modernizing the Minecraft 1.12.2 development environment.

---

# License

Licensed under the **MIT License**.

# -------------------TODO-----------------------
1. [x] !*Felder Managment System
2. [x] !Bank Befehl
3. [x] !*Setup Befehl
    1. [x] !Main Settings
   	2. [x] !* Fields
        [x] !Railway
        [x] !Haus
    3. [x] >*!Minigames
4. [x] Würfel Befehl imolement dam usage 
5. [x] Move Befehl
6. [x] Player Selector
7. [x] Scoreboard
8. [ ] > Event Handler

---

# Notes
- `*` = IMPORTANT
- `+` = MISSING
- `-` = INCOMPLETE
- `!` = TO BE TESTED
- `>` = Working on

---

Main Settings >
  - Self running
  - Anzahl Spieler
  - (Gespielte Male)
  - End Of Thinking

  
---
# Important things

- [ ] Messages!
- [ ] Delays
- [x] import MAEDN dice or MarioParty dice
- [ ] Formatting Unicode
- [ ] Field Manager beatufication -> Current player and Owner, Status
- [x] Player transactions moving is wrong, scoreboard and message
- [ ] Felder Manager
# Things must be fixed

- [ ] Infinity field loop

- [ ] 3xPay tax

- [ ] direct pay scoreboard

- [ ] Normal Field special (Neue Straße)

- [ ] 'org.bukkit.event.EventException: null
        at org.bukkit.plugin.java.JavaPluginLoader$1.execute(JavaPluginLoader.java:306) ~[spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at org.bukkit.plugin.RegisteredListener.callEvent(RegisteredListener.java:62) ~[spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at org.bukkit.plugin.SimplePluginManager.fireEvent(SimplePluginManager.java:500) [spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at org.bukkit.plugin.SimplePluginManager.callEvent(SimplePluginManager.java:485) [spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at net.minecraft.server.v1_12_R1.PlayerConnection.a(PlayerConnection.java:1889) [spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at net.minecraft.server.v1_12_R1.PacketPlayInWindowClick.a(SourceFile:33) [spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at net.minecraft.server.v1_12_R1.PacketPlayInWindowClick.a(SourceFile:10) [spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at net.minecraft.server.v1_12_R1.PlayerConnectionUtils$1.run(SourceFile:13) [spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at java.util.concurrent.Executors$RunnableAdapter.call(Unknown Source) [?:1.8.0_181]
        at java.util.concurrent.FutureTask.run(Unknown Source) [?:1.8.0_181]
        at net.minecraft.server.v1_12_R1.SystemUtils.a(SourceFile:46) [spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at net.minecraft.server.v1_12_R1.MinecraftServer.D(MinecraftServer.java:748) [spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at net.minecraft.server.v1_12_R1.DedicatedServer.D(DedicatedServer.java:406) [spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at net.minecraft.server.v1_12_R1.MinecraftServer.C(MinecraftServer.java:679) [spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at net.minecraft.server.v1_12_R1.MinecraftServer.run(MinecraftServer.java:577) [spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at java.lang.Thread.run(Unknown Source) [?:1.8.0_181]
Caused by: java.lang.NullPointerException
        at de.wolfi.minopoly.components.minigames.Minigame.isEquals(Minigame.java:78) ~[?:?]
        at de.wolfi.minopoly.components.MinigameManager.hasMinigame(MinigameManager.java:64) ~[?:?]
        at de.wolfi.minopoly.commands.SetupCommand.createMinigameMainSetup(SetupCommand.java:159) ~[?:?]
        at de.wolfi.minopoly.commands.SetupCommand.onClick(SetupCommand.java:223) ~[?:?]
        at sun.reflect.GeneratedMethodAccessor30.invoke(Unknown Source) ~[?:?]
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source) ~[?:1.8.0_181]
        at java.lang.reflect.Method.invoke(Unknown Source) ~[?:1.8.0_181]
        at org.bukkit.plugin.java.JavaPluginLoader$1.execute(JavaPluginLoader.java:302) ~[spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        ... 15 more
'
- [ ] 'org.bukkit.event.EventException: null
        at org.bukkit.plugin.java.JavaPluginLoader$1.execute(JavaPluginLoader.java:306) ~[spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at org.bukkit.plugin.RegisteredListener.callEvent(RegisteredListener.java:62) ~[spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at org.bukkit.plugin.SimplePluginManager.fireEvent(SimplePluginManager.java:500) [spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at org.bukkit.plugin.SimplePluginManager.callEvent(SimplePluginManager.java:485) [spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at net.minecraft.server.v1_12_R1.PlayerConnection.a(PlayerConnection.java:1889) [spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at net.minecraft.server.v1_12_R1.PacketPlayInWindowClick.a(SourceFile:33) [spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at net.minecraft.server.v1_12_R1.PacketPlayInWindowClick.a(SourceFile:10) [spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at net.minecraft.server.v1_12_R1.PlayerConnectionUtils$1.run(SourceFile:13) [spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at java.util.concurrent.Executors$RunnableAdapter.call(Unknown Source) [?:1.8.0_181]
        at java.util.concurrent.FutureTask.run(Unknown Source) [?:1.8.0_181]
        at net.minecraft.server.v1_12_R1.SystemUtils.a(SourceFile:46) [spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at net.minecraft.server.v1_12_R1.MinecraftServer.D(MinecraftServer.java:748) [spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at net.minecraft.server.v1_12_R1.DedicatedServer.D(DedicatedServer.java:406) [spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at net.minecraft.server.v1_12_R1.MinecraftServer.C(MinecraftServer.java:679) [spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at net.minecraft.server.v1_12_R1.MinecraftServer.run(MinecraftServer.java:577) [spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        at java.lang.Thread.run(Unknown Source) [?:1.8.0_181]
Caused by: java.lang.NullPointerException
        at de.wolfi.minopoly.commands.SetupCommand.onClick(SetupCommand.java:205) ~[?:?]
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_181]
        at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source) ~[?:1.8.0_181]
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source) ~[?:1.8.0_181]
        at java.lang.reflect.Method.invoke(Unknown Source) ~[?:1.8.0_181]
        at org.bukkit.plugin.java.JavaPluginLoader$1.execute(JavaPluginLoader.java:302) ~[spigot-1.12.2.jar:git-Spigot-2cf50f0-2b93d83]
        ... 15 more
'
- [x] Jail

- [x] Minigame

- [x] Dice > Pasch

- [ ] fix items according to pack

- [ ] Shitty Items in inventory -> Field overview

- [ ] FUCKING EVENT FIELD -> Event listener

- [x] Add price and blocks to Fundsfield & the ability to create the funds with a price in _SetupCommand_


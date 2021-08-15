## Description

A simple plugin with several features to secure your server against hackers!
To ensure optimal security, some commands (plugin reload, PIN creation / deletion) can be executed ONLY on the console.

## Developers

You can reuse nProtect but make sure you comply with the [LICENSE](https://github.com/thisisnzed/nProtect/blob/main/LICENSE).

### Maven

```xml
<dependencies>
    <dependency>
        <groupId>com.nz1337</groupId>
        <artifactId>nProtect</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

## Dependencies

* **EasySQL** : https://github.com/thisisnzed/EasySQL

## Features

* **MySQL Support** | Database & tables **auto-creation**
* Fully **[configurable](https://github.com/thisisnzed/nProtect/blob/main/src/main/resources/config.yml)**
* Fully **[translatable](https://github.com/thisisnzed/nProtect/blob/main/src/main/resources/lang.yml)**
* Plugin has task **asynchronously** to get best performances
* Support all versions **from 1.7**
* Several **protections** for your server : *Anti-UUIDSpoof*, *PIN Security*, *IPWhitelist*, *Anti-OP*, *Anti-Permissions*, *Anti-Gamemode*, *Map Scanner*, *Anti-Commands*, *IP per account* and more in the future!
* You can disable modules that you do not need in the configuration

## Installation

* I. Download the [last release](https://github.com/thisisnzed/nProtect/releases) of nProtect
* II. Put the file to your folder **plugins**
* III. Run the server
* IV. Configure database in **plugins/nProtect/config.yml**
* V. Restart the server

All messages are customizable!

## Features by module

### **PIN SECURITY FOR STAFF**

This module allows you to create a PIN code by staff with the **protect.staff** permission.

You can **create** a **PIN code** for a staff by running ONLY on the console the command **/pin create (player) (code)**.
But you can also remove a **staff code** by simply typing **/pin remove (player)** on the console as well.

If a staff connects to the server and has not entered his PIN code, then he will not be able to take any action.
To connect, just type **/pin login (code)**

In the configuration, you can decide to kick the player if he types the wrong PIN.

```yaml
# Kick the player if he use the wrong code
kickBadPin true
```

### **MAP SCANNER**

This module allows you to **detect** if an **unauthorized object** (such as bedrock) is present in the **inventory** of a **online player** or in a **chest of a loaded chunk**.

Simply type **/mapscanner start** to start **scanning the map** and the **inventory** of each **online player**.

In the configuration, you can select which items are detected. You can also choose to enable/disable inventory or map scan.

```yaml
# Check all items in each inventory
scanInventories: true
# Check content of chests for each loaded chunks
scanChests: true
# Prohibited items (see https://helpch.at/docs/1.8/org/bukkit/Material.html)
scanItems:
  - "ENDER_PORTAL_FRAME"
  - "ENDER_PORTAL"
  - "BEDROCK"
  - "EGG"
```

### **IP per account**

With "IP per account", you can **authorize ONE IP address per player**.

If the player connects with another IP address than the one indicated in the configuration, then he will be automatically excluded.

In the configuration, enter each nickname followed by ";" and by the authorized IP address.

```yaml
# List all concerned accounts by the ip per account module with this format : - "playerName;ipAddress"
accountsWithIp:
  - "player1;127.0.0.1"
  - "player2;192.168.1.1"
```

### **AntiCommands**

This module allows you to **disable** certain **in-game commands**. Please note that they will always be **executable on the console**.

By default, the most **dangerous commands are disabled**.

In the configuration, you can list every forbidden commands.

```yaml
# List all concerned commands by the anti commands module with this format : - "luckperms "
# All commands starting with each value will only be executable on the console
antiCommandsList:
  - "luckperm "
  - "luckperms "
  - "lpb "
  - "lp "
  - "luckperms:"
  - "bukkit:"
  - "minecraft:"
  - "spigot:"
  - "pex "
  - "permissionsex "
  - "permissionsex:"
  - "deop "
  - "op "
  - "reload "
  - "restart "
  - "rl "
  - "plugman:"
  - "plugman unload nprotect"
  - "plugman disable nprotect"
  - "plugman reload nprotect"
```

### **AntiGamemode**

If your server only supports one GameMode, this module is very useful. Indeed, it allows to detect all the players being in another GameMode.

In the configuration you will be able to modify the default gamemode, toggle restore to default gamemode, add a command to punish players who are in a other gamemode AND add players who will not be impacted.

```yaml
# If a player has a different gamemode than the one indicated, he will be automatically punished
# GameModes: SURVIVAL / CREATIVE / SPECTATOR / ADVENTURE
defaultGamemode: "SURVIVAL"

# Command that will be executed to punish a player in an invalid gamemode
gamemodePunishment: "ban %player% [nProtect] Your GameMode wasn't correct!"

# Set default gamemode to player if he has an invalid gamemode
restoreGamemode: true

# List of players who can be in any gamemode
bypassGamemodePlayers:
  - "player1"
  - "player2"
```

### **AntiOP**

This module **constantly checks** whether a player **(connected or not)** is **abnormally operator**.

In the configuration you can configure the players bypassing this protection, toggle the autodeop, define the interval between each check and you can also add a punishmeent command.

```yaml
# Command that will be executed to punish a player if he is OP
# Example : opPunishment: "ban %player% [nProtect] AntiOP"
# WARNING : If you decide to punish the OP player, the task will not run asynchronously!
opPunishment: ""

# Deop player if he is OP
autoDeop: true

# Check every X seconds if a player is OP on the server
checkOpTimer: 2

# List of players who can be OP
bypassOpPlayers:
  - "player1"
  - "player2"
```

### **AntiUUIDSpoof**

No configuration is available for this module because it **only kicks the player** trying to **change their UUID**.

### **AntiPermissions**

For **each action** (join, quit, send message, execute command etc...), **the plugin** will check that the player does not have any of the **permissions listed in the configuration**.

```yaml
# Command that will be executed to punish a player in an invalid gamemode
permissionPunishment: "ban %player% [nProtect] AntiPermissions"

# List of permissions that the plugin must check
permissionsDenied:
  - "essentials.eco"
  - "essentials.give"
  - "essentials.gamemode"
  - "essentials.gamemode.creative"
  - "minecraft.command.deop"
  - "minecraft.command.op"
  - "luckperms.admin"
  - "plugman.admin"
  - "worldedit.*"
  - "luckperms.*"
  - "*"
  - "op"
```

### **IPWhitelist**

If you have a **bungeecord server**, this is the perfect solution to **block "Bungee Exploit"** type **attacks** aimed at connecting by the **ports** of the **Spigot servers**.

You just need to enter the **IP address** (or IP addresses if you have more than one) of your **BungeeCord server**.
The plugin will **automatically block the connection** if the **player** tries to **connect** other than **through this IP**.

```yml
# Only IP addresses that players can connect to
# Enter only the IPv4 addresses of your proxy (your BungeeCord)
# If you are not using bungeecord or other proxy, please disable the "ipWhitelist" module above
whitelistedIps:
  - "12.34.56.78"
  - "11.22.33.44"
```

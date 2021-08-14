package com.nz1337.nprotect.configs;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Settings {

    @Getter @Setter private String permissionPunishment, host, database, user, password, defaultGamemode, gamemodePunishment, opPunishment;
    @Getter @Setter private int port, checkOpTimer;
    @Getter @Setter private boolean autoDeop, restoreGamemode, scanInventories, scanChests, ipWhitelist, antiUuidSpoofer, pinStaff, antiOp, antiPermissions, antiGamemode, mapScanner, antiCommands, ipPerAccount, kickBadPin;
    @Getter @Setter private ArrayList<String> whitelistedIps, permissionsDenied, bypassPermissionsPlayers, bypassOpPlayers, scanItems, accountsWithIp, antiCommandsList, bypassGamemodePlayers;

}
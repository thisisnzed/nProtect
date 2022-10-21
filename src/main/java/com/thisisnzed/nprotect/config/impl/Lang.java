package com.thisisnzed.nprotect.config.impl;

import com.thisisnzed.nprotect.config.FileManager;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Lang {

    PIN_LOGIN_SUCCESS, PIN_LOGIN_ALREADY_CONNECTED, PIN_LOGIN_NO_PIN, PIN_LOGIN_WRONG, PIN_LOGIN_USAGE, PIN_LOGIN_INVALID, PIN_REMOVE_SUCCESS, PIN_REMOVE_NO_PIN, PIN_REMOVE_NOT_FOUND, PIN_REMOVE_USAGE, PIN_CREATE_SUCCESS, PIN_CREATE_ALREADY, PIN_CREATE_INVALID, PIN_CREATE_NOT_FOUND, PIN_CREATE_USAGE, COMMAND_HELP_SCANNER, COMMAND_HELP_PROTECT, COMMAND_HELP_PIN, COMMAND_UNKNOWN_SUBCOMMAND, COMMAND_NO_PERMISSION, COMMAND_ONLY_CONSOLE, PIN_BAD_KICK, UUIDSPOOF_KICK, MAP_SCANNER_START, MAP_SCANNER_END, MAP_SCANNER_FOUND_CHEST, MAP_SCANNER_FOUND_PLAYER, IP_PER_ACCOUNT_KICK, IPWHITELIST_KICK, COMMAND_LOADED, COMMAND_UNLOADED, PIN_NOT_CONNECTED;

    private static final Map<Lang, String> values = new HashMap<>();

    static {
        for (final Lang lang : values()) values.put(lang, lang.getFromFile());
    }

    public String get() {
        return values.get(this);
    }

    public List<String> getList() {
        return FileManager.LANG.getConfig().getStringList(name().toLowerCase().replace("_", "-"));
    }

    private String getFromFile() {
        String value = FileManager.LANG.getConfig().getString(name().toLowerCase().replace("_", "-"));
        if (value == null) value = "";
        return ChatColor.translateAlternateColorCodes('&', value);
    }
}
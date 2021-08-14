package com.nz1337.nprotect.listeners;

import com.nz1337.nprotect.Protect;
import com.nz1337.nprotect.configs.Lang;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.HashMap;

public class AccountEvents implements Listener {

    private final HashMap<String, String> accounts;

    public AccountEvents(Protect protect) {
        this.accounts = new HashMap<>();
        protect.getSettings().getAccountsWithIp().stream().map(accounts -> accounts.split(";")).forEach(splited -> this.accounts.put(splited[0], splited[1]));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLogin(PlayerLoginEvent event) {
        final String playerName = event.getPlayer().getName();
        final String address = event.getAddress().getHostAddress();
        if (this.isConcerned(playerName) && !this.isValidAddress(playerName, address))
            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, Lang.IP_PER_ACCOUNT_KICK.get().replace("%ip%", address));
    }

    private boolean isConcerned(String playerName) {
        return this.accounts.containsKey(playerName);
    }

    private boolean isValidAddress(String playerName, String address) {
        return this.accounts.get(playerName).equals(address);
    }
}
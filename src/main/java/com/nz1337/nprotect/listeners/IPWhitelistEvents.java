package com.nz1337.nprotect.listeners;

import com.nz1337.nprotect.Protect;
import com.nz1337.nprotect.configs.Lang;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.ArrayList;

public class IPWhitelistEvents implements Listener {

    private final ArrayList<String> addresses;

    public IPWhitelistEvents(Protect protect) {
        this.addresses = new ArrayList<>();
        this.addresses.addAll(protect.getSettings().getWhitelistedIps());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (!isValidAddress(event.getRealAddress().getHostAddress())) event.disallow(PlayerLoginEvent.Result.KICK_BANNED, Lang.IPWHITELIST_KICK.get());
    }

    private boolean isValidAddress(String address) {
        return this.addresses.contains(address);
    }
}
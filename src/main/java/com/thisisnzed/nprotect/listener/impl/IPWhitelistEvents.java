package com.thisisnzed.nprotect.listener.impl;

import com.thisisnzed.nprotect.Protect;
import com.thisisnzed.nprotect.config.impl.Lang;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.ArrayList;

public class IPWhitelistEvents implements Listener {

    private final ArrayList<String> addresses;

    public IPWhitelistEvents(final Protect protect) {
        this.addresses = new ArrayList<>();
        this.addresses.addAll(protect.getSettings().getWhitelistedIps());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLogin(final PlayerLoginEvent event) {
        if (!this.isValidAddress(event.getRealAddress().getHostAddress())) event.disallow(PlayerLoginEvent.Result.KICK_BANNED, Lang.IPWHITELIST_KICK.get());
    }

    private boolean isValidAddress(final String address) {
        return this.addresses.contains(address);
    }
}
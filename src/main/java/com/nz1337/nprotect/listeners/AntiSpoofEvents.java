package com.nz1337.nprotect.listeners;

import com.nz1337.nprotect.configs.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class AntiSpoofEvents implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLogin(final PlayerLoginEvent event) {
        final Player player = event.getPlayer();
        if (!Bukkit.getOfflinePlayer(player.getName()).getUniqueId().equals(player.getUniqueId()))
            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, Lang.UUIDSPOOF_KICK.get());
    }
}
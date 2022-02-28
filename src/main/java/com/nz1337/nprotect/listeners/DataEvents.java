package com.nz1337.nprotect.listeners;

import com.nz1337.nprotect.Protect;
import com.nz1337.nprotect.data.UserData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DataEvents implements Listener {

    private final Protect protect;

    public DataEvents(final Protect protect) {
        this.protect = protect;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(final PlayerJoinEvent event) {
        this.protect.userData.add(new UserData(event.getPlayer()));
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        this.protect.getUserManager().delete(event.getPlayer());
    }
}
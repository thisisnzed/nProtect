package com.thisisnzed.nprotect.listener.impl;

import com.thisisnzed.nprotect.Protect;
import com.thisisnzed.nprotect.config.impl.Lang;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;

public class AntiCmdEvents implements Listener {

    private final ArrayList<String> commands;

    public AntiCmdEvents(final Protect protect) {
        this.commands = new ArrayList<>();
        this.commands.addAll(protect.getSettings().getAntiCommandsList());
    }

    @EventHandler
    public void onCommand(final PlayerCommandPreprocessEvent event) {
        if (this.isConcerned(event.getMessage())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Lang.COMMAND_ONLY_CONSOLE.get());
        }
    }

    private boolean isConcerned(final String command) {
        return this.commands.contains("/" + command.toLowerCase());
    }
}
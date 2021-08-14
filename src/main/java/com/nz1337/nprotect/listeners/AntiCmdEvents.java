package com.nz1337.nprotect.listeners;

import com.nz1337.nprotect.Protect;
import com.nz1337.nprotect.configs.Lang;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;

public class AntiCmdEvents implements Listener {

    private final ArrayList<String> commands;

    public AntiCmdEvents(Protect protect) {
        this.commands = new ArrayList<>();
        this.commands.addAll(protect.getSettings().getAntiCommandsList());
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (this.isConcerned(event.getMessage())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Lang.COMMAND_ONLY_CONSOLE.get());
        }
    }

    private boolean isConcerned(String command) {
        return this.commands.contains("/" + command.toLowerCase());
    }
}
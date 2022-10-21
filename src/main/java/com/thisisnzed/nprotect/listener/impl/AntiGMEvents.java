package com.thisisnzed.nprotect.listener.impl;

import com.thisisnzed.nprotect.Protect;
import com.thisisnzed.nprotect.config.impl.Settings;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class AntiGMEvents implements Listener {

    private final ArrayList<String> players;
    private final Settings settings;

    public AntiGMEvents(final Protect protect) {
        this.settings = protect.getSettings();
        this.players = new ArrayList<>();
        this.players.addAll(this.settings.getBypassGamemodePlayers());
    }

    @EventHandler
    public void onUpdateGamemode(final PlayerGameModeChangeEvent event) {
        final Player player = event.getPlayer();
        final GameMode gameMode = GameMode.valueOf(this.settings.getDefaultGamemode());
        if (!this.canBypass(player) && !gameMode.equals(event.getNewGameMode())) {
            if (this.settings.isRestoreGamemode()) player.setGameMode(gameMode);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.settings.getGamemodePunishment().replace("%player%", player.getName()));
        }
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final GameMode gameMode = GameMode.valueOf(this.settings.getDefaultGamemode());
        if (!this.canBypass(player) && !gameMode.equals(player.getGameMode())) {
            if (this.settings.isRestoreGamemode()) player.setGameMode(gameMode);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.settings.getGamemodePunishment().replace("%player%", player.getName()));
        }
    }

    private boolean canBypass(final Player player) {
        return this.players.contains(player.getName());
    }
}
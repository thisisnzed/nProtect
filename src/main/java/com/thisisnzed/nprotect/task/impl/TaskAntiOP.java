package com.thisisnzed.nprotect.task.impl;

import com.thisisnzed.nprotect.Protect;
import com.thisisnzed.nprotect.config.impl.Settings;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class TaskAntiOP extends BukkitRunnable {

    private final ArrayList<String> players;
    private final Settings settings;
    private final boolean async;

    public TaskAntiOP(final Protect protect, final boolean async) {
        this.settings = protect.getSettings();
        this.players = new ArrayList<>();
        this.async = async;
        this.players.addAll(this.settings.getBypassOpPlayers());
    }

    @Override
    public void run() {
        Bukkit.getOperators().forEach(player -> {
            if (!this.canBypass(player) && player.isOp()) {
                if (this.settings.isAutoDeop()) player.setOp(false);
                if (!this.async)
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.settings.getOpPunishment().replace("%player%", player.getName()));
            }
        });
    }

    private boolean canBypass(final OfflinePlayer player) {
        return this.players.contains(player.getName());
    }
}
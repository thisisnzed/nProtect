package com.nz1337.nprotect.tasks;

import com.nz1337.nprotect.Protect;
import com.nz1337.nprotect.configs.Settings;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class TaskAntiOP extends BukkitRunnable {

    private final ArrayList<String> players;
    private final Settings settings;
    private final boolean async;

    public TaskAntiOP(Protect protect, boolean async) {
        this.settings = protect.getSettings();
        this.players = new ArrayList<>();
        this.async = async;
        this.players.addAll(this.settings.getBypassOpPlayers());
    }

    @Override
    public void run() {
        Bukkit.getOperators().forEach(player -> {
            if (!this.isBypasser(player) && player.isOp()) {
                if (this.settings.isAutoDeop()) player.setOp(false);
                if(!this.async) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.settings.getOpPunishment().replace("%player%", player.getName()));
            }
        });
    }

    private boolean isBypasser(OfflinePlayer player) {
        return this.players.contains(player.getName());
    }
}
package com.nz1337.nprotect.listeners;

import com.nz1337.nprotect.Protect;
import com.nz1337.nprotect.configs.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.ArrayList;

public class AntiPermEvents implements Listener {

    private final ArrayList<String> players;
    private final ArrayList<String> permissions;
    private final Settings settings;

    public AntiPermEvents(final Protect protect) {
        this.settings = protect.getSettings();
        this.players = new ArrayList<>();
        this.permissions = new ArrayList<>();
        this.players.addAll(this.settings.getBypassPermissionsPlayers());
        this.permissions.addAll(this.settings.getPermissionsDenied());
    }

    @EventHandler
    public void onUpdateGamemode(final PlayerGameModeChangeEvent event) {
        this.check(event.getPlayer());
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        this.check(event.getPlayer());
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        this.check(event.getPlayer());
    }

    @EventHandler
    public void onCommand(final PlayerCommandPreprocessEvent event) {
        this.check(event.getPlayer());
    }

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent event) {
        this.check(event.getPlayer());
    }

    private void check(final Player player) {
        if (!this.isBypasser(player) && this.hasDeniedPermission(player)) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.settings.getPermissionPunishment().replace("%player%", player.getName()));
    }

    private boolean isBypasser(final Player player) {
        return this.players.contains(player.getName());
    }

    private boolean hasDeniedPermission(final Player player) {
        return this.permissions.stream().anyMatch(player::hasPermission);
    }
}
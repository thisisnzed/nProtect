package com.thisisnzed.nprotect.listener.impl;

import com.thisisnzed.nprotect.Protect;
import com.thisisnzed.nprotect.config.impl.Lang;
import com.thisisnzed.nprotect.data.UserData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

public class PinEvents implements Listener {

    private final Protect protect;
    private final String message = Lang.PIN_NOT_CONNECTED.get();

    public PinEvents(final Protect protect) {
        this.protect = protect;
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        this.protect.userData.add(new UserData(player));
        final UserData userData = this.protect.getUserManager().getUserData(player);
        if (player.hasPermission("protect.staff") || player.hasPermission("protect.admin")) {
            userData.setLoginWaiting(true);
            if (this.hasPin(player)) userData.setPin(Integer.parseInt(String.valueOf(this.protect.getDatabaseManager().getColumn().getValue("uuid", player.getUniqueId().toString(), "pin"))));
        }
    }

    @EventHandler
    public void onPlace(final BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        if (!this.hasPin(player) || this.isLogged(player)) return;
        event.setCancelled(true);
        player.sendMessage(this.message);
    }

    @EventHandler
    public void onBreak(final BlockBreakEvent event) {
        final Player player = event.getPlayer();
        if (!this.hasPin(player) || this.isLogged(player)) return;
        event.setCancelled(true);
        player.sendMessage(this.message);
    }

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        if (this.isLogged(player) || !this.hasPin(player) || event.getMessage().toLowerCase().startsWith("/pin login"))
            return;
        event.setCancelled(true);
        player.sendMessage(this.message);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        if (!this.hasPin(player) || this.isLogged(player)) return;
        event.setCancelled(true);
        player.sendMessage(this.message);
    }

    @EventHandler
    public void onCommand(final PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        if (this.isLogged(player) || !this.hasPin(player) || event.getMessage().toLowerCase().startsWith("/pin login"))
            return;
        event.setCancelled(true);
        player.sendMessage(this.message);
    }

    @EventHandler
    public void onDrop(final PlayerDropItemEvent event) {
        final Player player = event.getPlayer();
        if (!this.hasPin(player) || this.isLogged(player)) return;
        event.setCancelled(true);
        player.sendMessage(this.message);
    }

    @EventHandler
    public void onChangeItem(final PlayerItemHeldEvent event) {
        final Player player = event.getPlayer();
        if (!this.hasPin(player) || this.isLogged(player)) return;
        event.setCancelled(true);
        player.sendMessage(this.message);
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (this.isLogged(player) || !this.hasPin(player) || (event.getTo().getBlockX() == event.getFrom().getBlockX() && event.getTo().getBlockZ() == event.getFrom().getBlockZ()))
            return;
        event.setTo(event.getFrom());
        player.sendMessage(this.message);
    }

    private boolean isLogged(final Player player) {
        return !this.protect.getUserManager().getUserData(player).isLoginWaiting();
    }

    private boolean hasPin(final Player player) {
        return this.protect.getDatabaseManager().getColumn().isExists("uuid", player.getUniqueId().toString());
    }
}
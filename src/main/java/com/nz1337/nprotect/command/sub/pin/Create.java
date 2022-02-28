package com.nz1337.nprotect.command.sub.pin;

import com.nz1337.easysql.manager.Column;
import com.nz1337.nprotect.command.SubCommandManager;
import com.nz1337.nprotect.configs.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Create extends SubCommandManager {

    private final Column column;

    public Create(final Column column) {
        this.column = column;
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        if (!sender.hasPermission("protect.admin")) {
            sender.sendMessage(Lang.COMMAND_NO_PERMISSION.get());
            return;
        }
        if (sender instanceof Player) {
            sender.sendMessage(Lang.COMMAND_ONLY_CONSOLE.get());
            return;
        }
        if (args.length != 3) {
            sender.sendMessage(Lang.PIN_CREATE_USAGE.get());
            return;
        }
        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
        if (offlinePlayer == null) {
            sender.sendMessage(Lang.PIN_CREATE_NOT_FOUND.get());
            return;
        }
        if (!this.isInteger(args[2]) || args[2].length() != 4) {
            sender.sendMessage(Lang.PIN_CREATE_INVALID.get());
            return;
        }
        final String uuid = offlinePlayer.getUniqueId().toString();
        final String playerName = offlinePlayer.getName();
        final int pin = Integer.parseInt(args[2]);
        if (this.column.isExists("uuid", uuid)) {
            sender.sendMessage(Lang.PIN_CREATE_ALREADY.get().replace("%player%", playerName));
            return;
        }
        this.column.insertDefault(uuid, pin);
        sender.sendMessage(Lang.PIN_CREATE_SUCCESS.get().replace("%pin%", String.valueOf(pin)).replace("%player%", playerName));
    }

    private boolean isInteger(final String potential) {
        try {
            Integer.parseInt(potential);
        } catch (final NumberFormatException | NullPointerException ignored) {
            return false;
        }
        return true;
    }

    @Override
    public String name() {
        return "create";
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
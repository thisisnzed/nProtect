package com.nz1337.nprotect.command.sub.pin;

import com.nz1337.easysql.manager.Column;
import com.nz1337.nprotect.command.SubCommandManager;
import com.nz1337.nprotect.configs.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Remove extends SubCommandManager {

    private final Column column;

    public Remove(Column column) {
        this.column = column;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("protect.admin")) {
            sender.sendMessage(Lang.COMMAND_NO_PERMISSION.get());
            return;
        }
        if (sender instanceof Player) {
            sender.sendMessage(Lang.COMMAND_ONLY_CONSOLE.get());
            return;
        }
        if (args.length != 2) {
            sender.sendMessage(Lang.PIN_REMOVE_USAGE.get());
            return;
        }
        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
        if (offlinePlayer == null) {
            sender.sendMessage(Lang.PIN_REMOVE_NOT_FOUND.get());
            return;
        }
        final String uuid = offlinePlayer.getUniqueId().toString();
        final String playerName = offlinePlayer.getName();
        if (!this.column.isExists("uuid", uuid)) {
            sender.sendMessage(Lang.PIN_REMOVE_NO_PIN.get().replace("%player%", playerName));
            return;
        }
        this.column.delete("uuid", uuid);
        sender.sendMessage(Lang.PIN_REMOVE_SUCCESS.get().replace("%player%", playerName));
    }

    @Override
    public String name() {
        return "remove";
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
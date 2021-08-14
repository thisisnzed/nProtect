package com.nz1337.nprotect.command.sub.pin;

import com.nz1337.easysql.manager.Column;
import com.nz1337.nprotect.Protect;
import com.nz1337.nprotect.command.SubCommandManager;
import com.nz1337.nprotect.configs.Lang;
import com.nz1337.nprotect.data.UserData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Login extends SubCommandManager {

    private final Column column;
    private final Protect protect;

    public Login(Column column, Protect protect) {
        this.column = column;
        this.protect = protect;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("protect.admin")) {
            sender.sendMessage(Lang.COMMAND_NO_PERMISSION.get());
            return;
        }
        if (!(sender instanceof Player)) return;
        final Player player = (Player) sender;
        if (args.length != 2) {
            sender.sendMessage(Lang.PIN_LOGIN_USAGE.get());
            return;
        }
        if (!this.isInteger(args[1])) {
            sender.sendMessage(Lang.PIN_LOGIN_INVALID.get());
            return;
        }
        final String uuid = player.getUniqueId().toString();
        if (!this.column.isExists("uuid", uuid)) {
            sender.sendMessage(Lang.PIN_LOGIN_NO_PIN.get());
            return;
        }
        final UserData userData = this.protect.getUserManager().getUserData(player);
        if (!userData.isLoginWaiting()) {
            sender.sendMessage(Lang.PIN_LOGIN_ALREADY_CONNECTED.get());
            return;
        }
        if (Integer.parseInt(args[1]) != userData.getPin()) {
            sender.sendMessage(Lang.PIN_LOGIN_WRONG.get());
            if (this.protect.getSettings().isKickBadPin()) player.kickPlayer(Lang.PIN_BAD_KICK.get());
            return;
        }
        sender.sendMessage(Lang.PIN_LOGIN_SUCCESS.get());
        userData.setLoginWaiting(false);
    }

    private boolean isInteger(String potential) {
        try {
            Integer.parseInt(potential);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    @Override
    public String name() {
        return "login";
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
package com.thisisnzed.nprotect.command.subcommands.protect;

import com.thisisnzed.nprotect.command.SubCommandManager;
import com.thisisnzed.nprotect.config.impl.Lang;
import com.thisisnzed.nprotect.launcher.Launcher;
import com.thisisnzed.nprotect.utils.PluginHelper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reload extends SubCommandManager {

    private final Launcher launcher;

    public Reload(final Launcher launcher) {
        this.launcher = launcher;
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
        PluginHelper.unload(sender, "nProtect");
        PluginHelper.load(sender, "nProtect", this.launcher);
    }

    @Override
    public String name() {
        return "reload";
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
package com.nz1337.nprotect.command.sub.protect;

import com.nz1337.nprotect.command.SubCommandManager;
import com.nz1337.nprotect.configs.Lang;
import com.nz1337.nprotect.launcher.Launcher;
import com.nz1337.nprotect.utils.PluginHelper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reload extends SubCommandManager {

    private final Launcher launcher;

    public Reload(Launcher launcher) {
        this.launcher = launcher;
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
package com.nz1337.nprotect.command;

import com.nz1337.easysql.manager.Column;
import com.nz1337.nprotect.Protect;
import com.nz1337.nprotect.command.sub.pin.Create;
import com.nz1337.nprotect.command.sub.pin.Login;
import com.nz1337.nprotect.command.sub.pin.Remove;
import com.nz1337.nprotect.command.sub.protect.Reload;
import com.nz1337.nprotect.command.sub.scanner.Start;
import com.nz1337.nprotect.configs.Lang;
import com.nz1337.nprotect.launcher.Launcher;
import com.nz1337.nprotect.manager.ModuleManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandManager implements CommandExecutor {

    private final ArrayList<SubCommandManager> pinSub = new ArrayList<>();
    private final ArrayList<SubCommandManager> scannerSub = new ArrayList<>();
    private final ArrayList<SubCommandManager> mainSub = new ArrayList<>();
    private final Protect protect;
    private final Launcher launcher;
    private final Column column;

    public CommandManager(final Protect protect) {
        this.protect = protect;
        this.launcher = protect.getLauncher();
        this.column = protect.getDatabaseManager().getColumn();
    }

    public void registerCommands() {
        this.addExecutor("protect");
        this.mainSub.add(new Reload(this.launcher));
        if (ModuleManager.PINSTAFF.isToggle()) {
            this.addExecutor("pin");
            this.pinSub.add(new Create(this.column));
            this.pinSub.add(new Remove(this.column));
            this.pinSub.add(new Login(this.column, protect));
        }
        if (ModuleManager.MAPSCANNER.isToggle()) {
            this.addExecutor("mapscanner");
            this.scannerSub.add(new Start(protect));
        }
    }

    private void addExecutor(final String command) {
        this.launcher.getCommand(command).setExecutor(this);
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args) {
        if (sender.hasPermission("protect.staff") || sender.hasPermission("protect.admin")) {
            switch (command.getName().toLowerCase()) {
                case "pin": {
                    if (args.length == 0) {
                        Lang.COMMAND_HELP_PIN.getList().forEach(e -> sender.sendMessage(ChatColor.translateAlternateColorCodes('&', e)));
                        return true;
                    }
                    final SubCommandManager target = this.get(args[0], this.pinSub);
                    if (target == null) {
                        sender.sendMessage(Lang.COMMAND_UNKNOWN_SUBCOMMAND.get());
                        return true;
                    }
                    new ArrayList<>(Arrays.asList(args)).remove(0);
                    try {
                        target.execute(sender, args);
                    } catch (final Exception exception) {
                        exception.printStackTrace();
                    }
                    break;
                }
                case "protect": {
                    if (args.length == 0) {
                        Lang.COMMAND_HELP_PROTECT.getList().forEach(e -> sender.sendMessage(ChatColor.translateAlternateColorCodes('&', e)));
                        return true;
                    }
                    final SubCommandManager target = this.get(args[0], this.mainSub);
                    if (target == null) {
                        sender.sendMessage(Lang.COMMAND_UNKNOWN_SUBCOMMAND.get());
                        return true;
                    }
                    new ArrayList<>(Arrays.asList(args)).remove(0);
                    try {
                        target.execute(sender, args);
                    } catch (final Exception exception) {
                        exception.printStackTrace();
                    }
                    break;
                }
                case "mapscanner": {
                    if (args.length == 0) {
                        Lang.COMMAND_HELP_SCANNER.getList().forEach(e -> sender.sendMessage(ChatColor.translateAlternateColorCodes('&', e)));
                        return true;
                    }
                    final SubCommandManager target = this.get(args[0], this.scannerSub);
                    if (target == null) {
                        sender.sendMessage(Lang.COMMAND_UNKNOWN_SUBCOMMAND.get());
                        return true;
                    }
                    new ArrayList<>(Arrays.asList(args)).remove(0);
                    try {
                        target.execute(sender, args);
                    } catch (final Exception exception) {
                        exception.printStackTrace();
                    }
                    break;
                }
            }
        } else sender.sendMessage(Lang.COMMAND_NO_PERMISSION.get());
        return true;
    }

    private SubCommandManager get(String name, ArrayList<SubCommandManager> cmd) {
        for (SubCommandManager sc : cmd) {
            if (sc.name().equalsIgnoreCase(name)) return sc;
            String[] aliases;
            for (int var5 = 0; var5 < (aliases = sc.aliases()).length; ++var5)
                if (name.equalsIgnoreCase(aliases[var5])) return sc;
        }
        return null;
    }
}
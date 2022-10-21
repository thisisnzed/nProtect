package com.thisisnzed.nprotect.command;

import com.nz1337.easysql.manager.Column;
import com.thisisnzed.nprotect.Protect;
import com.thisisnzed.nprotect.command.subcommands.pin.Create;
import com.thisisnzed.nprotect.command.subcommands.pin.Login;
import com.thisisnzed.nprotect.command.subcommands.pin.Remove;
import com.thisisnzed.nprotect.command.subcommands.protect.Reload;
import com.thisisnzed.nprotect.command.subcommands.scanner.Start;
import com.thisisnzed.nprotect.config.impl.Lang;
import com.thisisnzed.nprotect.launcher.Launcher;
import com.thisisnzed.nprotect.module.ModuleManager;
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
                    this.execute(sender, args, this.pinSub);
                    break;
                }
                case "protect": {
                    if (args.length == 0) {
                        Lang.COMMAND_HELP_PROTECT.getList().forEach(e -> sender.sendMessage(ChatColor.translateAlternateColorCodes('&', e)));
                        return true;
                    }
                    this.execute(sender, args, this.mainSub);
                    break;
                }
                case "mapscanner": {
                    if (args.length == 0) {
                        Lang.COMMAND_HELP_SCANNER.getList().forEach(e -> sender.sendMessage(ChatColor.translateAlternateColorCodes('&', e)));
                        return true;
                    }
                    this.execute(sender, args, this.scannerSub);
                    break;
                }
            }
        } else sender.sendMessage(Lang.COMMAND_NO_PERMISSION.get());
        return true;
    }

    private void execute(final CommandSender commandSender, final String[] args, final ArrayList<SubCommandManager> subCommandManager) {
        final SubCommandManager target = this.get(args[0], subCommandManager);
        if (target == null) {
            commandSender.sendMessage(Lang.COMMAND_UNKNOWN_SUBCOMMAND.get());
            return;
        }
        new ArrayList<>(Arrays.asList(args)).remove(0);
        try {
            target.execute(commandSender, args);
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
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
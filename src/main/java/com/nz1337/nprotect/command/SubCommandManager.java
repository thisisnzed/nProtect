package com.nz1337.nprotect.command;

import org.bukkit.command.CommandSender;

import java.io.IOException;

public abstract class SubCommandManager {

    public abstract void execute(final CommandSender sender, final String[] args) throws IOException;

    public abstract String name();

    public abstract String info();

    public abstract String[] aliases();
}
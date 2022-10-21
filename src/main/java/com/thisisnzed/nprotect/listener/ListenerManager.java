package com.thisisnzed.nprotect.listener;

import com.thisisnzed.nprotect.Protect;
import com.thisisnzed.nprotect.launcher.Launcher;
import com.thisisnzed.nprotect.listener.impl.*;
import com.thisisnzed.nprotect.module.ModuleManager;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class ListenerManager {

    private final Protect protect;
    private final ArrayList<Listener> listeners;

    public ListenerManager(final Protect protect) {
        this.protect = protect;
        this.listeners = new ArrayList<>();
        this.addAllListeners();
    }

    private void addAllListeners() {
        if (ModuleManager.PINSTAFF.isToggle()) this.listeners.add(new PinEvents(this.protect));
        if (ModuleManager.ANTIUUIDSPOOFER.isToggle()) this.listeners.add(new AntiSpoofEvents());
        if (ModuleManager.IPPERACCOUNT.isToggle()) this.listeners.add(new AccountEvents(this.protect));
        if (ModuleManager.ANTICOMMANDS.isToggle()) this.listeners.add(new AntiCmdEvents(this.protect));
        if (ModuleManager.ANTIGAMEMODE.isToggle()) this.listeners.add(new AntiGMEvents(this.protect));
        if (ModuleManager.ANTIPERMISSIONS.isToggle()) this.listeners.add(new AntiPermEvents(this.protect));
        if (ModuleManager.IPWHITELIST.isToggle()) this.listeners.add(new IPWhitelistEvents(this.protect));
        this.listeners.add(new DataEvents(this.protect));
    }

    public void registerListeners(final Launcher launcher) {
        this.listeners.forEach(listener -> launcher.getServer().getPluginManager().registerEvents(listener, launcher));
    }
}

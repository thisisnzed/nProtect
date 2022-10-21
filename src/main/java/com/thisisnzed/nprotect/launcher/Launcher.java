package com.thisisnzed.nprotect.launcher;

import com.thisisnzed.nprotect.Protect;
import org.bukkit.plugin.java.JavaPlugin;

public class Launcher extends JavaPlugin {

    private Launch launch;

    public void onEnable() {
        this.launch = new Protect();
        this.launch.launch(this, this.getClassLoader());
    }

    public void onDisable() {
        this.launch.shutdown();
    }
}

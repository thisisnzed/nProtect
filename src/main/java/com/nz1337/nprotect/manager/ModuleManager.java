package com.nz1337.nprotect.manager;

public enum ModuleManager {

    IPWHITELIST("IpWhitelist", true),
    ANTIUUIDSPOOFER("AntiUuidSpoofer", true),
    PINSTAFF("PinStaff", true),
    ANTIOP("AntiOp", true),
    ANTIPERMISSIONS("AntiPermissions", true),
    ANTIGAMEMODE("AntiGamemode", true),
    MAPSCANNER("MapScanner", true),
    ANTICOMMANDS("AntiCommands", true),
    IPPERACCOUNT("IpPerAccount", true);

    private final String name;
    private boolean toggle;

    ModuleManager(final String name, final boolean toggle) {
        this.name = name;
        this.toggle = toggle;
    }

    public String getModuleName() {
        return this.name;
    }

    public boolean isToggle() {
        return this.toggle;
    }

    public void setToggle(boolean toogle) {
        this.toggle = toogle;
    }
}

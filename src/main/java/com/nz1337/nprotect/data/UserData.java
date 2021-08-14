package com.nz1337.nprotect.data;

import org.bukkit.entity.Player;

import java.util.UUID;

public class UserData {

    private Player player;
    private UUID uuid;
    private int pin;
    private boolean loginWaiting;

    public UserData(Player player) {
        this.uuid = player.getUniqueId();
        this.pin = 1234;
        this.loginWaiting = false;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public boolean isLoginWaiting() {
        return loginWaiting;
    }

    public void setLoginWaiting(boolean loginWaiting) {
        this.loginWaiting = loginWaiting;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

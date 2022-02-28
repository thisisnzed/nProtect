package com.nz1337.nprotect.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.UUID;

public class UserData {

    @Getter
    @Setter
    private Player player;
    @Getter
    @Setter
    private UUID uuid;
    @Getter
    @Setter
    private int pin;
    @Getter
    @Setter
    private boolean loginWaiting;

    public UserData(Player player) {
        this.uuid = player.getUniqueId();
        this.pin = 1234;
        this.loginWaiting = false;
    }
}

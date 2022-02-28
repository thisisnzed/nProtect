package com.nz1337.nprotect.manager;

import com.nz1337.nprotect.Protect;
import com.nz1337.nprotect.data.UserData;
import org.bukkit.entity.Player;

import java.util.Optional;

public class UserManager {

    private final Protect protect;

    public UserManager(final Protect protect) {
        this.protect = protect;
    }

    public Optional<UserData> getUser(final Player player) {
        return this.protect.userData.stream().filter(user -> user.getUuid().toString().equals(player.getUniqueId().toString())).findFirst();
    }

    public void delete(final Player player) {
        getUser(player).ifPresent(this.protect.userData::remove);
    }

    public UserData getUserData(final Player player) {
        return this.protect.userData.stream().filter(userData -> userData.getUuid().equals(player.getUniqueId())).findFirst().orElse(null);
    }
}

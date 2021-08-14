package com.nz1337.nprotect.manager;

import com.nz1337.nprotect.Protect;
import com.nz1337.nprotect.data.UserData;
import org.bukkit.entity.Player;

import java.util.Optional;

public class UserManager {

    private final Protect protect;

    public UserManager(Protect protect) {
        this.protect = protect;
    }

    public Optional<UserData> getUser(Player player) {
        return this.protect.userData.stream().filter(user -> user.getUuid().toString().equals(player.getUniqueId().toString())).findFirst();
    }

    public void delete(Player player) {
        getUser(player).ifPresent(this.protect.userData::remove);
    }

    public UserData getUserData(Player player) {
        for (UserData userData : this.protect.userData)
            if (userData.getUuid().equals(player.getUniqueId())) return userData;
        return null;
    }
}

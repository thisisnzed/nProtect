package com.nz1337.nprotect.command.sub.scanner;

import com.nz1337.nprotect.Protect;
import com.nz1337.nprotect.command.SubCommandManager;
import com.nz1337.nprotect.configs.Lang;
import com.nz1337.nprotect.configs.Settings;
import com.nz1337.nprotect.launcher.Launcher;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Start extends SubCommandManager {

    private final Settings settings;
    private final Launcher launcher;
    private final ArrayList<Material> materials;

    public Start(Protect protect) {
        this.settings = protect.getSettings();
        this.launcher = protect.getLauncher();
        this.materials = new ArrayList<>();
        this.settings.getScanItems().stream().filter(item -> Material.getMaterial(item) != null).forEach(item -> this.materials.add(Material.valueOf(item)));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("protect.admin")) {
            sender.sendMessage(Lang.COMMAND_NO_PERMISSION.get());
            return;
        }
        sender.sendMessage(Lang.MAP_SCANNER_START.get());
        final ArrayList<Player> players = new ArrayList<>();
        final ArrayList<Chest> chests = new ArrayList<>();
        Bukkit.getScheduler().runTaskAsynchronously(this.launcher, () -> {
            if (settings.isScanChests()) {
                final World world = (sender instanceof Player) ? ((Player) sender).getWorld() : Bukkit.getWorlds().get(0);
                Arrays.stream(world.getLoadedChunks()).forEach(chunk -> Arrays.stream(chunk.getTileEntities()).filter(blockState -> blockState instanceof Chest).forEach(blockState -> {
                    final Chest chest = (Chest) blockState;
                    final Location location = chest.getLocation();
                    Arrays.stream(chest.getBlockInventory().getContents()).filter(Objects::nonNull).filter(itemstack -> this.materials.contains(itemstack.getType())).forEach(itemStack -> {
                        if (!chests.contains(chest)) {
                            sender.sendMessage(Lang.MAP_SCANNER_FOUND_CHEST.get().replace("%y%", String.valueOf(location.getY())).replace("%z%", String.valueOf(location.getZ())).replace("%x%", String.valueOf(location.getX())).replace("%material%", itemStack.getType().name()));
                            chests.add(chest);
                        }
                    });
                }));
            }
            if (settings.isScanInventories()) {
                Bukkit.getOnlinePlayers().forEach(player -> Arrays.stream(player.getInventory().getContents()).filter(Objects::nonNull).filter(itemStack -> this.materials.contains(itemStack.getType())).forEach(itemStack -> {
                    if (!players.contains(player)) {
                        sender.sendMessage(Lang.MAP_SCANNER_FOUND_PLAYER.get().replace("%player%", player.getName()).replace("%material%", itemStack.getType().name()));
                        players.add(player);
                    }
                }));
            }
            sender.sendMessage(Lang.MAP_SCANNER_END.get());
        });
    }

    @Override
    public String name() {
        return "start";
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
package com.nz1337.nprotect;

import com.nz1337.nprotect.command.CommandManager;
import com.nz1337.nprotect.configs.Settings;
import com.nz1337.nprotect.data.UserData;
import com.nz1337.nprotect.launcher.Launch;
import com.nz1337.nprotect.launcher.Launcher;
import com.nz1337.nprotect.manager.*;
import org.bukkit.Bukkit;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class Protect implements Launch {

    public final ArrayList<UserData> userData = new ArrayList<>();
    private Launcher launcher;
    private Settings settings;
    private UserManager userManager;
    private CommandManager commandManager;
    private ListenerManager listenerManager;
    private TaskManager taskManager;
    private DatabaseManager databaseManager;

    public void launch(Launcher launcher, ClassLoader classLoader) {
        this.launcher = launcher;
        this.createFiles(launcher.getLogger(), classLoader);
        this.loadModules();
        this.databaseManager = new DatabaseManager(this);
        this.userManager = new UserManager(this);
        this.commandManager = new CommandManager(this);
        this.listenerManager = new ListenerManager(this);
        this.taskManager = new TaskManager(this);
        this.commandManager.registerCommands();
        Bukkit.getOnlinePlayers().stream().filter(player -> userManager.getUserData(player) == null).map(UserData::new).forEach(this.userData::add);
    }

    public void shutdown() {
        Bukkit.getOnlinePlayers().stream().filter(player -> userManager.getUserData(player) != null).forEach(player -> userManager.delete(player));
        Bukkit.getScheduler().cancelTasks(this.getLauncher());
        this.getDatabaseManager().close();
    }

    private void loadModules() {
        Arrays.stream(ModuleManager.values()).forEach(module -> {
            try {
                module.setToggle((boolean) this.getSettings().getClass().getMethod("is" + module.getModuleName()).invoke(this.getSettings()));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private void createFiles(Logger logger, ClassLoader classLoader) {
        final FileManager config = FileManager.CONFIG;
        final FileManager lang = FileManager.LANG;
        lang.create(logger);
        config.create(logger);
        try (final Reader reader = Files.newBufferedReader(config.getFile().toPath(), StandardCharsets.UTF_8)) {
            Yaml yaml = new Yaml(new CustomClassLoaderConstructor(classLoader));
            yaml.setBeanAccess(BeanAccess.FIELD);
            this.settings = yaml.loadAs(reader, Settings.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Launcher getLauncher() {
        return launcher;
    }

    public DatabaseManager getDatabaseManager() {
        return this.databaseManager;
    }

    public UserManager getUserManager() {
        return this.userManager;
    }

    public Settings getSettings() {
        return this.settings;
    }
}
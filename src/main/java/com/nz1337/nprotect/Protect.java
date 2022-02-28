package com.nz1337.nprotect;

import com.nz1337.nprotect.command.CommandManager;
import com.nz1337.nprotect.configs.Settings;
import com.nz1337.nprotect.data.UserData;
import com.nz1337.nprotect.launcher.Launch;
import com.nz1337.nprotect.launcher.Launcher;
import com.nz1337.nprotect.manager.*;
import lombok.Getter;
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
    @Getter
    private Launcher launcher;
    @Getter
    private Settings settings;
    @Getter
    private UserManager userManager;
    @Getter
    private DatabaseManager databaseManager;
    private CommandManager commandManager;
    private ListenerManager listenerManager;
    private TaskManager taskManager;

    public void launch(final Launcher launcher, final ClassLoader classLoader) {
        this.launcher = launcher;
        this.createFiles(launcher.getLogger(), classLoader);
        this.loadModules();
        this.databaseManager = new DatabaseManager(this);
        this.userManager = new UserManager(this);
        this.commandManager = new CommandManager(this);
        this.listenerManager = new ListenerManager(this);
        this.taskManager = new TaskManager(this);
        this.commandManager.registerCommands();
        Bukkit.getOnlinePlayers().stream().filter(player -> this.userManager.getUserData(player) == null).map(UserData::new).forEach(this.userData::add);
    }

    public void shutdown() {
        Bukkit.getOnlinePlayers().stream().filter(player -> this.userManager.getUserData(player) != null).forEach(player -> this.userManager.delete(player));
        Bukkit.getScheduler().cancelTasks(this.getLauncher());
        this.getDatabaseManager().close();
    }

    private void loadModules() {
        Arrays.stream(ModuleManager.values()).forEach(module -> {
            try {
                module.setToggle((boolean) this.getSettings().getClass().getMethod("is" + module.getModuleName()).invoke(this.getSettings()));
            } catch (final NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
                exception.printStackTrace();
            }
        });
    }

    private void createFiles(final Logger logger, final ClassLoader classLoader) {
        final FileManager config = FileManager.CONFIG;
        final FileManager lang = FileManager.LANG;
        lang.create(logger);
        config.create(logger);
        try (final Reader reader = Files.newBufferedReader(config.getFile().toPath(), StandardCharsets.UTF_8)) {
            Yaml yaml = new Yaml(new CustomClassLoaderConstructor(classLoader));
            yaml.setBeanAccess(BeanAccess.FIELD);
            this.settings = yaml.loadAs(reader, Settings.class);
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }
}
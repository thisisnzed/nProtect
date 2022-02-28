package com.nz1337.nprotect.manager;

import com.nz1337.nprotect.launcher.Launcher;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.logging.Logger;

public enum FileManager {

    CONFIG("config.yml"), LANG("lang.yml");

    private final Launcher instance = Launcher.getPlugin(Launcher.class);
    private final String fileName;
    private final File dataFolder;

    FileManager(final String fileName) {
        this.fileName = fileName;
        this.dataFolder = instance.getDataFolder();
    }

    public File getFile() {
        return new File(dataFolder, fileName);
    }

    public void create(final Logger logger) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("ResourcesPath cannot be null or empty");
        }
        InputStream in = instance.getResource(fileName);
        if (in == null) {
            throw new IllegalArgumentException("The resource " + fileName + " cannot be found in jar");
        }
        if (!dataFolder.exists() && !dataFolder.mkdir()) {
            logger.severe("Failed to make directory");
        }
        File outFile = getFile();
        try {
            if (!outFile.exists()) {
                logger.info("The " + fileName + " wasn't found, creation in progress");
                OutputStream out = new FileOutputStream(outFile);
                byte[] buffer = new byte[1024];
                int n;
                while ((n = in.read(buffer)) >= 0) {
                    out.write(buffer, 0, n);
                }
                out.close();
                in.close();
                if (!outFile.exists()) {
                    logger.severe("Unable to copy file");
                }
            }
        } catch (final IOException exception) {
            exception.printStackTrace();
        }

    }

    public FileConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(getFile());
    }

    public void save(final FileConfiguration fileConfiguration) {
        try {
            fileConfiguration.save(getFile());
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }
}
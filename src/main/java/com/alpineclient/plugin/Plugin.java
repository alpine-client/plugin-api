package com.alpineclient.plugin;

import co.aikar.commands.PaperCommandManager;
import com.alpineclient.plugin.config.ConfigManager;
import com.alpineclient.plugin.config.ConfigWrapper;
import com.alpineclient.plugin.framework.Command;
import com.alpineclient.plugin.framework.EventListener;
import com.alpineclient.plugin.framework.PluginListener;
import com.alpineclient.plugin.network.NetHandlerPlugin;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Thomas Wearmouth
 * Created on 21/06/2023
 */
@ApiStatus.Internal
public final class Plugin extends JavaPlugin {
    @Getter
    private static Plugin instance;
    {
        instance = this;
    }

    @Getter
    private ConfigManager configManager;

    @Getter
    private PaperCommandManager commandManager;

    @Getter
    private PlayerHandler playerHandler;

    @Getter
    private NetHandlerPlugin netHandler;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager();
        this.commandManager = new PaperCommandManager(this);
        this.playerHandler = new PlayerHandler();
        this.netHandler = new NetHandlerPlugin();

        this.setupLocales();
        this.registerAll();
    }

    @SuppressWarnings("UnstableApiUsage")
    private void registerAll() {
        String packageName = this.getClass().getPackage().getName();
        Set<Class<?>> clazzes = ImmutableSet.of();
        try {
            clazzes = ClassPath.from(this.getClassLoader()).getAllClasses().stream()
                    .filter(clazz -> clazz.getPackageName().contains(packageName))
                    .map(ClassPath.ClassInfo::load)
                    .collect(Collectors.toSet());
        }
        catch (Exception ex) {
            Reference.LOGGER.error("Error scanning classpath", ex);
        }
        for (Class<?> clazz : clazzes) {
            if (Modifier.isAbstract(clazz.getModifiers()))
                continue;

            try {
                if (EventListener.class.isAssignableFrom(clazz)) {
                    Constructor<? extends EventListener> constructor = ((Class<? extends EventListener>) clazz).getDeclaredConstructor();
                    constructor.setAccessible(true);
                    EventListener listener = constructor.newInstance();
                    this.getServer().getPluginManager().registerEvents(listener, this);
                }
                else if (PluginListener.class.isAssignableFrom(clazz)) {
                    Constructor<? extends PluginListener> constructor = ((Class<? extends PluginListener>) clazz).getDeclaredConstructor();
                    constructor.setAccessible(true);
                    PluginListener listener = constructor.newInstance();
                    Bukkit.getMessenger().registerOutgoingPluginChannel(this, listener.getChannelId());
                    Bukkit.getMessenger().registerIncomingPluginChannel(this, listener.getChannelId(), listener);
                }
                else if (Command.class.isAssignableFrom(clazz)) {
                    Constructor<? extends Command> constructor = ((Class<? extends Command>) clazz).getDeclaredConstructor();
                    constructor.setAccessible(true);
                    Command command = constructor.newInstance();
                    this.commandManager.registerCommand(command, true);
                    command.registerCompletions(this.commandManager);
                    command.registerConditions(this.commandManager);
                }
            }
            catch (Exception ex) {
                Reference.LOGGER.error("Failed to register " + clazz.getName(), ex);
            }
        }
    }

    private void setupLocales() {
        ConfigWrapper acfMessages = new ConfigWrapper("acf_config.yml");
        acfMessages.getConfig().options().copyDefaults(true);
        acfMessages.saveConfig();
        acfMessages.saveDefaultConfig();

        try {
            this.commandManager.getLocales().loadYamlLanguageFile(new File(this.getDataFolder(), "acf_config.yml"),
                    this.commandManager.getLocales().getDefaultLocale());
        } catch (IOException | InvalidConfigurationException ex) {
            Reference.LOGGER.error("Unable to load ACF config", ex);
        }
    }
}

package com.alpineclient.plugin;

import co.aikar.commands.PaperCommandManager;
import com.alpineclient.plugin.config.ConfigManager;
import com.alpineclient.plugin.config.ConfigWrapper;
import com.alpineclient.plugin.framework.Command;
import com.alpineclient.plugin.framework.EventListener;
import com.alpineclient.plugin.framework.PluginListener;
import com.alpineclient.plugin.network.NetHandlerPlugin;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

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
        this.registerEventListeners();
        this.registerPluginListeners();
        this.registerCommands();
    }

    private void registerEventListeners() {
        Reflections reflections = new Reflections("com.alpineclient.plugin.listener.event");
        Set<Class<? extends EventListener>> classes = reflections.getSubTypesOf(EventListener.class);

        // Register event listeners
        for (Class<? extends EventListener> clazz : classes) {
            if (Modifier.isAbstract(clazz.getModifiers()))
                continue;

            try {
                EventListener event = clazz.getDeclaredConstructor().newInstance();
                this.getServer().getPluginManager().registerEvents(event, this);
            }
            catch (Exception ex) {
                Reference.LOGGER.error("Unable to register event listener {}", clazz.getName());
            }
        }
    }

    private void registerPluginListeners() {
        Reflections reflections = new Reflections("com.alpineclient.plugin.listener.plugin");
        Set<Class<? extends PluginListener>> classes = reflections.getSubTypesOf(PluginListener.class);

        // Register plugin listeners
        for (Class<? extends PluginListener> clazz : classes) {
            if (Modifier.isAbstract(clazz.getModifiers()))
                continue;

            try {
                PluginListener plugin = clazz.getDeclaredConstructor().newInstance();
                Bukkit.getMessenger().registerOutgoingPluginChannel(this, plugin.getChannelId());
                Bukkit.getMessenger().registerIncomingPluginChannel(this, plugin.getChannelId(), plugin);
            }
            catch (Exception ex) {
                Reference.LOGGER.error("Unable to register plugin listener {}", clazz.getName());
            }
        }
    }

    private void registerCommands() {
        Reflections reflections = new Reflections("com.alpineclient.plugin.command");
        Set<Class<? extends Command>> classes = reflections.getSubTypesOf(Command.class);
        Set<Command> commands = new HashSet<>();

        // Register commands
        for (Class<? extends Command> clazz : classes) {
            if (Modifier.isAbstract(clazz.getModifiers()))
                continue;

            try {
                Command command = clazz.getDeclaredConstructor().newInstance();
                this.commandManager.registerCommand(command, true);
                commands.add(command);
            }
            catch (Exception ex) {
                Reference.LOGGER.error("Unable to register command {}", clazz.getName());
            }
        }

        // Register completions and conditions
        commands.forEach(cmd -> {
            cmd.registerCompletions(commandManager);
            cmd.registerConditions(commandManager);
        });
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

package com.alpineclient.plugin;

import com.alpineclient.plugin.command.*;
import com.alpineclient.plugin.config.ConfigManager;
import com.alpineclient.plugin.config.impl.MessageConfig;
import com.alpineclient.plugin.framework.EventListener;
import com.alpineclient.plugin.framework.PluginListener;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.adventure.bukkit.platform.LiteAdventurePlatformExtension;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import dev.rollczi.litecommands.bukkit.LiteBukkitSettings;
import dev.rollczi.litecommands.message.LiteMessages;
import dev.rollczi.litecommands.schematic.SchematicFormat;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;

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
    private LiteCommands<CommandSender> commandManager;

    @Getter
    private PlayerHandler playerHandler;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager();
        this.playerHandler = new PlayerHandler();
        this.commandManager = this.createCommandManager();

        this.registerAll();
    }

    @SuppressWarnings("UnstableApiUsage")
    private LiteCommands<CommandSender> createCommandManager() {
        MessageConfig config = ConfigManager.getInstance().getConfig(MessageConfig.class);
        LiteCommandsBuilder<CommandSender, LiteBukkitSettings, ?> builder = LiteBukkitFactory.builder(this.getName())
                // <Required Arguments> [Optional Arguments]
                .schematicGenerator(SchematicFormat.angleBrackets())

                // Enable Adventure support
                .extension(new LiteAdventurePlatformExtension<>(Reference.AUDIENCES), cfg -> cfg
                        .miniMessage(true)
                        .legacyColor(true)
                        .colorizeArgument(true)
                        .serializer(Reference.MINI_MESSAGE)
                )

                // Feed in our commands
                .commands(new CommandCheck(), new CommandList(), new CommandNotify())

                // Configure error messages
                .message(LiteMessages.MISSING_PERMISSIONS, input -> config.missingPermissions.build())
                .message(LiteBukkitMessages.PLAYER_NOT_FOUND, input -> config.playerNotFound.build("player", input))
                .message(LiteBukkitMessages.PLAYER_ONLY, input -> config.playerOnly.build())
                .invalidUsage(new CommandInvalidUsage());
        return builder.build();
    }

    @SuppressWarnings({"UnstableApiUsage", "unchecked"})
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
                    this.getServer().getMessenger().registerOutgoingPluginChannel(this, listener.getChannelId());
                    this.getServer().getMessenger().registerIncomingPluginChannel(this, listener.getChannelId(), listener);
                }
            }
            catch (Exception ex) {
                Reference.LOGGER.error("Failed to register {}", clazz.getName(), ex);
            }
        }
    }
}

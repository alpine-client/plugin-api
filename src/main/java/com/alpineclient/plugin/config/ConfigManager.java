package com.alpineclient.plugin.config;

import com.alpineclient.plugin.PluginMain;
import com.alpineclient.plugin.Reference;
import com.alpineclient.plugin.config.impl.GeneralConfig;
import com.alpineclient.plugin.config.impl.MessageConfig;
import de.exlll.configlib.ConfigLib;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * @author BestBearr
 * Created on 02/06/23
 */
public final class ConfigManager {
    @Getter
    private static ConfigManager instance;

    public static final YamlConfigurationProperties PROPERTIES = ConfigLib.BUKKIT_DEFAULT_PROPERTIES.toBuilder()
            .header(String.join("\n",
                    Reference.NAME + " (" + Reference.VERSION + ")",
                    "Copyright (c) 2023, Crystal Development, LLC. All rights reserved.",
                    "",
                    "Messages are deserialized by the Adventure library",
                    "  - https://webui.advntr.dev/",
                    ""))
            .inputNulls(true).outputNulls(true)
            .build();

    @Getter
    private final Map<Class<? extends AbstractConfig>, AbstractConfig> registeredConfigurations = new HashMap<>();

    public ConfigManager() {
        instance = this;
        PluginMain main = PluginMain.getInstance();

        File path = main.getDataFolder();
        if (!path.exists() && !path.mkdirs())
            throw new IllegalStateException("Unable to generate configuration directory");

        File dataFolder = main.getDataFolder();
        this.registeredConfigurations.put(GeneralConfig.class, new GeneralConfig(new File(dataFolder, "config.yml").toPath()));
        this.registeredConfigurations.put(MessageConfig.class, new MessageConfig(new File(dataFolder, "messages.yml").toPath()));

        // Load/save the config
        for (Map.Entry<Class<? extends AbstractConfig>, AbstractConfig> abstractConfigEntry : this.registeredConfigurations.entrySet()) {
            AbstractConfig config = abstractConfigEntry.getValue();
            if (Files.exists(config.getConfigPath())) {
                AbstractConfig newConfig = YamlConfigurations.load(config.getConfigPath(), config.getClass(), PROPERTIES);
                newConfig.setConfigPath(config.getConfigPath());
                this.registeredConfigurations.put(config.getClass(), newConfig);
                YamlConfigurations.save(config.getConfigPath(), (Class<? super AbstractConfig>) config.getClass(), newConfig, PROPERTIES);
            }
            else {
                YamlConfigurations.save(config.getConfigPath(), (Class<? super AbstractConfig>) config.getClass(), config, PROPERTIES);
            }
        }
    }

    public void loadConfigs() {
        for (Map.Entry<Class<? extends AbstractConfig>, AbstractConfig> abstractConfigEntry : this.registeredConfigurations.entrySet()) {
            AbstractConfig config = abstractConfigEntry.getValue();
            if (!Files.exists(config.getConfigPath()))
                YamlConfigurations.save(config.getConfigPath(), (Class<? super AbstractConfig>) config.getClass(), config, PROPERTIES);
            else {
                AbstractConfig newConfig = YamlConfigurations.load(config.getConfigPath(), config.getClass(), PROPERTIES);
                newConfig.setConfigPath(config.getConfigPath());
                this.registeredConfigurations.put(config.getClass(), newConfig);
            }
        }
    }

    public void saveConfigs() {
        for (Map.Entry<Class<? extends AbstractConfig>, AbstractConfig> abstractConfigEntry : this.registeredConfigurations.entrySet()) {
            AbstractConfig config = abstractConfigEntry.getValue();
            YamlConfigurations.save(config.getConfigPath(), (Class<? super AbstractConfig>) config.getClass(), config, PROPERTIES);
        }
    }

    public <T extends AbstractConfig> @NotNull T getConfig(@NotNull Class<T> configClass) {
        AbstractConfig config = this.registeredConfigurations.get(configClass);
        if (config != null)
            return (T) config;
        throw new IllegalStateException("config was not registered");
    }
}

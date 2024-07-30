/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.alpineclient.plugin.config;

import com.alpineclient.plugin.PluginMain;
import com.alpineclient.plugin.Reference;
import com.alpineclient.plugin.config.impl.GeneralConfig;
import com.alpineclient.plugin.config.impl.MessageConfig;
import com.google.common.collect.ImmutableMap;
import de.exlll.configlib.ConfigLib;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author BestBearr
 * Created on 02/06/23
 */
public final class ConfigManager {
    private static final Map<Class<? extends AbstractConfig>, Path> CONFIGURATIONS = ImmutableMap.of(
            GeneralConfig.class, Paths.get("config.yml"),
            MessageConfig.class, Paths.get("messages.yml")
    );

    private static final YamlConfigurationProperties PROPERTIES = ConfigLib.BUKKIT_DEFAULT_PROPERTIES.toBuilder()
            .header(String.join("\n",
                    Reference.NAME + " (" + Reference.VERSION + ")",
                    "",
                    "Messages are deserialized by the Adventure library",
                    "  - https://webui.advntr.dev/",
                    ""))
            .inputNulls(true).outputNulls(true)
            .charset(StandardCharsets.UTF_8)
            .build();

    @Getter
    private static ConfigManager instance;

    private final Path directory;

    private final Map<Class<? extends AbstractConfig>, AbstractConfig> loadedConfigs = new HashMap<>(CONFIGURATIONS.size());

    public ConfigManager() {
        instance = this;
        this.directory = PluginMain.getInstance().getDataFolder().toPath();

        if (!Files.exists(this.directory)) {
            try {
                Files.createDirectories(this.directory);
            }
            catch (IOException ex) {
                Reference.LOGGER.error("Unable to create config directory at \"{}\"", this.directory, ex);
            }
        }

        CONFIGURATIONS.forEach((key, val) -> {
            try {
                AbstractConfig loadedConfig = YamlConfigurations.update(this.directory.resolve(val), key, PROPERTIES);
                this.loadedConfigs.put(key, loadedConfig);
            }
            catch (RuntimeException ex) {
                Reference.LOGGER.error("Unable to load config file \"{}\"", val, ex);
            }
        });
    }

    public <T extends AbstractConfig> @NotNull T getConfig(@NotNull Class<T> clazz) {
        AbstractConfig config = this.loadedConfigs.get(clazz);
        if (config != null)
            return (T) config;
        throw new IllegalStateException("No config was loaded for " + clazz.getSimpleName());
    }
}

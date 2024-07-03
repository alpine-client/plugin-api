package com.alpineclient.plugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;

/**
 * @author Thomas Wearmouth
 * Created on 21/06/2023
 */
@ApiStatus.Internal
public final class Reference {
    public static final String NAME = "${pluginName}";
    public static final String ID = "${pluginId}";
    public static final String VERSION = "${pluginVersion}";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static final BukkitAudiences AUDIENCES = BukkitAudiences.create(PluginMain.getInstance());
    public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
    public static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
}

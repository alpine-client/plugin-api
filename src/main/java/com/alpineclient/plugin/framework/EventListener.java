package com.alpineclient.plugin.framework;

import com.alpineclient.plugin.Plugin;
import com.alpineclient.plugin.config.ConfigManager;
import com.alpineclient.plugin.config.impl.MessageConfig;
import org.bukkit.event.Listener;

/**
 * @author BestBearr
 * Created on 09/06/23
 */
public abstract class EventListener implements Listener {
    protected final Plugin plugin = Plugin.getInstance();

    protected final MessageConfig messageConfig = ConfigManager.getInstance().getConfig(MessageConfig.class);
}

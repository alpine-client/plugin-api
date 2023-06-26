package com.alpineclient.plugin.framework;

import com.alpineclient.plugin.config.ConfigManager;
import com.alpineclient.plugin.config.impl.MessageConfig;
import org.bukkit.event.Listener;

/**
 * @author BestBearr
 * Created on 09/06/23
 */
public abstract class EventListener implements Listener {

    public MessageConfig messageConfig = ConfigManager.getInstance().getConfig(MessageConfig.class, c -> this.messageConfig = c);
}

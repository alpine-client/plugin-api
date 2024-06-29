package com.alpineclient.plugin.framework;

import com.alpineclient.plugin.Plugin;
import com.alpineclient.plugin.config.ConfigManager;
import com.alpineclient.plugin.config.impl.MessageConfig;

/**
 * @author BestBearr
 * Created on 07/06/23
 */
public abstract class BaseCommand {
    protected final Plugin plugin = Plugin.getInstance();

    protected final MessageConfig messageConfig = ConfigManager.getInstance().getConfig(MessageConfig.class);
}

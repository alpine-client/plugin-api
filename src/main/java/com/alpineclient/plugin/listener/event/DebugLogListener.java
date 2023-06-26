package com.alpineclient.plugin.listener.event;

import com.alpineclient.plugin.Reference;
import com.alpineclient.plugin.api.event.ClientDisconnectEvent;
import com.alpineclient.plugin.api.event.ClientHandshakeEvent;
import com.alpineclient.plugin.config.ConfigManager;
import com.alpineclient.plugin.config.impl.GeneralConfig;
import com.alpineclient.plugin.framework.EventListener;
import org.bukkit.event.EventHandler;

/**
 * @author Thomas Wearmouth
 * Created on 24/06/2023
 */
public final class DebugLogListener extends EventListener {
    @EventHandler
    public void onPlayerHandshake(ClientHandshakeEvent event) {
        if (ConfigManager.getInstance().getConfig(GeneralConfig.class).logging) {
            Reference.LOGGER.info("Player completed Alpine Client handshake: {} @ {}", event.getPlayer().getBukkitPlayer().getName(), event.getPlayer().getClientBrand());
        }
    }

    @EventHandler
    public void onPlayerDisconnect(ClientDisconnectEvent event) {
        if (ConfigManager.getInstance().getConfig(GeneralConfig.class).logging) {
            Reference.LOGGER.info("Alpine Client player disconnected: {}", event.getPlayer().getBukkitPlayer().getName());
        }
    }
}

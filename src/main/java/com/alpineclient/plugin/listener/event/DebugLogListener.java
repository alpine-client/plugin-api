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
    private final GeneralConfig generalConfig = ConfigManager.getInstance().getConfig(GeneralConfig.class);

    @EventHandler
    public void onPlayerHandshake(ClientHandshakeEvent event) {
        if (this.generalConfig.logging) {
            Reference.LOGGER.info("Player completed handshake: {} @ {}", event.getPlayer().getBukkitPlayer().getName(), event.getPlayer().getClientBrand());
        }
    }

    @EventHandler
    public void onPlayerDisconnect(ClientDisconnectEvent event) {
        if (this.generalConfig.logging) {
            Reference.LOGGER.info("Player disconnected: {}", event.getPlayer().getBukkitPlayer().getName());
        }
    }
}

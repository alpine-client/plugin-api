package com.alpineclient.plugin.listener.event;

import com.alpineclient.plugin.api.event.ClientHandshakeEvent;
import com.alpineclient.plugin.api.objects.AlpinePlayer;
import com.alpineclient.plugin.framework.EventListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;

/**
 * @author Thomas Wearmouth
 * Created on 24/06/2023
 */
public final class WorldListener extends EventListener {
    @EventHandler
    public void onPlayerHandshake(ClientHandshakeEvent event) {
        event.getPlayer().sendModules();
        event.getPlayer().sendWorldUpdate();
    }

    @EventHandler
    private void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        AlpinePlayer player = this.plugin.getPlayerHandler().getConnectedPlayer(event.getPlayer());
        if (player != null) {
            player.sendWorldUpdate();
        }
    }
}

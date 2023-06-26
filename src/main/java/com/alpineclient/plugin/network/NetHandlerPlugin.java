package com.alpineclient.plugin.network;

import com.alpineclient.plugin.Plugin;
import com.alpineclient.plugin.listener.plugin.PlayListener;
import org.bukkit.entity.Player;

/**
 * @author Thomas Wearmouth
 * Created on 21/06/2023
 */
public final class NetHandlerPlugin {
    public void sendPacket(Player player, Packet packet) {
        if (packet.getClass().isAnnotationPresent(ReadOnly.class))
            throw new RuntimeException("Packet is read-only");

        player.sendPluginMessage(Plugin.getInstance(), PlayListener.CHANNEL_ID, packet.toByteArray());
    }

    public void handlePacket(Player player, byte[] data) {
        Packet packet = Packet.handle(data);
        if (packet != null) {
            if (packet.getClass().isAnnotationPresent(WriteOnly.class))
                throw new RuntimeException("Packet is write-only");

            packet.process(player, this);
        }
    }
}

package com.alpineclient.plugin.listener.plugin;

import com.alpineclient.plugin.Plugin;
import com.alpineclient.plugin.Reference;
import com.alpineclient.plugin.framework.PluginListener;
import com.alpineclient.plugin.util.ByteBufUtils;
import com.alpineclient.plugin.util.object.HandshakeData;
import com.google.gson.JsonSyntaxException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

/**
 * @author Thomas Wearmouth
 * Created on 21/06/2023
 */
public final class HandshakeListener extends PluginListener {
    public static final String CHANNEL_ID = "ac:handshake";

    public HandshakeListener() {
        super(CHANNEL_ID);
    }

    @Override
    public void onMessage(@NotNull Player player, byte[] message) {
        String json = new String(message);
        if (!json.isEmpty()) {
            try {
                HandshakeData data = Reference.GSON.fromJson(json, HandshakeData.class);
                boolean success = Plugin.getInstance().getPlayerHandler().addConnectedPlayer(player, data);
                if (success) {
                    player.setMetadata("IsOnAlpineClient", new FixedMetadataValue(Plugin.getInstance(), true));
                    this.sendOK(player);
                }
            }
            catch (JsonSyntaxException ex) {
                Reference.LOGGER.warn("Invalid handshake payload received from {}", player.getName());
            }
        }
        else {
            Reference.LOGGER.warn("Invalid (zero-length) handshake payload received from {}", player.getName());
        }
    }

    private void sendOK(Player player) {
        ByteBuf buf = Unpooled.buffer();
        ByteBufUtils.writeUTF8String(buf, "OK");
        player.sendPluginMessage(Plugin.getInstance(), this.getChannelId(), buf.array());
    }
}

package com.alpineclient.plugin.network.packet;

import com.alpineclient.plugin.api.objects.ClientResource;
import com.alpineclient.plugin.api.objects.Notification;
import com.alpineclient.plugin.network.ByteBufWrapper;
import com.alpineclient.plugin.network.NetHandlerPlugin;
import com.alpineclient.plugin.network.Packet;
import com.alpineclient.plugin.network.WriteOnly;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @author BestBearr
 * Created on 13/06/23
 */
@WriteOnly
@AllArgsConstructor @NoArgsConstructor
public final class PacketNotificationAdd extends Packet {
    private Notification notification;

    @Override
    public void write(@NotNull ByteBufWrapper out) throws IOException {
        if (this.notification.getTitle() != null) {
            out.writeString(this.notification.getTitle());
        }
        else {
            out.writeString("");
        }
        out.writeString(this.notification.getDescription());
        out.writeInt(this.notification.getColor());
        out.writeInt((int) this.notification.getDuration());

        ClientResource texture = this.notification.getTexture();
        if (texture == null) {
            out.writeBool(false);
        }
        else {
            out.writeBool(true);
            out.writeBool(texture.getType() == ClientResource.Type.INTERNAL);
            out.writeString(texture.getValue());
        }
    }

    @Override
    public void read(@NotNull ByteBufWrapper in) throws IOException {
        // NO-OP
    }

    @Override
    public void process(@NotNull Player player, @NotNull NetHandlerPlugin netHandler) {
        // NO-OP
    }
}

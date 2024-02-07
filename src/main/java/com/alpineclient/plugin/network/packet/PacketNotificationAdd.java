package com.alpineclient.plugin.network.packet;

import com.alpineclient.plugin.api.objects.ClientResource;
import com.alpineclient.plugin.api.objects.Notification;
import com.alpineclient.plugin.network.Packet;
import com.alpineclient.plugin.network.WriteOnly;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;

/**
 * @author BestBearr
 * Created on 13/06/23
 */
@WriteOnly
public final class PacketNotificationAdd extends Packet {
    private final Notification notification;

    public PacketNotificationAdd(@NotNull Notification notification) {
        this.notification = notification;
    }

    @Override
    public void write(@NotNull MessagePacker packer) throws IOException {
        String title = this.notification.getTitle() == null ? "" : this.notification.getTitle();

        packer.packString(title);
        packer.packString(this.notification.getDescription());
        packer.packInt(this.notification.getColor());
        packer.packLong(this.notification.getDuration());

        ClientResource texture = this.notification.getTexture();
        if (texture == null) {
            packer.packBoolean(false);
        }
        else {
            packer.packBoolean(true);
            packer.packBoolean(texture.getType() == ClientResource.Type.INTERNAL);
            packer.packString(texture.getValue());
        }
    }

    @Override
    public void read(@NotNull MessageUnpacker unpacker) {
        // NO-OP
    }

    @Override
    public void process(@NotNull Player player) {
        // NO-OP
    }
}

package com.alpineclient.plugin.network.packet;

import com.alpineclient.plugin.network.Packet;
import com.alpineclient.plugin.network.WriteOnly;
import com.alpineclient.plugin.util.MsgPackUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Thomas Wearmouth <tomwmth@pm.me>
 * Created on 7/02/2024
 */
@WriteOnly
public final class PacketWaypointRemove extends Packet {
    private final UUID id;

    public PacketWaypointRemove(@NotNull UUID id) {
        this.id = id;
    }

    @Override
    public void write(@NotNull MessagePacker packer) throws IOException {
        MsgPackUtils.packUuid(packer, this.id);
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

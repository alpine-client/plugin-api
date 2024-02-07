package com.alpineclient.plugin.network.packet;

import com.alpineclient.plugin.network.Packet;
import com.alpineclient.plugin.network.WriteOnly;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;

/**
 * @author Thomas Wearmouth
 * Created on 24/06/2023
 */
@WriteOnly
public final class PacketWorldUpdate extends Packet {
    private final String world;

    public PacketWorldUpdate(@NotNull String world) {
        this.world = world;
    }

    @Override
    public void write(@NotNull MessagePacker packer) throws IOException {
        packer.packString(this.world);
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

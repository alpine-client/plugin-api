package com.alpineclient.plugin.network.packet;

import com.alpineclient.plugin.api.objects.Capability;
import com.alpineclient.plugin.network.Packet;
import com.alpineclient.plugin.network.WriteOnly;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.util.Collection;

/**
 * @author Thomas Wearmouth
 * Created on 29/06/2024
 */
@WriteOnly
public final class PacketCapabilities extends Packet {
    private final Collection<Capability> capabilities;

    public PacketCapabilities(Collection<Capability> capabilities) {
        this.capabilities = capabilities;
    }

    @Override
    public void write(@NotNull MessagePacker packer) throws IOException {
        int size = this.capabilities.size();
        packer.packArrayHeader(size);
        for (Capability capability : this.capabilities) {
            packer.packString(capability.getId());
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

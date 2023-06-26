package com.alpineclient.plugin.network.packet;

import com.alpineclient.plugin.network.ByteBufWrapper;
import com.alpineclient.plugin.network.NetHandlerPlugin;
import com.alpineclient.plugin.network.Packet;
import com.alpineclient.plugin.network.WriteOnly;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @author Thomas Wearmouth
 * Created on 24/06/2023
 */
@AllArgsConstructor
@WriteOnly
public final class PacketWorldUpdate extends Packet {
    private final String world;

    @Override
    public void write(@NotNull ByteBufWrapper out) throws IOException {
        if (this.world != null)
            out.writeString(this.world);
    }

    @Override
    public void read(@NotNull ByteBufWrapper in) throws IOException {
        // NO-OP
    }

    @Override
    public void process(@NotNull Player player, @NotNull NetHandlerPlugin handler) {
        // NO-OP
    }
}

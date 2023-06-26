package com.alpineclient.plugin.network.packet;

import com.alpineclient.plugin.network.ByteBufWrapper;
import com.alpineclient.plugin.network.NetHandlerPlugin;
import com.alpineclient.plugin.network.Packet;
import com.alpineclient.plugin.network.WriteOnly;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

/**
 * @author Thomas Wearmouth
 * Created on 26/06/2023
 */
@WriteOnly
@AllArgsConstructor @NoArgsConstructor
public final class PacketModules extends Packet {
    private Map<String, Boolean> modules;

    @Override
    public void write(@NotNull ByteBufWrapper out) throws IOException {
        int size = this.modules.size();
        out.writeInt(size);
        for (Map.Entry<String, Boolean> entry : this.modules.entrySet()) {
            out.writeString(entry.getKey());
            out.writeBool(entry.getValue());
        }
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

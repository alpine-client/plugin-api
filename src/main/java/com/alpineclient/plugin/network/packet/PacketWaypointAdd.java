package com.alpineclient.plugin.network.packet;

import com.alpineclient.plugin.api.objects.Waypoint;
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
 * @author Thomas Wearmouth
 * Created on 26/06/2023
 */
@WriteOnly
@AllArgsConstructor @NoArgsConstructor
public final class PacketWaypointAdd extends Packet {
    private Waypoint waypoint;

    @Override
    public void write(@NotNull ByteBufWrapper out) throws IOException {
        out.writeString(this.waypoint.getName());
        out.writeInt(this.waypoint.getPos().getBlockX());
        out.writeInt(this.waypoint.getPos().getBlockY());
        out.writeInt(this.waypoint.getPos().getBlockZ());
        out.writeInt(this.waypoint.getColor());
        out.writeString(this.waypoint.getWorld());
        out.writeInt((int) this.waypoint.getDuration());
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

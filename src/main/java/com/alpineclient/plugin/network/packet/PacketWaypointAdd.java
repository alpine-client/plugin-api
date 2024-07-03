/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.alpineclient.plugin.network.packet;

import com.alpineclient.plugin.api.objects.Waypoint;
import com.alpineclient.plugin.network.Packet;
import com.alpineclient.plugin.network.WriteOnly;
import com.alpineclient.plugin.util.MsgPackUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;

/**
 * @author Thomas Wearmouth
 * Created on 26/06/2023
 */
@WriteOnly
public final class PacketWaypointAdd extends Packet {
    private final Waypoint waypoint;

    public PacketWaypointAdd(@NotNull Waypoint waypoint) {
        this.waypoint = waypoint;
    }

    @Override
    public void write(@NotNull MessagePacker packer) throws IOException {
        MsgPackUtils.packUuid(packer, this.waypoint.getId());
        packer.packString(this.waypoint.getName());
        packer.packInt(this.waypoint.getPos().getBlockX());
        packer.packInt(this.waypoint.getPos().getBlockY());
        packer.packInt(this.waypoint.getPos().getBlockZ());
        packer.packInt(this.waypoint.getColor());
        packer.packString(this.waypoint.getWorld());
        packer.packLong(this.waypoint.getDuration());
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

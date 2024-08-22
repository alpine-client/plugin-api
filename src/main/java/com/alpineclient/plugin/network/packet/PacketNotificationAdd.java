/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.alpineclient.plugin.network.packet;

import com.alpineclient.plugin.api.objects.Notification;
import com.alpineclient.plugin.network.Packet;
import com.alpineclient.plugin.network.WriteOnly;
import com.alpineclient.plugin.util.MsgPackUtils;
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
        MsgPackUtils.packClientResource(packer, this.notification.getTexture());
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

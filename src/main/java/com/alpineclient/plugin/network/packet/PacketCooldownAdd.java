/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.alpineclient.plugin.network.packet;

import com.alpineclient.plugin.api.objects.Cooldown;
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
 * Created on 30/06/2024
 */
@WriteOnly
public final class PacketCooldownAdd extends Packet {
    private final Cooldown cooldown;

    public PacketCooldownAdd(@NotNull Cooldown cooldown) {
        this.cooldown = cooldown;
    }

    @Override
    public void write(@NotNull MessagePacker packer) throws IOException {
        MsgPackUtils.packUuid(packer, this.cooldown.getId());
        packer.packString(this.cooldown.getName());
        packer.packInt(this.cooldown.getColor());
        packer.packLong(this.cooldown.getDuration());
        MsgPackUtils.packClientResource(packer, this.cooldown.getTexture());
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

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.alpineclient.plugin.network.packet;

import com.alpineclient.plugin.network.Packet;
import com.alpineclient.plugin.network.WriteOnly;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.util.Map;

/**
 * @author Thomas Wearmouth
 * Created on 26/06/2023
 */
@WriteOnly
public final class PacketModules extends Packet {
    private final Map<String, Boolean> modules;

    public PacketModules(@NotNull Map<String, Boolean> modules) {
        this.modules = modules;
    }

    @Override
    public void write(@NotNull MessagePacker packer) throws IOException {
        int size = this.modules.size();
        packer.packArrayHeader(size);
        for (Map.Entry<String, Boolean> entry : this.modules.entrySet()) {
            packer.packString(entry.getKey());
            packer.packBoolean(entry.getValue());
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

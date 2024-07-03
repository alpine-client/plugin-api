/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.alpineclient.plugin.listener.plugin;

import com.alpineclient.plugin.Reference;
import com.alpineclient.plugin.framework.PluginListener;
import com.alpineclient.plugin.network.Packet;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author Thomas Wearmouth
 * Created on 21/06/2023
 */
public final class PlayListener extends PluginListener {
    public static final String CHANNEL_ID = "ac:play";

    public PlayListener() {
        super(CHANNEL_ID);
    }

    @Override
    public void onMessage(@NotNull Player player, byte[] message) {
        if (this.main.getPlayerHandler().isPlayerConnected(player.getUniqueId())) {
            Packet packet = Packet.fromBytes(message);
            if (packet != null) {
                packet.process(player);
            }
        }
        else {
            Reference.LOGGER.warn("{} sent a payload before performing a handshake; ignoring", player.getName());
        }
    }
}

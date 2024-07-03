/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.alpineclient.plugin.listener.event;

import com.alpineclient.plugin.api.event.ClientHandshakeEvent;
import com.alpineclient.plugin.api.objects.AlpinePlayer;
import com.alpineclient.plugin.api.objects.Capability;
import com.alpineclient.plugin.config.ConfigManager;
import com.alpineclient.plugin.config.impl.GeneralConfig;
import com.alpineclient.plugin.framework.EventListener;
import com.alpineclient.plugin.listener.plugin.PlayListener;
import com.alpineclient.plugin.network.Packet;
import com.alpineclient.plugin.network.packet.PacketCapabilities;
import com.alpineclient.plugin.network.packet.PacketModules;
import com.alpineclient.plugin.network.packet.PacketWorldUpdate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Thomas Wearmouth
 * Created on 21/06/2023
 */
public final class PlayerListener extends EventListener {
    @EventHandler
    public void onPlayerHandshake(ClientHandshakeEvent event) {
        AlpinePlayer player = event.getPlayer();
        this.sendModules(player);
        this.sendCapabilities(player);
        this.sendWorldUpdate(player);
    }

    @EventHandler
    private void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        AlpinePlayer player = this.main.getPlayerHandler().getConnectedPlayer(event.getPlayer());
        if (player != null) {
            this.sendWorldUpdate(player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID id = player.getUniqueId();
        if (this.main.getPlayerHandler().isPlayerConnected(id)) {
            boolean success = this.main.getPlayerHandler().removeConnectedPlayer(id);
            if (success) {
                player.removeMetadata("IsOnAlpineClient", this.main);
            }
        }
    }

    private void sendModules(AlpinePlayer player) {
        GeneralConfig config = ConfigManager.getInstance().getConfig(GeneralConfig.class);
        PacketModules packet = new PacketModules(config.modules);
        this.sendPacket(player, packet);
    }

    private void sendCapabilities(AlpinePlayer player) {
        Collection<Capability> capabilities = this.main.getCapabilityHandler().getCapabilities();
        if (!capabilities.isEmpty()) {
            PacketCapabilities packet = new PacketCapabilities(capabilities);
            this.sendPacket(player, packet);
        }
    }

    private void sendWorldUpdate(AlpinePlayer player) {
        PacketWorldUpdate packet = new PacketWorldUpdate(player.getBukkitPlayer().getWorld().getName());
        this.sendPacket(player, packet);
    }

    private void sendPacket(AlpinePlayer player, Packet packet) {
        player.getBukkitPlayer().sendPluginMessage(this.main, PlayListener.CHANNEL_ID, packet.toBytes());
    }
}

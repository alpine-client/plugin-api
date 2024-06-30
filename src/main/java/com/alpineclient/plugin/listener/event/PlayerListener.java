package com.alpineclient.plugin.listener.event;

import com.alpineclient.plugin.api.event.ClientDisconnectEvent;
import com.alpineclient.plugin.api.event.ClientHandshakeEvent;
import com.alpineclient.plugin.api.objects.AlpinePlayer;
import com.alpineclient.plugin.config.ConfigManager;
import com.alpineclient.plugin.config.impl.GeneralConfig;
import com.alpineclient.plugin.framework.EventListener;
import com.alpineclient.plugin.listener.plugin.PlayListener;
import com.alpineclient.plugin.network.Packet;
import com.alpineclient.plugin.network.packet.PacketCapabilities;
import com.alpineclient.plugin.network.packet.PacketModules;
import com.alpineclient.plugin.network.packet.PacketWorldUpdate;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
        AlpinePlayer player = this.plugin.getPlayerHandler().getConnectedPlayer(event.getPlayer());
        if (player != null) {
            this.sendWorldUpdate(player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID id = event.getPlayer().getUniqueId();
        if (this.plugin.getPlayerHandler().isPlayerConnected(id)) {
            AlpinePlayer player = this.plugin.getPlayerHandler().getConnectedPlayer(id);
            this.plugin.getPlayerHandler().removeConnectedPlayer(id);
            Bukkit.getPluginManager().callEvent(new ClientDisconnectEvent(player));
        }
    }

    private void sendModules(AlpinePlayer player) {
        GeneralConfig config = ConfigManager.getInstance().getConfig(GeneralConfig.class);
        PacketModules packet = new PacketModules(config.modules);
        this.sendPacket(player, packet);
    }

    private void sendCapabilities(AlpinePlayer player) {
        if (!this.plugin.getCapabilityHandler().getCapabilities().isEmpty()) {
            PacketCapabilities packet = new PacketCapabilities(this.plugin.getCapabilityHandler().getCapabilities());
            this.sendPacket(player, packet);
        }
    }

    private void sendWorldUpdate(AlpinePlayer player) {
        PacketWorldUpdate packet = new PacketWorldUpdate(player.getBukkitPlayer().getWorld().getName());
        this.sendPacket(player, packet);
    }

    private void sendPacket(AlpinePlayer player, Packet packet) {
        player.getBukkitPlayer().sendPluginMessage(this.plugin, PlayListener.CHANNEL_ID, packet.toBytes());
    }
}

package com.alpineclient.plugin.api.objects;

import com.alpineclient.plugin.Plugin;
import com.alpineclient.plugin.config.ConfigManager;
import com.alpineclient.plugin.config.impl.GeneralConfig;
import com.alpineclient.plugin.listener.plugin.PlayListener;
import com.alpineclient.plugin.network.Packet;
import com.alpineclient.plugin.network.packet.*;
import com.alpineclient.plugin.util.object.HandshakeData;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * Holds data about an Alpine Client user and provides methods
 * for interacting with them.
 *
 * @author Thomas Wearmouth
 * @since 1.0.0
 */
public final class AlpinePlayer {
    /**
     * The underlying Bukkit player object.
     */
    @Getter private final Player bukkitPlayer;

    /**
     * The mod platform the user is on.<p>
     * e.g. {@code fabric}, {@code forge}
     */
    @Getter private final String platform;

    /**
     * The Minecraft version the user is on.<p>
     * e.g. {@code 1.8.9}, {@code 1.20}
     */
    @Getter private final String version;

    /**
     * A list containing all the user's loaded mod IDs.<p>
     * e.g. {@code ["sodium", "lithium", "alpineclient"]}
     */
    @Getter private final List<String> mods;

    @ApiStatus.Internal
    public AlpinePlayer(Player bukkitPlayer, HandshakeData data) {
        this(bukkitPlayer, data.getPlatform(), data.getVersion(), data.getMods());
    }

    @ApiStatus.Internal
    public AlpinePlayer(Player bukkitPlayer, String platform, String version, List<String> mods) {
        this.bukkitPlayer = bukkitPlayer;
        this.platform = platform;
        this.version = version;
        this.mods = mods;
    }

    /**
     * Get the full client brand consisting of their version
     * and platform.<p>
     * e.g. {@code 1.20-fabric}
     *
     * @return the client brand
     */
    public String getClientBrand() {
        return this.version + "-" + this.platform;
    }

    /**
     * Sends a notification to this player.
     *
     * @param notification the notification to send
     */
    public void sendNotification(@NotNull Notification notification) {
        PacketNotificationAdd packet = new PacketNotificationAdd(notification);
        this.sendPacket(packet);
    }

    /**
     * Sends a waypoint to this player.
     *
     * @param waypoint the waypoint to send
     */
    public void sendWaypoint(@NotNull Waypoint waypoint) {
        PacketWaypointAdd packet = new PacketWaypointAdd(waypoint);
        this.sendPacket(packet);
    }

    /**
     * Requests the player delete a waypoint with a given UUID.
     *
     * @param id the waypoint to delete
     *
     * @since 1.2.1
     */
    public void deleteWaypoint(@NotNull UUID id) {
        PacketWaypointRemove packet = new PacketWaypointRemove(id);
        this.sendPacket(packet);
    }

    /**
     * Sends the current module settings to this player.
     */
    public void sendModules() {
        GeneralConfig config = ConfigManager.getInstance().getConfig(GeneralConfig.class);
        PacketModules packet = new PacketModules(config.modules);
        this.sendPacket(packet);
    }

    /**
     * Sends the current world name to this player.
     */
    public void sendWorldUpdate() {
        PacketWorldUpdate packet = new PacketWorldUpdate(this.bukkitPlayer.getWorld().getName());
        this.sendPacket(packet);
    }

    private void sendPacket(Packet packet) {
        this.bukkitPlayer.sendPluginMessage(Plugin.getInstance(), PlayListener.CHANNEL_ID, packet.toBytes());
    }
}

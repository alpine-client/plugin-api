/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.alpineclient.plugin.api.objects;

import com.alpineclient.plugin.PluginMain;
import com.alpineclient.plugin.api.AlpineClientApi;
import com.alpineclient.plugin.listener.plugin.PlayListener;
import com.alpineclient.plugin.network.Packet;
import com.alpineclient.plugin.network.packet.*;
import com.alpineclient.plugin.util.object.HandshakeData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
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
    private final Player bukkitPlayer;
    private final String platform;
    private final String version;
    private final List<String> mods;

    @ApiStatus.Internal
    public AlpinePlayer(@NotNull Player bukkitPlayer, @NotNull HandshakeData data) {
        this(bukkitPlayer, data.getPlatform(), data.getVersion(), data.getMods());
    }

    @ApiStatus.Internal
    public AlpinePlayer(@NotNull Player bukkitPlayer, @NotNull String platform, @NotNull String version, @NotNull List<String> mods) {
        this.bukkitPlayer = bukkitPlayer;
        this.platform = platform;
        this.version = version;
        this.mods = mods;
    }

    /**
     * Get the underlying Bukkit player object.
     *
     * @return the {@link org.bukkit.entity.Player}
     */
    public @NotNull Player getBukkitPlayer() {
        return this.bukkitPlayer;
    }

    /**
     * Get the mod platform the user is on.
     * <p>
     * e.g. {@code fabric}, {@code forge}
     *
     * @return the mod platform
     */
    public @NotNull String getPlatform() {
        return this.platform;
    }

    /**
     * Get the Minecraft version the user is on.
     * <p>
     * e.g. {@code 1.8.9}, {@code 1.20}
     *
     * @return the Minecraft version
     */
    public @NotNull String getVersion() {
        return this.version;
    }

    /**
     * Get all the user's loaded mod IDs.
     * <p>
     * e.g. {@code ["sodium", "lithium", "alpineclient"]}
     *
     * @return a list containing mod IDs
     */
    public @NotNull List<String> getMods() {
        return Collections.unmodifiableList(this.mods);
    }

    /**
     * Get the full client brand consisting of their version
     * and platform.
     * <p>
     * e.g. {@code 1.20-fabric}
     *
     * @return the client brand
     */
    public @NotNull String getClientBrand() {
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
     * Sends a cooldown to this player.
     * <p>
     * If you intend to utilize the Cooldowns module, you must first declare
     * the capability with {@link AlpineClientApi#registerCapabilities(Plugin, Capability...)}.
     *
     * @param cooldown the cooldown to send
     *
     * @since 1.3.0
     */
    public void sendCooldown(@NotNull Cooldown cooldown) {
        PacketCooldownAdd packet = new PacketCooldownAdd(cooldown);
        this.sendPacket(packet);
    }

    private void sendPacket(Packet packet) {
        this.bukkitPlayer.sendPluginMessage(PluginMain.getInstance(), PlayListener.CHANNEL_ID, packet.toBytes());
    }
}

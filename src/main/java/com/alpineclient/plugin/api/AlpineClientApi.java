package com.alpineclient.plugin.api;

import com.alpineclient.plugin.Plugin;
import com.alpineclient.plugin.api.objects.AlpinePlayer;
import com.alpineclient.plugin.api.objects.Capability;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Main wrapper class to interact with the API.
 *
 * @author Thomas Wearmouth
 * @since 1.0.0
 */
public final class AlpineClientApi {
    private static final Plugin plugin = Plugin.getInstance();

    /**
     * Fake constructor to stop attempted instantiation.
     */
    private AlpineClientApi() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This is a static utility class and cannot be instantiated");
    }

    /**
     * Check if a player is currently connected via Alpine Client.
     *
     * @param player the {@link com.alpineclient.plugin.api.objects.AlpinePlayer}
     *
     * @return {@code true} if the player is using Alpine Client
     */
    public static boolean isPlayerConnected(@NotNull AlpinePlayer player) {
        return isPlayerConnected(player.getBukkitPlayer().getUniqueId());
    }

    /**
     * Check if a player is connected via Alpine Client.
     *
     * @param player the {@link org.bukkit.entity.Player}
     *
     * @return {@code true} if the player is using Alpine Client
     */
    public static boolean isPlayerConnected(@NotNull Player player) {
        return isPlayerConnected(player.getUniqueId());
    }

    /**
     * Check if a player is connected via Alpine Client.
     *
     * @param id the {@link java.util.UUID} of the players account
     *
     * @return {@code true} if the player is using Alpine Client
     */
    public static boolean isPlayerConnected(@NotNull UUID id) {
        return plugin.getPlayerHandler().isPlayerConnected(id);
    }

    /**
     * Get a player connected via Alpine Client.
     *
     * @param player the {@link org.bukkit.entity.Player}
     *
     * @return an {@link Optional} describing the player
     *
     * @since 1.1.2
     */
    public static @NotNull Optional<AlpinePlayer> getPlayer(@NotNull Player player) {
        return Optional.ofNullable(plugin.getPlayerHandler().getConnectedPlayer(player));
    }

    /**
     * Get a player connected via Alpine Client.
     *
     * @param id the {@link java.util.UUID} of the players account
     *
     * @return an {@link Optional} describing the player
     *
     * @since 1.1.2
     */
    public static @NotNull Optional<AlpinePlayer> getPlayer(@NotNull UUID id) {
        return Optional.ofNullable(plugin.getPlayerHandler().getConnectedPlayer(id));
    }

    /**
     * Get all players connected via Alpine Client.
     *
     * @return a list containing {@link com.alpineclient.plugin.api.objects.AlpinePlayer}
     *
     * @since 1.1.2
     */
    public static @NotNull Collection<AlpinePlayer> getAllPlayers() {
        return plugin.getPlayerHandler().getConnectedPlayers();
    }


    // region Capabilities
    /**
     * Register a capability that should be communicated to any players connecting via Alpine Client.
     *
     * @param capability the {@link com.alpineclient.plugin.api.objects.Capability}
     *
     * @since 1.3.0
     */
    public static void registerCapability(@NotNull Capability capability) {
        plugin.getCapabilityHandler().addCapability(capability);
    }

    /**
     * Registers capabilities that should be communicated to any players connecting via Alpine Client.
     *
     * @param capabilities a varargs array containing {@link com.alpineclient.plugin.api.objects.Capability}
     *
     * @since 1.3.0
     */
    public static void registerCapabilities(@NotNull Capability... capabilities) {
        for (Capability capability : capabilities) {
            registerCapability(capability);
        }
    }

    /**
     * Unregister a capability that should stop being communicated to any players connecting via Alpine Client.
     *
     * @param capability the {@link com.alpineclient.plugin.api.objects.Capability}
     *
     * @since 1.3.0
     */
    public static void unregisterCapability(@NotNull Capability capability) {
        plugin.getCapabilityHandler().removeCapability(capability);
    }

    /**
     * Unregisters capabilities that should stop being communicated to any players connecting via Alpine Client.
     *
     * @param capabilities a varargs array containing {@link com.alpineclient.plugin.api.objects.Capability}
     *
     * @since 1.3.0
     */
    public static void unregisterCapabilities(@NotNull Capability... capabilities) {
        for (Capability capability : capabilities) {
            unregisterCapability(capability);
        }
    }
    // endregion
}

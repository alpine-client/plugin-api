package com.alpineclient.plugin.api;

import com.alpineclient.plugin.Plugin;
import com.alpineclient.plugin.api.objects.AlpinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
     * @return true if the player is using Alpine Client
     */
    public static boolean isPlayerConnected(@NotNull AlpinePlayer player) {
        return isPlayerConnected(player.getBukkitPlayer().getUniqueId());
    }

    /**
     * Check if a player is connected via Alpine Client.
     *
     * @param player the {@link org.bukkit.entity.Player}
     *
     * @return true if the player is using Alpine Client
     */
    public static boolean isPlayerConnected(@NotNull Player player) {
        return isPlayerConnected(player.getUniqueId());
    }

    /**
     * Check if a player is connected via Alpine Client.
     *
     * @param id the {@link java.util.UUID} of the players account
     *
     * @return true if the player is using Alpine Client
     */
    public static boolean isPlayerConnected(@NotNull UUID id) {
        return Plugin.getInstance().getPlayerHandler().isPlayerConnected(id);
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
        return Optional.ofNullable(Plugin.getInstance().getPlayerHandler().getConnectedPlayer(player));
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
        return Optional.ofNullable(Plugin.getInstance().getPlayerHandler().getConnectedPlayer(id));
    }

    /**
     * Get all players connected via Alpine Client.
     *
     * @return A list containing {@link com.alpineclient.plugin.api.objects.AlpinePlayer}
     *
     * @since 1.1.2
     */
    public static @NotNull Collection<AlpinePlayer> getAllPlayers() {
        return Plugin.getInstance().getPlayerHandler().getConnectedPlayers();
    }
}

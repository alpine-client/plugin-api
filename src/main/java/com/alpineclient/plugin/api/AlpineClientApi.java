package com.alpineclient.plugin.api;

import com.alpineclient.plugin.Plugin;
import com.alpineclient.plugin.api.objects.AlpinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

/**
 * Main wrapper class to interact with the API.
 *
 * @author Thomas Wearmouth
 * @since 1.0
 */
public final class AlpineClientApi {
    /**
     * Fake constructor to stop attempted instantiation.
     */
    private AlpineClientApi() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This is a static utility class and cannot be instantiated");
    }

    /**
     * Check if a player is currently connected.
     *
     * @param player The {@link com.alpineclient.plugin.api.objects.AlpinePlayer}
     *
     * @return Whether the player is connected
     */
    public static boolean isPlayerConnected(@NotNull AlpinePlayer player) {
        return isPlayerConnected(player.getBukkitPlayer().getUniqueId());
    }

    /**
     * Check if a player is currently connected.
     *
     * @param player The {@link org.bukkit.entity.Player}
     *
     * @return Whether the player is connected
     */
    public static boolean isPlayerConnected(@NotNull Player player) {
        return isPlayerConnected(player.getUniqueId());
    }

    /**
     * Check if a player is currently connected.
     *
     * @param id The player's {@link java.util.UUID}
     *
     * @return Whether the player is connected
     */
    public static boolean isPlayerConnected(@NotNull UUID id) {
        return Plugin.getInstance().getPlayerHandler().isPlayerConnected(id);
    }

    /**
     * Get a currently connected player.
     *
     * @param player The {@link org.bukkit.entity.Player}
     *
     * @return The {@link com.alpineclient.plugin.api.objects.AlpinePlayer}
     */
    public static @Nullable AlpinePlayer getConnectedPlayer(@NotNull Player player) {
        return Plugin.getInstance().getPlayerHandler().getConnectedPlayer(player);
    }

    /**
     * Get a currently connected player.
     *
     * @param id The player's {@link java.util.UUID}
     *
     * @return The {@link com.alpineclient.plugin.api.objects.AlpinePlayer}
     */
    public static @Nullable AlpinePlayer getConnectedPlayer(@NotNull UUID id) {
        return Plugin.getInstance().getPlayerHandler().getConnectedPlayer(id);
    }

    /**
     * Get currently connected players.
     *
     * @return A list containing {@link com.alpineclient.plugin.api.objects.AlpinePlayer}
     */
    public static @NotNull Collection<AlpinePlayer> getConnectedPlayers() {
        return Plugin.getInstance().getPlayerHandler().getConnectedPlayers();
    }
}

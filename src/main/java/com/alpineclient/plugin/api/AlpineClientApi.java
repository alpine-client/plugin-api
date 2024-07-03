package com.alpineclient.plugin.api;

import com.alpineclient.plugin.PluginMain;
import com.alpineclient.plugin.api.objects.AlpinePlayer;
import com.alpineclient.plugin.api.objects.Capability;
import com.alpineclient.plugin.handler.CapabilityHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
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
    private static final PluginMain MAIN = PluginMain.getInstance();

    /**
     * Fake constructor to stop attempted instantiation.
     */
    private AlpineClientApi() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This is a static utility class and cannot be instantiated");
    }

    /**
     * Check if a player is currently connected via Alpine Client.
     *
     * @param player the {@link AlpinePlayer}
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
     * @param id the {@link UUID} of the players account
     *
     * @return {@code true} if the player is using Alpine Client
     */
    public static boolean isPlayerConnected(@NotNull UUID id) {
        return MAIN.getPlayerHandler().isPlayerConnected(id);
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
        return Optional.ofNullable(MAIN.getPlayerHandler().getConnectedPlayer(player));
    }

    /**
     * Get a player connected via Alpine Client.
     *
     * @param id the {@link UUID} of the players account
     *
     * @return an {@link Optional} describing the player
     *
     * @since 1.1.2
     */
    public static @NotNull Optional<AlpinePlayer> getPlayer(@NotNull UUID id) {
        return Optional.ofNullable(MAIN.getPlayerHandler().getConnectedPlayer(id));
    }

    /**
     * Get all players connected via Alpine Client.
     *
     * @return a list containing {@link AlpinePlayer}
     *
     * @since 1.1.2
     */
    public static @NotNull Collection<AlpinePlayer> getAllPlayers() {
        return MAIN.getPlayerHandler().getConnectedPlayers();
    }


    // region Capabilities
    /**
     * Registers Alpine Client capabilities for a given plugin.
     * <p>
     * Should be called once for all your desired capabilities on plugin enable.
     *
     * @param plugin the responsible {@link Plugin}
     * @param capabilities a varargs array containing {@link Capability}
     *
     * @see Plugin#onEnable()
     *
     * @since 1.3.0
     */
    public static void registerCapabilities(@NotNull Plugin plugin, @NotNull Capability... capabilities) {
        CapabilityHandler handler = MAIN.getCapabilityHandler();
        for (Capability capability : capabilities) {
            handler.register(plugin, capability);
        }
    }

    /**
     * Unregister all Alpine Client capabilities for a given plugin.
     * <p>
     * Should be called once on plugin disable.
     *
     * @param plugin the responsible {@link Plugin}
     *
     * @see Plugin#onDisable()
     *
     * @since 1.3.0
     */
    public static void unregisterCapabilities(@NotNull Plugin plugin) {
        MAIN.getCapabilityHandler().unregister(plugin);
    }
    // endregion
}

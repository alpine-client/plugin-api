package com.alpineclient.plugin.api.event;

import com.alpineclient.plugin.api.objects.AlpinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Called whenever a user who had previously completed a handshake disconnects.
 *
 * @author Thomas Wearmouth
 * @since 1.0.0
 */
public final class ClientDisconnectEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final AlpinePlayer player;

    @ApiStatus.Internal
    public ClientDisconnectEvent(@NotNull AlpinePlayer player) {
        this.player = player;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    @ApiStatus.Internal
    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }

    public @NotNull AlpinePlayer getPlayer() {
        return this.player;
    }
}

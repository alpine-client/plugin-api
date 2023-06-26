package com.alpineclient.plugin.api.event;

import com.alpineclient.plugin.api.objects.AlpinePlayer;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Called whenever a user successfully completes a handshake.
 *
 * @author Thomas Wearmouth
 * @since 1.0
 */
public final class ClientHandshakeEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    @Getter private final AlpinePlayer player;

    @ApiStatus.Internal
    public ClientHandshakeEvent(AlpinePlayer player) {
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
}

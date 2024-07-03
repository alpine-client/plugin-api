/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.alpineclient.plugin.api.event;

import com.alpineclient.plugin.api.objects.AlpinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Called whenever a user successfully completes a handshake.
 *
 * @author Thomas Wearmouth
 * @since 1.0.0
 */
public final class ClientHandshakeEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final AlpinePlayer player;

    @ApiStatus.Internal
    public ClientHandshakeEvent(@NotNull AlpinePlayer player) {
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

    /**
     * Get the player which this event pertains to.
     *
     * @return the {@link AlpinePlayer}
     */
    public @NotNull AlpinePlayer getPlayer() {
        return this.player;
    }
}

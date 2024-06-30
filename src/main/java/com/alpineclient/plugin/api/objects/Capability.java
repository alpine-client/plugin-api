package com.alpineclient.plugin.api.objects;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Describes a capability of the server. The client will either enable or disable functionality when capabilities
 * are communicated to it directly after the handshake.
 *
 * @author Thomas Wearmouth
 * @since 1.3.0
 */
public enum Capability {
    /**
     * The client should enable the cooldowns module as the server intends to use it.
     */
    COOLDOWNS("alpineclient.cooldowns");

    private final String id;

    Capability(@NotNull String id) {
        this.id = id;
    }

    public @NotNull String getId() {
        return this.id;
    }

    @ApiStatus.Internal
    public static @Nullable Capability fromId(@NotNull String id) {
        for (Capability capability : values()) {
            if (id.equals(capability.getId())) {
                return capability;
            }
        }
        return null;
    }
}

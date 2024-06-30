package com.alpineclient.plugin.handler;

import com.alpineclient.plugin.api.objects.Capability;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Thomas Wearmouth
 * Created on 29/06/2024
 */
@ApiStatus.Internal
public final class CapabilityHandler {
    private final Set<Capability> capabilities = new HashSet<>();

    public boolean addCapability(@NotNull Capability capability) {
        return this.capabilities.add(capability);
    }

    public boolean removeCapability(@NotNull Capability capability) {
        return this.capabilities.remove(capability);
    }

    public boolean hasCapability(@NotNull Capability capability) {
        return this.capabilities.contains(capability);
    }

    public @NotNull Collection<Capability> getCapabilities() {
        return Collections.unmodifiableSet(this.capabilities);
    }
}

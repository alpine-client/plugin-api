package com.alpineclient.plugin.handler;

import com.alpineclient.plugin.api.objects.Capability;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Thomas Wearmouth
 * Created on 29/06/2024
 */
@ApiStatus.Internal
public final class CapabilityHandler {
    private final Set<CapabilityRegistration> registrations = new HashSet<>();

    public void register(@NotNull Plugin plugin, @NotNull Capability capability) {
        this.registrations.add(new CapabilityRegistration(plugin, capability));
    }

    public void unregister(@NotNull Plugin plugin) {
        this.registrations.removeIf(r -> r.getPlugin().equals(plugin));
    }

    public boolean hasCapability(@NotNull Capability capability) {
        return this.getUniqueCapabilities().contains(capability);
    }

    public @NotNull Collection<Capability> getCapabilities() {
        return Collections.unmodifiableSet(this.getUniqueCapabilities());
    }

    private Set<Capability> getUniqueCapabilities() {
        return this.registrations.stream()
                .map(CapabilityRegistration::getCapability)
                .collect(Collectors.toSet());
    }

    @Getter
    private static final class CapabilityRegistration {
        private final Plugin plugin;
        private final Capability capability;

        private CapabilityRegistration(Plugin plugin, Capability capability) {
            this.plugin = plugin;
            this.capability = capability;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (!(obj instanceof CapabilityRegistration))
                return false;

            CapabilityRegistration other = (CapabilityRegistration) obj;
            return this.plugin.equals(other.getPlugin()) && this.capability == other.getCapability();
        }

        @Override
        public int hashCode() {
            int result = this.plugin.hashCode();
            result = 31 * result + this.capability.hashCode();
            return result;
        }
    }
}

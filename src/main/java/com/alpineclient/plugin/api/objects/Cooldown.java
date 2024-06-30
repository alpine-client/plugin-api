package com.alpineclient.plugin.api.objects;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a location in the world that is highlighted to the user.
 *
 * @author Thomas Wearmouth
 * @since 1.3.0
 */
public final class Cooldown {
    /**
     * The default color used in {@link Cooldown.Builder}.
     */
    public static final int DEFAULT_COLOR = new Color(255, 255, 255, 255).getRGB();

    private final UUID id;
    private final String name;
    private final int color;
    private final long duration;
    private final ClientResource texture;

    private Cooldown(@NotNull UUID id, @NotNull String name, int color, long duration, @NotNull ClientResource texture) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.duration = duration;
        this.texture = texture;
    }

    /**
     * Get the name of the cooldown.
     *
     * @return the name
     */
    public @NotNull UUID getId() {
        return this.id;
    }

    /**
     * Get the name of the cooldown.
     *
     * @return the name
     */
    public @NotNull String getName() {
        return this.name;
    }

    /**
     * Get the color of the cooldown.
     *
     * @return the name
     */
    public int getColor() {
        return this.color;
    }

    /**
     * Get the duration of the cooldown in milliseconds.
     *
     * @return the duration in milliseconds
     */
    public long getDuration() {
        return this.duration;
    }

    /**
     * Get the texture of the notification.
     *
     * @return the {@link ClientResource}
     */
    public @NotNull ClientResource getTexture() {
        return this.texture;
    }

    @Override
    public @NotNull String toString() {
        return String.format(
                "Cooldown{id=%s, name=%s, color=%d, duration=%d, texture=%s}",
                this.id, this.name, this.color, this.duration, Objects.toString(this.texture)
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;

        Cooldown other = (Cooldown) obj;
        return Objects.equals(this.id, other.getId()) && Objects.equals(this.name, other.getName()) && this.color == other.getColor()
                && this.duration == other.getDuration() && Objects.equals(this.texture, other.getTexture());
    }

    @Override
    public int hashCode() {
        int result = this.id.hashCode();
        result = 31 * result + this.name.hashCode();
        result = 31 * result + this.color;
        result = 31 * result + Long.hashCode(this.duration);
        result = 31 * result + this.texture.hashCode();
        return result;
    }

    /**
     * Begins a new builder of this class.
     *
     * @return The builder
     */
    public static @NotNull Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link Cooldown}.
     */
    public static class Builder {
        private UUID id = UUID.randomUUID();
        private String name = null;
        private int color = Cooldown.DEFAULT_COLOR;
        private long duration = 0L;
        private ClientResource texture = null;

        /**
         * Sets the ID to be built into the {@link Cooldown}.
         *
         * @param id the ID
         * @return the builder
         */
        public @NotNull Builder id(@NotNull UUID id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the name to be built into the {@link Cooldown}.
         *
         * @param name the name
         * @return the builder
         */
        public @NotNull Builder name(@NotNull String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the color to be built into the {@link Cooldown}.
         *
         * @param color the color
         * @return the builder
         */
        public @NotNull Builder color(int color) {
            this.color = color;
            return this;
        }

        /**
         * Sets the color to be built into the {@link Cooldown}.
         *
         * @param color the color
         * @return the builder
         */
        public @NotNull Builder color(@NotNull Color color) {
            return this.color(color.getRGB());
        }

        /**
         * Sets the duration to be built into the {@link Cooldown}.
         *
         * @param duration the duration
         * @return the builder
         */
        public @NotNull Builder duration(long duration) {
            this.duration = duration;
            return this;
        }

        /**
         * Sets the texture to be built into the {@link Cooldown}.
         *
         * @param texture the texture
         * @return the builder
         */
        public @NotNull Builder texture(@NotNull ClientResource texture) {
            this.texture = texture;
            return this;
        }

        /**
         * Assembles the {@link Cooldown}.
         *
         * @return the built {@link Cooldown}
         */
        public @NotNull Cooldown build() {
            Preconditions.checkNotNull(this.id);
            Preconditions.checkNotNull(this.name);
            Preconditions.checkNotNull(this.texture);
            Preconditions.checkState(this.duration >= 1L, "duration must be >= 1");
            return new Cooldown(this.id, this.name, this.color, this.duration, this.texture);
        }
    }
}

package com.alpineclient.plugin.api.objects;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.UUID;

/**
 * Represents a location in the world that is highlighted to the user.
 *
 * @author Thomas Wearmouth
 * @since 1.0.0
 */
public final class Waypoint {
    /**
     * The default color used in {@link Waypoint.Builder}.
     */
    public static final int DEFAULT_COLOR = new Color(78, 75, 214).getRGB();
    /**
     * The default world name used in {@link Waypoint.Builder}.
     */
    public static final String DEFAULT_WORLD = "unknown_world";
    /**
     * The default duration used in {@link Waypoint.Builder}.
     */
    public static final long DEFAULT_DURATION = 5000L;
    /**
     * The value to be used for any {@link Waypoint} intended to last an infinite amount of time.
     */
    public static final long NO_DURATION = -1L;

    private final UUID id = UUID.randomUUID();
    private final String name;
    private final Location pos;
    private final int color;
    private final String world;
    private final long duration;

    private Waypoint(@NotNull String name, @NotNull Location pos, int color, @NotNull String world, long duration) {
        this.name = name;
        this.pos = pos;
        this.color = color;
        this.world = world;
        this.duration = duration;
    }

    /**
     * Get the unique ID of the waypoint.
     *
     * @return the {@link java.util.UUID}
     */
    public @NotNull UUID getId() {
        return this.id;
    }

    /**
     * Get the name of the waypoint.
     *
     * @return the name
     */
    public @NotNull String getName() {
        return this.name;
    }

    /**
     * Get the position of the waypoint.
     *
     * @return the position
     */
    public @NotNull Location getPos() {
        return this.pos;
    }

    /**
     * Get the color of the waypoint.
     *
     * @return the name
     */
    public int getColor() {
        return this.color;
    }

    /**
     * Get the name of the world that the waypoint is in.
     *
     * @return the name of the world
     */
    public @NotNull String getWorld() {
        return this.world;
    }

    /**
     * Get the duration of the waypoint in milliseconds.
     *
     * @return the duration in milliseconds
     */
    public long getDuration() {
        return this.duration;
    }

    @Override
    public @NotNull String toString() {
        return String.format(
                "Waypoint{id=%s, name=%s, pos=[%d, %d, %d], color=%d, world=%s, duration=%d}",
                this.id, this.name,
                this.pos.getBlockX(), this.pos.getBlockY(), this.pos.getBlockZ(),
                this.color, this.world, this.duration
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

        Waypoint other = (Waypoint) obj;
        return this.id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    /**
     * Begins a new builder of this class.
     *
     * @return the builder
     */
    public static @NotNull Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link Waypoint}.
     */
    public static class Builder {
        private String name = null;
        private Location pos = null;
        private int color = DEFAULT_COLOR;
        private String world = DEFAULT_WORLD;
        private long duration = DEFAULT_DURATION;

        /**
         * Sets the name to be built into the {@link Waypoint}.
         *
         * @param name the name
         * @return the builder
         */
        public @NotNull Builder name(@NotNull String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the position to be built into the {@link Waypoint}.
         *
         * @param pos the position
         * @return the builder
         */
        public @NotNull Builder pos(@NotNull Location pos) {
            this.pos = pos;
            return this;
        }


        /**
         * Sets the color to be built into the {@link Waypoint}.
         *
         * @param color the color
         * @return the builder
         */
        public @NotNull Builder color(int color) {
            this.color = color;
            return this;
        }

        /**
         * Sets the world name to be built into the {@link Waypoint}.
         *
         * @param world the world name
         * @return the builder
         */
        public @NotNull Builder world(@NotNull String world) {
            this.world = world;
            return this;
        }

        /**
         * Sets the world name to be built into the {@link Waypoint}.
         *
         * @param worldObj the world object
         * @return the builder
         */
        public @NotNull Builder world(@NotNull World worldObj) {
            this.world = worldObj.getName();
            return this;
        }

        /**
         * Sets the duration to be built into the {@link Waypoint}.
         *
         * @param duration the duration
         * @return the builder
         */
        public @NotNull Builder duration(long duration) {
            this.duration = duration;
            return this;
        }

        /**
         * Sets the duration to be built into the {@link Waypoint} to infinite.
         *
         * @return the builder
         */
        public @NotNull Builder infinite() {
            this.duration = NO_DURATION;
            return this;
        }

        /**
         * Assembles the {@link Waypoint}.
         *
         * @return the built {@link Waypoint}
         */
        public @NotNull Waypoint build() {
            Preconditions.checkNotNull(this.name);
            Preconditions.checkNotNull(this.pos);
            Preconditions.checkNotNull(this.world);
            return new Waypoint(this.name, this.pos, this.color, this.world, this.duration);
        }
    }
}

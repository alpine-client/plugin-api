package com.alpineclient.plugin.api.objects;

import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Represents a location in the world that is highlighted to the user.
 *
 * @author Thomas Wearmouth
 * @since 1.0
 */
@Getter
@ToString @EqualsAndHashCode
public final class Waypoint {
    /**
     * The default color used in {@link Builder}
     */
    public static final int DEFAULT_COLOR = new Color(78, 75, 214).getRGB();
    /**
     * The default world name used in {@link Builder}
     */
    public static final String DEFAULT_WORLD = "unknown_world";
    /**
     * The default duration used in {@link Builder}
     */
    public static final long DEFAULT_DURATION = 5000L;
    /**
     * The value to be used for any {@link Waypoint} intended to last an infinite amount of time.
     */
    public static final long NO_DURATION = -1L;

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
     * Begins a new builder of this class
     *
     * @return The builder
     */
    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link Waypoint}
     */
    public static class Builder {
        private String name = null;
        private Location pos = null;
        private int color = DEFAULT_COLOR;
        private String world = DEFAULT_WORLD;
        private long duration = DEFAULT_DURATION;

        /**
         * Sets the name to be built into the {@link Waypoint}
         *
         * @param name The name
         * @return The builder
         */
        @NotNull
        public Builder name(@NotNull String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the position to be built into the {@link Waypoint}
         *
         * @param pos The position
         * @return The builder
         */
        @NotNull
        public Builder pos(@NotNull Location pos) {
            this.pos = pos;
            return this;
        }


        /**
         * Sets the color to be built into the {@link Waypoint}
         *
         * @param color The color
         * @return The builder
         */
        @NotNull
        public Builder color(int color) {
            this.color = color;
            return this;
        }

        /**
         * Sets the world name to be built into the {@link Waypoint}
         *
         * @param world The world name
         * @return The builder
         */
        @NotNull
        public Builder world(@NotNull String world) {
            this.world = world;
            return this;
        }

        /**
         * Sets the duration to be built into the {@link Waypoint}
         *
         * @param duration The duration
         * @return The builder
         */
        @NotNull
        public Builder duration(long duration) {
            this.duration = duration;
            return this;
        }

        /**
         * Sets the duration to be built into the {@link Waypoint} to infinite
         *
         * @return The builder
         */
        @NotNull
        public Builder infinite() {
            this.duration = NO_DURATION;
            return this;
        }

        /**
         * Assembles the {@link Waypoint}
         *
         * @return The built {@link Waypoint}
         */
        @NotNull
        public Waypoint build() {
            Preconditions.checkNotNull(this.name);
            Preconditions.checkNotNull(this.pos);
            Preconditions.checkNotNull(this.world);
            return new Waypoint(this.name, this.pos, this.color, this.world, this.duration);
        }
    }
}

package com.alpineclient.plugin.api.objects;

import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an object that displays a toast popup on the user's screen.
 *
 * @author BestBearr
 * @since 1.0.0
 */
@Getter
@ToString @EqualsAndHashCode
public final class Notification {
    /**
     * The default color used in {@link Builder}
     */
    public static final int DEFAULT_COLOR = -2;
    /**
     * The default display duration used in {@link Builder}
     */
    public static final long DEFAULT_DISPLAY_DURATION = 5000L;

    private final String title;
    private final String description;
    private final int color;
    private final long duration;
    private final ClientResource texture;

    private Notification(@Nullable String title, @NotNull String description, int color, long duration, @Nullable ClientResource texture) {
        this.title = title;
        this.description = description;
        this.color = color;
        this.duration = duration;
        this.texture = texture;
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
     * Builder for {@link Notification}
     */
    public static class Builder {
        private String title = null;
        private String description;
        private int color = Notification.DEFAULT_COLOR;
        private long duration = DEFAULT_DISPLAY_DURATION;
        private ClientResource texture = null;

        /**
         * Sets the title to be built into the {@link Notification}
         *
         * @param title The title
         * @return The builder
         */
        @NotNull
        public Builder title(@NotNull String title) {
            this.title = title;
            return this;
        }

        /**
         * Sets the description to be built into the {@link Notification}
         *
         * @param description The description
         * @return The builder
         */
        @NotNull
        public Builder description(@NotNull String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the color to be built into the {@link Notification}
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
         * Sets the duration to be built into the {@link Notification}
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
         * Sets the texture to be built into the {@link Notification}
         *
         * @param texture The texture
         * @return The builder
         */
        @NotNull
        public Builder texture(@NotNull ClientResource texture) {
            this.texture = texture;
            return this;
        }

        /**
         * Assembles the {@link Notification}
         *
         * @return The built {@link Notification}
         */
        @NotNull
        public Notification build() {
            Preconditions.checkNotNull(this.description);
            return new Notification(this.title, this.description, this.color, this.duration, this.texture);
        }
    }
}

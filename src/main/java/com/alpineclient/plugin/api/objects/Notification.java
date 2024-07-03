/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.alpineclient.plugin.api.objects;

import com.alpineclient.plugin.api.util.Resources;
import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Objects;

/**
 * Represents an object that displays a toast popup on the user's screen.
 *
 * @author BestBearr
 * @since 1.0.0
 */
public final class Notification {
    /**
     * The default color used in {@link Notification.Builder}.
     */
    public static final int DEFAULT_COLOR = new Color(255, 255, 255, 255).getRGB();
    /**
     * The default display duration used in {@link Notification.Builder}.
     */
    public static final long DEFAULT_DISPLAY_DURATION = 5000L;
    /**
     * The default display icon used in {@link Notification.Builder}.
     *
     * @since 1.3.0
     */
    public static final ClientResource DEFAULT_TEXTURE = Resources.LETTER_A_LOGO;

    private final String title;
    private final String description;
    private final int color;
    private final long duration;
    private final ClientResource texture;

    private Notification(@Nullable String title, @NotNull String description, int color, long duration, @NotNull ClientResource texture) {
        this.title = title;
        this.description = description;
        this.color = color;
        this.duration = duration;
        this.texture = texture;
    }

    /**
     * Get the title of the notification.
     *
     * @return the title
     */
    public @Nullable String getTitle() {
        return this.title;
    }

    /**
     * Get the description of the notification.
     *
     * @return the description
     */
    public @NotNull String getDescription() {
        return this.description;
    }

    /**
     * Get the color of the notification.
     *
     * @return the color
     */
    public int getColor() {
        return this.color;
    }

    /**
     * Get the duration of the notification in milliseconds.
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
                "Notification{title=%s, description=%s, color=%d, duration=%d, texture=%s}",
                this.title, this.description, this.color, this.duration, Objects.toString(this.texture)
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

        Notification other = (Notification) obj;
        return Objects.equals(this.title, other.getTitle()) && Objects.equals(this.description, other.getDescription())
                && this.color == other.getColor() && this.duration == other.getDuration()
                && Objects.equals(this.texture, other.getTexture());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(this.title);
        result = 31 * result + this.description.hashCode();
        result = 31 * result + this.color;
        result = 31 * result + Long.hashCode(this.duration);
        result = 31 * result + Objects.hashCode(this.texture);
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
     * Builder for {@link Notification}.
     */
    public static class Builder {
        private String title = null;
        private String description;
        private int color = Notification.DEFAULT_COLOR;
        private long duration = Notification.DEFAULT_DISPLAY_DURATION;
        private ClientResource texture = Notification.DEFAULT_TEXTURE;

        /**
         * Sets the title to be built into the {@link Notification}.
         *
         * @param title the title
         * @return the builder
         */
        public @NotNull Builder title(@NotNull String title) {
            this.title = title;
            return this;
        }

        /**
         * Sets the description to be built into the {@link Notification}.
         *
         * @param description the description
         * @return the builder
         */
        public @NotNull Builder description(@NotNull String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the color to be built into the {@link Notification}.
         *
         * @param color the color
         * @return the builder
         */
        public @NotNull Builder color(int color) {
            this.color = color;
            return this;
        }

        /**
         * Sets the color to be built into the {@link Notification}.
         *
         * @param color the color
         * @return the builder
         *
         * @since 1.3.0
         */
        public @NotNull Builder color(@NotNull Color color) {
            return this.color(color.getRGB());
        }

        /**
         * Sets the duration to be built into the {@link Notification}.
         *
         * @param duration the duration
         * @return the builder
         */
        public @NotNull Builder duration(long duration) {
            this.duration = duration;
            return this;
        }

        /**
         * Sets the texture to be built into the {@link Notification}.
         *
         * @param texture the texture
         * @return the builder
         */
        public @NotNull Builder texture(@NotNull ClientResource texture) {
            this.texture = texture;
            return this;
        }

        /**
         * Assembles the {@link Notification}.
         *
         * @return the built {@link Notification}
         */
        public @NotNull Notification build() {
            Preconditions.checkNotNull(this.description);
            Preconditions.checkNotNull(this.texture);
            return new Notification(this.title, this.description, this.color, this.duration, this.texture);
        }
    }
}

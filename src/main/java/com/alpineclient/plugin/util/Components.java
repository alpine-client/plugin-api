/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.alpineclient.plugin.util;

import com.alpineclient.plugin.config.AbstractConfig;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author BestBearr
 * Created on 06/06/23
 */
@UtilityClass
@ApiStatus.Internal
public final class Components {
    public static @NotNull Component reset() {
        Style.Builder style = Style.style().color(TextColor.color(-1));
        for (TextDecoration value : TextDecoration.values())
            style.decoration(value, TextDecoration.State.FALSE);
        return Component.text("").color(TextColor.color(-1)).style(style.build());
    }

    public static @NotNull Component join(@NotNull Component... components) {
        return reset().append(Component.join(JoinConfiguration.noSeparators(), components)).compact();
    }

    public static @NotNull Component join(@NotNull Iterable<Component> components) {
        return reset().append(Component.join(JoinConfiguration.noSeparators(), components)).compact();
    }

    public static @NotNull Component joinSpaces(@NotNull Component... components) {
        return reset().append(Component.join(JoinConfiguration.separator(Component.space()), components)).compact();
    }

    public static @NotNull Component joinSpaces(@NotNull Iterable<Component> components) {
        return reset().append(Component.join(JoinConfiguration.separator(Component.space()), components)).compact();
    }

    public static @NotNull Component joinNewLines(@NotNull Component... components) {
        return reset().append(Component.join(JoinConfiguration.newlines(), components)).compact();
    }

    public static @NotNull Component joinNewLines(@NotNull Iterable<Component> components) {
        return reset().append(Component.join(JoinConfiguration.newlines(), components)).compact();
    }

    public static Component brackets(@NotNull Component value) {
        return reset().append(
                join(
                        Component.text("[").color(AbstractConfig.DARK_DIVIDER_COLOR),
                        value.color(AbstractConfig.DIVIDER_COLOR),
                        Component.text("]").color(AbstractConfig.DARK_DIVIDER_COLOR)
                )
        ).compact();
    }
}

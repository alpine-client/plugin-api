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
    @NotNull
    public static Component reset() {
        Style.Builder style = Style.style().color(TextColor.color(-1));
        for (TextDecoration value : TextDecoration.values())
            style.decoration(value, TextDecoration.State.FALSE);
        return Component.text("").color(TextColor.color(-1)).style(style.build());
    }

    @NotNull
    public static Component join(@NotNull Component... components) {
        return reset().append(Component.join(JoinConfiguration.noSeparators(), components)).compact();
    }

    @NotNull
    public static Component join(@NotNull Iterable<Component> components) {
        return reset().append(Component.join(JoinConfiguration.noSeparators(), components)).compact();
    }

    @NotNull
    public static Component joinSpaces(@NotNull Component... components) {
        return reset().append(Component.join(JoinConfiguration.separator(Component.space()), components)).compact();
    }

    @NotNull
    public static Component joinSpaces(@NotNull Iterable<Component> components) {
        return reset().append(Component.join(JoinConfiguration.separator(Component.space()), components)).compact();
    }

    @NotNull
    public static Component joinNewLines(@NotNull Component... components) {
        return reset().append(Component.join(JoinConfiguration.newlines(), components)).compact();
    }

    @NotNull
    public static Component joinNewLines(@NotNull Iterable<Component> components) {
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

package com.alpineclient.plugin.util;

import com.alpineclient.plugin.Reference;
import com.alpineclient.plugin.config.AbstractConfig;
import com.alpineclient.plugin.config.impl.MessageConfig;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author BestBearr
 * Created on 06/06/23
 */
@UtilityClass
public final class Components {

    @Setter
    private static MessageConfig messageConfig;

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

    @NotNull
    public static Component list(@NotNull Component... components) {
        Component[] list = Arrays.stream(components)
                .map(v -> messageConfig.listItem.build("value", Reference.MINI_MESSAGE.serialize(v)))
                .collect(Collectors.toList())
                .toArray(new Component[0]);
        return joinNewLines(list).compact();
    }

    @NotNull
    public static Component map(@NotNull Map<Object, Component> map) {
        Component[] list = map.entrySet().stream()
                .map(v -> messageConfig.mapListItem.build("key", v.getKey(), "value", Reference.MINI_MESSAGE.serialize(v.getValue())))
                .collect(Collectors.toList())
                .toArray(new Component[0]);
        return joinNewLines(list).compact();
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

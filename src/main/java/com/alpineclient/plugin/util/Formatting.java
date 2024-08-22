/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.alpineclient.plugin.util;

import com.alpineclient.plugin.Reference;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * @author BestBearr
 * Created on 07/06/23
 */
@UtilityClass
@ApiStatus.Internal
public final class Formatting {
    private static final Map<ChatColor, Integer> COLOR_HEX_CODES = CollectionUtils.map(
            ChatColor.DARK_RED, 0xFFAA0000,
            ChatColor.RED, 0xFFFF5555,
            ChatColor.GOLD, 0xFFFFAA00,
            ChatColor.YELLOW, 0xFFFFFF55,
            ChatColor.DARK_GREEN, 0xFF00AA00,
            ChatColor.GREEN, 0xFF55FF55,
            ChatColor.DARK_AQUA, 0xFF00AAAA,
            ChatColor.AQUA, 0xFF55FFFF,
            ChatColor.DARK_BLUE, 0xFF0000AA,
            ChatColor.BLUE, 0xFF5555FF,
            ChatColor.DARK_PURPLE, 0xFFAA00AA,
            ChatColor.LIGHT_PURPLE, 0xFFFF55FF,
            ChatColor.BLACK, 0xFF000000,
            ChatColor.DARK_GRAY, 0xFF555555,
            ChatColor.GRAY, 0xFFAAAAAA,
            ChatColor.WHITE, 0xFFFFFFFF
    );

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    /**
     * Retrieve the hex code for a chat formatting code.
     * <br>
     * Returns {@code -1} for non-color codes.
     *
     * @param color The Bukkit {@link ChatColor}
     * @return The corresponding hex code.
     */
    public static int getHexColor(@NotNull ChatColor color) {
        if (color.isColor()) {
            return COLOR_HEX_CODES.get(color);
        }
        else return -1;
    }

    /**
     * Formats a material name to a more readable format.
     *
     * @param stack The ItemStack to format
     * @return The formatted name.
     * BLACK_CONCRETE_POWDER -> Black Concrete Powder
     * WHITE_WOOL -> White Wool
     * RED_SAND -> Red Sand
     */
    public static @NotNull String formatItemStack(@NotNull ItemStack stack) {
        return formatItemStack(stack, false);
    }


    /**
     * Formats a material name to a more readable format.
     *
     * @param stack The ItemStack to format
     * @return The formatted name.
     * BLACK_CONCRETE_POWDER -> Black Concrete Powder
     * WHITE_WOOL -> White Wool
     * RED_SAND -> Red Sand
     */
    public static @NotNull String formatItemStack(@NotNull ItemStack stack, boolean useCustomName) {
        if (useCustomName && stack.hasItemMeta()) {
            String[] split = stack.getItemMeta().getDisplayName().toLowerCase().split("[_ ]+");
            StringBuilder builder = new StringBuilder();
            for (String str : split) {
                builder.append(str.substring(0, 1).toUpperCase()).append(str.substring(1)).append(" ");
            }
            return builder.toString().trim();
        }

        return formatEnumeration(stack.getType());
    }


    /**
     * Formats an enum name to a more readable format.
     *
     * @param anEnum The enum to format
     * @return The formatted name.
     * BLACK_CONCRETE_POWDER -> Black Concrete Powder
     * WHITE_WOOL -> White Wool
     * RED_SAND -> Red Sand
     */
    public static @NotNull String formatEnumeration(@NotNull Enum<?> anEnum) {
        String[] split = anEnum.name().toLowerCase().split("[_ ]+");
        StringBuilder builder = new StringBuilder();
        for (String str : split) {
            builder.append(str.substring(0, 1).toUpperCase()).append(str.substring(1)).append(" ");
        }
        return builder.toString().trim();
    }

    public @NotNull static String formatPlaceholders(@Nullable String string, @NotNull Object... placeholders) {
        if (string == null)
            return "";
        if (placeholders.length == 0)
            return string;

        if (placeholders.length == 1) {
            // Replace all placeholders with given value
            string = string.replaceAll("%\\w+%", placeholders[0].toString());
        }
        else
            for (int i = 0; i < (placeholders.length / 2) * 2; i += 2) {
                String placeholder = (String) placeholders[i];
                Object rawReplacer = placeholders[i + 1];
                String formattedReplacer;

                if (rawReplacer instanceof Float || rawReplacer instanceof Double) formattedReplacer = DECIMAL_FORMAT.format(rawReplacer);
                else if (rawReplacer instanceof Boolean) {
                    boolean bl = (Boolean) rawReplacer;
                    formattedReplacer = bl ? "True" : "False";
                }
                else if (rawReplacer instanceof Component) {
                    Component cp = (Component) rawReplacer;
                    formattedReplacer = Reference.MINI_MESSAGE.serialize(cp);
                }
                else formattedReplacer = rawReplacer.toString();

                string = string.replaceAll("%" + placeholder + "%", formattedReplacer);
            }

        return string;
    }
}

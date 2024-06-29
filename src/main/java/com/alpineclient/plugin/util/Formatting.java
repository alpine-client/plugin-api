package com.alpineclient.plugin.util;

import com.alpineclient.plugin.Reference;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;

/**
 * @author BestBearr
 * Created on 07/06/23
 */
@UtilityClass
@ApiStatus.Internal
public final class Formatting {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    /**
     * Formats a material name to a more readable format.
     *
     * @param stack The ItemStack to format
     * @return The formatted name.
     * BLACK_CONCRETE_POWDER -> Black Concrete Powder
     * WHITE_WOOL -> White Wool
     * RED_SAND -> Red Sand
     */
    @NotNull
    public static String formatItemStack(@NotNull ItemStack stack) {
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
    @NotNull
    public static String formatItemStack(@NotNull ItemStack stack, boolean useCustomName) {
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
    @NotNull
    public static String formatEnumeration(@NotNull Enum<?> anEnum) {
        String[] split = anEnum.name().toLowerCase().split("[_ ]+");
        StringBuilder builder = new StringBuilder();
        for (String str : split) {
            builder.append(str.substring(0, 1).toUpperCase()).append(str.substring(1)).append(" ");
        }
        return builder.toString().trim();
    }

    @NotNull
    public static String formatPlaceholders(@Nullable String string, @NotNull Object... placeholders) {
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

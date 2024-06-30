package com.alpineclient.plugin.config.object;

import com.alpineclient.plugin.Reference;
import com.alpineclient.plugin.config.impl.MessageConfig;
import com.alpineclient.plugin.util.Components;
import com.alpineclient.plugin.util.Formatting;
import de.exlll.configlib.Configuration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author BestBearr
 * Created on 06/06/23
 */
@AllArgsConstructor @NoArgsConstructor
@Getter @Configuration
public final class ConfigMessage {
    private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.builder()
            .character(LegacyComponentSerializer.SECTION_CHAR)
            .build();

    private static MessageConfig config;

    public String message;

    public MessageType messageType;

    public boolean prefixed;

    public @NotNull Component build() {
        Component component = Reference.MINI_MESSAGE.deserialize(this.message);
        return this.prefixed ? Components.joinSpaces(this.getPrefix(), component) : component;
    }

    public @NotNull Component build(@NotNull Object... placeholders) {

        // No need to use this function if no placeholders were supplied
        if (placeholders.length == 0)
            return this.build();

        String message = Formatting.formatPlaceholders(this.message, placeholders);
        Component component = Reference.MINI_MESSAGE.deserialize(message);
        return this.prefixed ? Components.joinSpaces(this.getPrefix(), component) : component;
    }

    public void send(@NotNull CommandSender sender, @NotNull Object... placeholders) {
        Reference.AUDIENCES.sender(sender).sendMessage(this.build(placeholders));
    }

    public void send(@NotNull Collection<CommandSender> senders, @NotNull Object... placeholders) {
        senders.forEach(sender -> this.send(sender, placeholders));
    }

    private @NotNull Component getPrefix() {
        return this.messageType == MessageType.ERROR ? config.errorPrefix.build() : config.prefix.build();
    }

    public static void setConfig(@NotNull MessageConfig config) {
        ConfigMessage.config = config;
    }

    public static @NotNull ConfigMessage normal(@NotNull Component... components) {
        return builder().message(components).type(MessageType.NORMAL).build();
    }

    public static @NotNull ConfigMessage error(@NotNull Component... components) {
        return builder().message(components).type(MessageType.ERROR).build();
    }

    public static @NotNull Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String message;

        private MessageType type = MessageType.NORMAL;

        private boolean prefixed = true;

        public @NotNull Builder message(@NotNull String message) {
            this.message = message;
            return this;
        }

        public @NotNull Builder message(@NotNull Component component) {
            this.message = Reference.MINI_MESSAGE.serialize(component.compact());
            return this;
        }

        public @NotNull Builder message(@NotNull Component... components) {
            this.message = Reference.MINI_MESSAGE.serialize(Component.join(JoinConfiguration.noSeparators(), components).compact());
            return this;
        }

        public @NotNull Builder lines(@NotNull Component... components) {
            this.message = Reference.MINI_MESSAGE.serialize(Component.join(JoinConfiguration.newlines(), components).compact());
            return this;
        }

        public @NotNull Builder type(@NotNull MessageType type) {
            this.type = type;
            return this;
        }

        public @NotNull Builder withoutPrefix() {
            this.prefixed = false;
            return this;
        }

        public @NotNull ConfigMessage build() {
            return new ConfigMessage(this.message, this.type, this.prefixed);
        }
    }
}
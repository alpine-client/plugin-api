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

    @NotNull
    public Component build() {
        Component component = Reference.MINI_MESSAGE.deserialize(this.message);
        return this.prefixed ? Components.joinSpaces(this.getPrefix(), component) : component;
    }

    @NotNull
    public Component build(@NotNull Object... placeholders) {

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

    @NotNull
    private Component getPrefix() {
        return this.messageType == MessageType.ERROR ? config.errorPrefix.build() : config.prefix.build();
    }

    public static void setConfig(@NotNull MessageConfig config) {
        ConfigMessage.config = config;
    }

    @NotNull
    public static ConfigMessage normal(@NotNull Component... components) {
        return builder().message(components).type(MessageType.NORMAL).build();
    }

    @NotNull
    public static ConfigMessage error(@NotNull Component... components) {
        return builder().message(components).type(MessageType.ERROR).build();
    }

    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String message;

        private MessageType type = MessageType.NORMAL;

        private boolean prefixed = true;

        @NotNull
        public Builder message(@NotNull String message) {
            this.message = message;
            return this;
        }

        @NotNull
        public Builder message(@NotNull Component component) {
            this.message = Reference.MINI_MESSAGE.serialize(component.compact());
            return this;
        }

        @NotNull
        public Builder message(@NotNull Component... components) {
            this.message = Reference.MINI_MESSAGE.serialize(Component.join(JoinConfiguration.noSeparators(), components).compact());
            return this;
        }

        @NotNull
        public Builder lines(@NotNull Component... components) {
            this.message = Reference.MINI_MESSAGE.serialize(Component.join(JoinConfiguration.newlines(), components).compact());
            return this;
        }

        @NotNull
        public Builder type(@NotNull MessageType type) {
            this.type = type;
            return this;
        }

        @NotNull
        public Builder withoutPrefix() {
            this.prefixed = false;
            return this;
        }

        @NotNull
        public ConfigMessage build() {
            return new ConfigMessage(this.message, this.type, this.prefixed);
        }
    }
}
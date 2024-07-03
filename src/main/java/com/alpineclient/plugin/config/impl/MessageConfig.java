/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.alpineclient.plugin.config.impl;

import com.alpineclient.plugin.Reference;
import com.alpineclient.plugin.config.AbstractConfig;
import com.alpineclient.plugin.config.object.ConfigMessage;
import com.alpineclient.plugin.config.object.MessageType;
import com.alpineclient.plugin.util.Components;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

/**
 * @author BestBearr
 * Created on 06/06/23
 */
@NoArgsConstructor
public final class MessageConfig extends AbstractConfig {
    {
        ConfigMessage.setConfig(this);
    }

    public ConfigMessage prefix = ConfigMessage.builder()
            .message(
                    Component.text("[").color(DIVIDER_COLOR),
                    Component.text(Reference.NAME).color(PRIMARY_COLOR),
                    Component.text("]").color(DIVIDER_COLOR))
            .withoutPrefix()
            .build();

    public ConfigMessage errorPrefix = ConfigMessage.builder()
            .message(
                    Component.text("[").color(DIVIDER_COLOR),
                    Component.text(Reference.NAME).color(PRIMARY_ERROR_COLOR),
                    Component.text("]").color(DIVIDER_COLOR))
            .type(MessageType.ERROR)
            .withoutPrefix()
            .build();

    public ConfigMessage clientCheck = ConfigMessage.normal(
            Component.text("%player_name%").color(TEXT_COLOR),
            Component.text("%player_status%"),
            Component.text("using %client_text%.").color(TEXT_COLOR)
    );

    public ConfigMessage notOnClient = ConfigMessage.error(
            Component.text("%player_name%").color(SECONDARY_ERROR_COLOR),
            Component.text(" is not on Alpine Client.").color(ERROR_TEXT_COLOR)
    );

    public ConfigMessage listPlayers = ConfigMessage.builder()
            .message(
                    Components.joinNewLines(
                            Component.text("----------------------------------------").color(DARK_DIVIDER_COLOR).decorate(TextDecoration.BOLD, TextDecoration.STRIKETHROUGH),
                            Component.empty().append(
                                    Component.text("Connected (%player_amount%): ").color(PRIMARY_COLOR).decorate(TextDecoration.BOLD)
                            ).append(
                                    Component.text("%player_names%").color(TEXT_COLOR)
                            ),
                            Component.text("----------------------------------------").color(DARK_DIVIDER_COLOR).decorate(TextDecoration.BOLD, TextDecoration.STRIKETHROUGH)
                    )
            )
            .withoutPrefix()
            .build();

    public ConfigMessage notifSuccess = ConfigMessage.normal(
            Component.text("Successfully notified ").color(TEXT_COLOR),
            Component.text("%player_name%").color(SECONDARY_COLOR),
            Component.text(".").color(TEXT_COLOR)
    );

    public ConfigMessage missingPermissions = ConfigMessage.error(
            Component.text("You do not have permission to execute this command.").color(ERROR_TEXT_COLOR)
    );

    public ConfigMessage playerNotFound = ConfigMessage.error(
            Component.text("Could not find a player with the name \"%player%\".").color(ERROR_TEXT_COLOR)
    );

    public ConfigMessage playerOnly = ConfigMessage.error(
            Component.text("Console may not execute this command.").color(ERROR_TEXT_COLOR)
    );

    public ConfigMessage invalidUsageSingle = ConfigMessage.error(
            Component.text("Invalid command usage. Syntax: ").color(ERROR_TEXT_COLOR),
            Component.text("%syntax%").color(SECONDARY_ERROR_COLOR)
    );

    public ConfigMessage invalidUsageMultiHeader = ConfigMessage.error(
            Component.text("Invalid command usage:").color(ERROR_TEXT_COLOR)
    );

    public ConfigMessage invalidUsageMultiContent = ConfigMessage.error(
            Component.text("  * ").color(DIVIDER_COLOR),
            Component.text("%syntax%").color(SECONDARY_ERROR_COLOR)
    );

    public MessageConfig(@NotNull Path configPath) {
        super(configPath);
    }
}

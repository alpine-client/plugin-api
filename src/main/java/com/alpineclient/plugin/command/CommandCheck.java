package com.alpineclient.plugin.command;

import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import com.alpineclient.plugin.Plugin;
import com.alpineclient.plugin.api.objects.AlpinePlayer;
import com.alpineclient.plugin.config.AbstractConfig;
import com.alpineclient.plugin.framework.Command;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;

/**
 * @author Thomas Wearmouth
 * Created on 21/06/2023
 */
@CommandAlias("accheck|acheck")
@Description("Check whether a player is using Alpine Client")
@CommandPermission("alpineapi.check")
public final class CommandCheck extends Command {
    @Default @Syntax("<player>")
    public void execute(CommandSender sender, OnlinePlayer target) {
        AlpinePlayer alpinePlayer = Plugin.getInstance().getPlayerHandler().getConnectedPlayer(target.getPlayer());
        boolean connected = alpinePlayer != null;
        String text = connected ? " is " : " is not ";
        TextColor color = connected ? AbstractConfig.PRIMARY_COLOR : AbstractConfig.PRIMARY_ERROR_COLOR;
        Component status = Component.text(text).color(color);
        String clientText = "Alpine Client";
        if (connected) {
            clientText += " " + alpinePlayer.getClientBrand();
        }
        this.messageConfig.clientCheck.send(sender, "player_name", target.getPlayer().getName(), "player_status", status, "client_text", clientText);
    }
}

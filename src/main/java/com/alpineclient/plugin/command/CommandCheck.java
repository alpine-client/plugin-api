package com.alpineclient.plugin.command;

import com.alpineclient.plugin.api.objects.AlpinePlayer;
import com.alpineclient.plugin.config.AbstractConfig;
import com.alpineclient.plugin.framework.BaseCommand;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.description.Description;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Thomas Wearmouth
 * Created on 21/06/2023
 */
@Command(name = "accheck", aliases = { "acheck" })
@Description("Check whether a player is using Alpine Client")
@Permission("alpineapi.check")
public final class CommandCheck extends BaseCommand {
    @Execute
    public void execute(@Context CommandSender sender, @Arg("target") Player player) {
        AlpinePlayer alpinePlayer = this.plugin.getPlayerHandler().getConnectedPlayer(player);
        boolean connected = alpinePlayer != null;
        String text = connected ? " is " : " is not ";
        TextColor color = connected ? AbstractConfig.PRIMARY_COLOR : AbstractConfig.PRIMARY_ERROR_COLOR;
        Component status = Component.text(text).color(color);
        String clientText = "Alpine Client";
        if (connected) {
            clientText += " " + alpinePlayer.getClientBrand();
        }
        this.messageConfig.clientCheck.send(sender, "player_name", player.getName(), "player_status", status, "client_text", clientText);
    }
}

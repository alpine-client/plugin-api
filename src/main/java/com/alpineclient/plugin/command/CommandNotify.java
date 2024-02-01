package com.alpineclient.plugin.command;

import com.alpineclient.plugin.Plugin;
import com.alpineclient.plugin.api.objects.AlpinePlayer;
import com.alpineclient.plugin.api.objects.Notification;
import com.alpineclient.plugin.framework.BaseCommand;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.description.Description;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Thomas Wearmouth
 * Created on 24/06/2023
 */
@Command(name = "acnotify", aliases = { "anotify" })
@Description("Send a notification to a player on Alpine Client")
@Permission("alpineapi.notify")
public final class CommandNotify extends BaseCommand {
    @Execute
    public void execute(@Context CommandSender sender, @Arg("target") Player player, @Arg("content") String content) {
        AlpinePlayer alpinePlayer = Plugin.getInstance().getPlayerHandler().getConnectedPlayer(player);
        if (alpinePlayer != null) {
            Notification notification = Notification.builder().description(content).build();
            alpinePlayer.sendNotification(notification);
            this.messageConfig.notifSuccess.send(sender, "player_name", player.getName());
        }
        else {
            this.messageConfig.notOnClient.send(sender, "player_name", player.getName());
        }
    }
}

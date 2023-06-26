package com.alpineclient.plugin.command;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import com.alpineclient.plugin.Plugin;
import com.alpineclient.plugin.api.objects.AlpinePlayer;
import com.alpineclient.plugin.api.objects.Notification;
import com.alpineclient.plugin.framework.Command;
import org.bukkit.command.CommandSender;

/**
 * @author Thomas Wearmouth
 * Created on 24/06/2023
 */
@CommandAlias("acnotify|anotify")
@Description("Send a notification to a player on Alpine Client")
@CommandPermission("alpineapi.notify")
public final class CommandNotify extends Command {
    @Default
    public void execute(CommandSender sender, OnlinePlayer target, String content) {
        AlpinePlayer alpinePlayer = Plugin.getInstance().getPlayerHandler().getConnectedPlayer(target.getPlayer());
        if (alpinePlayer != null) {
            Notification notif = Notification.builder().description(content).build();
            alpinePlayer.sendNotification(notif);
            this.messageConfig.notifSuccess.send(sender, "player_name", target.getPlayer().getName());
        }
        else {
            this.messageConfig.notOnClient.send(sender, "player_name", target.getPlayer().getName());
        }
    }
}

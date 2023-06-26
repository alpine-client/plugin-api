package com.alpineclient.plugin.command;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import com.alpineclient.plugin.Plugin;
import com.alpineclient.plugin.framework.Command;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CommandAlias("aclist|alist")
@Description("Shows a list of Alpine Client users on the server")
@CommandPermission("alpineapi.list")
public final class CommandList extends Command {
    @Default
    public void execute(CommandSender sender) {
        List<String> playerNames = Plugin.getInstance().getPlayerHandler().getConnectedPlayers().stream()
                .map(player -> player == null ? null : player.getBukkitPlayer().getName())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        int playerAmount = playerNames.size();
        this.messageConfig.listPlayers.send(sender, "player_amount", playerAmount, "player_names", String.join(", ", playerNames));
    }
}

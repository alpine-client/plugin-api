package com.alpineclient.plugin.command;

import com.alpineclient.plugin.framework.BaseCommand;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.description.Description;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Command(name = "aclist", aliases = { "alist" })
@Description("Shows a list of Alpine Client users on the server")
@Permission("alpineapi.list")
public final class CommandList extends BaseCommand {
    @Execute
    public void execute(@Context CommandSender sender) {
        List<String> playerNames = this.main.getPlayerHandler().getConnectedPlayers().stream()
                .map(player -> player == null ? null : player.getBukkitPlayer().getName())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        int playerAmount = playerNames.size();
        this.messageConfig.listPlayers.send(sender, "player_amount", playerAmount, "player_names", String.join(", ", playerNames));
    }
}

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.tomwmth.exampleplugin.command;

import com.alpineclient.plugin.api.AlpineClientApi;
import com.alpineclient.plugin.api.objects.Waypoint;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * A simple command that broadcasts a 10-second waypoint of the user's location
 * to all online Alpine Client users.
 *
 * @author Thomas Wearmouth <tomwmth@pm.me>
 * Created on 30/06/2024
 */
public final class PingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // If the command was sent from console, return
        if (!(sender instanceof Player))
            return false;

        Player player = (Player) sender;
        // Construct the waypoint of the senders location
        Waypoint waypoint = Waypoint.builder()
                .name(player.getName() + "'s Location")
                .pos(player.getLocation())
                .world(player.getWorld())
                .duration(10_000L)
                .build();
        // Send the waypoint to all online Alpine Client users
        AlpineClientApi.getAllPlayers().forEach((alpinePlayer) -> alpinePlayer.sendWaypoint(waypoint));
        return true;
    }
}

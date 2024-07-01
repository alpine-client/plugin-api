package dev.tomwmth.exampleplugin.listener;

import com.alpineclient.plugin.api.AlpineClientApi;
import com.alpineclient.plugin.api.objects.ClientResource;
import com.alpineclient.plugin.api.objects.Cooldown;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A simple event listener that implements a 10-second Ender Pearl cooldown.
 *
 * @author Thomas Wearmouth <tomwmth@pm.me>
 * Created on 30/06/2024
 */
public final class EnderPearlListener implements Listener {
    // The length of the cooldown in milliseconds
    private static final long COOLDOWN_LENGTH = 10_000L;

    // The Alpine Client API object for the cooldown
    // Since the details are always the same, we can create this ahead of time and reuse it
    private static final Cooldown ALPINE_COOLDOWN = Cooldown.builder()
            .name("Ender Pearl")
            .color(0xFF2CCDB1)
            .duration(COOLDOWN_LENGTH)
            .texture(ClientResource.internal("minecraft:textures/item/ender_pearl.png"))
            .build();

    // The map for tracking the actual cooldowns
    private final Map<UUID, Long> pearlCooldowns = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        // If the interaction does not involve an Ender Pearl, return
        if (event.getItem() == null || event.getItem().getType() != Material.ENDER_PEARL)
            return;

        // If the interaction is not a right click (use), return
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        UUID id = event.getPlayer().getUniqueId();
        long currentTime = System.currentTimeMillis();

        // Is there an existing cooldown for this player?
        if (this.pearlCooldowns.containsKey(id)) {
            // Is the player still on cooldown?
            long expiryTime = this.pearlCooldowns.get(id);
            if (expiryTime >= currentTime) {
                // Cancel the interaction and return
                event.setCancelled(true);
                return;
            }
        }

        // Add the cooldown
        this.pearlCooldowns.put(id, currentTime + COOLDOWN_LENGTH);
        // Send the cooldown via the Alpine Client API
        AlpineClientApi.getPlayer(id).ifPresent(player -> player.sendCooldown(ALPINE_COOLDOWN));
    }
}

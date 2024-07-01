package dev.tomwmth.exampleplugin;

import com.alpineclient.plugin.api.AlpineClientApi;
import com.alpineclient.plugin.api.objects.Capability;
import dev.tomwmth.exampleplugin.command.PingCommand;
import dev.tomwmth.exampleplugin.listener.EnderPearlListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Thomas Wearmouth
 * Created on 30/06/2024
 */
public final class ExamplePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        // We intend to send cooldown information, tell the client to enable the module
        AlpineClientApi.registerCapability(Capability.COOLDOWNS);

        // Register our event listeners
        this.getServer().getPluginManager().registerEvents(new EnderPearlListener(), this);

        // Register our commands
        this.getCommand("ping").setExecutor(new PingCommand());
    }

    @Override
    public void onDisable() {
        // We no longer intend to send cooldown information, no point in having the client enable the module
        // DO NOT do this if you have multiple different plugins planning to utilize a given capability
        AlpineClientApi.unregisterCapability(Capability.COOLDOWNS);
    }
}

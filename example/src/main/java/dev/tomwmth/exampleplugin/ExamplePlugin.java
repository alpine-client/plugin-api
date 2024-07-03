/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

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
        AlpineClientApi.registerCapabilities(this, Capability.COOLDOWNS);

        // Register our event listeners
        this.getServer().getPluginManager().registerEvents(new EnderPearlListener(), this);

        // Register our commands
        this.getCommand("ping").setExecutor(new PingCommand());
    }

    @Override
    public void onDisable() {
        // Unregister all of the capabilities that we registered
        AlpineClientApi.unregisterCapabilities(this);
    }
}

package com.alpineclient.plugin.framework;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import com.alpineclient.plugin.Plugin;
import com.alpineclient.plugin.config.ConfigManager;
import com.alpineclient.plugin.config.impl.MessageConfig;

/**
 * @author BestBearr
 * Created on 07/06/23
 */
public abstract class Command extends BaseCommand {

    public final Plugin plugin = Plugin.getInstance();

    public MessageConfig messageConfig = ConfigManager.getInstance().getConfig(MessageConfig.class, c -> this.messageConfig = c);

    /**
     * Called when command conditions should be registered.
     *
     * @param manager The command manager
     */
    public void registerConditions(PaperCommandManager manager) {
        // NO-OP
    }


    /**
     * Called when command completions should be registered.
     *
     * @param manager The command manager
     */
    public void registerCompletions(PaperCommandManager manager) {
        // NO-OP
    }
}

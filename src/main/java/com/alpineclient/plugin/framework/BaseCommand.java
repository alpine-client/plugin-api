/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.alpineclient.plugin.framework;

import com.alpineclient.plugin.PluginMain;
import com.alpineclient.plugin.config.ConfigManager;
import com.alpineclient.plugin.config.impl.MessageConfig;

/**
 * @author BestBearr
 * Created on 07/06/23
 */
public abstract class BaseCommand {
    protected final PluginMain main = PluginMain.getInstance();

    protected final MessageConfig messageConfig = ConfigManager.getInstance().getConfig(MessageConfig.class);
}

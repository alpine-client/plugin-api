/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.alpineclient.plugin.config.impl;

import com.alpineclient.plugin.config.AbstractConfig;
import com.google.common.collect.ImmutableMap;
import de.exlll.configlib.Comment;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Map;

/**
 * @author Thomas Wearmouth
 * Created on 26/06/2023
 */
@NoArgsConstructor
public final class GeneralConfig extends AbstractConfig {
    @Comment({
            " --- Debug Logging --- ",
            "    Whether the plugin should log standard interactions with players."
    })
    public boolean logging = true;

    @Comment({
            " --- Module Settings --- ",
            "    <module id>: <allowed>",
            "    Any modules not included (except Cannon View) are allowed by default.",
            "    A list of all module IDs can be found in the GitHub repository under \"MODULES.md\"."
    })
    public Map<String, Boolean> modules = ImmutableMap.of("cannon_view", false);

    public GeneralConfig(@NotNull Path configPath) {
        super(configPath);
    }
}

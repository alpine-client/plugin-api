/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.alpineclient.plugin.config;

import de.exlll.configlib.Configuration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

/**
 * @author BestBearr
 * Created on 06/06/23
 */
@Configuration @NoArgsConstructor
public class AbstractConfig {
    public static final TextColor PRIMARY_COLOR = TextColor.color(0x06dd63);
    public static final TextColor SECONDARY_COLOR = TextColor.color(0x68f9a7);
    public static final TextColor PRIMARY_ERROR_COLOR = TextColor.color(0xef2c13);
    public static final TextColor SECONDARY_ERROR_COLOR = TextColor.color(0xe86445);
    public static final TextColor DARK_DIVIDER_COLOR = TextColor.color(0x3a3a3a);
    public static final TextColor DIVIDER_COLOR = TextColor.color(0x777777);
    public static final TextColor TEXT_COLOR = TextColor.color(0xdbfce9);
    public static final TextColor ERROR_TEXT_COLOR = TextColor.color(0xfcd5cc);

    @Getter @Setter
    private transient Path configPath;

    public AbstractConfig(@NotNull Path configPath) {
        this.configPath = configPath;
    }
}

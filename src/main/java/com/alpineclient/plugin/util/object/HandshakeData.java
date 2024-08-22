/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.alpineclient.plugin.util.object;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import org.jetbrains.annotations.ApiStatus;

/**
 * @author Thomas Wearmouth
 * Created on 21/06/2023
 */
@Getter
@ApiStatus.Internal
public final class HandshakeData {
    @SerializedName("platform")
    private final String platform;
    @SerializedName("version")
    private final String version;

    public HandshakeData(String platform, String version) {
        this.platform = platform;
        this.version = version;
    }
}

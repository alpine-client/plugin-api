package com.alpineclient.plugin.util.object;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

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
    @SerializedName("mods")
    private final List<String> mods;

    public HandshakeData(String platform, String version, List<String> mods) {
        this.platform = platform;
        this.version = version;
        this.mods = mods;
    }
}

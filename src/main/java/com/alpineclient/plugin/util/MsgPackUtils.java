package com.alpineclient.plugin.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Thomas Wearmouth <tomwmth@pm.me>
 * Created on 7/02/2024
 */
@UtilityClass
@ApiStatus.Internal
public final class MsgPackUtils {
    public static @NotNull UUID unpackUuid(@NotNull MessageUnpacker unpacker) throws IOException {
        long msb = unpacker.unpackLong();
        long lsb = unpacker.unpackLong();
        return new UUID(msb, lsb);
    }

    public static void packUuid(@NotNull MessagePacker packer, @NotNull UUID id) throws IOException {
        packer.packLong(id.getMostSignificantBits());
        packer.packLong(id.getLeastSignificantBits());
    }
}

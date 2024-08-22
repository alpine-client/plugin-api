/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.alpineclient.plugin.util;

import com.alpineclient.plugin.api.objects.ClientResource;
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
    public static void packUuid(@NotNull MessagePacker packer, @NotNull UUID id) throws IOException {
        packer.packLong(id.getMostSignificantBits());
        packer.packLong(id.getLeastSignificantBits());
    }

    public static @NotNull UUID unpackUuid(@NotNull MessageUnpacker unpacker) throws IOException {
        long msb = unpacker.unpackLong();
        long lsb = unpacker.unpackLong();
        return new UUID(msb, lsb);
    }

    public static void packClientResource(@NotNull MessagePacker packer, @NotNull ClientResource resource) throws IOException {
        packer.packBoolean(resource.getType() == ClientResource.Type.INTERNAL);
        packer.packString(resource.getValue());
    }

    public static @NotNull ClientResource unpackClientResource(@NotNull MessageUnpacker unpacker) throws IOException {
        boolean internal = unpacker.unpackBoolean();
        String value = unpacker.unpackString();
        return internal ? ClientResource.internal(value) : ClientResource.external(value);
    }
}

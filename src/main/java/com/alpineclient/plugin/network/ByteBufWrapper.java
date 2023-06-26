package com.alpineclient.plugin.network;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

/**
 * @author BestBearr
 * @since 27/09/2021
 */
@AllArgsConstructor
public final class ByteBufWrapper {

    private final ByteBuf buf;

    public void writeByteArray(byte[] bytes) {
        buf.writeBytes(bytes);
    }

    public byte[] readByteArray() {
        int len = this.readInt();
        byte[] buffer = new byte[len];
        this.buf.readBytes(buffer);
        return buffer;
    }

    public void writeInt(int b) {
        while ((b & 0xFFFFFF80) != 0x0) {
            this.buf.writeByte((b & 0x7F) | 0x80);
            b >>>= 7;
        }
        this.buf.writeByte(b);
    }

    public int readInt() {
        int i = 0;
        int chunk = 0;
        byte b;
        do {
            b = this.buf.readByte();
            i |= (b & 0x7F) << chunk++ * 7;
            if (chunk > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while ((b & 0x80) == 0x80);
        return i;
    }

    public void writeString(String s) {
        byte[] arr = s.getBytes(StandardCharsets.UTF_8);
        this.writeInt(arr.length);
        this.buf.writeBytes(arr);
    }

    public String readString() {
        int len = this.readInt();
        byte[] buffer = new byte[len];
        this.buf.readBytes(buffer);
        return new String(buffer, StandardCharsets.UTF_8);
    }

    public void writeUUID(UUID uuid) {
        this.buf.writeLong(uuid.getMostSignificantBits());
        this.buf.writeLong(uuid.getLeastSignificantBits());
    }

    public UUID readUUID() {
        long mostSigBits = this.buf.readLong();
        long leastSigBits = this.buf.readLong();
        return new UUID(mostSigBits, leastSigBits);
    }

    public void writeBool(boolean b) {
        String s = String.valueOf(b);
        byte[] arr = s.getBytes(StandardCharsets.UTF_8);
        this.writeInt(arr.length);
        this.buf.writeBytes(arr);
    }

    public boolean readBool() {
        int len = this.readInt();
        byte[] buffer = new byte[len];
        this.buf.readBytes(buffer);
        return Boolean.parseBoolean(new String(buffer, StandardCharsets.UTF_8));
    }

    public ByteBuf buf() {
        return buf;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        ByteBufWrapper that = (ByteBufWrapper) obj;
        return Objects.equals(this.buf, that.buf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buf);
    }

    @Override
    public String toString() {
        return "ByteBufWrapper[" +
                "buf=" + buf + ']';
    }
}

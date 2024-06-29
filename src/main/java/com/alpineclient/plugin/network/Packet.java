package com.alpineclient.plugin.network;

import com.alpineclient.plugin.Reference;
import com.alpineclient.plugin.network.packet.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author BestBearr
 * Created on 27/09/2021
 */
public abstract class Packet {
    private static final Map<Class<? extends Packet>, Integer> CLASS_TO_ID;
    private static final Map<Integer, Class<? extends Packet>> ID_TO_CLASS;

    /**
     * Write all the necessary values to the provided byte buffer.
     *
     * @param packer The message packer.
     */
    public abstract void write(@NotNull MessagePacker packer) throws IOException;

    /**
     * Read this packet from the given byte buffer.
     *
     * @param unpacker The message unpacker.
     */
    public abstract void read(@NotNull MessageUnpacker unpacker) throws IOException;

    /**
     * Process a packet after it has been received and read.
     *
     * @param player The player responsible for sending this packet.
     */
    public abstract void process(@NotNull Player player);

    /**
     * Deconstructs an interpreted packet back into an array of bytes for transmission.
     *
     * @return This packet represented as a byte array.
     */
    public byte[] toBytes() {
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
        try {
            packer.packInt(Packet.CLASS_TO_ID.get(this.getClass()));
            this.write(packer);
            packer.close();
        }
        catch (IOException ex) {
            Reference.LOGGER.error("Unable to write packet", ex);
        }
        return packer.toByteArray();
    }

    /**
     * Turns an array of bytes sent from the server into something we can work with.
     *
     * @param data The byte array.
     * @return The {@link Packet} derived from the bytes.
     */
    public static @Nullable Packet fromBytes(byte[] data) {
        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(data);
        try {
            int packetId = unpacker.unpackInt();
            Class<? extends Packet> clazz = Packet.ID_TO_CLASS.get(packetId);
            if (clazz != null) {
                Packet packet = clazz.getConstructor().newInstance();
                packet.read(unpacker);
                return packet;
            }
        }
        catch (Exception ex) {
            Reference.LOGGER.error("Exception handling packet", ex);
        }
        return null;
    }

    protected static void addPacket(int id, Class<? extends Packet> clazz) {
        Packet.CLASS_TO_ID.put(clazz, id);
        Packet.ID_TO_CLASS.put(id, clazz);
    }

    static {
        CLASS_TO_ID = new HashMap<>();
        ID_TO_CLASS = new HashMap<>();

        /* Shared */
        addPacket(0xA7, PacketNotificationAdd.class);
        addPacket(0xA9, PacketWaypointAdd.class);
        addPacket(0xA10, PacketWaypointRemove.class);

        /* Plugin API */
        addPacket(0xB0, PacketModules.class);
        addPacket(0xB1, PacketWorldUpdate.class);
    }
}

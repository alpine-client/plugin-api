package com.alpineclient.plugin.network;

import com.alpineclient.plugin.Reference;
import com.alpineclient.plugin.network.packet.PacketModules;
import com.alpineclient.plugin.network.packet.PacketNotificationAdd;
import com.alpineclient.plugin.network.packet.PacketWaypointAdd;
import com.alpineclient.plugin.network.packet.PacketWorldUpdate;
import io.netty.buffer.Unpooled;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author BestBearr
 * @since 27/09/2021
 */
public abstract class Packet {

    private static final Map<Class<? extends Packet>, Integer> CLASS_TO_ID;
    private static final Map<Integer, Class<? extends Packet>> ID_TO_CLASS;

    /**
     * Write all the necessary values to the provided byte buffer.
     *
     * @param out The output byte buffer.
     */
    public abstract void write(@NotNull ByteBufWrapper out) throws IOException;

    /**
     * Read this packet from the given byte buffer.
     *
     * @param in The byte buffer.
     */
    public abstract void read(@NotNull ByteBufWrapper in) throws IOException;

    /**
     * Process a packet after it has been received and read.
     *
     * @param handler The net handler responsible for reading this packet.
     */
    public abstract void process(@NotNull Player player, @NotNull NetHandlerPlugin handler);

    /**
     * Deconstructs an interpreted packet back into an array of bytes for transmission.
     *
     * @return This packet represented as a byte array.
     */
    public byte[] toByteArray() {
        ByteBufWrapper wrappedBuffer = new ByteBufWrapper(Unpooled.buffer(0));
        wrappedBuffer.writeInt(Packet.CLASS_TO_ID.get(this.getClass()));
        try {
            this.write(wrappedBuffer);
        }
        catch (IOException ex) {
            Reference.LOGGER.error("Unable to write packet", ex);
        }
        return wrappedBuffer.buf().array();
    }

    /**
     * Turns an array of bytes sent from the server into something we can work with.
     *
     * @param data The byte array.
     * @return The {@link Packet} derived from the bytes.
     */
    public static Packet handle(byte[] data) {
        ByteBufWrapper wrapper = new ByteBufWrapper(Unpooled.wrappedBuffer(data));
        int packetId = wrapper.readInt();

        Class<? extends Packet> clazz = Packet.ID_TO_CLASS.get(packetId);
        if (clazz != null) {
            try {
                Packet packet = clazz.getConstructor().newInstance();
                packet.read(wrapper);
                return packet;
            }
            catch (Throwable ex) {
                Reference.LOGGER.error("Exception handling packet", ex);
            }
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

        /* Plugin API */
        addPacket(0xB0, PacketModules.class);
        addPacket(0xB1, PacketWorldUpdate.class);
    }
}

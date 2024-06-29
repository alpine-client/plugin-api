package com.alpineclient.plugin.api.objects;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Represents an image texture.
 *
 * @author BestBearr
 * @since 1.0.0
 */
public final class ClientResource {
    private final Type type;
    private final String value;

    private ClientResource(@NotNull Type type, @NotNull String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Get the type of the resource.
     *
     * @return the {@link com.alpineclient.plugin.api.objects.ClientResource.Type}
     */
    public @NotNull Type getType() {
        return this.type;
    }

    /**
     * Get the value of the resource.
     * <p>
     * For {@link com.alpineclient.plugin.api.objects.ClientResource.Type#INTERNAL} this is a path,
     * for {@link com.alpineclient.plugin.api.objects.ClientResource.Type#EXTERNAL} this is a URL.
     *
     * @return the {@link com.alpineclient.plugin.api.objects.ClientResource.Type}
     */
    public @NotNull String getValue() {
        return this.value;
    }

    @Override
    public @NotNull String toString() {
        return String.format("ClientResource{type=%s, value=%s}", this.type, this.value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;

        ClientResource other = (ClientResource) obj;
        return Objects.equals(this.type, other.getType()) && Objects.equals(this.value, other.getValue());
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    /**
     * Compiles an internal resource.
     * @see Type#INTERNAL
     *
     * @param value the local path to the resource
     * @return the compiled resource
     */
    public static @NotNull ClientResource internal(@NotNull String value) {
        return new ClientResource(Type.INTERNAL, value);
    }

    /**
     * Compiles an external resource.
     * @see Type#EXTERNAL
     *
     * @param value the URL to the resource
     * @return the compiled resource
     */
    public static @NotNull ClientResource external(@NotNull String value) {
        throw new UnsupportedOperationException();
        // return new ClientResource(Type.EXTERNAL, value);
    }

    /**
     * Represents where the client looks for a resource.
     */
    public enum Type {
        /**
         * The resource is located in the client's inbuilt resources.
         */
        INTERNAL,
        /**
         * The resource is located online.
         */
        EXTERNAL
    }
}
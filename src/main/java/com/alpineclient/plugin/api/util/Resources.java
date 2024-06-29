package com.alpineclient.plugin.api.util;

import com.alpineclient.plugin.api.objects.ClientResource;

/**
 * Contains some useful icons that can be used in notifications
 * sent to the client.
 *
 * @author Thomas Wearmouth
 * @since 1.0.0
 */
public final class Resources {
    /**
     * Fake constructor to stop attempted instantiation.
     */
    private Resources() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This is a static utility class and cannot be instantiated");
    }

    // Status
    /**
     * A white, rounded plus sign from Material Icons.
     */
    public static final ClientResource ADD = ClientResource.internal("add_box.png");
    /**
     * A white, rounded warning symbol from Material Icons.
     */
    public static final ClientResource WARNING = ClientResource.internal("warning.png");
    /**
     * A white, rounded save symbol from Material Icons.
     */
    public static final ClientResource SAVE = ClientResource.internal("save.png");
    /**
     * A white, rounded upload symbol from Material Icons.
     */
    public static final ClientResource UPLOAD = ClientResource.internal("upload.png");
    /**
     * A white, rounded cog/settings symbol from Material Icons.
     */
    public static final ClientResource COG = ClientResource.internal("cog.png");
    /**
     * A white question mark symbol from Material Icons.
     */
    public static final ClientResource QUESTION_MARK = ClientResource.internal("question_mark.png");
    /**
     * A white, rounded heart symbol from Material Icons.
     */
    public static final ClientResource HEART = ClientResource.internal("heart.png");
    /**
     * A white, filled push pin symbol from Material Icons.
     */
    public static final ClientResource PUSH_PIN = ClientResource.internal("push_pin_filled.png");
    /**
     * A white, unfilled push pin symbol from Material Icons.
     */
    public static final ClientResource LOCATION_PIN = ClientResource.internal("location_pin.png");

    // Logo
    /**
     * The Alpine Client "Mountain" logo in white.
     */
    public static final ClientResource MOUNTAIN_LOGO = ClientResource.internal("logo_nobr.png");
    /**
     * The Alpine Client "A" logo in white.
     */
    public static final ClientResource LETTER_A_LOGO = ClientResource.internal("logo_symbol.png");

    // Commercial
    /**
     * A white, rounded shopping cart symbol from Material Icons.
     */
    public static final ClientResource SHOPPING_CART = ClientResource.internal("shopping_cart.png");
    /**
     * A white, rounded clothes hanger symbol from Material Icons.
     */
    public static final ClientResource HANGER = ClientResource.internal("hanger.png");
    /**
     * A white, rounded shirt symbol from Material Icons.
     */
    public static final ClientResource SHIRT = ClientResource.internal("shirt.png");

    // Misc
    /**
     * A white, filled Discord logo.
     */
    public static final ClientResource DISCORD = ClientResource.internal("discord_logo.png");
    /**
     * A white, rounded game controller symbol from Material Icons.
     */
    public static final ClientResource GAME_CONTROLLER = ClientResource.internal("game_controller.png");
    /**
     * A white, rounded keyboard symbol from Material Icons.
     */
    public static final ClientResource KEYBOARD = ClientResource.internal("keyboard.png");
}

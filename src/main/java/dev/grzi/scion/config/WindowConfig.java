package dev.grzi.scion.config;

import dev.grzi.scion.utils.Color;
import dev.grzi.scion.utils.Dimension;

public class WindowConfig {
    /**
     * Enables fullscreen mode
     */
    private boolean fullscreen;
    /**
     * Default window width and height in pixels.
     */
    private Dimension dimension;
    /**
     * Minimum window width and height in pixels.
     */
    private Dimension minDimension;
    /**
     * Maximum window width and height in pixels.
     */
    private Dimension maxDimension;
    /**
     * Whether to display the window, Use full for loading
     */
    private boolean visibility;
    /**
     * The path relative to the game executable of the window icon.
     */
    private String icon;
    /**
     * Whether the window should always be on top of other windows.
     */
    private boolean alwaysOnTop;
    /**
     * Whether the window should have borders and bars.
     */
    private boolean decorations;
    /**
     * Whether the window should be maximized upon creation.
     */
    private boolean maximized;
    /**
     * If the user can resize the window
     */
    private boolean resizable;
    /**
     * If the window should be able to be transparent.
     */
    private boolean transparent;
    /**
     * Default background color of each frame in the window
     */
    private Color defaultBackgroundColor;
}

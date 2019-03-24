package com.jme3.jfx.injme.cursor.proton;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.ClasspathLocator;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.jfx.injme.cursor.CursorDisplayProvider;
import com.jme3.jfx.util.JfxPlatform;
import com.jme3.input.InputManager;
import com.ss.rlib.common.logging.Logger;
import com.ss.rlib.common.logging.LoggerManager;
import com.sun.javafx.cursor.CursorFrame;
import com.sun.javafx.cursor.CursorType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * http://www.rw-designer.com/cursor-set/proton by juanello <br> A cursorProvider that simulates the
 * native JFX one and tries to behave similar,<br> using native cursors and 2D surface logic.
 *
 * @author empire
 */
public class ProtonCursorProvider implements CursorDisplayProvider {

    @NotNull
    private static final Logger LOGGER = LoggerManager.getLogger(JfxPlatform.class);

    @NotNull
    private Map<CursorType, JmeCursor> cache = new ConcurrentHashMap<>();

    @NotNull
    private AssetManager assetManager;

    @NotNull
    private InputManager inputManager;

    @NotNull
    private Application application;

    public ProtonCursorProvider(@NotNull final Application application, @NotNull final AssetManager assetManager,
                                @NotNull final InputManager inputManager) {
        this.assetManager = assetManager;
        this.inputManager = inputManager;
        this.application = application;
        assetManager.registerLocator("", ClasspathLocator.class);
    }

    @Override
    public void prepare(@NotNull final CursorType cursorType) {

        JmeCursor jmeCursor = null;

        switch (cursorType) {
            case CLOSED_HAND:
                break;
            case CROSSHAIR:
                jmeCursor = (JmeCursor) assetManager.loadAsset("com/jme3/jfx/injme/cursor/proton/aero_cross.cur");
                break;
            case DEFAULT:
                jmeCursor = (JmeCursor) assetManager.loadAsset("com/jme3/jfx/injme/cursor/proton/aero_arrow.cur");
                break;
            case DISAPPEAR:
                break;
            case E_RESIZE:
                jmeCursor = (JmeCursor) assetManager.loadAsset("com/jme3/jfx/injme/cursor/proton/aero_ew.cur");
                break;
            case HAND:
                jmeCursor = (JmeCursor) assetManager.loadAsset("com/jme3/jfx/injme/cursor/proton/aero_link.cur");
                break;
            case H_RESIZE:
                jmeCursor = (JmeCursor) assetManager.loadAsset("com/jme3/jfx/injme/cursor/proton/aero_ew.cur");
                break;
            case IMAGE:
                break;
            case MOVE:
                jmeCursor = (JmeCursor) assetManager.loadAsset("com/jme3/jfx/injme/cursor/proton/aero_move.cur");
                break;
            case NE_RESIZE:
                jmeCursor = (JmeCursor) assetManager.loadAsset("com/jme3/jfx/injme/cursor/proton/aero_nesw.cur");
                break;
            case NONE:
                break;
            case NW_RESIZE:
                jmeCursor = (JmeCursor) assetManager.loadAsset("com/jme3/jfx/injme/cursor/proton/aero_nwse.cur");
                break;
            case N_RESIZE:
                jmeCursor = (JmeCursor) assetManager.loadAsset("com/jme3/jfx/injme/cursor/proton/aero_ns.cur");
                break;
            case OPEN_HAND:
                break;
            case SE_RESIZE:
                jmeCursor = (JmeCursor) assetManager.loadAsset("com/jme3/jfx/injme/cursor/proton/aero_nwse.cur");
                break;
            case SW_RESIZE:
                jmeCursor = (JmeCursor) assetManager.loadAsset("com/jme3/jfx/injme/cursor/proton/aero_nesw.cur");
                break;
            case S_RESIZE:
                jmeCursor = (JmeCursor) assetManager.loadAsset("com/jme3/jfx/injme/cursor/proton/aero_ns.cur");
                break;
            case TEXT:
                jmeCursor = (JmeCursor) assetManager.loadAsset("com/jme3/jfx/injme/cursor/proton/aero_text.cur");
                break;
            case V_RESIZE:
                jmeCursor = (JmeCursor) assetManager.loadAsset("com/jme3/jfx/injme/cursor/proton/aero_ns.cur");
                break;
            case WAIT:
                jmeCursor = (JmeCursor) assetManager.loadAsset("com/jme3/jfx/injme/cursor/proton/aero_busy.ani");
                break;
            case W_RESIZE:
                jmeCursor = (JmeCursor) assetManager.loadAsset("com/jme3/jfx/injme/cursor/proton/aero_ew.cur");
                break;
        }

        if (jmeCursor != null) {
            cache.putIfAbsent(cursorType, jmeCursor);
        }
    }

    @Override
    public void show(@NotNull final CursorFrame cursorFrame) {

        CursorType cursorType = cursorFrame.getCursorType();

        if (cache.get(cursorType) == null) {
            LOGGER.debug(this, cursorType, type -> "Unknown Cursor! " + type);
            cursorType = CursorType.DEFAULT;
        }

        final JmeCursor toDisplay = cache.get(cursorType);

        if (toDisplay != null) {
            application.enqueue((Callable<Void>) () -> {
                inputManager.setMouseCursor(toDisplay);
                return null;
            });
        }
    }
}

package me.cyning.statusbarfontcolor;

import android.app.Activity;
import android.os.Build;

import java.util.HashMap;
import java.util.Map;

/**
 * //todo 描述
 *
 * @author lixingyun
 * @since 2017-04-05
 */

public class StatusBarAdapter {

    public static final String MIUI = "miui";
    public static final String FLYME = "flyme";
    public static final String OS_M = "os_m";

    private volatile static StatusBarAdapter instance = null;
    private Map<String, IStatusBar> mStatusBars = new HashMap<>();

    private StatusBarAdapter() {

    }

    public static StatusBarAdapter getInstance() {
        if (instance == null) {
            synchronized (StatusBarAdapter.class) {
                if (instance == null) {
                    instance = new StatusBarAdapter();
                }
            }
        }
        return instance;
    }


    public void register(IStatusBar iStatusBar) {
        if (iStatusBar != null) {
            if (mStatusBars.get(iStatusBar.getTypeName()) == null) {
                mStatusBars.put(iStatusBar.getTypeName(), iStatusBar);
            }
        }
    }


    public void initDefault() {
        this.register(new MeizuStatusBar());
        this.register(new MiuiStatusbar());
        this.register(new OSM());
    }

    public void applay(Activity activity, boolean isDarkFont) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            IStatusBar statusBar = null;
            if (OSUtils.isFlyme()) {
                statusBar = mStatusBars.get(StatusBarAdapter.FLYME);
            } else if (OSUtils.isMIUI()) {
                statusBar = mStatusBars.get(StatusBarAdapter.MIUI);
            } else if (OSUtils.isOSM()) {
                statusBar = mStatusBars.get(StatusBarAdapter.OS_M);
            }

            if (statusBar != null) {
                statusBar.setStatusBarLightMode(activity, isDarkFont);
            }

        }

    }


}

package me.cyning.statusbarfontcolor;

import android.os.Build;

import java.io.IOException;

/**
 * //todo 描述
 *
 * @author lixingyun
 * @since 2017-04-06
 */

public class OSUtils {
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static final String KEY_FLYME_ICON_FALG = "persist.sys.use.flyme.icon";
    private static final String KEY_FLYME_SETUP_FALG = "ro.meizu.setupwizard.flyme";
    private static final String KEY_FLYME_PUBLISH_FALG = "ro.flyme.published";

    /**
     * 是不是FlyME
     *
     * @return
     */
    public static boolean isFlyme() {
        try {
            final BuildProperties buildProperties = BuildProperties.newInstance();
            if (buildProperties.containsKey(KEY_FLYME_ICON_FALG)
                    || buildProperties.containsKey(KEY_FLYME_SETUP_FALG)
                    || buildProperties.containsKey(KEY_FLYME_PUBLISH_FALG)) {

                return true;

            }

        } catch (final Exception e) {
        }
        return false;
    }

    public static boolean isMIUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }

    public static boolean isOSM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

}

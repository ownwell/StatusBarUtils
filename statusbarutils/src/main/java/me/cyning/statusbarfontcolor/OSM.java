package me.cyning.statusbarfontcolor;

import android.app.Activity;
import android.os.Build;
import android.view.View;

/**
 * //todo 描述
 *
 * @author lixingyun
 * @since 2017-04-05
 */

public class OSM implements IStatusBar {
    /**
     * @return if version is lager than M
     */
    @Override
    public boolean setStatusBarLightMode(Activity activity, boolean isFontColorDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isFontColorDark) {
                activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

//                int uiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
//                activity.getWindow().getDecorView().setSystemUiVisibility(uiVisibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                int uiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();

                //非沉浸式
                activity.getWindow().getDecorView().setSystemUiVisibility(uiVisibility| View.SYSTEM_UI_FLAG_VISIBLE);
            }
            return true;
        }
        return false;
    }

    @Override
    public String getTypeName() {
        return StatusBarAdapter.OS_M;
    }
}

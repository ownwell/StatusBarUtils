package me.cyning.statusbarfontcolor;

import android.app.Activity;

/**
 * //todo 描述
 *
 * @author lixingyun
 * @since 2017-04-05
 */

public interface IStatusBar {
    public boolean setStatusBarLightMode(Activity activity, boolean isFontColorDark) ;
    public String getTypeName();
}

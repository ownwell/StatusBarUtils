package me.cyning.statusbarcompat;

import android.content.Context;
import android.support.annotation.ColorRes;

/**
 * //todo 描述
 *
 * @author lixingyun
 * @since 2017-02-06
 */

public class ResourceUtils {

    public static int getColorId(Context context, @ColorRes int colorResID) {
        if (context != null) {
           return context.getResources().getColor(colorResID);
        }
        return  -1;
    }
}

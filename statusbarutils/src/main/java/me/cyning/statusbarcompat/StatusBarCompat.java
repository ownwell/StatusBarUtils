package me.cyning.statusbarcompat;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import me.cyning.statusbarfontcolor.R;


/**
 * //todo 描述
 *
 * @author lixingyun
 * @since 2017-02-06
 */
public class StatusBarCompat {

    /**
     * 模仿状态栏View的ID
     * 为了更好滴处理fakeStatusBarView，需要为这个view设置一个id
     */
    public static final int FAKE_STATUS_BAR_VIEW_ID = R.id.statusbarutil_fake_status_bar_view;


    /**
     * 半透明
     * 透明度范围为（1-255）
     */
    public static final int DEFAULT_STATUSBAR_ALPHA = 122;

    /**
     * 颜色处理方式，alpha改变透明度（0：颜色值为透明 255：完全不透明）
     */
    public final static int TYPE_ALPHA = 1;
    /**
     * 颜色处理方式，alpha改变颜色值（255，黑色）
     */
    public final static int TYPE_BLACK = 2;


    /**
     * 设置彩色状态栏
     *
     * @param activity
     * @param statusBarColor 状态栏的颜色
     */
    public static void setColor(Activity activity, @ColorInt int statusBarColor) {
        setStatusbarColor(activity, false, TYPE_BLACK, statusBarColor, 0);
    }


    /**
     * 设置半透明的
     *
     * @param activity
     * @param isFullScreen
     * @param statusBarColor
     */
    public static void setTranslucent(Activity activity, boolean isFullScreen, @ColorInt int statusBarColor) {
        setStatusbarColor(activity, isFullScreen, TYPE_BLACK, statusBarColor, DEFAULT_STATUSBAR_ALPHA);
    }

    /**
     * 设置状态栏全透明
     *
     * @param activity 需要设置的activity
     */
    public static void setTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        transparentStatusBar(activity);
    }

    /**
     * 设置彩色的状态栏
     *
     * @param activity
     * @param isFullScreen   全屏模式/着色模式
     * @param type           是黑
     * @param statusBarColor 状态栏颜色
     * @param alpha          透明度
     */
    public static void setStatusbarColor(Activity activity,
                                         boolean isFullScreen,
                                         int type,
                                         @ColorInt int statusBarColor,
                                         @IntRange(from = 0, to = 255) int alpha) {
        // 使状态栏透明
        transparentStatusBar(activity);
        // 是否为全屏
        if (!isFullScreen) {
            setFitsSystemWindows(activity);
        }
        //添加一个空白的view到手机屏幕的顶部
        addTopStatusBar(activity, type, statusBarColor, alpha);
    }

    /**
     * 使状态栏透明
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void transparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    /**
     * 在顶部添加一个和状态栏同高的view
     *
     * @param activity
     * @param type
     * @param statusBarColor
     * @param statusBarAlpha
     */
    private static void addTopStatusBar(Activity activity, int type, int statusBarColor, int statusBarAlpha) {
        ViewGroup decorView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);

        View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        int color = calculatesColorWithAlpha(statusBarColor, statusBarAlpha);
        if (type == TYPE_BLACK) {
            color = calculateStatusColor(statusBarColor, statusBarAlpha);
        }
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(color);
        } else {
            decorView.addView(createStatusBarView(activity, color));
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }


    /**
     * 生成一个和状态栏大小相同的半透明矩形条
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @return 状态栏矩形条
     */
    private static StatusBarView createStatusBarView(Activity activity, @ColorInt int color) {
        // 绘制一个和状态栏一样高的矩形
        StatusBarView statusBarView = new StatusBarView(activity);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(color);
        statusBarView.setId(FAKE_STATUS_BAR_VIEW_ID);
        return statusBarView;
    }


    public static void expandTop(View view) {
        int topPadding = view.getPaddingTop();
        int bottomPadding = view.getPaddingBottom();
        int rightPadding = view.getPaddingRight();
        int leftPadding = view.getPaddingLeft();

        topPadding = topPadding + getStatusBarHeight(view.getContext());
        view.setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
    }

    private static void setFitsSystemWindows(Activity activity) {
        //为了设置全屏
        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            ViewCompat.setFitsSystemWindows(mChildView, true);

        }
    }

    /**
     * 计算状态栏颜色
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private static int calculateStatusColor(@ColorInt int color, int alpha) {
        if (alpha == 0) {
            return color;
        }
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    public static int calculatesColorWithAlpha(@ColorInt int color, int alpha) {
        if (alpha == 0) {
            return color;
        }
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;

        return Color.argb(alpha, red, green, blue);
    }

}

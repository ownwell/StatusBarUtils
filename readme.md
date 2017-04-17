最近一直忙着写业务，一直没精力更新博客，趁着最近有点空闲事件，觉得很有必要写一篇博客捡回以前的那个状态。就写一个自己当时总结的一个适配功能吧：关于状态栏的字体/图标颜色适配。

随着Android6.0 Android7.0系统的系统升级，开发者来说适配的容易程度越来越低，我们需要将精力放到适配4.4+以上，而Android4.4相对来说是一个比较稳定的版本，尤其是在UI上它又和Android 6.0、Android7.0接近。
<!-- more -->
对于状态栏的，我记得之前我写过文章来实现着色的、透明的等各种样式的适配的文章，今天不再赘述。

我们要说的是今天的关于状态栏的字体/图标颜色。

# 状态字体颜色有区分
可能对于我这个被人成为"MI Body"的伪米粉来说，手里握着MI 5，听音乐用的小米耳机，还戴着小米手环，可能觉得小米最经典之作是小米定制的系统---MI UI。

当然除了Mi UI其他厂商也有定制，Flyme，EMUI，Color OS以及一加的氢OS，Smartisan OS等等，每款定制ROM都有其独特之处，不过个人已经习惯了Mi UI。
MI UI一个很好的体验就是状态的字体能够变色，黑色变白色，白色变黑色。
![](http://7xj9f0.com1.z0.glb.clouddn.com/14920106163118.jpg)


![](http://7xj9f0.com1.z0.glb.clouddn.com/14920106348516.jpg)

是不是很有意思，其他Android 6.0也就是我们说的Android N也是支持的。

![](http://7xj9f0.com1.z0.glb.clouddn.com/14920119504831.jpg)


![](http://7xj9f0.com1.z0.glb.clouddn.com/14920118899908.jpg)

>猜想：Google下一步会不会把状态栏的字体和图标颜色也开放能设置成彩色呢？

那么如何来实现呢，这种适配呢？

# 适配状态栏的字体/图标颜色
由于国内的ROM如MI UI和Flyme已经修改过Android的源码，他们在Android 4.4 做了适配，而原生的系统则是在Android M上才能设置状态栏字体颜色。

我们需要自己去一个个判断适配：

```java
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
```

## 小米的MIUI：

```java
import android.app.Activity;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * // 小米
 *
 * @author Cyning
 * @since 2017-04-05
 */

public class MiuiStatusbar implements IStatusBar {
    /**
     * 设置状态栏字体图标为深色，需要MIUI6以上
     *
     * @param isFontColorDark 是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    @Override
    public boolean setStatusBarLightMode(Activity activity, boolean isFontColorDark) {
        Window window = activity.getWindow();
        boolean result = false;
        try {
            if (window != null) {
                Class clazz = window.getClass();
                try {
                    int darkModeFlag = 0;
                    Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                    Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                    darkModeFlag = field.getInt(layoutParams);
                    Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                    if (isFontColorDark) {
                        extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                    } else {
                        extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                    }
                    result = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public String getTypeName() {
        return StatusBarAdapter.MIUI;
    }
}
```


## 魅族的Flyme

```java
/**
 * // Flyme
 *
 * @author Cyning
 * @since 2017-04-05
 */

public class MeizuStatusBar implements IStatusBar {
    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param isFontColorDark 是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    @Override
    public boolean setStatusBarLightMode(Activity activity, boolean isFontColorDark) {
        Window window = activity.getWindow();
        boolean result = false;
        try {
            if (window != null) {
                try {
                    WindowManager.LayoutParams lp = window.getAttributes();
                    Field darkFlag = WindowManager.LayoutParams.class
                            .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                    Field meizuFlags = WindowManager.LayoutParams.class
                            .getDeclaredField("meizuFlags");
                    darkFlag.setAccessible(true);
                    meizuFlags.setAccessible(true);
                    int bit = darkFlag.getInt(null);
                    int value = meizuFlags.getInt(lp);
                    if (isFontColorDark) {
                        value |= bit;
                    } else {
                        value &= ~bit;
                    }
                    meizuFlags.setInt(lp, value);
                    window.setAttributes(lp);
                    result = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String getTypeName() {
        return StatusBarAdapter.FLYME;
    }
}
```

## Android M

```java
import android.app.Activity;
import android.os.Build;
import android.view.View;

/**
 * // Android M
 *
 * @author Cyning
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
                activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

//                int uiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
//                activity.getWindow().getDecorView().setSystemUiVisibility(uiVisibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                int uiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
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
```


源代码放到github上了[github项目地址](https://github.com/ownwell/StatusBarUtils).
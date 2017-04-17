package me.cyning.statusbarcompat;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Jaeger on 16/6/8.
 *
 * Email: chjie.jaeger@gmail.com
 * GitHub: https://github.com/laobie
 */
public class StatusBarView extends View {
    public StatusBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
    }

    public StatusBarView(Context context) {
        this(context, null);
        setBackgroundColor(Color.BLACK);
    }
}

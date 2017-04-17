package me.cyning.statusbarutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import me.cyning.statusbarcompat.StatusBarCompat;
import me.cyning.statusbarfontcolor.StatusBarAdapter;

/**
 * //todo 描述
 *
 * @author lixingyun
 * @since 2017-02-06
 */

public class TransparentActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transparent);

        StatusBarCompat.setTransparent(this);
        StatusBarAdapter.getInstance().initDefault();
        StatusBarAdapter.getInstance().applay(this, false);

//        int color = ResourceUtils.getColorId(this, R.color.translu);
//        StatusBarCompat.setStatusbarColor(this, color, 0);

    }
}

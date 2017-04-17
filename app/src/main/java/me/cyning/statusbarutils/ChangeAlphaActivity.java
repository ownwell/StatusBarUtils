package me.cyning.statusbarutils;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.SeekBar;


import me.cyning.statusbarcompat.ResourceUtils;
import me.cyning.statusbarcompat.StatusBarCompat;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class ChangeAlphaActivity extends SwipeBackActivity {

    protected SeekBar sbAlpha;
    protected RelativeLayout activityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_change_alpha);

        initView();
    }

    private void setStatusBarColor(int alpha) {
        int color = ResourceUtils.getColorId(this, R.color.colorPrimary);
        StatusBarCompat.setStatusbarColor(this, true, StatusBarCompat.TYPE_ALPHA, color, alpha);

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setStatusBarColor(122);
    }

    private void initView() {
        sbAlpha = (SeekBar) findViewById(R.id.sbAlpha);
        activityMain = (RelativeLayout) findViewById(R.id.activity_main);



        sbAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int colorProgress = (int) Math.floor((progress* 255f )  / 100f);
                colorProgress = Math.min(255, Math.max(colorProgress, 1));
                setStatusBarColor(colorProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        int progress = StatusBarCompat.DEFAULT_STATUSBAR_ALPHA  *100/ 255;
        sbAlpha.setProgress(progress);
    }
}

package me.cyning.statusbarutils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import me.cyning.statusbarcompat.StatusBarCompat;
import me.cyning.statusbarutils.fragments.StatusBarFragment;


/**
 * //todo 描述
 *
 * @author lixingyun
 * @since 2017-02-06
 */

public class NestedFragmentActivity extends AppCompatActivity {

    protected ViewPager vpContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment);

        initView();
        StatusBarCompat.setTransparent(this);
//        StatusBarAdapter.getInstance().initDefault();
//        StatusBarAdapter.getInstance().applay(this, true);

//        setStatusBarColor(StatusBarCompat.DEFAULT_STATUSBAR_ALPHA);
//        int colorResId = ResourceUtils.getColorId(this, R.color.md_blue);
//        StatusBarCompat.setStatusbarColorWithAlpha(this,true, colorResId, StatusBarCompat.DEFAULT_STATUSBAR_ALPHA);

    }

    private void setStatusBarColor(int alpha) {
//        int color = ResourceUtils.getColorId(this, R.color.md_purple);
//        StatusBarCompat.setStatusbarColor(this, true, StatusBarCompat.TYPE_ALPHA, color, alpha);
//        StatusBarCompat.setColor(this,color);
    }

    private void initView() {
        vpContent = (ViewPager) findViewById(R.id.vpContent);
        vpContent.setAdapter(new NestFragmentAdapter(getSupportFragmentManager()));

        vpContent.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
    }

    class NestFragmentAdapter extends FragmentPagerAdapter {
        public NestFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new StatusBarFragment();
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}

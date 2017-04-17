package me.cyning.statusbarutils.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.Random;

import me.cyning.statusbarcompat.StatusBarCompat;
import me.cyning.statusbarutils.R;

/**
 * //todo 描述
 *
 * @author lixingyun
 * @since 2017-02-06
 */

public class StatusBarFragment extends Fragment {


    protected View rootView;
    protected View viewEmpty;
    protected TextView tvbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = View.inflate(getActivity(), R.layout.fragment_statusbar, null);

        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        viewEmpty = (View) rootView.findViewById(R.id.viewEmpty);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, StatusBarCompat.getStatusBarHeight(getContext()));
        viewEmpty.setLayoutParams(params);
        tvbar = (TextView) rootView.findViewById(R.id.tvbar);
        StatusBarCompat.expandTop(viewEmpty);
        Random random = new Random();
        int color = 0xff000000 | random.nextInt(0xffffff);
        viewEmpty.setBackgroundColor(color);
    }
}

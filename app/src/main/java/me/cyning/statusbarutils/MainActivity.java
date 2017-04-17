package me.cyning.statusbarutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import me.cyning.statusbarcompat.ResourceUtils;
import me.cyning.statusbarcompat.StatusBarCompat;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected Button btnChangeAlpha;
    protected RelativeLayout activityMain;
    protected Button btnTransp;
    protected Button btnFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();
        int color  = ResourceUtils.getColorId(this, R.color.colorAccent);
        StatusBarCompat.setColor(this, color);
    }

    private void initView() {
        btnChangeAlpha = (Button) findViewById(R.id.btnChangeAlpha);
        btnTransp = (Button) findViewById(R.id.btnTransp);
        activityMain = (RelativeLayout) findViewById(R.id.activity_main);
        btnFragment = (Button) findViewById(R.id.btnFragment);

        btnChangeAlpha.setOnClickListener(this);
        btnTransp.setOnClickListener(this);
        btnFragment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnChangeAlpha) {
            startActivity(new Intent(this, ChangeAlphaActivity.class));
        } else if (id == R.id.btnTransp) {
            startActivity(new Intent(this, TransparentActivity.class));
        } else if (id == R.id.btnFragment) {
            startActivity(new Intent(this, NestedFragmentActivity.class));
        }
    }
}

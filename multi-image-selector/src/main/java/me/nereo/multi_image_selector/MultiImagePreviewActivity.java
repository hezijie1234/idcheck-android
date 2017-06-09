package me.nereo.multi_image_selector;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.LinearInterpolator;

import java.util.List;

import me.nereo.multi_image_selector.utils.AnimationUtil;
import me.nereo.multi_image_selector.view.PreviewPager;

public class MultiImagePreviewActivity extends AppCompatActivity {

    private Toolbar toolbar;

    protected boolean isUp;
    protected int current;
    protected List<String> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mis_activity_image_preview);
        initBase(getIntent().getExtras());
        initView();
        initData();
        overridePendingTransition(R.anim.mis_activity_alpha_action_in, 0);
    }

    private void initBase(Bundle extras) {
        if (extras == null)
            return;

        if (extras.containsKey("photos")) {
            photos = extras.getStringArrayList("photos");
            current = extras.getInt("position", 0);
        }
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        updatePercent();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        PreviewPager viewPager = (PreviewPager) findViewById(R.id.vp_base_app);
        if (viewPager != null) {
            viewPager.setOnImageClickListener(new PreviewPager.OnImageClickListener() {
                @Override
                public void onImageClick(int position) {
                    if (toolbar != null) {
                        if (!isUp) {
                            new AnimationUtil(getApplicationContext(), R.anim.mis_translate_up)
                                    .setInterpolator(new LinearInterpolator())
                                    .setFillAfter(true)
                                    .startAnimation(toolbar);
                            isUp = true;
                        } else {
                            new AnimationUtil(getApplicationContext(), R.anim.mis_translate_down_current)
                                    .setInterpolator(new LinearInterpolator())
                                    .setFillAfter(true)
                                    .startAnimation(toolbar);
                            isUp = false;
                        }
                    }
                }

                @Override
                public void onImageSelected(int position) {
                    current = position;
                    updatePercent();
                }
            });
            viewPager.bindData(photos, current);
        }
    }

    private void initData() {
        updatePercent();
    }

    protected void updatePercent() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (photos != null) {
                actionBar.setTitle((current + 1) + "/" + photos.size());
            } else {
                actionBar.setTitle(0 + "/" + 0);
            }
        }
    }
}

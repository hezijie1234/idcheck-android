package me.nereo.multi_image_selector.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import me.nereo.multi_image_selector.R;
import me.nereo.multi_image_selector.polites.GestureImageView;

public class ImagePreview extends LinearLayout implements OnClickListener {

    private ProgressBar pbLoading;
    private GestureImageView ivContent;
    private OnClickListener l;

    public ImagePreview(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.mis_image_preview, this, true);

        pbLoading = (ProgressBar) findViewById(R.id.pb_loading_vpp);
        ivContent = (GestureImageView) findViewById(R.id.iv_content_vpp);
        ivContent.setOnClickListener(this);
    }

    public ImagePreview(Context context, AttributeSet attrs, int defStyle) {
        this(context);
    }

    public ImagePreview(Context context, AttributeSet attrs) {
        this(context);
    }

    public void loadImage(String path) {
        Picasso.with(getContext())
                .load(path)
                .error(R.drawable.mis_ic_picture_loadfailed)
                .into(target);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        this.l = l;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_content_vpp && l != null)
            l.onClick(ivContent);
    }

    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            ivContent.setImageBitmap(bitmap);
            pbLoading.setVisibility(View.GONE);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            ivContent.setImageDrawable(errorDrawable);
            pbLoading.setVisibility(View.GONE);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
}

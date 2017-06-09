package me.nereo.multi_image_selector.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by win7 on 2016/10/31.
 */
public class PreviewPager extends ViewPager {

    private OnImageClickListener onImageClickListener;
    private List<String> photos;
    private int current;

    public PreviewPager(Context context) {
        super(context);
        init();
    }

    public PreviewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected void init() {
        addOnPageChangeListener(mPageChangeListener);
        setAdapter(mPagerAdapter);
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    public void bindData(List<String> photos, int position) {
        this.photos = photos;
        this.current = position;
        mPagerAdapter.notifyDataSetChanged();
        setCurrentItem(current);
    }

    private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            current = position;
            if (onImageClickListener != null) {
                onImageClickListener.onImageSelected(current);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private PagerAdapter mPagerAdapter = new PagerAdapter() {

        @Override
        public int getCount() {
            if (photos == null) {
                return 0;
            } else {
                return photos.size();
            }
        }

        @Override
        public View instantiateItem(final ViewGroup container, final int position) {
            ImagePreview photoPreview = new ImagePreview(getContext());
            container.addView(photoPreview);
            photoPreview.loadImage(photos.get(position));
            photoPreview.setOnClickListener(photoItemClickListener);
            return photoPreview;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    };

    private View.OnClickListener photoItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onImageClickListener != null) {
                onImageClickListener.onImageClick(current);
            }
        }
    };

    public interface OnImageClickListener {

        void onImageClick(int position);

        void onImageSelected(int position);
    }
}

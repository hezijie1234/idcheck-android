package com.huiyu.tech.zhongxing.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.models.ResultPageModel;
import com.huiyu.tech.zhongxing.ui.ZZBaseAdapter;
import com.squareup.picasso.Picasso;

/**
 * Created by ml on 2016/8/5.
 */
public class GridPicAdapter2 extends ZZBaseAdapter<ResultPageModel.ImagesBean> {
    public GridPicAdapter2(Context context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        ResultPageModel.ImagesBean model = list.get(position);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_add_pic, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.layoutAdd.setVisibility(View.GONE);
        holder.ivDelete.setVisibility(View.GONE);
        holder.ivPic.setVisibility(View.VISIBLE);
        Picasso.with(context).load(ApiImpl.DOMIN +model.getImagefile())
                .placeholder(R.mipmap.jz_11)
                .error(R.mipmap.jz_11)
                .fit()
                .into(holder.ivPic);

        return convertView;
    }

    class ViewHolder {
        private ImageView ivPic;
        private ImageView ivDelete;
        private RelativeLayout layoutAdd;

        ViewHolder(View view) {
            ivDelete = (ImageView)view. findViewById(R.id.iv_delete);
            ivPic = (ImageView)view. findViewById(R.id.iv_pic);
            layoutAdd = (RelativeLayout)view. findViewById(R.id.layout_add);
        }

    }
}

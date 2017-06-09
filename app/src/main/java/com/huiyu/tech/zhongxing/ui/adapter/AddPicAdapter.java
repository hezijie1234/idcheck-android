package com.huiyu.tech.zhongxing.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.models.NoticeModel;
import com.huiyu.tech.zhongxing.ui.ZZBaseAdapter;
import com.huiyu.tech.zhongxing.utils.ImageUtils;
import com.huiyu.tech.zhongxing.utils.LogUtils;

/**
 * Created by ml on 2016/8/5.
 */
public class AddPicAdapter extends ZZBaseAdapter<Uri> {
    private Handler handler;

    public AddPicAdapter(Context context,Handler handler) {
        super(context);
        this.handler = handler;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Uri model = list.get(position);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_add_pic, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(model.getPath().equals("delete")){
            holder.layoutAdd.setVisibility(View.VISIBLE);
            holder.ivPic.setVisibility(View.GONE);
            holder.ivDelete.setVisibility(View.GONE);
        }else{
            holder.layoutAdd.setVisibility(View.GONE);
            holder.ivPic.setVisibility(View.VISIBLE);
            holder.ivDelete.setVisibility(View.VISIBLE);
            ImageUtils.setImage(context,model,holder.ivPic);
            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.obtainMessage(0,position,0).sendToTarget();
                }
            });
        }

        return convertView;
    }

    class ViewHolder {
        private ImageView ivPic;
//        private ImageButton ivAddPic;
        private ImageView ivDelete;
        private RelativeLayout layoutAdd;

        ViewHolder(View view) {
            ivDelete = (ImageView)view. findViewById(R.id.iv_delete);
            ivPic = (ImageView)view. findViewById(R.id.iv_pic);
            layoutAdd = (RelativeLayout)view. findViewById(R.id.layout_add);
        }

    }
}

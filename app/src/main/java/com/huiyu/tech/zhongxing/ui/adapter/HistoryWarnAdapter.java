package com.huiyu.tech.zhongxing.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.models.CheckInfo;
import com.huiyu.tech.zhongxing.models.WarningDealModel;
import com.huiyu.tech.zhongxing.ui.ZZBaseAdapter;
import com.squareup.picasso.Picasso;

/**
 * Created by ml on 2016/8/5.
 */
public class HistoryWarnAdapter extends ZZBaseAdapter<WarningDealModel.DBean.ListBean> {
    public HistoryWarnAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        WarningDealModel.DBean.ListBean model = list.get(position);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_checkinfo, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(model.getFaceImage() != null){
            Picasso.with(context).load(model.getFaceImage())
                    .placeholder(R.mipmap.id_03)
                    .error(R.mipmap.id_03)
                    .fit()
                    .into(holder.ivHead);
        }else{
            Picasso.with(context).load(ApiImpl.DOMIN )
                    .placeholder(R.mipmap.id_03)
                    .error(R.mipmap.id_03)
                    .fit()
                    .into(holder.ivHead);
        }
        holder.tvType.setText(model.getAlarmPlaceDesc());
        holder.tvTime.setText(model.getCreateDate());
        holder.name.setText(model.getAlarmName());
        holder.idCard.setText(model.getAlarmIdcard());
        holder.send.setText("查看详情");
//        ImageUtils.setImage(context, model.getAddress(),holder.ivPic,R.mipmap.jz_07);
        if(model.getModelImage() != null){
            Picasso.with(context).load(model.getModelImage())
                    .placeholder(R.mipmap.jz_07)
                    .error(R.mipmap.jz_07)
                    .fit()
                    .into(holder.ivPic);
        }else{
            Picasso.with(context).load(Constants.IMG_HOST  )
                    .placeholder(R.mipmap.jz_07)
                    .error(R.mipmap.jz_07)
                    .fit()
                    .into(holder.ivPic);
        }
        return convertView;
    }

    class ViewHolder {
        private TextView tvType;
        private TextView tvTime;
        private ImageView ivPic;
        private ImageView ivHead;
        private TextView name;
        private TextView idCard;
        private TextView send;

        ViewHolder(View view) {
            tvType = (TextView) view.findViewById(R.id.place);
            tvTime = (TextView) view.findViewById(R.id.time);
            ivPic = (ImageView)view. findViewById(R.id.camera_image);
            ivHead = (ImageView)view. findViewById(R.id.idcard_image);
            name = (TextView) view.findViewById(R.id.name);
            idCard = (TextView) view.findViewById(R.id.idcard);
            send = (TextView) view.findViewById(R.id.send);
        }

    }
}

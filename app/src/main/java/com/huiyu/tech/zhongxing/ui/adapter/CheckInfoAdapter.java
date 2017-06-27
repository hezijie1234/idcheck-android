package com.huiyu.tech.zhongxing.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.models.CheckInfo;
import com.huiyu.tech.zhongxing.ui.ZZBaseAdapter;
import com.squareup.picasso.Picasso;

/**
 * Created by ml on 2016/8/5.
 */
public class CheckInfoAdapter extends ZZBaseAdapter<CheckInfo.ListBean> {
    public CheckInfoAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        CheckInfo.ListBean model = list.get(position);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_checkinfo, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(model.getIdcardImg() != null){
            Picasso.with(context).load(ApiImpl.DOMIN +model.getIdcardImg().getPath())
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
        holder.tvType.setText(model.getUserName()+" "+model.getStationName() +" "+model.getPlaceNo()+"进站口");
        holder.tvTime.setText(model.getCreateDate());
//        holder.tvIdcard.setText("身份证：" + model.getIdcard());
//        holder.tvSuspectType.setText(model.getAlarmType());
//        holder.tvName.setText(model.getUserName());
//        holder.tvZu.setText(model.getNation());
//        if(!TextUtils.isEmpty(model.getIdcard())&&model.getIdcard().length() == 18){
//            holder.tvYear.setText(model.getIdcard().substring(6,10));
//            holder.tvMonth.setText(model.getIdcard().substring(10,12));
//            holder.tvDay.setText(model.getIdcard().substring(12,14));
//        }
//        holder.tvAddr.setText(model.getAddress());
//        holder.tvCard.setText(model.getIdcard());
//        holder.tvSex.setText("male".equals(model.getSex()) ? "男" : "女");
//        ImageUtils.setImage(context, model.getAddress(),holder.ivPic,R.mipmap.jz_07);
        if(model.getFrontImg() != null){
            Picasso.with(context).load(ApiImpl.DOMIN +model.getFrontImg().getPath())
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

        ViewHolder(View view) {
            tvType = (TextView) view.findViewById(R.id.place);
            tvTime = (TextView) view.findViewById(R.id.time);
            ivPic = (ImageView)view. findViewById(R.id.camera_image);
            ivHead = (ImageView)view. findViewById(R.id.idcard_image);
        }

    }
}

package com.huiyu.tech.zhongxing.ui.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.huiyu.tech.zhongxing.models.EmergencyNoticeModel;
import com.huiyu.tech.zhongxing.models.WarningDealModel;
import com.huiyu.tech.zhongxing.ui.ZZBaseAdapter;
import com.huiyu.tech.zhongxing.ui.activity.HandleResultActivity;
import com.huiyu.tech.zhongxing.ui.activity.LargeMapActivity;
import com.huiyu.tech.zhongxing.utils.DataUtils;
import com.huiyu.tech.zhongxing.utils.LogUtils;
import com.huiyu.tech.zhongxing.utils.TimeRenderUtils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by ml on 2016/8/5.
 */
public class CheckInfoAdapter extends ZZBaseAdapter<WarningDealModel.DBean.ListBean> {
    public CheckInfoAdapter(Context context) {
        super(context);
    }
    private void sortData(List<WarningDealModel.DBean.ListBean> mDataList) {
        Collections.sort(mDataList, new Comparator<WarningDealModel.DBean.ListBean>() {
            @Override
            public int compare(WarningDealModel.DBean.ListBean listBean, WarningDealModel.DBean.ListBean t1) {
                if(listBean != null && t1 != null){
                    Date date1 = DataUtils.string2Data(listBean.getCreateDate());
                    Date date2 = DataUtils.string2Data(t1.getCreateDate());
                    if(date1 != null && date2 != null){
                        if(date1.before(date2)){
                            return 1;
                        }
                    }
                }
                return -1;
            }
        });
    }

    @Override
    public int getCount() {
        sortData(list);
        return super.getCount();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final WarningDealModel.DBean.ListBean model = list.get(position);

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
        holder.ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentActivity(model.getFaceImage());
            }
        });
        holder.ivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentActivity(model.getModelImage());
            }
        });
        holder.tvType.setText(model.getAlarmPlaceDesc());
        Date time = TimeRenderUtils.getTime("yyyy-MM-dd HH:mm:ss", model.getCreateDate());
        String createTime = TimeRenderUtils.getDate("MM-dd HH:mm", time.getTime());
        holder.tvTime.setText(createTime);
        holder.name.setText(model.getAlarmName());
        holder.idCard.setText(model.getAlarmIdcard());
        if(!TextUtils.isEmpty(model.getAlarmRemark())){
            holder.remark.setText(model.getAlarmRemark());
        }
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
        Log.e("111", "getView: "+model.getFullviewImage() );
            if(model.getFullviewImage() != null){
                Picasso.with(context).load(ApiImpl.DOMIN + model.getFullviewImage())
                        .placeholder(R.mipmap.jz_07)
                        .error(R.mipmap.jz_07)
                        .fit()
                        .into(holder.fullView);
            }
            holder.fullView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intentActivity(ApiImpl.DOMIN + model.getFullviewImage());
                }
            });
        return convertView;
    }
    private void intentActivity(String imageUrl){
        Intent intent = new Intent(context,LargeMapActivity.class);
        intent.putExtra("imageurl",imageUrl);
        context.startActivity(intent);
    }

    class ViewHolder {
        TextView tvType;
        TextView tvTime;
        ImageView ivPic;
        ImageView ivHead;
        TextView name;
        TextView idCard;
        TextView remark;
        ImageView fullView;
        ViewHolder(View view) {
            tvType = (TextView) view.findViewById(R.id.place);
            tvTime = (TextView) view.findViewById(R.id.time);
            ivPic = (ImageView)view. findViewById(R.id.camera_image);
            ivHead = (ImageView)view. findViewById(R.id.idcard_image);
            name = (TextView) view.findViewById(R.id.name);
            idCard = (TextView) view.findViewById(R.id.idcard);
            remark = (TextView) view.findViewById(R.id.type);
            fullView = (ImageView) view.findViewById(R.id.full_view_image);
        }

    }
}

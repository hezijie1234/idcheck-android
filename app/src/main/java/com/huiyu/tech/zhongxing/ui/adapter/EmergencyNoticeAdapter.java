package com.huiyu.tech.zhongxing.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.models.EmergencyNoticeModel;
import com.huiyu.tech.zhongxing.models.NoticeModel;
import com.huiyu.tech.zhongxing.ui.ZZBaseAdapter;
import com.huiyu.tech.zhongxing.utils.DataUtils;
import com.huiyu.tech.zhongxing.utils.ImageUtils;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by ml on 2016/8/5.
 */
public class EmergencyNoticeAdapter extends ZZBaseAdapter<EmergencyNoticeModel.ListBean> {
    public EmergencyNoticeAdapter(Context context) {
        super(context);
    }

    /**对数据按时间倒序排列
     * @param mDataList
     */
    private void sortData(List<EmergencyNoticeModel.ListBean> mDataList) {
        Collections.sort(mDataList, new Comparator<EmergencyNoticeModel.ListBean>() {
            @Override
            public int compare(EmergencyNoticeModel.ListBean listBean, EmergencyNoticeModel.ListBean t1) {
                Date date1 = DataUtils.string2Data(listBean.getPubeDate());
                Date date2 = DataUtils.string2Data(t1.getPubeDate());
                if(date1.before(date2)){
                    return 1;
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
        EmergencyNoticeModel.ListBean model = list.get(position);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_emergency_notice, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(model.getTitle());
        holder.tvDesc.setText(model.getRemarks());
        holder.tvTime.setText(model.getPubeDate());
        holder.publisher.setText(model.getCreateName());
        Picasso.with(context).load(Constants.IMG_HOST + model.getImage())
                .placeholder(R.mipmap.jz_11)
                .error(R.mipmap.jz_11)
                .fit()
                .into(holder.ivPic);
//        ImageUtils.setImage(context,model.getImage(),holder.ivPic,R.mipmap.jz_11);
        return convertView;
    }

    class ViewHolder {
        private ImageView ivPic;
        private TextView tvTitle;
        private TextView tvDesc;
        private TextView tvTime;
        private TextView publisher;
        ViewHolder(View view) {
            tvDesc = (TextView) view.findViewById(R.id.tv_desc);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            ivPic = (ImageView) view.findViewById(R.id.iv_pic);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
            publisher = (TextView) view.findViewById(R.id.item_emergency_publisher);
        }

    }
}

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
import com.huiyu.tech.zhongxing.models.NoticeModel;
import com.huiyu.tech.zhongxing.ui.ZZBaseAdapter;
import com.huiyu.tech.zhongxing.utils.DataUtils;
import com.huiyu.tech.zhongxing.utils.ImageUtils;
import com.huiyu.tech.zhongxing.utils.LogUtils;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by ml on 2016/8/5.
 */
public class NoticeAdapter extends ZZBaseAdapter<NoticeModel.ListBean> {
    public NoticeAdapter(Context context) {
        super(context);
    }

    /**对数据按时间倒序排列
     * @param mDataList
     */
    private void sortData(List<NoticeModel.ListBean> mDataList) {
        Collections.sort(mDataList, new Comparator<NoticeModel.ListBean>() {
            @Override
            public int compare(NoticeModel.ListBean listBean, NoticeModel.ListBean t1) {
                Date date1 = DataUtils.string2Data(listBean.getCreateDate());
                Date date2 = DataUtils.string2Data(t1.getCreateDate());
                if(date1.before(date2)){
                    return 1;
                }
                return -1;
            }
        });
    }

    /**重写getCount方法，调用排序方法
     * @return
     */
    @Override
    public int getCount() {
        sortData(list);
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        NoticeModel.ListBean model = list.get(position);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_notice, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(model.getTitle());
        //holder.tvDesc.setText(model.getRemarks());
        holder.publisherTv.setText(model.getCreateName());
        holder.publishTime.setText(model.getCreateDate());
        Log.e("111", "getView: "+model.getImage() );
        if(!TextUtils.isEmpty(model.getImage())){
            Picasso.with(context).load(model.getImage())
                    .placeholder(R.mipmap.jz_11)
                    .error(R.mipmap.jz_11)
                    .fit()
                    .into(holder.ivPic);
        }
        return convertView;
    }

    class ViewHolder {
        private ImageView ivPic;
        private TextView tvTitle;
        //private TextView tvDesc;
        private TextView publisherTv;
        private TextView publishTime;

        ViewHolder(View view) {
            //tvDesc = (TextView) view.findViewById(R.id.tv_desc);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            ivPic = (ImageView)view. findViewById(R.id.iv_pic);
            publisherTv = (TextView) view.findViewById(R.id.item_notice_publisher);
            publishTime = (TextView) view.findViewById(R.id.item_notice_time);
        }

    }
}

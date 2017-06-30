package com.huiyu.tech.zhongxing.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.models.SuspectRecordModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017-06-27.
 */

public class RecordAdapter extends BaseAdapter {

    private Context context;
    private List<SuspectRecordModel.DBean.ListBean> mDataList;

    public RecordAdapter(Context context, List<SuspectRecordModel.DBean.ListBean> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }

    @Override
    public int getCount() {
        Log.e("111", "getCount: "+mDataList.size() );
        return null == mDataList ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("111", "getView: "+position );
        ViewHolder holder = null;
        if(null == convertView){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_suspect_record,parent,false);
            holder = new ViewHolder(convertView);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        SuspectRecordModel.DBean.ListBean dBean = mDataList.get(position);
        holder.time.setText(dBean.getOperation_time());
        holder.typeName.setText(dBean.getSuspectTypeName());
        Picasso.with(context).load(dBean.getFace_img_src() )
                .placeholder(R.mipmap.id_03)
                .error(R.mipmap.id_03)
                .fit()
                .into(holder.cameraImage);
        Picasso.with(context).load(dBean.getIdcrad_img_src() )
                .placeholder(R.mipmap.id_03)
                .error(R.mipmap.id_03)
                .fit()
                .into(holder.idCardImage);
        return convertView;
    }
   class ViewHolder{
       ImageView cameraImage;
       ImageView idCardImage;
       TextView typeName;
       TextView time;

       public ViewHolder(View view) {
           view.setTag(this);
           cameraImage = (ImageView) view.findViewById(R.id.camera_image);
           idCardImage = (ImageView) view.findViewById(R.id.idcard_image);
           typeName = (TextView) view.findViewById(R.id.typeName);
           time = (TextView) view.findViewById(R.id.time);
       }
   }

}

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
import com.huiyu.tech.zhongxing.models.BlackRecordModel;
import com.huiyu.tech.zhongxing.models.RecongRecordModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.huiyu.tech.zhongxing.api.ApiImpl.DOMIN;

/**
 * Created by Administrator on 2017-06-27.
 */

public class BlackRecordAdapter extends BaseAdapter {

    private Context context;
    private List<BlackRecordModel.DBean.ListBean> mDataList;

    public BlackRecordAdapter(Context context, List<BlackRecordModel.DBean.ListBean> mDataList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_black_record,parent,false);
            holder = new ViewHolder(convertView);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        BlackRecordModel.DBean.ListBean dBean = mDataList.get(position);

        holder.time.setText(dBean.getCreateDate());
        holder.name.setText(dBean.getUserName());
        holder.idCard.setText(dBean.getIdcard());
        String imageStr = dBean.getUserImage();
        int i = imageStr.indexOf("|");

        String firstImage = imageStr.substring(0,i);
        String secondImage = null;
        if((i+1) < imageStr.length()){
            secondImage = imageStr.substring(i+1,imageStr.length() - 1);
        }

        Log.e("111", "getView: "+firstImage + "----"+secondImage );
        Picasso.with(context).load(firstImage )
                .placeholder(R.mipmap.id_03)
                .error(R.mipmap.id_03)
                .fit()
                .into(holder.cameraImage);
        Picasso.with(context).load(secondImage )
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
       TextView idCard;
       TextView name;

       public ViewHolder(View view) {
           view.setTag(this);
           cameraImage = (ImageView) view.findViewById(R.id.camera_image);
           idCardImage = (ImageView) view.findViewById(R.id.idcard_image);
           typeName = (TextView) view.findViewById(R.id.typeName);
           time = (TextView) view.findViewById(R.id.time);
           name = (TextView) view.findViewById(R.id.name);
           idCard = (TextView) view.findViewById(R.id.idcard);
       }
   }

}

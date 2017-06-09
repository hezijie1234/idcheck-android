package com.huiyu.tech.zhongxing.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.models.SuspectInfo;
import com.huiyu.tech.zhongxing.utils.Base64Util;

import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */

public class SuspectListAdapter extends RecyclerView.Adapter<SuspectListAdapter.MyViewHolder>{

    private List<SuspectInfo> data;

    public SuspectListAdapter(List<SuspectInfo> data){
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suspect,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SuspectInfo info = data.get(position);
        holder.ivAvatarPhone.setImageBitmap(Base64Util.base64ToBitmap(info.phoneImage));
        holder.ivAvatarServer.setImageBitmap(Base64Util.base64ToBitmap(info.serverImage));
        holder.tvName.setText(info.name);
        holder.tvCard.setText(info.idcrad);
        holder.tvType.setText(info.type);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder{

         private ImageView ivAvatarPhone;
         private ImageView ivAvatarServer;
         private TextView tvName;
         private TextView tvCard;
         private TextView tvType;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivAvatarPhone = (ImageView) itemView.findViewById(R.id.ivAvatarPhone);
            ivAvatarServer = (ImageView) itemView.findViewById(R.id.ivAvatarServer);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvCard = (TextView) itemView.findViewById(R.id.tvCard);
            tvType = (TextView) itemView.findViewById(R.id.tvType);
        }
    }
}

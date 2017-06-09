package com.huiyu.tech.zhongxing.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.models.ContactsModel;
import com.huiyu.tech.zhongxing.models.NoticeModel;
import com.huiyu.tech.zhongxing.ui.ZZBaseAdapter;
import com.huiyu.tech.zhongxing.utils.CommonUtils;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.ImageUtils;
import com.squareup.picasso.Picasso;

import java.util.Locale;

/**
 * Created by ml on 2016/8/5.
 */
public class ContactsAdapter extends ZZBaseAdapter<ContactsModel> implements SectionIndexer {
    public ContactsAdapter(Context context) {
        super(context);
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    @Override
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase(Locale.CHINESE).charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Object[] getSections() {
        return null;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_contacts, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ContactsModel mContent = list.get(position);
        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            holder.itemContactsCatalog.setVisibility(View.VISIBLE);
            holder.itemContactsCatalog.setText(mContent.getSortLetters());
        } else {
            holder.itemContactsCatalog.setVisibility(View.GONE);
        }
        Picasso.with(context).load(Constants.IMG_HOST + mContent.getAvatar())
                .placeholder(R.mipmap.jz_03)
                .error(R.mipmap.jz_03)
                .fit()
                .into(holder.ivHead);
//        ImageUtils.setImage(context, mContent.getAvatar(),holder.ivHead,R.mipmap.jz_03);
        holder.itemName.setText(mContent.getNickname());
        holder.ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(mContent.getPhone())){
                    CommonUtils.call((Activity) context,mContent.getPhone());
                }else {
                    CustomToast.showToast(context,"当前用户没有电话");
                }
            }
        });
        holder.ivMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(mContent.getPhone())){
                    CommonUtils.sendSms((Activity) context,mContent.getPhone());
                }else {
                    CustomToast.showToast(context,"当前用户没有电话");
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView itemContactsCatalog;
        private ImageView ivHead;
        private TextView itemName;
        private ImageView ivMsg;
        private ImageView ivPhone;

        ViewHolder(View view) {
            itemContactsCatalog = (TextView) view.findViewById(R.id.item_contacts_catalog);
            ivHead = (ImageView) view.findViewById(R.id.iv_head);
            itemName = (TextView) view.findViewById(R.id.item_name);
            ivMsg = (ImageView) view.findViewById(R.id.iv_msg);
            ivPhone = (ImageView) view.findViewById(R.id.iv_phone);
        }

    }
}

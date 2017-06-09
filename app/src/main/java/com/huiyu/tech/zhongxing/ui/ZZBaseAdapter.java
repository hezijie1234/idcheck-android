package com.huiyu.tech.zhongxing.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2015/12/7.
 */
public abstract class ZZBaseAdapter<T> extends BaseAdapter {

    private static final String TAG = "ZZBaseAdapter";
    protected Context context;
    protected LayoutInflater inflater;
    protected List<T> list = new ArrayList<T>();

    public ZZBaseAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 判断数据是否为空
     *
     * @return 为空返回true，不为空返回false
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * 在原有的数据上添加新数据
     *
     * @param itemList
     */
    public void addItems(List<T> itemList) {
        this.list.addAll(itemList);
        notifyDataSetChanged();
    }

    /**
     * 在原有的数据上添加新数据
     *
     * @param data
     */
    public void addItem(T data) {
        this.list.add(data);
        notifyDataSetChanged();
    }

    /**
     * 设置为新的数据，旧数据会被清空
     *
     * @param itemList
     */
    public void setItems(List<T> itemList) {
        if (itemList != null) {
            list.clear();
            list.addAll(itemList);
        }
        notifyDataSetChanged();
    }

    /**
     * 获取列表数据
     *
     */
    public List<T> getItems() {
        return list;
    }

    /**
     * 清空数据
     */
    public void clearItems() {
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

}

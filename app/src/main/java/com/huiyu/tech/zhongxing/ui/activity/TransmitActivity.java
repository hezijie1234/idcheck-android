package com.huiyu.tech.zhongxing.ui.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransmitActivity extends ZZBaseActivity {
    private ExpandableListView listView;
    private List<String> parentList = new ArrayList<>();
    private Map<String,List<String>> mDataMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transmit);
        showBackView();
        listView = (ExpandableListView) findViewById(R.id.expandablelistview);
        parentList.add("市局刑警队");
        parentList.add("市局特警队");
        parentList.add("市局稽查大队");
        List<String> list1 = new ArrayList<>();
        list1.add("张三");
        list1.add("李四");
        list1.add("王五");
        List<String> list2 = new ArrayList<>();
        list2.add("张飞");
        list2.add("赵信");
        list2.add("关羽");
        List<String> list3 = new ArrayList<>();
        list3.add("刘备");
        list3.add("赵云");
        list3.add("诸葛亮");
        mDataMap.put(parentList.get(0),list1);
        mDataMap.put(parentList.get(1),list2);
        mDataMap.put(parentList.get(2),list3);
        TransmitAdapter adapter = new TransmitAdapter();
        listView.setAdapter(adapter);
    }

    class TransmitAdapter extends BaseExpandableListAdapter{

        @Override
        public int getGroupCount() {
            return mDataMap.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return mDataMap.get(parentList.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mDataMap.get(parentList.get(groupPosition));
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return mDataMap.get(parentList.get(groupPosition)).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            TextView textView = new TextView(TransmitActivity.this);
            textView.setText(parentList.get(groupPosition));
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(30);
            return textView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null){
                convertView = LayoutInflater.from(TransmitActivity.this).inflate(R.layout.expandable_item,parent,false);
                holder = new ViewHolder(convertView);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(mDataMap.get(parentList.get(groupPosition)).get(childPosition));
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
        class ViewHolder{
            TextView textView;
            CheckBox checkBox;

            public ViewHolder(View view) {
                view.setTag(this);
                textView = (TextView) view.findViewById(R.id.person_name);
                checkBox = (CheckBox) view.findViewById(R.id.expand_checkbox);
            }
        }
    }
}

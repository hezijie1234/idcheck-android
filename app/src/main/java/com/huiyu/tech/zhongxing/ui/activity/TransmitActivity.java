package com.huiyu.tech.zhongxing.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.models.TranslatePerson;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.utils.CustomToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransmitActivity extends ZZBaseActivity implements OnResponseListener {
    private ExpandableListView listView;
    private List<String> parentList = new ArrayList<>();
    private Map<String,List<TranslatePerson>> mDataMap = new HashMap<>();
    private List<String> mTranslatedPserson;
    private TransmitAdapter adapter;
    private String alarmId = null;
    private Button mRecall;
    private Button mTransmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transmit);
        showBackView();
        init();
        initView();
        ApiImpl.getInstance().getTranslateList(this);
        showProgressDialog();
    }

    private void initView() {
        listView = (ExpandableListView) findViewById(R.id.expandablelistview);
        mRecall = (Button) findViewById(R.id.btn_recall);
        mTransmit = (Button) findViewById(R.id.btn_transmit);
        adapter = new TransmitAdapter();
        listView.setAdapter(adapter);

        mTransmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < mTranslatedPserson.size(); i++) {
                    if(i == 0){
                        builder.append(mTranslatedPserson.get(i));
                    }else{
                        builder.append(",").append(mTranslatedPserson.get(i));
                    }
                }
                ApiImpl.getInstance().transmitEmergency(alarmId,builder.toString(),TransmitActivity.this);
            }
        });
    }

    private void init() {
        Intent intent = getIntent();
        alarmId = intent.getStringExtra("alarmId");
        Log.e("111", "init: " + alarmId );
        mTranslatedPserson = new ArrayList<>();
    }

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        hideProgressDialog();
        Gson gson = new Gson();
        if(flag.equals(ApiImpl.TRANSLATE_LIST)){
            JSONArray d = json.optJSONArray("d");
            for (int i = 0; i < d.length(); i++) {
                try {
                    JSONObject o = (JSONObject) d.get(i);
                    TranslatePerson translatePerson = gson.fromJson(o.toString(), TranslatePerson.class);
                    List<TranslatePerson> child = translatePerson.getChild();
                    String name = translatePerson.getName();//湖北省总公司。
                    for (int j = 0; j < child.size(); j++) {
                        TranslatePerson translatePerson1 = child.get(j);//武汉市分公司或公司领导
                        String name1 = translatePerson1.getName();//分公司名称或部门名称
                        List<TranslatePerson> child1 = translatePerson1.getChild();//部门所有人，或公司的所有部门。
                        if(child1 != null && child1.size() > 0){
                            if(child1.get(0).getUserId() != null){
                                mDataMap.put(name + name1,child1);
                                parentList.add(name+name1);
                            }else {
                                for (int k = 0; k < child1.size(); k++) {
                                    TranslatePerson translatePerson2 = child1.get(k);//可能是部门，也可能是次一级子公司。
                                    String name2 = translatePerson2.getName();
                                    List<TranslatePerson> child2 = translatePerson2.getChild();
                                    if(child2 != null && child2.size()>0){
                                        if(child2.get(0).getUserId() != null){
                                            mDataMap.put(name1 + name2,child2);
                                            parentList.add(name1 + name2);
                                        }else {
                                            for (int l = 0; l < child2.size(); l++) {
                                                TranslatePerson translatePerson3 = child2.get(l);
                                                String name3 = translatePerson3.getName();
                                                List<TranslatePerson> child3 = translatePerson3.getChild();
                                                if(child3 != null && child3.size() > 0){
                                                    if(child3.get(0).getUserId() != null){
                                                        mDataMap.put(name2 + name3 ,child3);
                                                        parentList.add(name2 + name3);
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }

                        }

                    }
                    adapter.notifyDataSetChanged();
                    //当userId等于空就说明这个对象不是某个人。

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
//            Log.e("111", "onAPISuccess: "+json );
//            TranslatePerson translatePerson = gson.fromJson(json.toString(), TranslatePerson.class);
//            List<TranslatePerson.DBean> data = translatePerson.getD();
//            if(data != null && data.size() > 0){
//                for (int i = 0; i < data.size(); i++) {
//
//                    String mainName = data.get(i).getName();
//                    List<TranslatePerson.DBean.ChildBeanX> childBeanX = data.get(i).getChild();
//                    if(childBeanX != null && childBeanX.size() > 0){
//                        for (int j = 0; j <childBeanX.size() ; j++) {
//                            String childName = childBeanX.get(j).getName();
//                            parentList.add(mainName + childName);
//                            mDataMap.put(mainName + childName ,childBeanX.get(j).getChild());
//                            adapter.notifyDataSetChanged();
//                        }
//                    }
//
//                }
//            }
            Log.e("111", "onAPISuccess: "+mDataMap );
        }else if (flag.equals(ApiImpl.TRANSLATE)){
            Log.e("111", "onAPISuccess: "+json );
            CustomToast.showToast(this,json.optString("m"));
        }
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        hideProgressDialog();
        CustomToast.showToast(this,error);
    }

    class TransmitAdapter extends BaseExpandableListAdapter{

        @Override
        public int getGroupCount() {
            return mDataMap.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return mDataMap.get(parentList.get(groupPosition)) == null ? 0 : mDataMap.get(parentList.get(groupPosition)).size();
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
            textView.setTextSize(20);
            return textView;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            final TranslatePerson child = mDataMap.get(parentList.get(groupPosition)).get(childPosition);
            if(convertView == null){
                convertView = LayoutInflater.from(TransmitActivity.this).inflate(R.layout.expandable_item,parent,false);
                holder = new ViewHolder(convertView);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(child.getName());
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        Log.e("111", "onCheckedChanged: " + child.getUserId() );
                        mTranslatedPserson.add(child.getUserId());
                    }else {
                        mTranslatedPserson.remove(child.getUserId());
                    }
                }
            });
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

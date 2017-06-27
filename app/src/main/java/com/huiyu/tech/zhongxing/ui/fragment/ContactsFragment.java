package com.huiyu.tech.zhongxing.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.models.ContactsModel;
import com.huiyu.tech.zhongxing.models.ContactsPageModel;
import com.huiyu.tech.zhongxing.ui.BaseFragment;
import com.huiyu.tech.zhongxing.ui.adapter.ContactsAdapter;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.LogUtils;
import com.huiyu.tech.zhongxing.utils.pinyin.CharacterParser;
import com.huiyu.tech.zhongxing.utils.pinyin.PinyinComparator2;
import com.huiyu.tech.zhongxing.widget.ClearEditText;
import com.huiyu.tech.zhongxing.widget.SideBar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends BaseFragment implements OnResponseListener {

    private ClearEditText etSearch;
    private ListView lvContacts;
    private TextView tvDialog;
    private SideBar sidebar;

    private List<ContactsPageModel.ListBean> mAllContactsList;
    private List<ContactsModel> sortList;//排序后的数据
    private ContactsAdapter contactsAdapter;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator2 pinyinComparator;

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contacts, container, false);
        initBase();
        initView();
        loadData();
        return view;
    }

    private void initBase(){
        mAllContactsList = new ArrayList<>();
        sortList = new ArrayList<>();
    }

    private void initView() {
        showTitleView(getResources().getString(R.string.title_contacts));

        etSearch = (ClearEditText) view.findViewById(R.id.et_search);
        lvContacts = (ListView) view.findViewById(R.id.lv_contacts);
        tvDialog = (TextView) view.findViewById(R.id.tv_dialog);
        sidebar = (SideBar) view.findViewById(R.id.sidebar);

        sidebar.setTextView(tvDialog);
        characterParser = CharacterParser.getInstance();
        mAllContactsList = new ArrayList<ContactsPageModel.ListBean>();

        pinyinComparator = new PinyinComparator2();
        contactsAdapter = new ContactsAdapter(getActivity());
        lvContacts.setAdapter(contactsAdapter);

        initListener();
    }

    private void initListener() {

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                LogUtils.i("onTextChanged");
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                LogUtils.i("afterTextChanged");
                String content = etSearch.getText().toString();
//                if (content.length() > 0) {
//                    ArrayList<SelectContactsModel> fileterList = (ArrayList<SelectContactsModel>) search(content);
//                    selectContactsAdapter.setItems(fileterList);
//                } else {
////                    selectContactsAdapter.setItems(mAllContactsList);
//                }
//                lvContacts.setSelection(0);
                matchSearch(content);
            }
        });

        // 设置右侧[A-Z]快速导航栏触摸监听
        sidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = contactsAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    lvContacts.setSelection(position);
                }
            }
        });
    }

    private void initData(){
    }

    private void loadData(){
        showProgressDialog(true);
        ApiImpl.getInstance().getContactsList(this);
    }

    /**
     * @Title: getSortContacts
     * @Description: 获取排序后的通讯录数据
     * @return
     */
    public List<ContactsModel> getSortContacts(List<ContactsPageModel.ListBean> contacts){
        if(contacts==null){
            return null;
        }
        List<ContactsModel> sortList = new ArrayList<ContactsModel>();
        for (int i = 0; i < contacts.size(); i++){
            ContactsModel sortModel = new ContactsModel();
            // 汉字转换成拼音
            if(characterParser==null){
                characterParser = new CharacterParser();
            }
            String pinyin;
            if(!TextUtils.isEmpty(contacts.get(i).getName())){
                pinyin = getSortLetter(contacts.get(i).getName());
                sortModel.setNickname(contacts.get(i).getName());
            }else if(!TextUtils.isEmpty(contacts.get(i).getPhone())){
                pinyin = getSortLetter(contacts.get(i).getPhone());
                sortModel.setNickname(contacts.get(i).getPhone());
            }else{
                pinyin = "";
                sortModel.setNickname(contacts.get(i).getPhone());
            }
            sortModel.setPinyin(pinyin);
                if(TextUtils.isEmpty(pinyin)){
                    sortModel.setSortLetters("#");
                }else{
                    String sortString = pinyin.substring(0, 1).toUpperCase();
                    // 正则表达式，判断首字母是否是英文字母
                    if (sortString.matches("[A-Z]")){
                        sortModel.setSortLetters(sortString.toUpperCase());
//                contacts.get(i).setPinyin(pinyin);
                    } else{
                        sortModel.setSortLetters("#");
                    }
                }
            sortModel.setPhone(contacts.get(i).getMobile());
            sortModel.setUser_id(contacts.get(i).getId());
            sortModel.setAvatar(contacts.get(i).getPhoto());
            sortList.add(sortModel);
        }
        // 根据a-z进行排序源数据
        if(pinyinComparator==null){
            pinyinComparator = new PinyinComparator2();
        }
        Collections.sort(sortList, pinyinComparator);

        return sortList;
    }

    /**
     * 名字转拼音,取首字母
     *
     * @param name
     * @return
     */
    private String getSortLetter(String name) {
        String letter = "#";
        if (name == null) {
            return letter;
        }
        // 汉字转换成拼音
        String pinyin = characterParser.getSelling(name);
//        String pinyin = PinYin.getPinYin(name);
        Log.i("main", "pinyin:" + pinyin);
        String sortString = pinyin.substring(0, 1).toUpperCase(Locale.CHINESE);

        // 正则表达式，判断首字母是否是英文字母
        if (sortString.matches("[a-zA-Z]")) {
            letter = sortString.toUpperCase(Locale.CHINESE);
        }
        return letter;
    }

    /**
     * 模糊查询
     *
     * @param str
     * @return
     */
    private void matchSearch(String str) {
        List<ContactsModel> filterList = new ArrayList<ContactsModel>();// 过滤后的list
        if(str.length() == 0){
            filterList = sortList;
        }else{
            for (ContactsModel contact : sortList) {
                // 姓名全匹配,姓名首字母简拼匹配,姓名全字母匹配
                if (str.matches(enReg)) {
                    LogUtils.i("dddd1");
                    if (contact.getNickname().contains(str) || (null != contact.getPinyin() && contact.getPinyin().toLowerCase(Locale.CHINESE).contains(str.toLowerCase(Locale.CHINESE)))) {
                        filterList.add(contact);
                    }
                } else if (str.matches(chReg)) {
                    LogUtils.i("dddd2");
                    if (contact.getNickname().contains(str)) {
                        filterList.add(contact);
                    }
                }
            }
        }
        contactsAdapter.setItems(filterList);
    }

    String chReg = "[\\u4E00-\\u9FA5]+";// 中文字符串匹配
    String enReg="[^\\u4E00-\\u9FA5]+";//除中文外的字符匹配

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        Log.e("111", "onAPISuccess:数据加载成功 "+json );
        hideProgressDialog();
        ContactsPageModel checkInfo = JSON.parseObject(json.optString("d"), ContactsPageModel.class);
        if(checkInfo.getList() != null && checkInfo.getList().size() > 0){
            mAllContactsList.addAll(checkInfo.getList());
            sortList = getSortContacts(mAllContactsList);
            contactsAdapter.setItems(sortList);
        }
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        hideProgressDialog();
        CustomToast.showToast(getActivity(),error);
    }
}

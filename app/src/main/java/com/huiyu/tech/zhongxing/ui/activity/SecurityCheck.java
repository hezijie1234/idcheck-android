package com.huiyu.tech.zhongxing.ui.activity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.models.SecurityModel;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.huiyu.tech.zhongxing.api.ApiImpl.SECURITY_CHECK;
import static com.huiyu.tech.zhongxing.api.ApiImpl.SECURITY_SEND;

public class SecurityCheck extends ZZBaseActivity implements OnResponseListener {
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private LinearLayout timeLinear;
    private TextView timeText;
    private Button sendBtn;
    private RadioGroup checkGroup;
    private List<String> stationList;
    private List<String> wayList;
    private List<String> companyList;
    private ArrayAdapter<String> stationAdapter;
    private ArrayAdapter<String> wayAdapter;
    private ArrayAdapter<String> companyAdapter;
    private Map<String,String> map = new HashMap<>();
    private String station;
    private String way;
    private String company;
    private String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_check);
        init();
        initView();
    }

    private void init() {
        stationList = new ArrayList<>();
        wayList = new ArrayList<>();
        companyList = new ArrayList<>();
        ApiImpl.getInstance().securityCheck(this);
    }

    private void initView() {
        spinner1 = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner3);
        spinner3 = (Spinner) findViewById(R.id.spinner4);
        timeLinear = (LinearLayout) findViewById(R.id.spinner_linear2);
        timeText = (TextView) findViewById(R.id.time_tv);
        sendBtn = (Button) findViewById(R.id.btn_send_check);
        checkGroup = (RadioGroup) findViewById(R.id.check_radiogroup);
        stationAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, stationList);
        wayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, wayList);
        companyAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, companyList);
        spinner1.setAdapter(stationAdapter);
        spinner2.setAdapter(wayAdapter);
        spinner3.setAdapter(companyAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                station = map.get(stationList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                way = map.get(wayList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                company = map.get(companyList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        timeLinear.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {
                showDatePickerDialog(timeText);
            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiImpl.getInstance().securitySend(station,timeText.getText().toString(),way,company,state,SecurityCheck.this);
            }
        });

        checkGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.check_success){
                    state = "1";
                    Log.e("111", "onCheckedChanged: success" );
                }else if (checkedId == R.id.check_unsuccess){
                    state = "0";
                    Log.e("111", "onCheckedChanged: unsuccess" );
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void showDatePickerDialog(final TextView result) {
        Calendar calendar = Calendar.getInstance();
        if (TextUtils.isEmpty(result.getText())) {
            calendar.setTime(new Date());
        } else {
            try {
                calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(result.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        new DatePickerDialog(this,R.style.ThemeDialog,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = year + "-" + (month < 10 ? "0" : "") + (month + 1) + "-" + (dayOfMonth < 10 ? "0" : "") + dayOfMonth;
                result.setText(date);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        Gson gson = new Gson();
        if(SECURITY_CHECK.equals(flag)){
            Log.e("111", "onAPISuccess: " + json);
            SecurityModel securityModel = gson.fromJson(json.toString(), SecurityModel.class);
            SecurityModel.DBean d = securityModel.getD();
            List<SecurityModel.DBean.StationListBean> stationList = d.getStationList();
            List<SecurityModel.DBean.WayListBean> wayList = d.getWayList();
            List<SecurityModel.DBean.CompanyListBean> companyList = d.getCompanyList();
            for(SecurityModel.DBean.StationListBean stationBean : stationList){
                this.stationList.add(stationBean.getLabel());
                map.put(stationBean.getLabel(),stationBean.getValue());
            }
            for(SecurityModel.DBean.WayListBean wayBean :wayList){
                this.wayList.add(wayBean.getLabel());
                map.put(wayBean.getLabel(),wayBean.getValue());
            }
            for(SecurityModel.DBean.CompanyListBean companyBean :companyList){
                this.companyList.add(companyBean.getLabel());
                map.put(companyBean.getLabel(),companyBean.getValue());
            }
            station = this.stationList.get(0);
            way = this.wayList.get(0);
            company = this.companyList.get(0);
            stationAdapter.notifyDataSetChanged();
            wayAdapter.notifyDataSetChanged();
            companyAdapter.notifyDataSetChanged();
        }else if (SECURITY_SEND.equals(flag)){
            Log.e("111", "send: "+json );
            Toast.makeText(this, json.optString("m"), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}

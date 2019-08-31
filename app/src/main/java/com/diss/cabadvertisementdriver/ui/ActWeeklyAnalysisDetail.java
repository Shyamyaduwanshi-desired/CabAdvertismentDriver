package com.diss.cabadvertisementdriver.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.diss.cabadvertisementdriver.R;
import com.diss.cabadvertisementdriver.adapter.WeeklyReportAdapter;
import com.diss.cabadvertisementdriver.model.DriverCampaignBean;
import com.diss.cabadvertisementdriver.model.WeeklyReportBean;
import com.diss.cabadvertisementdriver.presenter.DriverCampaignPresenter;

import java.util.ArrayList;

public class ActWeeklyAnalysisDetail extends AppCompatActivity implements View.OnClickListener, DriverCampaignPresenter.CampaingListData,WeeklyReportAdapter.WeeklyReportClick {
Context context;
    ImageView imageViewback;
    AppData appdata;
    private DriverCampaignPresenter presenter;
    TextView tvCampaignNm,tvCampaignAmt,tvValidityDate,tvNoData;
    LinearLayout categorylinear,lyMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_weekly_analysis_detail);
        context=this;
        appdata=new AppData(this);
        presenter = new DriverCampaignPresenter(this, this);
        InitCompo();
        Listener();
        GetIntentData();
    }
    String campaignId="",reportType="";
    private void GetIntentData() {
        campaignId = getIntent().getStringExtra("s_id");
        reportType="yesterday";
        Log.e("","campaignId= "+campaignId);
        GetDriverCampaingData(1);



    }
    private void GetDriverCampaingData(int diff_) {
        if(appdata.isNetworkConnected(this)){
            switch (diff_)
            {
                case 1:
                    presenter.GetAnalysisOfACampaign(campaignId,reportType);
                    break;
            }

        }else {
            appdata.ShowNewAlert(this,"Please connect to internet");
        }
    }
    private void Listener() {
        imageViewback.setOnClickListener(this);

        tvMonth.setOnClickListener(this);
        tvWeek.setOnClickListener(this);
        tvYesterday.setOnClickListener(this);
    }

    TextView tvMonth,tvWeek,tvYesterday,tvLocNm,tvTotalKm,tvTotalhours,tvDateRange;
    public void InitCompo()
    {
        imageViewback=findViewById(R.id.imageback);
        lyMain=findViewById(R.id.ly_main);
        tvCampaignNm=findViewById(R.id.tv_compaign_name);
        tvCampaignAmt=findViewById(R.id.tv_compaign_amount);
        tvValidityDate=findViewById(R.id.tv_validity);
        tvNoData=findViewById(R.id.tv_no_data);
        categorylinear=findViewById(R.id.category_linear_id);

        tvMonth=findViewById(R.id.tv_month);
        tvWeek=findViewById(R.id.tv_week);
        tvYesterday=findViewById(R.id.tv_yesterday);

        tvLocNm=findViewById(R.id.tv_covered_loc);
        tvTotalKm=findViewById(R.id.tv_total_km);
        tvTotalhours=findViewById(R.id.tv_hours_km);
        tvDateRange=findViewById(R.id.tv_date);

        categorylinear.requestFocus();
        SetView(R.id.tv_yesterday);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imageback:
                finish();
                Animatoo.animateFade(this);
                break;
            case R.id.wrap:
                break;
            case R.id.bt_submit:
                break;
            case R.id.tv_month:
                reportType="month";
                SetView(R.id.tv_month);
                GetDriverCampaingData(1);
                break;
            case R.id.tv_week:
                reportType="week";
                SetView(R.id.tv_week);
                GetDriverCampaingData(1);
                break;
            case R.id.tv_yesterday:
                reportType="yesterday";
                SetView(R.id.tv_yesterday);
                GetDriverCampaingData(1);
                break;

        }
    }
    private void SetView(int tv_month) {
        tvMonth.setBackgroundResource(R.drawable.normal_bg);
        tvWeek.setBackgroundResource(R.drawable.normal_bg);
        tvYesterday.setBackgroundResource(R.drawable.normal_bg);
        switch (tv_month)
        {
            case R.id.tv_month:
                tvMonth.setBackgroundResource(R.drawable.selected_bg);
                break;
            case R.id.tv_week:
                tvWeek.setBackgroundResource(R.drawable.selected_bg);
                break;
            case R.id.tv_yesterday:
                tvYesterday.setBackgroundResource(R.drawable.selected_bg);
                break;
        }
    }
    ArrayList<DriverCampaignBean> arDriverCampaign=new ArrayList<>();
    @Override
    public void success(ArrayList<DriverCampaignBean> response, String status) {
        arDriverCampaign.clear();
        arDriverCampaign=response;
        setViewData();
    }


    @Override
    public void error(String response) {
        tvNoData.setVisibility(View.VISIBLE);
        appdata.ShowNewAlert(this,response);
    }

    @Override
    public void fail(String response) {
        tvNoData.setVisibility(View.VISIBLE);
        appdata.ShowNewAlert(this,response);
    }
    private void setViewData() {
        if(arDriverCampaign.size()>0)
        {
            tvCampaignNm.setText("Name of Campaign: "+arDriverCampaign.get(0).getsCampaignNm());
            tvCampaignAmt.setText("Rs. "+arDriverCampaign.get(0).getDriverHireAmount());

            tvValidityDate.setText("( "+appdata.ConvertDate(arDriverCampaign.get(0).getAdded_on())+" - "+appdata.ConvertDate01(arDriverCampaign.get(0).getLastdate())+" )");//tvValidityDate

            lyMain.setVisibility(View.VISIBLE);
            tvNoData.setVisibility(View.GONE);
//            DummyData();

            tvLocNm.setText(arDriverCampaign.get(0).getC_l_name());
            tvTotalKm.setText(arDriverCampaign.get(0).getTotal_km()+" Km");
            tvTotalhours.setText(arDriverCampaign.get(0).getTotal_hours()+" Hours");
            tvDateRange.setText(arDriverCampaign.get(0).getDate_range());
        }
        else
        {
            lyMain.setVisibility(View.GONE);
            tvNoData.setVisibility(View.VISIBLE);
        }

    }

//adapter click
    @Override
    public void onClick(int position, int diff) {

    }


}


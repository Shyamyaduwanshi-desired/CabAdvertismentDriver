package com.diss.cabadvertisementdriver.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.diss.cabadvertisementdriver.R;
import com.diss.cabadvertisementdriver.adapter.TimeLineAdapter;
import com.diss.cabadvertisementdriver.adapter.WeeklyReportAdapter;
import com.diss.cabadvertisementdriver.model.DriverCampaignBean;
import com.diss.cabadvertisementdriver.model.ImageBean;
import com.diss.cabadvertisementdriver.model.WeeklyReportBean;
import com.diss.cabadvertisementdriver.model.WithdrawAmountBean;
import com.diss.cabadvertisementdriver.presenter.DriverCampaignPresenter;
import com.diss.cabadvertisementdriver.presenter.HelpAndRequestPresenter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class WeeklyAnalysisReportActivity extends AppCompatActivity implements View.OnClickListener, DriverCampaignPresenter.CampaingListData,WeeklyReportAdapter.WeeklyReportClick {
Context context;
    ImageView imageViewback;
//    private VerifyCampaignDataPresenter VerifyCamDatapresenter;
    AppData appdata;
    private DriverCampaignPresenter presenter;
    private RecyclerView.Adapter mAdapter;
    RecyclerView rvMyCampignAnalysis;
    TextView tvCampaignNm,tvCampaignAmt,tvValidityDate,tvNoData;
    LinearLayout categorylinear,lyMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekly_analysis_activity);
        context=this;
        appdata=new AppData(this);
        presenter = new DriverCampaignPresenter(this, this);

        InitCompo();
        Listener();
        GetDriverCampaingData();
    }
    private void GetDriverCampaingData() {
        if(appdata.isNetworkConnected(this)){
            presenter.GetDriverCampaignList();
        }else {
            appdata.ShowNewAlert(this,"Please connect to internet");
        }
    }
    private void Listener() {
        imageViewback.setOnClickListener(this);
//        weeklylinear.setOnClickListener(this);
    }


    public void InitCompo()
    {
        imageViewback=findViewById(R.id.imageback);
//        weeklylinear=findViewById(R.id.wrap);
//        datelinear=findViewById(R.id.nav_shareinvite);

        lyMain=findViewById(R.id.ly_main);
        tvCampaignNm=findViewById(R.id.tv_compaign_name);
        tvCampaignAmt=findViewById(R.id.tv_compaign_amount);
        tvValidityDate=findViewById(R.id.tv_validity);
        tvNoData=findViewById(R.id.tv_no_data);
        categorylinear=findViewById(R.id.category_linear_id);

        rvMyCampignAnalysis = findViewById(R.id.rv_my_compaign);
        rvMyCampignAnalysis.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvMyCampignAnalysis.setHasFixedSize(true);
//        rvMyCampignAnalysis.clearOnScrollListeners();
//        RecyclerViewDisabler disabler = new RecyclerViewDisabler(false);
//        rvMyCampignAnalysis.addOnItemTouchListener(disabler);

// TO ENABLE/DISABLE JUST USE THIS
//        disabler.setEnable(true);
        rvMyCampignAnalysis.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerView.stopScroll();
//                if (viewModel.isItemSelected) {
//
//                }
            }
        });
        categorylinear.requestFocus();

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
//                countBACK=1;
//                if (count==0){
//                    datelinear.setVisibility(View.VISIBLE);
//                    count=1;
//
//                }else{
//                    datelinear.setVisibility(View.GONE);
//                    count=0;
//                }
                break;
            case R.id.bt_submit:
//                addValidationToViews();
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
            tvCampaignNm.setText("Name of Campaign: "+arDriverCampaign.get(0).getC_l_name());
            tvCampaignAmt.setText("INR "+arDriverCampaign.get(0).getPlan_amount());
            tvValidityDate.setText("( "+appdata.ConvertDate(arDriverCampaign.get(0).getAdded_on())+" - "+appdata.ConvertDate01(arDriverCampaign.get(0).getLastdate())+" )");//tvValidityDate

            lyMain.setVisibility(View.VISIBLE);
            tvNoData.setVisibility(View.GONE);
            DummyData();
        }
        else
        {
            lyMain.setVisibility(View.GONE);
            tvNoData.setVisibility(View.VISIBLE);
        }

    }
    ArrayList<WeeklyReportBean>arWeeklyAnalysis=new ArrayList<>();
    public void DummyData()
    {
        WeeklyReportBean bean;
        for(int i=0;i<3;i++)
        {
            bean=new WeeklyReportBean();
            bean.setWeeklyReportId((i+1)+"");
            switch (i)
            {
                case 0://ReportDate,TotalKm,totalTime,locNm
                    bean.setLocNm("Palasiya");
                    bean.setTotalKm("50 Km");
                    bean.setTotalTime("12 Hours");
                    bean.setReportDate("2019-06-18 11:17:55");

                    break;
                case 1:
                    bean.setLocNm("Anand Bazar");
                    bean.setTotalKm("30 Km");
                    bean.setTotalTime("15 Hours");
                    bean.setReportDate("2019-06-25 11:17:55");

                    break;
                case 2:
                    bean.setLocNm("Khajrana");
                    bean.setTotalKm("30 Km");
                    bean.setTotalTime("10 Hours");
                    bean.setReportDate("2019-06-30 11:17:55");

                    break;
            }
            arWeeklyAnalysis.add(bean) ;
        }
        SetAdapter();
    }

    public void SetAdapter()
    {
        mAdapter= new WeeklyReportAdapter(context, arWeeklyAnalysis, WeeklyAnalysisReportActivity.this);
        rvMyCampignAnalysis.setAdapter(mAdapter);
    }
//adapter click
    @Override
    public void onClick(int position, int diff) {
//        Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
        int pos=0;
        switch (diff)
        {
            case 1://left
                pos=position-1;
                if(pos>=0)
                {
                    SetScrollPosition(pos);
                }
                break;
            case 2://right
                pos=position+1;
                if(pos<arWeeklyAnalysis.size())
                {
                    SetScrollPosition(pos);
                }
                break;
        }
    }

    private void SetScrollPosition(final int pos) {
        rvMyCampignAnalysis.post(new Runnable() {
            @Override
            public void run() {
                rvMyCampignAnalysis.smoothScrollToPosition(pos);
//                rvMyCampignAnalysis.smoothScrollToPosition(mAdapter.getItemCount() - 1);
            }
        });
    }


    public class RecyclerViewDisabler implements RecyclerView.OnItemTouchListener {

        boolean isEnable = true;

        public RecyclerViewDisabler(boolean isEnable) {
            this.isEnable = isEnable;
        }

        public boolean isEnable() {
            return isEnable;
        }

        public void setEnable(boolean enable) {
            isEnable = enable;
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            return !isEnable;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {}

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept){}
    }

}


package com.diss.cabadvertisementdriver.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.diss.cabadvertisementdriver.R;
import com.diss.cabadvertisementdriver.adapter.VehicleAdapter;
import com.diss.cabadvertisementdriver.adapter.WeeklyReportAdapter;
import com.diss.cabadvertisementdriver.model.VehicelInfoBean;
import com.diss.cabadvertisementdriver.model.WeeklyReportBean;
import com.diss.cabadvertisementdriver.presenter.VehicleDetailPresenter;

import java.util.ArrayList;

public class VehicalActivity extends AppCompatActivity implements View.OnClickListener, VehicleAdapter.VehicelClick, VehicleDetailPresenter.VehicleInfo{

    Button btAddVechile;

    AppData appdata;
    VehicleDetailPresenter vehicleDetailPresenter;
    private RecyclerView.Adapter mAdapter;
    RecyclerView rvMyCampignAnalysis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehical_activity);
        appdata=new AppData(this);
        vehicleDetailPresenter = new VehicleDetailPresenter(this, this);
        InitCompo();
        Listener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SetPreData();
    }

    private void SetPreData() {
        if(appdata.isNetworkConnected(this)){
            vehicleDetailPresenter.GetVehicleInfo();
        }else {
            appdata.ShowNewAlert(this,"Please connect to internet");
        }
    }

    private void Listener() {
        btAddVechile.setOnClickListener(this);
    }
//    TextView tvVehicleModel,tvVehicleModelYear,tvMobileNumber,tvRegisterNumber;
    public void InitCompo()
    {
//        tvVehicleModel=findViewById(R.id.tv_model_name);
//        tvVehicleModelYear=findViewById(R.id.tv_model_year);
//        tvMobileNumber=findViewById(R.id.tv_mobile_number);
//        tvRegisterNumber=findViewById(R.id.tv_vehicle_regi_number);

        rvMyCampignAnalysis = findViewById(R.id.rv_my_vehicle_info);
        rvMyCampignAnalysis.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvMyCampignAnalysis.setHasFixedSize(true);

        btAddVechile=(Button)findViewById(R.id.bt_add_new_vehicle);
//        tvWalletAmount = findViewById(R.id.fetchamount);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
//            case R.id.imageback:
//                finish();
//                Animatoo.animateFade(this);
//                break;
            case R.id.bt_add_new_vehicle:
                Intent intent = new Intent(VehicalActivity.this, VehicalFromActivity.class);
                startActivity(intent);
                Animatoo.animateSlideRight(this);
                break;
//            case R.id.bt_submit:
//
//                break;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        Animatoo.animateFade(this);
    }
    ArrayList<VehicelInfoBean> arVehicelInfo=new ArrayList<>();
//vehicle information api
    @Override
    public void success(ArrayList<VehicelInfoBean> response, String otp) {
        arVehicelInfo.clear();
        arVehicelInfo=response;
        SetAdapter();
    }

    @Override
    public void error(String response) {

    }

    @Override
    public void fail(String response) {

    }

    public void SetAdapter()
    {
        mAdapter= new VehicleAdapter(this, arVehicelInfo, this);
        rvMyCampignAnalysis.setAdapter(mAdapter);
    }
//adapter click
    @Override
    public void onClick(int position, int diff) {

    }
}


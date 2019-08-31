package com.diss.cabadvertisementdriver.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.diss.cabadvertisementdriver.R;
import com.diss.cabadvertisementdriver.model.AddCarModelBean;
import com.diss.cabadvertisementdriver.model.CarModelBean;
import com.diss.cabadvertisementdriver.model.CountryBean;
import com.diss.cabadvertisementdriver.model.SignupBean;
import com.diss.cabadvertisementdriver.presenter.AddCarModelPresenter;
import com.diss.cabadvertisementdriver.presenter.SignupPresenter;

import java.util.ArrayList;

public class VehicalFromActivity extends AppCompatActivity implements View.OnClickListener , SignupPresenter.CarModel,AddCarModelPresenter.AddCarModel{
    Button btSubmit;
    //    private HelpAndRequestPresenter HelpAndReqPresenter;
    AppData appdata;
    private String sCarBrand,sCarRegiNo,sDriverAddress,sCarModel;
    private SignupPresenter carModelPresenter;
    private AddCarModelPresenter addCarModelPresenter;

    String[] getModel,getCountry,getState,getCity;
    int locPosCity=0;
    String sCityId="0";
    int locPosModel=0;
//    String sModelId="";
    String sModelNm="";
//    String sModelNo="";
    int locPosCountry=0;
    String sCountryId="0";
    int locPostate=0;
    String sStateId="0";
    //for car model,countrylist,statelist,citylist
    ArrayList<CarModelBean> arCarModel=new ArrayList<>();
    ArrayList<CountryBean> arCountry=new ArrayList<>();
    ArrayList<CountryBean> arState=new ArrayList<>();
    ArrayList<CountryBean> arCity=new ArrayList<>();
    TextView tvModelNo,tvCountry,tvState,tvCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehical_registraion_activity_new);
        appdata=new AppData(this);
        carModelPresenter = new SignupPresenter(this, this,"");
        addCarModelPresenter = new AddCarModelPresenter(this, this);

        InitCompo();
        Listener();
//        GetPreData(4);
        SetDirectModelData();
    }

    private void GetPreData(int diff_) {
        if (appdata.isNetworkConnected(this)) {
            switch (diff_)
            {
                case 1:
                    carModelPresenter.GetAllCountry();
                    break;
                case 2:
                    carModelPresenter.GetState(sCountryId);
                    break;
                case 3:
                    carModelPresenter.GetCity(sStateId);
                    break;
                case 4:
                    carModelPresenter.GetCarModel();
                    break;
            }

        } else {
            appdata.ShowNewAlert(this, "Please connect to internet");
        }
    }
    private void Listener() {
        btSubmit.setOnClickListener(this);
        tvCountry.setOnClickListener(this);
        tvState.setOnClickListener(this);
        tvCity.setOnClickListener(this);
        tvModelNo.setOnClickListener(this);
    }

    public void InitCompo()
    {

        etCarBrand = findViewById(R.id.et_car_brand);
        etCarRegiNo = findViewById(R.id.et_car_registration_no);
        etDriverAddress = findViewById(R.id.et_address);
        etCarModel = findViewById(R.id.et_car_model);

        tvModelNo = findViewById(R.id.tv_model);
        tvCountry = findViewById(R.id.tv_coutry);
        tvState = findViewById(R.id.tv_state);
        tvCity = findViewById(R.id.tv_city);
        btSubmit = findViewById(R.id.bt_submit);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
//            case R.id.imageback:
//                finish();
//                Animatoo.animateFade(this);
//                break;
            case R.id.bt_submit:
                addValidationToViews();
                break;
           case R.id.tv_model:
               Log.e("","getModel= "+getModel.toString());
            ShowAlertCarModelDlg();
            break;
            case R.id.tv_coutry:
                ShowAlertCoutryDlg();
                break;
            case R.id.tv_state:
                ShowAlertStateDlg();
                break;
            case R.id.tv_city:
                ShowAlertCityDlg();
                break;
        }
    }


    public  void ShowAlertCarModelDlg()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Select Model");
        mBuilder.setSingleChoiceItems(getModel, locPosModel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tvModelNo.setText(getModel[i]);
                locPosModel=i;
//                sModelId=getModel[i];
//                sModelId=arCarModel.get(i).getCar_model_id();
                sModelNm=arCarModel.get(i).getModel_name();
//                sModelNo=arCarModel.get(i).getModel_no();
                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }


    public  void ShowAlertCoutryDlg()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Select Country");
        mBuilder.setSingleChoiceItems(getCountry, locPosCountry, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tvCountry.setText(getCountry[i]);
                locPosCountry=i;
                sCountryId=arCountry.get(i).getCountry_id();
                GetPreData(2);//for state
                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    public  void ShowAlertStateDlg()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Select State");
        mBuilder.setSingleChoiceItems(getState, locPostate, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tvState.setText(getState[i]);
                locPostate=i;
//                sStateId=getState[i];
                sStateId=arState.get(i).getCountry_id();
                GetPreData(3);//for city
                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    public  void ShowAlertCityDlg()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Select City");
        mBuilder.setSingleChoiceItems(getCity, locPosCity, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tvCity.setText(getCity[i]);
                locPosCity=i;
                sCityId=arCity.get(i).getCountry_id();
//                sCityId=getState[i];
                dialogInterface.dismiss();
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }
    EditText etCarBrand,etCarRegiNo,etDriverAddress,etCarModel;
    public void addValidationToViews(){

        sCarBrand=etCarBrand.getText().toString().trim();
        sCarRegiNo=etCarRegiNo.getText().toString().trim();
        sDriverAddress=etDriverAddress.getText().toString().trim();
        sCarModel=etCarModel.getText().toString().trim();

        if (TextUtils.isEmpty(sCarBrand)) {
            etCarBrand.setError("Please Enter Car Brand");
            etCarBrand.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(sCarModel)) {
            etCarModel.setError("Please Enter Car Model Number");
            etCarModel.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(sCarRegiNo)) {
            etCarRegiNo.setError("Please Enter Car Registration Number");
            etCarRegiNo.requestFocus();
            return;
        }


        else if (TextUtils.isEmpty(sDriverAddress)) {
            etDriverAddress.setError("Please Enter Your Address");
            etDriverAddress.requestFocus();
            return;
        }//sCountryId,sModelId.equals("0")||
        else if (sCountryId.equals("0")||sStateId.equals("0")||sCityId.equals("0")) {
            Toast.makeText(this, "please select Correct Model Number, Counrty, State and City", Toast.LENGTH_SHORT).show();
            return;
        }
        else {

                if (appdata.isNetworkConnected(this)) {

//                    Toast.makeText(this, "sModelId= "+sModelId, Toast.LENGTH_SHORT).show();
                    AddCarModelBean bean=new AddCarModelBean();
                    bean.setCar_brand(sCarBrand);
                    bean.setModel_name(sModelNm);//model year
                    bean.setCar_registration_no(sCarRegiNo);
                    bean.setModel_no(sCarModel);//model name
                    bean.setDriver_add(sDriverAddress);
                    bean.setDriver_county(sCountryId);
                    bean.setDriver_state(sStateId);
                    bean.setDriver_city(sCityId);
                    Log.e("","sign bean= "+bean.toString());
                    addCarModelPresenter.AddDriverCar(bean);
                } else {
                    appdata.ShowNewAlert(this, "Please connect to internet");
                }
            }


    }

    public void SetDirectModelData()
    {
        getModel=null;
        arCarModel.clear();

        CarModelBean bean;
        for(int i=0;i<20;i++)
        {
            bean = new CarModelBean();
            bean.setCar_model_id((i+1)+"");
            if(i<10)
            {
                bean.setModel_name("200"+i);
                bean.setModel_no("200"+i);
            }
            else
            {
                bean.setModel_name("20"+i);
                bean.setModel_no("20"+i);
            }
            arCarModel.add(bean);
        }


        if(arCarModel.size()>0) {
            tvModelNo.setText(arCarModel.get(0).getModel_name());
            getModel=new String[arCarModel.size()];
//            sModelId=arCarModel.get(0).getCar_model_id();
            sModelNm=arCarModel.get(0).getModel_name();
            for(int i=0;i<arCarModel.size();i++) {
                getModel[i]=arCarModel.get(i).getModel_name();
            }
        }
        GetPreData(1);//for country
    }
    @Override
    public void success(ArrayList<CarModelBean> modelList, ArrayList<CountryBean> countryList, String diff) {
        switch (diff)
        {
            case "1"://for car model
//                getModel=null;
//                arCarModel.clear();
//                arCarModel=modelList;
//                if(arCarModel.size()>0) {
//                    tvModelNo.setText(arCarModel.get(0).getModel_name());
//                    getModel=new String[arCarModel.size()];
//                    sModelId=arCarModel.get(0).getCar_model_id();
//                    sModelNm=arCarModel.get(0).getModel_name();
//                    sModelNo=arCarModel.get(0).getModel_no();
//
//                    for(int i=0;i<arCarModel.size();i++) {
//                        getModel[i]=arCarModel.get(i).getModel_name();
//                    }
//                }
//                GetPreData(1);//for country
                break;
            case "2"://for country
                getCountry=null;
                arCountry.clear();
                arCountry=countryList;

                if(arCountry.size()>0) {
                    tvCountry.setText(arCountry.get(0).getCountry_name());
                    getCountry=new String[arCountry.size()];
                    sCountryId=arCountry.get(0).getCountry_id();
                    for(int i=0;i<arCountry.size();i++) {
                        getCountry[i]=arCountry.get(i).getCountry_name();
                    }
                }
                GetPreData(2);//for state
                break;
            case "3"://for state
                getState=null;
                arState.clear();
                arState=countryList;

                if(arState.size()>0) {
                    tvState.setText(arState.get(0).getCountry_name());
                    getState=new String[arState.size()];
                    sStateId=arState.get(0).getCountry_id();
                    for(int i=0;i<arState.size();i++) {
                        getState[i]=arState.get(i).getCountry_name();
                    }
                }
                GetPreData(3);
                break;
            case "4"://for city
                getCity=null;
                arCity.clear();
                arCity=countryList;

                if(arCity.size()>0) {
                    tvCity.setText(arCity.get(0).getCountry_name());
                    getCity=new String[arCity.size()];
                    sCityId=arCity.get(0).getCountry_id();
                    for(int i=0;i<arCity.size();i++) {
                        getCity[i]=arCity.get(i).getCountry_name();
                    }
                }
                break;
        }
    }

    //for car model,countrylist,statelist,citylist
    @Override
    public void error(String response,String diff) {
        switch (diff)
        {
            case "1"://for car model
                getModel=null;
                arCarModel.clear();
//                sModelId="0";
                sModelNm="0";
//                sModelNo="0";
                tvModelNo.setText("Select Model");
                GetPreData(1);//for country
                break;
            case "2"://for country
                getCountry=null;
                arCountry.clear();
                sCountryId="0";
                tvCountry.setText("Select Country");
                GetPreData(2);//for state
                break;
            case "3"://for state
                getState=null;
                arState.clear();
                sStateId="0";
                tvState.setText("Select State");

                GetPreData(3);
                break;
            case "4"://for city
                getCity=null;
                arCity.clear();
                sCityId="0";
                tvCity.setText("Select city");
                break;
        }
    }

    @Override
    public void fail(String response,String diff) {
        appdata.ShowNewAlert(this,response);
    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        Animatoo.animateFade(this);
    }
//for add car model api
    @Override
    public void success(String response, String status) {
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    public void error(String response) {
        appdata.ShowNewAlert(this,response);
    }

    @Override
    public void fail(String response) {
        appdata.ShowNewAlert(this,response);
    }
}

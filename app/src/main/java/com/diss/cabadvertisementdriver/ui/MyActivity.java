package com.diss.cabadvertisementdriver.ui;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.diss.cabadvertisementdriver.BroadcastService;
import com.diss.cabadvertisementdriver.R;
import com.diss.cabadvertisementdriver.model.DriverCampaignBean;
import com.diss.cabadvertisementdriver.model.LocDat;
import com.diss.cabadvertisementdriver.presenter.CampaignDetailsPresenter;
import com.diss.cabadvertisementdriver.presenter.DriverCampaignPresenter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.johnnylambada.location.LocationObserver;
import com.johnnylambada.location.LocationProvider;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MyActivity extends AppCompatActivity implements View.OnClickListener, LocationObserver, Runnable, LocationListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,DriverCampaignPresenter.CampaingListData, CampaignDetailsPresenter.CampaignInfo {

    ImageView imageViewback;
    AppData appdata;
    double lati = 0;//22.71246
    double longi = 0;//75.86491
    SupportMapFragment mapFragment;
    protected GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    private GoogleMap mMapSession;
    Marker mCurrLocationMarker;
    //    String diff;
    private DatabaseReference mDatabase;
    private DriverCampaignPresenter presenter;
    private CampaignDetailsPresenter reqAcceptRejePresenter;

    int miliSecond = 30000;//10000
    int timerCountdown = 1000;//1000

    boolean checkDistanceFlag = false;
    TextView tvMainTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        appdata = new AppData(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        presenter = new DriverCampaignPresenter(this, this);
        reqAcceptRejePresenter = new CampaignDetailsPresenter(this, this);
        InitCompo();
        Listener();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        registerReceiver(br, new IntentFilter(BroadcastService.COUNTDOWN_BR));
//        Log.i(TAG, "Registered broacast receiver");
        GetIntentData();
    }

    String s_id = "", status_accept_req = "0";

    private void GetIntentData() {
        s_id = appdata.getS_id();
        status_accept_req = appdata.getAccepted_request_status();

        if (s_id.equals("0") || TextUtils.isEmpty(s_id)) {
            lyAccept.setVisibility(View.GONE);
//            tvGoLine.setVisibility(View.GONE);
        } else {
            //yeah show hoga tab tak ki campaign complete nhi ho gata
//            tvGoLine.setVisibility(View.VISIBLE);
//            rlCampInfo.setVisibility(View.VISIBLE);

            if(appdata.getDfffS_id().equals("0")&&(!s_id.equals("0")||!TextUtils.isEmpty(s_id))) {
                //show only first time
                lyAccept.setVisibility(View.VISIBLE);
            }
            CallApi(3);
        }

        if (status_accept_req.equals("0") || TextUtils.isEmpty(status_accept_req)) {
            rlCampInfo.setVisibility(View.GONE);
            tvGoLine.setVisibility(View.GONE);
        } else {
            //yeah show hoga tab tak ki campaign complete nhi ho gata

            rlCampInfo.setVisibility(View.VISIBLE);
            tvGoLine.setVisibility(View.VISIBLE);
        }
        InitTimer();
        setCurLoc();
        SetOnlineText();


//        diff=getIntent().getStringExtra("diff_");
//        switch (s_id)
//        {
//            case "0"://from dashboard
////                lyAccept.setVisibility(View.GONE);
//                break;
//            case "2"://from notification
//                lyAccept.setVisibility(View.VISIBLE);
//                break;
//        }


    }

    public void SetOnlineText() {
        String logStatus = appdata.getIsOnline();
        if (logStatus.equals("0") || TextUtils.isEmpty(logStatus)) {
            tvGoLine.setText("Go Live");//for go online
            tvOnOffIndicator.setBackgroundResource(R.drawable.circle_red);
            cancelTimer();
        } else {
            StartTimer();
            tvGoLine.setText("Go Offline");//for go offline
            tvOnOffIndicator.setBackgroundResource(R.drawable.circle_green);
            checkDistanceFlag = true;
        }
    }

    private void CallApi(int pos) {
        if (appdata.isNetworkConnected(this)) {
            switch (pos) {
                case 1://accept request
                case 2://reject request
                    Log.e("", "s_id= " + s_id + " pos=" + pos);
                    reqAcceptRejePresenter.AcceptRejectCampaignRequest(s_id, pos);
                    break;
                case 3://get campign detail
                    presenter.GetRequestCampaignDetail(s_id);
                    break;
               case 4://update driver cover kilomer
                   String ObjectData = appdata.getAccepted_request_jsonBean();
                   Gson gson = new Gson();
                   if (!TextUtils.isEmpty(ObjectData)) {
                       DriverCampaignBean requstBean = gson.fromJson(ObjectData, DriverCampaignBean.class);
                    String currentdate=appdata.GetCurrentDateTime();
                    Log.e("","currentdate= "+currentdate+" driverKm= "+driverKm+" s_id= "+requstBean.getCompaignId());
//                     String calCurrentHours

                       reqAcceptRejePresenter.UpdateDriverDistance(totalHours,driverKm,requstBean.getCompaignId());
                   }
                    break;
            }

        } else {
            appdata.ShowNewAlert(this, "Please connect to internet");
        }
    }

    public String CalculatOnlineHours()
    {

        String currentDateTime=appdata.GetCurrentDateTime();//yyyy-MM-dd hh:mm:ss

        String onlinetime=appdata.getOnlineTime();//yyyy-MM-dd hh:mm:ss

        String format = "yyyy-MM-dd hh:mm";//GetCurrentDateTime,MM/dd/yyyy

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");
        int diffhours=0,diffmin=0;
        String calculatTime="";
        try {
            Date dateObj1 = sdf.parse(onlinetime);//old
            Date dateObj2 = sdf.parse(currentDateTime);//current

            long diff = dateObj2.getTime() - dateObj1.getTime();
             diffhours = (int) (diff / (60 * 60 * 1000));
             diffmin = (int) (diff / (60 * 1000));

   System.out.println("difference between hours: " + crunchifyFormatter.format(diffhours)+" Minutes= "+ crunchifyFormatter.format(diffmin));
   Log.e("","difference between hours: " + crunchifyFormatter.format(diffhours)+" Minutes= "+ crunchifyFormatter.format(diffmin));


        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(diffhours>0)
        {
            calculatTime=String.valueOf(diffhours);
        }
        else
        {
            calculatTime="0."+diffmin;
        }

        return calculatTime;

    }

    private void Listener() {
        imageViewback.setOnClickListener(this);
        tvGoLine.setOnClickListener(this);
        tvAccept.setOnClickListener(this);
        tvReject.setOnClickListener(this);
        rlCampInfo.setOnClickListener(this);
        tvMainTitle.setOnClickListener(this);

    }

    LinearLayout lyAccept;
    RelativeLayout rlCampInfo, rlMain;
    TextView tvGoLine, tvCampaignNm, tvCampaignAmount, tvCampaignLoc, tvCampaignTime, tvAccept, tvReject, tvOnOffIndicator;

    public void InitCompo() {

        imageViewback = findViewById(R.id.imageback);
        tvGoLine = findViewById(R.id.tv_go_online);
        tvCampaignNm = findViewById(R.id.tv_compaign_name);
        tvCampaignAmount = findViewById(R.id.tv_compaign_amount);
        tvCampaignLoc = findViewById(R.id.tv_compaign_loc);
        tvCampaignTime = findViewById(R.id.tv_compaign_time);
        tvAccept = findViewById(R.id.tv_yes);
        tvReject = findViewById(R.id.tv_no);
        tvOnOffIndicator = findViewById(R.id.tv_online);
        lyAccept = findViewById(R.id.ly_accept);
        rlCampInfo = findViewById(R.id.rl_camp_info);
        rlMain = findViewById(R.id.rl_main);
        tvMainTitle = findViewById(R.id.logo);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        buildGoogleApiClient();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageback:
                onBackPressed();
//                finish();
//                Animatoo.animateSlideRight(this);
                break;
            case R.id.tv_yes://tvAccept
                CallApi(1);
                break;
            case R.id.tv_no://tvReject
                CallApi(2);
                break;
            case R.id.rl_camp_info://tvReject
                showCampInfoDlg();
                break;

            case R.id.logo://calculate time
               String time=  CalculatOnlineHours();
                break;

            case R.id.tv_go_online://go live
                String logStatus = appdata.getIsOnline();
                if (logStatus.equals("0") || TextUtils.isEmpty(logStatus)) {
                    appdata.setIsOnline("1");

//                           String currentTime= appdata.GetCurrentDateTime();
//                           appdata.setOnlineTime(currentTime);

//                    startService(new Intent(this, BroadcastService.class));
                } else {
                    appdata.setIsOnline("0");
//                    appdata.setOnlineTime("");
//                    stopService(new Intent(this, BroadcastService.class));
                }

                String currentTime= appdata.GetCurrentDateTime();
                appdata.setOnlineTime(currentTime);

                SetOnlineText();
                break;

        }
    }


    Dialog campInfoDlg;

    public void showCampInfoDlg() {
        if (campInfoDlg != null) {
            campInfoDlg = null;
        }
        campInfoDlg = new Dialog(this);
        campInfoDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        campInfoDlg.setCancelable(false);
        campInfoDlg.setContentView(R.layout.dialog_campaign_infomation);
        RelativeLayout rlClose = campInfoDlg.findViewById(R.id.rl_close);
        TextView tvCampNm = campInfoDlg.findViewById(R.id.tv_campaign_name);
        TextView tvCamLocation = campInfoDlg.findViewById(R.id.tv_campaign_loc);
        TextView tvCamTime = campInfoDlg.findViewById(R.id.tv_campaign_time);
        TextView tvCamAmnt = campInfoDlg.findViewById(R.id.tv_campaign_amount);
        TextView tvCamDate = campInfoDlg.findViewById(R.id.tv_campaign_date);

        String ObjectData = appdata.getAccepted_request_jsonBean();
        Gson gson = new Gson();
        if (!TextUtils.isEmpty(ObjectData)) {

            DriverCampaignBean requstBean = gson.fromJson(ObjectData, DriverCampaignBean.class);

            tvCampNm.setText(requstBean.getsCampaignNm());
            tvCamLocation.setText(requstBean.getC_l_name());
            tvCamTime.setText(requstBean.getPlan_month() + " Days");
            tvCamAmnt.setText("Rs. " + requstBean.getDriverHireAmount());
//            tvCamDate.setText(arDriverCampaign.get(0).getDriverHireAmount());
        }

        rlClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campInfoDlg.dismiss();
            }
        });


        campInfoDlg.show();
    }
    //for location

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            lati = mLastLocation.getLatitude();
            longi = mLastLocation.getLongitude();
        }
        myLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMapSession = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMapSession.setMyLocationEnabled(true);
//        myLocation();


    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    public void myLocation()//only for show current position
    {
        LatLng latLng = new LatLng(lati, longi);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMapSession.addMarker(markerOptions);
        //move map camera
        mMapSession.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

//        mMapSession.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16f));
//        mMapSession.getUiSettings().setZoomControlsEnabled(true);

    }
    //for location

    //for go live
    public void setCurLoc() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkLocationPermission()) {
                LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, 101);
                } else {
                    initLocation();
                }
            } else {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            }
        } else {
            LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 101);
            } else {
                initLocation();
            }
        }
    }

    private LocationProvider locationProvider;

    private void initLocation() {
        locationProvider = new LocationProvider.Builder(this)
                .locationObserver(this)
                .intervalMs(miliSecond)//300000
                .onPermissionDeniedFirstTime(this)
                .onPermissionDeniedAgain(this)
                .onPermissionDeniedForever(this)
                .build();
        locationProvider.startTrackingLocation();
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    LatLng firstLatLng;
    LatLng currentLatLng;
String driverKm="",totalHours="1";
    //for  LocationProvider.Builder
    @Override
    public void onLocation(Location location) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getSubLocality();
            String city = addresses.get(0).getLocality();

            Log.e("", "run onLocation lati= " + location.getLatitude() + " longi= " + location.getLongitude());
//            mMapSession.
            if (mCurrLocationMarker != null) {
                mCurrLocationMarker.remove();
                mCurrLocationMarker = null;
            }
            mMapSession.clear();

            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            mCurrLocationMarker = mMapSession.addMarker(markerOptions);

            lati = location.getLatitude();
            longi = location.getLongitude();

            if (checkDistanceFlag)//first time online
            {

                firstLatLng = new LatLng(lati, longi);
                checkDistanceFlag = false;
            }
            currentLatLng = new LatLng(lati, longi);


//          double distantce = CalculationByDistance(firstLatLng, currentLatLng);
//            double distantce = CalculationByDistance11(firstLatLng, currentLatLng);
//            if (distantce > 3) {
//                driverKm=String.valueOf(distantce);
//                totalHours=CalculatOnlineHours();
//                CallApi(4);
//            }

//        if (isAppRunning(this, "com.diss.cabadvertisementdriver")) {
//            // App is running,app is in background
////             intent = new Intent( this , NotificationActivity. class );
//            Toast.makeText(this, "App is running", Toast.LENGTH_SHORT).show();
//        } else {
//            // App is not running
//            Toast.makeText(this, "App is not running", Toast.LENGTH_SHORT).show();
//        }


        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Log.e("", "run");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationProvider.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //////////////////////////
    public void SetViewData() {
        if (arDriverCampaign.size() > 0) {
            tvCampaignNm.setText(arDriverCampaign.get(0).getsCampaignNm());
            tvCampaignLoc.setText(arDriverCampaign.get(0).getC_l_name());
            tvCampaignTime.setText(arDriverCampaign.get(0).getPlan_month() + " Days");
            tvCampaignAmount.setText("Rs. " + arDriverCampaign.get(0).getDriverHireAmount());
//            tvCamDate.setText(arDriverCampaign.get(0).getDriverHireAmount());
        }
    }

    //    for campaign detail
    ArrayList<DriverCampaignBean> arDriverCampaign = new ArrayList<>();

    @Override
    public void success(ArrayList<DriverCampaignBean> response, String status) {
        arDriverCampaign.clear();
        arDriverCampaign = response;
        SetViewData();
        rlCampInfo.setVisibility(View.VISIBLE);

    }

    //accept /reject
    @Override
    public void success(String response, int status) {

        if(status==3)//update driver km
        {
            Toast.makeText(this, "Km Update", Toast.LENGTH_SHORT).show();
            checkDistanceFlag=true;

            String currentTime= appdata.GetCurrentDateTime();
            appdata.setOnlineTime(currentTime);
        }
        else
            {
            switch (status) {
                case 1://accept
                    LocDat locDat = new LocDat(appdata.getUserID(), lati, longi);
                    mDatabase.child("S_ID-" + appdata.getS_id()).child(appdata.getUserID()).setValue(locDat);
                    appdata.sets_Id("");
                    appdata.setAccepted_request_status("1");
                    rlCampInfo.setVisibility(View.VISIBLE);
                    lyAccept.setVisibility(View.GONE);
                    tvGoLine.setVisibility(View.VISIBLE);
                    break;
                case 2://reject
                    appdata.sets_Id("");
                    appdata.setAccepted_request_status("0");
                    rlCampInfo.setVisibility(View.GONE);
                    lyAccept.setVisibility(View.GONE);
                    tvGoLine.setVisibility(View.GONE);
                    break;
            }
            appdata.ShowNewAlert(this, response);
        }
    }

    @Override
    public void error(String response) {

        appdata.ShowNewAlert(this, response);
    }

    @Override
    public void fail(String response) {
        appdata.ShowNewAlert(this, response);
    }

    CountDownTimer cTimer = null;

    //start timer function
    void InitTimer() {
        cTimer = new CountDownTimer(miliSecond, timerCountdown) {
            public void onTick(long millisUntilFinished) {
//                Log.e("","remaining time= "+(millisUntilFinished / 1000));
                long sec = (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                Log.e("", "remaining time=: " + sec);


            }

            public void onFinish() {
                SetDriverLocationData();


//                if (checkDistanceFlag)//first time online
//                {
//                    firstLatLng = new LatLng(lati, longi);
//                    checkDistanceFlag = false;
//                }
//                currentLatLng = new LatLng(lati, longi);
////            double distantce = CalculationByDistance(firstLatLng, currentLatLng);
//                double distantce = CalculationByDistance11(firstLatLng, currentLatLng);
////                        location.getAccuracy();
////            double distantce=5.0;
//                if (distantce > 3) {
////                Toast.makeText(this, "greater distance " + distantce, Toast.LENGTH_SHORT).show();
//                    driverKm=String.valueOf(distantce);
//                    CallApi(4);
//                } else {
////                Toast.makeText(this, "less distance " + distantce, Toast.LENGTH_SHORT).show();
//                }


            }
        };
//        StartTimer();
    }

    private void SetDriverLocationData() {
        try {
            String ObjectData = appdata.getAccepted_request_jsonBean();//this object for future use
            Gson gson = new Gson();
            Log.e("", "ObjectData= " + ObjectData);
            if (!TextUtils.isEmpty(ObjectData)) {
                DriverCampaignBean requstBean = gson.fromJson(ObjectData, DriverCampaignBean.class);
                LocDat locDat = new LocDat(appdata.getUserID(), lati, longi);
                mDatabase.child("S_ID-" + requstBean.getCompaignId()/*appdata.getS_id()*/).child(appdata.getUserID()).setValue(locDat);
            }


//          double distantce = CalculationByDistance(firstLatLng, currentLatLng);

            double distantce = CalculationByDistance11(firstLatLng, currentLatLng);
            if (distantce > 1) {
                driverKm=String.valueOf(distantce);
                totalHours=CalculatOnlineHours();
                Toast.makeText(this, "driverKm= "+driverKm+" totalHours= "+totalHours, Toast.LENGTH_SHORT).show();
                CallApi(4);
            }

            StartTimer();

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    public void StartTimer() {
        if (cTimer != null)
            cTimer.start();
    }

    //cancel timer
    void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
    }

    //calculate distance
    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = (valueResult % 1000);
        double kmMeter = (valueResult % 1000) / 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return kmMeter;
//    return Radius * c;
    }

    public double CalculationByDistance11(LatLng StartP, LatLng EndP) {
    double distance;
    Location locationA = new Location("");
locationA.setLatitude(StartP.latitude);
locationA.setLongitude(StartP.longitude);
    Location locationB = new Location("");
locationB.setLatitude(EndP.latitude);
locationB.setLongitude(EndP.longitude);
    distance =locationA.distanceTo(locationB)/1000;//for km  /1000
//kmeter.setText(String.valueOf(distance));
//Toast.makeText(getApplicationContext(), ""+distance,Toast.LENGTH_LONG).show();
        distance=Math.round(distance);
return distance;
}

    public  boolean isAppRunning(final Context context, final String packageName) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        if (procInfos != null)
        {
            for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                if (processInfo.processName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
            appdata.setNotiClick("0");
            appdata.setNotiType("0");
        Intent intent = new Intent(this, DashBoardActivity.class);
        startActivity(intent);
        finish();
        Animatoo.animateSlideUp(this);
    }


    /*private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateGUI(intent); // or whatever method used to update your GUI fields
        }
    };
String TAG="shyam activity";
    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            long millisUntilFinished = intent.getLongExtra("countdown", 0);
            Log.i(TAG, "Countdown seconds remaining: " +  millisUntilFinished / 1000);
        }
    }


 *//*   @Override
    public void onResume() {
        super.onResume();

    }*//*

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(br);
        Log.i(TAG, "Unregistered broacast receiver");
    }

    @Override
    public void onStop() {
        try {
            unregisterReceiver(br);
        } catch (Exception e) {
            // Receiver was probably already stopped in onPause()
        }
        super.onStop();
    }
    @Override
    public void onDestroy() {
        stopService(new Intent(this, BroadcastService.class));
        Log.i(TAG, "Stopped service");
        super.onDestroy();
    }*/
}

package com.diss.cabadvertisementdriver.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.diss.cabadvertisementdriver.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashBoardActivity extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    AppData appdata;
    DrawerLayout drawer;
    CircleImageView ivUsrPic;
    TextView tvUsrNm,tvUsrPhoneNo;

    double lati = 0;//22.71246
    double longi = 0;//75.86491
    SupportMapFragment mapFragment;
    protected GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    private GoogleMap mMapSession;
    Marker mCurrLocationMarker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = findViewById(R.id.toolbar);
      /*  setSupportActionBar(toolbar);
*/
        appdata=new AppData(DashBoardActivity.this);
         drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        View hView =  navigationView.getHeaderView(0);
        tvUsrNm = hView.findViewById(R.id.navname);
        tvUsrPhoneNo = hView.findViewById(R.id.navnumber);
        ivUsrPic=hView.findViewById(R.id.iv_user_Pic);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        buildGoogleApiClient();
        GetRegistration();
    }
    @Override
    protected void onResume() {
        super.onResume();
        setProrileData();
    }
    public void setProrileData()
    {
        if(!appdata.getProfilePic().equals("NA")) {
            Glide.with(DashBoardActivity.this)
                    .load(appdata.getProfilePic())
                    .placeholder(R.drawable.ic_user)
                    .into(ivUsrPic);
        }
        tvUsrNm.setText(appdata.getUsername());
        tvUsrPhoneNo.setText(appdata.getMobile());
    }
    @Override
    public void onBackPressed() {
        LogOutDlg(2,"Are you sure you want to exit?");
        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/

    }
    public void LogOutDlg(final int diff,String msg)
    {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        new AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       switch (diff)
                       {
                           case 1:
                               appdata.setUserID("NA");
                               appdata.setUserStatus("NA");
                               appdata.clear();
                               startActivity(new Intent(DashBoardActivity.this, LoginActivity.class));
                               finish();
                               Animatoo.animateSlideLeft(DashBoardActivity.this);
                               break;
                          case 2:
                              finishAffinity();
                               break;
                       }

//                                finishAffinity();
                        Animatoo.animateSlideDown(DashBoardActivity.this);
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_profile) {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            Animatoo.animateSlideLeft(DashBoardActivity.this);
        } else if (id == R.id.nav_wallet) {

            startActivity(new Intent(getApplicationContext(), Wallet_activity.class));
            Animatoo.animateSlideLeft(DashBoardActivity.this);


        } else if (id == R.id.nav_activity) {
            startActivity(new Intent(getApplicationContext(), MyActivity.class).putExtra("diff_","1"));
            Animatoo.animateSlideLeft(DashBoardActivity.this);


        } else if (id == R.id.nav_information) {

            startActivity(new Intent(getApplicationContext(), VehicalActivity.class));
            Animatoo.animateSlideLeft(DashBoardActivity.this);

        } else if (id == R.id.nav_compaign) {

            startActivity(new Intent(getApplicationContext(), MyCompaignDetailActivity.class));
            Animatoo.animateSlideLeft(DashBoardActivity.this);

        } else if (id == R.id.nav_analysis) {

//            startActivity(new Intent(getApplicationContext(), WeeklyAnalysisReportActivity.class));
            startActivity(new Intent(getApplicationContext(), ActWeeklyAnalysis.class));
            Animatoo.animateSlideLeft(DashBoardActivity.this);

        }else if (id == R.id.nav_help) {

            startActivity(new Intent(getApplicationContext(), HelpActivity.class));
            Animatoo.animateSlideLeft(DashBoardActivity.this);

        }else if (id == R.id.nav_notification) {

            startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
            Animatoo.animateSlideLeft(DashBoardActivity.this);

        }else if (id == R.id.nav_shareinvite) {

            startActivity(new Intent(getApplicationContext(), ShareActivity.class));
            Animatoo.animateSlideLeft(DashBoardActivity.this);

        }else if (id == R.id.nav_ratereview) {

            startActivity(new Intent(getApplicationContext(), RateActivity.class));
            Animatoo.animateSlideLeft(DashBoardActivity.this);

        }else if (id == R.id.nav_logout) {
            LogOutDlg(1,"Are you sure you want to logout?");
          /*  if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            appdata.setUserID("NA");
                            appdata.setUserStatus("NA");
                            appdata.clear();
                            startActivity(new Intent(DashBoardActivity.this, LoginActivity.class));
                            finish();
//                                finishAffinity();
                            Animatoo.animateSlideDown(DashBoardActivity.this);
                        }
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();*/
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

//        if(lati!=0.0||logiti!=0.0)
//        {
//            add= supportUtils.getCompleteAddressString(ActBookSession.this,lati,logiti);
//            Log.e("","0000add= "+add);
////            mLocationAddress.setText(add);
////            ShowLine();
//        }
//        Toast.makeText(this, "currnet loat= "+lati+" logiti= "+logiti, Toast.LENGTH_SHORT).show();

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

    public void myLocation()
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

    public void GetRegistration()
    {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

// Get the Instance ID token//
                        String token = task.getResult().getToken();
//                        String msg = getString(R.string.fcm_token, token);
                        Log.d("", "shyam fcm token= "+token);

                    }
                });

    }

}

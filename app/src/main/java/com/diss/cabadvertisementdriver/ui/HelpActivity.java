package com.diss.cabadvertisementdriver.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.diss.cabadvertisementdriver.R;
import com.diss.cabadvertisementdriver.presenter.HelpAndRequestPresenter;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener, HelpAndRequestPresenter.HelpAndRequest{

//    TextView  clicktext;
    LinearLayout clicllinear;
    int count=0;
    ImageView imageViewback;
    private HelpAndRequestPresenter HelpAndReqPresenter;
    AppData appdata;
    LinearLayout lyClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_activity);
        appdata=new AppData(this);
        HelpAndReqPresenter = new HelpAndRequestPresenter(this, this);
        InitCompo();
        Listener();
    }
    private void Listener() {
        imageViewback.setOnClickListener(this);
        btSubmit.setOnClickListener(this);
        lyClick.setOnClickListener(this);

    }
//    ImageView ivBack;
    EditText etName,etEmail,etDescription;
    Button btSubmit;
    public void InitCompo()
    {
        imageViewback=findViewById(R.id.imageback);
//        clicktext=findViewById(R.id.click_id);
        lyClick=findViewById(R.id.ly_click);
        clicllinear=(LinearLayout)findViewById(R.id.clickeventlinear);


        etName=findViewById(R.id.et_name);
        etEmail=findViewById(R.id.et_email);
        etDescription=findViewById(R.id.et_dsc);
        btSubmit=findViewById(R.id.bt_submit);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imageback:
                finish();
                Animatoo.animateFade(this);
                break;
            case R.id.ly_click:
                if (count==0){
                    clicllinear.setVisibility(View.GONE);
                    count=1;

                }else{
                    clicllinear.setVisibility(View.VISIBLE);
                    count=0;
                }
                break;
            case R.id.bt_submit:
                addValidationToViews();
                break;

        }
    }
    String sName,sEmail,sDsc;
    public void addValidationToViews(){
        sName=etName.getText().toString().trim();
        sEmail=etEmail.getText().toString().trim();
        sDsc=etDescription.getText().toString().trim();


        if (TextUtils.isEmpty(sName)) {
            etName.setError("Please Enter Name");
            etName.requestFocus();
            return;
        }
        else   if (TextUtils.isEmpty(sEmail)) {
            etEmail.setError("Please Enter Your Email");
            etEmail.requestFocus();
            return;
        }
        else  if (!appdata.isEmailValid(sEmail)) {
            etEmail.setError("Enter a Valid Email");
            etEmail.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(sDsc)) {
            etDescription.setError("Please Enter Description");
            etDescription.requestFocus();
            return;
        }
        else {

            if(appdata.isNetworkConnected(this)){
                HelpAndReqPresenter.HelpAndRequestMethod(sName,sEmail,sDsc);
            }else {
                appdata.ShowNewAlert(this,"Please connect to internet");
            }
        }
    }
    //help and request api
    @Override
    public void success(String response, String otp) {
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        finish();
        Animatoo.animateFade(this);
    }

    @Override
    public void error(String response) {

    }

    @Override
    public void fail(String response) {

    }
}

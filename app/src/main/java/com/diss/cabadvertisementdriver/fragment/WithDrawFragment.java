package com.diss.cabadvertisementdriver.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.diss.cabadvertisementdriver.R;
import com.diss.cabadvertisementdriver.presenter.WalletAmountPresenter;
import com.diss.cabadvertisementdriver.ui.AppData;
import com.diss.cabadvertisementdriver.ui.Wallet_activity;

public class WithDrawFragment extends Fragment implements View.OnClickListener, WalletAmountPresenter.WalletInfo {
    View view;
    AppData appdata;
    private WalletAmountPresenter walletInfoPresenter;
    public WithDrawFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_withdraw_wallet_amount, container, false);
        appdata=new AppData(getActivity());
        walletInfoPresenter = new WalletAmountPresenter(getActivity(), WithDrawFragment.this);
        InitCompo();
        Listener();
        return  view;
    }
    private void Listener() {
        btProceed.setOnClickListener(this);

    }
    EditText etName,etACNumber,etIfscCode,etAmount,etDsc;
    Button btProceed;
    public void InitCompo()
    {
        etName=view.findViewById(R.id.et_name);
        etACNumber=view.findViewById(R.id.et_ac_number);
        etIfscCode=view.findViewById(R.id.et_ifsc_code);
        etAmount=view.findViewById(R.id.et_amount);
        etDsc=view.findViewById(R.id.et_dsc);
        btProceed=view.findViewById(R.id.bt_proceed);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bt_proceed:
                addValidationToViews();
                break;

        }
    }
    String sName,sACNumber,sIfscCode,sAmount,sDsc;
    public void addValidationToViews(){
        sName=etName.getText().toString().trim();
        sACNumber=etACNumber.getText().toString().trim();
        sIfscCode=etIfscCode.getText().toString().trim();
        sAmount=etAmount.getText().toString().trim();
        sDsc=etDsc.getText().toString().trim();


        if (TextUtils.isEmpty(sName)) {
            etName.setError("Please Enter Your Name");
            etName.requestFocus();
            return;
        }
        else   if (TextUtils.isEmpty(sACNumber)) {
            etACNumber.setError("Please Enter Your Account Number");
            etACNumber.requestFocus();
            return;
        }

        else if (TextUtils.isEmpty(sIfscCode)) {
            etIfscCode.setError("Please Enter IFSC Code");
            etIfscCode.requestFocus();
            return;
        }
        else   if (TextUtils.isEmpty(sAmount)) {
            etAmount.setError("Please Enter Amount");
            etAmount.requestFocus();
            return;
        }

        else if (TextUtils.isEmpty(sDsc)) {
            etDsc.setError("Please Enter Description");
            etDsc.requestFocus();
            return;
        }
        else {

            if(appdata.isNetworkConnected(getActivity())){
                 walletInfoPresenter.WithdrawWalletAmount(sName,sACNumber,sIfscCode,sDsc,sAmount);
            }else {
                appdata.ShowNewAlert(getActivity(),"Please connect to internet");
            }
        }
    }

    @Override
    public void success(String response, String amount) {

    }

    @Override
    public void error(String response) {
        appdata.ShowNewAlert(getActivity(),response);
    }

    @Override
    public void fail(String response) {
        appdata.ShowNewAlert(getActivity(),response);
    }
}


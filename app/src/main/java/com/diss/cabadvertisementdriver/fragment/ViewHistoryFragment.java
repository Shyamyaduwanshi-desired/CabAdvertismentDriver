package com.diss.cabadvertisementdriver.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.diss.cabadvertisementdriver.R;
import com.diss.cabadvertisementdriver.adapter.WalletWithdrawHistoryAdapter;
import com.diss.cabadvertisementdriver.model.WithdrawAmountBean;
import com.diss.cabadvertisementdriver.model.WithdrawhistoryBean;
import com.diss.cabadvertisementdriver.presenter.HelpAndRequestPresenter;
import com.diss.cabadvertisementdriver.presenter.WalletAmountPresenter;
import com.diss.cabadvertisementdriver.ui.AppData;

import java.util.ArrayList;

public class ViewHistoryFragment extends Fragment implements View.OnClickListener,WalletWithdrawHistoryAdapter.WalletDetailClick, WalletAmountPresenter.WalletHistroy{
    View view;
    AppData appdata;
    TextView tvNoData;
    private RecyclerView cvWalletWithdraw;
    private RecyclerView.Adapter mAdapter;
   Context context;
    private WalletAmountPresenter walletInfoPresenter;
    public ViewHistoryFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_view_wallet_history, container, false);
        appdata=new AppData(getActivity());
        context=getActivity();
        walletInfoPresenter = new WalletAmountPresenter(getActivity(), ViewHistoryFragment.this,"");
        InitCompo();
        Listener();
//        DummyData();
        GetHistoryData();
        return  view;
    }

    private void GetHistoryData() {
        if(appdata.isNetworkConnected(getActivity())){
            walletInfoPresenter.GetWalletHistroy();
        }else {
            appdata.ShowNewAlert(getActivity(),"Please connect to internet");
        }
    }

    private void Listener() {
//        imageViewback.setOnClickListener(this);
//        btSubmit.setOnClickListener(this);
//        clicktext.setOnClickListener(this);

    }

    public void InitCompo()
    {
        tvNoData=view.findViewById(R.id.tv_no_data);
        cvWalletWithdraw = view.findViewById(R.id.rv_withdraw_history);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        cvWalletWithdraw.setLayoutManager(mLayoutManager);
        cvWalletWithdraw.setItemAnimator(new DefaultItemAnimator());

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            /*case R.id.imageback:
                finish();
                Animatoo.animateFade(this);
                break;
            case R.id.click_id:
                countBACK=1;
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
                break;*/

        }
    }


//    public void DummyData()
//    {
//        WithdrawAmountBean bean;
//         for(int i=0;i<3;i++)
//         {
//             bean=new WithdrawAmountBean();
//             bean.setsWithdrawId((i+1)+"");
//             switch (i)
//             {
//                 case 0:
//                     bean.setsName("Johan");
//                     bean.setsAmount("$100.00");
//                     bean.setsTransId("DHAR5421");
//                     bean.setsDate("2019-06-18 11:17:55");
//
//                     break;
//                 case 1:
//                     bean.setsName("Johan1");
//                     bean.setsAmount("$200.00");
//                     bean.setsTransId("DHAR54214324");
//                     bean.setsDate("2019-06-25 09:17:55");
//                     break;
//                 case 2:
//                     bean.setsName("Johan2");
//                     bean.setsAmount("$100.00");
//                     bean.setsTransId("DHAR5421sdfsdfds");
//                     bean.setsDate("2019-06-30 07:17:55");
//                     break;
//             }
//             arWalletWithdraw.add(bean) ;
//         }
//
//
//        SetAdapter();
//    }

    public void SetAdapter()
    {
        int size=arWalletWithdraw.size();
        Log.e("","size= "+size);
        mAdapter = new WalletWithdrawHistoryAdapter(context,arWalletWithdraw, ViewHistoryFragment.this);
        cvWalletWithdraw.setAdapter(mAdapter);

        if(arWalletWithdraw.size()>0)
        {
            tvNoData.setVisibility(View.GONE);
        }
        else
        {
            tvNoData.setVisibility(View.VISIBLE);
        }
    }
    ArrayList<WithdrawhistoryBean> arWalletWithdraw=new ArrayList<>();
//adapter click
    @Override
    public void onClick(int position, int diff) {
        Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
    }
//withdraw history list
    @Override
    public void success(ArrayList<WithdrawhistoryBean> response, String diff) {
        arWalletWithdraw.clear();
        arWalletWithdraw=response;
        SetAdapter();
    }

    @Override
    public void error(String response, String diff) {
        appdata.ShowNewAlert(getActivity(),response);
    }

    @Override
    public void fail(String response, String diff) {
        appdata.ShowNewAlert(getActivity(),response);
    }
}




package com.diss.cabadvertisementdriver.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.diss.cabadvertisementdriver.R;
import com.diss.cabadvertisementdriver.adapter.ViewPagerAdaptertablayout;
import com.diss.cabadvertisementdriver.fragment.ViewHistoryFragment;
import com.diss.cabadvertisementdriver.fragment.WithDrawFragment;
import com.diss.cabadvertisementdriver.presenter.HelpAndRequestPresenter;
import com.diss.cabadvertisementdriver.presenter.WalletAmountPresenter;

public class Wallet_activity extends AppCompatActivity implements TabLayout.OnTabSelectedListener,View.OnClickListener, WalletAmountPresenter.WalletInfo{

    FrameLayout frameLayout;
    ViewPagerAdaptertablayout adapter;
    public TabLayout tabLayout;
    private ViewPager viewPager1;
    ImageView imageViewback;

    private WalletAmountPresenter walletInfoPresenter;
    AppData appdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wallet_activity);
        appdata=new AppData(this);
        walletInfoPresenter = new WalletAmountPresenter(this, this);
        InitCompo();
        Listener();
        TabAdapter();
        GetWalletInfo(1);

    }

    private void GetWalletInfo(int diff_) {
        if(appdata.isNetworkConnected(this)){
            switch (diff_)
            {
                case 1:
                    walletInfoPresenter.GetWallet();
                    break;
                case 2:

                    break;
            }

        }else {
            appdata.ShowNewAlert(this,"Please connect to internet");
        }
    }

    public void TabAdapter()
    {
        Pager adapter = new Pager(Wallet_activity.this.getSupportFragmentManager(), tabLayout.getTabCount());
        //Adding adapter to pager
        viewPager1.setAdapter(adapter);
    }

    private void Listener() {
        imageViewback.setOnClickListener(this);

        tabLayout.setOnTabSelectedListener(Wallet_activity.this);
        viewPager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setScrollPosition(position, 0f, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }
    TextView tvWalletAmount;
    public void InitCompo()
    {
        imageViewback=findViewById(R.id.imageback);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager1 = (ViewPager) findViewById(R.id.pager);
        tvWalletAmount = findViewById(R.id.fetchamount);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imageback:
                finish();
                Animatoo.animateFade(this);
                break;
            case R.id.click_id:

                break;
            case R.id.bt_submit:

                break;

        }
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager1.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    public class Pager extends FragmentStatePagerAdapter {

        //integer to count number of tabs
        int tabCount;

        //Constructor to the class
        public Pager(FragmentManager fm, int tabCount) {
            super(fm);
            //Initializing tab count
            this.tabCount = tabCount;
        }

        //Overriding method getItem
        @Override
        public Fragment getItem(int position) {
            //Returning the current tabs
            switch (position) {
                case 0:
                    WithDrawFragment tab1 = new WithDrawFragment();
                    return tab1;
                case 1:
                    ViewHistoryFragment tab2 = new ViewHistoryFragment();
                    return tab2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }

    //wallet infor
    @Override
    public void success(String response, String amount) {
        tvWalletAmount.setText("Rs. "+amount);
    }

    @Override
    public void error(String response) {
        tvWalletAmount.setText("Rs. 0");
        appdata.ShowNewAlert(this,response);
    }

    @Override
    public void fail(String response) {
        tvWalletAmount.setText("Rs. 0");
        appdata.ShowNewAlert(this,response);
    }
}


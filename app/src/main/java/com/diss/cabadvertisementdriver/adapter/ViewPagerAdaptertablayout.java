package com.diss.cabadvertisementdriver.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdaptertablayout extends FragmentPagerAdapter {
    private  final List<Fragment> OverviewFRagment =new ArrayList<>();
    private final List<String> Litstitles=new ArrayList<>();


    public ViewPagerAdaptertablayout(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return OverviewFRagment.get(position);
    }

    @Override
    public int getCount() {
        return Litstitles.size();
    }
    public  void Addfragment (Fragment fragment,String title){
        OverviewFRagment.add(fragment);
        Litstitles.add(title);


    }

}

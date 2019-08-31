package com.diss.cabadvertisementdriver.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.diss.cabadvertisementdriver.R;
import com.diss.cabadvertisementdriver.model.DriverCampaignBean;
import com.diss.cabadvertisementdriver.ui.AppData;

import java.util.List;

public class MyCampaigListAdapter extends RecyclerView.Adapter<MyCampaigListAdapter.MyViewHolder> {
    private List<DriverCampaignBean> list;
    private Context context;
    private CompainDetailClick compaignClick;
    AppData appdata;
    public MyCampaigListAdapter(Context context, List<DriverCampaignBean> list, CompainDetailClick compaignClick) {
        this.context = context;
        this.list = list;
        this.compaignClick = compaignClick;
        appdata=new AppData(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_my_campaign, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvComAmount.setText("Rs. "+list.get(position).getDriverHireAmount());
        holder.tvCampNm.setText("Name of Campaign: "+list.get(position).getsCampaignNm());
        holder.tvValidate.setText("( "+appdata.ConvertDate(list.get(position).getAdded_on())+" - "+appdata.ConvertDate01(list.get(position).getLastdate())+" )");
//        holder.tvNoOfCab.setText(list.get(position).getNumber_of_cabs());

        holder.lyMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compaignClick.onClick(position,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCampNm,tvComAmount,tvValidate/*,tvNoOfCab*/;
        LinearLayout lyMain;
        public MyViewHolder(View view) {
            super(view);
            tvCampNm = view.findViewById(R.id.tv_compaign_name);
            tvComAmount = view.findViewById(R.id.tv_compaign_amount);
            tvValidate = view.findViewById(R.id.tv_validity);
//            tvNoOfCab = view.findViewById(R.id.tv_no_of_cab);
            lyMain = view.findViewById(R.id.ly_main);
        }
    }

    public interface CompainDetailClick{
        void onClick(int position, int diff);
    }
}

package com.diss.cabadvertisementdriver.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diss.cabadvertisementdriver.R;
import com.diss.cabadvertisementdriver.model.VehicelInfoBean;
import com.diss.cabadvertisementdriver.ui.AppData;

import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.MyViewHolder> {
    private List<VehicelInfoBean> list;
    private Context context;
    private VehicelClick VehicelClick;
    AppData appdata;

    public VehicleAdapter(Context context, List<VehicelInfoBean> list, VehicelClick VehicelClick) {
        this.context = context;
        this.list = list;
        this.VehicelClick = VehicelClick;
        appdata=new AppData(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_vehicle, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvBrandNm.setText(list.get(position).getCar_brand());
        holder.tvModelYear.setText(list.get(position).getModel_no());
        holder.tvMobileNumber.setText(list.get(position).getMobile_number());
        holder.tvRegisterationNo.setText(list.get(position).getCar_registration_no());
//        holder.rlLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                VehicelClick.onClick(position,1);
//            }
//        });
//        holder.tvRegisterationNo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                VehicelClick.onClick(position,2);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvBrandNm,tvRegisterationNo,tvModelYear,tvMobileNumber;

        public MyViewHolder(View view) {
            super(view);
            
            tvRegisterationNo = view.findViewById(R.id.tv_vehicle_regi_number);
            tvBrandNm = view.findViewById(R.id.tv_model_name);
            tvModelYear = view.findViewById(R.id.tv_model_year);
            tvMobileNumber = view.findViewById(R.id.tv_mobile_number);

        }
    }

    public interface VehicelClick{
        void onClick(int position, int diff);
    }
}

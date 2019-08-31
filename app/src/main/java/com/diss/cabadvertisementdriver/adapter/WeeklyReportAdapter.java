package com.diss.cabadvertisementdriver.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diss.cabadvertisementdriver.R;
import com.diss.cabadvertisementdriver.model.WeeklyReportBean;
import com.diss.cabadvertisementdriver.ui.AppData;

import java.util.List;

public class WeeklyReportAdapter extends RecyclerView.Adapter<WeeklyReportAdapter.MyViewHolder> {
    private List<WeeklyReportBean> list;
    private Context context;
    private WeeklyReportClick weeklyReportClick;
    AppData appdata;

    public WeeklyReportAdapter(Context context, List<WeeklyReportBean> list, WeeklyReportClick weeklyReportClick) {
        this.context = context;
        this.list = list;
        this.weeklyReportClick = weeklyReportClick;
        appdata=new AppData(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_weekly_report, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvLocNm.setText(list.get(position).getLocNm());
        holder.tvTotalKm.setText(list.get(position).getTotalKm());
        holder.tvTotalHours.setText(list.get(position).getTotalTime());
        holder.tvDate.setText(appdata.ConvertDate02(list.get(position).getReportDate()));
        holder.rlLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weeklyReportClick.onClick(position,1);
            }
        });
        holder.rlRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weeklyReportClick.onClick(position,2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvLocNm,tvTotalKm,tvDate,tvTotalHours;
        RelativeLayout rlLeft,rlRight;
        public MyViewHolder(View view) {
            super(view);
            tvDate = view.findViewById(R.id.tv_date);
            rlLeft = view.findViewById(R.id.rl_left);
            rlRight = view.findViewById(R.id.rl_right);
            tvLocNm = view.findViewById(R.id.tv_covered_loc);
            tvTotalKm = view.findViewById(R.id.tv_total_km);
            tvTotalHours = view.findViewById(R.id.tv_hours_km);

        }
    }

    public interface WeeklyReportClick{
        void onClick(int position, int diff);
    }
}

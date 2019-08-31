package com.diss.cabadvertisementdriver.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diss.cabadvertisementdriver.R;
import com.diss.cabadvertisementdriver.model.timeline_model;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.List;

public class TimeLineAdapters extends RecyclerView.Adapter<TimeLineAdapters.MyViewHolder> {

    private List<timeline_model> recommendededataset;
    private final Context mContext;
    private Object ProductDetailsActivity;

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        TextView name;
        TimelineView timelineView;

        public MyViewHolder(View itemView) {

            super(itemView);

            this.name = (TextView) itemView.findViewById(R.id.text_id);
           /* this.timelineView=(TimelineView)itemView.findViewById(R.id.timeline_id);*/

        }

    }

    public TimeLineAdapters(Context context, List<timeline_model> data) {
        this.recommendededataset = data;
        this.mContext = context;
    }

    @Override
    public TimeLineAdapters.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_timeline, parent, false);

        TimeLineAdapters.MyViewHolder myViewHolder = new TimeLineAdapters.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final TimeLineAdapters.MyViewHolder holder, final int listPosition) {

        TextView name = holder.name;
        name.setText(recommendededataset.get(listPosition).getname());
        holder.timelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.circle_button), ContextCompat.getColor(mContext, R.color.appThemeColor_1));

    }

    @Override
    public int getItemCount() {
        return recommendededataset.size();
    }
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }
}
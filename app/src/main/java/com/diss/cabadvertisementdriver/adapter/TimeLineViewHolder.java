package com.diss.cabadvertisementdriver.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.diss.cabadvertisementdriver.R;
import com.github.vipulasri.timelineview.TimelineView;

public class TimeLineViewHolder extends RecyclerView.ViewHolder {
    public TimelineView mTimelineView;

    public TimeLineViewHolder(View itemView, int viewType) {
        super(itemView);
        mTimelineView = (TimelineView) itemView.findViewById(R.id.timeline);
        mTimelineView.initLine(viewType);
    }

    public TimeLineViewHolder(@NonNull View itemView) {
        super(itemView);
    }


}



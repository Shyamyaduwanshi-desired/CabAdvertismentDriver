package com.diss.cabadvertisementdriver.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diss.cabadvertisementdriver.R;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.HashMap;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    Context mContext;
    ArrayList<HashMap<String, String>> list_item;
    Adapter.OnItemClickList onItemClickList;

    public Adapter(Context mContext, ArrayList<HashMap<String, String>> list_item) {
        this.mContext = mContext;
        this.list_item = list_item;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.marker), ContextCompat.getColor(mContext, R.color.appThemeColor_1));

        if (list_item.get(position).get("line").equals("start")) {
            holder.mTimelineView.initLine(LineType.BEGIN);

            holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext,R.drawable.circle_button));

        } else if (list_item.get(position).get("line").equals("two")) {
            if (list_item.get(position).get("state").equals("false")) {
                holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext,R.drawable.circle_button));
            } else {
                holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext,R.drawable.circle_button));

            }
        } else if (list_item.get(position).get("line").equals("three")) {
            if (list_item.get(position).get("state").equals("false")) {
                holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext,R.drawable.circle_button));
            } else {
                holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext,R.drawable.circle_button));

            }
        } else if (list_item.get(position).get("line").equals("end")) {
            holder.mTimelineView.initLine(LineType.END);
            if (list_item.get(position).get("state").equals("false")) {
                holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext,R.drawable.circle_button));
            }
            else
            {
                holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext,R.drawable.circle_button));

            }
        }
        holder.text_inactive_title.setText(list_item.get(position).get("title"));
        if (list_item.get(position).get("msg").equals("")) {
            holder.text_inactive_msg.setVisibility(View.GONE);
        } else {
            holder.text_inactive_msg.setText(list_item.get(position).get("msg"));
            holder.text_inactive_msg.setVisibility(View.VISIBLE);
        }
        if (list_item.get(position).get("btn").equals("")) {
            holder.text_inactive_btn.setVisibility(View.GONE);
        } else {
            holder.text_inactive_btn.setVisibility(View.VISIBLE);
            holder.text_inactive_btn.setText(list_item.get(position).get("btn"));

        }

        holder.text_inactive_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickList != null) {
                    onItemClickList.onItemClick(position);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return Integer.parseInt(null);
    }
    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }
    public interface OnItemClickList {
        void onItemClick(int position);
    }

    public void setOnItemClickList(Adapter.OnItemClickList onItemClickList) {
        this.onItemClickList = onItemClickList;
    }


        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView text_inactive_title;
            public TextView text_inactive_msg;
            public TextView text_inactive_btn;
            public TimelineView mTimelineView;


            public ViewHolder(View view) {
                super(view);

                text_inactive_title = (TextView) view.findViewById(R.id.text_inactive_title);
                text_inactive_msg = (TextView) view.findViewById(R.id.text_inactive_msg);
                text_inactive_btn = (TextView) view.findViewById(R.id.text_inactive_btn);
                mTimelineView = (TimelineView) view.findViewById(R.id.timeline);
            }
        }
    }



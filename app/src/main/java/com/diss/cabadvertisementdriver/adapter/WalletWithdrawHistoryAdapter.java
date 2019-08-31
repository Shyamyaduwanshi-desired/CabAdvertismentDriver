package com.diss.cabadvertisementdriver.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diss.cabadvertisementdriver.R;
/*import com.diss.cabadvertisementdriver.model.WithdrawAmountBean;*/
import com.diss.cabadvertisementdriver.model.WithdrawhistoryBean;
import com.diss.cabadvertisementdriver.ui.AppData;

import java.util.List;

public class WalletWithdrawHistoryAdapter extends RecyclerView.Adapter<WalletWithdrawHistoryAdapter.MyViewHolder> {
    private List<WithdrawhistoryBean> list;
    private Context context;
    private WalletDetailClick walletClick;
    AppData appdata;

    public WalletWithdrawHistoryAdapter(Context context, List<WithdrawhistoryBean> list, WalletDetailClick walletClick) {
        this.context = context;
        this.list = list;
        this.walletClick = walletClick;
        appdata=new AppData(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_wallet_withdraw_history, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvName.setText(list.get(position).getName());

        holder.tvAmount.setText("Rs. "+list.get(position).getAmount_request());

        if(list.get(position).getRequest_status()=="0")
        {
            holder.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.pdlg_color_red));

        }
        else
        {
            holder.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.pdlg_color_green));
        }


        if(TextUtils.isEmpty(list.get(position).getTrans_id())||list.get(position).getTrans_id()==null||list.get(position).getTrans_id().equals("null"))
        {

        }
        else
        {
            holder.tvTransId.setText(list.get(position).getTrans_id());
        }

        if(!TextUtils.isEmpty(list.get(position).getPayment_on()))
        {
            holder.tvDate.setText(appdata.ConvertDate02(list.get(position).getPayment_on()));
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walletClick.onClick(position,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvAmount,tvDate,tvTransId;
        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            tvAmount = view.findViewById(R.id.tv_amount);
            tvDate = view.findViewById(R.id.tv_date);
            tvTransId = view.findViewById(R.id.tv_transaction_id);
        }
    }

    public interface WalletDetailClick{
        void onClick(int position, int diff);
    }
}

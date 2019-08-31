package com.diss.cabadvertisementdriver.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.diss.cabadvertisementdriver.model.WithdrawhistoryBean;
import com.diss.cabadvertisementdriver.ui.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CampaignDetailsPresenter {
    private Context context;
    private CampaignInfo campInfo;
//    private WalletHistroy walletHistory;
    private AppData appData;
    public CampaignDetailsPresenter(Context context, CampaignInfo campInfo) {
        this.context = context;
        this.campInfo = campInfo;
        appData = new AppData(context);
    }

    public interface CampaignInfo{
        void success(String response, int status);
        void error(String response);
        void fail(String response);
    }

    public void AcceptRejectCampaignRequest(final String  s_id,final int diff) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        String actionNm="";
         switch (diff)
         {
             case 1://accept
                 actionNm="accept_c_request";
                 break;
             case 2://reject
                 actionNm="reject_c_request";
                 break;
         }
        AndroidNetworking.post(AppData.url)
                .addBodyParameter("action", actionNm)
                .addBodyParameter("driver_id", appData.getUserID())
                .addBodyParameter("s_id", s_id)
                .addHeaders("Username","admin")
                .addHeaders("Password","admin123")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject reader) {
                        if(progress!=null)
                        progress.dismiss();
                        try {
                            String status = reader.getString("code");
                            String msg = reader.getString("message");
                            Log.e("","accept reject output json= "+reader.toString());
                            if(status.equals("200")){
                                campInfo.success(msg,diff);
                            }else {
                                campInfo.error(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            campInfo.fail("Something went wrong. Please try after some time.");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if(progress!=null)
                        progress.dismiss();
                        campInfo.fail("Something went wrong. Please try after some time.");
                    }
                });
    }

    public void UpdateDriverDistance(final String  hours,final String  driverKm,final String  s_id) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();

        AndroidNetworking.post(AppData.url)
                .addBodyParameter("action", "give_driver_km")
                .addBodyParameter("driver_id", appData.getUserID())
                .addBodyParameter("s_id", s_id)
                .addBodyParameter("km", driverKm)
                .addBodyParameter("hours", hours)
                .addBodyParameter("date", appData.GetCurrentDate())
                .addHeaders("Username","admin")
                .addHeaders("Password","admin123")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject reader) {
                        if(progress!=null)
                            progress.dismiss();
                        try {
                            String status = reader.getString("code");
                            String msg = reader.getString("message");
                            Log.e("","update driver kilometer output json= "+reader.toString());
                            if(status.equals("200")){
                                campInfo.success(msg,3);
                            }else {
                                campInfo.error(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            campInfo.fail("Something went wrong. Please try after some time.");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if(progress!=null)
                            progress.dismiss();
                        campInfo.fail("Something went wrong. Please try after some time.");
                    }
                });
    }

}

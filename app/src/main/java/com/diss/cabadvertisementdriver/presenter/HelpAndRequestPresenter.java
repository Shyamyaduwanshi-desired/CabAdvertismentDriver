package com.diss.cabadvertisementdriver.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.diss.cabadvertisementdriver.model.CarModelBean;
import com.diss.cabadvertisementdriver.model.CountryBean;
import com.diss.cabadvertisementdriver.model.SignupBean;
import com.diss.cabadvertisementdriver.ui.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HelpAndRequestPresenter {
    private Context context;
    private HelpAndRequest helpReq;
    private AppData appData;

    public HelpAndRequestPresenter(Context context, HelpAndRequest helpReq) {
        this.context = context;
        this.helpReq = helpReq;
        appData = new AppData(context);
    }

    public interface HelpAndRequest{
        void success(String response, String otp);
        void error(String response);
        void fail(String response);
    }

    public void HelpAndRequestMethod(String name,String email,String request) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        AndroidNetworking.post(AppData.url)
                .addBodyParameter("action", "help_request")
                .addBodyParameter("user_id", appData.getUserID())
                .addBodyParameter("name", name)
                .addBodyParameter("email", email)
                .addBodyParameter("request", request)

                .addHeaders("Username","admin")
                .addHeaders("Password","admin123")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject reader) {
                        // do anything with response
                        if(progress!=null)
                        progress.dismiss();
                        try {
                            String status = reader.getString("code");
                            String msg = reader.getString("message");
                            Log.e("","help and request output json= "+reader.toString());
                            if(status.equals("200")){
                                helpReq.success(msg,"");
                            }else {
                                helpReq.error(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            helpReq.fail("Something went wrong. Please try after some time.");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if(progress!=null)
                        progress.dismiss();
                        helpReq.fail("Something went wrong. Please try after some time.");
                    }
                });
    }


}

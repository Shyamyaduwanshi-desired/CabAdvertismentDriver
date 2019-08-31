package com.diss.cabadvertisementdriver.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.diss.cabadvertisementdriver.ui.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class VerifyCampaignDataPresenter {
    private Context context;
    private CampaignData campaignData;
    private AppData appData;

    public VerifyCampaignDataPresenter(Context context, CampaignData campaignData) {
        this.context = context;
        this.campaignData = campaignData;
        appData = new AppData(context);
        AndroidNetworking.initialize(context);
    }

    public interface CampaignData{
        void success(String response, String status);
        void error(String response,String status);
        void fail(String response,String status);
    }

   public void DriverRecieveCampaingData(final String companyId, final String campaignId, final String position) {
       final ProgressDialog progress = new ProgressDialog(context);
       progress.setMessage("Please Wait..");
       progress.setCancelable(false);
       progress.show();
       AndroidNetworking.post(AppData.url)
               .addBodyParameter("action", "recieve_location_bydriver")
               .addBodyParameter("driver_id", appData.getUserID())
               .addBodyParameter("user_id", companyId)
               .addBodyParameter("s_id", campaignId)
               .addBodyParameter("status", position)
               .addHeaders("Username","admin")
               .addHeaders("Password","admin123")
               .setPriority(Priority.MEDIUM)
               .build()
               .getAsJSONObject(new JSONObjectRequestListener() {
                   @Override
                   public void onResponse(JSONObject reader) {
                       // do anything with response
                       progress.dismiss();
                       try {
                           String status = reader.getString("code");
                           String msg = reader.getString("message");
                           Log.e("","campaign Data verify output json= "+reader.toString());
                           if(status.equals("200")){
                               campaignData.success(msg,"VerifyCampaign");
                           }else {
                               campaignData.error(msg,"VerifyCampaign");
                           }
                       } catch (JSONException e) {
                           e.printStackTrace();
                           campaignData.fail("Something went wrong. Please try after some time.","VerifyCampaign");
                       }
                   }
                   @Override
                   public void onError(ANError error) {
                       // handle error
                       progress.dismiss();
                       campaignData.fail("Something went wrong. Please try after some time.","VerifyCampaign");
                   }
               });
   }

    public void DriverReadyToGo(final String campaignId) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        AndroidNetworking.post(AppData.url)
                .addBodyParameter("action", "ready_to_go")
                .addBodyParameter("driver_id", appData.getUserID())
                .addBodyParameter("s_id", campaignId)
//                .addBodyParameter("status", position)
//                .addBodyParameter("user_id", companyId)
                .addHeaders("Username","admin")
                .addHeaders("Password","admin123")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject reader) {
                        // do anything with response
                        progress.dismiss();
                        try {
                            String status = reader.getString("code");
                            String msg = reader.getString("message");
                            Log.e("","redy to go verify output json= "+reader.toString());
                            if(status.equals("200")){
                                campaignData.success(msg,"ReadyToGo");
                            }else {
                                campaignData.error(msg,"ReadyToGo");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            campaignData.fail("Something went wrong. Please try after some time.","VerifyCampaign");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        progress.dismiss();
                        campaignData.fail("Something went wrong. Please try after some time.","VerifyCampaign");
                    }
                });
    }

    public void DriverPaymentRecieved(final String campaignId) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        AndroidNetworking.post(AppData.url)
                .addBodyParameter("action", "receive_payment_by_driver")
                .addBodyParameter("driver_id", appData.getUserID())
                .addBodyParameter("s_id", campaignId)
//                .addBodyParameter("status", position)
//                .addBodyParameter("user_id", companyId)
                .addHeaders("Username","admin")
                .addHeaders("Password","admin123")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject reader) {
                        // do anything with response
                        progress.dismiss();
                        try {
                            String status = reader.getString("code");
                            String msg = reader.getString("message");
                            Log.e("","Driver Payment Recieved verify output json= "+reader.toString());
                            if(status.equals("200")){
                                campaignData.success(msg,"DriverPaymentRecieved");
                            }else {
                                campaignData.error(msg,"DriverPaymentRecieved");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            campaignData.fail("Something went wrong. Please try after some time.","VerifyCampaign");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        progress.dismiss();
                        campaignData.fail("Something went wrong. Please try after some time.","VerifyCampaign");
                    }
                });
    }


    public void UploadPhotoVechileWithSticker(final String campaignId, JSONArray jsonArrayImage) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        Log.e("","campaignId= "+campaignId+" getUserID= "+appData.getUserID()+" arImage.get(0)= "+String.valueOf(jsonArrayImage)/*arImage.get(0)*/);
        AndroidNetworking.post(AppData.url)
                .addBodyParameter("action", "upload_vehicle_with_stickers")
                .addBodyParameter("driver_id", appData.getUserID())
                .addBodyParameter("s_id", campaignId)
                .addBodyParameter("medias", String.valueOf(jsonArrayImage))
//                .addBodyParameter("medias", jsonArrayImage.toString())
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
                            Log.e("","UploadPhotoVechileWithSticker output json= "+reader.toString());
                            if(status.equals("200")){
                                campaignData.success(msg,"PhotoVechileWithSticker");
                            }else {
                                campaignData.error(msg,"PhotoVechileWithSticker");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            campaignData.fail("Something went wrong. Please try after some time33.","PhotoVechileWithSticker");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if(progress!=null)
                            progress.dismiss();
                        campaignData.fail("Something went wrong. Please try after some time.","PhotoVechileWithSticker");
                    }
                });
    }

    public void DriverFeedback(final String campaignId,final  String ratting,final String feedback) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        int userId=Integer.parseInt(appData.getUserID());
        Log.e("","campaignId= "+campaignId+" getUserID= "+userId+" ratting= "+ratting+" feedback= "+feedback);
        AndroidNetworking.post(AppData.url)
//                .addBodyParameter("action", "upload_vehicle_with_stickers")
                .addBodyParameter("action", "give_driver_feedback")//feedback for campaign(company give_company_feedback)
                .addBodyParameter("driver_id", appData.getUserID())
//                .addBodyParameter("user_id", companyId)
                .addBodyParameter("s_id", campaignId+"")
                .addBodyParameter("feedback", feedback)
                .addBodyParameter("rating", ratting+"")

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
                            Log.e("","feedback for company vai driver output json= "+reader.toString());

                            if(status.equals("200")){
                                campaignData.success(msg,"feedback");
                            }else {
                                campaignData.error(msg,"feedback");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            campaignData.fail("Something went wrong. Please try after some time.","feedback");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if(progress!=null)
                            progress.dismiss();
                        campaignData.fail("Something went wrong. Please try after some time.","feedback");
                    }
                });
    }

}

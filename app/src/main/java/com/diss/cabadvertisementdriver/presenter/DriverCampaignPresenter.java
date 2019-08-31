package com.diss.cabadvertisementdriver.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import com.diss.cabadvertisementdriver.model.DriverCampaignBean;
import com.diss.cabadvertisementdriver.ui.AppData;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DriverCampaignPresenter {
    private Context context;
    private CampaingListData campaingData;
    private AppData appData;

    public DriverCampaignPresenter(Context context, CampaingListData campaingData) {
        this.context = context;
        this.campaingData = campaingData;
        appData = new AppData(context);
    }

    public interface CampaingListData{
        void success(ArrayList<DriverCampaignBean> response, String status);
        void error(String response);
        void fail(String response);
    }

   public void GetDriverCampaignList() {//current campaing object
       final ArrayList<DriverCampaignBean> list = new ArrayList<>();
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        AndroidNetworking.post(AppData.url)
                .addBodyParameter("action", "get_driver_capaigns")
                .addBodyParameter("driver_id", appData.getUserID())
                .addHeaders("Username","admin")
                .addHeaders("Password","admin123")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject reader) {
                        // do anything with response
                        if(progress!=null) {
                            progress.dismiss();
                        }
                        list.clear();
                        try {
                            String status = reader.getString("code");
                            String msg = reader.getString("message");
                            Log.e("","current campaign output json= "+reader.toString());
                            if(status.equals("200")){

                                JSONArray jsArrayPlan = reader.getJSONArray("body");
                                DriverCampaignBean bean;
                                for (int count = 0; count < jsArrayPlan.length(); count++) {
                                    JSONObject object = jsArrayPlan.getJSONObject(count);
                                    bean=new DriverCampaignBean();
                                    String s_id = object.getString("s_id");
                                    String campaign_name = object.getString("campaign_name");

                                    String user_id = object.getString("user_id");
                                    String c_name = object.getString("c_l_name");
                                    String c_l_lat = object.getString("c_l_lat");
                                    String c_l_lng = object.getString("c_l_lng");

                                    String media_id = object.getString("media_id");
                                    String amount = object.getString("amount");
                                    JSONArray jsImageArray=object.getJSONArray("image");
                                    String sub_status = object.getString("sub_status");

                                    String plan_name = object.getString("plan_name");
                                    String plan_days = object.getString("plan_month");
                                    String number_of_cabs = object.getString("number_of_cabs");
                                    String plan_amount = object.getString("plan_amount");

                                    String added_on = object.getString("added_on");
                                    String lastdate = object.getString("lastdate");


                                    bean.setCompaignId(s_id);
                                    bean.setsCampaignNm(campaign_name);
                                    bean.setUser_id(user_id);
                                    bean.setC_l_name(c_name);
                                    bean.setC_l_lat(c_l_lat);
                                    bean.setC_l_lng(c_l_lng);
                                    bean.setMedia_id(media_id);
                                    bean.setImage(jsImageArray.toString());
                                    bean.setSub_status(sub_status);
//                                    bean.setPlan_id(plan_id);
                                    bean.setPlan_name(plan_name);
                                    bean.setPlan_month(plan_days);
                                    bean.setNumber_of_cabs(number_of_cabs);
                                    bean.setPlan_amount(plan_amount);
                                    bean.setAdded_on(added_on);
                                    bean.setLastdate(lastdate);
                                    bean.setDriverHireAmount(amount);
                                    list.add(bean);
                                }

                                campaingData.success(list,"");
                            }else {
                                campaingData.error(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            campaingData.fail("Something went wrong. Please try after some time.");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if(progress!=null)
                        progress.dismiss();
                        campaingData.fail("Something went wrong. Please try after some time.");
                    }
                });
    }

    public void GetDriverAllCampaignList() {//for all old and current campaing list
       final ArrayList<DriverCampaignBean> list = new ArrayList<>();
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        AndroidNetworking.post(AppData.url)
                .addBodyParameter("action", "get_all_campaigns_of_drivers")
                .addBodyParameter("driver_id", appData.getUserID())
                .addHeaders("Username","admin")
                .addHeaders("Password","admin123")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject reader) {
                        // do anything with response
                        if(progress!=null) {
                            progress.dismiss();
                        }
                        list.clear();
                        try {
                            String status = reader.getString("code");
                            String msg = reader.getString("message");
                            Log.e("","all campaign output json= "+reader.toString());
                            if(status.equals("200")){

                                JSONArray jsArrayPlan = reader.getJSONArray("body");
                                DriverCampaignBean bean;
                                for (int count = 0; count < jsArrayPlan.length(); count++) {
                                    JSONObject object = jsArrayPlan.getJSONObject(count);
                                    bean=new DriverCampaignBean();
                                    String s_id = object.getString("s_id");
                                    String campaign_name = object.getString("c_name");

                                    String user_id = object.getString("user_id");

                                    String plan_name = object.getString("plan_name");
                                    String plan_days = object.getString("plan_month");
                                    String number_of_cabs = object.getString("number_of_cabs");
                                    String plan_amount = object.getString("plan_amount");
                                    String added_on = object.getString("added_on");
                                    String lastdate = object.getString("lastdate");
                                    String c_name = object.getString("c_l_name");
                                    String c_l_lat = object.getString("c_l_lat");
                                    String c_l_lng = object.getString("c_l_lng");
                                    String amount = object.getString("amount");
                                    String sub_status = object.getString("status");


                                    bean.setCompaignId(s_id);
                                    bean.setsCampaignNm(campaign_name);
                                    bean.setUser_id(user_id);
                                    bean.setPlan_name(plan_name);
                                    bean.setPlan_month(plan_days);
                                    bean.setNumber_of_cabs(number_of_cabs);
                                    bean.setPlan_amount(plan_amount);
                                    bean.setAdded_on(added_on);
                                    bean.setLastdate(lastdate);

                                    bean.setDriverHireAmount(amount);
                                    bean.setC_l_name(c_name);
                                    bean.setC_l_lat(c_l_lat);
                                    bean.setC_l_lng(c_l_lng);
                                    bean.setSub_status(sub_status);

                                    bean.setMedia_id("");
                                    bean.setImage("");


                                    list.add(bean);
                                }

                                campaingData.success(list,"");
                            }else {
                                campaingData.error(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            campaingData.fail("Something went wrong. Please try after some time.");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if(progress!=null)
                        progress.dismiss();
                        campaingData.fail("Something went wrong. Please try after some time.");
                    }
                });
    }


    public void GetAnalysisOfACampaign(final String s_id,final String type) {//for single campaing details
       final ArrayList<DriverCampaignBean> list = new ArrayList<>();
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        AndroidNetworking.post(AppData.url)
                .addBodyParameter("action", "analysis_report_driver")
                .addBodyParameter("driver_id", appData.getUserID())
                .addBodyParameter("s_id", s_id)
                .addBodyParameter("date", appData.GetCurrentDate())
                .addBodyParameter("type", type)
                .addHeaders("Username","admin")
                .addHeaders("Password","admin123")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject reader) {
                        // do anything with response
                        if(progress!=null) {
                            progress.dismiss();
                        }
                        list.clear();
                        try {
                            String status = reader.getString("code");
                            String msg = reader.getString("message");
                            Log.e("","Single campaign output json= "+reader.toString());
                            if(status.equals("200")){

//                                JSONArray jsArrayPlan = reader.getJSONArray("body");
                                DriverCampaignBean bean;
//                                for (int count = 0; count < jsArrayPlan.length(); count++)
                                {
//                                    JSONObject object = jsArrayPlan.getJSONObject(count);
                                    JSONObject object = reader.getJSONObject("body");
                                    bean=new DriverCampaignBean();
                                    String s_id = object.getString("s_id");
                                    String campaign_name = object.getString("c_name");

//                                    String user_id = object.getString("user_id");

                                    String plan_name = object.getString("plan_name");
                                    String plan_days = object.getString("plan_month");
                                    String number_of_cabs = object.getString("number_of_cabs");
                                    String plan_amount = object.getString("plan_amount");



                                    String c_name = object.getString("c_l_name");
                                    String c_l_lat = object.getString("c_l_lat");
                                    String c_l_lng = object.getString("c_l_lng");
                                    String amount = object.getString("amount");

                                    String total_km = object.getString("total_km");
                                    String total_hours = object.getString("total_hours");

                                    String added_on = object.getString("added_on");
                                    String lastdate = object.getString("lastdate");
                                    String date_range = object.getString("date_range");

                                    if(total_km.equals(null)||total_km.equals("null"))
                                    {
                                        total_km="0";
                                    }

                                    if(total_hours.equals(null)||total_hours.equals("null"))
                                    {
                                        total_hours="0";
                                    }
                                    if(date_range.equals(null)||date_range.equals("null"))
                                    {
                                        date_range="";
                                    }


//                                    String sub_status = object.getString("status");

                                    bean.setCompaignId(s_id);
                                    bean.setsCampaignNm(campaign_name);
                                    bean.setPlan_name(plan_name);
                                    bean.setPlan_month(plan_days);
                                    bean.setNumber_of_cabs(number_of_cabs);
                                    bean.setPlan_amount(plan_amount);
                                    bean.setDriverHireAmount(amount);
                                    bean.setC_l_name(c_name);
                                    bean.setC_l_lat(c_l_lat);
                                    bean.setC_l_lng(c_l_lng);
                                    bean.setAdded_on(added_on);
                                    bean.setLastdate(lastdate);

//                                    bean.setUser_id(user_id);
//                                    bean.setSub_status(sub_status);


                                    bean.setUser_id("");
                                    bean.setSub_status("");
                                    bean.setMedia_id("");
                                    bean.setImage("");

                                    bean.setTotal_km(total_km);
                                    bean.setTotal_hours(total_hours);
                                    bean.setDate_range(date_range);

                                    list.add(bean);
                                }

                                campaingData.success(list,"");
                            }else {
                                campaingData.error(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            campaingData.fail("Something went wrong. Please try after some time.");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if(progress!=null)
                        progress.dismiss();
                        campaingData.fail("Something went wrong. Please try after some time.");
                    }
                });
    }


    public void GetRequestCampaignDetail(final String s_id) {//this is calling from notification and from login if driver login after accept campaign request
        final ArrayList<DriverCampaignBean> list = new ArrayList<>();
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        AndroidNetworking.post(AppData.url)
                .addBodyParameter("action", "get_driver_campaign_details")
                .addBodyParameter("driver_id", appData.getUserID())
                .addBodyParameter("s_id", s_id)
                .addHeaders("Username","admin")
                .addHeaders("Password","admin123")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject reader) {
                        // do anything with response
                        if(progress!=null) {
                            progress.dismiss();
                        }
                        list.clear();
                        try {
                            String status = reader.getString("code");
                            String msg = reader.getString("message");
                            Log.e("","request single campaign details output json= "+reader.toString());
                            if(status.equals("200")){

                                DriverCampaignBean bean;
                                    JSONObject object = reader.getJSONObject("body");;
                                    bean=new DriverCampaignBean();
                                    String s_id = object.getString("s_id");
                                    String c_name = object.getString("c_name");
                                    String c_l_name = object.getString("c_l_name");
                                    String c_l_lat = object.getString("c_l_lat");
                                    String c_l_lng = object.getString("c_l_lng");
                                    String plan_days = object.getString("plan_month");
                                    String amount = object.getString("amount");


                                    bean.setCompaignId(s_id);
                                    bean.setsCampaignNm(c_name);
                                    bean.setC_l_name(c_l_name);
                                    bean.setC_l_lat(c_l_lat);
                                    bean.setC_l_lng(c_l_lng);
                                    bean.setPlan_month(plan_days);
                                    bean.setDriverHireAmount(amount);

                                    bean.setUser_id("");
                                    bean.setMedia_id("");
                                    bean.setImage("");
                                    bean.setSub_status("");
                                    bean.setPlan_name("");
                                    bean.setNumber_of_cabs("");
                                    bean.setPlan_amount("");
                                    bean.setAdded_on("");
                                    bean.setLastdate("");
                                    list.add(bean);

                                    //for show future perpose
                                    Gson gson = new Gson();
                                    String jsonObj = gson.toJson(list.get(0));
                                    appData.setAccepted_request_jsonBean(jsonObj);
                                    if(appData.getDfffS_id().equals("1")) {
                                        appData.sets_Id("");
                                        appData.setDfffS_Id("0");
                                    }

                                campaingData.success(list,"");
                            }else {
                                campaingData.error(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            campaingData.fail("Something went wrong. Please try after some time.");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if(progress!=null)
                            progress.dismiss();
                        campaingData.fail("Something went wrong. Please try after some time.");
                    }
                });
    }



}

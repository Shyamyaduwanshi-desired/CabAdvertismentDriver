package com.diss.cabadvertisementdriver.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.diss.cabadvertisementdriver.model.VehicelInfoBean;
import com.diss.cabadvertisementdriver.ui.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class VehicleDetailPresenter {
    private Context context;
    private VehicleInfo vehicleDetail;
    private AppData appData;

    public VehicleDetailPresenter(Context context, VehicleInfo vehicleDetail) {
        this.context = context;
        this.vehicleDetail = vehicleDetail;
        appData = new AppData(context);
    }

    public interface VehicleInfo{
        void success(ArrayList<VehicelInfoBean> response, String status);
        void error(String response);
        void fail(String response);
    }
// i want to show list data if multiple vehicle
    public void GetVehicleInfo() {
        final ArrayList<VehicelInfoBean> modelList = new ArrayList<>();
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        AndroidNetworking.post(AppData.url)
                .addBodyParameter("action", "get_car_models")
                .addBodyParameter("driver_id", appData.getUserID())
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
                        modelList.clear();
                        try {
                            String status = reader.getString("code");
                            String msg = reader.getString("message");
                            Log.e("","vehicle info output json= "+reader.toString());
                            if(status.equals("200")){
                                VehicelInfoBean bean;
                                try {
                                    JSONArray jsArrayModel = reader.getJSONArray("body");
                                    if (jsArrayModel != null&&jsArrayModel.length()>0) {
                                        for (int count = 0; count < jsArrayModel.length(); count++) {
                                            JSONObject object = jsArrayModel.getJSONObject(count);
                                            bean = new VehicelInfoBean();
                                            String ID = object.getString("ID");
                                            String model_name = object.getString("model_no");
                                            String model_no = object.getString("model_year");
                                            String car_brand = object.getString("car_brand");
                                            String car_registration_no = object.getString("car_registration_no");

                                            bean.setID(ID);
                                            bean.setModel_name(model_name);
                                            bean.setModel_no(model_no);
                                            bean.setCar_brand(car_brand);
                                            bean.setCar_registration_no(car_registration_no);

                                            modelList.add(bean);
                                        }
                                    }
                                    vehicleDetail.success(modelList,"");
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                    vehicleDetail.error(msg);
                                }


                            }else {
                                vehicleDetail.error(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            vehicleDetail.fail("Something went wrong. Please try after some time.");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if(progress!=null)
                        progress.dismiss();
                        vehicleDetail.fail("Something went wrong. Please try after some time.");
                    }
                });
    }


}

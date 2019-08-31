package com.diss.cabadvertisementdriver.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.diss.cabadvertisementdriver.model.AddCarModelBean;
import com.diss.cabadvertisementdriver.model.CarModelBean;
import com.diss.cabadvertisementdriver.model.CountryBean;
import com.diss.cabadvertisementdriver.model.SignupBean;
import com.diss.cabadvertisementdriver.ui.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AddCarModelPresenter {
    private Context context;
    private AddCarModel addCar;
    private AppData appData;

    public AddCarModelPresenter(Context context, AddCarModel addCar) {
        this.context = context;
        this.addCar = addCar;
        appData = new AppData(context);
    }

    public interface AddCarModel{
        void success(String response, String status);
        void error(String response);
        void fail(String response);
    }

    public void AddDriverCar(AddCarModelBean bean) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        AndroidNetworking.post(AppData.url)
                .addBodyParameter("action", "add_car_model")
                .addBodyParameter("driver_id", appData.getUserID())
                .addBodyParameter("car_brand", bean.getCar_brand())
                .addBodyParameter("model_year", bean.getModel_name())//model year
                .addBodyParameter("model_no", bean.getModel_no())//model number
                .addBodyParameter("car_registration_no", bean.getCar_registration_no())
                .addBodyParameter("address", bean.getDriver_add())
                .addBodyParameter("country", bean.getDriver_county())
                .addBodyParameter("state", bean.getDriver_state())
                .addBodyParameter("city", bean.getDriver_city())

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
                            Log.e("","add car output json= "+reader.toString());
                            if(status.equals("200")){
//                                reader.getJSONObject("body").getString("otp")
                                addCar.success(msg,"");
                            }else {
                                addCar.error(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            addCar.fail("Something went wrong. Please try after some time.");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if(progress!=null)
                        progress.dismiss();
                        addCar.fail("Something went wrong. Please try after some time.");
                    }
                });
    }


}

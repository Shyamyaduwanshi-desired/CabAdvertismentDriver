package com.diss.cabadvertisementdriver.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import com.diss.cabadvertisementdriver.model.UpdateProfileBean;
import com.diss.cabadvertisementdriver.ui.AppData;

import org.json.JSONException;
import org.json.JSONObject;


public class UpdateProfilePresenter {
    private Context context;
    private updateProfile updateProfile;
    private AppData appData;

    public UpdateProfilePresenter(Context context, updateProfile updateProfile) {
        this.context = context;
        this.updateProfile = updateProfile;
        appData = new AppData(context);
    }

    public interface updateProfile{
        void success(String response, String status);
        void error(String response);
        void fail(String response);
    }

    public void updateProfileData(UpdateProfileBean bean) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        AndroidNetworking.post(AppData.url)
                .addBodyParameter("action", "update_driver_profile")
                .addBodyParameter("driver_name", bean.getDriver_name())
                .addBodyParameter("user_id", appData.getUserID())
                .addBodyParameter("profile_pic", bean.getProfile_pic())
                .addBodyParameter("profile_pic_name", bean.getPic_name())
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
                            Log.e("","update profile output json= "+reader.toString());
                            if(status.equals("200")){

                                appData.setProfilePic(reader.getJSONObject("body").getString("user_pic_full"));
                                appData.setUsername(reader.getJSONObject("body").getString("display_name"));

//                                appData.setCarBrand(reader.getJSONObject("body").getString("car_brand"));
//                                appData.setCarModel(reader.getJSONObject("body").getString("Model_no"));
//                                appData.setCarRegitrationNo(reader.getJSONObject("body").getString("car_registration_no"));
//                                appData.setDriverAdd(reader.getJSONObject("body").getString("address"));
//                                appData.setDriverCountry(reader.getJSONObject("body").getString("country"));
//                                appData.setDriverState(reader.getJSONObject("body").getString("state"));
//                                appData.setDriverCity(reader.getJSONObject("body").getString("city"));

                                updateProfile.success(msg,"");
                            }else {
                                updateProfile.error(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            updateProfile.fail("Something went wrong. Please try after some time.");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if(progress!=null)
                        progress.dismiss();
                        updateProfile.fail("Something went wrong. Please try after some time.");
                    }
                });
    }
}

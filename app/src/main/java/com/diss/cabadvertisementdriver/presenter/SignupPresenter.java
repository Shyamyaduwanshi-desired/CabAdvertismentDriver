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
import com.diss.cabadvertisementdriver.model.CouponBean;
import com.diss.cabadvertisementdriver.model.SignupBean;
import com.diss.cabadvertisementdriver.ui.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SignupPresenter {
    private Context context;
    private SignUp signup;
    private CarModel carModel;
    private AppData appData;

    public SignupPresenter(Context context, SignUp signup) {
        this.context = context;
        this.signup = signup;
        appData = new AppData(context);
    }

    public interface SignUp{
        void success(String response, String otp);
        void error(String response);
        void fail(String response);
    }

    public void SignUpDriver(SignupBean bean) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        AndroidNetworking.post(AppData.url)
                .addBodyParameter("action", "register_driver")
                .addBodyParameter("driver_name", bean.getDriver_name())
                .addBodyParameter("email", bean.getEmail())
                .addBodyParameter("mobile_no", bean.getMobile_no())
                .addBodyParameter("password", bean.getPassword())
                .addBodyParameter("confirm_password", bean.getConfirm_password())
                .addBodyParameter("car_brand", bean.getCar_brand())
                .addBodyParameter("model_no", bean.getCar_model())//name
                .addBodyParameter("model_year", bean.getModel_name())//year

                .addBodyParameter("car_registration_no", bean.getCar_register_no())
                .addBodyParameter("address", bean.getDriver_add())
                .addBodyParameter("country", bean.getDrive_country())
                .addBodyParameter("state", bean.getDrive_state())
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
                            Log.e("","sign output json= "+reader.toString());
                            if(status.equals("200")){
                                appData.setForGotUserId(reader.getJSONObject("body").getString("ID"));
                                signup.success(msg,reader.getJSONObject("body").getString("otp"));
                            }else {
                                signup.error(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            signup.fail("Something went wrong. Please try after some time.");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if(progress!=null)
                        progress.dismiss();
                        signup.fail("Something went wrong. Please try after some time.");
                    }
                });
    }

    public SignupPresenter(Context context, CarModel carModel,String diff) {
        this.context = context;
        this.carModel = carModel;
        appData = new AppData(context);
    }

    public interface CarModel{
        void success(ArrayList<CarModelBean> response,ArrayList<CountryBean> countryList, String diff);
        void error(String response, String diff);
        void fail(String response, String diff);
    }
    public void GetCarModel() {
        final ArrayList<CarModelBean> modelList = new ArrayList<>();
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        AndroidNetworking.post(AppData.url)
                .addBodyParameter("action", "car_model")
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
                            Log.e("","car model output json= "+reader.toString());
                            if(status.equals("200")){
                                CarModelBean bean;
                                try {
                                    JSONArray jsArrayModel = reader.getJSONArray("body");
                                    if (jsArrayModel != null&&jsArrayModel.length()>0) {
                                    for (int count = 0; count < jsArrayModel.length(); count++) {
                                    JSONObject object = jsArrayModel.getJSONObject(count);
                                            bean = new CarModelBean();
                                            String car_model_id = object.getString("car_model_id");
                                            String model_name = object.getString("model_name");
                                            String model_no = object.getString("model_no");

                                            bean.setCar_model_id(car_model_id);
                                            bean.setModel_name(model_name);
                                            bean.setModel_no(model_no);

                                            modelList.add(bean);
                                        }
                                    }
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                                carModel.success(modelList,null,"1");

                            }
                            else {
                                carModel.error(msg,"1");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            carModel.fail("Something went wrong. Please try after some time.","1");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if(progress!=null)
                            progress.dismiss();
                        carModel.fail("Something went wrong. Please try after some time.","1");
                    }
                });
    }

    public void GetAllCountry() {
        final ArrayList<CountryBean> countryList = new ArrayList<>();
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        AndroidNetworking.post(AppData.url)
                .addBodyParameter("action", "get_country")
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
                        countryList.clear();
                        try {
                            String status = reader.getString("code");
                            String msg = reader.getString("message");
                            Log.e("","coupon output json= "+reader.toString());
                            if(status.equals("200")){
                                CountryBean bean;
                                try {
                                    JSONArray jsArrayCountry = reader.getJSONArray("body");
                                    if (jsArrayCountry != null&&jsArrayCountry.length()>0) {
                                    for (int count = 0; count < jsArrayCountry.length(); count++) {
                                    JSONObject object = jsArrayCountry.getJSONObject(count);
                                            bean = new CountryBean();
                                            String country_id = object.getString("country_id");
                                            String country_name = object.getString("country_name");
                                            bean.setCountry_id(country_id);
                                            bean.setCountry_name(country_name);
                                            countryList.add(bean);
                                        }
                                    }
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                                carModel.success(null,countryList,"2");

                            }
                            else {
                                carModel.error(msg+"country error","2");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            carModel.fail("Something went wrong. Please try after some time.","2");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if(progress!=null)
                            progress.dismiss();
                        carModel.fail("Something went wrong. Please try after some time.","2");
                    }
                });
    }

    public void GetState(String countryId) {
        final ArrayList<CountryBean> stateList = new ArrayList<>();
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        AndroidNetworking.post(AppData.url)
                .addBodyParameter("action", "get_state")
                .addBodyParameter("country_id", countryId)
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
                        stateList.clear();
                        try {
                            String status = reader.getString("code");
                            String msg = reader.getString("message");
                            Log.e("","state output json= "+reader.toString());
                            if(status.equals("200")){
                                CountryBean bean;
                                try {
                                    JSONArray jsArrayCountry = reader.getJSONArray("body");
                                    if (jsArrayCountry != null&&jsArrayCountry.length()>0) {
                                    for (int count = 0; count < jsArrayCountry.length(); count++) {
                                    JSONObject object = jsArrayCountry.getJSONObject(count);
                                            bean = new CountryBean();
                                            String state_id = object.getString("state_id");
                                            String state_name = object.getString("state_name");
                                            bean.setCountry_id(state_id);
                                            bean.setCountry_name(state_name);
                                            stateList.add(bean);
                                        }
                                    }
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                                carModel.success(null,stateList,"3");

                            }
                            else {
                                carModel.error(msg+"state error","3");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            carModel.fail("Something went wrong. Please try after some time.","3");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if(progress!=null)
                            progress.dismiss();
                        carModel.fail("Something went wrong. Please try after some time.","3");
                    }
                });
    }

    public void GetCity(String stateId) {
        final ArrayList<CountryBean> cityList = new ArrayList<>();
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        AndroidNetworking.post(AppData.url)
                .addBodyParameter("action", "get_city")
                .addBodyParameter("state_id", stateId)
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
                        cityList.clear();
                        try {
                            String status = reader.getString("code");
                            String msg = reader.getString("message");
                            Log.e("","state output json= "+reader.toString());
                            if(status.equals("200")){
                                CountryBean bean;
                                try {
                                    JSONArray jsArrayCountry = reader.getJSONArray("body");
                                    if (jsArrayCountry != null&&jsArrayCountry.length()>0) {
                                    for (int count = 0; count < jsArrayCountry.length(); count++) {
                                    JSONObject object = jsArrayCountry.getJSONObject(count);
                                            bean = new CountryBean();
                                            String city_id = object.getString("city_id");
                                            String city_name = object.getString("city_name");
                                            bean.setCountry_id(city_id);
                                            bean.setCountry_name(city_name);
                                            cityList.add(bean);
                                        }
                                    }
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                                carModel.success(null,cityList,"4");

                            }
                            else {
                                carModel.error(msg,"4");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            carModel.fail("Something went wrong. Please try after some time.","4");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if(progress!=null)
                            progress.dismiss();
                        carModel.fail("Something went wrong. Please try after some time.","4");
                    }
                });
    }

}

package com.diss.cabadvertisementdriver.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.diss.cabadvertisementdriver.model.WithdrawhistoryBean;
import com.diss.cabadvertisementdriver.model.CountryBean;
import com.diss.cabadvertisementdriver.model.SignupBean;
import com.diss.cabadvertisementdriver.ui.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class WalletAmountPresenter {
    private Context context;
    private WalletInfo walletInfo;
    private WalletHistroy walletHistory;
    private AppData appData;
    public WalletAmountPresenter(Context context, WalletInfo WalletInfo) {
        this.context = context;
        this.walletInfo = WalletInfo;
        appData = new AppData(context);
    }

    public interface WalletInfo{
        void success(String response, String amount);
        void error(String response);
        void fail(String response);
    }

    public void GetWallet() {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        AndroidNetworking.post(AppData.url)
                .addBodyParameter("action", "get_wallet_amount")
                .addBodyParameter("user_id", appData.getUserID())
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
                            Log.e("","wallet amount output json= "+reader.toString());
                            if(status.equals("200")){

                                walletInfo.success(msg,reader.getJSONObject("body").getString("wallet_amount"));
                            }else {
                                walletInfo.error(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            walletInfo.fail("Something went wrong. Please try after some time.");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if(progress!=null)
                        progress.dismiss();
                        walletInfo.fail("Something went wrong. Please try after some time.");
                    }
                });
    }


    public void WithdrawWalletAmount(String name,String accountNumber,String ifsc,String dsc,String withDrawamount) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        AndroidNetworking.post(AppData.url)
                .addBodyParameter("action", "withdraw_request")
                .addBodyParameter("driver_id", appData.getUserID())
                .addBodyParameter("name", name)
                .addBodyParameter("account_number", accountNumber)
                .addBodyParameter("ifsc_code", ifsc)
                .addBodyParameter("description", dsc)

                .addBodyParameter("amount", withDrawamount)
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
                            Log.e("","wallet withdraw amount output json= "+reader.toString());
                            if(status.equals("200")){
//                                reader.getJSONObject("body").getString("wallet_amount")
                                walletInfo.success(msg,"withdraw");
                            }else {
                                walletInfo.error(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            walletInfo.fail("Something went wrong. Please try after some time.");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if(progress!=null)
                        progress.dismiss();
                        walletInfo.fail("Something went wrong. Please try after some time.");
                    }
                });
    }

    public WalletAmountPresenter(Context context, WalletHistroy walletHistory, String diff) {
        this.context = context;
        this.walletHistory = walletHistory;
        appData = new AppData(context);
    }

    public interface WalletHistroy{
        void success(ArrayList<WithdrawhistoryBean> response, String diff);
        void error(String response, String diff);
        void fail(String response, String diff);
    }
    public void GetWalletHistroy() {
        final ArrayList<WithdrawhistoryBean> WithdrawhisList = new ArrayList<>();
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        AndroidNetworking.post(AppData.url)
                .addBodyParameter("action", "withdraw_history")
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
                        WithdrawhisList.clear();
                        try {
                            String status = reader.getString("code");
                            String msg = reader.getString("message");
                            Log.e("","withdraw histroy output json= "+reader.toString());
                            if(status.equals("200")){
                                WithdrawhistoryBean bean;
                                try {
                                    JSONArray jsArrayModel = reader.getJSONArray("body");
                                    if (jsArrayModel != null&&jsArrayModel.length()>0) {
                                        for (int count = 0; count < jsArrayModel.length(); count++) {
                                            JSONObject object = jsArrayModel.getJSONObject(count);
                                            bean = new WithdrawhistoryBean();
                                            String ID = object.getString("ID");
                                            String user_id = object.getString("user_id");
                                            String amount_request = object.getString("amount_request");
                                            String amount_paid = object.getString("amount_paid");
                                            String request_status = object.getString("request_status");
                                            String requested_on = object.getString("requested_on");
                                            String remark = object.getString("remark");
                                            String trans_id = object.getString("trans_id");
                                            String accepted_on = object.getString("accepted_on");
                                            String payment_on = object.getString("payment_on");
                                            String name = object.getString("name");
                                            String account_number = object.getString("account_number");
                                            String ifsc_code = object.getString("ifsc_code");
                                            String description = object.getString("description");

                                            bean.setID(ID);
                                            bean.setUser_id(user_id);
                                            bean.setAmount_request(amount_request);
                                            bean.setAmount_paid(amount_paid);
                                            bean.setTrans_id(trans_id);

                                            bean.setName(name);
                                            bean.setAccount_number(account_number);
                                            bean.setIfsc_code(ifsc_code);
                                            bean.setDescription(description);
                                            bean.setPayment_on(payment_on);
                                            bean.setRequest_status(request_status);

                                            WithdrawhisList.add(bean);
                                        }
                                    }
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                                walletHistory.success(WithdrawhisList,"1");

                            }
                            else {
                                walletHistory.error(msg,"1");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            walletHistory.fail("Something went wrong. Please try after some time.","1");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if(progress!=null)
                            progress.dismiss();
                        walletHistory.fail("Something went wrong. Please try after some time.","1");
                    }
                });
    }
}

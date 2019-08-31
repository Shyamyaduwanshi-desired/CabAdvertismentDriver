package com.diss.cabadvertisementdriver.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


import com.diss.cabadvertisementdriver.R;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class AppData {
    public static String url = "http://cabadvert.webdesigninguk.co/api";
    public static String CAR_BRAND="car_brand";
    public static String CAR_MODEL="car_model_no";
    public static String CAR_REGIS_NO="car_registration_no";
    public static String DRIVER_ADDRESS="driver_add";
    public static String DRIVER_COUNRTY="driver_country";
    public static String DRIVER_STATE="driver_state";
    public static String DRIVER_CITY="driver_city";
    public static String S_ID="campaign_id";
    public static String DIFFS_ID="diff_calling_for_s_id";
    public static String ONLINE_OFFLINE="online_offline";
    public static String ACCEPT_CAMPAIGN_REQUEST="campaign_accept";
    public static String REQUEST_CAMPAIGN_BEAN_OBJ="req_campaign_bean_obj";
    public static String ONLINE_TIME="online_time";
    public static String NOTIFICATION_TYPE="noti_type";
    public static String SET_NOTIFICATION="set_noti";

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

//    serverkey=AIzaSyAsIU15RhLV0vD889uDJqStE_PIYYH1wEw
//    senderid=178687190565

//    public static String image = "";
//    public static String agentID = "";
//
//    public static String AuthUserKey = "Username";
//    public static String AuthUserValue = "Password";
//
//    public static String AuthUserPassKey = "admin";
//    public static String AuthUserPassValue = "admin123";


//    public static final String API_KEY = "c26b952cf256107329b42f97bd9af382";
//    public static final String API_KEY = "AIzaSyDzdTN8o1eYFEnCOF080AA7LN6GxaH-VLc";

    public AppData(Context context) {
        sharedPref = context.getSharedPreferences("cab_advertisement_driver", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public void setUserID(String userID) {
        editor.putString("userID", userID);
        editor.commit();
    }
    public String getUserID() {
        return  sharedPref.getString("userID", "NA");
    }


    public void setMobile(String mobile) {
        editor.putString("mobile", mobile);
        editor.commit();
    }
    public String getMobile() {
        return sharedPref.getString("mobile", "NA");
    }


    public void setEmail(String email) {
        editor.putString("email", email);
        editor.commit();
    }
    public String getEmail() {
        return sharedPref.getString("email", "NA");
    }


    public void setUsername(String username) {
        editor.putString("username", username);
        editor.commit();
    }
    public String getUsername() {
        return sharedPref.getString("username", "NA");
    }

    public String getProfilePic() {
        return sharedPref.getString("user_profile", "NA");
    }

    public void setProfilePic(String profileUrl) {
        editor.putString("user_profile", profileUrl);
        editor.commit();
    }


    public void setCarBrand(String carBrand) {
        editor.putString(CAR_BRAND, carBrand);
        editor.commit();
    }
    public String getCarBrand() {
        return sharedPref.getString(CAR_BRAND, "NA");
    }


    public void setCarModel(String carModel) {
        editor.putString(CAR_MODEL, carModel);
        editor.commit();
    }

    public String getCarModel() {
        return sharedPref.getString(CAR_MODEL, "NA");
    }

    public void setCarRegitrationNo(String carRegistNo) {
        editor.putString(CAR_REGIS_NO, carRegistNo);
        editor.commit();
    }
    public String getCarRegitrationNo() {
        return sharedPref.getString(CAR_REGIS_NO, "NA");
    }

    public void setDriverAdd(String comAreaOfBuss) {
        editor.putString(DRIVER_ADDRESS, comAreaOfBuss);
        editor.commit();
    }
    public String getDriverAdd() {
        return sharedPref.getString(DRIVER_ADDRESS, "NA");
    }
    public void setDriverCountry(String country) {
        editor.putString(DRIVER_COUNRTY, country);
        editor.commit();
    }
    public String getDriverCountry() {
        return sharedPref.getString(DRIVER_COUNRTY, "NA");
    }
    public void setDriverState(String state) {
        editor.putString(DRIVER_STATE, state);
        editor.commit();
    }
    public String getDriverState() {
        return sharedPref.getString(DRIVER_STATE, "NA");
    }
    public void setDriverCity(String country) {
        editor.putString(DRIVER_CITY, country);
        editor.commit();
    }
    public String getDriverCity() {
        return sharedPref.getString(DRIVER_CITY, "NA");
    }


    public void setUserStatus(String usrtype) {
        editor.putString("user_status", usrtype);
        editor.commit();
//   0 = not verify otp &  1 = not approve by admin &  2 = approved by admin
    }

    public String getUserStatus() {
        return  sharedPref.getString("user_status", "NA");
//   0 = not verify otp &  1 = not approve by admin &  2 = approved by admin
    }
    public void setForGotUserId(String usrid) {
        editor.putString("user_forgot", usrid);
        editor.commit();
//        1=vendor,2=manager,3=staff
    }

    public String getForGotUserId() {
        return  sharedPref.getString("user_forgot", "NA");
//        Role 0 = vendor & Role 1 = staff& Role 2 = manager
    }


    public String getS_id() {
        return  sharedPref.getString(S_ID, "0");
    }
    public void sets_Id(String s_id) {
        editor.putString(S_ID, s_id);
        editor.commit();
    }

    public void setDfffS_Id(String s_id) {//for manage s_id from notification or login for show accepted campaign request
        editor.putString(DIFFS_ID, s_id);
        editor.commit();
    }
    public String getDfffS_id() {
        return  sharedPref.getString(DIFFS_ID, "0");
    }

    public void setIsOnline(String onlineOffline) {
        editor.putString(ONLINE_OFFLINE, onlineOffline);
        editor.commit();
//        0 =offline   1 =online
    }
    public String getIsOnline() {
        return  sharedPref.getString(ONLINE_OFFLINE, "0");
    }

    public void setAccepted_request_status(String accept_req_status) {
        editor.putString(ACCEPT_CAMPAIGN_REQUEST, accept_req_status);
        editor.commit();
        //0 not accept,1 accept
    }
    public String getAccepted_request_status() {
        return  sharedPref.getString(ACCEPT_CAMPAIGN_REQUEST, "0");
        //0 not accept,1 accept
    }

    public void setAccepted_request_jsonBean(String req_campaign_bean) {
        editor.putString(REQUEST_CAMPAIGN_BEAN_OBJ, req_campaign_bean);
        editor.commit();
        //0 not accept,1 accept
    }
    public String getAccepted_request_jsonBean() {
        return  sharedPref.getString(REQUEST_CAMPAIGN_BEAN_OBJ, "0");
        //0 not accept,1 accept
    }
    public void setOnlineTime(String req_campaign_bean) {
        editor.putString(ONLINE_TIME, req_campaign_bean);
        editor.commit();
        //0 not accept,1 accept
    }
    public String getOnlineTime() {
        return  sharedPref.getString(ONLINE_TIME, "0");
        //0 not accept,1 accept
    }



    public void setWalletAmount(String walletAmount) {
        editor.putString("walletAmount", walletAmount);
        editor.commit();
    }

    public String getWalletAmount() {
        return  sharedPref.getString("walletAmount", "0");
    }

    public void setReferalCode(String referalcode) {
        editor.putString("referal_code", referalcode);
        editor.commit();
    }

    public String getReferalCode() {
        return  sharedPref.getString("referal_code", "0");
    }


    public void setNotiClick(String setNotivalue) {
        editor.putString(SET_NOTIFICATION, setNotivalue);
        editor.commit();
    }

    public String getNotiClick() {

        return  sharedPref.getString(SET_NOTIFICATION, "0");
    }
    public void setNotiType(String setNotiType) {
        editor.putString(NOTIFICATION_TYPE, setNotiType);
        editor.commit();
    }

    public String getNotiType() {

        return  sharedPref.getString(NOTIFICATION_TYPE, "0");
    }

    public void setCount(int count){
        editor.putInt("count", count);
        editor.commit();
    }

    public int getCount(){
        return sharedPref.getInt("count", -1);
    }

    public void clear(){
        editor.clear();
        editor.commit();
    }

    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


    public void clearData(){
        editor.clear();
        editor.commit();
    }
    PrettyDialog prettyDialog=null;
    public  void ShowNewAlert(Context context,String message) {
        if(prettyDialog!=null)
        {
            prettyDialog.dismiss();
        }
        prettyDialog = new PrettyDialog(context);
        prettyDialog.setCanceledOnTouchOutside(false);
        TextView title = (TextView) prettyDialog.findViewById(libs.mjn.prettydialog.R.id.tv_title);
        TextView tvmessage = (TextView) prettyDialog.findViewById(libs.mjn.prettydialog.R.id.tv_message);
        title.setTextSize(15);
        tvmessage.setTextSize(15);
        prettyDialog.setIconTint(R.color.colorPrimary);
        prettyDialog.setIcon(R.drawable.pdlg_icon_info);
        prettyDialog.setTitle("");
        prettyDialog.setMessage(message);
        prettyDialog.setAnimationEnabled(false);
        prettyDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        prettyDialog.addButton("Ok", R.color.black, R.color.white, new PrettyDialogCallback() {
            @Override
            public void onClick() {
                prettyDialog.dismiss();

            }
        }).show();

//        prettyDialog.addButton("Search again", R.color.black, R.color.white, new PrettyDialogCallback() {
//            @Override
//            public void onClick() {
//                prettyDialog.dismiss();
//
//            }
//        }).show();
    }
//2019-08-02 09:01:35
//2019-06-18 11:17:55 to 18 jun
    public String ConvertDate(String indate)
    {
        String formattedDate = null;
        try {
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("dd MMMM");// hh:mm:ss aa
            Date date = originalFormat.parse(indate);
            formattedDate = targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }
    //    //2019-06-18 to 18 jun
    public String ConvertDate01(String indate)
    {
        String formattedDate = null;
        try {
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("dd MMMM");
            Date date = originalFormat.parse(indate);
            formattedDate = targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    //2019-06-18 11:17:55  to  18 June 2019
    public String ConvertDate02(String indate)
    {
        String formattedDate = null;
        try {
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("dd MMMM yyyy");
            Date date = originalFormat.parse(indate);
            formattedDate = targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

//    //2019-06-18 11:17:55 to 18/06/2019
    public String ConvertDate1(String indate)
    {
        String formattedDate = null;
        try {
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = originalFormat.parse(indate);
            formattedDate = targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }
    //2019-06-18 11:17:55 to June 18 2019
    public String ConvertDate2(String indate)
    {
        String formattedDate = null;
        try {
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("MMMM dd  yyyy");
            Date date = originalFormat.parse(indate);
            formattedDate = targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }
    //    //2019-06-18  to  18 June
    public String ConvertDate3(String indate)
    {
        String formattedDate = null;
        try {
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("dd MMMM");
            Date date = originalFormat.parse(indate);
            formattedDate = targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }
    //    //2019-06-18  to  18 June 2019
    public String ConvertDate4(String indate)
    {
        String formattedDate = null;
        try {
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("dd MMMM yyyy");
            Date date = originalFormat.parse(indate);
            formattedDate = targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    // 2019-06-18 11:17:55  to  18 June 2019 11:17
    public String ConvertDate5(String indate)
    {
        String formattedDate = null;
        try {
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("dd MMMM yyyy hh:mm");
            Date date = originalFormat.parse(indate);
            formattedDate = targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

//   2019-06-18 11:17:55 to 11:17
    public String ConvertTime(String indate)
    {
        String shortTimeStr="";
        try {
            SimpleDateFormat toFullDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date fullDate = toFullDate.parse(indate);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
             shortTimeStr = sdf.format(fullDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return shortTimeStr;
    }

    //   09:08:00 to 11:17 AM
    public String ConvertTime1(String indate)
    {
        String shortTimeStr="";
        try {
            SimpleDateFormat toFullDate = new SimpleDateFormat("HH:mm:ss");
            Date fullDate = toFullDate.parse(indate);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
            shortTimeStr = sdf.format(fullDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return shortTimeStr;
    }



    public boolean isEmailValid(String email) {

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public  boolean isValidPhoneNumber(CharSequence target) {
        if (target == null || target.length() < 10 || target.length() > 12) {
            return false;
        } else {
            Pattern p = Pattern.compile("(0/91)?[6-9][0-9]{9}");
            Matcher m = p.matcher(target)
                    ;
            return (m.find() && m.group().equals(target));
        }
    }
    public  void hideKeyboard(Activity activity) {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public String GetCurrentDateTime()//setOnlineTime
    {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");//yyyy-MM-dd hh:mm:ss a
        String dateToStr = format.format(today);
        System.out.println(dateToStr);
        return dateToStr;
    }
    public String GetCurrentDateTimewithSecond()
    {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//2019-08-30 11:53:14
        String dateToStr = format.format(today);
        System.out.println(dateToStr);
        return dateToStr;
    }
    public String GetCurrentDate()
    {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateToStr = format.format(today);
        System.out.println(dateToStr);
        return dateToStr;
    }

//    yyyy-MM-dd hh:mm:ss
    public String CalculatDiffTime(String onlinetime)
    {

        String currentDateTime=GetCurrentDateTimewithSecond();//yyyy-MM-dd hh:mm:ss

        String format = "yyyy-MM-dd hh:mm:ss";//GetCurrentDateTime,MM/dd/yyyy
        SimpleDateFormat sdf = new SimpleDateFormat(format);
//        DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");
//        int diffhours=0,diffmin=0;
        long elapsedDays=0,elapsedHours=0,elapsedMinutes=0;
        String calculatTime="";
        try {
            Date dateObj1 = sdf.parse(onlinetime);//old
            Date dateObj2 = sdf.parse(currentDateTime);//current
Log.e("","onlinetime= "+onlinetime+" currentDateTime= "+currentDateTime);

            long diff = dateObj2.getTime() - dateObj1.getTime();

//            diffhours = (int) (diff / (60 * 60 * 1000));
//            diffmin = (int) (diff / (60 * 1000));

//            long secondsInMilli = 1000;
//            long minutesInMilli = secondsInMilli * 60;
//            long hoursInMilli = minutesInMilli * 60;
//            long daysInMilli = hoursInMilli * 24;

//             elapsedDays = diff / daysInMilli;
//
//            diff = diff % daysInMilli;
//             elapsedHours = diff / hoursInMilli;
//
//            diff = diff % hoursInMilli;
//             elapsedMinutes = diff / minutesInMilli;

            elapsedDays = TimeUnit.MILLISECONDS.toDays(diff);
            elapsedHours = TimeUnit.MILLISECONDS.toHours(diff);
            elapsedMinutes = TimeUnit.MILLISECONDS.toMinutes(diff);
//            long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diff);


//            diff = diff % minutesInMilli;
//            long elapsedSeconds = diff / secondsInMilli;

//            System.out.println("%d days, %d hours, %d minutes, %d seconds%n",elapsedDays, elapsedHours, elapsedMinutes);
            System.out.printf("%d days, %d hours, %d minutes",
                    elapsedDays, elapsedHours, elapsedMinutes);

//            Log.e("","difference between hours: " + crunchifyFormatter.format(diffhours)+" Minutes= "+ crunchifyFormatter.format(diffmin));


        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(elapsedDays>0)
        {
            calculatTime=elapsedDays+" Days ago";
        }
        else  if(elapsedHours>0)
        {
            calculatTime=elapsedHours+" Hours ago";
        }
        else
        {
            calculatTime=elapsedMinutes+" Minutes ago";
        }


        return calculatTime;

    }

}

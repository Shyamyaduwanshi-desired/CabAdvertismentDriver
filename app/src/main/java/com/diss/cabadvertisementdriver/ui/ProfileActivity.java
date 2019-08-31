package com.diss.cabadvertisementdriver.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.diss.cabadvertisementdriver.R;
import com.diss.cabadvertisementdriver.model.SignupBean;
import com.diss.cabadvertisementdriver.model.UpdateProfileBean;
import com.diss.cabadvertisementdriver.presenter.SignupPresenter;
import com.diss.cabadvertisementdriver.presenter.UpdateProfilePresenter;
import com.hbb20.CountryCodePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener , UpdateProfilePresenter.updateProfile/*, SignupPresenter.CarModel*/ {


//    ImageView imageViewback;
    EditText etDriverNm,/*etCarBrand,etCarRegiNo,etPassword,etConfirmPassword,etDriverAddress,*/etDriverMobile,etEmail;
    CountryCodePicker countryCode;
    private String sDriverNm,sCarBrand/*,sCarModel*/,sCarRegiNo,sMobi,sPassword,sConfirmPassword,sDriverAddress,sEmail,countryCodeValue;

    private UpdateProfilePresenter updatePresenter/*,carModelPresenter*/;
    AppData appdata;

    LinearLayout lyPhoto,lyBottom;
    CircleImageView ivPic;
//    ImageView ivPic;
    boolean editFlag=false;
    TextView tvEditProfile;
    Button btUpdateProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_activity);
        appdata=new AppData(ProfileActivity.this);
        updatePresenter = new UpdateProfilePresenter(ProfileActivity.this, ProfileActivity.this);
        InitCompo();
        Listener();
        SetPreDataAtView();
    }
    public void InitCompo()
    {

        etDriverNm = findViewById(R.id.et_driver_nm);
        etEmail = findViewById(R.id.et_driver_email);
        etDriverMobile = findViewById(R.id.et_driver_phone);
        countryCode=findViewById(R.id.spin_coun_code);
//        imageViewback=findViewById(R.id.imageback);

        lyPhoto=findViewById(R.id.ly_photo);
        lyBottom=findViewById(R.id.ly_bottom);
        tvEditProfile=findViewById(R.id.tv_edit_profile);

        ivPic=findViewById(R.id.profile_pic);
//        ivPic=findViewById(R.id.imagepicker);
        btUpdateProfile=findViewById(R.id.bt_update_profile);

      /*  etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_pass);

        etCarBrand = findViewById(R.id.et_car_brand);
        etCarRegiNo = findViewById(R.id.et_car_registration_no);
        etDriverAddress = findViewById(R.id.et_address);
        tvModelNo = findViewById(R.id.tv_model);
        tvCountry = findViewById(R.id.tv_coutry);
        tvState = findViewById(R.id.tv_state);
        tvCity = findViewById(R.id.tv_city);
        chCondition = findViewById(R.id.cb_condition);*/

    }
    public void SetPreDataAtView()
    {
        etDriverNm.setText(appdata.getUsername());
        etEmail.setText(appdata.getEmail());
        etDriverMobile.setText(appdata.getMobile());
//        countryCode.sele

//        etPersonNm.setText(appdata.getUsername());
//        etPersonContact.setText(appdata.getMobile());

        if(!appdata.getProfilePic().equals("NA")) {
            Glide.with(ProfileActivity.this)
                    .load(appdata.getProfilePic())
//                    .placeholder(R.drawable.ic_user)
                    .into(ivPic);
        }
        SetEditTag(editFlag);
    }
    private void Listener() {
//        imageViewback.setOnClickListener(this);
        lyPhoto.setOnClickListener(this);
        tvEditProfile.setOnClickListener(this);
        btUpdateProfile.setOnClickListener(this);

    }
    public void SetEditTag(boolean checkFlag)
    {
        if(checkFlag)
        {
            etDriverNm.setEnabled(true);
            etEmail.setEnabled(false);
            etDriverMobile.setEnabled(false);
//            etAreaBusiness.setEnabled(true);
//            etPersonNm.setEnabled(true);
//            etPersonContact.setEnabled(false);
//            etCompanyNm.setFocusable(true);
            etDriverNm.setSelection(etDriverNm.getText().toString().length());
            lyBottom.setVisibility(View.VISIBLE);
        }
        else
        {
            etDriverNm.setEnabled(false);
            etEmail.setEnabled(false);
            etDriverMobile.setEnabled(false);
//            etAreaBusiness.setEnabled(false);
//            etPersonNm.setEnabled(false);
//            etPersonContact.setEnabled(false);
            lyBottom.setVisibility(View.GONE);
        }

    }

    static  int CAMERA_REQUEST=3,Result_Load_Image=4;
    int MY_CAMERA_PERMISSION_CODE=11;
    private void selectImage1() {//,"Choose pdf"
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Select Profile");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_CAMERA_PERMISSION_CODE);
                    } else {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");//only for image
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select File"), Result_Load_Image);

                }
                else if (items[item].equals("Choose pdf")) {
                  /*  new MaterialFilePicker()
                            .withActivity(DetailsActivity.this)
                            .withRequestCode(1)
                            .withHiddenFiles(true)
                            .withTitle("Sample title")
                            .start();*/

                }

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    String uploadBase64="";
    String filePath="",fileNm="";
    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Result_Load_Image && data != null && data.getData() != null) {
                Uri filePath2 = data.getData();

                try {
                    Bitmap bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath2);
                    Bitmap bit=Bitmap.createScaledBitmap(bitmap,150,150,false);
                    Uri selectedImage = getImageUri(this, bit);
                    filePath = getPath(selectedImage);
                    File f = new File(filePath);
                    fileNm = f.getName();
                    uploadBase64=getFileToBase64_1(f);
//                    ivPic.setImageBitmap(bitmap);
//                    ivPic.setImageBitmap(BitmapFactory.decodeFile(filePath));//getting problem into show bitmap
                    Log.e(""," uploadBase64= "+uploadBase64);
//                    Log.e("","shyam photo size11= "+f.length()+" uploadBase64= "+uploadBase64);

                    Glide.with(this)
                            .load(new File(filePath))
//                            .placeholder(R.mipmap.ic_launcher)
                            .into(ivPic);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Bitmap bit=Bitmap.createScaledBitmap(photo ,150,150,false);
                Uri selectedImage = getImageUri(getApplicationContext(), bit);
                filePath = getPath(selectedImage);
                File f = new File(filePath);
                fileNm = f.getName();
                uploadBase64=getFileToBase64_1(f);
//                ivPic.setImageBitmap(bit);//getting problem into show bitmap

                Glide.with(this)
                        .load(new File(filePath))
//                            .placeholder(R.mipmap.ic_launcher)
                        .into(ivPic);
                Log.e("","shyam file size11= "+f.length()+" uploadBase64= "+uploadBase64);


            }
            else if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

                File f  = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
                filePath = f.getAbsolutePath();

                File f1 = new File(filePath);
                fileNm = f1.getName();
//                tvFileNm.setText(fileNm);
//                uploadBase64=getFileToBase64(filePath);

//                firstcamera.setImageBitmap(bit);
                Log.e("","shyam file size22= "+f.length()+" uploadBase64= "+uploadBase64);
            }
            else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
    public String getFileToBase64_1(File f) {
        InputStream inputStream = null;
        String encodedFile= "", lastVal;
        try {
            inputStream = new FileInputStream(f.getAbsolutePath());

            byte[] buffer = new byte[10240];//specify the size to allow
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);
            }
            output64.close();
            encodedFile =  output.toString();
        }
        catch (FileNotFoundException e1 ) {
            e1.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        lastVal = encodedFile;
        return lastVal;
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    public void addValidationToViews(){
        sDriverNm=etDriverNm.getText().toString().trim();
        sEmail=etEmail.getText().toString().trim();
        sMobi=etDriverMobile.getText().toString().trim();
        countryCodeValue=countryCode.getSelectedCountryCode();

//        sPassword=etPassword.getText().toString().trim();
//        sConfirmPassword=etConfirmPassword.getText().toString().trim();
//        sCarBrand=etCarBrand.getText().toString().trim();
//        sCarRegiNo=etCarRegiNo.getText().toString().trim();
//        sDriverAddress=etDriverAddress.getText().toString().trim();

        if(TextUtils.isEmpty(sDriverNm)){
            etDriverNm.setError("Enter Your Name");
            etDriverNm.requestFocus();
            return;
        }
        else   if (TextUtils.isEmpty(sEmail)) {
            etEmail.setError("Please Enter Your Email");
            etEmail.requestFocus();
            return;
        }
        else  if (!appdata.isEmailValid(sEmail)) {
            etEmail.setError("Enter a Valid Email");
            etEmail.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(sMobi)) {
            etDriverMobile.setError("Please Enter Your Mobile Number");
            etDriverMobile.requestFocus();
            return;
        }
        else  if(sMobi.length()<10){//!appdata.isValidPhoneNumber(sMobi)
            etDriverMobile.setError("Enter a valid Mobile Number");
            etDriverMobile.requestFocus();
            return;
        }
        else {

                if (appdata.isNetworkConnected(this)) {
                   /* if(TextUtils.isEmpty(uploadBase64))
                    {
                        fileNm="";
                    }*/
                    UpdateProfileBean bean=new UpdateProfileBean();
                    bean.setDriver_name(sDriverNm);
//                    bean.setEmail("");//sEmail
                    bean.setProfile_pic(uploadBase64);
                    bean.setPic_name(fileNm);

//                    bean.setMobile_no(("+"+countryCodeValue+sMobi));

//                    bean.setPassword(sPassword);
//                    bean.setConfirm_password(sConfirmPassword);
//                    bean.setCar_brand(sCarBrand);
//                    bean.setCar_model(sModelId);
//                    bean.setCar_register_no(sCarRegiNo);
//                    bean.setDriver_add(sDriverAddress);
//
//                    bean.setDrive_country(sCountryId);
//                    bean.setDrive_state(sStateId);
//                    bean.setDriver_city(sCityId);

                    Log.e("","update bean= "+bean.toString());
                    updatePresenter.updateProfileData(bean);
                } else {
                    appdata.ShowNewAlert(this, "Please connect to internet");
                }

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bt_update_profile:
                addValidationToViews();
                break;

            case R.id.ly_photo:
                if(editFlag) {
                    selectImage1();
                }
                else
                {
                    uploadBase64="";//for empty data
                }
                break;
            case R.id.tv_edit_profile:
                if(!editFlag) {
                    editFlag=true;
                }
                else
                {
                    editFlag=false;
                }

                SetEditTag(editFlag);
                break;
            case R.id.imageback:
                finish();
                Animatoo.animateSlideDown(ProfileActivity.this);
//                ShowAlertCityDlg();
                break;
        }
    }
//update profile
    @Override
    public void success(String response, String status) {
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        finish();
        Animatoo.animateSlideDown(ProfileActivity.this);
    }

    @Override
    public void error(String response) {
        appdata.ShowNewAlert(this,response);
    }

    @Override
    public void fail(String response) {
        appdata.ShowNewAlert(this,response);
    }
}


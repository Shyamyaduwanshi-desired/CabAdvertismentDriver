package com.diss.cabadvertisementdriver.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.diss.cabadvertisementdriver.R;
import com.diss.cabadvertisementdriver.adapter.SelectedImageAdapter;
import com.diss.cabadvertisementdriver.adapter.TimeLineAdapter;
import com.diss.cabadvertisementdriver.adapter.UploadImageAdapter;
import com.diss.cabadvertisementdriver.model.DriverCampaignBean;
import com.diss.cabadvertisementdriver.model.ImageBean;
import com.diss.cabadvertisementdriver.model.TimeLineModel;
import com.diss.cabadvertisementdriver.presenter.DriverCampaignPresenter;
import com.diss.cabadvertisementdriver.presenter.VerifyCampaignDataPresenter;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MyCompaignDetailActivity1 extends AppCompatActivity implements TimeLineAdapter.CompaignDetailClick,View.OnClickListener, DriverCampaignPresenter.CampaingListData ,SelectedImageAdapter.ImageClick,UploadImageAdapter.UploadImageClick , VerifyCampaignDataPresenter.CampaignData {
    RecyclerView recyclerView;
    String[] name = {"Campaign Request Accepted", "Receive Location", "Campaign Stickers Received"/*,"Campaign Sticker Uploaded on Vehicle"*/, "Upload Photo of Vehicle with Stickers","Receive Payment","Ready to Go!","Campaign Start","Campaign Completed","Feedback"};
    String[] status = {"active", "inactive", "inactive","inactive","inactive","inactive","inactive","inactive","inactive"/*,"inactive"*/};

    List<TimeLineModel> timeLineModelList;
    TimeLineModel[] timeLineModel;
    Context context;
    LinearLayoutManager linearLayoutManager;
    int countItem=1;
    LinearLayout categorylinear,timelinelinear,lyMain;
    TimeLineAdapter adapter;
    private DriverCampaignPresenter presenter;
    private VerifyCampaignDataPresenter VerifyCamDatapresenter;
    AppData appdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_compaigns_details_activity);
        appdata=new AppData(MyCompaignDetailActivity1.this);
        presenter = new DriverCampaignPresenter(MyCompaignDetailActivity1.this, MyCompaignDetailActivity1.this);
        VerifyCamDatapresenter = new VerifyCampaignDataPresenter(MyCompaignDetailActivity1.this, MyCompaignDetailActivity1.this);
        InitCompo();
        Listener();
        GetDriverCampaingData();

    }

    private void GetDriverCampaingData() {
        if(appdata.isNetworkConnected(this)){
            presenter.GetDriverCampaignList();
        }else {
            appdata.ShowNewAlert(this,"Please connect to internet");
        }
    }

    private void Listener() {
        ivBack.setOnClickListener(this);
        lyMyCampaign.setOnClickListener(this);

    }
ImageView ivBack;
    TextView tvCampaignNm,tvCampaignAmt,tvValidityDate,tvNoData;
    LinearLayout lyMyCampaign;
    public void InitCompo()
    {
        tvCampaignNm=findViewById(R.id.tv_compaign_name);
        tvCampaignAmt=findViewById(R.id.tv_compaign_amount);
        tvValidityDate=findViewById(R.id.tv_validity);

        tvNoData=findViewById(R.id.tv_no_data);

        ivBack=findViewById(R.id.imageback);
        categorylinear=(LinearLayout)findViewById(R.id.category_linear_id);
        timelinelinear=(LinearLayout)findViewById(R.id.timeline_linear_id);
        lyMyCampaign=findViewById(R.id.mycampaigns_linear_id);
        lyMain=findViewById(R.id.ly_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_recommennd);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        categorylinear.requestFocus();

    }
    public void setAdapter()
    {
        timeLineModelList = new ArrayList<>();
        int size = name.length;
        timeLineModel = new TimeLineModel[size];
        context = MyCompaignDetailActivity1.this;
        linearLayoutManager = new LinearLayoutManager(this);

        for (int i = 0; i < size; i++) {
            timeLineModel[i] = new TimeLineModel();
            timeLineModel[i].setName(name[i]);
            timeLineModel[i].setStatus(status[i]);
            timeLineModelList.add(timeLineModel[i]);
        }
         adapter= new TimeLineAdapter(context, timeLineModelList, MyCompaignDetailActivity1.this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imageback:
//                finish();
                onBackPressed();
                break;
            case R.id.mycampaigns_linear_id:
              //when campaing complete then set this value
                appdata.setAccepted_request_status("0");
                appdata.setAccepted_request_jsonBean("");
                break;
        }
    }

int RecyclePosti=0;
    @Override
    public void CompaignOnClick(int position, int diff) {
        RecyclePosti=position;
         if(position==(countItem))
         {
             switch (position) {
                 case 1://Recieve Location,"Location: palasiya"
                     showRecievLocDlg(position, "Recieve Location","Location: "+ arDriverCampaign.get(0).getC_l_name());//arDriverCampaign.get(0)
                     break;
                 case 2://Campaign Stickers Recieved
                     showRecievStickerlg(position);//
                     break;
                 case 3://Campaign Sticker Uploaded on Vehicle
                     showRecievLocDlg(position, "Campaign Sticker Uploaded on Vehicle", "");
                     break;
                 case 4://Upload Photo of Vehicle with Stickers
                     showUploadStickerDlg(position);//
                     break;
                 case 5://Recieve Payment
                     String hireamount="0";
                    if(!TextUtils.isEmpty(arDriverCampaign.get(0).getDriverHireAmount()))
                      {
                         hireamount=arDriverCampaign.get(0).getDriverHireAmount();
                      }

                     showRecievLocDlg(position, "Recieve Payment", "Amount: Rs."+hireamount);
                     break;
                 case 6://Ready to Go! status =9
                     showRecievLocDlg(position, "Ready to Go!", "");
                     break;
                 case 7://Campaign Start
                     showRecievLocDlg(position, "Please wait for start campaign from company", "");//this is automatically start via company
                     break;
                case 8://Campaign Completed
                    showRecievLocDlg(position, "Campaign Completed", "");
                     break;
                case 9://Feedback
                    DriverFeedbacDlg(position);
                     break;
             }
         }
    }
     Dialog VerifyCamDatadialog;
    public void showRecievLocDlg(final int position, final String title, String loc){
         VerifyCamDatadialog = new Dialog(this);
        VerifyCamDatadialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        VerifyCamDatadialog.setCancelable(false);
        VerifyCamDatadialog.setContentView(R.layout.dialog_recieve_loc);
        TextView TvLocNmTitle=VerifyCamDatadialog.findViewById(R.id.tv_loc_txt);
        TextView TvLocNm=VerifyCamDatadialog.findViewById(R.id.tv_loc);
        TextView tvYes=VerifyCamDatadialog.findViewById(R.id.tv_yes);
        TextView tvNo=VerifyCamDatadialog.findViewById(R.id.tv_no);
        TvLocNmTitle.setText(title);
        if(TextUtils.isEmpty(loc))
        {
            TvLocNm.setVisibility(View.GONE);
        }
        else
        {
            TvLocNm.setVisibility(View.VISIBLE);
            TvLocNm.setText(loc);
        }

        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(appdata.isNetworkConnected(MyCompaignDetailActivity1.this)){
                        switch (title)
                        {
                            case"Ready to Go!":
                                VerifyCamDatapresenter.DriverReadyToGo(arDriverCampaign.get(0).getCompaignId());//countItem+3
                                break;

                            case"Recieve Payment":
                                VerifyCamDatapresenter.DriverPaymentRecieved(arDriverCampaign.get(0).getCompaignId());//countItem+3
                                break;
                            case"Please wait for start campaign from company":
                                VerifyCamDatadialog.dismiss();
                                break;

                                default:
                                    VerifyCamDatapresenter.DriverRecieveCampaingData(arDriverCampaign.get(0).getUser_id(), arDriverCampaign.get(0).getCompaignId(), String.valueOf(countItem + 2));//countItem + 3
                                    break;
                        }

                }else {
                    appdata.ShowNewAlert(MyCompaignDetailActivity1.this,"Please connect to internet");
                }
            }
        });
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifyCamDatadialog.dismiss();
            }
        });
        VerifyCamDatadialog.show();
    }
    RecyclerView rvImage;
     Dialog stickerDlg;
    public void showRecievStickerlg(final int position){
        if(stickerDlg!=null)
        {
            stickerDlg=null;
        }
        stickerDlg = new Dialog(this);
        stickerDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        stickerDlg.setCancelable(false);
        stickerDlg.setContentView(R.layout.dialog_recieve_sticker);
        TextView tvYes=stickerDlg.findViewById(R.id.tv_yes);
        TextView tvNo=stickerDlg.findViewById(R.id.tv_no);
        rvImage = stickerDlg.findViewById(R.id.rv_image_list);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvImage.setLayoutManager(mLayoutManager1);
        rvImage.setItemAnimator(new DefaultItemAnimator());

        SetImageAdapter();
        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(appdata.isNetworkConnected(MyCompaignDetailActivity1.this)){
                    if(stickerDlg!=null)
                    {
                        stickerDlg.dismiss();
                    }
                    VerifyCamDatapresenter.DriverRecieveCampaingData(arDriverCampaign.get(0).getUser_id(),arDriverCampaign.get(0).getCompaignId(), String.valueOf(countItem+3));

                }else {
                    appdata.ShowNewAlert(MyCompaignDetailActivity1.this,"Please connect to internet");
                }
            }
        });
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stickerDlg.dismiss();
            }
        });
        stickerDlg.show();
    }
    ArrayList<ImageBean>arImageNm=new ArrayList<>();
    private RecyclerView.Adapter mImgAdapter;
    public void SetImageAdapter()
    {
        int size=arImageNm.size();
        Log.e("","size= "+size);
        mImgAdapter = new SelectedImageAdapter(MyCompaignDetailActivity1.this,arImageNm, MyCompaignDetailActivity1.this);
        rvImage.setAdapter(mImgAdapter);

    }

//sticker adapter
    @Override
    public void PhotonClick(int position, int diff) {
        Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
    }

    RecyclerView rvUploadImage;
     Dialog upLoadVichleWithStickerDlg;
    public void showUploadStickerDlg(final int position){
       upLoadVichleWithStickerDlg = new Dialog(this);
        upLoadVichleWithStickerDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        upLoadVichleWithStickerDlg.setCancelable(false);
        upLoadVichleWithStickerDlg.setContentView(R.layout.dialog_upload_sticker);
        LinearLayout lyAddPhoto=upLoadVichleWithStickerDlg.findViewById(R.id.ly_add_photo);
        TextView tvUpload=upLoadVichleWithStickerDlg.findViewById(R.id.tv_reload);
        TextView tvYes=upLoadVichleWithStickerDlg.findViewById(R.id.tv_yes);
        TextView tvNo=upLoadVichleWithStickerDlg.findViewById(R.id.tv_no);
        rvUploadImage = upLoadVichleWithStickerDlg.findViewById(R.id.rv_upload_image_list);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvUploadImage.setLayoutManager(mLayoutManager1);
        rvUploadImage.setItemAnimator(new DefaultItemAnimator());

        SetUploadImageAdapter();
        lyAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage1();
            }
        });
        tvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arUploadImage.clear();
                mUploadImgAdapter.notifyDataSetChanged();
            }
        });
        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arUploadImage.size()>0) {


                    if(appdata.isNetworkConnected(MyCompaignDetailActivity1.this)){

                        JSONArray jsonArrayImage = new JSONArray();
                        for(int i=0;i<arUploadImage.size();i++) {
                            JSONObject locObj = new JSONObject();
                            try {
//                                locObj.put("data", arUploadImage.get(i).getData());
                                locObj.put("profile_pic", arUploadImage.get(i).getData());
                                locObj.put("name", arUploadImage.get(i).getName());
                                jsonArrayImage.put(locObj);

                            } catch (JSONException e) {

                                e.printStackTrace();
                            }
                        }
              Log.e("","arDriverCampaign.get(0).getCompaignId()= "+arDriverCampaign.get(0).getCompaignId()+""+jsonArrayImage.toString());

                        VerifyCamDatapresenter.UploadPhotoVechileWithSticker(arDriverCampaign.get(0).getCompaignId(),jsonArrayImage);
                    }else {
                        appdata.ShowNewAlert(MyCompaignDetailActivity1.this,"Please connect to internet");
                    }
                }
                else
                {
                    Toast.makeText(context, "Please select atleast one Image", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upLoadVichleWithStickerDlg.dismiss();
            }
        });
        upLoadVichleWithStickerDlg.show();
    }

    RatingBar rtBar;
    EditText etFeedbackDsc;
    Dialog feedBackDlg;
    public void DriverFeedbacDlg(final int position){
        feedBackDlg = new Dialog(this);
        feedBackDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        feedBackDlg.setCancelable(false);
        feedBackDlg.setContentView(R.layout.dailog_driver_feedback);
        RelativeLayout rlClose=feedBackDlg.findViewById(R.id.rl_close);
         rtBar=feedBackDlg.findViewById(R.id.ratingBar);
         etFeedbackDsc=feedBackDlg.findViewById(R.id.et_feedback_dsc);
        Button btSubmit=feedBackDlg.findViewById(R.id.bt_submit_feedbac);

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(ValidFeedback()) {

                     if(appdata.isNetworkConnected(MyCompaignDetailActivity1.this)){
                         VerifyCamDatapresenter.DriverFeedback(arDriverCampaign.get(0).getCompaignId(),String.valueOf(feebackRatting),sfeedbackDsc);

                     }else {
                         appdata.ShowNewAlert(MyCompaignDetailActivity1.this,"Please connect to internet");
                     }
                 }
            }
        });
        rlClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                feedBackDlg.dismiss();
            }
        });
        feedBackDlg.show();
    }
 String sfeedbackDsc="";
    float feebackRatting=0;
    private boolean ValidFeedback() {
        boolean feedbackFlag=true;
        sfeedbackDsc=etFeedbackDsc.getText().toString().trim();
        feebackRatting = rtBar.getRating();
        if(feebackRatting==0||feebackRatting==0.0)
        {
            Toast.makeText(context, "Please Rate this App", Toast.LENGTH_SHORT).show();
            feedbackFlag=false;
        }
        else if(TextUtils.isEmpty(sfeedbackDsc))
        {
            etFeedbackDsc.setError("Please Enter Feedback Description");
            etFeedbackDsc.requestFocus();
            feedbackFlag=false;
        }
        return feedbackFlag;
    }

    ArrayList<ImageBean>arUploadImage=new ArrayList<>();
    private RecyclerView.Adapter mUploadImgAdapter;
    public void SetUploadImageAdapter()
    {
        int size=arUploadImage.size();
        Log.e("","size= "+size);
        mUploadImgAdapter = new UploadImageAdapter(MyCompaignDetailActivity1.this,arUploadImage, MyCompaignDetailActivity1.this);
        rvUploadImage.setAdapter(mUploadImgAdapter);

    }
//upload adapter click
    @Override
    public void UploadPhotoClick(int position, int diff) {
        Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
    }


    static  int CAMERA_REQUEST=3,Result_Load_Image=4;
    int MY_CAMERA_PERMISSION_CODE=11;
    private void selectImage1() {//,"Choose pdf"
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MyCompaignDetailActivity1.this);
        builder.setTitle("Add File");
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Result_Load_Image && data != null && data.getData() != null) {
                Uri filePath2 = data.getData();

                try {
                    Bitmap bitmap= MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), filePath2);
                    Bitmap bit=Bitmap.createScaledBitmap(bitmap,150,150,false);

                    Uri selectedImage = getImageUri(getApplicationContext(), bit);

                    filePath = getPath(selectedImage);
                    File f = new File(filePath);
                    fileNm = f.getName();
                    uploadBase64=getFileToBase64_1(f);
                    Log.e("","shyam photo size11= "+f.length()+" uploadBase64= "+uploadBase64);
                    if(arUploadImage.size()<5) {

                        ImageBean bean=new ImageBean();
                        bean.setData(uploadBase64);
                        bean.setName(fileNm);
                        bean.setImagePath(filePath);
                        arUploadImage.add(bean);
                        mUploadImgAdapter.notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(this, "Max file only 5", Toast.LENGTH_SHORT).show();
                    }
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
                Log.e("","shyam file size11= "+f.length()+" uploadBase64= "+uploadBase64);
                if(arUploadImage.size()<5) {
                    ImageBean bean=new ImageBean();
                    bean.setData(uploadBase64);
                    bean.setName(fileNm);
                    bean.setImagePath(filePath);
                    arUploadImage.add(bean);

//                    SetUploadImageAdapter();
                    mUploadImgAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(this, "Max file only 5", Toast.LENGTH_SHORT).show();
                }

            }
            else if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

                File f  = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
                filePath = f.getAbsolutePath();

                File f1 = new File(filePath);
                fileNm = f1.getName();
                uploadBase64=getFileToBase64_1(f);
                if(arUploadImage.size()<5) {
                    ImageBean bean=new ImageBean();
                    bean.setData(uploadBase64);
                    bean.setName(fileNm);
                    bean.setImagePath(filePath);
                    arUploadImage.add(bean);
                    mUploadImgAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(this, "Max file only 5", Toast.LENGTH_SHORT).show();
                }
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
    ArrayList<DriverCampaignBean> arDriverCampaign=new ArrayList<>();
    @Override
    public void success(ArrayList<DriverCampaignBean> response, String status) {
//        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        arDriverCampaign.clear();
        arDriverCampaign=response;
        setViewData();
    }


    @Override
    public void error(String response) {
        tvNoData.setVisibility(View.VISIBLE);
            appdata.ShowNewAlert(this,response);
    }

    @Override
    public void fail(String response) {
        tvNoData.setVisibility(View.VISIBLE);
        appdata.ShowNewAlert(this,response);
    }
    private void setViewData() {
            if(arDriverCampaign.size()>0)
            {
                tvCampaignNm.setText("Name of Campaign: "+arDriverCampaign.get(0).getsCampaignNm());//getC_l_name
                tvCampaignAmt.setText("Rs. "+arDriverCampaign.get(0).getDriverHireAmount());
                tvValidityDate.setText("( "+appdata.ConvertDate(arDriverCampaign.get(0).getAdded_on())+" - "+appdata.ConvertDate01(arDriverCampaign.get(0).getLastdate())+" )");//tvValidityDate

                lyMain.setVisibility(View.VISIBLE);
                tvNoData.setVisibility(View.GONE);
//                countItem=1;
                int setPos=Integer.parseInt(arDriverCampaign.get(0).getSub_status());
                if(setPos==13)
                {//reupload photo vehicle with sticker  status =13 recycle positin=4
                    countItem = Integer.parseInt(arDriverCampaign.get(0).getSub_status()) - 9;
                }
               /* else if(setPos==12)
                {//Ready to Go! status =12 recycle positin=9
                    countItem = Integer.parseInt(arDriverCampaign.get(0).getSub_status()) - 2;
                }*/
                else  if(setPos>=4)
                {// status =4 recycle positin=2
                    countItem = Integer.parseInt(arDriverCampaign.get(0).getSub_status()) - 2;
                }
                else {
//                    status =2 recycle positin=1
                    countItem = Integer.parseInt(arDriverCampaign.get(0).getSub_status()) - 1;//initial position 2(accepted request)
                }
                 Log.e("","countItem= "+countItem);
                for(int i=0;i<countItem;i++)
                {
                    status[i]="active";
                }
                setAdapter();

                try {
                    JSONArray jsArray = new JSONArray(arDriverCampaign.get(0).getImage());
                    for(int i=0;i<jsArray.length();i++)
                              {
                                  ImageBean bean=new ImageBean();
                                  bean.setData("");
                                  bean.setName("");
                                  bean.setImagePath(jsArray.get(i).toString());
                                  arImageNm.add(bean);
                              }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else
            {
                countItem=0;
                lyMain.setVisibility(View.GONE);
                tvNoData.setVisibility(View.VISIBLE);
            }

    }


    //for campaign data verification
    @Override
    public void success(String response, String status) {
        switch (status)
        {
//            case "feedback":
////                VerifyCamDatapresenter.DriverRecieveCampaingData(arDriverCampaign.get(0).getUser_id(),arDriverCampaign.get(0).getCompaignId(), String.valueOf(countItem+2));
//
//                 if(upLoadVichleWithStickerDlg!=null)
//                 {
//                     upLoadVichleWithStickerDlg.dismiss();
//                 }
//
//                if(feedBackDlg!=null)
//                {
//                    feedBackDlg.dismiss();
//                }
//
//                break;

            case "PhotoVechileWithSticker":
                if(upLoadVichleWithStickerDlg!=null)
                {
                    upLoadVichleWithStickerDlg.dismiss();
                }
                appdata.ShowNewAlert(this,"Photo upload successfully. please wait for comapany approval");
                break;

            case "feedback":
            case "ReadyToGo"://(this status update via company after status=9)
            case "DriverPaymentRecieved":
            case "VerifyCampaign"://for show view
                UpdateViewData();
                break;
        }
    }

    private void UpdateViewData() {

        if(VerifyCamDatadialog!=null) {
            VerifyCamDatadialog.dismiss();
        }
        if(stickerDlg!=null)
        {
            stickerDlg.dismiss();
        }

        if(upLoadVichleWithStickerDlg!=null)
        {
            upLoadVichleWithStickerDlg.dismiss();
        }
        if(feedBackDlg!=null)
        {
            feedBackDlg.dismiss();
        }
//        if(RecyclePosti==6)//ReadyToGo not go to next text
//        {
//
//        }
//        else
//        {
//            RecyclePosti=RecyclePosti+1;
//            timeLineModel[RecyclePosti].setStatus("active");
//            adapter.notifyDataSetChanged();
//        }


        switch (RecyclePosti)
        {
            case 1:
                countItem=2;
                break;
            case 2:
                countItem=3;
                break;
            case 3:
                countItem=4;
                break;
            case 4:
                countItem=5;
                break;
            case 5:
                countItem=6;
                break;
            case 6://ReadyToGo
                countItem=7;
                break;
            case 7:
                countItem=8;
                break;
            case 8:
                countItem=9;
                break;
           case 9:
                countItem=10;
                break;
        }
//        RecyclePosti=RecyclePosti+1;
            timeLineModel[RecyclePosti].setStatus("active");
            adapter.notifyDataSetChanged();

    }


    //for campaign data verification
    @Override
    public void error(String response,String status) {
        appdata.ShowNewAlert(this,response);
    }
    //for campaign data verification
    @Override
    public void fail(String response,String status) {
        appdata.ShowNewAlert(this,response);
    }


    @Override
    public void onBackPressed() {
        appdata.setNotiClick("0");
        appdata.setNotiType("0");
        Intent intent = new Intent(this, DashBoardActivity.class);
        startActivity(intent);
        finish();
        Animatoo.animateSlideUp(this);
    }
}
package com.example.shunkapos.demo;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.shunkapos.R;
import com.example.shunkapos.base.BaseActivity;
import com.example.shunkapos.cos.CosServiceFactory;
import com.example.shunkapos.demo.demobean.MAreaBean;
import com.example.shunkapos.demo.demobean.MCityBean;
import com.example.shunkapos.demo.demobean.MProvinceBean;
import com.example.shunkapos.demo.demobean.ThreeLabelBean;
import com.example.shunkapos.demo.demobean.TitleBean;
import com.example.shunkapos.demo.demobean.TwoLabelBean;
import com.example.shunkapos.demo.model.ProvinceView;
import com.example.shunkapos.net.HttpRequest;
import com.example.shunkapos.net.OkHttpException;
import com.example.shunkapos.net.RequestParams;
import com.example.shunkapos.net.ResponseCallback;
import com.example.shunkapos.utils.SPUtils;
import com.example.shunkapos.utils.TimeUtils;
import com.example.shunkapos.utils.Utility;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlProgressListener;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.transfer.COSXMLUploadTask;
import com.tencent.cos.xml.transfer.TransferConfig;
import com.tencent.cos.xml.transfer.TransferManager;
import com.tencent.cos.xml.transfer.TransferState;
import com.tencent.cos.xml.transfer.TransferStateListener;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * ??????: qgl
 * ???????????????2022/6/15
 * ??????:???????????????2
 */
public class NewDemoActivity02 extends BaseActivity implements View.OnClickListener {
//    private TextView merchants_use_type;
    private TextView province_tv;
    private List<String> merchantUseList;
    private OptionsPickerView reasonPicker;
    private OptionsPickerView settlementPicker;
    private List<String> settlementList;
    private String serviceType = "";
    private ProvinceView provinceView;
    private TextView settlement_type;
    private Button submit_bt;
    private RelativeLayout certImageBack_relative;
    private RelativeLayout certImage_relative;
    private LinearLayout iv_back;
    private SimpleDraweeView inDoorImages_iv;
    private SimpleDraweeView outDoorImages_iv;
    private SimpleDraweeView certImage_iv;
    private SimpleDraweeView certImageBack_iv;
    private CosXmlService cosXmlService;
    private TransferManager transferManager;
    private COSXMLUploadTask cosxmlTask;
    private String folderName = "";
    private int INDOOR = 10062;
    private int OUTDOOR = 10063;
    private int CERT = 10064;
    private int CERTBACK = 10065;
    private String token = "eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6IjY1MDQ5NzhmLTg5MWYtNDBlYy1iYWVhLTRmYjRjZTJmYzBiZiJ9.x8hak7RLm4vUcpuBbCra0auVdvOJn55ipTvkuW0MHZrDFG62-0ua0L9Wsiawqlgxgl4mVvUbbDDG5Euf_k8jCQ";
    private String bucketName = "shunka-1303987307";
    private String secretKey = "UouqnWDf2A1cyKn46tQm00whEYvDJI2Z";
    private String secretId = "AKIDvnxzoQIz8FAe2BfJHHRFo8Fn45rMAdQz";
    private EditText contact_name_ed;
    private EditText contact_phone_ed;
    private EditText contact_adr_ed;
    private EditText alipayLogonId_ed;
//    private EditText bindingAlipayLogonId_ed;
    /***********?????????************/
    //????????????
    private String merchantType = "";
    // ???????????????-????????????
    private String mccStatus = "";
    //???????????????
    private String mcc = "";
    //????????????
    private String name = "";
    //????????????
    private String aliasName = "";
    //??????????????????
    private String certNo = "";
    //??????????????????????????????????????????(???????????????????????????????????????????????????)
    private String certName = "";
    //????????????(???????????????????????????)
    private String legalName = "";
    //??????????????????(???????????????????????????)
    private String legalCertNo = "";
    //?????????????????????(??????????????????)
    private String legalCertFrontImage = "";
    //?????????????????????(??????????????????)????????????
    private String legalCertFrontImageAdd = "";
    //?????????????????????(??????????????????)
    private String legalCertBackImage = "";
    //?????????????????????(??????????????????)????????????
    private String legalCertBackImageAdd = "";
    //????????????????????????(??????????????????)
    private String industryQualificationType = "";
    //????????????????????????(??????????????????)
    private String industryQualificationImage = "";
    //????????????????????????(??????????????????)????????????
    private String industryQualificationImageAdd = "";
    private String certType = "";
    /***********?????????************/
    /***********???2???************/
    // ????????? ??????????????? ????????????ant.merchant.expand.indirect.image.upload???????????????????????????oss key???
    private String inDoorImages;
    // ????????????
    private String inDoorImagesAdd;
    // ????????? ??????????????? ????????????ant.merchant.expand.indirect.image.upload???????????????????????????oss key???
    private String outDoorImages;
    // ????????????
    private String outDoorImagesAdd;
    // ??????????????????URL
    private String certImage;
    // ????????????
    private String certImageAdd;
    // ????????????????????????
    private String certImageBack;
    // ????????????
    private String certImageBackAdd;
    // ????????????
    private String provinceCode;
    // ????????????
    private String cityCode;
    // ????????????
    private String districtCode;
    private String siteType = "02";

    /***********???2???************/

    @Override
    protected int getLayoutId() {
        // ?????????????????????
        statusBarConfig(R.color.new_theme_color, false).init();
        return R.layout.new_demo_activity02;
    }

    @Override
    protected void initView() {
        //????????????????????????
        cosXmlService = CosServiceFactory.getCosXmlService(this, "ap-beijing", secretId, secretKey, true);
        TransferConfig transferConfig = new TransferConfig.Builder().build();
        transferManager = new TransferManager(cosXmlService, transferConfig);
        folderName = "quote" + "/" + "zfb" + "/android/" + TimeUtils.getNowTime("day");
        merchantType = getIntent().getStringExtra("merchantType");
        mccStatus = getIntent().getStringExtra("mccStatus");
        name = getIntent().getStringExtra("name");
        mcc = getIntent().getStringExtra("mcc");
        aliasName = getIntent().getStringExtra("aliasName");
        certNo = getIntent().getStringExtra("certNo");
        certName = getIntent().getStringExtra("certName");
        legalName = getIntent().getStringExtra("legalName");
        legalCertNo = getIntent().getStringExtra("legalCertNo");
        legalCertFrontImage = getIntent().getStringExtra("legalCertFrontImage");
        legalCertFrontImageAdd = getIntent().getStringExtra("legalCertFrontImageAdd");
        legalCertBackImage = getIntent().getStringExtra("legalCertBackImage");
        legalCertBackImageAdd = getIntent().getStringExtra("legalCertBackImageAdd");
        industryQualificationType = getIntent().getStringExtra("industryQualificationType");
        industryQualificationImage = getIntent().getStringExtra("industryQualificationImage");
        industryQualificationImageAdd = getIntent().getStringExtra("industryQualificationImageAdd");
        certType = getIntent().getStringExtra("certType");
        provinceView = new ProvinceView(this, NewDemoActivity02.this);
        certImageBack_relative = findViewById(R.id.certImageBack_relative);
        certImage_relative = findViewById(R.id.certImage_relative);
//        merchants_use_type = findViewById(R.id.merchants_use_type);
        province_tv = findViewById(R.id.province_tv);
        settlement_type = findViewById(R.id.settlement_type);
        submit_bt = findViewById(R.id.submit_bt);
        iv_back = findViewById(R.id.iv_back);
        inDoorImages_iv = findViewById(R.id.inDoorImages_iv);
        outDoorImages_iv = findViewById(R.id.outDoorImages_iv);
        certImage_iv = findViewById(R.id.certImage_iv);
        contact_name_ed = findViewById(R.id.contact_name_ed);
        contact_phone_ed = findViewById(R.id.contact_phone_ed);
        contact_adr_ed = findViewById(R.id.contact_adr_ed);
        certImageBack_iv = findViewById(R.id.certImageBack_iv);
        alipayLogonId_ed = findViewById(R.id.alipayLogonId_ed);
//        bindingAlipayLogonId_ed = findViewById(R.id.bindingAlipayLogonId_ed);
//        initUse();
//        initSettlement();
        settlement_type.setText("?????????");
//        merchants_use_type.setText(merchantUseList.get(0));
        if (merchantType.equals("06")) {
            certImageBack_relative.setVisibility(View.VISIBLE);
        } else {
            certImageBack_relative.setVisibility(View.GONE);
        }

        if (!mccStatus.equals("0") || !merchantType.equals("06")) {
            certImage_relative.setVisibility(View.VISIBLE);
        }
    }

//    private void initSettlement() {
//        settlementList = new ArrayList<>();
//        settlementList.add("?????????");
//        settlementPicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                //??????????????????
//                settlement_type.setText(settlementList.get(options1));
//            }
//        }).setTitleText("?????????????????????").setContentTextSize(17).setTitleSize(17).setSubCalSize(16).build();
//        settlementPicker.setPicker(settlementList);
//    }

    @Override
    protected void initListener() {
//        merchants_use_type.setOnClickListener(this);
        province_tv.setOnClickListener(this);
        submit_bt.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        inDoorImages_iv.setOnClickListener(this);
//        settlement_type.setOnClickListener(this);
        outDoorImages_iv.setOnClickListener(this);
        certImage_iv.setOnClickListener(this);
        certImageBack_iv.setOnClickListener(this);
    }

    @Override
    protected void initData() {


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
//            case R.id.merchants_use_type:
//                reasonPicker.show();
//                break;
            case R.id.province_tv:
                provinceView.showCityPicker();
                break;
//            case R.id.settlement_type:
//                settlementPicker.show();
//                break;
            //??????????????????
            case R.id.inDoorImages_iv:
                PictureSelector
                        .create(NewDemoActivity02.this, INDOOR)
                        .selectPicture(false);
                break;
            //?????????
            case R.id.outDoorImages_iv:
                PictureSelector
                        .create(NewDemoActivity02.this, OUTDOOR)
                        .selectPicture(false);
                break;
            //???????????????
            case R.id.certImage_iv:
                PictureSelector
                        .create(NewDemoActivity02.this, CERT)
                        .selectPicture(false);
                break;
            //?????????????????????
            case R.id.certImageBack_iv:
                PictureSelector
                        .create(NewDemoActivity02.this, CERTBACK)
                        .selectPicture(false);
                break;

            case R.id.submit_bt:
                if (TextUtils.isEmpty(contact_name_ed.getText().toString().trim())) {
                    Toast.makeText(this, "????????????????????????", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(contact_phone_ed.getText().toString().trim())) {
                    Toast.makeText(this, "????????????????????????", Toast.LENGTH_LONG).show();
                    return;
                }
//                if (TextUtils.isEmpty(serviceType)) {
//                    Toast.makeText(this, "???????????????????????????", Toast.LENGTH_LONG).show();
//                    return;
//                }
                if (TextUtils.isEmpty(provinceCode)) {
                    Toast.makeText(this, "??????????????????", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(provinceCode)) {
                    Toast.makeText(this, "??????????????????", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(contact_adr_ed.getText().toString().trim())) {
                    Toast.makeText(this, "?????????????????????", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(inDoorImages)) {
                    Toast.makeText(this, "??????????????????", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(outDoorImages)) {
                    Toast.makeText(this, "??????????????????", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!merchantType.equals("06")) {
                    if (TextUtils.isEmpty(certImage)) {
                        Toast.makeText(this, "????????????????????????", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                if (merchantType.equals("06")) {
                    if (TextUtils.isEmpty(certImageBack)) {
                        Toast.makeText(this, "??????????????????????????????", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                if (TextUtils.isEmpty(alipayLogonId_ed.getText().toString().trim())) {
                    Toast.makeText(this, "??????????????????????????????", Toast.LENGTH_LONG).show();
                    return;
                }

//                if (TextUtils.isEmpty(bindingAlipayLogonId_ed.getText().toString().trim())) {
//                    Toast.makeText(this, "??????????????????????????????", Toast.LENGTH_LONG).show();
//                    return;
//                }

                postData();
                break;

        }
    }

    //????????????????????????
//    private void initUse() {
//        merchantUseList = new ArrayList<>();
//        merchantUseList.add("?????????");
//        reasonPicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                //??????????????????
//                merchants_use_type.setText(merchantUseList.get(options1));
//                serviceType = merchantUseList.get(options1);
//                if (merchantType.equals("06")) {
//                    certImageBack_relative.setVisibility(View.VISIBLE);
//                } else {
//                    certImageBack_relative.setVisibility(View.GONE);
//                }
//
//                if (!mccStatus.equals("0") || !merchantType.equals("06")) {
//                    certImage_relative.setVisibility(View.VISIBLE);
//                }
//
//            }
//        }).setTitleText("???????????????????????????").setContentTextSize(17).setTitleSize(17).setSubCalSize(16).build();
//        reasonPicker.setPicker(merchantUseList);
//    }

    public void postProvince(String adrParentCode, String adrType, int type) {
        RequestParams params = new RequestParams();
        params.put("adrParentCode", adrParentCode);
        params.put("adrType", adrType);
        HttpRequest.postAdrCode(params, token, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //???????????????????????????
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    if (type == 0) {
                        List<MProvinceBean> titleList = gson.fromJson(result.getJSONArray("data").toString(),
                                new TypeToken<List<MProvinceBean>>() {
                                }.getType());
                        provinceView.setTitleData(titleList);
                    } else if (type == 1) {
                        List<MCityBean> titleList = gson.fromJson(result.getJSONArray("data").toString(),
                                new TypeToken<List<MCityBean>>() {
                                }.getType());
                        provinceView.setTwoLabelData(titleList);
                    } else {
                        List<MAreaBean> titleList = gson.fromJson(result.getJSONArray("data").toString(),
                                new TypeToken<List<MAreaBean>>() {
                                }.getType());
                        provinceView.setThreeLabelData(titleList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Failuer(failuer.getEcode(), failuer.getEmsg());
            }
        });


    }

    public void setSelectAdrCallback(String name, String pCode, String cCode, String dCode) {
        province_tv.setText(name);
        provinceCode = pCode;
        cityCode = cCode;
        districtCode = dCode;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            PictureBean pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
            if (requestCode == INDOOR) {
                lubanWith(pictureBean.getPath(), inDoorImages_iv, 0);
            } else if (requestCode == OUTDOOR) {
                lubanWith(pictureBean.getPath(), outDoorImages_iv, 1);
            } else if (requestCode == CERT) {
                lubanWith(pictureBean.getPath(), certImage_iv, 2);
            } else if (requestCode == CERTBACK) {
                lubanWith(pictureBean.getPath(), certImageBack_iv, 3);
            }

        }
    }


    /**
     * ????????????
     *
     * @param path
     * @param draweeView
     */
    private void lubanWith(String path, SimpleDraweeView draweeView, int card) {
        Luban.with(this)
                .load(path)
                .ignoreBy(100)
                .setTargetDir(Utility.getPath())
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        draweeView.setImageBitmap(BitmapFactory.decodeFile(file.getPath()));
                        upload(file.getPath(), folderName, card);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }).launch();
    }

    /**
     * ??????????????????
     *
     * @param path
     * @param folderName
     * @param card
     */
    private void upload(String path, String folderName, int card) {
        loadDialog.show();
        if (TextUtils.isEmpty(path)) {
            return;
        }
        if (cosxmlTask == null) {
            File file = new File(path);
            String cosPath;
            if (TextUtils.isEmpty(folderName)) {
                cosPath = file.getName();
            } else {
                cosPath = folderName + File.separator + file.getName();
            }
            cosxmlTask = transferManager.upload(bucketName, cosPath, path, null);
            Log.e("??????-------???", bucketName + "----" + cosPath + "---" + path);
            cosxmlTask.setTransferStateListener(new TransferStateListener() {
                @Override
                public void onStateChanged(final TransferState state) {
                    // refreshUploadState(state);
                }
            });
            cosxmlTask.setCosXmlProgressListener(new CosXmlProgressListener() {
                @Override
                public void onProgress(final long complete, final long target) {
                }
            });

            cosxmlTask.setCosXmlResultListener(new CosXmlResultListener() {
                @Override
                public void onSuccess(CosXmlRequest request, CosXmlResult result) {
                    COSXMLUploadTask.COSXMLUploadTaskResult cOSXMLUploadTaskResult = (COSXMLUploadTask.COSXMLUploadTaskResult) result;
                    cosxmlTask = null;
                    postUploadImages(card, cOSXMLUploadTaskResult.accessUrl);
                    setResult(RESULT_OK);
                }

                @Override
                public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {
                    if (cosxmlTask.getTaskState() != TransferState.PAUSED) {
                        cosxmlTask = null;
                        Log.e("1111", "????????????");
                    }
                    getDismissLoadDialog();
                    exception.printStackTrace();
                    serviceException.printStackTrace();
                }
            });

        }
    }

    /**
     * ?????????????????????
     *
     * @param card
     * @param accessUrl
     */
    private void postUploadImages(int card, String accessUrl) {
        RequestParams params = new RequestParams();
        params.put("url", accessUrl);
        HttpRequest.postUploadImages(params, token, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                getDismissLoadDialog();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    if (card == 0) {
                        inDoorImages = result.getJSONObject("data").getString("imageId");
                        inDoorImagesAdd = result.getString("url");
                    } else if (card == 1) {
                        outDoorImages = result.getJSONObject("data").getString("imageId");
                        outDoorImagesAdd = result.getString("url");
                    } else if (card == 2) {
                        certImage = result.getJSONObject("data").getString("imageId");
                        certImageAdd = result.getString("url");
                    } else if (card == 3) {
                        certImageBack = result.getJSONObject("data").getString("imageId");
                        certImageBackAdd = result.getString("url");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                getDismissLoadDialog();
                Failuer(failuer.getEcode(), failuer.getEmsg());
            }
        });

    }


    //????????????
    private void postData() {
        RequestParams params = new RequestParams();
        params.put("userId", SPUtils.get(this, "mUserId", "").toString());
        params.put("merchantUserId", SPUtils.get(this, "merchantUserId", "").toString());
        params.put("inDoorImages", inDoorImages);
        params.put("inDoorImagesAdd", inDoorImagesAdd);
        params.put("outDoorImages", outDoorImages);
        params.put("outDoorImagesAdd", outDoorImagesAdd);
        params.put("name", name);
        params.put("aliasName", aliasName);
        params.put("merchantType", merchantType);
        params.put("mcc", mcc);
        params.put("certNo", certNo);
        params.put("certType", certType);
        params.put("certImage", certImage);
        params.put("certImageAdd", certImageAdd);
        params.put("certImageBack", certImageBack);
        params.put("certImageBackAdd", certImageBackAdd);
        params.put("legalName", legalName);
        params.put("legalCertNo", legalCertNo);
        params.put("legalCertFrontImage", legalCertFrontImage);
        params.put("legalCertFrontImageAdd", legalCertFrontImageAdd);
        params.put("legalCertBackImage", legalCertBackImage);
        params.put("legalCertBackImageAdd", legalCertBackImageAdd);
        params.put("provinceCode", provinceCode);
        params.put("cityCode", cityCode);
        params.put("districtCode", districtCode);
        params.put("address", contact_adr_ed.getText().toString().trim());
        params.put("userName", contact_name_ed.getText().toString().trim());
        params.put("userPhone", contact_phone_ed.getText().toString().trim());
        params.put("service", "?????????");
        params.put("alipayLogonId", alipayLogonId_ed.getText().toString().trim());
        params.put("siteType", siteType);
        params.put("siteUrl", "");
        params.put("siteName", "????????????");
        params.put("certName", certName);
        params.put("bindingAlipayLogonId", alipayLogonId_ed.getText().toString().trim());
        params.put("defaultSettleType", "alipayAccount");
        params.put("defaultSettleTarget", alipayLogonId_ed.getText().toString().trim());
        params.put("industryQualificationType", industryQualificationType);
        params.put("industryQualificationImage", industryQualificationImage);
        params.put("industryQualificationImageAdd", industryQualificationImageAdd);
        HttpRequest.postIncoming(params, token, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {

            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });
    }

}
package com.example.shunkapos.demo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.shunkapos.R;
import com.example.shunkapos.base.BaseActivity;
import com.example.shunkapos.cos.CosServiceFactory;
import com.example.shunkapos.demo.adapter.TitleAdapter;
import com.example.shunkapos.demo.demobean.ThreeLabelBean;
import com.example.shunkapos.demo.demobean.TitleBean;
import com.example.shunkapos.demo.demobean.TwoLabelBean;
import com.example.shunkapos.demo.model.LabelView;
import com.example.shunkapos.homefragment.homemerchants.homenewmerchants.adapter.AreaAdapter;
import com.example.shunkapos.homefragment.homemerchants.homenewmerchants.adapter.CityAdapter;
import com.example.shunkapos.homefragment.homemerchants.homenewmerchants.adapter.ProvinceAdapter;
import com.example.shunkapos.homefragment.homemerchants.homenewmerchants.merchantstype.bean.CityBean;
import com.example.shunkapos.homefragment.homemerchants.homenewmerchants.merchantstype.bean.DistrictBean;
import com.example.shunkapos.homefragment.homemerchants.homenewmerchants.merchantstype.bean.ProvinceBean;
import com.example.shunkapos.homefragment.homemerchants.homenewmerchants.merchantstype.bean.SmallinformationBean;
import com.example.shunkapos.homefragment.homequoteactivity.HomeQuoteActivity2;
import com.example.shunkapos.net.HttpRequest;
import com.example.shunkapos.net.OkHttpException;
import com.example.shunkapos.net.RequestParams;
import com.example.shunkapos.net.ResponseCallback;
import com.example.shunkapos.useractivity.LoginActivity;
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
 * 作者: qgl
 * 创建日期：2022/6/14
 * 描述:
 */
public class NewDemoActivity extends BaseActivity implements View.OnClickListener {
    final private int IDCARDIN = 1004;
    final private int IDCARDON = 1005;
    final private int LICENSE = 1006;
    private TextView merchants_type;
    //选择商户类型弹出控件
    private OptionsPickerView reasonPicker;
    private List<NewDemoBean> beanList = new ArrayList<>();
    private EditText shop_name_ed;
    private EditText certificate_num_ed;
    private TextView mer_code_type;
    private LabelView labelView;
    private String industryQualificationType = "";
    private LinearLayout query_linear2;
    private TextView mccMessage_tv;
    private SimpleDraweeView id_card_is;
    private SimpleDraweeView id_card_the;
    private SimpleDraweeView license_card;
    private CosXmlService cosXmlService;
    private TransferManager transferManager;
    private COSXMLUploadTask cosxmlTask;
    private String folderName = "";
    //商户行业资质
    private String industryQualificationImage;
    private String industryQualificationImageAdd;
    //法人身份证正面
    private String legalCertFrontImage;
    private String legalCertFrontImageAdd;
    //法人身份证反面
    private String legalCertBackImage;
    private String legalCertBackImageAdd;
    private RelativeLayout legalName_liner;
    private RelativeLayout legalCertNo_liner;
    private View legalName_view;
    private View legalCertNo_view;
    private Button submit_bt;
    private RelativeLayout lincene_relative;
    private View lincene_line_view;
    private LinearLayout iv_back;
    private EditText alias_name_ed;
    private EditText license_name_ed;
    private EditText legalName_ed;
    private EditText legalCertNo_ed;
    //商户类型
    private String merchantType = "";
    private String certType = "";
    // 进件的二级商户名称
    private String mcc = "";
    //是否特殊行业 0 不是 1 是
    private String mccStatus = "0";
    private String bucketName = "shunka-1303987307";
    private String token = "eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6IjY1MDQ5NzhmLTg5MWYtNDBlYy1iYWVhLTRmYjRjZTJmYzBiZiJ9.x8hak7RLm4vUcpuBbCra0auVdvOJn55ipTvkuW0MHZrDFG62-0ua0L9Wsiawqlgxgl4mVvUbbDDG5Euf_k8jCQ";
    private String secretKey = "UouqnWDf2A1cyKn46tQm00whEYvDJI2Z";
    private String secretId = "AKIDvnxzoQIz8FAe2BfJHHRFo8Fn45rMAdQz";

    @Override
    protected int getLayoutId() {
        // 设置状态栏颜色
        statusBarConfig(R.color.new_theme_color, false).init();
        return R.layout.new_demo_activity;
    }

    @Override
    protected void initView() {
        labelView = new LabelView(this, NewDemoActivity.this);
        //初始化存储桶控件
        cosXmlService = CosServiceFactory.getCosXmlService(this, "ap-beijing", secretId, secretKey, true);
        TransferConfig transferConfig = new TransferConfig.Builder().build();
        transferManager = new TransferManager(cosXmlService, transferConfig);
        folderName = "quote" + "/" + "zfb" + "/android/" + TimeUtils.getNowTime("day");
        merchants_type = findViewById(R.id.merchants_type);
        shop_name_ed = findViewById(R.id.shop_name_ed);
        certificate_num_ed = findViewById(R.id.certificate_num_ed);
        mer_code_type = findViewById(R.id.mer_code_type);
        query_linear2 = findViewById(R.id.query_linear2);
        mccMessage_tv = findViewById(R.id.mccMessage_tv);
        id_card_is = findViewById(R.id.id_card_is);
        id_card_the = findViewById(R.id.id_card_the);
        license_card = findViewById(R.id.license_card);
        legalName_liner = findViewById(R.id.legalName_liner);
        legalCertNo_liner = findViewById(R.id.legalCertNo_liner);
        legalName_view = findViewById(R.id.legalName_view);
        legalCertNo_view = findViewById(R.id.legalCertNo_view);
        submit_bt = findViewById(R.id.submit_bt);
        lincene_relative = findViewById(R.id.lincene_relative);
        lincene_line_view = findViewById(R.id.lincene_line_view);
        iv_back = findViewById(R.id.iv_back);
        alias_name_ed = findViewById(R.id.alias_name_ed);
        license_name_ed = findViewById(R.id.license_name_ed);
        legalName_ed = findViewById(R.id.legalName_ed);
        legalCertNo_ed = findViewById(R.id.legalCertNo_ed);
        postMerType();
    }

    //获取商户类别
    public void postMcc(String parentId, String type) {
        RequestParams params = new RequestParams();
        params.put("parentId", parentId);
        HttpRequest.postMccType(params,token , new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                        List<ThreeLabelBean> threeLabelList = gson.fromJson(result.getJSONArray("data").toString(),
                                new TypeToken<List<ThreeLabelBean>>() {
                                }.getType());
                        labelView.setThreeLabelData(threeLabelList);

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

    private void postMerType() {
        RequestParams params = new RequestParams();
        HttpRequest.postMerchantType(params, token, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    beanList = gson.fromJson(result.getJSONArray("data").toString(), new TypeToken<List<NewDemoBean>>() {
                    }.getType());
                    initMerReason(beanList, merchants_type);
                    iniValue();
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

    //回显商户选项
    private void initMerReason(List<NewDemoBean> list, TextView textView) {
        reasonPicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //兑换对象赋值
                textView.setText(list.get(options1).getMerchantName());
                shop_name_ed.setHint(list.get(options1).getNameMessage());
                certificate_num_ed.setHint(list.get(options1).getCertNoMessage());
                merchantType = list.get(options1).getMerchantType();
                certType = list.get(options1).getCertType();
                compareType(list.get(options1).getMerchantType());
            }
        }).setTitleText("请选择商户类型").setContentTextSize(17).setTitleSize(17).setSubCalSize(16).build();
        reasonPicker.setPicker(list);
    }

    @Override
    protected void initListener() {
        merchants_type.setOnClickListener(this);
        mer_code_type.setOnClickListener(this);
        id_card_is.setOnClickListener(this);
        id_card_the.setOnClickListener(this);
        license_card.setOnClickListener(this);
        submit_bt.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        query_linear2.setVisibility(View.GONE);
    }


    private void iniValue() {
        merchantType = beanList.get(0).getMerchantType();
        certType = beanList.get(0).getCertType();
        merchants_type.setText(beanList.get(0).getMerchantName());
        shop_name_ed.setHint(beanList.get(0).getNameMessage());
        certificate_num_ed.setHint(beanList.get(0).getCertNoMessage());
        compareType(beanList.get(0).getMerchantType());
    }


    /**
     * 判断是否需要上传身份证名称、身份证号
     *
     * @param merchantType
     */
    private void compareType(String merchantType) {
        if (merchantType.equals("06")) {
            legalName_liner.setVisibility(View.GONE);
            legalCertNo_liner.setVisibility(View.GONE);
            legalName_view.setVisibility(View.GONE);
            legalCertNo_view.setVisibility(View.GONE);
            lincene_relative.setVisibility(View.GONE);
            lincene_line_view.setVisibility(View.GONE);
        } else if (merchantType.equals("07")) {
            legalName_liner.setVisibility(View.VISIBLE);
            legalCertNo_liner.setVisibility(View.VISIBLE);
            legalName_view.setVisibility(View.VISIBLE);
            legalCertNo_view.setVisibility(View.VISIBLE);
            lincene_relative.setVisibility(View.VISIBLE);
            lincene_line_view.setVisibility(View.VISIBLE);
        } else {
            legalName_liner.setVisibility(View.VISIBLE);
            legalCertNo_liner.setVisibility(View.VISIBLE);
            legalName_view.setVisibility(View.VISIBLE);
            legalCertNo_view.setVisibility(View.VISIBLE);
            lincene_relative.setVisibility(View.GONE);
            lincene_line_view.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.merchants_type:
                reasonPicker.show();
                break;
            case R.id.mer_code_type:
                labelView.showCityPicker();
                break;
            case R.id.id_card_is:
                PictureSelector
                        .create(NewDemoActivity.this, IDCARDIN)
                        .selectPicture(false);
                break;
            case R.id.id_card_the:
                PictureSelector
                        .create(NewDemoActivity.this, IDCARDON)
                        .selectPicture(false);
                break;
            case R.id.license_card:
                PictureSelector
                        .create(NewDemoActivity.this, LICENSE)
                        .selectPicture(false);
                break;
            case R.id.submit_bt:
                if (merchantType.equals("")) {
                    showToast(3, "商户类型");
                    return;
                }
                if (TextUtils.isEmpty(shop_name_ed.getText().toString())) {
                    Toast.makeText(this, "请输入商户名称", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(alias_name_ed.getText().toString())) {
                    Toast.makeText(this, "请输入商户别名", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(certificate_num_ed.getText().toString())) {
                    Toast.makeText(this, "请输入证件编号", Toast.LENGTH_LONG).show();
                    return;
                }
                if (merchantType.equals("07")) {
                    if (TextUtils.isEmpty(license_name_ed.getText().toString())) {
                        Toast.makeText(this, "请输入营业执照名称", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                if (!merchantType.equals("06")) {
                    if (TextUtils.isEmpty(legalName_ed.getText().toString())) {
                        Toast.makeText(this, "请输入法人姓名", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (TextUtils.isEmpty(legalCertNo_ed.getText().toString())) {
                        Toast.makeText(this, "请输入法人身份证号", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                if (mcc.equals("")) {
                    showToast(3, "商户类别码");
                    return;
                }
                if (mccStatus.equals("1")) {
                    if (TextUtils.isEmpty(legalCertFrontImage)) {
                        Toast.makeText(this, "请上传身份证正面", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (TextUtils.isEmpty(legalCertBackImage)) {
                        Toast.makeText(this, "请上传身份证反面", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (TextUtils.isEmpty(industryQualificationImage)) {
                        Toast.makeText(this, "请上传商户行业资质", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                Intent intent = new Intent(this, NewDemoActivity02.class);
                //商户类型
                intent.putExtra("merchantType", merchantType);
                //商户名称
                intent.putExtra("name", shop_name_ed.getText().toString());
                //商户别名
                intent.putExtra("aliasName", alias_name_ed.getText().toString());
                //商户证件编号
                intent.putExtra("certNo", certificate_num_ed.getText().toString());
                //个体工商户类型要求填入本字段(填写值为个体工商户营业执照上的名称)
                intent.putExtra("certName", license_name_ed.getText().toString());
                //法人名称(非个人商户类型必填)
                intent.putExtra("legalName", legalName_ed.getText().toString());
                //法人身份证号(非个人商户类型必填)
                intent.putExtra("legalCertNo", legalCertNo_ed.getText().toString());
                //法人身份证正面(特殊行业必填)
                intent.putExtra("legalCertFrontImage", legalCertFrontImage);
                //法人身份证正面(特殊行业必填)本地地址
                intent.putExtra("legalCertFrontImageAdd", legalCertFrontImageAdd);
                //法人身份证反面(特殊行业必填)
                intent.putExtra("legalCertBackImage", legalCertBackImage);
                //法人身份证反面(特殊行业必填)本地地址
                intent.putExtra("legalCertBackImageAdd", legalCertBackImageAdd);
                //商户行业资质类型(特殊行业必填)
                intent.putExtra("industryQualificationType", industryQualificationType);
                //商户行业资质图片(特殊行业必填)
                intent.putExtra("industryQualificationImage", industryQualificationImage);
                //商户行业资质图片(特殊行业必填)本地地址
                intent.putExtra("industryQualificationImageAdd", industryQualificationImageAdd);
                // 商品类别码-辅助字段
                intent.putExtra("mccStatus", mccStatus);
                //商品类别码
                intent.putExtra("mcc", mcc);
                intent.putExtra("certType", certType);
                startActivity(intent);
                break;
        }
    }


    public void setSelectCallback(String s) {
        mer_code_type.setText(s);
    }


    /**
     * 特殊行业
     *
     * @param type
     * @param mccMessage
     */
    public void setMccSatus(String status, String type, String mccMessage, String mc) {
        mccStatus = status;
        mcc = mc;
        if (mccStatus.equals("0")) {
            query_linear2.setVisibility(View.GONE);
        } else {
            industryQualificationType = type;
            query_linear2.setVisibility(View.VISIBLE);
            mccMessage_tv.setText(mccMessage);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            PictureBean pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
            if (requestCode == IDCARDIN) {
                lubanWith(pictureBean.getPath(), id_card_is, 0);
            } else if (requestCode == IDCARDON) {
                lubanWith(pictureBean.getPath(), id_card_the, 1);
            } else if (requestCode == LICENSE) {
                lubanWith(pictureBean.getPath(), license_card, 2);
            }
        }
    }


    /**
     * 图片压缩
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

    //上传图片
    private void upload(String fileUrl, String folderName, int card) {
        loadDialog.show();
        if (TextUtils.isEmpty(fileUrl)) {
            return;
        }
        if (cosxmlTask == null) {
            File file = new File(fileUrl);
            String cosPath;
            if (TextUtils.isEmpty(folderName)) {
                cosPath = file.getName();
            } else {
                cosPath = folderName + File.separator + file.getName();
            }
            cosxmlTask = transferManager.upload(bucketName, cosPath, fileUrl, null);
            Log.e("参数-------》", getBucketName() + "----" + cosPath + "---" + fileUrl);
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
                        Log.e("1111", "上传失败");
                    }
                    getDismissLoadDialog();
                    exception.printStackTrace();
                    serviceException.printStackTrace();
                }
            });

        }
    }

    /**
     * 支付宝图片存储
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
                        legalCertFrontImage = result.getJSONObject("data").getString("imageId");
                        legalCertFrontImageAdd = result.getString("url");
                    } else if (card == 1) {
                        legalCertBackImage = result.getJSONObject("data").getString("imageId");
                        legalCertBackImageAdd = result.getString("url");

                    } else if (card == 2) {
                        industryQualificationImage = result.getJSONObject("data").getString("imageId");
                        industryQualificationImageAdd = result.getString("url");
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

}
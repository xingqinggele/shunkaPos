package com.example.shunkapos.useractivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.shunkapos.MainActivity;
import com.example.shunkapos.R;
import com.example.shunkapos.net.HttpRequest;
import com.example.shunkapos.net.OkHttpException;
import com.example.shunkapos.net.RequestParams;
import com.example.shunkapos.net.ResponseCallback;
import com.example.shunkapos.socket.JWebSocketClientService;
import com.example.shunkapos.utils.SPUtils;
import com.example.shunkapos.utils.StatusBarUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者: qgl
 * 创建日期：2021/2/1
 * 描述: 欢迎界面
 */
public class WelcomeActivity extends Activity{
    //延时操作
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            /**
             *要执行的操作
             */
                //跳转到登录界面
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //导航栏设置
        StatusBarUtil.transparencyBar(this);
        //App打包apk安装后重复启动根界面的问题
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                finish();
                return;
            }
        }
        //xml界面
        setContentView(R.layout.welcome_activity);
        //延时操作
        Timer timer = new Timer();
        timer.schedule(task, 3000);//3秒后执行TimeTask的run方法
    }




}

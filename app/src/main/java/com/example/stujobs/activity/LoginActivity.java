package com.example.stujobs.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.stujobs.Api;
import com.example.stujobs.R;
import com.example.stujobs.base.BaseActivity;
import com.umeng.analytics.MobclickAgent;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.dialog.MiniLoadingDialog;
import com.xuexiang.xutil.system.DeviceUtils;

import org.json.JSONObject;

import butterknife.BindView;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_number)
    EditText number;
    @BindView(R.id.et_password)
    EditText password;

    @BindView(R.id.bt_login)
    Button button;


    public static final int SUCCESSFUL=100;
    MiniLoadingDialog mMiniLoadingDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMiniLoadingDialog =  WidgetUtils.getMiniLoadingDialog(this);
        //登记
        MobclickAgent.onProfileSignIn(DeviceUtils.getAndroidID());
        initListener();
    }

    @SuppressLint("HandlerLeak")
    private final Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESSFUL:
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    break;
                default:
                    break;
            } }

    };

    private void initListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMiniLoadingDialog.show();
                SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);//拿到 sharedPreferences 的引用
                SharedPreferences.Editor editor=sharedPreferences.edit();//拿到编辑对象
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            Response response = Api.login(number.getText().toString(),password.getText().toString());
                            //Response response = Api.login("19858156575","123456");
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            int code = response.code();
                            if (code == 200){
                                Log.d("webConnect", "成功"+ jsonObject);
                                editor.putInt("id", (Integer) jsonObject.get("id"));
                                editor.putString("name", (String) jsonObject.get("name"));
                                editor.putString("phone", (String) jsonObject.get("phone"));
                                editor.putInt("remark", (Integer) jsonObject.get("remark"));
                                editor.apply();
                                Message msg =new Message();
                                msg.what=SUCCESSFUL;
                                handler.sendMessage(msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("webConnect_login", "请求失败"+e);
                        }

                    }
                }.start();

            }
        });
    }

}
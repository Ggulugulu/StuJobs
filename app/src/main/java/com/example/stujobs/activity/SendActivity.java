package com.example.stujobs.activity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.stujobs.Api;
import com.example.stujobs.R;
import com.example.stujobs.adapter.SendAdapter;
import com.example.stujobs.adapter.entity.SendInfo;
import com.example.stujobs.base.BaseActivity;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.dialog.MiniLoadingDialog;
import com.xuexiang.xui.widget.toast.XToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Response;

public class SendActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private TextView title;
    private List<SendInfo> datas = new ArrayList<>();
    private SendAdapter adapter;
    public static final int SUCCESSFUL_SEND=100;
    public static final int SUCCESSFUL_TO=101;
    private int remark;
    private int id;
    MiniLoadingDialog mMiniLoadingDialog;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_send;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMiniLoadingDialog =  WidgetUtils.getMiniLoadingDialog(this);
        //设置沉浸式状态栏
        StatusBarUtils.translucent(this);
        title = findViewById(R.id.common_toolbar_title);
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        remark = sharedPreferences.getInt("remark", 0);
        id = sharedPreferences.getInt("id", 0);

        recyclerView = findViewById(R.id.recyclerView);
        WidgetUtils.initRecyclerView(recyclerView, 0);
        recyclerView.setAdapter(adapter = new SendAdapter(this,datas,remark));
        initTitle();
        mMiniLoadingDialog.show();
        initDatas();

    }
    private void initTitle() {
        title.setText("投递处理");
    }

    @SuppressLint("HandlerLeak")
    private final Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESSFUL_SEND:
                    adapter.notifyDataSetChanged();
                    mMiniLoadingDialog.dismiss();
                    break;
                case SUCCESSFUL_TO:
                    adapter.notifyDataSetChanged();
                    mMiniLoadingDialog.dismiss();
                    initListeners();
                    break;
                default:
                    break;
            } }
    };

    private void initDatas() {
        if(id != 0){
            new Thread(){
                @Override
                public void run() {
                    try {
                        if(remark ==1){
                            Response response = Api.sendList(id);
                            JSONArray jsonArray = new JSONArray(response.body().string());
                            int code = response.code();
                            if(code == 200){
                                //Log.d("webConnect_send", "成功"+ jsonArray);
                                for(int i = 0;i< jsonArray.length();i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    SendInfo send = new SendInfo(jsonObject.getInt("id"),
                                            jsonObject.getInt("job_id"),
                                            jsonObject.getString("position"),
                                            jsonObject.getString("company"),
                                            jsonObject.getString("result"),
                                            jsonObject.getString("title"),
                                            jsonObject.getString("url"),
                                            jsonObject.getString("name"));
                                    datas.add(send);
                                }
                                Message msg =new Message();
                                msg.what=SUCCESSFUL_SEND;
                                handler.sendMessage(msg);
                            }
                        }
                        if(remark ==2) {
                            Response response = Api.toList(id);
                            Log.d("webConnect_send", String.valueOf(id));
                            JSONArray jsonArray = new JSONArray(response.body().string());
                            Log.d("webConnect_send", "成功"+ jsonArray);
                            int code = response.code();
                            if (code == 200) {
                                //Log.d("webConnect_send", "成功"+ jsonArray);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    SendInfo send = new SendInfo(jsonObject.getInt("id"),
                                            jsonObject.getInt("job_id"),
                                            jsonObject.getString("position"),
                                            jsonObject.getString("company"),
                                            jsonObject.getString("result"),
                                            jsonObject.getString("title"),
                                            jsonObject.getString("url"),
                                            jsonObject.getString("name"));
                                    datas.add(send);
                                }
                                Message msg = new Message();
                                msg.what = SUCCESSFUL_TO;
                                handler.sendMessage(msg);
                            }
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                        Log.d("webConnect_login", "请求失败"+e);
                    }
                }
            }.start();
        }else {
            Log.d("id", "id=0");
        }
    }

    private void initListeners(){
        if(datas.size() > 0){
            adapter.setItemCellClicker(new SendAdapter.ItemCellClicker() {
                @Override
                public void onItemClick(View view, int position) {
                    switch (view.getId()){
                        case R.id.send_pass:
                            mMiniLoadingDialog.show();
                            new Thread(){
                                @Override
                                public void run() {
                                    Api.PostUpdateSend(String.valueOf(datas.get(position).getId()),"已通过");
                                }
                            }.start();
                            mMiniLoadingDialog.dismiss();
                            datas.remove(position);
                            adapter.notifyDataSetChanged();
                            XToast.success(SendActivity.this, "已通过！").show();
                            break;
                        case R.id.send_out:
                            mMiniLoadingDialog.show();
                            new Thread(){
                                @Override
                                public void run() {
                                    Api.PostUpdateSend(String.valueOf(datas.get(position).getId()),"已拒绝");
                                }
                            }.start();
                            mMiniLoadingDialog.dismiss();
                            datas.remove(position);
                            adapter.notifyDataSetChanged();
                            XToast.success(SendActivity.this, "已拒绝！").show();
                            break;
                    }

                }
                @Override
                public void onIntroduceClick(View view, int position, String url) {
                    openPdf(datas.get(position).getUrl());
                }
            });
        }
    }
    private void openPdf(String pdfFilePath) {
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pdfIntent.setDataAndType(Uri.parse("file://" + pdfFilePath), "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

}
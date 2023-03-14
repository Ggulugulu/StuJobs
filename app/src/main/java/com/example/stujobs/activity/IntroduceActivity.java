package com.example.stujobs.activity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.stujobs.Api;
import com.example.stujobs.R;
import com.example.stujobs.adapter.IntroduceAdapter;
import com.example.stujobs.adapter.entity.IntroInfo;
import com.example.stujobs.base.BaseActivity;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.dialog.MiniLoadingDialog;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.toast.XToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import okhttp3.Response;

public class IntroduceActivity extends BaseActivity {

    @BindView(R.id.introduce_add)
    LinearLayout add;

    private static final int REQUEST_CODE = 100;
    private static final int REQUEST_CODE_OPEN_DOCUMENT = 2;
    //设置适配器
    private IntroduceAdapter adapter;
    private TextView title;
    private RecyclerView recyclerView;
    private Context mContext;
    private List<IntroInfo> datas = new ArrayList<>();
    MiniLoadingDialog mMiniLoadingDialog;
    public static final int SUCCESSFUL=100;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_introduce;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //设置沉浸式状态栏
        StatusBarUtils.translucent(this);
        mMiniLoadingDialog =  WidgetUtils.getMiniLoadingDialog(this);
        title = findViewById(R.id.common_toolbar_title);
        recyclerView = findViewById(R.id.recyclerView);

        WidgetUtils.initRecyclerView(recyclerView, 0);
        recyclerView.setAdapter(adapter = new IntroduceAdapter(this, datas));
        initTitle();
        mMiniLoadingDialog.show();
        initDatas();
        initListeners();
    }

    @SuppressLint("HandlerLeak")
    private final Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESSFUL:
                    mMiniLoadingDialog.dismiss();
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            } }
    };


    private void initTitle() {
        title.setText("我的简历");
    }
    private void initDatas() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("id", 0);
        if(id != 0){
            new Thread(){
                @Override
                public void run() {
                    try {
                        Response response = Api.introduceList(id);
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        int code = response.code();
                        if (code == 200){
                            Log.d("webConnect_introduce", "成功"+ jsonArray);
                            for(int i = 0;i< jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Log.d("webConnect_introduce", String.valueOf(jsonObject));
                                IntroInfo intro = new IntroInfo(jsonObject.getString("title"),
                                        jsonObject.getString("url") );
                                datas.add(intro);
                            }
                            Message msg =new Message();
                            msg.what=SUCCESSFUL;
                            handler.sendMessage(msg);
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                        Log.d("webConnect_introduce", "请求失败"+e);
                    }
                }
            }.start();
        }else {
            Log.d("id", "id=0");
        }


    }
    private void initListeners() {
        adapter.setItemCellClicker(new IntroduceAdapter.ItemCellClicker() {
            @Override
            public void onItemClick(View view, int position) {
                switch (view.getId()){
                    case R.id.introduce_see:
                        //String pdfFilePath = "/storage/emulated/0/Download/PDF_330178815.pdf";
                        openPdf(datas.get(position).getUrl());
                        break;
                    case R.id.introduce_delete:
                        datas.remove(position);
                        adapter.notifyDataSetChanged();
                        break;

                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(mContext)
                        .title("选项")
                        .items(R.array.menu_values)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                switch (position) {
                                    case 0:
                                        openDocument();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        })
                        .show();
            }
        });
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

    private void openDocument() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, REQUEST_CODE_OPEN_DOCUMENT);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_OPEN_DOCUMENT && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            String filePath = uri.getPath().substring("/document/raw:".length());

            Log.d("1111", "Selected file: " + filePath);
            XToast.success(this, "上传成功！").show();

            IntroInfo intro = new IntroInfo("简历模板"+(datas.size()+1),filePath);
            datas.add(intro);
            adapter.notifyDataSetChanged();
        }
    }

}
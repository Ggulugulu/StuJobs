package com.example.stujobs.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.stujobs.Api;
import com.example.stujobs.R;
import com.example.stujobs.adapter.PraiseAdapter;
import com.example.stujobs.adapter.entity.JobInfo;
import com.example.stujobs.base.BaseActivity;
import com.google.android.flexbox.FlexboxLayout;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Response;

public class MoreActivity extends BaseActivity {

    private SuperTextView position;
    private SuperTextView company;
    private SuperTextView hr;
    private SuperTextView praise;
    private TextView more;
    private TextView request;
    private TextView title;
    private PraiseAdapter adapter;
    private FlexboxLayout flexboxLayout;
    private RecyclerView recyclerView;
    private RoundButton send;
    private Context mContext;
    private List<JobInfo> datas = new ArrayList<>();
    public static final int SUCCESSFUL=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        mContext = this;
        //设置沉浸式状态栏
        StatusBarUtils.translucent(this);
        Intent intent = getIntent();
        JobInfo job = (JobInfo) intent.getSerializableExtra("job");


        title = findViewById(R.id.common_toolbar_title);
        position = findViewById(R.id.more_position);
        company = findViewById(R.id.more_company);
        hr = findViewById(R.id.more_hr);
        flexboxLayout = findViewById(R.id.more_fl);
        more = findViewById(R.id.more_more);
        request = findViewById(R.id.more_request);

        recyclerView = findViewById(R.id.recyclerView);
        praise = findViewById(R.id.more_praise);
        send = findViewById(R.id.more_send);

        for(int i = 0;i<job.getTags().size();i++){
            View tagView = LayoutInflater.from(this).inflate(R.layout.adapter_item_tag, null, false);
            TextView tag = tagView.findViewById(R.id.tv_tag);
            tag.setText(job.getTags().get(i));
            flexboxLayout.addView(tagView);
        }

        initViews();
        initDatas(job);
        initListeners();
    }
    private void initViews() {
        title.setText("详情");
        WidgetUtils.initRecyclerView(recyclerView, 0);
        recyclerView.setAdapter(adapter = new PraiseAdapter(mContext, datas));
    }

    @SuppressLint("HandlerLeak")
    private final Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESSFUL:

                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            } }

    };
    private void initDatas(JobInfo job) {
        int id = job.getID();
        position.setLeftTopString(job.getPosition()).setRightBottomString(job.getPrice());
        company.setLeftTopString(job.getCompany());
        hr.setLeftString(job.getHr());
        more.setText(job.getMore());
        request.setText(job.getRequest());


        // 设置标签点击事件
        //tag.setOnClickListener(view -> itemCellClicker.onItemClick(temp));


        new Thread(){
            @Override
            public void run() {
                try {
                    Response response = Api.pingjiaList(id);
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    int code = response.code();
                    if (code == 200){
                        //Log.d("webConnect_position", "成功"+ jsonArray);
                        for(int i = 0;i< jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            //Log.d("webConnect_position", String.valueOf(jsonObject));
                            JobInfo tJob=new JobInfo(jsonObject.getString("content"),
                                    jsonObject.getString("time"),
                                    jsonObject.getInt("count"));
                            datas.add(tJob);
                        }
                        Message msg =new Message();
                        msg.what=SUCCESSFUL;
                        handler.sendMessage(msg);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    Log.d("webConnect_pingjia", "请求失败"+e);
                }
            }
        }.start();
    }



    private void initListeners() {
       adapter.setItemCellClicker(new PraiseAdapter.ItemCellClicker() {
           @Override
           public void onItemClick(View view, int position) {
               datas.get(position).setMore_count(datas.get(position).getMore_count()+1);
               adapter.notifyDataSetChanged();
           }
       });

       praise.setRightTvClickListener(new SuperTextView.OnRightTvClickListener() {
           @Override
           public void onClick(TextView textView) {
               new MaterialDialog.Builder(mContext)
                       .title("评价")
                       .inputType(InputType.TYPE_CLASS_TEXT)
                       .input(
                               "请输入内容",
                               "",
                               true,
                               null)
                       .positiveText("确认")
                       .negativeText("取消")
                       .onPositive(new MaterialDialog.SingleButtonCallback() {
                           @Override
                           public void onClick(@NonNull @NotNull MaterialDialog dialog, @NonNull @NotNull DialogAction which) {
                               JobInfo p = new JobInfo();
                               p.setMore_praise(dialog.getInputEditText().getText().toString());
                               Date curDate = new Date(System.currentTimeMillis());
                               SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm");
                               String createDate = formatter.format(curDate);
                               p.setMore_time(createDate);
                               p.setMore_count(0);
                               datas.add(p);
                               adapter.notifyDataSetChanged();
                           }
                       })
                       .cancelable(true)
                       .show();
           }
       });

       send.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               new BottomSheet.BottomListSheetBuilder(mContext)
                       .setTitle("简历模板")
                       .addItem("简历模板1")
                       .addItem("简历模板2")
                       .setIsCenter(true)
                       .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                           dialog.dismiss();
                           XToastUtils.success("投递成功！");
                       })
                       .build()
                       .show();
           }
       });
    }


}
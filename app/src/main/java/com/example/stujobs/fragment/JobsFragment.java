package com.example.stujobs.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.example.stujobs.Api;
import com.example.stujobs.DemoDataProvider;
import com.example.stujobs.R;
import com.example.stujobs.activity.MoreActivity;
import com.example.stujobs.adapter.JobsCardViewListAdapter;
import com.example.stujobs.adapter.entity.FilterEntity;
import com.example.stujobs.adapter.entity.JobInfo;
import com.example.stujobs.adapter.entity.filter.FilterMulSelectEntity;
import com.example.stujobs.adapter.entity.filter.FilterSelectedEntity;
import com.example.stujobs.base.BaseHomeFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.layout.linkage.view.LinkageRecyclerView;
import com.xuexiang.xutil.net.JsonUtil;
import com.yangbin.FilterTabConfig;
import com.yangbin.base.BaseFilterBean;
import com.yangbin.bean.FilterInfoBean;
import com.yangbin.view.FilterTabView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import okhttp3.Response;

@Page(name = "首页")
public class JobsFragment extends BaseHomeFragment {

    @BindView(R.id.recyclerView)
    LinkageRecyclerView recyclerView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.ftb_filter)
    FilterTabView ftb_filter;

    private JobsCardViewListAdapter mJobsCardViewListAdapter;
    private List<BaseFilterBean> mAreaList = new ArrayList<>();//区域
    private List<FilterSelectedEntity> mAllPriceList = new ArrayList<>();//薪资
    private List<FilterMulSelectEntity> mPosition = new ArrayList<>();//职位
    private List<FilterMulSelectEntity> mMoreList = new ArrayList<>();//要求
    private  List<JobInfo> datas = new ArrayList<>();
    public static final int SUCCESSFUL=100;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_jobs;
    }

    @Override
    protected void initViews() {
        WidgetUtils.initRecyclerView(recyclerView, 0);
        recyclerView.setAdapter(mJobsCardViewListAdapter = new JobsCardViewListAdapter(getContext(),datas));
        initDatas();
        initPop();
    }

    @SuppressLint("HandlerLeak")
    private final Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESSFUL:
                    mJobsCardViewListAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            } }
    };

    private void initDatas() {
        new Thread(){
            @Override
            public void run() {
                try {
                    Response response = Api.jobsList();
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    int code = response.code();
                    if (code == 200){
                        //Log.d("webConnect_position", "成功"+ jsonArray);
                        for(int i = 0;i< jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            //Log.d("webConnect_position", String.valueOf(jsonObject));

                            String tag = jsonObject.getString("tags")
                                    .replace(" ", "");
                            List<String> tags= Arrays.asList(tag.split(","));
                            JobInfo job = new JobInfo(jsonObject.getInt("id"),
                                    jsonObject.getString("company"),
                                    jsonObject.getString("position"),tags,
                                    jsonObject.getString("price"),
                                    jsonObject.getString("like"),
                                    jsonObject.getString("pingjia"),
                                    jsonObject.getString("hr"),
                                    jsonObject.getString("more").replace("\\r\\n", ""),
                                    jsonObject.getString("request").replace("\\r\\n", "")
                                    );
                            //Log.d("webConnect_position", String.valueOf(job));
                            datas.add(job);
                        }

                        Message msg =new Message();
                        msg.what=SUCCESSFUL;
                        handler.sendMessage(msg);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    Log.d("webConnect_position", "请求失败"+e);
                }
            }
        }.start();
    }
    private void initPop() {
        mAreaList = DemoDataProvider.getArea(getActivity());
        mAllPriceList = JsonUtil.fromJson(DemoDataProvider.getJson(getActivity(), "city.json"), FilterEntity.class).getPrice();
        mPosition = JsonUtil.fromJson(DemoDataProvider.getJson(getActivity(), "city.json"), FilterEntity.class).getPosition();
        mMoreList = JsonUtil.fromJson(DemoDataProvider.getJson(getActivity(), "city.json"), FilterEntity.class).getMulSelect();

        FilterInfoBean bean_area = new FilterInfoBean("区域", FilterTabConfig.FILTER_TYPE_AREA, mAreaList);
        FilterInfoBean bean_price = new FilterInfoBean("薪资", FilterTabConfig.FILTER_TYPE_SINGLE_SELECT, mAllPriceList);
        FilterInfoBean bean_position = new FilterInfoBean("职位", FilterTabConfig.FILTER_TYPE_MUL_SELECT, mPosition);
        FilterInfoBean bean_ask = new FilterInfoBean("要求", FilterTabConfig.FILTER_TYPE_MUL_SELECT, mMoreList);

        ftb_filter.addFilterItem(bean_area.getTabName(), bean_area.getFilterData(), bean_area.getPopupType(), 0, false);
        ftb_filter.addFilterItem(bean_price.getTabName(), bean_price.getFilterData(), bean_price.getPopupType(), 1, false);
        ftb_filter.addFilterItem(bean_position.getTabName(), bean_position.getFilterData(), bean_position.getPopupType(), 2, false);
        ftb_filter.addFilterItem(bean_ask.getTabName(), bean_ask.getFilterData(), bean_ask.getPopupType(), 3, false);
    }

    //
    @Override
    protected void initListeners() {
        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> refreshLayout.getLayout().postDelayed(() -> {
            mJobsCardViewListAdapter.notifyDataSetChanged();
            refreshLayout.finishRefresh();
        }, 1000));
        //上拉加载
        refreshLayout.setOnLoadMoreListener(refreshLayout -> refreshLayout.getLayout().postDelayed(() -> {
            mJobsCardViewListAdapter.notifyDataSetChanged();
            refreshLayout.finishLoadMore();
        }, 1000));
        refreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果

        if(datas.size() > 2){
            mJobsCardViewListAdapter.setItemCellClicker(new JobsCardViewListAdapter.ItemCellClicker() {
                @Override
                public void onItemClick(String tag) {
                    //tag -> Toast.makeText(getActivity(), tag, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCardClick(View view, int position) {
                    Intent intent = new Intent(getActivity(), MoreActivity.class);
                    intent.putExtra("job", datas.get(position));
                    startActivity(intent);
                }
            });
        }

    }


}
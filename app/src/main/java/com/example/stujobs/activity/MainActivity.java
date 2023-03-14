package com.example.stujobs.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.example.stujobs.MyService;
import com.example.stujobs.R;
import com.example.stujobs.base.BaseActivity;
import com.example.stujobs.fragment.ChatFragment;
import com.example.stujobs.fragment.JobsFragment;
import com.example.stujobs.fragment.PersonalFragment;
import com.example.stujobs.utils.SettingSPUtils;
import com.google.android.material.tabs.TabLayout;
import com.umeng.analytics.MobclickAgent;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xutil.system.DeviceUtils;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    private MyService myService;
    private boolean isBound = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //登记
        MobclickAgent.onProfileSignIn(DeviceUtils.getAndroidID());
        initTab();
    }

    private void initTab() {
        WidgetUtils.addTabWithoutRipple(mTabLayout, "职位", SettingSPUtils.getInstance().isUseCustomTheme() ? R.drawable.tab_1 : R.drawable.tab_1);
        WidgetUtils.addTabWithoutRipple(mTabLayout, "消息", SettingSPUtils.getInstance().isUseCustomTheme() ? R.drawable.tab_2 : R.drawable.tab_2);
        WidgetUtils.addTabWithoutRipple(mTabLayout, "个人", SettingSPUtils.getInstance().isUseCustomTheme() ? R.drawable.tab_3 : R.drawable.tab_3);
        WidgetUtils.setTabLayoutTextFont(mTabLayout);

        switchPage(JobsFragment.class);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        switchPage(JobsFragment.class);
                        break;
                    case 1:
                        switchPage(ChatFragment.class);
                        break;
                    case 2:
                        switchPage(PersonalFragment.class);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder binder) {
            // 当服务连接成功时，获取服务的实例
            MyService.LocalBinder localBinder = (MyService.LocalBinder) binder;
            myService = localBinder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            // 当服务断开连接时，将服务实例置为空
            myService = null;
            isBound = false;
        }
    };
    @Override
    protected void onStart() {
        super.onStart();
        // 绑定服务
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 解绑服务
        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
    }

}
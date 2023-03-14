package com.example.stujobs;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.xuexiang.xpage.PageConfig;
import com.xuexiang.xpage.PageConfiguration;
import com.xuexiang.xpage.base.XPageActivity;
import com.xuexiang.xpage.config.AppPageConfig;
import com.xuexiang.xpage.model.PageInfo;

import java.util.List;

import io.rong.imkit.RongIM;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        PageConfig.getInstance()
                .setPageConfiguration(new PageConfiguration() { //页面注册
                    @Override
                    public List<PageInfo> registerPages(Context context) {
                        //自动注册页面,是编译时自动生成的，build一下就出来了。如果你还没使用@Page的话，暂时是不会生成的。
                        return AppPageConfig.getInstance().getPages(); //自动注册页面
                    }
                })
                .debug("PageLog")       //开启调试
                .setContainActivityClazz(XPageActivity .class) //设置默认的容器Activity
                .init(this);            //初始化页面配置
        RongIM.init((Application) getApplicationContext(), "pvxdm17jpf3nr");
    }
}

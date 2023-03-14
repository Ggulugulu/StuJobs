package com.example.stujobs;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.stujobs.adapter.entity.IntroInfo;
import com.example.stujobs.adapter.entity.JobInfo;
import com.example.stujobs.adapter.entity.filter.FilterAreaOneEntity;
import com.example.stujobs.adapter.entity.filter.FilterAreaTwoEntity;
import com.xuexiang.xaop.annotation.MemoryCache;
import com.xuexiang.xutil.net.JsonUtil;
import com.yangbin.base.BaseFilterBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 演示数据
 *
 * @author xuexiang
 * @since 2018/11/23 下午5:52
 */
public class DemoDataProvider {

    /**
     * 用于占位的空信息
     *
     * @return
     */
    @MemoryCache
    public static List<JobInfo> getDemoNewInfos() {
        List<JobInfo> list = new ArrayList<>();
        List<String> li = new ArrayList<>();

        li.add("c语言");
        li.add("杭州");
        li.add("Java开发");
        li.add("10k-15k");


        list.add(new JobInfo("10-12K/月", "Android开发")
                .setTags(li).setCompany("上海米哈游有限公司")
        );

        list.add(new JobInfo("10-12K/月", "Android开发")
                .setTags(Collections.singletonList("Java开发")).setCompany("上海米哈游有限公司")
        );

        return list;
    }
    public static List<Map<String, Object>> getMessInfo() {
        List<Map<String, Object>> list = new ArrayList<>();
        HashMap<String, Object > h = new HashMap<String, Object>();
        h.put("pic",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202107%2F11%2F20210711101603_fd40d.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1678948012&t=24463a9e005cd958be8a24bbe320d59b");
        h.put("title","南浩磊");
        h.put("content","薪资如何");
        h.put("time","中午12:30");
        h.put("code", "8.9");

        HashMap<String, Object > h1 = new HashMap<String, Object>();
        h1.put("pic",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202107%2F11%2F20210711101603_fd40d.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1678948012&t=24463a9e005cd958be8a24bbe320d59b");
        h1.put("title","南浩磊");
        h1.put("content","在吗？");
        h1.put("time","下午1:30");
        h1.put("code", "7");

        list.add(h);
        list.add(h1);
        return list;
    }
    public static List<JobInfo> getPraise() {
        List<JobInfo> list = new ArrayList<>();
        JobInfo h = new JobInfo();
        JobInfo h1 = new JobInfo();
        h.setMore_praise("这家公司不错");
        h.setMore_time("2023-02-20 15:30");
        h.setMore_count(12);

        h1.setMore_praise("平时加班很严重，工资也不是很合理");
        h1.setMore_time("2023-01-20 15:30");
        h1.setMore_count(3);


        list.add(h);
        list.add(h1);
        return list;
    }

    public static List<IntroInfo> getIntroInfo() {
        List<IntroInfo > list = new ArrayList<>();

        list.add(new IntroInfo("模拟简历1","/storage/emulated/0/Download/PDF_330178815.pdf"));
        list.add(new IntroInfo("模拟简历2","/storage/emulated/0/Download/证书.pdf"));
        return list;
    }

    public static List<BaseFilterBean> getArea(Context context) {
        List<BaseFilterBean> areas = new ArrayList<>();

        try{
            JSONArray area = JsonUtil.toJSONObject(getJson(context, "city.json")).getJSONArray("area");
            for(int i = 0;i < area.length();i++){
                List<FilterAreaTwoEntity> twos = new ArrayList<>();
                JSONObject jsonObject = (JSONObject) area.get(i);
                FilterAreaOneEntity one = new FilterAreaOneEntity();
                one.setName(jsonObject.getString("name"));

                JSONArray area_two = jsonObject.getJSONArray("childAreas");
                for(int j = 0;j < area_two.length();j++) {
                    JSONObject jsonObject1 = (JSONObject) area_two.get(j);
                    FilterAreaTwoEntity two = new FilterAreaTwoEntity();
                    two.setName(jsonObject1.getString("name"));
                    twos.add(two);
                }

                one.setFilterAreaEntityList(twos);

                areas.add(one);
                Log.d("area", String.valueOf(areas));
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return areas;
    }

    /**
     * 获取assets下的json文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context, String fileName) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName), "utf-8"));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }



}

package com.example.stujobs;

import android.util.Log;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Api {

    static OkHttpClient client=new OkHttpClient().newBuilder().build();
    static MediaType mediaType = MediaType.parse("application/json");
    static String BASE_URL="https://fa6eba68-e4ff-474f-9c73-9919e89ab771.mock.pstmn.io";

    public static Response login(String number, String password) throws IOException {
        final Request request=new Request.Builder()
                .url(BASE_URL + "/user/login?number=" + number + "&password=" + password)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }
    public static Response jobsList() throws IOException {
        final Request request=new Request.Builder()
                .url(BASE_URL + "/jobs/list")
                .get()
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }
    public static Response pingjiaList(int jobId) throws IOException {
        final Request request=new Request.Builder()
                .url(BASE_URL + "/pingjia/list/detail?jobId="+jobId)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        return response;

    }
    public static Response introduceList(int userId) throws IOException{
        final Request request=new Request.Builder()
                .url(BASE_URL + "/introduce/list?userId="+userId)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }
    public static Response sendList(int userId) throws IOException{
        final Request request=new Request.Builder()
                .url(BASE_URL + "/send/sendList?sendId="+userId)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }
    public static Response toList(int userId) throws IOException{
        final Request request=new Request.Builder()
                .url(BASE_URL + "/send/toList?toId="+userId)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public static Response PostAddSend(String send_id,String to_id,String job_id,String introduce_id) throws IOException{
        RequestBody requestBody = new FormBody.Builder()
                .add("id","")
                .add("sendId", send_id)
                .add("toId", to_id)
                .add("jobId", job_id)
                .add("introduceId", introduce_id)
                .add("result", "待处理")
                .build();
        Request request = new Request.Builder()
                .url(BASE_URL + "/send/addSend")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public static void PostUpdateSend(String id,String result){
        try{
            RequestBody requestBody = new FormBody.Builder()
                    .add("id", id)
                    .add("result", result)
                    .build();
            Request request = new Request.Builder()
                    .url(BASE_URL + "/send/updateResult")
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            Log.d("postSuccess", String.valueOf(response));
        }catch (IOException e){
            Log.d("post", String.valueOf(e));
        }
    }

}

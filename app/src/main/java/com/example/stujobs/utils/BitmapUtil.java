package com.example.stujobs.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author huangrui
 * @date 2018/8/31 10:57
 * @desc
 */
public class BitmapUtil {
    private PicHandler pic_hdl;
    private ImageView imgView;
    private String url;

    /**
     * 通过图片url路径获取图片并显示到对应控件上
     *
     *
     *
     */
    public void setImage(ImageView imgView, String url) {
        this.url = url;
        this.imgView = imgView;
        pic_hdl = new PicHandler();
        Thread t = new LoadPicThread();
        t.start();
    }

    class LoadPicThread extends Thread {
        @Override
        public void run() {
            Bitmap img = getUrlImage(url);
            System.out.println(img + "---");
            Message msg = pic_hdl.obtainMessage();
            msg.what = 0;
            msg.obj = img;
            pic_hdl.sendMessage(msg);
        }
    }

    class PicHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            Bitmap myimg = (Bitmap) msg.obj;
            imgView.setImageBitmap(myimg);
        }

    }

    public Bitmap getUrlImage(String url) {
        Bitmap img = null;
        try {
            URL picurl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) picurl
                    .openConnection();
            conn.setConnectTimeout(6000);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.connect();
            InputStream is = conn.getInputStream();
            img = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }


     public static ImageSize getImageSize(Bitmap bitmap) {
        ImageSize imageSize = new ImageSize();
        if (null == bitmap || bitmap.isRecycled()) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteTmp = baos.toByteArray();
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(byteTmp, 0, byteTmp.length, bitmapOptions);
        int outWidth = bitmapOptions.outWidth;
        int outHeight = bitmapOptions.outHeight;
        int maxWidth = 400;
        int maxHeight = 400;
        int minWidth = 150;
        int minHeight = 150;
        if (outWidth / maxWidth > outHeight / maxHeight) {//
            if (outWidth >= maxWidth) {//
                imageSize.setWidth(maxWidth);
                imageSize.setHeight(outHeight * maxWidth / outWidth);
            } else {
                imageSize.setWidth(outWidth);
                imageSize.setHeight(outHeight);
            }
            if (outHeight < minHeight) {
                imageSize.setHeight(minHeight);
                int width = outWidth * minHeight / outHeight;
                if (width > maxWidth) {
                    imageSize.setWidth(maxWidth);
                } else {
                    imageSize.setWidth(width);
                }
            }
        } else {
            if (outHeight >= maxHeight) {
                imageSize.setHeight(maxHeight);
                imageSize.setWidth(outWidth * maxHeight / outHeight);
            } else {
                imageSize.setHeight(outHeight);
                imageSize.setWidth(outWidth);
            }
            if (outWidth < minWidth) {
                imageSize.setWidth(minWidth);
                int height = outHeight * minWidth / outWidth;
                if (height > maxHeight) {
                    imageSize.setHeight(maxHeight);
                } else {
                    imageSize.setHeight(height);
                }
            }
        }

        return imageSize;
    }






}

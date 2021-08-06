package com.example.constellation.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.constellation.bean.StarInfoBean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 读取assets文件夹中数据的工具类
 */
public class AssetsUtil {

    private static Map<String,Bitmap> logoIamMap;

    private static Map<String,Bitmap> contentLogoIamMap;

    /*
        读取assets文件夹中的数据，存放到字符串中
     */
    public static String getJsonFromAssets(Context context,String fileName){
        //1.获取Assets文件夹管理器
        AssetManager assets = context.getResources().getAssets();
        //创建一个内存流，用来存放读取到的内容
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //2.获取文件输入流
        try {
            InputStream is = assets.open(fileName);
            //读取输入流的内容存放到内存流
            int hasRead = 0;
            byte[] buf = new byte[1024];
            while (true) {
                hasRead = is.read(buf);
                if (hasRead == -1) {
                    break;
                }
                //写入内存流
                baos.write(buf,0,hasRead);
            }
            is.close();
            Log.e("MainActivity1",baos.toString("utf8"));
            return baos.toString("utf8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*读取assets文件夹中图片，返回Bitmap对象*/
    public static Bitmap getBitmapFromAssets(Context context,String fileName) {
        Bitmap bitmap = null;
        //获取文件夹管理者
        AssetManager assets = context.getResources().getAssets();
        try {
            InputStream is = assets.open(fileName);
            //通过位图管理器，将输入流转化成位图对象
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /*
    * 将assets文件夹下的图片一起读取，放置到内存中，便于管理
    * */
    public static void saveBitmapFromAssets(Context context, StarInfoBean starInfoBean){
        logoIamMap = new HashMap<>();
        contentLogoIamMap = new HashMap<>();
        List<StarInfoBean.StarinfoBean> starList = starInfoBean.getStarinfo();
        for (int i = 0; i < starList.size(); i++) {
            String logoName = starList.get(i).getLogoname();
            String fileName = "xzlogo/" + logoName + ".png";
            Bitmap bitmap = getBitmapFromAssets(context, fileName);
            logoIamMap.put(logoName,bitmap);

            String contentName = "xzcontentlogo/" + logoName + ".png";
            Bitmap contentBitmap = getBitmapFromAssets(context, contentName);
            contentLogoIamMap.put(logoName,contentBitmap);
        }
    }

    public static Map<String, Bitmap> getLogoIamMap() {
        return logoIamMap;
    }

    public static Map<String, Bitmap> getContentLogoIamMap() {
        return contentLogoIamMap;
    }
}

package com.example.yolov5tfliteandroid.web;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class network
{
    public static String Server = "http://43.143.146.203/";
    public static MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static OkHttpClient client = new OkHttpClient();
    public static String GET(String url,PPCallBack ppCallBack) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String ans=response.body().string();
            ppCallBack.success(new JSONObject(ans));
            return ans;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String POST(String url, String json) {
        RequestBody body = RequestBody.create( json,JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /*mappost没有callback封装，导致同步请求冲突弃用*/
/*    public static JSONObject mapPOST(String url, Map<String, String> map) {
        final JSONObject jsonObject = new JSONObject();
        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                jsonObject.put(entry.getKey(), entry.getValue());
            }
            String ans = POST(url, jsonObject.toString());
            return new JSONObject(ans);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/

      public static void ppmapPOST(final String url, Map<String, String> map,PPCallBack ppCallBack) {
        final JSONObject jsonObject = new JSONObject();
        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                jsonObject.put(entry.getKey(), entry.getValue());
            }
            String ans = POST(url, jsonObject.toString());
            ppCallBack.success(new JSONObject(ans));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

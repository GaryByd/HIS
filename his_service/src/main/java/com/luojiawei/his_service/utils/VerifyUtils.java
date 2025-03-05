package com.luojiawei.his_service.utils;



import okhttp3.*;

import java.io.IOException;



public class VerifyUtils {

    private static final String URL = "https://idenauthen.market.alicloudapi.com/idenAuthentication";
    private static final String APPCODE = "ecd2ab77c07f406eb31bf936b4f359b2";

    public static String verifyIdentity(String name, String idNo) throws IOException {
        return postData(APPCODE, URL, name, idNo);
    }

    private static String postData(String appCode, String url, String name, String idNo) throws IOException {
        String result = null;
        RequestBody formBody = new FormBody.Builder().add("name", name).add("idNo", idNo).build();
        Request request = new Request.Builder().url(url).addHeader("Authorization", "APPCODE " + appCode).post(formBody).build();

        Call call = new OkHttpClient().newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            System.out.println("execute failed, message:" + e.getMessage());
        }

        assert response != null;
        if (!response.isSuccessful()) {
            System.out.println("request failed----" + "返回状态码" + response.code()  + ",message:" + response.message());
        }
        result = response.body().string();
        System.out.println("response:" + result);
        return result;
    }

}
package com.lnyp.listviewoptimize;

import android.content.Context;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;

/**
 * 网络请求工具类
 *
 * @author lining
 */
public class HttpUtil {
    /**
     * 请求的根URL地址
     */
    public static final String BASE_URL = "http://www.imooc.com/api/teacher?type=4&num=50";

    public static void sendRequest(final Context context,
                                   final HttpMethod method, RequestParams params,
                                   final IOAuthCallBack iOAuthCallBack) {
        HttpUtils http = new HttpUtils();

        http.configCurrentHttpCacheExpiry(1000 * 5);
        // 设置超时时间
        http.configTimeout(5 * 1000);
        http.configSoTimeout(5 * 1000);

        if (method == HttpMethod.GET) {
            http.configCurrentHttpCacheExpiry(5000); // 设置缓存5秒,5秒内直接返回上次成功请求的结果。
        }

        http.send(method, BASE_URL, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                        LogUtils.d(method.name() + " request is onStart.......");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.d("statusCode:" + responseInfo.statusCode + " ----->" + responseInfo.result);
                        iOAuthCallBack.getIOAuthCallBack(responseInfo.result);// 利用接口回调数据传输
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        LogUtils.d("statusCode:" + error.getExceptionCode() + " -----> " + msg);
                        iOAuthCallBack.getIOAuthCallBack("FF");// 利用接口回调数据传输
                    }
                });
    }
}
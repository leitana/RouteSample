package com.boco.routesample.utils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson mGson;//gson对象
    private final TypeAdapter<T> adapter;

    /**
     * 构造器
     */
    public JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.mGson = gson;
        this.adapter = adapter;
    }

    /**
     * 转换
     *
     * @param responseBody
     * @return
     * @throws IOException
     */
    @Override
    public T convert(ResponseBody responseBody) throws IOException {

        String response = responseBody.string();

//        String strResult = response.substring(1, response.length() - 1);
//        String result = XXTEA.Decrypt(strResult, HttpConstant.KEY);//解密
//        Log.i("xiaozhang", "解密的服务器数据：" + result);
//        PageBean pageBean = mGson.fromJson(result, PageBean.class);
//        return (T) pageBean;

//        String decrypt = AES.decrypt(response);
//        return (T) mGson.fromJson(decrypt, CommMsgResponse.class);

//        JSONObject jsonObject = JSONObject.parseObject(decrypt);
//        String str = jsonObject.get("dataList").toString();
        String decrypt = AES.decrypt(response);
        return (T) decrypt;
    }

}
package com.hzitshop.dingding;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Token {
    private Logger logger = LogManager.getLogger(Token.class);
    @Value("${corpId}")
    private String corpId;
    @Value("${corpSecret}")
    private String corpSecret;

    private static final JSONObject jsonObject ;
    private static HttpClient  httpClient = null;
    static {
        jsonObject = new JSONObject();
        httpClient = HttpClients.createDefault();
    }

    public  JSONObject getToken(){
        if(jsonObject.get("access_token") == null){
            //说明没有token数据,请求获取数据
            requestToken();
        }else{
            long startTime = (long)jsonObject.get("startTime");
            System.out.println("开始时间:"+startTime);
            System.out.println(System.currentTimeMillis() -startTime);
            //9124943
            //
            if((System.currentTimeMillis() -startTime) >6800*1000){//超时设置
                requestToken();
            }
        }
        return jsonObject;
    }

    private void requestToken() {
        String url ="https://oapi.dingtalk.com/gettoken?corpid="+corpId+"&corpsecret="+corpSecret;
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse resp = httpClient.execute(httpGet);
            String result = EntityUtils.toString(resp.getEntity(),"utf-8");
            JSONObject jo = JSONObject.parseObject(result);
            jsonObject.put("access_token",jo.get("access_token"));
            jsonObject.put("startTime",System.currentTimeMillis());
            String tickerUrl =   "https://oapi.dingtalk.com/get_jsapi_ticket?access_token="+jsonObject.get("access_token");
            httpGet = new HttpGet(tickerUrl);
            resp=httpClient.execute(httpGet);
            String ticketJson = EntityUtils.toString(resp.getEntity(),"utf-8");
            JSONObject ticketObject = JSONObject.parseObject(ticketJson);
            jsonObject.put("ticket",ticketObject.get("ticket"));
            logger.info("获取新的token 和ticket! ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package com.hzitshop.task;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.CorpMessageCorpconversationAsyncsendRequest;
import com.dingtalk.api.response.CorpMessageCorpconversationAsyncsendResponse;
import com.hzitshop.dingding.LinkMsg;
import com.hzitshop.dingding.Token;
import com.hzitshop.entity.CustomerInfo;
import com.hzitshop.service.ICustomerInfoService;
import com.hzitshop.service.IEmployeeInfoService;
import com.taobao.api.ApiException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import  java.util.Map;
/**
 * 定时任务
 * Created by xianyaoji on 2017/3/7.
 */
@Component
public class MyJob {
    @Autowired
    private ICustomerInfoService customerInfoService;

    @Autowired
    private IEmployeeInfoService employeeInfoService;

    @Autowired
    private Token token;

    @Value("${mainUrl}")
    private String url;

    @Value("${agentId}")
    private long agentId;
    @Scheduled(cron="0 15 7-16 * * ?")
    public void execute() throws JobExecutionException {
        //查询客户表所设置的跟进时间
        System.out.println("我的任务!!");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String traceTime = sdf.format(new Date());
        Map<String,Object>  paramMap = new HashMap<>();
        paramMap.put("traceTime",traceTime);
        List<Map<String,Object>> resultMap = customerInfoService.traceData(paramMap);
        //发送消息
        for(Map<String,Object> map : resultMap){
            DingTalkClient client = new DefaultDingTalkClient("https://eco.taobao.com/router/rest");
            CorpMessageCorpconversationAsyncsendRequest req = new CorpMessageCorpconversationAsyncsendRequest();
            req.setMsgtype("link");
            req.setAgentId(agentId);
            req.setUseridList((String)map.get("userId"));
            //req.setDeptIdList("123,456");
            req.setTimestamp(System.currentTimeMillis());
            req.setToAllUser(false);
            //req.setDeptIdList("58081116");
            LinkMsg linkMsg = new LinkMsg(url,
                    "@lALOACZwe2Rk",
                    "营销系统",
                    traceTime+"\n亲，您今天有"+map.get("count")+"个学员需要跟进");
            req.setMsgcontentString(JSON.toJSONString(linkMsg));
            //System.out.println(JSON.toJSON(linkMsg));
            CorpMessageCorpconversationAsyncsendResponse rsp = null;
            try {
                rsp = client.execute(req,(String)token.getToken().get("access_token"));
                System.out.println("结果:"+rsp.getBody());
                //System.out.println(map.get("userId"));
            } catch (ApiException e) {
                e.printStackTrace();
            }

        }
        //查询
       // System.out.println(resultMap);
        //获取员工的钉钉编号
        /*DingTalkClient client = new DefaultDingTalkClient("https://eco.taobao.com/router/rest");
        CorpMessageCorpconversationAsyncsendRequest req = new CorpMessageCorpconversationAsyncsendRequest();
        req.setMsgtype("text");
        req.setAgentId(163860100L);
        req.setUseridList("162750303521146294");
        //req.setDeptIdList("123,456");
        req.setToAllUser(false);
        req.setTimestamp(System.currentTimeMillis());*/
        //req.setMsgcontentString("{\"message_url\": \"http://dingtalk.com\",\"head\": {\"bgcolor\": \"FFBBBBBB\",\"text\": \"头部标题\"},\"body\": {\"title\": \"正文标题\",\"form\": [{\"key\": \"姓名:\",\"value\": \"张三\"},{\"key\": \"爱好:\",\"value\": \"打球、听音乐\"}],\"rich\": {\"num\": \"15.6\",\"unit\": \"元\"},\"content\": \"大段文本大段文本大段文本大段文本大段文本大段文本大段文本大段文本大段文本大段文本大段文本大段文本\",\"image\": \"@lADOADmaWMzazQKA\",\"file_count\": \"3\",\"author\": \"李四 \"}}");
       /*req.setMsgcontentString("{\n" +
                "        \"messageUrl\": \"http://xianyaoji.natapp1.cc/mobile/index\",\n" +
                "        \"picUrl\":\"@lALOACZwe2Rk\",\n" +
                "        \"title\": \"营销系统\",\n" +
                "        \"text\": \"亲，您今天有五个学员需要跟进!\"\n" +
                "    }");*/
        /*req.setMsgcontentString("{\"content\": \"张三的请假申请\"}");
        CorpMessageCorpconversationAsyncsendResponse rsp = null;
        try {
            rsp = client.execute(req,(String)token.getToken().get("access_token"));
            System.out.println(token.getToken().get("access_token"));
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());*/

    }


}

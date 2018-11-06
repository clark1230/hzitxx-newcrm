package com.hzitshop.web.controllers;

import com.dingtalk.oapi.lib.aes.DingTalkEncryptException;
import com.dingtalk.oapi.lib.aes.DingTalkJsApiSingnature;
import com.hzitshop.dingding.Token;
import com.hzitshop.entity.CustomerInfo;
import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.service.ICustomerInfoService;
import com.hzitshop.service.IEmployeeInfoService;
import com.hzitshop.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import  java.util.Map;
@Controller
public class MobileController {
    @Autowired
    private IEmployeeInfoService employeeInfoService;

    @Autowired
    private ICustomerInfoService customerInfoService;

    @Autowired
    private Token token;

    @Value("${agentId}")
    private Long agentId;

    @Value("${corpId}")
    private String corpId;

    @Value("${mainUrl}")
    private String url;

    @RequestMapping(value = "/error",method = RequestMethod.GET)
    public String error(){
        return "/mobile/error";
    }

    /**
     * 获取签名
     * @return
     */
    @GetMapping("/mobile/getSign")
    @ResponseBody
    public Object getSign() throws DingTalkEncryptException {
        Map<String,Object> map = new HashMap<>();
        long currTime = System.currentTimeMillis();
        map.put("agentId",agentId);
        map.put("timeStamp",currTime);
        map.put("corpId",corpId);
        map.put("nonceStr","nonceStr");
        System.out.println("ticket:"+token.getToken().getString("ticket"));
        String sign = DingTalkJsApiSingnature.getJsApiSingnature(url, "nonceStr",
                currTime,(String) token.getToken().get("ticket"));
        map.put("signature",sign);
        return map;

    }

    /**
     * 检查是否有权限
     * @param corpId
     * @param emplId
     * @return
     */
    @PostMapping("/mobile/checkPermission")
    @ResponseBody
    public Object checkPermission(String corpId,String emplId){
        System.out.println(corpId);
        System.out.println(emplId);
        Map<String,Object> map = new HashMap<>();
        map.put("dingding_id",emplId);
        List<EmployeeInfo> employeeInfos =  employeeInfoService.selectByMap(map);
        if(this.corpId.equals(corpId) && employeeInfos.size() ==1){
            return ServerResponse.createBySuccess();//成功
        }else{
            return ServerResponse.createByError();//失败
        }

    }

    /**
     * 会销
     * @return
     */
    @GetMapping("/mobile/huiXiao")
    @ResponseBody
    public Object huiXiao(String emplId,HttpSession httpSession){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String,Object> paramsMap = new HashMap<>();
        EmployeeInfo ei = (EmployeeInfo)httpSession.getAttribute("employeeInfo");
        if(!ei.getRoleName().contains("管理员")|| !ei.getRoleName().contains("总经理")){
            paramsMap.put("companyId",ei.getCompanyId());
        }
        paramsMap.put("date",sdf.format(new Date()));
        return customerInfoService.mobileHuiXiao(paramsMap);
    }

    /**
     * 修改数据
     * @param ci
     * @return
     */
    @PostMapping("/mobile/updateMarket")
    @ResponseBody
    public Object updateMarket(CustomerInfo ci, HttpSession session){
        EmployeeInfo ei = (EmployeeInfo)session.getAttribute("employeeInfo");
        if(!"0".equals(ci.getIsMarket())){
            ci.setIsMarket(ei.getName());
        }else{
            ci.setIsMarket("0");
        }
        return this.customerInfoService.updateSelectiveById(ci);
    }
}

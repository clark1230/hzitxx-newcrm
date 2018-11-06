package com.hzitshop.web.controllers;

import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.service.ICustomerInfoService;
import com.hzitshop.service.ICustomerTraceRecordService;
import com.hzitshop.service.IEmployeeInfoService;
import com.hzitshop.util.ServerResponse;
import com.hzitshop.vo.WelcomeVo;
import com.hzitshop.vo.huixiao.MobileHuiXiaoVo;
import com.hzitshop.vo.trace.DailyTraceDataVo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xianyaoji on 2017/2/7.
 */
@Controller
public class IndexController {
    @Autowired
    private ICustomerInfoService customerInfoService;
    @Autowired
    private ICustomerTraceRecordService traceRecordService;
    @Autowired
    private IEmployeeInfoService employeeInfoService;

    /**
     * 跳转到系统主页
     * @return
     */
    @RequestMapping(value={"/","/index"},method = RequestMethod.GET)
    public String index(){
        /**
         * 用户登录时获取用户所能访问的app图标
         */
        return "index2";
    }

    /**
     * 跳转到系统移动端首页
     * @return
     */
    @GetMapping("/mobile/index")
    public String mobileIndex(Model model,HttpSession httpSession){
        EmployeeInfo ei = (EmployeeInfo) httpSession.getAttribute("employeeInfo");
        Map<String,Object> paramsMap = new HashMap<>();
        //查询该用户所拥有的移动端角色
        //查询数据
        System.out.println(ei.getRoleName());
        if(ei.getRoleName().contains("会销")){
            //展示会销数据
            System.out.println("会销数据-----");
            paramsMap.put("companyId",ei.getCompanyId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            paramsMap.put("date",sdf.format(new Date()));
            ServerResponse serverResponse = customerInfoService.mobileHuiXiao(paramsMap);
            model.addAttribute("mobilehuiXiaoVos",serverResponse.getData());
            model.addAttribute("menu",2);
        }else if(ei.getRoleName().contains("咨询")){
            System.out.println("咨询数据....");
            paramsMap.put("userId",ei.getUserId());
            List<DailyTraceDataVo> traceDataVos =traceRecordService.selectDailyTraceData(paramsMap);
            model.addAttribute("traceDataVos",traceDataVos);
            //展示咨询数据
            model.addAttribute("menu",1);
        }else if(ei.getRoleName().contains("管理员")|| ei.getRoleName().contains("总经理")){
            //默认展示咨询数据
            paramsMap.put("userId",null);
            List<DailyTraceDataVo> traceDataVos =traceRecordService.selectDailyTraceData(paramsMap);
            model.addAttribute("traceDataVos",traceDataVos);
            model.addAttribute("menu",1);
        }

        return "mobile/index";
    }

    /**
     * 获取咨询数据
     * @param httpSession
     * @return
     */
    @GetMapping("/mobile/follow")
    @ResponseBody
    public Object follow(HttpSession httpSession){
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("userId",null);
        EmployeeInfo ei = (EmployeeInfo)httpSession.getAttribute("employeeInfo");
        if(ei.getRoleName().contains("咨询")){
            paramsMap.put("userId",ei.getUserId());
        }
        List<DailyTraceDataVo> traceDataVos =traceRecordService.selectDailyTraceData(paramsMap);
        return  ServerResponse.createBySuccess(traceDataVos);
    }
    /**
     * 跳转到系统欢迎页
     * @param model
     * @return
     */
    @RequestMapping("/welcome")
    public String welcome(Model model, HttpSession httpSession,
                          @RequestParam(value = "page",defaultValue = "1")int page,
                          @RequestParam(value = "limit",defaultValue = "10")int limit){
        WelcomeVo welcomeVo = customerInfoService.totalCount();
        model.addAttribute("welcomeVo",welcomeVo);
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("offset",(page-1)*limit);
        paramMap.put("limit",limit);
        EmployeeInfo employeeInfo = (EmployeeInfo)httpSession.getAttribute("employeeInfo");
        if(employeeInfo.getRoleName().contains("咨询")){//只能看到自己的数据

        }else if("管理员".equals(employeeInfo.getRoleName())|| employeeInfo.getRoleName().contains("总经理")){

        }else if("市场经理".equals(employeeInfo.getRoleName())){

        }
         return "welcome";
    }


}

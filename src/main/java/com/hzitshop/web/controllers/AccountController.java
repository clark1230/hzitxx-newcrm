package com.hzitshop.web.controllers;

import com.hzitshop.config.DefaultUsernamePasswordToken;
import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.service.IEmployeeInfoService;
import com.hzitshop.util.Captcha;
import com.hzitshop.util.ServerResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xianyaoji on 2017/2/10.
 */
@Controller
public class AccountController {
    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private IEmployeeInfoService iEmployeeInfoService;

    @Value("${corpId}")
    private String corpId;

    /**
     * 跳转到登陆页面
     * @param request
     * @return
     */
    @RequestMapping(value="/account/login",method = RequestMethod.GET)
    public String login(HttpServletRequest request){
        if(this.judgeIsMoblie(request)){
            return "/mobile/login";
        }else{
            return "login2";
        }
    }


    /**
     *
     * 获取登录验证码!
     * @param response
     */
    @RequestMapping("/account/captcha")
    public  void captcha(HttpServletResponse response, HttpServletRequest request){
        if(request != null && response != null){
            Captcha.init(response,request).render();
        }
    }

    /**
     * 处理登录请求!
     * @return
     */
    @RequestMapping(value ="/account/login",method = RequestMethod.POST)
    @ResponseBody
    public Object login(EmployeeInfo employeeInfo,HttpServletRequest request) throws Exception {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        Subject subject = SecurityUtils.getSubject();//从SecurityUtils中获取主体对象
        if(this.judgeIsMoblie(request)){
            return this.mobile(request);
        }else{
            return pc(employeeInfo, request, resultMap, subject);
        }
    }

    /**
     * pc端登陆
     * @param employeeInfo
     * @param request
     * @param resultMap
     * @param subject
     * @return
     */
    private Map<String, Object> pc(EmployeeInfo employeeInfo, HttpServletRequest request,
                                   Map<String, Object> resultMap, Subject subject) {
        DefaultUsernamePasswordToken token = new DefaultUsernamePasswordToken(employeeInfo.getName(),
                employeeInfo.getPassword());
        token.setLoginType("pc");//pc端登陆
        try{
            subject.login(token);
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("name",employeeInfo.getName());
            paramMap.put("isLocked","0");
            List<EmployeeInfo> employeeInfoList = iEmployeeInfoService.selectByMap(paramMap);
            HttpSession httpSession = request.getSession();
            if(employeeInfoList!=null && employeeInfoList.size() >0){
                employeeInfo = employeeInfoList.get(0);
                employeeInfo.setPassword("");
            }
            httpSession.setAttribute("employeeInfo",employeeInfo);
            resultMap.put("status",200);
            resultMap.put("message", "登录成功!");
        }catch (AuthenticationException e){
            logger.error("------------用户登录出错----------------"+e.getMessage());
            resultMap.put("status",300);
            resultMap.put("message", "登录失败!");
        }
        return resultMap;
    }

    /**
     * 手机端登陆
     * @return
     */
    private Object mobile(HttpServletRequest request){
        System.out.println("手机端登陆....");
        String corpId = request.getParameter("corpId");
        String emplId = request.getParameter("emplId");
        String username = request.getParameter("username");
        if(!this.corpId.equals(corpId)){
            return ServerResponse.createByErrorCodeMessage(1,"corpId不正确!");
        }
        DefaultUsernamePasswordToken token = new DefaultUsernamePasswordToken(username,emplId);
        token.setLoginType("mobile");//移动端登陆
        Subject subject = SecurityUtils.getSubject();
       try{
           subject.login(token);
           //保存用户信息到session中
           HttpSession session = request.getSession();
           Map<String,Object> resultMap = new HashMap<>();
           resultMap.put("dingding_id",emplId);
           List<EmployeeInfo> employeeInfos = iEmployeeInfoService.selectByMap(resultMap);
           session.setAttribute("employeeInfo",employeeInfos.get(0));
       }catch ( Exception e){
           System.out.println("------------移动端登陆失败！---------------");
           return ServerResponse.createByErrorCodeMessage(1,"登陆失败!用户名或者密码错误!");
       }
       return ServerResponse.createBySuccessMessage("登陆成功!");

    }

    //判断是否为手机浏览器
    private  boolean judgeIsMoblie(HttpServletRequest request) {
        boolean isMoblie = false;
        String[] mobileAgents = { "iphone", "android","ipad", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
                "opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
                "nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
                "docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
                "techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
                "wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
                "pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
                "240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
                "blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
                "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
                "mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
                "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
                "smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
                "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
                "Googlebot-Mobile" };
        if (request.getHeader("User-Agent") != null) {
            String agent=request.getHeader("User-Agent");
            for (String mobileAgent : mobileAgents) {
                if (agent.toLowerCase().indexOf(mobileAgent) >= 0&&agent.toLowerCase().indexOf("windows nt")<=0 &&agent.toLowerCase().indexOf("macintosh")<=0) {
                    isMoblie = true;
                    break;
                }
            }
        }
        return isMoblie;
    }
    /**
     * 系统注销!
     * @return
     */
    @RequestMapping(value = "/account/logout")
    public String logout(HttpServletRequest request){
        SecurityUtils.getSubject().logout();//系统注销!!
        return "redirect:/account/login";//跳转到登录页
    }
    /**
     * 非法操作
     * @return
     */
    @RequestMapping("/account/unauthor")
    public  String unauthor(){
        return "unauthor";
    }

}

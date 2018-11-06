package com.hzitshop.web.controllers;


import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.entity.Signup;
import com.hzitshop.util.LayuiEntity;
import com.hzitshop.util.ServerResponse;
import com.hzitshop.vo.signup.SignupVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.hzitshop.service.ISignupService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xianyaoji
 * @since 2018-03-12
 */
@Controller
@RequestMapping("/signup")
public class SignupController  extends  BaseController{
    @Autowired
    private ISignupService signupService;
    /**
    * 跳转页面
    */
    @RequiresPermissions("signup:signup")
    @GetMapping(value="/signup")
    public String signup(){
         return "signup/signup";
    }

    /**
     * 分页
     * @return
     */
    @GetMapping(value="signupAjax")
    @ResponseBody
    public LayuiEntity<SignupVo> signupAjax(@RequestParam(value = "page",defaultValue = "1") int page,
                                            @RequestParam(value = "limit",defaultValue = "20") int  limit,
                                            String value,Signup signup,String name, HttpSession httpSession){
        Map<String,Object>  map = new HashMap<>();
        map.put("isDelete",0);
        if(StringUtils.isNotEmpty(value)){
            map.put("realName",value);
        }

        if(signup.getStatus()!=-1){
            map.put("status",signup.getStatus());

        }
        if(StringUtils.isNotEmpty(name)){
            map.put("name",name);
        }
        EmployeeInfo ei = (EmployeeInfo)httpSession.getAttribute("employeeInfo");
        String roleName = ei.getRoleName();
        if(roleName.contains("咨询")){
            map.put("companyId",ei.getCompanyId());
            if("咨询师".equals(roleName)){
                map.put("userId",ei.getUserId());
            }
        }else if(roleName.contains("总经理")|| roleName.contains("管理员")){

        }else{
            map.put("companyId",ei.getCompanyId());
        }
        LayuiEntity<SignupVo> layuiEntity = signupService.page(page,limit,map);
        return layuiEntity;
    }

    /**
     * 跳转到编辑页面
     * @param id
     * @param realName
     * @param model
     * @return
     */
    @RequiresPermissions("signup:editSigup")
    @GetMapping("editSignup")
    public String editSignup(Integer id,String realName,Model model){
        model.addAttribute("signup",signupService.findOne(id));
        model.addAttribute("realName",realName);
        return "signup/editSignup";
    }

    /**
     * 保存编辑数据
     * @param signup
     * @param model
     * @return
     */
    @PostMapping("editSignup")
    public String editSignup(Signup signup,Model model){
        String view;
        ServerResponse serverResponse = signupService.updateSelectiveById(signup);
        if(!serverResponse.isSuccess()){
            view = "product/signup/editSignup";
        }else{
            view = "redirect:signup";
        }
        return view;
    }

    /**
     * 逻辑删除数据
     * @return
     */
    @RequiresPermissions("signup:updateDeleteStatus")
    @GetMapping("/updateDeleteStatus")
    @ResponseBody
    public Object updateDeleteStatus(Signup signup){
        return this.signupService.updateSelectiveById(signup);
    }
}

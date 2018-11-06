package com.hzitshop.web.controllers;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hzitshop.entity.TbDict;
import com.hzitshop.service.ICustomerInfoService;
import com.hzitshop.service.IEmployeeInfoService;
import com.hzitshop.service.ITbDictService;
import com.hzitshop.vo.CompanyCount;
import com.sun.javafx.sg.prism.NGShape;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xianyaoji on 2017/3/6.
 */
@Controller
public class StatisticsController {

    @Autowired
    private ITbDictService iTbDictService;
    @Autowired
    private ICustomerInfoService iCustomerInfoService;
    @Autowired
    private IEmployeeInfoService iEmployeeInfoService;

    /**
     * 跳转到数据统计页面
     * @return
     */
    @RequiresPermissions("statistics:index")
    @RequestMapping(value="/statistics/index")
    public  String index(Model model){
        //获取所有分公司信息
        List<TbDict> tbDictList = iTbDictService.selectList(new EntityWrapper<TbDict>().where("pid=35"));
        model.addAttribute("tbDictList",tbDictList);
        //获取学员状态
       // List<TbDict> customerStateList =  iTbDictService.selectList(new EntityWrapper<TbDict>().where("pid=27"));
        //model.addAttribute("customerStateList",customerStateList);
        return "/statistics/index";
    }

    /**
     * 
     * @return
     */
    @RequestMapping(value="/statistics/companyCount")
    @ResponseBody
    public  List<CompanyCount> companyCount(String companyId,String month){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("companyId",companyId);
        paramMap.put("month",month);
        List<CompanyCount> companyCounts = iCustomerInfoService.companyCount(paramMap);
        return companyCounts;
    }

    /**
     * 获取每月每个校区每个咨询师的报名量
     * @param companyId
     * @param month
     * @return
     */
    @RequestMapping(value="/statistics/baoming")
    @ResponseBody
    public  List<CompanyCount> baoming(String companyId,String month){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("companyId",companyId);
        paramMap.put("month",month);
        List<CompanyCount> baomingList = iCustomerInfoService.baoming(paramMap);
        return baomingList;
    }

    /**
     * 跳转到创量部门每月应聘渠道信息统计主页
     * @return
     */
    @RequiresPermissions("statistics:recruitChannel")
    @RequestMapping(value="/statistics/recruitChannel",method = RequestMethod.GET)
    public  String recruitChannel(Model model){
        //获取所有分公司信息
        List<TbDict> tbDictList = iTbDictService.selectList(new EntityWrapper<TbDict>().where("pid=35"));
        model.addAttribute("tbDictList",tbDictList);
        return "/statistics/recruitChannel";
    }

    /**
     * 获取创量统计数据
     * @param companyId
     * @param month
     * @return
     */
    @RequestMapping(value="/statistics/recruitChannel",method = RequestMethod.POST)
    @ResponseBody
    public  List<CompanyCount>  recruitChannel(String companyId,String month){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("companyId",companyId);
        paramMap.put("month",month);
        List<CompanyCount> companyCounts = iCustomerInfoService.recruitChannelMonthData(paramMap);
        return companyCounts;
    }

    /**
     * 统计每月每个分公司每个创量人员的邀约人数
     * @param companyId
     * @param month
     * @return
     */
    @RequestMapping(value="/statistics/chuangliangMonthData")
    @ResponseBody
    public  List<CompanyCount> chuangliangMonthData(String companyId,String month){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("companyId",companyId);
        paramMap.put("month",month);
        List<CompanyCount> companyCounts = iCustomerInfoService.chuangliangMonthData(paramMap);
        return companyCounts;
    }
}

package com.hzitshop.web.controllers;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hzitshop.entity.CustomerInfo;
import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.entity.TbDict;
import com.hzitshop.service.ICustomerInfoService;
import com.hzitshop.service.IEmployeeInfoService;
import com.hzitshop.service.ITbDictService;
import com.hzitshop.vo.BootstrapEntity;
import com.hzitshop.vo.BootstrapTable;
import com.hzitshop.vo.CustomerInfoVo;
import com.hzitshop.vo.EmployeeInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 运营管理
 * Created by xianyaoji on 2017/3/29.
 */
@Controller
public class BusinessController {
    @Autowired
    private ICustomerInfoService iCustomerInfoService;
    @Autowired
    private ITbDictService iTbDictService;
    @Autowired
    private IEmployeeInfoService iEmployeeInfoService;
    /**
     * 运营主页
     * @return
     */
    @RequestMapping("/business/index")
    public String index(){
        return "/business/index";
    }

    /**
     * 异步获取数据
     * @return
     */
    @RequestMapping("/business/getAjaxData")
    @ResponseBody
    public BootstrapTable<CustomerInfoVo> getAjaxData(BootstrapEntity bt, CustomerInfo customerInfo,
                                                      EmployeeInfo ei, HttpServletRequest request){
        HttpSession httpSession = request.getSession();
        EmployeeInfo employeeInfo = (EmployeeInfo)httpSession.getAttribute("employeeInfo");
        if (bt.getOffset() == null || bt.getLimit() == null) {
            bt.setOffset(1);
            bt.setLimit(20);
        } else {
            bt.setOffset(bt.getOffset() / bt.getLimit());
        }
        if(customerInfo.getIsDelete() == null){
            customerInfo.setIsDelete(0);   //默认显示非逻辑删除数据
        }
        Wrapper wrapper = null;
        //运营部
        List<EmployeeInfo> employeeInfoList = iEmployeeInfoService.selectList(new EntityWrapper<EmployeeInfo>().where("dept_id=86"));
        StringBuilder sb = new StringBuilder();
        if(employeeInfoList!= null && employeeInfoList.size() >0){
            for(EmployeeInfo employeeInfo1  : employeeInfoList){
                sb.append("'"+employeeInfo1.getUserId()+"'"+",")  ;
            }
        }
        String userIds = sb.toString().substring(0,sb.toString().lastIndexOf(","));
        if(employeeInfo.getRoleName().contains("运营")){
            wrapper = new EntityWrapper<CustomerInfo>().where("introducer = '"+employeeInfo.getUserId()+"'");
        } else{    //管理员
            wrapper = new EntityWrapper<CustomerInfo>().where("introducer in("+userIds+")");
        }
         BootstrapTable<CustomerInfoVo> bootstrapTable = iCustomerInfoService.ajaxData(
                 new Page<>(bt.getOffset(),bt.getLimit()),wrapper);
        return bootstrapTable;
    }

    /**
     * 为学员分配咨询师
     * @return
     */
    @RequestMapping("/business/grant")
    public String grant(Model model,int companyId,int customerId){
        List<TbDict>  companyList = iTbDictService.selectList(new EntityWrapper<TbDict>().where("pid=35"));
        List<EmployeeInfo> employeeInfos = iEmployeeInfoService.selectList(new EntityWrapper<EmployeeInfo>()
        .where("dept_id=74").and("company_id="+companyId));
        model.addAttribute("employeeInfoList",employeeInfos);
        model.addAttribute("companyList",companyList);
        model.addAttribute("customerId",customerId);
        return "/business/grant";
    }

    
}

package com.hzitshop.web.controllers;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hzitshop.entity.CustomerInfo;
import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.entity.TbDict;
import com.hzitshop.service.IBusinessService;
import com.hzitshop.service.ICustomerInfoService;
import com.hzitshop.service.IEmployeeInfoService;
import com.hzitshop.service.ITbDictService;
import com.hzitshop.util.ServerResponse;
import com.hzitshop.vo.BootstrapEntity;
import com.hzitshop.vo.BootstrapTable;
import com.hzitshop.vo.CustomerInfoVo;
import com.hzitshop.vo.EmployeeInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

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
    @Autowired
    private IBusinessService iBusinessService;
    /**
     * 运营主页
     * @return
     */
    @RequiresPermissions("business:index")
    @RequestMapping("/business/index")
    public String index(){
        return "/business/index";
    }

    /**
     * 运营公共库
     * @return
     */
    @RequiresPermissions("business:common")
    @GetMapping("/business/common")
    public String common(){
        return "/business/common";
    }
    /**
     * 异步获取数据
     * @return
     */
    @RequestMapping("/business/getAjaxData")
    @ResponseBody
    public BootstrapTable<CustomerInfoVo> getAjaxData(BootstrapEntity bt,
                                                      CustomerInfo customerInfo,
                                                      EmployeeInfo ei,
                                                      HttpServletRequest request,
                                                      int common,
                                                      String condition,
                                                      String value){
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
        Wrapper wrapper = new EntityWrapper()
                .where("customer_type=1")
                .and("isDelete=0");

        if(common == 1){
          wrapper.and("yunying= 0");
        }else if(common == 0){
            if(employeeInfo.getRoleName().contains("运营")){
                wrapper.and("yunying="+employeeInfo.getUserId());
            }else{
                wrapper.and("yunying!=0");

            }
        }
        if(StringUtils.isNotEmpty(condition)){
            wrapper.like(condition,value);
        }
        wrapper.orderBy("create_time",false);
        //运营部
        BootstrapTable<CustomerInfoVo> bootstrapTable = iCustomerInfoService.ajaxData(
                 new Page<>(bt.getOffset(),bt.getLimit()),wrapper);
        return bootstrapTable;
    }

    /**
     * 获取运营人员数据
     * @return
     */
    @GetMapping("/business/getEmployee")
    @ResponseBody
    public Object getEmployee(){
        Wrapper<EmployeeInfo> employeeInfoWrapper = new EntityWrapper<EmployeeInfo>()
                .where("isLocked=0")
                .like("role_name","运营");
        List<EmployeeInfo> employeeInfoList = this.iEmployeeInfoService.selectList(employeeInfoWrapper);
        if(employeeInfoList!= null && employeeInfoList.size() >0){
            return ServerResponse.createBySuccess(employeeInfoList);
        }
        return ServerResponse.createByErrorMessage("获取运营人员信息失败!");
    }

    /**
     * 运营分配数据
     * @return
     */
    @RequiresPermissions("business:grant")
    @PostMapping("/business/grant")
    @ResponseBody
    public Object grant(Integer[] customerIdArr,int userId){
        CustomerInfo ci = null;
        try{
            for(Integer id: customerIdArr){
                ci = new CustomerInfo();
                ci.setCustomerId(id);
                ci.setYunying(userId);
                ci.setCustomerType(1);
                ServerResponse serverResponse = iCustomerInfoService.updateSelectiveById(ci);
                System.out.println(serverResponse.getStatus());
            }
            return ServerResponse.createBySuccessMessage("操作成功!");
        }catch (Exception e){
            return ServerResponse.createByErrorMessage("操作失败!");
        }
    }

    /**
     * 跳转到添加页面
     * @return
     */
    @GetMapping("/business/add")
    public String add(ModelMap modelMap, EmployeeInfo ei,HttpServletRequest request){
        HttpSession httpSession = request.getSession();
        EmployeeInfo employeeInfo = (EmployeeInfo)httpSession.getAttribute("employeeInfo");
        if(ei.getCompanyId()==null){
            this.modelMap(modelMap,employeeInfo.getCompanyId());
        }  else{
            this.modelMap(modelMap,ei.getCompanyId());
        }
        return "business/add";
    }


    /**
     * 根据父编号获取数据字典信息
     *
     * @param pid
     * @return
     */
    private List<TbDict> getTbgDict(String pid) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pid", pid);
        return iTbDictService.selectByMap(paramMap);
    }

    public void modelMap(ModelMap modelMap,Integer companyId) {
        modelMap.addAttribute("customerStateList", this.getTbgDict("27"));//学员状态
        modelMap.addAttribute("targetSkillList", this.getTbgDict("2"));//目标技能
        List<TbDict> tbDictList = null;
        /*if(isYunYing!= null && isYunYing ==1){
            tbDictList = this.getTbgDict("79");
            // modelMap.addAttribute("recruitChannelList", this.getTbgDict("21"));//应聘渠道
        } else{
            tbDictList = this.getTbgDict("21");
            // modelMap.addAttribute("recruitChannelList",this.getTbgDict("79"));  //运营渠道
        }*/
        modelMap.addAttribute("recruitChannelList",tbDictList);   //应聘渠道
        modelMap.addAttribute("customerLevelList", this.getTbgDict("16"));//学员级别
        modelMap.addAttribute("educationBgList", this.getTbgDict("7")); //获取学历信息
        if(companyId !=null  && companyId!=0 ){
        }  else{
            List<EmployeeInfo> employeeInfos = iEmployeeInfoService.selectList(
                    new EntityWrapper<EmployeeInfo>().
                            where("isConsultant=1").
                            and("isLocked='0'"));//获取所有关单人
            if(employeeInfos!= null && employeeInfos.size() >0){
                for (EmployeeInfo employeeInfo : employeeInfos){
                    TbDict tbDict  = iTbDictService.selectById(employeeInfo.getCompanyId());
                    employeeInfo.setName(employeeInfo.getName()+"----"+tbDict.getName());
                }
            }
            modelMap.addAttribute("guandanList", employeeInfos);//获取所有关单人
           /* if(isYunYing!= null && isYunYing ==1){
                employeeInfos = iEmployeeInfoService.selectList(
                        new EntityWrapper<EmployeeInfo>()
                                .where("dept_id=86")
                                .and("isLocked='0'")); //获取创量数据
            } else{
                employeeInfos = iEmployeeInfoService.selectList(new EntityWrapper<EmployeeInfo>()
                        .where("dept_id=76").and("isLocked='0'"));
            }
*/
            if(employeeInfos!= null && employeeInfos.size() >0){
                for (EmployeeInfo employeeInfo : employeeInfos){
                    TbDict tbDict  = iTbDictService.selectById(employeeInfo.getCompanyId());
                    employeeInfo.setName(employeeInfo.getName()+"----"+tbDict.getName());
                }
            }
            modelMap.addAttribute("introducerList",
                    employeeInfos);//获取所有邀约人
        }


    }

    /**
     * 还原数据
     * @return
     */
    @RequiresPermissions("business:recover")
    @PostMapping("/business/recover")
    @ResponseBody
    public ServerResponse recover(Integer[] customerIdArr){
        return iBusinessService.recover(customerIdArr);
    }

    /**
     * 将数据让放入公共库中
     * @param customerIdArr
     * @return
     */
    @RequiresPermissions("business:trunToCommon")
    @PostMapping("/business/trurnToCommon")
    @ResponseBody
    public ServerResponse trunToCommon(Integer[] customerIdArr){
        if(customerIdArr!= null && customerIdArr.length >0){
            try{
                List<CustomerInfo> customerInfoList = new ArrayList<>();
                CustomerInfo ci = null;
                for(Integer id : customerIdArr){
                    ci = new CustomerInfo();
                    ci.setCustomerId(id);
                    ci.setYunying(0);
                    ServerResponse result = this.iCustomerInfoService.updateSelectiveById(ci);
                    System.out.println(result.isSuccess());
                }
                return  ServerResponse.createBySuccessMessage("操作成功!");
            }catch (Exception e){
                e.printStackTrace();
                return  ServerResponse.createByErrorMessage("操作失败!");
            }
        }else{
            return  ServerResponse.createByErrorMessage("操作失败!");
        }
    }
}

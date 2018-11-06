package com.hzitshop.web.controllers;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.entity.ImportInfo;
import com.hzitshop.entity.Settings;
import com.hzitshop.entity.TbDict;
import com.hzitshop.service.IEmployeeInfoService;
import com.hzitshop.service.ITbSettingsService;
import com.hzitshop.service.ImportInfoService;
import com.hzitshop.service.ITbDictService;
import com.hzitshop.util.ServerResponse;
import com.hzitshop.vo.*;

import com.hzitshop.vo.employeevo.EmployeeVoNameId;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-09-08 16:33
 * @description
 */
@Controller
public class ImportInfoController {

    @Autowired
    private ImportInfoService importInfoServiceImpl;

    @Autowired
    private ITbDictService iTbDictServiceImpl;

    @Autowired
    private IEmployeeInfoService iEmployeeInfoService;

    @Autowired
    private ITbSettingsService settingsService;

    private Logger logger = LoggerFactory.getLogger(ImportInfoController.class);


    /**
     * 导入Excel
     */
    @RequiresPermissions(value = {"importInfo:import"})
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> importExcel(@RequestParam("excelFile") MultipartFile file,
                                          String recruitChannel,
                                          Integer cvType,
                                          HttpServletRequest request,
                                          HttpSession session) throws IOException {
        Map<String,Object> resultMap = new HashMap<>();
        String result = importInfoServiceImpl.importExcel(file.getInputStream(),recruitChannel,cvType,session);
        if(result != null){
            resultMap.put("code",200);
            resultMap.put("msg",result);
            return resultMap;
        }else{
            resultMap.put("code", 300);
            resultMap.put("msg", "文件导入失败!");
        }
        return resultMap;
    }

    /**
     * 跳转到数据导入页面
     * @param model
     * @param session
     * @return
     */
    @RequiresPermissions(value={"import:importList"})
    @RequestMapping("/import/importList")
    public String toImportList(Model model,HttpSession session){
        this.getIntroducerList(model,session);
        List<TbDict> eBgList = this.getTbgDict("7");
        model.addAttribute("eBgList",eBgList);
        return "/import/importList";
    }

    @RequestMapping("/import/importPage")
    public String toImportPage(ModelMap modelMap){
        List<TbDict> recruitChannelList = this.getTbgDict("21");
        modelMap.addAttribute("recruitChannelList",recruitChannelList);
        return "/import/importPage";
    }

    /**
     * 根据父编号获取数据字典信息
     *
     * @param pid
     * @return
     */
    protected List<TbDict> getTbgDict(String pid) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pid", pid);
        return iTbDictServiceImpl.selectByMap(paramMap);
    }
    @RequiresPermissions(value={"import:recycleBin"})
    @RequestMapping("/import/recycleBin")
    public String toRecycleBin(){ return "/import/recycleBin"; }

    @RequestMapping("/import/editMemo")
    public String toEditMemo(ModelMap modelMap, ImportInfo importInfo){
        importInfo = importInfoServiceImpl.selectOne(new EntityWrapper<ImportInfo>().where("customer_id=" + importInfo.getCustomerId()));
        modelMap.addAttribute("importInfo", importInfo);
        return "/import/editMemo";
    }

    /**
     * 保存修改的备注信息
     * @return
     */
    @RequestMapping("/importInfo/editMemo")
    @ResponseBody
    public Map<String,Object> editMemo(ImportInfo importInfo){
        Map<String, Object> resultMap = new HashMap<>();
        ImportInfo im = new ImportInfo();
        im = importInfoServiceImpl.selectOne(new EntityWrapper<ImportInfo>().where("customer_id=" + importInfo.getCustomerId()));
        im.setMemo(importInfo.getMemo());
        im.setLastTime(new Date());
        boolean result = importInfoServiceImpl.updateSelectiveById(im);
        if(result){
            resultMap.put("code",200);
            resultMap.put("msg","备注修改成功!");
        }else{
            resultMap.put("code",300);
            resultMap.put("msg","备注修改失败!");
        }
        return resultMap;
    }

    /**
     * 查询已逻辑删除的数据（公共库）
     * @param bt
     * @param session
     * @return
     */
    @RequestMapping(value = "/importInfo/recycleData")
    @ResponseBody
    public BootstrapTable<ImportInfoVo> RecycleData(BootstrapEntity bt,HttpSession session){
        if (bt.getOffset() == null || bt.getLimit() == null) {
            bt.setOffset(1);
            bt.setLimit(20);
        } else {
            bt.setOffset(bt.getOffset() / bt.getLimit());
        }
        if("create_time".equals(bt.getCondition())){
            bt.setCondition("DATE_FORMAT(create_time,'%Y-%m-%d')");
        }
        Page<ImportInfo> searchPage = new Page<ImportInfo>(bt.getOffset(), bt.getLimit());

        Wrapper<ImportInfo> ew = new EntityWrapper<ImportInfo>()
                .where("isDelete=1")
                .like(bt.getCondition(), bt.getValue())
                .orderBy("last_time desc");
        if("-1".equals(bt.getCondition()) ){
            bt.setCondition("");
        }


        BootstrapTable<ImportInfoVo> bootstrapTable = importInfoServiceImpl.ajaxData(searchPage,ew);
        return bootstrapTable;
    }

    /**
     * 逻辑删除
     * @param importIdArr
     * @return
     */
    @RequestMapping(value = "/importInfo/remove")
    @ResponseBody
    public Map<String,Object> remove(String importIdArr, HttpSession session){
        String condition = "isDelete";
        return getStringObjectMap(importIdArr,condition, session);
    }

    /**
     * 恢复逻辑删除的数据
     * @param importIdArr
     * @return
     */
    @RequestMapping(value = "/importInfo/recover")
    @ResponseBody
    public Map<String,Object> recover(String importIdArr, HttpSession session){
        String condition = "isDelete=0";
        return getStringObjectMap(importIdArr,condition,session);
    }


    /**
     *彻底删除
     * @param importIdArr
     * @return
     */
    @RequiresPermissions(value = {"importInfo:delete"})
    @RequestMapping(value = "/importInfo/delete")
    @ResponseBody
    public Map<String,Object> delete(String importIdArr){
        Map<String, Object> resultMap = new HashMap<>();
        String[] idArr = null;
        List<String> listIds = new ArrayList<>();
        if(importIdArr!=null && !"".equals(importIdArr)){
            idArr = importIdArr.split(",");
        }
        if(idArr != null){
            for(String id : idArr){
                listIds.add(id);
            }
            boolean result = importInfoServiceImpl.deleteBatchIds(listIds);
            if(result){
                resultMap.put("code",200);
                resultMap.put("msg","删除成功!");
            }else{
                resultMap.put("code",300);
                resultMap.put("msg","删除失败!");
            }
        }
        return resultMap;
    }

    /**
     * 导入列表分页查询
     * @param bt
     * @param session
     * @return
     */
    @RequestMapping(value = "/importInfo/listData")
    @ResponseBody
    public BootstrapTable<ImportInfoVo> listData(BootstrapEntity bt,HttpSession session){
        if (bt.getOffset() == null || bt.getLimit() == null) {
            bt.setOffset(1);
            bt.setLimit(20);
        } else {
            bt.setOffset(bt.getOffset() / bt.getLimit());
        }
        String value = null;
        if("apply_job".equals(bt.getCondition())){
            Settings settings =  settingsService.findOne(1);
            String[] arr= settings.getValue().split(",");
            StringBuilder sb = new StringBuilder();
            for(String str: arr){
                sb.append("'"+str+"',");
            }
            value = sb.toString().substring(0,sb.toString().length()-1);
            //查询数据
//            if("1".equals(bt.getValue())){
//                bt.setValue(" "+value+"");
//            }else if("2".equals(bt.getValue())){
//                bt.setValue("("+value+")");
//            }
        }

        Page<ImportInfo> searchPage = new Page<ImportInfo>(bt.getOffset(), bt.getLimit());
        EmployeeInfo em = (EmployeeInfo) session.getAttribute("employeeInfo");
        Wrapper<ImportInfo> ew = null;
        if("创量经理".equals(em.getRoleName()) || "管理员".equals(em.getRoleName())
                || "运营".equals(em.getRoleName())
                || "总经理".equals(em.getRoleName())){
            if("apply_job".equals(bt.getCondition())){
                if("1".equals(bt.getValue())){
                    ew = new EntityWrapper<ImportInfo>()
                            .where("isDelete=0")
                            .in(bt.getCondition(), value)
                            .orderBy("create_time desc");
                }else{
                    ew = new EntityWrapper<ImportInfo>()
                            .where("isDelete=0")
                            .notIn(bt.getCondition(), value)
                            .orderBy("create_time desc");
                }
            }else{
                ew = new EntityWrapper<ImportInfo>()
                        .where("isDelete=0")
                        .and((bt.getIntroducer() == null)? "1=1":"introducer="+bt.getIntroducer())
                        .like(bt.getCondition(), bt.getValue())
                        .orderBy("create_time desc");
            }
        }else if("创量主管".equals(em.getRoleName())){
            //根据当前登录用户所在校区筛选导入列表
             if("apply_job".equals(bt.getCondition())){
                 if("1".equals(bt.getValue())){
                     ew = new EntityWrapper<ImportInfo>()
                             .where("company_id="+em.getCompanyId())
                             .and("isDelete=0")
                             .in(bt.getCondition(), value)
                             .orderBy("create_time desc");
                 }else{
                     ew = new EntityWrapper<ImportInfo>()
                             .where("company_id="+em.getCompanyId())
                             .and("isDelete=0")
                             .notIn(bt.getCondition(), value)
                             .orderBy("create_time desc");
                 }
             }else{
                 ew = new EntityWrapper<ImportInfo>()
                         .where("company_id="+em.getCompanyId())
                         .and("isDelete=0")
                         .and((bt.getIntroducer() == null)? "1=1":"introducer="+bt.getIntroducer())
                         .like(bt.getCondition(), bt.getValue())
                         .orderBy("create_time desc");
             }
        }else{
            //查询创量人员负责的学员
            if("apply_job".equals(bt.getCondition())){
               if("1".equals(bt.getValue())){
                   ew = new EntityWrapper<ImportInfo>()
                           .where("introducer = " +em.getUserId())
                           .and("isDelete=0")
                           .in(bt.getCondition(), value)
                           .orderBy("create_time desc");
               }else{
                   ew = new EntityWrapper<ImportInfo>()
                           .where("introducer = " +em.getUserId())
                           .and("isDelete=0")
                           .notIn(bt.getCondition(),value)
                           .orderBy("create_time desc");
               }
            }else{
                ew = new EntityWrapper<ImportInfo>()
                        .where("introducer = " +em.getUserId())
                        .and("isDelete=0")
                        .like(bt.getCondition(), bt.getValue())
                        .orderBy("create_time desc");
            }
        }

        if("-1".equals(bt.getCondition()) ){
            bt.setCondition("");
        }
        BootstrapTable<ImportInfoVo> bootstrapTable = importInfoServiceImpl.ajaxData(searchPage,ew);
        return bootstrapTable;

    }

    /**
     * 前台检测是否存在该简历数据
     * @param importInfo
     * @return
     */
    @RequestMapping("/import/checkImportInfo")
    @ResponseBody
    public Map<String,Object> checkImportInfo(ImportInfo importInfo){
        ImportInfo i = null;
        Map<String,Object> resultMap = new HashMap<>();
        i = importInfoServiceImpl.selectOne(new EntityWrapper<ImportInfo>()
                .where("tel="+importInfo.getTel())
                .and("company_id="+importInfo.getCompanyId()));
        if(i == null){
            resultMap.put("code",300);
            resultMap.put("msg","系统暂未导入该用户简历,请手动录入!");
        }else {
            i.setCreateTime(null);
            resultMap.put("code",200);
            resultMap.put("msg","简历已导入,请核对用户姓名后再录入!");
            resultMap.put("importInfo",i);
        }
        return resultMap;
    }


    /**
     * 根据id批量操作导入表importInfo
     * @param importIdArr
     * @param condition
     * @return
     */
    private Map<String, Object> getStringObjectMap(String importIdArr, String condition, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>();
        EmployeeInfo em = (EmployeeInfo) session.getAttribute("employeeInfo");
        try {
            ImportInfo importInfo = null;
            if (StringUtils.isNotEmpty(importIdArr)) {
                String[] idArr = importIdArr.split(",");
                for (String id : idArr) {
                    importInfo = new ImportInfo();
                    importInfo.setCustomerId(Integer.parseInt(id));
                    if ("isDelete".equals(condition)) {
                        importInfo.setIsDelete(1);//逻辑删除数据
                    }else if("isDelete=0".equals(condition)){
                        importInfo = importInfoServiceImpl.selectOne(new EntityWrapper<ImportInfo>().where("customer_id=" + importInfo.getCustomerId()));
                        //恢复->同步当前登录用户信息
                        Date date = new Date();
                        importInfo.setIntroducer(em.getUserId().toString());
                        importInfo.setCompanyId(em.getCompanyId());
                        importInfo.setLastTime(date);
                        importInfo.setIsDelete(0);//恢复逻辑删除的数据
                        if(importInfo.getMemo() == null || "".equals(importInfo.getMemo())){
                            importInfo.setMemo("该数据从公共库恢复");
                        }else if(!importInfo.getMemo().contains("该数据从公共库恢复")){
                            importInfo.setMemo("该数据公共库恢复"+importInfo.getMemo());
                        }
                    }
                    importInfoServiceImpl.update(importInfo,
                            new EntityWrapper<ImportInfo>().where("customer_id=" + importInfo.getCustomerId()));
                }
            }
            resultMap.put("code", 200);
            resultMap.put("msg", "操作成功!");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code", 300);
            resultMap.put("msg", "操作失败!");
        }
        return resultMap;
    }

    /**
     * 分配创量人员
     * @param userId
     * @param customerId
     * @return
     */
    @RequestMapping(value = "/import/allotIntroducer",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> toAllotIntroducer(Integer userId,Integer ... customerId){
        Map<String,Object> resultMap = new HashMap<>();
        boolean result = false;
        for(Integer id : customerId){
            ImportInfo info = new ImportInfo();
            info.setCustomerId(id);
            info.setIntroducer(userId + "");
            result = importInfoServiceImpl.updateSelectiveById(info);
        }
        if(result){
            resultMap.put("code",200);
            resultMap.put("msg","保存成功!");
        }else {
            resultMap.put("code",300);
            resultMap.put("msg","保存失败,请稍后再试!");
        }
        return resultMap;
    }

    /**
     * 跳转分配创量人员页面
     * @param model
     * @param customerIds
     * @return
     */
    @RequestMapping(value = "/import/allotIntroducer",method = RequestMethod.GET)
    public String toAllotIntroducer(Model model,HttpSession session,Integer ... customerIds){
        model.addAttribute("customerIds",customerIds);
        this.getIntroducerList(model,session);
        return "/import/allotIntroducer";
    }
    /**
     * 跳转到修改数据页面
     * @param importInfo
     * @param modelMap
     * @return
     */
    @RequestMapping("/import/edit")
    public String toEdit(ImportInfo importInfo,ModelMap modelMap,HttpSession session){
        EmployeeInfo em = (EmployeeInfo) session.getAttribute("employeeInfo");
        if(importInfo.getCustomerId() != null){
             importInfo = importInfoServiceImpl.selectById(importInfo.getCustomerId());
        }
        modelMap.addAttribute("educationBgList", this.getTbgDict("7")); //获取学历信息
        modelMap.addAttribute("customerStateList", this.getTbgDict("27"));//学员状态
        modelMap.addAttribute("targetSkillList", this.getTbgDict("2"));//目标技能
        modelMap.addAttribute("recruitChannelList",this.getTbgDict("21")); //应聘渠道
        Wrapper wrapper = null;
        if("创量经理".equals(em.getRoleName()) || "管理员".equals(em.getRoleName())){
            wrapper = new EntityWrapper<EmployeeInfo>()
                    .where("isLocked=0")
                    .like("role_name","创量");
        }else{
            wrapper = new EntityWrapper<EmployeeInfo>().
                    where("company_id=" + em.getCompanyId())
                    .and("isLocked=0")
                    .like("role_name","创量");
        }
        List<EmployeeInfo> yaoyue = iEmployeeInfoService.selectList(wrapper);
        List<TbDict> companyList = this.getTbgDict("35");
        for(EmployeeInfo employeeInfo : yaoyue){
            for(TbDict dict : companyList){
                if(dict.getId() == employeeInfo.getCompanyId())
                employeeInfo.setName(employeeInfo.getName()+"---"+dict.getName());
            }
        }
        modelMap.addAttribute("introducerList",yaoyue );//邀约人
        modelMap.addAttribute("companyList",this.getTbgDict("35") );//校区
        modelMap.addAttribute("editImportInfo",importInfo);
        return "/import/edit";
    }

    /**
     * 保存修改的数据
     * @param importInfo
     * @return
     */
    @RequestMapping(value = "/import/editImportInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editImportInfo(ImportInfo importInfo){
        Map<String, Object> resultMap = new HashMap<>();
        boolean result = false;
        if(importInfo.getCustomerId() != null){
            importInfo.setLastTime(new Date());
            result = importInfoServiceImpl.updateSelectiveById(importInfo);
        }
        if(result){
            resultMap.put("code", 200);
            resultMap.put("msg", "操作成功!");
        }else{
            resultMap.put("code", 300);
            resultMap.put("msg", "操作失败!");
        }
        return resultMap;
    }




    /**
     * 获取创量人员
     * @param model
     * @param session
     */
    private void getIntroducerList(Model model,HttpSession session){
        EmployeeInfo user = (EmployeeInfo) session.getAttribute("employeeInfo");
        List<EmployeeInfo> introdecerList = null;
        Integer companyId = user.getCompanyId();
        if("创量经理".equals(user.getRoleName())){
            introdecerList = iEmployeeInfoService.selectList(new EntityWrapper<EmployeeInfo>()
                    .where("role_name like '%创量%'").and("isLocked !=1"));
        }else{
            if(companyId != null && companyId != 0){
                //获取登录用户所在公司的创量人员
                introdecerList = iEmployeeInfoService.selectList(new EntityWrapper<EmployeeInfo>()
                        .where("company_id = "+companyId)
                        .and("role_name like '%创量%'")
                .and("isLocked !=1"));
            }else {
                //获取所有公司的创量人员
                introdecerList = iEmployeeInfoService.selectList(new EntityWrapper<EmployeeInfo>()
                        .where("role_name like '%创量%'")
                        .and("isLocked !=1"));
            }
        }
        for(EmployeeInfo e : introdecerList){
            TbDict company = iTbDictServiceImpl.selectOne(new EntityWrapper<TbDict>()
                    .where("id = " + e.getCompanyId()));
            e.setName(e.getName());
        }
        model.addAttribute("introdecerList",introdecerList);
    }

    /**
     * 跳转到数据去重页面
     * @return
     */
    @GetMapping("/import/filterData")
    public String filterData(HttpSession httpSession,Model model){
        EmployeeInfo ei = (EmployeeInfo)httpSession.getAttribute("employeeInfo");
        Map<String,Object> map = new HashMap<>();
        map.put("companyId",ei.getCompanyId());
        List<ImportInfoFilterData> importInfoFilterDataList = importInfoServiceImpl.filterData(map);
        model.addAttribute("importInfoFilterData",importInfoFilterDataList);
        return "import/filterData";
    }

    /**
     * 逻辑删除数据
     * @param status
     * @return
     */
    @RequiresPermissions("import:updateByStatus")
    @GetMapping("/import/updateByStatus")
    @ResponseBody
    public Object deleteById(@RequestParam("status")int status,
                             @RequestParam("customerId") int customerId,
                             @RequestParam("realName") String realName){
        Map<String,Object> map = new HashMap<>();
        map.put("isDelete",status);
        map.put("customerId",customerId);
        map.put("realName",realName);
        Map<String,Object> resultMap  = new HashMap<>();
        return importInfoServiceImpl.deleteByStatus(map);
    }

    /**
     * 数据过滤
     * @return
     */
    @RequiresPermissions("import:filterData")
    @GetMapping("/import/filterData2")
    public String filterData2(HttpSession httpSession,Model model){
        EmployeeInfo employeeInfo = (EmployeeInfo)httpSession.getAttribute("employeeInfo");
        //获取公司的所有未删除的创量人员信息
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("companyId",employeeInfo.getCompanyId());
        paramMap.put("like","创量%");
        List<EmployeeVoNameId> employeeVoNameIds  = iEmployeeInfoService.selectByRole(paramMap);
        model.addAttribute("employeeVoNameIds",employeeVoNameIds);
        return "import/filterData2";
    }

    /**
     * 获取最近一周的重复
     * @return
     */
    @GetMapping("/import/checkDailyImportInfo")
    @ResponseBody
    public Object checkDailyImportInfo(HttpSession httpSession,
                                       @RequestParam(value = "page",defaultValue = "1") int page,
                                       @RequestParam(value = "limit",defaultValue = "20") int limit,
                                       @RequestParam(value = "introducer",defaultValue = "") String introducer,
                                       @RequestParam(value = "realName",defaultValue = "") String realName){
        Map<String,Object> paramMap = new HashMap<>();
        EmployeeInfo ei = (EmployeeInfo)httpSession.getAttribute("employeeInfo");
        paramMap.put("companyId",ei.getCompanyId());
        paramMap.put("offset",(page-1)*limit);
        paramMap.put("limit",limit);
        //paramMap.put("c")
        if(!"".equals(introducer)){
            paramMap.put("introducer",introducer);
        }
        if(!"".equals(realName)){
            paramMap.put("realName",realName+"%");
        }
        JSONObject jsonObjectList = importInfoServiceImpl.checkDailyImportInfo(paramMap);
        return jsonObjectList;
    }

    /**
     * 设置颜色
     * @param customerId
     * @param color
     * @return
     */
    @GetMapping("/import/setColor")
    @ResponseBody
    public Object setColor(String customerId,String color){
        String[] customerIdArr = customerId.split(",");
        List<ImportInfo> importInfoList = new ArrayList<>();
        for(String id: customerIdArr){
            ImportInfo importInfo = new ImportInfo();
            importInfo.setCustomerId(Integer.parseInt(id));
            importInfo.setColor(color);
            importInfoList.add(importInfo);
        }
        boolean  result= importInfoServiceImpl.updateBatchById( importInfoList);
        if(result){
            return ServerResponse.createBySuccessMessage("成功!");
        }else{
            return ServerResponse.createByErrorCodeMessage(1,"失败!");
        }

    }
    @ExcelEntity
    private List<ImportInfo> importInfoList;
    /**
     *
     * 批量导出数据
     */
    @RequiresPermissions("import:exportExcel")
    @GetMapping("/import/exportExcel")
    public void exportExcel(HttpServletResponse resp,@RequestParam(value = "date") String date){
        OutputStream outputStream = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = date+"--应聘数据.xlsx";
        Wrapper<ImportInfo> wrapper = new EntityWrapper<ImportInfo>()
                .where("DATE_FORMAT(create_time,'%Y-%m-%d')='"+date+"'");
        //List<ImportInfo> importInfoList = importInfoServiceImpl.selectList(wrapper);
        try{
            resp.setHeader("Content-disposition", "attachment; filename="+
                    new String(fileName.getBytes("utf-8"),"ISO-8859-1"));// 设定输出文件头
            resp.setContentType("application/msexcel");// 定义输出类型
            outputStream = resp.getOutputStream();
            Workbook workbook = this.importInfoServiceImpl.exportExcel(wrapper);
            workbook.write(outputStream);
//            outputStream.flush();
//            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("创量数量导出失败!"+e.getMessage());
        }
    }

    /**
     * 将创量数据转到运营中
     * @return
     */
    @GetMapping("/import/turnToYunYing")
    @ResponseBody
    public Object turnToYunYing(Integer[] customerIdArr,HttpSession httpSession){

        return this.importInfoServiceImpl.turnToYunYing(customerIdArr,
                (EmployeeInfo)httpSession.getAttribute("employeeInfo"));
    }
}

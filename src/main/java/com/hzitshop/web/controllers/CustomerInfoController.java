package com.hzitshop.web.controllers;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.hzitshop.entity.*;
import com.hzitshop.service.*;
import com.hzitshop.vo.BootstrapTable;
import com.hzitshop.vo.BootstrapEntity;
import com.hzitshop.vo.CustomerInfoVo;
import com.hzitshop.vo.ExportExcelEntity;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-12
 */
@Controller
//@RequiresAuthentication //表示当前类需要进行授权
public class CustomerInfoController {
    @Autowired
    private ICustomerInfoService iCustomerInfoService;
    @Autowired
    private IEmployeeInfoService iEmployeeInfoService;
    @Autowired
    private ITbDictService iTbDictService;
    @Autowired
    private ICustomerTraceRecordService iCustomerTraceRecordService;

    @Autowired
    private IClassinfoService iClassinfoService;
    @Autowired
    private  IStudentinfoService iStudentinfoService;

    @Autowired
    private  ITbRoleService iTbRoleService;

    @Autowired
    private  ITbMenuAppService iTbMenuAppService;


    private Logger logger = LoggerFactory.getLogger(CustomerInfoController.class);

    /**
     * 跳转到学员列表页
     *
     * @return
     */
   /* @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)*/
    @RequiresPermissions(value="customerInfo:list")
    @RequestMapping("/customerInfo/list")
    protected String list() {
        return "/customerinfo/list";
    }

    /**
     * 获取学员数据
     *
     * @return
     */
    @RequiresPermissions(value = {"customerInfo:listData"})
    @RequestMapping("/customerInfo/listData")
    @ResponseBody
    protected BootstrapTable<CustomerInfoVo> listData(BootstrapEntity bt,CustomerInfo customerInfo,EmployeeInfo ei) {
        if (bt.getOffset() == null || bt.getLimit() == null) {
            bt.setOffset(1);
            bt.setLimit(20);
        } else {
            bt.setOffset(bt.getOffset() / bt.getLimit());
        }
        if(customerInfo.getIsDelete() == null){
            customerInfo.setIsDelete(0);   //默认显示非逻辑删除数据
        }
        //根据校区名称搜索
        if("company_id".equals(bt.getCondition())){      //所属校区
            TbDict tbDict =  iTbDictService.selectOne(new EntityWrapper<TbDict>().where("").like("name",bt.getValue()));
            if(tbDict!=null) {
                bt.setValue(tbDict.getId() + "");
            }
        }
        Page<CustomerInfo> searchPage = new Page<CustomerInfo>(bt.getOffset(), bt.getLimit());
        Wrapper<CustomerInfo> ew = null;
        if("-1".equals(bt.getCondition()) ){
            bt.setCondition("");
        }

        if(ei.getUserId()!=null){ //咨询师
            if(StringUtils.isNotEmpty(bt.getCondition())&& StringUtils.isNotEmpty(bt.getValue())){
                ew= new EntityWrapper<CustomerInfo>().
                         where("isDelete="+customerInfo.getIsDelete())
                        .and("user_id="+ei.getUserId())
                        .and("customer_state!=40")
                        .and("customer_state!=34")
                        .like(bt.getCondition(),bt.getValue(), SqlLike.RIGHT).
                        orderBy(" customer_id desc");
            }else{
                ew = new EntityWrapper<CustomerInfo>()
                        .where("isDelete="+customerInfo.getIsDelete())
                        .and("user_id="+ei.getUserId())
                        .and("customer_state!=40")
                        .and("customer_state!=34")
                        .orderBy("customer_id desc");
            }
        }else{ //非咨询师
            if(StringUtils.isNotEmpty(bt.getCondition())&& StringUtils.isNotEmpty(bt.getValue())){
                if(ei.getCompanyId()!=null){
                    ew= new EntityWrapper<CustomerInfo>().
                            where("isDelete="+customerInfo.getIsDelete())
                            .and("company_id="+ei.getCompanyId())
                            .like(bt.getCondition(),bt.getValue(), SqlLike.RIGHT).
                                    orderBy(" customer_id desc");
                } else{
                    ew= new EntityWrapper<CustomerInfo>().
                            where("isDelete="+customerInfo.getIsDelete())
                            .like(bt.getCondition(),bt.getValue(), SqlLike.RIGHT).
                                    orderBy(" customer_id desc");
                }

            }else{
                if(ei.getCompanyId()!=null){
                    ew = new EntityWrapper<CustomerInfo>()
                            .where("isDelete="+customerInfo.getIsDelete())
                            .and("company_id="+ei.getCompanyId())
                            .orderBy("customer_id desc");
                } else{
                    ew = new EntityWrapper<CustomerInfo>()
                            .where("isDelete="+customerInfo.getIsDelete())
                            .orderBy("customer_id desc");
                }

            }
        }
        BootstrapTable<CustomerInfoVo> bootstrapTable = iCustomerInfoService.ajaxData(searchPage,ew);
        return bootstrapTable;
    }

    /**
     * 跳转到添加页面
     *
     * @return
     */
    @RequiresPermissions(value = {"customerInfo:add"})
    @RequestMapping(value = "/customerInfo/add", method = RequestMethod.GET)
    protected String add(ModelMap modelMap, EmployeeInfo ei,Integer isYunYing, HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        EmployeeInfo employeeInfo = (EmployeeInfo)httpSession.getAttribute("employeeInfo");
        if(ei.getCompanyId()==null){
            this.modelMap(modelMap,employeeInfo.getCompanyId(),isYunYing);
        }  else{
            this.modelMap(modelMap,ei.getCompanyId(),isYunYing);
        }
        return "/customerinfo/add";
    }

    /**
     * 保存数据
     *
     * @param customerInfo
     * @return
     * @throws InterruptedException
     */
    @RequiresPermissions(value = {"customerInfo:add"})
    @RequestMapping(value = "/customerInfo/add", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String, Object> add(CustomerInfo customerInfo,int  isYunYing) throws InterruptedException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            if(customerInfo.getCustomerState()==null){
                customerInfo.setCustomerState(28);//待面试
            }
            if(isYunYing ==1){
                customerInfo.setCompanyId(null);
            }
            customerInfo.setCreateTime(new Date());
            customerInfo.setLastTime(new Date());
            iCustomerInfoService.insert(customerInfo);
            resultMap.put("code", 200);
            resultMap.put("msg", "添加成功!");
        } catch (Exception e) {
            logger.error("-----------咨询师添加学员信息 ------------------------"+e.getMessage());
            e.printStackTrace();
            resultMap.put("code", 300);
            resultMap.put("msg", "添加失败!");
        }
        //Thread.sleep(4000);模拟网络阻塞
        return resultMap;
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
        return iTbDictService.selectByMap(paramMap);
    }

    /**
     * 根据部门编号获取用户
     *
     * @param id
     * @return
     */
    protected List<EmployeeInfo> getEmployeeInfo(String id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("dept_id", id);
        return iEmployeeInfoService.selectByMap(paramMap);
    }

    /**
     * 修改学员信息页面
     *
     * @return
     */
    @RequiresPermissions(value={"customerInfo:edit"})
    @RequestMapping(value = "/customerInfo/edit", method = RequestMethod.GET)
    protected String edit(ModelMap modelMap, CustomerInfo customerInfo,Integer companyId,Integer isYunYing) {
        //根据customerId获取学员数据
        customerInfo = iCustomerInfoService.selectOne(new EntityWrapper<CustomerInfo>().where("customer_id=" + customerInfo.getCustomerId()));
        this.modelMap(modelMap,companyId,isYunYing);
        modelMap.addAttribute("customerInfo", customerInfo);
        return "/customerinfo/edit";
    }

    public void modelMap(ModelMap modelMap,Integer companyId,Integer isYunYing) {
        modelMap.addAttribute("customerStateList", this.getTbgDict("27"));//学员状态
        modelMap.addAttribute("targetSkillList", this.getTbgDict("2"));//目标技能
        List<TbDict> tbDictList = null;
        if(isYunYing!= null && isYunYing ==1){
            tbDictList = this.getTbgDict("79");
           // modelMap.addAttribute("recruitChannelList", this.getTbgDict("21"));//应聘渠道
        } else{
            tbDictList = this.getTbgDict("21");
           // modelMap.addAttribute("recruitChannelList",this.getTbgDict("79"));  //运营渠道
        }
        modelMap.addAttribute("recruitChannelList",tbDictList);   //应聘渠道
        modelMap.addAttribute("customerLevelList", this.getTbgDict("16"));//学员级别
        modelMap.addAttribute("educationBgList", this.getTbgDict("7")); //获取学历信息
        if(companyId !=null  && companyId!=0 ){
            modelMap.addAttribute("guandanList", this.getEmployeeInfo(new EntityWrapper<EmployeeInfo>()
                    .where("isConsultant=1").
                            and("company_id="+companyId).and("isLocked='0'")));//获取本公司关单人
             if(isYunYing!= null && isYunYing ==1){
                 modelMap.addAttribute("introducerList",this.getEmployeeInfo(new EntityWrapper<EmployeeInfo>().where("dept_id=86")));   //运营部
             }else{
                 modelMap.addAttribute("introducerList",
                         this.getEmployeeInfo(new EntityWrapper<EmployeeInfo>()
                                 .where("dept_id=76").and("company_id="+companyId)));//获取本公司邀约人   创量部
             }

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
            if(isYunYing!= null && isYunYing ==1){
               employeeInfos = iEmployeeInfoService.selectList(new EntityWrapper<EmployeeInfo>().where("dept_id=86"));
            } else{
                employeeInfos = iEmployeeInfoService.selectList(new EntityWrapper<EmployeeInfo>()
                        .where("dept_id=76"));
            }

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

    public List<EmployeeInfo> getEmployeeInfo(Wrapper<EmployeeInfo> wrapper){
        return iEmployeeInfoService.selectList(wrapper);
    }

    /**
     * 保存修改数据
     *
     * @param customerInfo
     * @return
     */
    @RequiresPermissions(value={"customerInfo:edit"})
    @RequestMapping(value = "/customerInfo/edit", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String, Object> edit(CustomerInfo customerInfo) {
        Map<String, Object> resultMap = new HashMap<>();
        EmployeeInfo employeeInfo = iEmployeeInfoService.selectById(customerInfo.getUserId());
        try {
            if(employeeInfo!= null){
                customerInfo.setCompanyId(employeeInfo.getCompanyId());//修改所属公司
            }
            iCustomerInfoService.update(customerInfo,
                    new EntityWrapper<CustomerInfo>().where("customer_id=" + customerInfo.getCustomerId()));
            resultMap.put("code", "200");
            resultMap.put("msg", "保存成功!");
        } catch (Exception e) {
            logger.error("------------咨询师编辑学员信息---------------"+e.getMessage());
            e.printStackTrace();
            resultMap.put("code", "300");
            resultMap.put("msg", "保存失败!");
        }
        return resultMap;
    }

    /**
     * 删除学员(丢到回收站)
     *
     * @return
     */
    /*@RequiresPermissions(value = {"customerInfo:remove"})
    @RequestMapping(value = "/customerInfo/remove", method = RequestMethod.GET)
    @ResponseBody
    protected Map<String, Object> remove(String customerIdArr) {
        Map<String, Object> resultMap = new HashMap<>();
        return resultMap;
    }*/

    /**
     * 彻底删除学员信息
     *到service层中线先删除该学员的跟进记录，后再删除customre_info中相关的数据
     * @return
     */
    @RequiresPermissions(value = {"customerInfo:delete"})
    @RequestMapping(value = "/customerInfo/delete", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String, Object> delete(String customerIdArr) {
        Map<String, Object> resultMap = new HashMap<>();
        try{
            iCustomerInfoService.deleteCustomerInfo(customerIdArr);
            resultMap.put("code",200);
            resultMap.put("msg","删除成功!");
        } catch (Exception e){
            logger.error("----------------------彻底删除学员数据---------------"+e.getMessage());
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put("code",300);
            resultMap.put("msg","删除失败!");
        }
        return resultMap;
    }

    /**
     * 批量面试学员
     *
     * @return
     */
    @RequiresPermissions(value = "customerInfo:interview")
    @RequestMapping(value = "/customerInfo/interview", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String, Object> interview(String customerIdArr) {
        String condition = "customer_sate";
        return getStringObjectMap(customerIdArr, condition);
    }

    /**
     * 逻辑删除学员数据
     *
     * @return
     */
    @RequiresPermissions(value="customerInfo:remove")
    @RequestMapping(value = "/customerInfo/remove")
    @ResponseBody
    protected Map<String, Object> remove(String customerIdArr) {
        String condition = "isDelete";
        return getStringObjectMap(customerIdArr, condition);
    }
    @RequiresPermissions(value="customerInfo:recover")
    @RequestMapping(value = "/customerInfo/recover",method = RequestMethod.POST)
    @ResponseBody
    protected  Map<String,Object> recover(String customerIdArr){
        return  getStringObjectMap(customerIdArr,"isDelete=0");

    }
    private Map<String, Object> getStringObjectMap(String customerIdArr, String condition) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            CustomerInfo customerInfo = null;
            if (StringUtils.isNotEmpty(customerIdArr)) {
                String[] idArr = customerIdArr.split(",");
                for (String id : idArr) {
                    customerInfo = new CustomerInfo();
                    if ("isDelete".equals(condition)) {
                        customerInfo.setIsDelete(1);//逻辑删除数据
                    }else if("isDelete=0".equals(condition)){
                        customerInfo.setIsDelete(0);//恢复逻辑删除的数据
                    } else if ("customer_sate".equals(condition)) {
                        customerInfo.setCustomerState(29); //修改面试状态为已面试
                    }
                    customerInfo.setCustomerId(Integer.parseInt(id));
                    iCustomerInfoService.update(customerInfo,
                            new EntityWrapper<CustomerInfo>().where("customer_id=" + customerInfo.getCustomerId()));
                }
            }
            resultMap.put("code", 200);
            resultMap.put("msg", "操作成功!");
        } catch (Exception e) {
            logger.error("--------------------面试学员-------------------"+e.getMessage());
            e.printStackTrace();
            resultMap.put("code", 300);
            resultMap.put("msg", "操作失败!");
        }
        return resultMap;
    }

    /**
     * 学员信息跟进
     *
     * @param customerInfo
     * @return
     */
    @RequiresPermissions(value="customerInfo:follow")
    @RequestMapping(value = "/customerInfo/follow", method = RequestMethod.GET)
    protected String follow(CustomerInfo customerInfo, ModelMap modelMap) {
        //根据customerId获取学员数据
        customerInfo = iCustomerInfoService.selectOne(new EntityWrapper<CustomerInfo>().where("customer_id=" + customerInfo.getCustomerId()));
        this.modelMap(modelMap,customerInfo.getCompanyId(),null);
        modelMap.addAttribute("customerInfo", customerInfo);
        return "/customerinfo/follow";
    }

    /**
     * 保存跟进记录
     * 需要咨询师编号,学员编号，跟进内容
     *
     * @param ctr
     * @return
     */
    @RequiresPermissions(value="customerInfo:follow")
    @RequestMapping(value = "/customerInfo/follow", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String, Object> follow(CustomerTraceRecord ctr) {
        Map<String, Object> resultMap = new HashMap<>();
        ctr.setRecordDate(new Date());
        try {
            //到customer_info中修改最新跟进时间
            CustomerInfo customerInfo = new CustomerInfo() ;
            customerInfo.setCustomerId(ctr.getCustomerId());
            customerInfo.setLastTime(new Date());
            iCustomerInfoService.updateById(customerInfo);
            iCustomerTraceRecordService.insert(ctr);   //保存跟进记录!!
            resultMap.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code",300);
        }
        return resultMap;
    }

    /**
     * 跳转到高级搜索页面
     * @return
     */
    @RequiresPermissions(value="customerInfo:multiSearch")
    @RequestMapping(value="/customerInfo/multiSearch",method = RequestMethod.GET)
    protected String multiSearch(){
        return "/customerinfo/multiSearch";
    }

    /**
     * 跳转到报表导出页面
     * @return
     */
    @RequiresPermissions(value="customerInfo:export")
    @RequestMapping(value="/customerInfo/export",method=RequestMethod.GET)
    protected String export(Integer companyId,Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        EmployeeInfo employeeInfo = (EmployeeInfo)session.getAttribute("employeeInfo");
        List<EmployeeInfo> employeeInfos = null;
        List<TbDict> tbDictList = null;
        if(companyId!=null){
            if(companyId!=0){ //说明是分校区
                employeeInfos =  iEmployeeInfoService.selectList(new EntityWrapper<EmployeeInfo>().
                        where("dept_id=74").and("company_id="+companyId));
                
            }else if(companyId ==0){     //获取所有校区的的数据
                employeeInfos = iEmployeeInfoService.selectList(new EntityWrapper<EmployeeInfo>()
                        .where("dept_id=74").and("company_id="+employeeInfo.getCompanyId())) ;
                tbDictList = iTbDictService.selectList(new EntityWrapper<TbDict>().where("pid=35"));//公司的pid
            }else{
                employeeInfos = new ArrayList<>();
            }
        }
        //获取学员状态
        List<TbDict> customerStateList = iTbDictService.selectList(new EntityWrapper<TbDict>().where("pid=27"));
        model.addAttribute("employeeInfoList",employeeInfos);
        model.addAttribute("companyList",tbDictList);
        model.addAttribute("customerStateList",customerStateList);
        return "/customerinfo/export";
    }

    /**
     * 回收站
     * @return
     */
    @RequiresPermissions(value="customerInfo:recyleBin")
    @RequestMapping(value="/customerInfo/recyleBin",method = RequestMethod.GET)
    public String recyleBin(){
        return "/customerinfo/recyleBin";
    }

    @RequestMapping(value="/customerInfo/recycleBinAjaxData",method = RequestMethod.GET)
    @ResponseBody
    protected  BootstrapTable<CustomerInfo> recycleBinAjaxData(BootstrapEntity bt,CustomerInfo customerInfo,EmployeeInfo ei){
        if (bt.getOffset() == null || bt.getLimit() == null) {
            bt.setOffset(1);
            bt.setLimit(20);
        } else {
            bt.setOffset(bt.getOffset() / bt.getLimit());
        }
        Wrapper<CustomerInfo> ew =null;
        if("-1".equals(bt.getCondition()) ){
            bt.setCondition("");
        }
        if("1".equals(ei.getIsConsultant())){ //咨询师
            if(StringUtils.isNotEmpty(bt.getCondition())&& StringUtils.isNotEmpty(bt.getValue())){
                ew= new EntityWrapper<CustomerInfo>().
                        where("isDelete=1").and("user_id="+ei.getUserId())
                        .and("customer_state!=40")
                        .and("customer_state!=34")
                        .like(bt.getCondition(),bt.getValue()).
                        orderBy(" customer_id desc");
            }else{
                ew = new EntityWrapper<CustomerInfo>()
                        .where("isDelete=1")
                        .and("user_id="+ei.getUserId())
                        .and("customer_state!=40")
                        .and("customer_state!=34")
                        .orderBy("customer_id desc");
            }
        }else{ //非咨询师
            if(StringUtils.isNotEmpty(bt.getCondition())&& StringUtils.isNotEmpty(bt.getValue())){
                ew= new EntityWrapper<CustomerInfo>().
                        where("isDelete=1").
                        like(bt.getCondition(),bt.getValue()).
                        orderBy(" customer_id desc");
            }else{
                ew = new EntityWrapper<CustomerInfo>()
                        .where("isDelete=1")
                        .orderBy("customer_id desc");
            }
        }

        Page<CustomerInfo> page = iCustomerInfoService.selectPage(new Page<CustomerInfo>(
                bt.getOffset(),bt.getLimit()),
                ew);
        BootstrapTable<CustomerInfo> bootstrapTable = new BootstrapTable<>();
        bootstrapTable.setRows(page.getRecords());
        bootstrapTable.setTotal(page.getTotal());
        return bootstrapTable;
    }

    /**
     * 跳转到搜索页
     * @return
     */
    @RequiresPermissions(value="customerInfo:searchPage")
    @RequestMapping("/customerInfo/searchPage")
    protected  String searchPage(ModelMap modelMap,EmployeeInfo ei){
        //获取学员状态
        //获取学员级别
        //获取目标技能
        //获取应聘渠道
        //获取邀约人
        //获取咨询师
        //获取关单人
        modelMap(modelMap,ei.getCompanyId(),null);
         return "/customerinfo/searchPage";
    }

    /**
     * 跳转到学员进班页
     * @return
     */
    @RequestMapping(value="/customerInfo/enterClass",method = RequestMethod.GET)
    protected String endterClass(EmployeeInfo employeeInfo,Model model){
        //获取班级信息
        List<Classinfo> classinfoList = iClassinfoService.selectClassInfo();
        model.addAttribute("classinfoList",classinfoList);
        //获取贷款信息
        List<TbDict> tbDictList = iTbDictService.selectList(new EntityWrapper<TbDict>().where("pid=46"));
        model.addAttribute("tbDictList",tbDictList);
        return "/customerinfo/enterClass";
    }

    /**
     * 获取学员进班数据
     * @param 
     * @return
     */
   /* @RequestMapping(value="/customerInfo/enterClass2")
    @ResponseBody
    public Map<String,Object> enterClass(Studentinfo studentinfo,Integer customerId){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            //iStudentinfoService.insertStudentinfo(studentinfo,customerId);
            resultMap.put("code",200);
            resultMap.put("msg","操作成功!");
        }  catch (Exception e){

           resultMap.put("code",300);
           resultMap.put("msg","操作失败!");
        }
        return resultMap;
    }*/
    @RequiresPermissions("customerInfo:enterClass")
    @RequestMapping("/customerInfo/enterClass")
    @ResponseBody
    public Map<String,Object> hello(Integer customerId,Integer stedentClass,
                                    String studentStatus,String studentintime,String studentdes) throws ParseException {
        Studentinfo studentinfo = new Studentinfo();
        studentinfo.setStedentClass(stedentClass);
        studentinfo.setStudentStatus(studentStatus);
        studentinfo.getStudentintime();
        studentinfo.setStudentdes(studentdes);
        Map<String,Object> resultMap = new HashMap<>();
        try{
            iStudentinfoService.insertStudentinfo(studentinfo,customerId);
            resultMap.put("code",200);
            resultMap.put("msg","操作成功");
        }   catch ( Exception e){
            e.printStackTrace();
            resultMap.put("code",300);
            resultMap.put("msg","操作失败!");
        }

        return resultMap;
    }

    /**
     * 跳转到进班学员列表
     * @return
     */
    @RequestMapping("/customerInfo/enterClassList")
    public String enterClassList(){
        return "/customerinfo/enterClassList";
   }


    /**
     * 根据用户名和电话号码检测该用户是否存在(防止抢单)
     * @return
     */
    @RequestMapping("/customerInfo/checkCustomerInfo")
    @ResponseBody
    public  Map<String,Object> checkCustomerInfo(CustomerInfo customerInfo){
        customerInfo =iCustomerInfoService.selectOne(new EntityWrapper<CustomerInfo>()
                .where("real_name='"+customerInfo.getRealName()+"'")
                .and("tel='"+customerInfo.getTel()+"'"));
        Map<String,Object> resultMap = new HashMap<>();
        if(customerInfo != null){
             resultMap.put("code",300);
             resultMap.put("msg","该学员已经存在了!");
        } else{
             resultMap.put("code",200);
             resultMap.put("msg","该学员可以录入!");
        }
        return resultMap;
    }
    @ExcelEntity
    private List<CustomerInfoVo> customerInfoVoList;
    /**
     * 导出全部数据
     * @return
     */
    @RequestMapping("/customerInfo/exportAllData")
    protected void exportAllData(HttpServletResponse response,int isDelete) throws IOException {
        String fileName = "学员.xlsx";
        response.setHeader("Content-disposition", "attachment; filename="+
                new String(fileName.getBytes("utf-8"),"ISO-8859-1"));// 设定输出文件头
        response.setContentType("application/msexcel");// 定义输出类型
        OutputStream outputStream = response.getOutputStream();
        Wrapper wrapper = new EntityWrapper().where("isDelete="+isDelete).orderBy("customer_id desc");
        int count = iCustomerInfoService.selectCount(wrapper);
        BootstrapTable<CustomerInfoVo> bt = iCustomerInfoService.ajaxData(new Page<>(0,count),wrapper);
        ExportParams ep = new ExportParams("学员","学员", ExcelType.XSSF);
        customerInfoVoList = bt.getRows();
        Workbook workbook  =ExcelExportUtil.exportBigExcel(ep,CustomerInfoVo.class,this.customerInfoVoList);
        // 主体内容生成结束
        workbook.write(outputStream); // 写入文件
        outputStream.flush();//刷新缓存
    }
    
    @RequestMapping("/customerInfo/exportData")
    protected  void export(HttpServletResponse response, ExportExcelEntity excelEntity) throws UnsupportedEncodingException {
        String fileName = "学员数据.xlsx";
        response.setHeader("Content-disposition", "attachment; filename="+
                new String(fileName.getBytes("utf-8"),"ISO-8859-1"));// 设定输出文件头
        response.setContentType("application/msexcel");// 定义输出类型

        int count = 0;
        Wrapper wrapper = null;
        if(StringUtils.isNotEmpty(excelEntity.getDate())){
            String dateReg = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
            String monthReg = "[0-9]{4}-[0-9]{2}";
            String yearReg= "[0-9]{4}";
            Pattern datePattern = Pattern.compile(dateReg);  //日
            Matcher dateMatcher = datePattern.matcher(excelEntity.getDate());
            Pattern monthPattern = Pattern.compile(monthReg);
            Matcher monthMatcher = monthPattern.matcher(excelEntity.getDate()); //月
            Pattern yearPattern = Pattern.compile(yearReg);
            Matcher yearMatcher = yearPattern.matcher(excelEntity.getDate()); //年
            String   dateFormat = "";
            if(dateMatcher.matches()){  //yyyy-MM-dd
                dateFormat = "DATE_FORMAT(create_time,'%Y-%m-%d')='";
            }else if(monthMatcher.matches()){
                dateFormat = "DATE_FORMAT(create_time,'%Y-%m')='";
            } else if(yearMatcher.matches()){
                dateFormat = "DATE_FORMAT(create_time,'%Y')='";
            }
           if(!"-1".equals(excelEntity.getUserId())){
                if(!"-1".equals(excelEntity.getCustomerState())){
                    count = iCustomerInfoService.selectCount(new EntityWrapper<CustomerInfo>().
                            where(dateFormat+excelEntity.getDate()+"'").
                            and("company_id="+excelEntity.getCompanyId()).
                            and("user_id="+excelEntity.getUserId())
                            .and("customer_state="+excelEntity.getCustomerState()));
                    wrapper = new EntityWrapper().
                            where(dateFormat+excelEntity.getDate()+"'").
                            and("company_id="+excelEntity.getCompanyId())
                            .and("user_id="+excelEntity.getUserId())
                            .and("customer_state="+excelEntity.getCustomerState());
                } else{
                    count = iCustomerInfoService.selectCount(new EntityWrapper<CustomerInfo>().
                            where(dateFormat+excelEntity.getDate()+"'").
                            and("company_id="+excelEntity.getCompanyId()).
                            and("user_id="+excelEntity.getUserId()));
                    wrapper = new EntityWrapper().
                            where(dateFormat+excelEntity.getDate()+"'").
                            and("company_id="+excelEntity.getCompanyId()).and("user_id="+excelEntity.getUserId());
                }

           }  else{
               if(!"-1".equals(excelEntity.getCustomerState())){
                   count = iCustomerInfoService.selectCount(new EntityWrapper<CustomerInfo>().
                           where(dateFormat+excelEntity.getDate()+"'").
                           and("company_id="+excelEntity.getCompanyId())
                           .and("customer_state="+excelEntity.getCustomerState()));
                   wrapper = new EntityWrapper().
                           where(dateFormat+excelEntity.getDate()+"'").
                           and("company_id="+excelEntity.getCompanyId())
                           .and("customer_state="+excelEntity.getCustomerState());
               } else{
                   count = iCustomerInfoService.selectCount(new EntityWrapper<CustomerInfo>().
                           where(dateFormat+excelEntity.getDate()+"'").
                           and("company_id="+excelEntity.getCompanyId()));
                   wrapper = new EntityWrapper().
                           where(dateFormat+excelEntity.getDate()+"'").
                           and("company_id="+excelEntity.getCompanyId());
               }

            }

        }else{
            if(!"-1".equals(excelEntity.getUserId())){
                if(!"-1".equals(excelEntity.getCustomerState())){
                    count = iCustomerInfoService.selectCount(new EntityWrapper<CustomerInfo>().
                            where("company_id="+excelEntity.getCompanyId()).
                            and("user_id="+excelEntity.getUserId()).
                            and("customer_state="+excelEntity.getCustomerState()));
                    wrapper = new EntityWrapper().
                            where("company_id="+excelEntity.getCompanyId()).
                            and("user_id="+excelEntity.getUserId())
                            .and("customer_state="+excelEntity.getCustomerState());
                }else{
                    count = iCustomerInfoService.selectCount(new EntityWrapper<CustomerInfo>().
                            where("company_id="+excelEntity.getCompanyId()).
                            and("user_id="+excelEntity.getUserId()).
                            and("customer_state="+excelEntity.getCustomerState()));
                    wrapper = new EntityWrapper().
                            where("company_id="+excelEntity.getCompanyId()).
                            and("user_id="+excelEntity.getUserId()).
                            and("customer_state="+excelEntity.getCustomerState());
                }
               
            }else{
                if(!"-1".equals(excelEntity.getCustomerState())){
                    count = iCustomerInfoService.selectCount(new EntityWrapper<CustomerInfo>().
                            where("company_id="+excelEntity.getCompanyId()).
                            and("customer_state="+excelEntity.getCustomerState()));
                    wrapper = new EntityWrapper().
                            where("company_id="+excelEntity.getCompanyId()).
                            and("customer_state="+excelEntity.getCustomerState());
                }  else{
                    count = iCustomerInfoService.selectCount(new EntityWrapper<CustomerInfo>().
                            where("company_id="+excelEntity.getCompanyId()));
                    wrapper = new EntityWrapper().
                            where("company_id="+excelEntity.getCompanyId());
                }

            }

        }
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
           logger.error("io异常");
        } finally {
            if(outputStream !=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                   logger.error("释放io资源失败!!");
                }
            }
        }
        BootstrapTable<CustomerInfoVo> bt = iCustomerInfoService.ajaxData(new Page<CustomerInfo>(0,count),wrapper);
        ExportParams ep = new ExportParams("学员","学员", ExcelType.XSSF);
        customerInfoVoList = bt.getRows();
        Workbook workbook  =ExcelExportUtil.exportBigExcel(ep,CustomerInfoVo.class,this.customerInfoVoList);
        // 主体内容生成结束
        try {
            workbook.write(outputStream); // 写出文件
        } catch (IOException e) {
           logger.error("写出excel文件出错!!!"+e.getMessage());
        } finally {
            if(outputStream!= null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                   logger.error("释放io资源失败!!"+e.getMessage());
                }
            }
        }
        //outputStream.flush();
    }

    /**
     * 导入Excel
     */
//    @RequestMapping(value = "/import", method = RequestMethod.POST)
//    @ResponseBody
//    public StatusVO importExcel(@RequestParam("excelFile") MultipartFile file,Integer channel,HttpServletRequest request,HttpSession session){
//        StatusVO statusVO = null;
//        try {
//            //licenseName字段为分校的校区名
//            //由于登入功能暂时没实现，获取不到session中的数据，暂时固定写死
//            String licenseName = "宝安";
//            String path = request.getSession().getServletContext().getRealPath("");
//            File f = new File(path+"/excel/"+file.getOriginalFilename());
//            if(!f.exists()){
//                try {
//                    File dir = new File(path+"/excel/");
//                    dir.mkdirs();
//                    if(!f.createNewFile()){
//                        statusVO = new StatusVO();
//                        statusVO.setCode(700);
//                        statusVO.setMsg("导入失败!");
//                        return statusVO;
//                    }
//                } catch (IOException e) {
//                    statusVO = new StatusVO();
//                    statusVO.setCode(700);
//                    statusVO.setMsg("导入失败!"+e.getMessage());
//                    return statusVO;
//                }
//            }
//            file.transferTo(f);
//            InputStream is = new FileInputStream(f);
//            boolean result = customerInfoService.importExcel(is,channel,licenseName);
//            if(result){
//                statusVO = new StatusVO();
//                statusVO.setCode(600);
//                statusVO.setMsg("导入成功!");
//                return statusVO;
//            }
//            statusVO = new StatusVO();
//            statusVO.setCode(700);
//            statusVO.setMsg("导入失败!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            statusVO = new StatusVO();
//            statusVO.setCode(700);
//            statusVO.setMsg("导入失败!"+e.getMessage());
//            return statusVO;
//        }
//        return statusVO;
//    }
}

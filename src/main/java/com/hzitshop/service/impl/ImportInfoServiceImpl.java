package com.hzitshop.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.hzitshop.entity.CustomerInfo;
import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.entity.ImportInfo;
import com.hzitshop.entity.TbDict;
import com.hzitshop.mapper.CustomerInfoMapper;
import com.hzitshop.mapper.EmployeeInfoMapper;
import com.hzitshop.mapper.ImportInfoMapper;
import com.hzitshop.mapper.TbDictMapper;
import com.hzitshop.service.ImportInfoService;
import com.hzitshop.util.ExcelUtil;
import com.hzitshop.util.ServerResponse;
import com.hzitshop.vo.*;
import com.hzitshop.vo.importresultvo.ImportVoNameId;
import com.hzitshop.vo.importresultvo.SelectByRealName;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-09-08 19:01
 * @description
 */
@Service
@Transactional
public class ImportInfoServiceImpl extends ServiceImpl<ImportInfoMapper,ImportInfo> implements ImportInfoService {

    @Autowired(required = false)
    private ImportInfoMapper importInfoMapper;

    @Autowired(required = false)
    private TbDictMapper tbDictMapper;

    @Autowired(required = false)
    private EmployeeInfoMapper employeeInfoMapper;

    @Autowired(required = false)
    private CustomerInfoMapper customerInfoMapper;

    /**
     * 导入excel文件
     * @param is
     * @param recruitChannel
     * @param cvType
     * @param session
     * @return
     */
    @Override
    public String importExcel(InputStream is, String recruitChannel,Integer cvType,HttpSession session) {
        String flag = null;
        if(ChannelPropertiesVo.ZHI_LIAN.equals(recruitChannel)) {
            //智联招聘渠道文件导入
            flag = ExcelUtil.zhilianImport(is, recruitChannel,cvType, importInfoMapper, session);
            return flag;
        }
        if(ChannelPropertiesVo.QIAN_CHENG.equals(recruitChannel)) {
            //前程无忧渠道文件导入
            flag = ExcelUtil.qianchengImport(is, recruitChannel,cvType, importInfoMapper, session);
            return flag;
        }
        if(ChannelPropertiesVo.TOMG_CHENG.equals(recruitChannel)) {
            //58同城
            flag = ExcelUtil.tongchengImport(is, recruitChannel,cvType, importInfoMapper, session);
            return flag;
        }
        if(ChannelPropertiesVo.REN_CAI.equals(recruitChannel)) {
            //人才热线
            flag = ExcelUtil.rencaiImport(is, recruitChannel,cvType, importInfoMapper, session);
            return flag;
        }
        if(ChannelPropertiesVo.YING_CAI.equals(recruitChannel)) {
            //中华英才
            flag = ExcelUtil.zhonghuaImport(is, recruitChannel,cvType, importInfoMapper, session);
            return flag;
        }
        if(ChannelPropertiesVo.GAN_JI.equals(recruitChannel)) {
            //赶集网
            flag = ExcelUtil.ganjiImport(is, recruitChannel,cvType, importInfoMapper, session);
            return flag;
        }
        if(ChannelPropertiesVo.BAI_XING.equals(recruitChannel)){
            //百姓网
            flag = ExcelUtil.baixingImport(is,recruitChannel,cvType,importInfoMapper,session);
            return flag;
        }
        return flag;
    }

    @Override
    public BootstrapTable<ImportInfoVo> ajaxData(Page<ImportInfo> page, Wrapper<ImportInfo> wrapper) {
        Page<ImportInfo> resultPage = this.selectPage(page,wrapper);
        BootstrapTable<ImportInfoVo> bootstrapTable = new BootstrapTable<>();
        bootstrapTable.setTotal(resultPage.getTotal());//获取总记录数
        List<ImportInfo> importInfoList = page.getRecords();
        List<ImportInfoVo> importInfoVos = getImportInfoVos(importInfoList);
        bootstrapTable.setRows(importInfoVos);
        return bootstrapTable;
    }

    /**
     * 筛选数据
     * @param paramMap
     * @return
     */
    @Override
    public List<ImportInfoFilterData> filterData(Map<String, Object> paramMap) {
        List<ImportInfoFilterData> importInfoFilterDataList = new ArrayList<>();
        ImportInfoFilterData filterData = null;
        List<EmployeeInfo> employeeInfos = employeeInfoMapper.selectByCompanyId(paramMap);

        for(EmployeeInfo ei : employeeInfos){
            Map<String,Object> map = new HashMap<>();
            map.put("createBy",ei.getUserId());//录入人
            FilterDataCount filterDataCount = importInfoMapper.filterDataCount(map);
            List<FilterData> filterDataList  = importInfoMapper.filterData(map);//获取该录入人的数据
            filterData = new ImportInfoFilterData(ei.getName(),
                    filterDataList,
                    filterDataCount.getTotal(),
                    (filterDataCount.getTotal()-filterDataCount.getInvalidNum()));
            importInfoFilterDataList.add(filterData);
        }
        return importInfoFilterDataList;
    }

    /**
     * 逻辑删除数据
     * @param paramMap
     * @return
     */
    @Override
    public ServerResponse<String> deleteByStatus(Map<String, Object> paramMap) {
        ServerResponse<String> resp = null;
        String msg = "";
        if((int)paramMap.get("isDelete") ==0){//需要该数据时才判断是否存在预约情况
            ImportInfo importInfo = importInfoMapper.checkInfoByHalfYear(paramMap);
            if(importInfo != null){//有预约信息
                msg = "但该数据存在预约情况!";
            }
        }
        int result= importInfoMapper.updateByStatus(paramMap);
        if(result == 1){
           resp = ServerResponse.createBySuccessMessage("处理成功!"+msg);
        }else{
            resp = ServerResponse.createByErrorCodeMessage(1,"处理失败!"+msg);
        }
        return resp;
    }

    @Override
    public List<ImportVoNameId> checkDailyInfo() {
        return null;
    }


    /**
     *
     * @param importInfoList
     * @return
     */
    private List<ImportInfoVo> getImportInfoVos(List<ImportInfo> importInfoList) {
        List<ImportInfoVo> importInfoVos = new ArrayList<>();
        //数据字典
        ImportInfoVo importInfoVo =  null;
        EmployeeInfo employeeInfo = null;
        TbDict tbDict =  null;
        for(ImportInfo importInfo : importInfoList){
            importInfoVo = new ImportInfoVo();
            BeanUtils.copyProperties(importInfo, importInfoVo);

            if(importInfo.getSex()!=null){
                if(importInfo.getSex()==1){
                    importInfoVo.setSexMsg("男");
                }else if(importInfo.getSex()==2){
                    importInfoVo.setSexMsg("女");
                }
            }

            //数据字典
//            if(importInfo.getEducationBg() != null){
//                tbDict = tbDictMapper.selectById(importInfo.getEducationBg());
//                if(tbDict !=null){
//                    importInfoVo.setEducationBgMsg(tbDict.getName());
//                }
//            }
            //学历
//            if(importInfo.getEducationBg() !=null){
//                tbDict = tbDictMapper.selectById(importInfo.getEducationBg());
//                if(tbDict != null){
//                    importInfoVo.setEducationBgMsg(tbDict.getName());
//                }
//            }
            //状态
            /*if(importInfo.getCustomerState() != null){
                tbDict = tbDictMapper.selectById(importInfo.getCustomerState());
                if(tbDict != null){
                    importInfoVo.setCustomerStateMsg(tbDict.getName());
                }
            }*/
            //级别
            if(StringUtils.isNotEmpty(importInfo.getCustomerLevel()) ){
                tbDict = tbDictMapper.selectById(importInfo.getCustomerLevel());
                if(tbDict!= null){
                    importInfoVo.setCustomerLevelMsg(tbDict.getName());
                }
            }
            //应聘渠道
           /* if(importInfo.getRecruitChannel() !=null){
                tbDict =tbDictMapper.selectById(importInfo.getRecruitChannel());
                if(tbDict != null){
                    importInfoVo.setRecruitChannelMsg(tbDict.getName());
                }

            }*/
            //所属公司
            if(importInfo.getCompanyId() !=null){
                tbDict = tbDictMapper.selectById(importInfo.getCompanyId());
                if(tbDict != null){
                    importInfoVo.setCompanyIdMsg(tbDict.getName());
                }
            }
            //关单人
            /*if(importInfo.getGuandan()!= null){
                employeeInfo =  employeeInfoMapper.selectById(importInfo.getGuandan());
                if(employeeInfo != null){
                    importInfoVo.setGuandaMsg(employeeInfo.getName());
                }
            }*/
            //咨询师
            /*if(importInfo.getUserId() !=null){
                employeeInfo = employeeInfoMapper.selectById(importInfo.getUserId());
                if(employeeInfo !=null){
                    importInfoVo.setUserIdMsg(employeeInfo.getName());
                }
            }*/
            //创量人员 (邀约人)
            if(StringUtils.isNotEmpty(importInfo.getIntroducer())){
                employeeInfo = employeeInfoMapper.selectById(importInfo.getIntroducer());
                if(employeeInfo !=null){
                    importInfoVo.setIntroducerMsg(employeeInfo.getName());
                }
            }
            if(importInfo.getCvType() != null){
                if(importInfo.getCvType() == 1){
                    importInfoVo.setCvTypeMsg("投递");
                }else if(importInfo.getCvType() == 2){
                    importInfoVo.setCvTypeMsg("下载");
                }
            }
            importInfoVos.add(importInfoVo);
        }
        return importInfoVos;
    }

    /**
     * 获取最近半年的重复数据
     * @return
     */
    @Override
    public JSONObject checkDailyImportInfo(Map<String,Object> map) {
        List<ImportVoNameId>  importVoNameIds  = importInfoMapper.checkDailyName(map);
        List<JSONObject> jsonObjects = new ArrayList<>();
        JSONObject resultObject = new JSONObject();
        JSONObject jsonObject = null;

        int count = importInfoMapper.selectByRealNameCount(map);
        for(ImportVoNameId importVoNameId : importVoNameIds){
            jsonObject = new JSONObject();
            jsonObject.put("realName",importVoNameId.getRealName());
            jsonObject.put("createTime",importVoNameId.getCreateTime());
            //查询具体的导入信息
            map.put("realName",importVoNameId.getRealName());
            map.put("createTime",importVoNameId.getCreateTime());
            List<SelectByRealName> selectByRealNames = importInfoMapper.selectByRealName(map);
            for(SelectByRealName selectByRealName: selectByRealNames){
                if(selectByRealName.getImportStatus() > 0){
                    jsonObject.put("status",selectByRealName.getImportStatus());
                    jsonObject.put("introducer",selectByRealName.getName());
                }
                if(selectByRealName.getIsDelete() == 1){
                    jsonObject.put("id"+selectByRealName.getIntroducer()+"",
                            "<input type='checkbox' lay-filter='filterData' name='isDelete' title='筛选'>" +
                                    "<span style='font-size:17px;background-color:white;color:lightgrey' class='layui-badge' >"+selectByRealName.getTel()+"</span>" +
                                    "<input type='hidden' name='filterData' value='"+selectByRealName.getCustomerId()+"'/>" +
                                    "<input type='hidden' name='realName' value='"+importVoNameId.getRealName()+"'/>");
                }else{
                    jsonObject.put("id"+selectByRealName.getIntroducer()+"",
                            "<input type='checkbox' lay-filter='filterData' name='isDelete' checked title='筛选'>" +
                                    "<span style='font-size:17px;padding:2px;color:#0086B1;class='layui-badge'>"+selectByRealName.getTel()+"</span>" +
                                    "<input type='hidden' name='filterData' value='"+selectByRealName.getCustomerId()+"'/>" +
                                    "<input type='hidden' name='realName' value='"+importVoNameId.getRealName()+"'/>");
                }

            }
            if(jsonObject.get("status") == null){
                jsonObject.put("status",0);
            }
            jsonObjects.add(jsonObject);
        }

        resultObject.put("code",0);
        resultObject.put("msg","数据");
        resultObject.put("count",count);
        resultObject.put("data",jsonObjects);
        return resultObject;
    }

    /**
     * 根据id修改数据
     * @param info
     * @return
     */
    @Override
    public Integer updateById2(ImportInfo info) {
        return importInfoMapper.updateById2(info);
    }

    @Override
    public Workbook exportExcel(Wrapper<ImportInfo> wrapper) {
        List<ImportInfo> importInfoList = this.selectList(wrapper);
        List<ImportInfoVo> importInfoVoList = new ArrayList<>();
        ImportInfoVo  importInfoVo = null;
        for(ImportInfo importInfo : importInfoList){
            importInfoVo = new ImportInfoVo();
            BeanUtils.copyProperties(importInfo,importInfoVo);
            if(importInfo.getSex() ==1){
                importInfoVo.setSexMsg("男");
            }else if(importInfo.getSex() == 2){
                importInfoVo.setSexMsg("女");
            }else{
                importInfoVo.setSexMsg("未知");
            }
            if(importInfo.getCvType() ==1){
                importInfoVo.setCvTypeMsg("投递");
            }else if(importInfo.getCvType() ==2){
                importInfoVo.setCvTypeMsg("下载");
            }
            EmployeeInfo employeeInfo = this.employeeInfoMapper.selectById(importInfo.getIntroducer());
            if(employeeInfo!= null){
                importInfoVo.setIntroducerMsg(employeeInfo.getName());
            }
            importInfoVoList.add(importInfoVo);
        }
        ExportParams ep = new ExportParams("招聘信息","招聘信息", ExcelType.XSSF);
        Workbook workbook  = ExcelExportUtil.exportBigExcel(ep,ImportInfoVo.class,importInfoVoList);

        return workbook;
    }

    @Override
    public boolean updateSelectiveById(ImportInfo importInfo) {
        int result = this.importInfoMapper.updateSelectiveById(importInfo);
        if(result == 1){
            return true;
        }
        return false;
    }

    /**
     * 前台检查用户是否存在
     * @param importInfo
     * @return
     */
    @Override
    public List<ImportInfo> checkRealName(ImportInfo importInfo) {
        return this.importInfoMapper.checkRealName(importInfo);
    }

    /**
     * 前台修改状态为已上门
     * @param importInfo
     * @return
     */
    @Override
    public int changeStatus(ImportInfo importInfo) {
        return this.importInfoMapper.changeStatus(importInfo);
    }

    /**
     * 将创量数据转到运营中
     * @param customerIdArr
     * @return
     */
    @Transactional
    @Override
    public ServerResponse turnToYunYing(Integer[] customerIdArr,EmployeeInfo ei) {
        if(customerIdArr!= null && customerIdArr.length >0){
            try{
                for(Integer id : customerIdArr){
                    ImportInfo importInfo = this.importInfoMapper.selectById(id);
                    CustomerInfo ci = new CustomerInfo();
                    BeanUtils.copyProperties(importInfo,ci,"customerId","isMarket");
                    ci.setCustomerType(1);
                    //往customer_info表插入数据
                    if(ei.getRoleName().contains("运营")){
                        ci.setYunying(ei.getUserId());
                    }
                    customerInfoMapper.insert(ci);
                    //修改创量数据的状态
                    importInfo.setIsDelete(3);
                    this.importInfoMapper.updateById(importInfo);
                }
                return ServerResponse.createBySuccessMessage("操作成功!");
            }catch (Exception e){
                return ServerResponse.createByErrorMessage("操作失败!");
            }
        }else{
            return ServerResponse.createByErrorMessage("操作失败!没有获取相应的数据!");
        }
    }
}

package com.hzitshop.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hzitshop.entity.CustomerInfo;
import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.entity.LayuiData;
import com.hzitshop.entity.TbDict;
import com.hzitshop.mapper.CustomerInfoMapper;
import com.hzitshop.mapper.CustomerTraceRecordMapper;
import com.hzitshop.mapper.EmployeeInfoMapper;
import com.hzitshop.mapper.TbDictMapper;
import com.hzitshop.service.ICustomerInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hzitshop.util.LayuiEntity;
import com.hzitshop.util.ServerResponse;
import com.hzitshop.vo.*;
import com.hzitshop.vo.customerinfovo.NotFollowUpVo;
import com.hzitshop.vo.front.FrontVo;
import com.hzitshop.vo.huixiao.MobileHuiXiaoVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-12
 */
@Service
public class CustomerInfoServiceImpl extends ServiceImpl<CustomerInfoMapper, CustomerInfo> implements ICustomerInfoService {

    @Autowired(required = false)
    private CustomerInfoMapper customerInfoMapper;

    @Autowired(required = false)
    private TbDictMapper tbDictMapper;

    @Autowired(required = false)
    private EmployeeInfoMapper employeeInfoMapper;

    @Autowired(required = false)
    private CustomerTraceRecordMapper customerTraceRecordMapper;

    /**
     * 获取数据
     * @param page
     * @param wrapper
     * @return
     */
    @Override
    public BootstrapTable<CustomerInfoVo> ajaxData(Page<CustomerInfo> page, Wrapper<CustomerInfo> wrapper) {
        Page<CustomerInfo> resultPage = this.selectPage(page,wrapper);
        BootstrapTable<CustomerInfoVo> bootstrapTable = new BootstrapTable<>();
        bootstrapTable.setTotal(resultPage.getTotal());//获取总记录数
        List<CustomerInfo> customerInfoList = page.getRecords();
        List<CustomerInfoVo> customerInfoVos = getCustomerInfoVos(customerInfoList);
        bootstrapTable.setRows(customerInfoVos);
        return bootstrapTable;
    }

    /**
     * 获取列表信息
     * @param paramsMap
     * @return
     */
    @Override
    public BootstrapTable<CustomerInfoVo> listData(Map<String, Object> paramsMap) {
        List<CustomerInfo> customerInfoList = this.customerInfoMapper.searchCustomerInfo(paramsMap);
        BootstrapTable<CustomerInfoVo> bt = new BootstrapTable<>();
        List<CustomerInfoVo> customerInfoVoList = this.getCustomerInfoVos(customerInfoList);
        bt.setRows(customerInfoVoList);
        bt.setTotal(this.customerInfoMapper.searchCustomerInfoCount(paramsMap));
        return bt;
    }

    private List<CustomerInfoVo> getCustomerInfoVos(List<CustomerInfo> customerInfoList) {
        List<CustomerInfoVo> customerInfoVos = new ArrayList<>();
        //数据字典
        CustomerInfoVo customerInfoVo =  null;
        EmployeeInfo employeeInfo = null;
        TbDict tbDict =  null;
        for(CustomerInfo customerInfo : customerInfoList){
            customerInfoVo = new CustomerInfoVo();
            BeanUtils.copyProperties(customerInfo,customerInfoVo);
            if(customerInfo.getSex()!=null){
                if(customerInfo.getSex()==1){
                    customerInfoVo.setSexMsg("男");
                }else if(customerInfo.getSex()==2){
                    customerInfoVo.setSexMsg("女");
                }
            }
            //数据字典
            if(customerInfo.getEducationBg() != null){
                tbDict = tbDictMapper.selectById(customerInfo.getEducationBg());
                if(tbDict !=null){
                    customerInfoVo.setEducationBgMsg(tbDict.getName());
                }
            }
            //学历
            if(customerInfo.getEducationBg() !=null){
                tbDict = tbDictMapper.selectById(customerInfo.getEducationBg());
                if(tbDict != null){
                    customerInfoVo.setEducationBgMsg(tbDict.getName());
                }
            }
            //状态
            if(customerInfo.getCustomerState() != null){
                tbDict = tbDictMapper.selectById(customerInfo.getCustomerState());
                if(tbDict != null){
                    customerInfoVo.setCustomerStateMsg(tbDict.getName());
                }
            }
            //级别
            if(StringUtils.isNotEmpty(customerInfo.getCustomerLevel()) ){
                tbDict = tbDictMapper.selectById(customerInfo.getCustomerLevel());
                if(tbDict!= null){
                    customerInfoVo.setCustomerLevelMsg(tbDict.getName());
                }
            }
            //目标技能
             if(StringUtils.isNotEmpty(customerInfo.getTargetSkill())){
                tbDict = tbDictMapper.selectById(customerInfo.getTargetSkill());
                if(tbDict!= null){
                    customerInfoVo.setTargetSkillMsg(tbDict.getName());
                }

             }
             //应聘渠道
            if(customerInfo.getRecruitChannel() !=null){
                 tbDict =tbDictMapper.selectById(customerInfo.getRecruitChannel());
                 if(tbDict != null){
                     customerInfoVo.setRecruitChannelMsg(tbDict.getName());
                 }

            }
            //所属公司
            if(customerInfo.getCompanyId() !=null){
                tbDict = tbDictMapper.selectById(customerInfo.getCompanyId());
                if(tbDict != null){
                    customerInfoVo.setCompanyIdMsg(tbDict.getName());
                }
            }
            //关单人
            if(customerInfo.getGuandan()!= null){
               employeeInfo =  employeeInfoMapper.selectById(customerInfo.getGuandan());
               if(employeeInfo != null){
                   customerInfoVo.setGuandaMsg(employeeInfo.getName());
               }
            }
            //咨询师
            if(customerInfo.getUserId() !=null){
                employeeInfo = employeeInfoMapper.selectById(customerInfo.getUserId());
                if(employeeInfo !=null){
                    customerInfoVo.setUserIdMsg(employeeInfo.getName());
                }
            }
            //创量人员 (邀约人)
            if(StringUtils.isNotEmpty(customerInfo.getIntroducer())){
                employeeInfo = employeeInfoMapper.selectById(customerInfo.getIntroducer());
                if(employeeInfo !=null){
                    customerInfoVo.setIntroducerMsg(employeeInfo.getName());
                }
            }
            customerInfoVos.add(customerInfoVo);
        }
        return customerInfoVos;
    }

    /**
     * 彻底删除学员信息
     * 传入的是客户端传来的id数字字符串  {23,45}
     * 1.先删除该学员的跟进记录
     * 2.后删除该学员的具体信息
     * @param customerInfoArr
     */
    @Override
    public void deleteCustomerInfo(String customerInfoArr) {
        //1.先删除学员的根进记录
         //if(cu)
        String[] idArr =  null;
        List<String> listIds = new ArrayList<>();
        if(customerInfoArr!=null && !"".equals(customerInfoArr)){
            idArr = customerInfoArr.split(",");
        }
        Map<String,Object> paramMap = new HashMap<>();
        if(idArr != null){
            for(String id :idArr){
                listIds.add(id);
                paramMap.put("customer_id",id) ;
                customerTraceRecordMapper.deleteByMap(paramMap);
                paramMap.clear();
            }
        }

        //2.删除学员的具体信息
        customerInfoMapper.deleteBatchIds(listIds);

    }

    /**
     * 获取当天的等待着列表
     * @param ci
     * @param ei
     * @return
     */
    @Override
    public List<CustomerInfoVo> customerInfoWaitList(CustomerInfo ci,EmployeeInfo ei) {
        List<CustomerInfo> customerInfoVoList = this.selectList(new EntityWrapper<CustomerInfo>()
                .where("company_id="+ei.getCompanyId())
                .and("customer_type="+ci.getCustomerType())
                .and("to_days(create_time)= to_days(now())"));
        return this.getCustomerInfoVos(customerInfoVoList);
    }

    @Override
    public List<CompanyCount> companyCount(Map<String, Object> map) {
        return customerInfoMapper.companyCount(map);
    }

    /**
     * 每月每个校区每个咨询师的报名量!!!
     * @param map
     * @return
     */
    @Override
    public List<CompanyCount> baoming(Map<String, Object> map) {
        return customerInfoMapper.baoming(map);
    }
    /**
     * 统计每月每个分公司中创量部的应聘渠道统计信息
     * @param map
     * @return
     */
    @Override
    public List<CompanyCount> recruitChannelMonthData(Map<String, Object> map) {
        return customerInfoMapper.recruitChannelMonthData(map);
    }

    @Override
    public List<CompanyCount> chuangliangMonthData(Map<String, Object> map) {
        return customerInfoMapper.chuangliangMonthData(map);
    }

    @Override
    public WelcomeVo totalCount() {
        return customerInfoMapper.totalCount();
    }

    @Override
    public List<Map<String,Object>> traceData(Map<String, Object> map) {
        return customerInfoMapper.traceData(map);
    }

    /**
     * 移动会销
     * @param map
     * @return
     */
    @Override
    public ServerResponse mobileHuiXiao(Map<String, Object> map) {
        List<MobileHuiXiaoVo> mobileHuiXiaoVos = this.customerInfoMapper.mobileHuiXiao(map);
        return ServerResponse.createBySuccess(mobileHuiXiaoVos);
    }

    /**
     * 修改数据
     * @param customerInfo
     * @return
     */
    @Override
    public ServerResponse updateSelectiveById(CustomerInfo customerInfo) {
        int result = customerInfoMapper.updateSelectiveById(customerInfo);;
        if(result ==1){
            return  ServerResponse.createBySuccess();
        }else{
            return  ServerResponse.createByError();
        }
    }

    /***
     * 前台展示数据
     * @param map
     * @return
     */
    @Override
    public Workbook foregroundExportData(Map<String, Object> map) {
        List<FrontVo> frontVoList = this.customerInfoMapper.foregroundExportData(map);
        if(frontVoList != null){
            for(FrontVo frontVo : frontVoList){
                if("0".equals(frontVo)){
                    frontVo.setMarket("无");
                }
            }
        }
        ExportParams ep = new ExportParams("招聘信息","招聘信息", ExcelType.XSSF);
        Workbook workbook  = ExcelExportUtil.exportBigExcel(ep,FrontVo.class,frontVoList);
        return workbook;
    }

    /**
     * 前台分页数据
     * @param map
     * @return
     */
    @Override
    public LayuiEntity<FrontVo> foregroundPage(Map<String, Object> map) {
        List<FrontVo>  obj= this.customerInfoMapper.foregroundExportData(map);
        LayuiEntity<FrontVo> layuiEntity=new LayuiEntity<>();
        layuiEntity.setCode(0);
        layuiEntity.setMsg("数据");
        layuiEntity.setCount(customerInfoMapper.foregroundExportDataCount(map));
        layuiEntity.setData(obj);
        return layuiEntity;
    }

    /**
     * 获取不跟进的咨询师信息
     * @param paramsMap
     * @return
     */
    @Override
    public LayuiEntity<NotFollowUpVo> selectByRecordTimeAndLevel(Map<String, Object> paramsMap) {
        LayuiEntity<NotFollowUpVo> layuiEntity = new LayuiEntity<>();
        layuiEntity.setCode(0);
        layuiEntity.setMsg("消息");
        layuiEntity.setCount(this.customerInfoMapper.selectByRecordTimeAndLevelCount(paramsMap));
        layuiEntity.setData(this.customerInfoMapper.selectByRecordTimeAndLevel(paramsMap));
        return layuiEntity;
    }

    /**
     * 定时任务获取每日跟进的信息
     * @param paramsMap
     * @return
     */
    @Override
    public List<NotFollowUpVo> selectByRecordTime(Map<String, Object> paramsMap) {
        return this.customerInfoMapper.selectByRecordTime(paramsMap);
    }

    /**
     * 定时把咨询的数据转到运营中
     * @param paramsMap
     * @return
     */
    @Override
    public int updateByLevelAndCountTime(Map<String, Object> paramsMap) {
        return this.customerInfoMapper.updateByLevelAndCountTime(paramsMap);
    }
}

package com.hzitshop.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hzitshop.entity.CustomerInfo;
import com.baomidou.mybatisplus.service.IService;
import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.entity.LayuiData;
import com.hzitshop.util.LayuiEntity;
import com.hzitshop.util.ServerResponse;
import com.hzitshop.vo.BootstrapTable;
import com.hzitshop.vo.CompanyCount;
import com.hzitshop.vo.CustomerInfoVo;
import com.hzitshop.vo.WelcomeVo;
import com.hzitshop.vo.customerinfovo.NotFollowUpVo;
import com.hzitshop.vo.front.FrontVo;
import com.hzitshop.vo.huixiao.MobileHuiXiaoVo;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-12
 */
public interface ICustomerInfoService extends IService<CustomerInfo> {

    BootstrapTable<CustomerInfoVo> ajaxData(Page<CustomerInfo> page, Wrapper<CustomerInfo> wrapper);

    /**
     * 获取列表数据
     * @param paramsMap
     * @return
     */
    BootstrapTable<CustomerInfoVo> listData(Map<String,Object> paramsMap);

    /**
     * 彻底删除学员数据
     * 传入的是客户端传来的id数组字符串  {23,45}  
     * @param customerInfoArr
     */
   void deleteCustomerInfo(String customerInfoArr);

    /**
     * 获取当天学员等待列表
     * @param ci
     * @param ei
     * @return
     */
   List<CustomerInfoVo> customerInfoWaitList(CustomerInfo ci, EmployeeInfo ei);

    /**
     * 根据公司获取目标技能
     * @param map
     * @return
     */
    List<CompanyCount> companyCount(Map<String,Object> map);
    /**
     * 每月每个校区每个咨询师的报名量!!!
     * @param map
     * @return
     */
    List<CompanyCount> baoming(Map<String,Object> map);


    /**
     * 统计每月每个分公司中创量部的应聘渠道统计信息
     * @param map
     * @return
     */
    List<CompanyCount> recruitChannelMonthData(Map<String,Object> map);
    /**
     * 统计每月每个分公司中创量部每个人的邀约人数
     * @param map
     * @return
     */
    List<CompanyCount> chuangliangMonthData(Map<String,Object> map);

    /**
     * 首页统计
     * @return
     */
    WelcomeVo totalCount();

    /**
     * 获取跟进数据
     * @param map
     * @return
     */
    List<Map<String,Object>> traceData(Map<String,Object> map);

    ServerResponse mobileHuiXiao(Map<String,Object> map);


    /**
     * 修改数据
     * @param customerInfo
     * @return
     */
    ServerResponse updateSelectiveById(CustomerInfo customerInfo);

    /**
     * 前台数据
     * @param map
     * @return
     */
   Workbook foregroundExportData(Map<String,Object> map);

    /**
     * 前台分页数据
     * @param map
     * @return
     */
   LayuiEntity<FrontVo> foregroundPage(Map<String,Object> map);

    /**
     * 获取不跟进的咨询师信息
      * @param paramsMap
     * @return
     */
   LayuiEntity<NotFollowUpVo> selectByRecordTimeAndLevel(Map<String,Object> paramsMap);

    /**
     * 定时任务每日获取没有跟进的信息
     * @param paramsMap
     * @return
     */
   List<NotFollowUpVo> selectByRecordTime(Map<String,Object> paramsMap);

    /**
     * 定时把咨询的数据转到运营中
      * @param paramsMap
     * @return
     */
   int updateByLevelAndCountTime(Map<String,Object> paramsMap);
}

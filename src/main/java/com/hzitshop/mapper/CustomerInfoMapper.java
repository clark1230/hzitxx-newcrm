package com.hzitshop.mapper;

import com.hzitshop.entity.CustomerInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hzitshop.vo.CompanyCount;
import com.hzitshop.vo.WelcomeVo;
import com.hzitshop.vo.customerinfovo.NotFollowUpVo;
import com.hzitshop.vo.front.FrontVo;
import com.hzitshop.vo.huixiao.MobileHuiXiaoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-12
 */
public interface CustomerInfoMapper extends BaseMapper<CustomerInfo> {
    /**
     * 根据公司获取目标技能
      * @param map
     * @return
     */
  List<CompanyCount> companyCount(@Param("map") Map<String,Object> map);

    /**
     * 每月每个校区每个咨询师的报名量!!!
     * @param map
     * @return
     */
  List<CompanyCount> baoming(@Param("map") Map<String,Object> map);

    /**
     * 统计每月每个分公司中创量部的应聘渠道统计信息
     * @param map
     * @return
     */
  List<CompanyCount> recruitChannelMonthData(@Param("map")Map<String,Object> map);

  /**
   * 统计每月每个分公司中创量部每个人的邀约人数
   * @param map
   * @return
   */
  List<CompanyCount> chuangliangMonthData(@Param("map")Map<String,Object> map);

  /**
   * 首页数据统计
   * @return
   */
  WelcomeVo totalCount();

  /**
   * 获取跟进数据
   * @param map
   * @return
   */
  List<Map<String,Object>> traceData(Map<String,Object> map);

  /**
   * 手机端获取会销数据
   * @param map
   * @return
   */
  List<MobileHuiXiaoVo> mobileHuiXiao(Map<String,Object> map);

  /**
   * 修改数据
   * @param customerInfo
   * @return
   */
  int updateSelectiveById(CustomerInfo customerInfo);

  /**
   * 前台数据
   * @param map
   * @return
   */
  List<FrontVo> foregroundExportData(Map<String,Object> map);

  /**
   * 前台数据统计
   * @param map
   * @return
   */
  long foregroundExportDataCount(Map<String,Object> map);

  /**
   * 查询学员信息
   * @param paramsMap
   * @return
   */
  List<CustomerInfo> searchCustomerInfo(@Param("map") Map<String,Object> paramsMap);

  /**
   * 获取学员记录数
   * @param paramsMpa
   * @return
   */
  int searchCustomerInfoCount(@Param("map") Map<String,Object> paramsMpa);

  /**
   * 获取不跟进的咨询师信息
   * @param paramsMap
   * @return
   */
  List<NotFollowUpVo> selectByRecordTimeAndLevel(Map<String,Object> paramsMap);

  int selectByRecordTimeAndLevelCount(Map<String,Object> paramsMap);

  /**
   * 每日定时获取没有跟进的信息
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
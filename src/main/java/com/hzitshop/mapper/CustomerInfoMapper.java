package com.hzitshop.mapper;

import com.hzitshop.entity.CustomerInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hzitshop.vo.CompanyCount;
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
}
package com.hzitshop.mapper;

import com.hzitshop.entity.CustomerTraceRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hzitshop.vo.trace.DailyTraceDataVo;
import com.hzitshop.vo.trace.TraceRecord;
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
public interface CustomerTraceRecordMapper extends BaseMapper<CustomerTraceRecord> {
    /**
     * 获取每日需要跟进的数据
     * @param paramMap
     * @return
     */
    List<DailyTraceDataVo> selectDailyTraceData(@Param("map")Map<String,Object> paramMap);

    /**
     * 获取每日需要跟进的数量
     * @param paramMap
     * @return
     */
    Integer selectDailyTraceDataCount(@Param("map")Map<String,Object> paramMap);

    /**
     * 移动端获取跟进信息
     * @param paramMap
     * @return
     */
    List<TraceRecord> mobileTraceMsg(@Param("map") Map<String,Object> paramMap);

    /**
     * 移动端添加跟进信息
     * @param customerTraceRecord
     * @return
     */
    int addMobileTraceMsg(CustomerTraceRecord customerTraceRecord);

    /**
     * 获取跟进记录
     * @param map
     * @return
     */
    List<TraceRecord> traceRecordData(Map<String,Object> map);

    /**
     * 获取跟进记录数
     * @param map
     * @return
     */
    long traceRecordDataCount(Map<String,Object> map);

}
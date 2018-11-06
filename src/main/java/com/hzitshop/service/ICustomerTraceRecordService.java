package com.hzitshop.service;

import com.hzitshop.entity.CustomerTraceRecord;
import com.baomidou.mybatisplus.service.IService;
import com.hzitshop.entity.LayuiData;
import com.hzitshop.util.LayuiEntity;
import com.hzitshop.util.ServerResponse;
import com.hzitshop.vo.trace.DailyTraceDataVo;
import com.hzitshop.vo.trace.TraceRecord;
import org.apache.ibatis.annotations.Param;

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
public interface ICustomerTraceRecordService extends IService<CustomerTraceRecord> {
    /**
     * 获取每日需要跟进的数据
     * @param paramMap
     * @return
     */
    LayuiData<DailyTraceDataVo> dailyTraceData(Map<String,Object> paramMap);

    /**
     * 获取每日需要跟进的数量
     * @param paramMap
     * @return
     */
    Integer selectDailyTraceDataCount(Map<String,Object> paramMap);

    /**
     * 获取每日需要跟进的数据
     * @param paramMap
     * @return
     */
    List<DailyTraceDataVo> selectDailyTraceData(Map<String,Object> paramMap);


    /**
     * 移动端获取跟进信息
     * @param paramMap
     * @return
     */
    List<TraceRecord> mobileTraceMsg(Map<String,Object> paramMap);

    /**
     * 移动端添加跟进信息
     * @param customerTraceRecord
     * @return
     */
    ServerResponse<String> addMobileTraceMsg(CustomerTraceRecord customerTraceRecord);

    /**
     * 跟进记录
     * @param map
     * @return
     */
    LayuiEntity<TraceRecord> traceRecordData(Map<String,Object> map);
}

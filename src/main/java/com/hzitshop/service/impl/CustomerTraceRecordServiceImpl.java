package com.hzitshop.service.impl;

import com.hzitshop.entity.CustomerTraceRecord;
import com.hzitshop.entity.LayuiData;
import com.hzitshop.mapper.CustomerTraceRecordMapper;
import com.hzitshop.service.ICustomerTraceRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hzitshop.util.LayuiEntity;
import com.hzitshop.util.ServerResponse;
import com.hzitshop.vo.trace.DailyTraceDataVo;
import com.hzitshop.vo.trace.TraceRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class CustomerTraceRecordServiceImpl extends ServiceImpl<CustomerTraceRecordMapper, CustomerTraceRecord> implements ICustomerTraceRecordService {
    @Autowired
    private CustomerTraceRecordMapper traceRecordMapper;

    /**
     * 获取每日统计的数据(封装为layui能够识别的数据格式)
     * @param paramMap
     * @return
     */
    @Override
    public LayuiData<DailyTraceDataVo> dailyTraceData(Map<String, Object> paramMap) {
        LayuiData<DailyTraceDataVo> dataVoLayuiData = new LayuiData<>();
        dataVoLayuiData.setCode(0);
        dataVoLayuiData.setMsg("跟进记录");
        dataVoLayuiData.setCount(this.selectDailyTraceDataCount(paramMap));
        dataVoLayuiData.setData(this.selectDailyTraceData(paramMap));
        return dataVoLayuiData;
    }

    /**
     * 获取每日需要跟进数据的数量
     * @param paramMap
     * @return
     */
    @Override
    public Integer selectDailyTraceDataCount(Map<String, Object> paramMap) {
        return traceRecordMapper.selectDailyTraceDataCount(paramMap);
    }

    /**
     * 获取每日需要跟进的数据
     * @param paramMap
     * @return
     */
    @Override
    public List<DailyTraceDataVo> selectDailyTraceData(Map<String, Object> paramMap) {
        return traceRecordMapper.selectDailyTraceData(paramMap);
    }

    /**
     * 移动端获取跟进信息
     * @param paramMap
     * @return
     */
    @Override
    public List<TraceRecord> mobileTraceMsg(Map<String, Object> paramMap) {
        return traceRecordMapper.mobileTraceMsg(paramMap);
    }

    /**
     * 移动端添加跟进信息
     * @param customerTraceRecord
     * @return
     */
    @Override
    public ServerResponse<String> addMobileTraceMsg(CustomerTraceRecord customerTraceRecord) {
        int result =  traceRecordMapper.addMobileTraceMsg(customerTraceRecord);
        if(result == 1){
            return ServerResponse.createBySuccessMessage("添加成功!");
        }else{
            return ServerResponse.createByErrorCodeMessage(1,"添加失败!");
        }
    }

    /**
     * 跟进记录
     * @param map
     * @return
     */
    @Override
    public LayuiEntity<TraceRecord> traceRecordData(Map<String, Object> map) {
        LayuiEntity<TraceRecord> layuiEntity = new LayuiEntity<>();
        layuiEntity.setCode(0);
        layuiEntity.setMsg("跟进");
        layuiEntity.setCount(this.traceRecordMapper.traceRecordDataCount(map));
        layuiEntity.setData(this.traceRecordMapper.traceRecordData(map));
        return layuiEntity;
    }
}

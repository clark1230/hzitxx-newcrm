package com.hzitshop.service.impl;

import com.hzitshop.entity.LayuiData;
import com.hzitshop.entity.LogEntity;
import com.hzitshop.mapper.LogMapper;
import com.hzitshop.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;

    /**
     * 添加日志信息
     * @param log
     * @return
     */
    @Override
    public int saveLog(LogEntity log) {
        return logMapper.insertTbLog(log);
    }

    /**
     * 日志查询分页
     * @param map
     * @return
     */
    @Override
    public LayuiData<LogEntity> page(Map<String, Object> map) {
        List<LogEntity> logEntities = logMapper.searchTbLogByParams(map);
        LayuiData<LogEntity> layuiData = new LayuiData<>();
        layuiData.setCode(0);
        layuiData.setCount(logMapper.getTotal(map));
        layuiData.setMsg("log");
        layuiData.setData(logEntities);
        return layuiData;
    }
}

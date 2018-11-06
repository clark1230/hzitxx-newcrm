package com.hzitshop.service;

import com.hzitshop.entity.LayuiData;
import com.hzitshop.entity.LogEntity;

import java.util.Map;

public interface LogService {
    /**
     * 保存日志信息
     * @param log
     * @return
     */
    public int saveLog(LogEntity log);

    /**
     * 查询分页
     * @param map
     * @return
     */
    public LayuiData<LogEntity> page(Map<String, Object> map);
}

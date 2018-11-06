package com.hzitshop.service;

import com.hzitshop.entity.Settings;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xianyaoji
 * @since 2018-02-26
 */

public interface ITbSettingsService  {

    int addTbSettings(Settings tbSettings);

    int addTbSettingsSelective(Settings obj);

    int deleteById(int id);

    int deleteByIds(String[] ids);

    int updateById(Settings tbSettings);

    int updateSelectiveById(Settings tbSettings);

    List<Settings> searchTbSettings(Map<String, Object> map);

    /**
     * 数据查询分页
     * @param page
     * @param limit
     * @param map
     * @return
     */
//    LayuiEntity<Settings> page(int page, int limit, Map<String, Object> map);

    /**
     * 根据编号查询数据
     *
     */
    Settings findOne(Integer id);

}

package com.hzitshop.mapper;

import com.hzitshop.entity.Settings;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xianyaoji
 * @since 2018-02-26
 */
public interface SettingsMapper {

    int addTbSettings(Settings obj);

    int addTbSettingsSelective(Settings obj);

    int deleteById(int id);
                
    int deleteByIds(String[] ids);

    int updateById(Settings tbSettings);

    int updateSelectiveById(Settings tbSettings);

    Settings findOne(Integer id);
                
    List<Settings> searchTbSettings(@Param("map") Map<String, Object> map);
}
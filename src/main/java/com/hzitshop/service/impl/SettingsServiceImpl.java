package com.hzitshop.service.impl;

import com.hzitshop.entity.Settings;
import com.hzitshop.mapper.SettingsMapper;
import com.hzitshop.service.ITbSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xianyaoji
 * @since 2018-02-26
 */
@Service
public class SettingsServiceImpl implements ITbSettingsService {

    @Autowired
    private SettingsMapper mapper;

    @Override
    public int addTbSettings(Settings tbSettings){
        return mapper.addTbSettings(tbSettings);
    }

    @Override
    public int addTbSettingsSelective(Settings tbSettings){
        return mapper.addTbSettingsSelective(tbSettings);
    }

    @Override
    public int deleteById(int id){
            return mapper.deleteById(id);
    }

    @Override
    public int deleteByIds(String[]ids){
        return mapper.deleteByIds(ids);
    }
    @Override
    public  int updateById(Settings tbSettings){
        return mapper.updateById(tbSettings);
    }

    @Override
    public  int updateSelectiveById(Settings tbSettings){
        return mapper.updateSelectiveById(tbSettings);
    }

    @Override
    public  List<Settings> searchTbSettings(Map<String, Object> map){
        return mapper.searchTbSettings(map);
    }

//    /**
//     * 数据查询分页
//     * @param page
//     * @param limit
//     * @param map
//     * @return
//     */
//    @Override
//    public LayuiEntity<Settings> page(int page,int limit,Map<String, Object> map){
//        PageHelper.startPage(page,limit);
//        List<Settings>  obj=mapper.searchTbSettings(map);
//        PageInfo<Settings> pageInfo=new PageInfo<>(obj);
//        LayuiEntity<Settings> layuiEntity=new LayuiEntity<>();
//        layuiEntity.setCode(0);
//        layuiEntity.setMsg("数据");
//        layuiEntity.setCount(pageInfo.getTotal());
//        layuiEntity.setData(pageInfo.getList());
//        return layuiEntity;
//    }

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    @Override
    public Settings findOne(Integer id){
            return mapper.findOne(id);
     }
}


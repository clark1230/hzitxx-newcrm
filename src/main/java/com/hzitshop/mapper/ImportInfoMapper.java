package com.hzitshop.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hzitshop.entity.ImportInfo;
import com.hzitshop.vo.FilterData;
import com.hzitshop.vo.FilterDataCount;
import com.hzitshop.vo.importresultvo.ImportVoNameId;
import com.hzitshop.vo.importresultvo.SelectByRealName;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-09-08 19:03
 * @description
 */
public interface ImportInfoMapper extends BaseMapper<ImportInfo>{
    /**
     * 创量数据筛选
     * @param paramMap
     * @return
     */
    List<FilterData> filterData(@Param("map")Map<String,Object> paramMap);

    /**
     * 过滤数据统计
     * @param paramMap
     * @return
     */
    FilterDataCount filterDataCount(@Param("map") Map<String,Object> paramMap);

    /**
     * 逻辑删除数据
     * @param paramMap
     * @return
     */
    int updateByStatus(@Param("map") Map<String,Object> paramMap);

    /**
     * 简历excel导入时判断当前简历是否存在重复数据
     * @param map
     * @return
     */
    List<Integer> checkPersonalRepreat(@Param("map") Map<String,Object> map);

    /**
     * 检查数据重复量
     * @param paramMap
     * @return
     */
    Integer checkCount(@Param("map") Map<String,Object> paramMap);

    /**
     * 修改重复数据
     * @param paramMap
     * @return
     */
    Integer updateCheckNum(@Param("map") Map<String,Object> paramMap);

    /**
     * 获取最近导入的重复数据
     * @return
     */
    List<ImportVoNameId> checkDailyInfo();

    /**
     * 获取最近重复的应聘者姓名
     * @return
     */
    List<ImportVoNameId> checkDailyName(@Param("map")Map<String,Object> paramMap);

    /**
     * 根据应聘者姓名获取相应的邀约人，应聘者状态，和是否删除
     * @param paramMap
     * @return
     */
    List<SelectByRealName> selectByRealName(@Param("map")Map<String,Object> paramMap);

    /**
     * 获取重复名称的数量
     * @return
     */
    int selectByRealNameCount(@Param("map")Map<String,Object> paramMap);

    /**
     * 根据编号修改数据
     * @param info
     * @return
     */
    Integer updateById2(ImportInfo info);

    /**
     * 查询半年中有没有预约的信息
     * @param paramMap
     * @return
     */
    ImportInfo checkInfoByHalfYear(@Param("map")Map<String,Object> paramMap);

    int updateSelectiveById(ImportInfo importInfo);

    /**
     * 前台检查用户是否存在
     * @param map
     * @return
     */
    List<ImportInfo> checkRealName(ImportInfo importInfo);

    /**
     * 前台修改状态为已上门
     * @param importInfo
     * @return
     */
    int changeStatus(ImportInfo importInfo);
}

package com.hzitshop.mapper;

import com.hzitshop.entity.EmployeeInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hzitshop.vo.employeevo.EmployeeVoNameId;
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
public interface EmployeeInfoMapper extends BaseMapper<EmployeeInfo> {
    /**
     * 获取创量信息
      * @param paramMap
     * @return
     */
    List<EmployeeInfo> selectByCompanyId(@Param("map")Map<String,Object> paramMap);

    /**
     * 根据角角色名称和公司获取相关人员
     * @param paramMap
     * @return
     */
    List<EmployeeVoNameId> selectByRole(@Param("map") Map<String,Object> paramMap);
}
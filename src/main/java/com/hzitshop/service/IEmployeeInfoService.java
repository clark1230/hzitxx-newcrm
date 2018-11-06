package com.hzitshop.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hzitshop.entity.EmployeeInfo;
import com.baomidou.mybatisplus.service.IService;
import com.hzitshop.vo.BootstrapTable;
import com.hzitshop.vo.EmployeeInfoVo;
import com.hzitshop.vo.employeevo.EmployeeVoNameId;
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
public interface IEmployeeInfoService extends IService<EmployeeInfo> {
    /*@Override
    Page<EmployeeInfoVo> selectPage(Page<EmployeeInfo> page, Wrapper<EmployeeInfo> wrapper);*/
    public BootstrapTable<EmployeeInfoVo> ajaxData(Page<EmployeeInfo> page, Wrapper<EmployeeInfo> wrapper);

    /**
     * 获取咨询师数据
     * @param employeeInfo
     * @return
     */
    public List<EmployeeInfoVo> consultantData(EmployeeInfo employeeInfo);

    /**
     * 根据用户id获取该用户所能访问的按钮资源
     * @param employeeInfo
     * @return
     */
    public List<String> getButtonResource(EmployeeInfo employeeInfo);

    /**
     * 根据角角色名称和公司获取相关人员
     * @param paramMap
     * @return
     */
    List<EmployeeVoNameId> selectByRole(@Param("map") Map<String,Object> paramMap);
}

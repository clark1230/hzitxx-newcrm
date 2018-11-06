package com.hzitshop.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.google.common.base.Equivalence;
import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.entity.ImportInfo;
import com.hzitshop.util.ServerResponse;
import com.hzitshop.vo.BootstrapTable;
import com.hzitshop.vo.ImportInfoFilterData;
import com.hzitshop.vo.ImportInfoVo;
import com.hzitshop.vo.importresultvo.ImportVoNameId;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.entity.ExportParams;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-09-08 18:42
 * @description
 */
public interface ImportInfoService extends IService<ImportInfo> {

    String importExcel(InputStream is, String recruitChannel,Integer cvType,HttpSession session);

    BootstrapTable<ImportInfoVo> ajaxData(Page<ImportInfo> page, Wrapper<ImportInfo> wrapper);

    /**
     * 创量筛选数据
     * @param paramMap
     * @return
     */
    List<ImportInfoFilterData> filterData(Map<String,Object> paramMap);

    /**
     * 逻辑删除数据
     * @param paramMap
     * @return
     */
    ServerResponse<String> deleteByStatus(Map<String,Object> paramMap);

    /**
     * 获取最近导入的重复数据
     * @return
     */
    List<ImportVoNameId> checkDailyInfo();

    /**
     * 获取最近一周的重复数据
     * @return
     */
    JSONObject  checkDailyImportInfo(Map<String,Object> map);
    /**
     * 根据编号修改数据
     * @param info
     * @return
     */
    Integer updateById2(ImportInfo info);

    public Workbook exportExcel(Wrapper<ImportInfo> wrapper);

    /**
     * 修改数据
     * @param importInfo
     * @return
     */
    boolean  updateSelectiveById(ImportInfo importInfo);

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


    /**
     * 将创量数据转到运营中
     * @param customerIdArr
     * @return
     */
    ServerResponse turnToYunYing(Integer[] customerIdArr, EmployeeInfo ei);
}

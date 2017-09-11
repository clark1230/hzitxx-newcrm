package com.hzitshop.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.google.common.base.Equivalence;
import com.hzitshop.entity.ImportInfo;
import com.hzitshop.vo.BootstrapTable;
import com.hzitshop.vo.ImportInfoVo;

import javax.servlet.http.HttpSession;
import java.io.InputStream;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-09-08 18:42
 * @description
 */
public interface ImportInfoService extends IService<ImportInfo> {

    boolean importExcel(InputStream is, Integer recruitChannel,HttpSession session);

    BootstrapTable<ImportInfoVo> ajaxData(Page<ImportInfo> page, Wrapper<ImportInfo> wrapper);
}

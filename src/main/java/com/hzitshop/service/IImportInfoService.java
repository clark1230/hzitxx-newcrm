package com.hzitshop.service;

import com.baomidou.mybatisplus.service.IService;
import com.hzitshop.entity.ImportInfo;

import javax.servlet.http.HttpSession;
import java.io.InputStream;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-09-08 18:42
 * @description
 */
public interface IImportInfoService extends IService<ImportInfo> {

    boolean importExcel(InputStream is, Integer recruitChannel,HttpSession session);
}

package com.hzitshop.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hzitshop.entity.ImportInfo;
import com.hzitshop.mapper.ImportInfoMapper;
import com.hzitshop.service.IImportInfoService;
import com.hzitshop.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.Map;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-09-08 19:01
 * @description
 */
@Service
public class ImportInfoServiceImpl extends ServiceImpl<ImportInfoMapper,ImportInfo> implements IImportInfoService{

    @Autowired
    private ImportInfoMapper importInfoMapper;

    @Transactional
    @Override
    public boolean importExcel(InputStream is, Integer recruitChannel,HttpSession session) {
        boolean flag = false;
        switch (recruitChannel){
            case 22:
                //智联招聘渠道文件导入
                flag = ExcelUtil.zhilianImport(is,recruitChannel,importInfoMapper,session);
                break;
            case 23:
                //前程无忧渠道文件导入
                flag = ExcelUtil.qianchengImport(is,recruitChannel,importInfoMapper,session);
                break;
            case 24:
                //58同城
                flag = ExcelUtil.tongchengImport(is,recruitChannel,importInfoMapper,session);
                break;
            case 87:
                //人才热线
                flag = ExcelUtil.rencaiImport(is,recruitChannel,importInfoMapper,session);
                break;
            case 88:
                //中华英才
                flag = ExcelUtil.zhonghuaImport(is,recruitChannel,importInfoMapper,session);
                break;
            case 89:
                //赶集网
                flag = ExcelUtil.ganjiImport(is,recruitChannel,importInfoMapper,session);
                break;
            default:
                flag = false;
                break;
        }
        return flag;
    }
}

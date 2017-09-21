package com.hzitshop.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.entity.ImportInfo;
import com.hzitshop.entity.TbDict;
import com.hzitshop.mapper.EmployeeInfoMapper;
import com.hzitshop.mapper.ImportInfoMapper;
import com.hzitshop.mapper.TbDictMapper;
import com.hzitshop.service.ImportInfoService;
import com.hzitshop.util.ExcelUtil;
import com.hzitshop.vo.BootstrapTable;
import com.hzitshop.vo.ChannelPropertiesVo;
import com.hzitshop.vo.ImportInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-09-08 19:01
 * @description
 */
@Service
@EnableConfigurationProperties(ChannelPropertiesVo.class)
public class ImportInfoServiceImpl extends ServiceImpl<ImportInfoMapper,ImportInfo> implements ImportInfoService {

    @Autowired
    private ChannelPropertiesVo channelPropertiesVo;

    @Autowired
    private ImportInfoMapper importInfoMapper;

    @Autowired
    private TbDictMapper tbDictMapper;

    @Autowired
    private EmployeeInfoMapper employeeInfoMapper;

    @Transactional
    @Override
    public boolean importExcel(InputStream is, Integer recruitChannel,HttpSession session) {
        boolean flag = false;
        if(recruitChannel == channelPropertiesVo.getZhilian()) {
            //智联招聘渠道文件导入
            flag = ExcelUtil.zhilianImport(is, recruitChannel, importInfoMapper, session);
            return flag;
        }
        if(recruitChannel == channelPropertiesVo.getQiancheng()) {
            //前程无忧渠道文件导入
            flag = ExcelUtil.qianchengImport(is, recruitChannel, importInfoMapper, session);
            return flag;
        }
        if(recruitChannel == channelPropertiesVo.getTongcheng()) {
            //58同城
            flag = ExcelUtil.tongchengImport(is, recruitChannel, importInfoMapper, session);
            return flag;
        }
        if(recruitChannel == channelPropertiesVo.getRencai()) {
            //人才热线
            flag = ExcelUtil.rencaiImport(is, recruitChannel, importInfoMapper, session);
            return flag;
        }
        if(recruitChannel == channelPropertiesVo.getZhonghua()) {
            //中华英才
            flag = ExcelUtil.zhonghuaImport(is, recruitChannel, importInfoMapper, session);
            return flag;
        }
        if(recruitChannel == channelPropertiesVo.getGanji()) {
            //赶集网
            flag = ExcelUtil.ganjiImport(is, recruitChannel, importInfoMapper, session);
            return flag;
        }
        return flag;
    }

    @Override
    public BootstrapTable<ImportInfoVo> ajaxData(Page<ImportInfo> page, Wrapper<ImportInfo> wrapper) {
        Page<ImportInfo> resultPage = this.selectPage(page,wrapper);
        BootstrapTable<ImportInfoVo> bootstrapTable = new BootstrapTable<>();
        bootstrapTable.setTotal(resultPage.getTotal());//获取总记录数
        List<ImportInfo> importInfoList = page.getRecords();
        List<ImportInfoVo> importInfoVos = getImportInfoVos(importInfoList);
        bootstrapTable.setRows(importInfoVos);
        return bootstrapTable;
    }

    private List<ImportInfoVo> getImportInfoVos(List<ImportInfo> importInfoList) {
        List<ImportInfoVo> importInfoVos = new ArrayList<>();
        //数据字典
        ImportInfoVo importInfoVo =  null;
        EmployeeInfo employeeInfo = null;
        TbDict tbDict =  null;
        for(ImportInfo importInfo : importInfoList){
            importInfoVo = new ImportInfoVo();
            BeanUtils.copyProperties(importInfo, importInfoVo);
            if(importInfo.getSex()!=null){
                if(importInfo.getSex()==1){
                    importInfoVo.setSexMsg("男");
                }else if(importInfo.getSex()==2){
                    importInfoVo.setSexMsg("女");
                }
            }
            //数据字典
            if(importInfo.getEducationBg() != null){
                tbDict = tbDictMapper.selectById(importInfo.getEducationBg());
                if(tbDict !=null){
                    importInfoVo.setEducationBgMsg(tbDict.getName());
                }
            }
            //学历
            if(importInfo.getEducationBg() !=null){
                tbDict = tbDictMapper.selectById(importInfo.getEducationBg());
                if(tbDict != null){
                    importInfoVo.setEducationBgMsg(tbDict.getName());
                }
            }
            //状态
            if(importInfo.getCustomerState() != null){
                tbDict = tbDictMapper.selectById(importInfo.getCustomerState());
                if(tbDict != null){
                    importInfoVo.setCustomerStateMsg(tbDict.getName());
                }
            }
            //级别
            if(StringUtils.isNotEmpty(importInfo.getCustomerLevel()) ){
                tbDict = tbDictMapper.selectById(importInfo.getCustomerLevel());
                if(tbDict!= null){
                    importInfoVo.setCustomerLevelMsg(tbDict.getName());
                }
            }
            //应聘渠道
            if(importInfo.getRecruitChannel() !=null){
                tbDict =tbDictMapper.selectById(importInfo.getRecruitChannel());
                if(tbDict != null){
                    importInfoVo.setRecruitChannelMsg(tbDict.getName());
                }

            }
            //所属公司
            if(importInfo.getCompanyId() !=null){
                tbDict = tbDictMapper.selectById(importInfo.getCompanyId());
                if(tbDict != null){
                    importInfoVo.setCompanyIdMsg(tbDict.getName());
                }
            }
            //关单人
            if(importInfo.getGuandan()!= null){
                employeeInfo =  employeeInfoMapper.selectById(importInfo.getGuandan());
                if(employeeInfo != null){
                    importInfoVo.setGuandaMsg(employeeInfo.getName());
                }
            }
            //咨询师
            if(importInfo.getUserId() !=null){
                employeeInfo = employeeInfoMapper.selectById(importInfo.getUserId());
                if(employeeInfo !=null){
                    importInfoVo.setUserIdMsg(employeeInfo.getName());
                }
            }
            //创量人员 (邀约人)
            if(StringUtils.isNotEmpty(importInfo.getIntroducer())){
                employeeInfo = employeeInfoMapper.selectById(importInfo.getIntroducer());
                if(employeeInfo !=null){
                    importInfoVo.setIntroducerMsg(employeeInfo.getName());
                }
            }
            if(importInfo.getCvType() != null){
                if(importInfo.getCvType() == 1){
                    importInfoVo.setCvTypeMsg("投递");
                }else if(importInfo.getCvType() == 2){
                    importInfoVo.setCvTypeMsg("下载");
                }
            }
            importInfoVos.add(importInfoVo);
        }
        return importInfoVos;
    }
}

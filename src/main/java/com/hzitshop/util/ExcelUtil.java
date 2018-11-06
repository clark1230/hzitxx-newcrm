package com.hzitshop.util;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.entity.ImportInfo;
import com.hzitshop.mapper.ImportInfoMapper;
import com.hzitshop.service.ImportInfoService;
import com.hzitshop.service.impl.ImportInfoServiceImpl;
import com.hzitshop.vo.importvo.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by 冼耀基 on 2016/9/24.
 */
public class ExcelUtil {
    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    @Autowired
    private static ImportInfoService importInfoServiceImpl ;

    @Autowired
    private static ImportInfoMapper importInfoMapper;
    public static void test(){
         HSSFWorkbook workbook = new HSSFWorkbook();//创建工作簿对象
         HSSFSheet sheet = workbook.createSheet();
     }
    /**
     * 智联招聘简历模板导入
     * @param inputStream
     * @param recruitChannel
     * @param importInfoMapper
     * @param session
     * @return
     */

    public static String zhilianImport(InputStream inputStream,String recruitChannel,Integer cvType,ImportInfoMapper importInfoMapper,HttpSession session){
        String msg = null;
        EmployeeInfo em = (EmployeeInfo) session.getAttribute("employeeInfo");
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<ZhilianCustomerVo> list = null;
        try {
            list = ExcelImportUtil.importExcel(inputStream, ZhilianCustomerVo.class, params);
            List<ImportInfo> list2 = new ArrayList<>();
            if(list.size() != 0 && list != null){
                for(ZhilianCustomerVo z : list){
                    if(z.getTel() != null) {
                        ImportInfo i = z.changeToCustomerInfo(z);
                        commons(recruitChannel, cvType, em, list2, i);
                    }
                }
            }
            //插入去重过滤
            msg = insertFilter(list2,importInfoMapper);
        } catch (Exception e) {
            logger.error(e.getMessage());

        } finally {
            commonClose(inputStream);
        }
        return msg;
    }


    /**
     * 58同城简历模板导入
     * @param inputStream
     * @param recruitChannel
     * @param importInfoMapper
     * @param session
     * @return
     */
    public static String tongchengImport(InputStream inputStream,String recruitChannel,Integer cvType,ImportInfoMapper importInfoMapper,HttpSession session){
        String msg  = null;
        EmployeeInfo em = (EmployeeInfo) session.getAttribute("employeeInfo");
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<TongchengCustomerVo> list = null;
        try {
            list = ExcelImportUtil.importExcel(inputStream, TongchengCustomerVo.class, params);
            List<ImportInfo> list2 = new ArrayList<>();
            if(list.size() != 0 && list != null) {
                for (TongchengCustomerVo tc : list) {
                    if (tc.getTel() != null) {
                        ImportInfo i = tc.changeToCustomerInfo(tc);
                        commons(recruitChannel, cvType, em, list2, i);
                    }
                }
            }
            //插入去重过滤
            msg = insertFilter(list2,importInfoMapper);
        } catch (Exception e) {
            logger.error(e.getMessage());

        } finally {
            commonClose(inputStream);
        }
        return msg;
    }

    private static void commonClose(InputStream inputStream) {
        if (inputStream!=null)
            try {
                inputStream.close();
            } catch (IOException e) {
                logger.error("流关闭异常!"+e.getMessage());
            }
    }

    /**
     * 51Job简历模板导入
     * @param inputStream
     * @param recruitChannel
     * @param importInfoMapper
     * @param session
     * @return
     */
    public static String qianchengImport(InputStream inputStream,String recruitChannel,Integer cvType,ImportInfoMapper importInfoMapper,HttpSession session){
        String msg = null;
        EmployeeInfo em = (EmployeeInfo) session.getAttribute("employeeInfo");
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<QianchengCustomerVo> list = null;
        try {
            list = ExcelImportUtil.importExcel(inputStream, QianchengCustomerVo.class, params);
            List<ImportInfo> list2 = new ArrayList<>();
            if(list.size() != 0 && list != null){
                for(QianchengCustomerVo qc : list){
                    if(qc.getTel() != null) {
                        ImportInfo i = qc.changeToCustomerInfo(qc);
                        commons(recruitChannel, cvType, em, list2, i);
                    }
                }
            }
            //插入去重过滤
            msg = insertFilter(list2,importInfoMapper);
        }  catch (Exception e) {
            logger.error(e.getMessage());

        }finally {
            commonClose(inputStream);
        }
        return msg;
    }

    /**
     * 赶集网简历模板导入
     * @param inputStream
     * @param recruitChannel
     * @param importInfoMapper
     * @param session
     * @return
     */
    public static String ganjiImport(InputStream inputStream,String recruitChannel,Integer cvType,ImportInfoMapper importInfoMapper,HttpSession session){
        String msg = null;
        EmployeeInfo em = (EmployeeInfo) session.getAttribute("employeeInfo");
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<GanjiCustomerVo> list = null;
        try {
            list = ExcelImportUtil.importExcel(inputStream, GanjiCustomerVo.class, params);
            List<ImportInfo> list2 = new ArrayList<>();
            if(list.size() != 0 && list != null) {
                for (GanjiCustomerVo gj : list) {
                    if (gj.getTel() != null) {
                        ImportInfo i = gj.changeToCustomerInfo(gj);
                        commons(recruitChannel, cvType, em, list2, i);
                    }
                }
            }
            //插入去重过滤
            msg = insertFilter(list2,importInfoMapper);
        } catch (Exception e) {
            logger.error(e.getMessage());

        }finally {
            commonClose(inputStream);
        }
        return msg;
    }


    /**
     * 人才热线简历模板导入
     * @param inputStream
     * @param recruitChannel
     * @param importInfoMapper
     * @param session
     * @return
     */
    public static String rencaiImport(InputStream inputStream,String recruitChannel,Integer cvType,
                                      ImportInfoMapper importInfoMapper,HttpSession session){
        String msg = null;
        EmployeeInfo em = (EmployeeInfo) session.getAttribute("employeeInfo");
        ImportParams params = new ImportParams();
        //params.setTitleRows(0);
        //params.setHeadRows(1);
        params.setStartSheetIndex(0);
       // params.setSheetNum(1);
        List<RencaiCustomerVo> list = null;
        try {
            list = ExcelImportUtil.importExcel(inputStream, RencaiCustomerVo.class, params);
            List<ImportInfo> list2 = new ArrayList<>();
            if(list.size() != 0 && list != null) {
                for (RencaiCustomerVo rc : list) {
                    if (rc.getTel() != null) {
                        ImportInfo i = rc.changeToCustomerInfo(rc);
                        commons(recruitChannel, cvType, em, list2, i);
                    }
                }
            }
            //插入去重过滤
           msg = insertFilter(list2,importInfoMapper);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

        }finally {
            commonClose(inputStream);
        }
        return msg;
    }

    /**
     * 中华英才简历模板导入
     * @param inputStream
     * @param recruitChannel
     * @param importInfoMapper
     * @param session
     * @return
     */

    public static String zhonghuaImport(InputStream inputStream,String recruitChannel,Integer cvType,ImportInfoMapper importInfoMapper,HttpSession session){
        String msg = null;
        EmployeeInfo em = (EmployeeInfo) session.getAttribute("employeeInfo");
        ImportParams params = new ImportParams();
        params.setHeadRows(2);
        List<ZhonghuaCustomerVo> list = null;
        try {
            list = ExcelImportUtil.importExcel(inputStream, ZhonghuaCustomerVo.class, params);
            List<ImportInfo> list2 = new ArrayList<>();
            if(list.size() != 0 && list != null) {
                for (ZhonghuaCustomerVo zh : list) {
                    if (zh.getTel() != null) {
                        ImportInfo i = zh.changeToCustomerInfo(zh);
                        commons(recruitChannel, cvType, em, list2, i);
                    }
                }
            }
            //插入去重过滤
            msg = insertFilter(list2,importInfoMapper);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

        }finally {
            commonClose(inputStream);
        }
       return msg;
    }

    /**
     * 百姓网简历模板导入
     * @param inputStream
     * @param recruitChannel
     * @param importInfoMapper
     * @param session
     * @return
     */

    public static String baixingImport(InputStream inputStream,String recruitChannel,Integer cvType,ImportInfoMapper importInfoMapper,HttpSession session){
        String msg = null;
        EmployeeInfo em = (EmployeeInfo) session.getAttribute("employeeInfo");
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<BaixingCustomerVo> list = null;
        try {
            list = ExcelImportUtil.importExcel(inputStream, BaixingCustomerVo.class, params);
            List<ImportInfo> list2 = new ArrayList<>();
            if(list.size() != 0 && list != null) {
                for (BaixingCustomerVo bx : list) {
                    if (bx.getTel() != null) {
                        ImportInfo i = bx.changeToCustomerInfo(bx);
                        commons(recruitChannel, cvType, em, list2, i);
                    }
                }
            }
            //插入去重过滤
            msg = insertFilter(list2,importInfoMapper);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }finally {
            commonClose(inputStream);
        }
        return msg;
    }

    private static void commons(String recruitChannel, Integer cvType, EmployeeInfo em, List<ImportInfo> list2, ImportInfo i) {
        i.setRecruitChannel(recruitChannel);
        i.setCompanyId(em.getCompanyId());
        i.setCvType(cvType);
        i.setIntroducer(em.getUserId()+"");
        i.setCreateBy(em.getName());
        list2.add(i);
    }

    /**
     * 传入ImportInfo List,判断该学员是否存在,然后去重
     * @param list
     * @param importInfoMapper
     * @return
     */
    private static String insertFilter(List<ImportInfo> list,ImportInfoMapper importInfoMapper){
        String msg = null;
        int result = 0;
        int repreat = 0;
        for(ImportInfo i : list){
            if(!"".equals(i.getTel()) && i.getTel() != null){
                result = 0;
                ImportInfo im = new ImportInfo();
                im.setCompanyId(i.getCompanyId());
                i.setCreateTime(new Date());
                im.setTel(i.getTel());
                //判断数据的重复 当天  同一个邀约人
                Map<String,Object> paramMap = new HashMap<>();
                paramMap.put("realName",i.getRealName());
                paramMap.put("introducer",i.getIntroducer());//录入人
                List<Integer> checkResult = importInfoMapper.checkPersonalRepreat(paramMap);
                if(checkResult == null || checkResult.size() <1){ //判断当日统一录入人数据是否重复
                    //判断次数
                    paramMap.remove("introducer");
                    Integer checkInportCount = importInfoMapper.checkCount(paramMap);
                    if(checkInportCount >0){  //判断当日其他人导入的重复数据量
                        i.setRepeatNum(checkInportCount+1);
                        i.setStatus("未上门");
                        //更新其他数据
                        paramMap.put("repeatNum",i.getRepeatNum());
                        importInfoMapper.updateCheckNum(paramMap);
                        result = importInfoMapper.insert(i); //插入数据
                    }else{
                        i.setRepeatNum(0);
                        result = importInfoMapper.insert(i);
                    }
                }else{
                    repreat++;
                }
            }
        }
        if(result ==0){
            if(repreat ==0){
                msg = "导入失败!";
            }else{
                msg ="excel文件数据全部重复!";
            }
        }else{
            if(repreat!= 0){
                msg = "导入成功,已过滤重复的数据!";
            }else{
                msg = "导入成功!";
            }
        }
        return msg;
    }

}

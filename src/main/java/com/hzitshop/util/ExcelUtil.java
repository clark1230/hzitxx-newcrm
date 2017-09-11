package com.hzitshop.util;

import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.entity.ImportInfo;
import com.hzitshop.mapper.ImportInfoMapper;
import com.hzitshop.vo.importvo.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 冼耀基 on 2016/9/24.
 */
public class ExcelUtil {
         public static void test(){

             HSSFWorkbook workbook = new HSSFWorkbook();//创建工作簿对象
             HSSFSheet sheet = workbook.createSheet();
             
             
         }
    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    /**
     * 智联招聘简历模板导入
     * @param inputStream
     * @param recruitChannel
     * @param importInfoMapper
     * @param session
     * @return
     */

    public static boolean zhilianImport(InputStream inputStream,Integer recruitChannel,ImportInfoMapper importInfoMapper,HttpSession session){
        boolean flag = false;
        EmployeeInfo em = (EmployeeInfo) session.getAttribute("employeeInfo");
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<ZhilianCustomerVo> list = null;
        try {
            list = ExcelImportUtil.importExcel(inputStream, ZhilianCustomerVo.class, params);
            List<ImportInfo> list2 = new ArrayList<>();
            for(ZhilianCustomerVo z : list){
                if(z.getTel() != null) {
                    z.setRecruitChannel(recruitChannel);
                    z.setCompanyId(em.getCompanyId());
                    z.setIntroducer(em.getUserId().toString());
                    ImportInfo i = z.changeToCustomerInfo(z);
                    list2.add(i);
                }
            }
            for(ImportInfo i : list2){
//                int result = 0;
//                result = importInfoMapper.insert(i);
//                if(result == 0){
//                    return false;
//                }
                System.out.println(i);
            }
            flag = true;
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        } finally {
            if (inputStream!=null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("流关闭异常!"+e.getMessage());
                }
        }
        return flag;
    }


    /**
     * 58同城简历模板导入
     * @param inputStream
     * @param recruitChannel
     * @param importInfoMapper
     * @param session
     * @return
     */
    public static boolean tongchengImport(InputStream inputStream,Integer recruitChannel,ImportInfoMapper importInfoMapper,HttpSession session){
        boolean flag = false;
        EmployeeInfo em = (EmployeeInfo) session.getAttribute("employeeInfo");
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<TongchengCustomerVo> list = null;
        try {
            list = ExcelImportUtil.importExcel(inputStream, TongchengCustomerVo.class, params);
            List<ImportInfo> list2 = new ArrayList<>();
            for(TongchengCustomerVo tc : list){
                if(tc.getTel() != null) {
                    tc.setRecruitChannel(recruitChannel);
                    tc.setCompanyId(em.getCompanyId());
                    tc.setIntroducer(em.getUserId().toString());
                    ImportInfo i = tc.changeToCustomerInfo(tc);
                    list2.add(i);
                }
            }
            for(ImportInfo i : list2){
//                int result = 0;
//                result = importInfoMapper.insert(i);
//                if(result == 0){
//                    return false;
//                }
                System.out.println(i);
            }
            flag = true;
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        } finally {
            if (inputStream!=null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("流关闭异常!"+e.getMessage());
                }
        }
        return flag;
    }

    /**
     * 51Job简历模板导入
     * @param inputStream
     * @param recruitChannel
     * @param importInfoMapper
     * @param session
     * @return
     */
    public static boolean qianchengImport(InputStream inputStream,Integer recruitChannel,ImportInfoMapper importInfoMapper,HttpSession session){
        boolean flag = false;
        EmployeeInfo em = (EmployeeInfo) session.getAttribute("employeeInfo");
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<QianchengCustomerVo> list = null;
        try {
            list = ExcelImportUtil.importExcel(inputStream, QianchengCustomerVo.class, params);
            List<ImportInfo> list2 = new ArrayList<>();
            for(QianchengCustomerVo qc : list){
                if(qc.getTel() != null) {
                    qc.setRecruitChannel(recruitChannel);
                    qc.setIntroducer(em.getUserId().toString());
                    qc.setCompanyId(em.getCompanyId());
                    ImportInfo i = qc.changeToCustomerInfo(qc);
                    list2.add(i);
                }
            }
            for(ImportInfo i : list2){
//                int result = 0;
//                result = importInfoMapper.insert(i);
//                if(result == 0){
//                    return false;
//                }
                System.out.println(i);
            }
            flag = true;
        }  catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }finally {
            if (inputStream!=null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("流关闭异常!"+e.getMessage());
                }
        }
        return flag;
    }

    /**
     * 赶集网简历模板导入
     * @param inputStream
     * @param recruitChannel
     * @param importInfoMapper
     * @param session
     * @return
     */
    public static boolean ganjiImport(InputStream inputStream,Integer recruitChannel,ImportInfoMapper importInfoMapper,HttpSession session){
        boolean flag = false;
        EmployeeInfo em = (EmployeeInfo) session.getAttribute("employeeInfo");
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<GanjiCustomerVo> list = null;
        try {
            list = ExcelImportUtil.importExcel(inputStream, GanjiCustomerVo.class, params);
            List<ImportInfo> list2 = new ArrayList<>();
            for(GanjiCustomerVo gj : list){
                if(gj.getTel() != null) {
                    gj.setRecruitChannel(recruitChannel);
                    gj.setCompanyId(em.getCompanyId());
                    gj.setIntroducer(em.getUserId().toString());
                    ImportInfo i = gj.changeToCustomerInfo(gj);
                    list2.add(i);
                }
            }
            for(ImportInfo i : list2){
//                int result = 0;
//                result = customerInfoMapper.insert(c);
//                if(result == 0){
//                    return false;
//                }
                System.out.println(i);
            }
            flag = true;
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }finally {
            if (inputStream!=null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("流关闭异常!"+e.getMessage());
                }
        }
        return flag;
    }


    /**
     * 人才热线简历模板导入
     *
     * 姓名字段导入时有bug
     * 人才热线网址导出的模板中姓名字段会存在下划线，
     * 会导致数据无法导入
     *
     * @param inputStream
     * @param recruitChannel
     * @param importInfoMapper
     * @param session
     * @return
     */

    public static boolean rencaiImport(InputStream inputStream,Integer recruitChannel,ImportInfoMapper importInfoMapper,HttpSession session){
        boolean flag = false;
        EmployeeInfo em = (EmployeeInfo) session.getAttribute("employeeInfo");
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<RencaiCustomerVo> list = null;
        try {
            list = ExcelImportUtil.importExcel(inputStream, RencaiCustomerVo.class, params);
            List<ImportInfo> list2 = new ArrayList<>();
            for(RencaiCustomerVo rc : list){
                if(rc.getTel() != null) {
                    rc.setRecruitChannel(recruitChannel);
                    rc.setIntroducer(em.getUserId().toString());
                    rc.setCompanyId(em.getCompanyId());
                    ImportInfo c = rc.changeToCustomerInfo(rc);
                    list2.add(c);
                }
            }
            for(ImportInfo i : list2){
//                int result = 0;
//                result = customerInfoMapper.insert(c);
//                if(result == 0){
//                    return false;
//                }
                System.out.println(i);
            }
            flag = true;
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }finally {
            if (inputStream!=null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("流关闭异常!"+e.getMessage());
                }
        }
        return flag;
    }

    /**
     * 中华英才简历模板导入
     * @param inputStream
     * @param recruitChannel
     * @param importInfoMapper
     * @param session
     * @return
     */

    public static boolean zhonghuaImport(InputStream inputStream,Integer recruitChannel,ImportInfoMapper importInfoMapper,HttpSession session){
        boolean flag = false;
        EmployeeInfo em = (EmployeeInfo) session.getAttribute("employeeInfo");
        ImportParams params = new ImportParams();
        params.setHeadRows(2);
        List<ZhonghuaCustomerVo> list = null;
        try {
            list = ExcelImportUtil.importExcel(inputStream, ZhonghuaCustomerVo.class, params);
            List<ImportInfo> list2 = new ArrayList<>();
            for(ZhonghuaCustomerVo zh : list){
                if(zh.getTel() != null) {
                    zh.setRecruitChannel(recruitChannel);
                    zh.setIntroducer(em.getUserId().toString());
                    zh.setCompanyId(em.getCompanyId());
                    ImportInfo i = zh.changeToCustomerInfo(zh);
                    list2.add(i);
                }
            }
            for(ImportInfo i : list2){
//                int result = 0;
//                result = importInfoMapper.insert(i);
//                if(result == 0){
//                    return false;
//                }
                System.out.println(i);
            }
            flag = true;
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }finally {
            if (inputStream!=null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("流关闭异常!"+e.getMessage());
                }
        }
        return flag;
    }
}

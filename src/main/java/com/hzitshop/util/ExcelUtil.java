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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 冼耀基 on 2016/9/24.
 */
public class ExcelUtil {

    @Autowired
    private static ImportInfoService importInfoServiceImpl ;

    @Autowired
    private static ImportInfoMapper importInfoMapper;

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

    public static boolean zhilianImport(InputStream inputStream,Integer recruitChannel,Integer cvType,ImportInfoMapper importInfoMapper,HttpSession session){
        boolean flag = false;
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
                        z.setRecruitChannel(recruitChannel);
                        z.setCompanyId(em.getCompanyId());
                        ImportInfo i = z.changeToCustomerInfo(z);
                        i.setCvType(cvType);
                        list2.add(i);
                    }
                }
            }
            //插入去重过滤
            if(insertFilter(list2,importInfoMapper)){
                flag = true;
            }else{
                flag = false;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            flag = false;
            return flag;
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
    public static boolean tongchengImport(InputStream inputStream,Integer recruitChannel,Integer cvType,ImportInfoMapper importInfoMapper,HttpSession session){
        boolean flag = false;
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
                        tc.setRecruitChannel(recruitChannel);
                        tc.setCompanyId(em.getCompanyId());
                        ImportInfo i = tc.changeToCustomerInfo(tc);
                        i.setCvType(cvType);
                        list2.add(i);
                    }
                }
            }
            //插入去重过滤
            if(insertFilter(list2,importInfoMapper)){
                flag = true;
            }else{
                flag = false;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            flag = false;
            return flag;
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
    public static boolean qianchengImport(InputStream inputStream,Integer recruitChannel,Integer cvType,ImportInfoMapper importInfoMapper,HttpSession session){
        boolean flag = false;
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
                        qc.setRecruitChannel(recruitChannel);
                        qc.setCompanyId(em.getCompanyId());
                        ImportInfo i = qc.changeToCustomerInfo(qc);
                        i.setCvType(cvType);
                        list2.add(i);
                    }
                }
            }
            //插入去重过滤
            if(insertFilter(list2,importInfoMapper)){
                flag = true;
            }else{
                flag = false;
            }
        }  catch (Exception e) {
            logger.error(e.getMessage());
            flag = false;
            return flag;
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
    public static boolean ganjiImport(InputStream inputStream,Integer recruitChannel,Integer cvType,ImportInfoMapper importInfoMapper,HttpSession session){
        boolean flag = false;
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
                        gj.setRecruitChannel(recruitChannel);
                        gj.setCompanyId(em.getCompanyId());
                        ImportInfo i = gj.changeToCustomerInfo(gj);
                        i.setCvType(cvType);
                        list2.add(i);
                    }
                }
            }
            //插入去重过滤
            if(insertFilter(list2,importInfoMapper)){
                flag = true;
            }else{
                flag = false;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            flag = false;
            return flag;
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

    public static boolean rencaiImport(InputStream inputStream,Integer recruitChannel,Integer cvType,ImportInfoMapper importInfoMapper,HttpSession session){
        boolean flag = false;
        EmployeeInfo em = (EmployeeInfo) session.getAttribute("employeeInfo");
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        params.setStartSheetIndex(0);
        params.setSheetNum(1);
        List<RencaiCustomerVo> list = null;
        try {
            list = ExcelImportUtil.importExcel(inputStream, RencaiCustomerVo.class, params);
            List<ImportInfo> list2 = new ArrayList<>();
            if(list.size() != 0 && list != null) {
                for (RencaiCustomerVo rc : list) {
                    if (rc.getTel() != null) {
                        rc.setRecruitChannel(recruitChannel);
                        rc.setCompanyId(em.getCompanyId());
                        ImportInfo i = rc.changeToCustomerInfo(rc);
                        i.setCvType(cvType);
                        list2.add(i);
                    }
                }
            }
            //插入去重过滤
            if(insertFilter(list2,importInfoMapper)){
                flag = true;
            }else{
                flag = false;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            flag = false;
            return flag;
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

    public static boolean zhonghuaImport(InputStream inputStream,Integer recruitChannel,Integer cvType,ImportInfoMapper importInfoMapper,HttpSession session){
        boolean flag = false;
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
                        zh.setRecruitChannel(recruitChannel);
                        zh.setCompanyId(em.getCompanyId());
                        ImportInfo i = zh.changeToCustomerInfo(zh);
                        i.setCvType(cvType);
                        list2.add(i);
                    }
                }
            }
            //插入去重过滤
            if(insertFilter(list2,importInfoMapper)){
                flag = true;
            }else{
                flag = false;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            flag = false;
            return flag;
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
     * 百姓网简历模板导入
     * @param inputStream
     * @param recruitChannel
     * @param importInfoMapper
     * @param session
     * @return
     */

    public static boolean baixingImport(InputStream inputStream,Integer recruitChannel,Integer cvType,ImportInfoMapper importInfoMapper,HttpSession session){
        boolean flag = false;
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
                        bx.setRecruitChannel(recruitChannel);
                        bx.setCompanyId(em.getCompanyId());
                        ImportInfo i = bx.changeToCustomerInfo(bx);
                        i.setCvType(cvType);
                        list2.add(i);
                    }
                }
            }
            //插入去重过滤
            if(insertFilter(list2,importInfoMapper)){
                flag = true;
            }else{
                flag = false;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            flag = false;
            return flag;
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
     * 传入ImportInfo List,判断该学员是否存在,然后去重
     * @param list
     * @param importInfoMapper
     * @return
     */
    private static boolean insertFilter(List<ImportInfo> list,ImportInfoMapper importInfoMapper){
        for(ImportInfo i : list){
            if(!"".equals(i.getTel()) && i.getTel() != null){
                int result = 0;
                ImportInfo im = new ImportInfo();
                im.setCompanyId(i.getCompanyId());
                im.setTel(i.getTel());
                //如果存在CompanyId、Tel相同的记录，则覆盖，否则则新增一条记录
                if((im = importInfoMapper.selectOne(im)) != null){
                    result = importInfoMapper.update(i, new EntityWrapper<ImportInfo>()
                            .where("customer_id=" + im.getCustomerId()));
                }else {
                    result = importInfoMapper.insert(i);
                }
                if(result == 0){
                    return false;
                }
            }
        }
        return true;
    }

}

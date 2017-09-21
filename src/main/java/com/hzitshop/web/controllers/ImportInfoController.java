package com.hzitshop.web.controllers;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.entity.ImportInfo;
import com.hzitshop.entity.TbDict;
import com.hzitshop.service.ImportInfoService;
import com.hzitshop.service.ITbDictService;
import com.hzitshop.vo.BootstrapEntity;
import com.hzitshop.vo.BootstrapTable;
import com.hzitshop.vo.ImportInfoVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-09-08 16:33
 * @description
 */
@Controller
public class ImportInfoController {

    @Autowired
    private ImportInfoService importInfoServiceImpl;

    @Autowired
    private ITbDictService iTbDictService;

    private Logger logger = LoggerFactory.getLogger(ImportInfoController.class);


    /**
     * 导入Excel
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> importExcel(@RequestParam("excelFile") MultipartFile file,Integer recruitChannel,Integer cvType,HttpServletRequest request,HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            String path = request.getSession().getServletContext().getRealPath("/");
            File f = new File(path+"/excel/"+file.getOriginalFilename());
            if(!f.exists()){
                try {
                    File dir = new File(path+"/excel/");
                    dir.mkdirs();
                    if(!f.createNewFile()){
                        resultMap.put("code",300);
                        resultMap.put("msg","文件导入失败!");
                        return resultMap;
                    }
                } catch (IOException e) {
                    logger.error("io异常");
                    resultMap.put("code",300);
                    resultMap.put("msg","文件导入失败!");
                }
            }
            file.transferTo(f);
            InputStream is = new FileInputStream(f);
            boolean result = importInfoServiceImpl.importExcel(is,recruitChannel,cvType,session);
            if(result){
                resultMap.put("code",200);
                resultMap.put("msg","文件导入成功!");
                return resultMap;
            }
            resultMap.put("code", 300);
            resultMap.put("msg", "文件导入失败!");
        } catch (FileNotFoundException e) {
            logger.error("io异常!"+e.getMessage());
            resultMap.put("code", 300);
            resultMap.put("msg", "文件导入失败!");
        } catch (IOException e) {
            logger.error("io异常!"+e.getMessage());
            resultMap.put("code", 300);
            resultMap.put("msg", "文件导入失败!");
        }
        return resultMap;
    }



    @RequestMapping("/import/importList")
    public String toImportList(){
        return "/import/importList";
    }

    @RequestMapping("/import/importPage")
    public String toImportPage(){
        return "/import/importPage";
    }

    @RequestMapping("/import/recycleBin")
    public String toRecycleBin(){ return "/import/recycleBin"; }

    @RequestMapping("/import/editMemo")
    public String toEditMemo(ModelMap modelMap, ImportInfo importInfo){
        importInfo = importInfoServiceImpl.selectOne(new EntityWrapper<ImportInfo>().where("customer_id=" + importInfo.getCustomerId()));
        modelMap.addAttribute("importInfo", importInfo);
        return "/import/editMemo";
    }

    /**
     * 保存修改的备注信息
     * @return
     */
    @RequestMapping("/importInfo/editMemo")
    @ResponseBody
    public Map<String,Object> editMemo(ImportInfo importInfo){
        Map<String, Object> resultMap = new HashMap<>();
        ImportInfo im = new ImportInfo();
        im = importInfoServiceImpl.selectOne(new EntityWrapper<ImportInfo>().where("customer_id=" + importInfo.getCustomerId()));
        im.setMemo(importInfo.getMemo());
        im.setLastTime(new Date());
        boolean result = importInfoServiceImpl.updateById(im);
        if(result){
            resultMap.put("code",200);
            resultMap.put("msg","备注修改成功!");
        }else{
            resultMap.put("code",300);
            resultMap.put("msg","备注修改失败!");
        }
        return resultMap;
    }

    /**
     * 查询已逻辑删除的数据（公共库）
     * @param bt
     * @param session
     * @return
     */
    @RequestMapping(value = "/importInfo/recycleData")
    @ResponseBody
    public BootstrapTable<ImportInfoVo> RecycleData(BootstrapEntity bt,HttpSession session){
        if (bt.getOffset() == null || bt.getLimit() == null) {
            bt.setOffset(1);
            bt.setLimit(20);
        } else {
            bt.setOffset(bt.getOffset() / bt.getLimit());
        }
        Page<ImportInfo> searchPage = new Page<ImportInfo>(bt.getOffset(), bt.getLimit());

        Wrapper<ImportInfo> ew = new EntityWrapper<ImportInfo>()
                .where("isDelete=1")
                .like(bt.getCondition(), bt.getValue())
                .orderBy("last_time desc");
        if("-1".equals(bt.getCondition()) ){
            bt.setCondition("");
        }
        BootstrapTable<ImportInfoVo> bootstrapTable = importInfoServiceImpl.ajaxData(searchPage,ew);
        return bootstrapTable;
    }

    /**
     * 逻辑删除
     * @param importIdArr
     * @return
     */
    @RequestMapping(value = "/importInfo/remove")
    @ResponseBody
    public Map<String,Object> remove(String importIdArr){
        String condition = "isDelete";
        return getStringObjectMap(importIdArr,condition);
    }

    /**
     * 恢复逻辑删除的数据
     * @param importIdArr
     * @return
     */
    @RequestMapping(value = "/importInfo/recover")
    @ResponseBody
    public Map<String,Object> recover(String importIdArr){
        String condition = "isDelete=0";
        return getStringObjectMap(importIdArr,condition);
    }


    /**
     *彻底删除
     * @param importIdArr
     * @return
     */
    @RequiresPermissions(value = {"importInfo:delete"})
    @RequestMapping(value = "/importInfo/delete")
    @ResponseBody
    public Map<String,Object> delete(String importIdArr){
        Map<String, Object> resultMap = new HashMap<>();
        String[] idArr = null;
        List<String> listIds = new ArrayList<>();
        if(importIdArr!=null && !"".equals(importIdArr)){
            idArr = importIdArr.split(",");
        }
        if(idArr != null){
            for(String id : idArr){
                listIds.add(id);
            }
            boolean result = importInfoServiceImpl.deleteBatchIds(listIds);
            if(result){
                resultMap.put("code",200);
                resultMap.put("msg","删除成功!");
            }else{
                resultMap.put("code",300);
                resultMap.put("msg","删除失败!");
            }
        }
        return resultMap;
    }

    /**
     * 导入列表分页查询
     * @param bt
     * @param session
     * @return
     */
    @RequestMapping(value = "/importInfo/listData")
    @ResponseBody
    public BootstrapTable<ImportInfoVo> listData(BootstrapEntity bt,HttpSession session){
        if (bt.getOffset() == null || bt.getLimit() == null) {
            bt.setOffset(1);
            bt.setLimit(20);
        } else {
            bt.setOffset(bt.getOffset() / bt.getLimit());
        }
        Page<ImportInfo> searchPage = new Page<ImportInfo>(bt.getOffset(), bt.getLimit());
        EmployeeInfo em = (EmployeeInfo) session.getAttribute("employeeInfo");
        //根据当前登录用户所在校区筛选导入列表
        Wrapper<ImportInfo> ew = new EntityWrapper<ImportInfo>()
                .where("company_id="+em.getCompanyId())
                .and("isDelete=0")
                .like(bt.getCondition(), bt.getValue())
                .orderBy("create_time desc");
        if("-1".equals(bt.getCondition()) ){
            bt.setCondition("");
        }
        BootstrapTable<ImportInfoVo> bootstrapTable = importInfoServiceImpl.ajaxData(searchPage,ew);
        return bootstrapTable;

    }

    @RequestMapping("/import/checkImportInfo")
    @ResponseBody
    public Map<String,Object> checkImportInfo(ImportInfo importInfo){
        ImportInfo i = null;
        Map<String,Object> resultMap = new HashMap<>();
        i = importInfoServiceImpl.selectOne(new EntityWrapper<ImportInfo>()
                .where("tel="+importInfo.getTel())
                .and("company_id="+importInfo.getCompanyId()));
        if(i == null){
            resultMap.put("code",300);
            resultMap.put("msg","系统暂未导入该用户简历,请手动录入!");
        }else {
            i.setCustomerId(null);
            i.setCreateTime(null);
            resultMap.put("code",200);
            resultMap.put("msg","简历已导入,请核对用户姓名后再录入!");
            resultMap.put("importInfo",i);
        }
        return resultMap;
    }


    /**
     * 根据id批量操作导入表importInfo
     * @param importIdArr
     * @param condition
     * @return
     */
    private Map<String, Object> getStringObjectMap(String importIdArr, String condition) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            ImportInfo importInfo = null;
            if (StringUtils.isNotEmpty(importIdArr)) {
                String[] idArr = importIdArr.split(",");
                for (String id : idArr) {
                    importInfo = new ImportInfo();
                    if ("isDelete".equals(condition)) {
                        importInfo.setIsDelete(1);//逻辑删除数据
                    }else if("isDelete=0".equals(condition)){
                        importInfo.setIsDelete(0);//恢复逻辑删除的数据
                    } else if ("customer_sate".equals(condition)) {
                        importInfo.setCustomerState(29); //修改面试状态为已面试
                    }
                    importInfo.setCustomerId(Integer.parseInt(id));
                    importInfoServiceImpl.update(importInfo,
                            new EntityWrapper<ImportInfo>().where("customer_id=" + importInfo.getCustomerId()));
                }
            }
            resultMap.put("code", 200);
            resultMap.put("msg", "操作成功!");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code", 300);
            resultMap.put("msg", "操作失败!");
        }
        return resultMap;
    }


}

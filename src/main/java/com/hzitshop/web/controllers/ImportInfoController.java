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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

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
    public Map<String,Object> importExcel(@RequestParam("excelFile") MultipartFile file,Integer recruitChannel,HttpServletRequest request,HttpSession session){
        Map<String,Object> resultMap = new HashMap<>();
        try {
//            licenseName字段为分校的校区名
//            由于登入功能暂时没实现，获取不到session中的数据，暂时固定写死
//            String licenseName = "宝安";
            
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
            boolean result = importInfoServiceImpl.importExcel(is,recruitChannel,session);
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
                .like(bt.getCondition(),bt.getValue())
                .orderBy("create_time desc");
        if("-1".equals(bt.getCondition()) ){
            bt.setCondition("");
        }

        BootstrapTable<ImportInfoVo> bootstrapTable = importInfoServiceImpl.ajaxData(searchPage,ew);
        return bootstrapTable;

    }

}

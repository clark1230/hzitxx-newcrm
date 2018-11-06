package com.hzitshop.web.controllers;

import com.hzitshop.service.ICustomerInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ExportController {
    @Autowired
    private ICustomerInfoService customerInfoService;

    /**
     * 跳转到报表导出页面
     * @return
     */
    @RequiresPermissions("foreground:exportExcel")
    @GetMapping("foreground/exportExcel")
    public String exportExcel(){
        return  "foreground/exportExcel";
    }

    /**
     * 前台导出数据
     * @param resp
     */
    @RequiresPermissions("foreground:exportExcelFile")
    @GetMapping("/foreground/exportExcelFile")
    public void exportExcel(HttpServletResponse resp, @RequestParam("date")String date){
        OutputStream outputStream = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = date+"--应聘数据.xlsx";
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("date",date);
        try{
            resp.setHeader("Content-disposition", "attachment; filename="+
                    new String(fileName.getBytes("utf-8"),"ISO-8859-1"));// 设定输出文件头
            resp.setContentType("application/msexcel");// 定义输出类型
            outputStream = resp.getOutputStream();
            Workbook workbook = this.customerInfoService.foregroundExportData(paramsMap);
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("前台应聘数量导出失败!"+e.getMessage());
        }
    }

    /**
     * 获取前台分页数据
     * @return
     */
    @GetMapping("/foreground/ajaxData")
    @ResponseBody
    public Object ajaxData(@RequestParam("page") int page,@RequestParam("limit") int limit,String date){
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("offset",(page-1)*limit);
        paramsMap.put("limit",limit);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(StringUtils.isNotEmpty(date)){
            paramsMap.put("date",date);
        }else{
            paramsMap.put("date",sdf.format(new Date()));
        }
        System.out.println(paramsMap.get("date"));
        return this.customerInfoService.foregroundPage(paramsMap);
    }
}

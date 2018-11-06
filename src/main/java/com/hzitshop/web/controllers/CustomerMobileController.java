package com.hzitshop.web.controllers;

import com.hzitshop.entity.CustomerTraceRecord;
import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.service.ICustomerTraceRecordService;
import com.hzitshop.vo.Email;
import com.hzitshop.vo.trace.TraceRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CustomerMobileController {
    @Autowired
    private ICustomerTraceRecordService traceRecordService;
    /**
     * 手机端获取客户信息
     * @param customerId
     * @return
     */
    @GetMapping("/mobile/customerInfo")
    public String customerInfo(@RequestParam("customerId")Integer customerId, Model model){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("customerId",customerId);
        List<TraceRecord> traceRecords = traceRecordService.mobileTraceMsg(paramMap);
        model.addAttribute("traceRecords",traceRecords);
        model.addAttribute("customerId",customerId);
        return  "mobile/customerInfo";
    }

    /**
     * 保存跟进信息
     * @return
     */
    @PostMapping("/mobile/addTraceMsg")
    @ResponseBody
    public Object addTraceMsg(CustomerTraceRecord customerTraceRecord, HttpSession httpSession){
        EmployeeInfo employeeInfo = (EmployeeInfo)httpSession.getAttribute("employeeInfo");
        customerTraceRecord.setUserId(employeeInfo.getUserId());
        customerTraceRecord.setRecordDate(new Date());
        return traceRecordService.addMobileTraceMsg(customerTraceRecord);

    }
}

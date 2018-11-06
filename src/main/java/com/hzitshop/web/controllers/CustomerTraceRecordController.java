package com.hzitshop.web.controllers;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hzitshop.entity.CustomerTraceRecord;
import com.hzitshop.service.ICustomerTraceRecordService;
import javafx.beans.binding.ObjectExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import  java.util.Map;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-12
 */
@Controller
public class CustomerTraceRecordController {
	@Autowired
    private ICustomerTraceRecordService iCustomerTraceRecordService;

    /**
     * 获取最新跟进记录!!
     * @return
     */
	@RequestMapping("/customerTraceRecord/traceRecord")
    @ResponseBody
    public List<CustomerTraceRecord> traceRecord(CustomerTraceRecord customerTraceRecord){
	    List<CustomerTraceRecord> list=  iCustomerTraceRecordService.selectList(
	            new EntityWrapper<CustomerTraceRecord>().
                        where("user_id="+customerTraceRecord.getUserId()+" and customer_id="+customerTraceRecord.getCustomerId() ).
                        orderBy("record_id  desc"));
	    return list;
    }

    /**
     * 异步获取跟进记录
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/customerTraceRecord/ajaxData")
    @ResponseBody
    public Object ajaxData(@RequestParam("page") int page,
                           @RequestParam("limit") int limit,
                           @RequestParam(value = "customerId")int customerId){
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("offset",(page-1)*limit);
        paramsMap.put("limit",limit);
        paramsMap.put("customerId",customerId);
        return this.iCustomerTraceRecordService.traceRecordData(paramsMap);
    }
}

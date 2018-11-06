package com.hzitshop.task;

import com.hzitshop.email.EmailUtil;
import com.hzitshop.service.ICustomerInfoService;
import com.hzitshop.vo.customerinfovo.NotFollowUpVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 客户跟进定时任务
 */
@Component
public class RecordTask {

    @Autowired
    private ICustomerInfoService customerInfoService;

    @Value("${futian}")
    private String futianEmail;

    @Value("${baoan}")
    private String baoanEmail;
    /**
     * 每天早上8点钟执行一次
     */
    @Scheduled(cron = "0 0 8 * * *")
    public void task(){
        Map<String,Object> paramsMap = new HashMap<>();
        int[] level = {17,18};
        int[] companyIds = {36,70};
        for(int l : level){
            paramsMap.put("level",l);
            if(l == 17){
                paramsMap.put("countTime",3);
            }else{
                paramsMap.put("countTime",5);
            }
            for(int company: companyIds){
                paramsMap.put("companyId",company);
                List<NotFollowUpVo> notFollowUpVos = this.customerInfoService.selectByRecordTime(paramsMap);
                if(notFollowUpVos!= null && notFollowUpVos.size() >0){
                    StringBuilder sb = new StringBuilder();
                    for(NotFollowUpVo notFollowUpVo: notFollowUpVos){
                        sb.append(notFollowUpVo.getName()+"----"+notFollowUpVo.getRealName()+",");
                    }
                    //发送邮件
                    if(company == 36){
                        String[] emails = baoanEmail.split(",");
                        sendEmail(sb, emails,l);
                    }else if(company ==70){
                        String[] emails = futianEmail.split(",");
                        sendEmail(sb, emails,l);
                    }
                }
            }
        }
    }

    /**
     * 发送邮件
     * @param sb
     * @param emails
     */
    private void sendEmail(StringBuilder sb, String[] emails,int level) {
        for(String email: emails){
            try {
                EmailUtil.sendEmail("","",email,
                        (level == 17 ? "A级别":"B级别")+"未跟进信息:"+sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Scheduled(cron = "0 0 1 * * *")
    public void task2(){
        // C 半个月转
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("level",17);
        paramsMap.put("countTime",90);
        this.customerInfoService.updateByLevelAndCountTime(paramsMap);
        // B  一个月转
        paramsMap.put("level",18);
        paramsMap.put("countTime",30);
        this.customerInfoService.updateByLevelAndCountTime(paramsMap);
        // A 3个月转
        paramsMap.put("level",19);
        paramsMap.put("countTime",15);
        this.customerInfoService.updateByLevelAndCountTime(paramsMap);

    }
}

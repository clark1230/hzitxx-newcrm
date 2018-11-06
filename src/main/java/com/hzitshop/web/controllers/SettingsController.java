package com.hzitshop.web.controllers;


import com.hzitshop.entity.Settings;
import com.hzitshop.service.ITbSettingsService;
import com.hzitshop.util.ServerResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.hzitshop.service.ITbSettingsService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xianyaoji
 * @since 2018-02-26
 */
@Controller
@RequestMapping("/settings")
public class SettingsController {
    @Autowired
    private ITbSettingsService tbSettingsService;

    /**
     * 跳转到修改页面
     * @return
     */
    @RequiresPermissions("settings:editSettings")
    @GetMapping("editSettings")
    public String editSettings(Integer id,Model model){
         id = 1;
         model.addAttribute("tbSettings",tbSettingsService.findOne(id));
         return "/import/editSettings";
    }
                

    /**
     * 处理修改数据表单提交
     * @return
     */
    @PostMapping("editSettings")
    @ResponseBody
    public Object editSettings(Settings tbSettings,Model model){
        String view;
        int result = tbSettingsService.updateSelectiveById(tbSettings);
        if(result ==1){
            return ServerResponse.createBySuccessMessage("保存成功!");
        }
        return ServerResponse.createByErrorCodeMessage(1,"保存失败");
    }


}

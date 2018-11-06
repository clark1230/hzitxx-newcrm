package com.hzitshop.service.impl;

import com.hzitshop.entity.CustomerInfo;
import com.hzitshop.entity.ImportInfo;
import com.hzitshop.mapper.CustomerInfoMapper;
import com.hzitshop.mapper.ImportInfoMapper;
import com.hzitshop.service.IBusinessService;
import com.hzitshop.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BusinessServiceImpl implements IBusinessService {
    @Autowired(required = false)
    private ImportInfoMapper importInfoMapper;

    @Autowired(required = false)
    private CustomerInfoMapper customerInfoMapper;

    @Transactional
    @Override
    public ServerResponse recover(Integer[] customerIdArr) {
        if(customerIdArr != null && customerIdArr.length >0){
            try{
                for(Integer id: customerIdArr){
                    CustomerInfo ci = this.customerInfoMapper.selectById(id);
                    if(ci.getUserId()!= null && !Integer.valueOf(0).equals(ci.getUserId()) ){  //说明是咨询的数据
                        //修改数据
                        ci.setCustomerType(0);
                        ci.setYunying(0);
                        customerInfoMapper.updateSelectiveById(ci);
                    }else{//创量的数据
                        ci.setIsDelete(1);
                        customerInfoMapper.updateSelectiveById(ci);
                        //修改创量的数据
                        // 查询条件 逻辑删除了,电话号码,邀约人id
                        Map<String,Object>  columnMap = new HashMap<>();
                        columnMap.put("real_name",ci.getRealName());
                        columnMap.put("introducer",ci.getIntroducer());
                        columnMap.put("isDelete","3");
                        List<ImportInfo> importInfoList = this.importInfoMapper.selectByMap(columnMap);
                        for(ImportInfo importInfo : importInfoList){
                            importInfo.setIsDelete(0);
                            int result= this.importInfoMapper.updateSelectiveById(importInfo);
                            System.out.println(result);
                        }
                    }
                }
                return  ServerResponse.createBySuccessMessage("还原成功!");
            }catch (Exception e){
                e.printStackTrace();
                return ServerResponse.createByErrorMessage("还原失败!");
            }
        }else{
            return  ServerResponse.createByErrorMessage("还原失败!");
        }
    }
}

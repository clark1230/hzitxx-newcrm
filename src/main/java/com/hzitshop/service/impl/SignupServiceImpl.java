package com.hzitshop.service.impl;

import com.hzitshop.entity.CustomerInfo;
import com.hzitshop.entity.Signup;
import com.hzitshop.mapper.CustomerInfoMapper;
import com.hzitshop.mapper.SignupMapper;
import com.hzitshop.service.ISignupService;
import com.hzitshop.util.LayuiEntity;
import com.hzitshop.util.ServerResponse;
import com.hzitshop.vo.signup.SignupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xianyaoji
 * @since 2018-03-12
 */
@Service
public class SignupServiceImpl implements ISignupService {

    @Autowired
    private SignupMapper mapper;

    @Autowired
    private CustomerInfoMapper customerInfoMapper;

    @Override
    public int addSignup(Signup signup){
        return mapper.addSignup(signup);
    }

    @Transactional
    @Override
    public int addSignupSelective(Signup signup){
        //修改学员的状态信息
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setCustomerId(signup.getCustomerId());
        customerInfo.setCustomerState(32);
        //添加之前先检查是否存在该人
        Signup signupResult = mapper.findOneByCustomerId(signup.getCustomerId());
        if(signupResult != null ){
            return 3;
        }
        customerInfoMapper.updateSelectiveById(customerInfo);
        return mapper.addSignupSelective(signup);
    }

    @Override
    public int deleteById(int id){
            return mapper.deleteById(id);
    }

    @Override
    public int deleteByIds(String[]ids){
        return mapper.deleteByIds(ids);
    }
    @Override
    public ServerResponse updateById(Signup signup){
        int result = this.mapper.updateById(signup);
        if(result !=1){
            return ServerResponse.createByErrorMessage("修改失败!");
        }
        return ServerResponse.createBySuccessMessage("修改成功!");
    }

    @Override
    public  ServerResponse updateSelectiveById(Signup signup){
        int result = this.mapper.updateSelectiveById(signup);
        if(result != 1){
            return ServerResponse.createByErrorMessage("修改失败!");
        }
        return ServerResponse.createBySuccessMessage("修改成功!");
    }

    @Override
    public  List<Signup> searchSignup(Map<String, Object> map){
        return mapper.searchSignup(map);
    }

    /**
     * 数据查询分页
     * @param page
     * @param limit
     * @param map
     * @return
     */
    @Override
    public LayuiEntity<SignupVo> page(int page, int limit, Map<String, Object> map){
       /* PageHelper.startPage(page,limit);
        List<Signup>  obj=mapper.searchSignup(map);
        PageInfo<Signup> pageInfo=new PageInfo<>(obj);
        LayuiEntity<Signup> layuiEntity=new LayuiEntity<>();
        layuiEntity.setCode(0);
        layuiEntity.setMsg("数据");
        layuiEntity.setCount(pageInfo.getTotal());
        layuiEntity.setData(pageInfo.getList());*/
       LayuiEntity<SignupVo> layuiEntity = new LayuiEntity<>();
       layuiEntity.setCode(0);
       layuiEntity.setMsg("报名数据!");
       layuiEntity.setCount(this.mapper.selectSignupCount(map));
       layuiEntity.setData(this.mapper.selectSignup(map));
        return layuiEntity;
    }

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    @Override
    public Signup findOne(Integer id){
            return mapper.findOne(id);
     }
}


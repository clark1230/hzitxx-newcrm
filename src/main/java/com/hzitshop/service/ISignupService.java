package com.hzitshop.service;

import com.hzitshop.entity.Signup;
import com.hzitshop.util.LayuiEntity;
import com.hzitshop.util.ServerResponse;
import com.hzitshop.vo.signup.SignupVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xianyaoji
 * @since 2018-03-12
 */

public interface ISignupService  {

    int addSignup(Signup signup);

    int addSignupSelective(Signup obj);

    int deleteById(int id);

    int deleteByIds(String[] ids);

    ServerResponse updateById(Signup signup);

    ServerResponse updateSelectiveById(Signup signup);

    List<Signup> searchSignup(Map<String, Object> map);

    /**
     * 数据查询分页
     * @param page
     * @param limit
     * @param map
     * @return
     */
    LayuiEntity<SignupVo> page(int page, int limit, Map<String, Object> map);

    /**
     * 根据编号查询数据
     *
     */
    Signup findOne(Integer id);

}

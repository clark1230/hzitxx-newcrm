package com.hzitshop.mapper;

import com.hzitshop.entity.Signup;

import com.hzitshop.vo.signup.SignupVo;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xianyaoji
 * @since 2018-03-12
 */
public interface SignupMapper{

    int addSignup(Signup obj);

    int addSignupSelective(Signup obj);

    int deleteById(int id);
                    
    int deleteByIds(String[] ids);

    int updateById(Signup signup);

    int updateSelectiveById(Signup signup);

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    Signup findOne(Integer id);

    /**
     * 根据customerId查询数据
     * @param customerId
     * @return
     */
    Signup findOneByCustomerId(Integer customerId);
                    
    List<Signup> searchSignup(Map<String, Object> map);

    List<SignupVo> selectSignup(Map<String,Object> paramsMap);

    int selectSignupCount(Map<String,Object> paramsMap);
}
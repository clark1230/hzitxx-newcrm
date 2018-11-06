package com.hzitshop.config;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.entity.TbMenuApp;
import com.hzitshop.service.IEmployeeInfoService;
import com.hzitshop.service.ITbMenuAppService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 验证用户登录
 */
@Component("userRealm")
public class UserRealm extends AuthorizingRealm {
	private Logger logger = LoggerFactory.getLogger(UserRealm.class);
	@Autowired
	private IEmployeeInfoService iEmployeeInfoService;
	@Autowired
	private ITbMenuAppService iTbMenuAppService;
	/**
	 * 密码加密方式
     */
	public UserRealm() {
		setName("UserRealm");
		// 采用MD5加密
		setCredentialsMatcher(new HashedCredentialsMatcher("md5"));
	}

	//权限资源角色
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//根据用户查找该用户所能访问的资源
		Set<String> set = new HashSet<>();
		EmployeeInfo employeeInfo = iEmployeeInfoService.selectOne(
				new EntityWrapper<EmployeeInfo>().where("name='"+username+"'").and("isLocked=0"));
		String ids = employeeInfo.getResourceIds();
		String[] idArr = ids.split(",");
		//到tb_menu_app表查询该用户所能访问的permission
		List<TbMenuApp> tbMenuAppList = iTbMenuAppService.selectBatchIds(Arrays.asList(idArr));
		for(TbMenuApp tbMenuApp: tbMenuAppList){
			if(tbMenuApp.getPermission()!=null && !"".equals(tbMenuApp.getPermission())){
				set.add(tbMenuApp.getPermission());
			}
		}
		info.setStringPermissions(set);
		return info;
	}
	
	//登录验证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
		DefaultUsernamePasswordToken upt = (DefaultUsernamePasswordToken) token;
		String userName = upt.getUsername();
		SimpleAuthenticationInfo info = null;
		if("pc".equals(upt.getLoginType())){//pc端登陆判断
			EmployeeInfo user = iEmployeeInfoService.selectOne(
					new EntityWrapper<EmployeeInfo>().where("name='"+userName+"'").and("isLocked=0"));//employeeInfoService.findByAccount(userName);
			if (user == null) {
				user = new EmployeeInfo();
				logger.error("-------------pc未知账号错误!--------------------");
			}
			info = new SimpleAuthenticationInfo(userName, user.getPassword(), getName());
		}else if("mobile".equals(upt.getLoginType())){//移动端登陆判断
			userName = upt.getUsername();
			String emplId = new String(upt.getPassword());
			Map<String,Object> map = new HashMap<>();
			map.put("dingding_id",emplId);
			List<EmployeeInfo> employeeInfos =  iEmployeeInfoService.selectByMap(map);
			if(employeeInfos.size() == 0){
				throw  new AuthenticationException();
			}
			Object pwd = new SimpleHash("md5",emplId);
			info = new SimpleAuthenticationInfo(userName,pwd,this.getName());
		}
		return info;
	}
}
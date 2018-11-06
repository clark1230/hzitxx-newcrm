package com.hzitshop.config;

import com.google.common.collect.Maps;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
public class ShiroConfig {

	@Bean
	public  DelegatingFilterProxyRegistrationBean delegatingFilterProxyRegistrationBean(){
		DelegatingFilterProxyRegistrationBean
				dfr = new DelegatingFilterProxyRegistrationBean("shiroFilter");
		Set<String> url = new HashSet<>();
		url.add("/*");
		dfr.setUrlPatterns(url);
		dfr.setEnabled(true);
		dfr.setDispatcherTypes(DispatcherType.REQUEST);
		return  dfr;
	}

	
	/**
	 * @see ShiroFilterFactoryBean
	 * 过滤器   哪些资源需要放行的
	 * @return
	 */
	@Bean(name = "shiroFilter")
	public MyShiroFilterFactoryBean shiroFilter(){
		MyShiroFilterFactoryBean bean = new MyShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager());
		bean.setLoginUrl("/account/login");
		bean.setUnauthorizedUrl("/exception/exception");
		
		Map<String, Filter>filters = Maps.newHashMap();
		//filters.put("perms", urlPermissionsFilter());
		filters.put("anon", new AnonymousFilter());
		bean.setFilters(filters);
		
		Map<String, String> chains = Maps.newHashMap();
		chains.put("/account/captcha","anon");//验证码
		chains.put("/account/checkValidate","anon");
		chains.put("/account/login", "anon");
		chains.put("/account/unauthor", "anon");
		chains.put("/account/logout", "logout");
		chains.put("/mobile/getSign", "anon");
		chains.put("/assets*/**", "anon");
		chains.put("/static*/**", "anon");
		chains.put("/css*/**", "anon");
		chains.put("/js*/**", "anon");
		chains.put("/images*/**", "anon");
		chains.put("/**", "authc,perms");
		bean.setFilterChainDefinitionMap(chains);
		return bean;
	}
	
	/**
	 * @see org.apache.shiro.mgt.SecurityManager
	 * @return
	 */
	@Bean(name="securityManager")
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(userRealm());
		manager.setCacheManager(cacheManager());
		manager.setSessionManager(defaultWebSessionManager());
		return manager;
	}

	/**
	 * 注册DelegatingFilterProxy（Shiro）
	 * 集成Shiro有2种方法：
	 * 1. 按这个方法自己组装一个FilterRegistrationBean（这种方法更为灵活，可以自己定义UrlPattern，
	 * 在项目使用中你可能会因为一些很但疼的问题最后采用它， 想使用它你可能需要看官网或者已经很了解Shiro的处理原理了）
	 * 2. 直接使用ShiroFilterFactoryBean（这种方法比较简单，其内部对ShiroFilter做了组装工作，无法自己定义UrlPattern，
	 * 默认拦截 /*）
	 * @return
	 */
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
		filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
		//  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
		filterRegistration.addInitParameter("targetFilterLifecycle", "true");
		filterRegistration.setEnabled(true);
		filterRegistration.addUrlPatterns("/*");// 可以自己灵活的定义很多，避免一些根本不需要被Shiro处理的请求被包含进来
		return filterRegistration;
	}

	/**
	 * @see DefaultWebSessionManager
	 * @return
	 */
	@Bean(name="sessionManager")
	public DefaultWebSessionManager defaultWebSessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setCacheManager(cacheManager());
		sessionManager.setGlobalSessionTimeout(7000*1000);//2小时
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		sessionManager.setDeleteInvalidSessions(true);
		return sessionManager;
	}
	
	/**
	 * @see UserRealm--->AuthorizingRealm
	 * @return
	 */
	@Bean
	@DependsOn(value="lifecycleBeanPostProcessor")
	public UserRealm userRealm() {
		UserRealm userRealm = new UserRealm();
		userRealm.setCacheManager(cacheManager());//缓存管理
		return userRealm;
	}


	/**
	 *  开启 Controller中@RequiresPermissions start
	 * <bean class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator"
	 * depends-on="lifecycleBeanPostProcessor" />
	 * @return
	 */
	@Bean
	@DependsOn(value="lifecycleBeanPostProcessor")
	public   DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
		System.out.println("proxyCreator");
		return new DefaultAdvisorAutoProxyCreator();
	}

	/**
	 * <bean class="org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor">
	 <property name="securityManager" ref="securityManager" />
	 </bean>
	 */
	@Bean
	@DependsOn(value="securityManager")
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
		return new AuthorizationAttributeSourceAdvisor();
	}


	/**
	 * 缓存管理
	 * @return
	 */
	@Bean
	public EhCacheManager cacheManager() {
		EhCacheManager cacheManager = new EhCacheManager();
		cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
		return cacheManager;
	}
	
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
}
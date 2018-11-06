package com.hzitshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Properties;

@MapperScan("com.hzitshop.mapper*")
/*@ImportResource(locations = "classpath:application-shiro.xml")*/
@SpringBootApplication
public class Application extends SpringBootServletInitializer{

	/**
	 * <p>
	 * 测试 RUN
	 * <br>
	 * 1、http://localhost:8080/user/test1<br>
	 * 2、http://localhost:8080/user/test2<br>
	 * </p>
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * 使用线程池!!
	 * @return
	 */
	/*@Bean
	public ThreadPoolTaskExecutor createThreadPoolTaskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(10);
		threadPoolTaskExecutor.setMaxPoolSize(20);
		return threadPoolTaskExecutor;
	}*/


	//配置mybatis的分页插件pageHelper
	/*@Bean
	public PageHelper pageHelper(){
		//分页插件
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("offsetAsPageNum", "true");
		properties.setProperty("rowBoundsWithCount", "true");
		properties.setProperty("reasonable", "true");
		properties.setProperty("supportMethodsArguments", "true");
		properties.setProperty("returnPageInfo", "check");
		//properties.setProperty("params", "pageNum=page;pageSize=limit;orderBy=orderBy");
		pageHelper.setProperties(properties);
		return pageHelper;
	}*/

}

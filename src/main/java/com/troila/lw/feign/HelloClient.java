package com.troila.lw.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//name指向最終服務提供者的application.yml配置文件中的服務名
//fallback指向如果出問題后的回退類
//下面的"spring-hystrix-member"就是groupkey，來源於你要調用的服務的服務名
@FeignClient(name = "spring-hystrix-member" ,fallback = HelloClientFallback.class)
public interface HelloClient {

	@RequestMapping(method = RequestMethod.GET , value = "/hello")
	public String hello();
	
	@RequestMapping(method = RequestMethod.GET , value = "/toHello")
	public String toHello();
}

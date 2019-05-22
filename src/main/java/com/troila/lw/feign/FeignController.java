package com.troila.lw.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandKey;

@RestController
public class FeignController {

	@Autowired
	private HelloClient helloClient;
	
	@RequestMapping(method = RequestMethod.GET , value = "/hello")
	public String hello() {
		return helloClient.hello();
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/toHello")
	public String toHello() {
		String result = helloClient.toHello();
		HystrixCircuitBreaker breaker = HystrixCircuitBreaker.Factory
				.getInstance(HystrixCommandKey.Factory.asKey("HelloClientFallback#toHello()"));
		
		return result;
	}
}

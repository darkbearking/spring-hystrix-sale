 package com.troila.lw;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 一個工程有什麼插件，需要在入口類這裡聲明
 * 比如熔斷插件、feign插件、負載均衡插件等
 * @author liwei
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@ServletComponentScan
@EnableFeignClients
public class SaleApp {

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(SaleApp.class).web(true).run(args);
	}

}

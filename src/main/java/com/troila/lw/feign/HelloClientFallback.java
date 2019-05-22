package com.troila.lw.feign;

import org.springframework.stereotype.Component;

//話說，只要是想讓spring容器發現并管理的東西，都可以看成一個bean
//只要想把某個東西當成一個bean，你就要至少給它聲明一個@Component註解
//或者不用給這個註解也沒問題，根據那個類或者接口的具體作用，添加上諸如@Controller、@Service等也可以
@Component
public class HelloClientFallback implements HelloClient {

	public String hello() {
		return "fallback hello";
	}

	@Override
	public String toHello() {
		return "fallback timeout hello";
	}

}

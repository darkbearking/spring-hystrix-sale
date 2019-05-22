package com.troila.lw.cache;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.troila.lw.bean.Member;

@Service
public class CacheService {

	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * CacheResult 需要與HystrixCommand搭配使用才可
	 * 同時，這個HystrixCommand註解中什麼都不寫沒事
	 * 一旦controller決定了從緩存取數據，就不會再從你這兒取數了。
	 */
	@CacheResult
	@HystrixCommand
	public Member cacheMember(int id) {
		System.out.println("進入調用Cache");

		Random r = new Random(10);
		Member member = new Member();
		member.setId(r.nextInt());
		//如果使用了緩存，那么你這裡再怎麼浪都不會影響到controller，因為人家沒有再次從你這兒取數據。
/*		Member member = restTemplate.getForObject(
				"http://spring-hystrix-member/member/{id}", Member.class, id);
		return member;*/
		
		System.out.println("退出調用Cache");
		return member;
	}
	
	/**
	 * 注意，當前方法setCache與下面的cancelCache是搭配使用的
	 * 一個用來設置緩存，一個用來取消緩存
	 */
	@CacheResult
	@HystrixCommand(commandKey = "cacheKey")
	public String setCache(int id) {
		System.out.println("設置緩存");
		return null;
	}
	
	/**
	 * 需要注意的是，這個取消緩存的方法，一定要與設置緩存的方法有兩處相同
	 * 第一：commandKey相同
	 * 第二：方法的入參，包括個數與值
	 */
	@CacheRemove(commandKey = "cacheKey")
	@HystrixCommand
	public void cancelCache(int id) {
		System.out.println("取消緩存");
	}
}

package com.troila.lw.collapser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.troila.lw.bean.Member;

@Service
public class CollapserService {

	@Autowired
	private RestTemplate restTemplate;
	
	//batchMethod表示收集那個請求
	@HystrixCollapser(batchMethod = "getMembers" ,
			//這個屬性表示合併請求，結合其內部的註解，表示收集一段時間內的所有請求
			collapserProperties = {
				//第一個參數值表示時間間隔，第二個參數值表示時長（毫秒）
				@HystrixProperty(name = "timerDelayInMilliseconds" , value = "1000")
			})
	public Future<Member> getMember(Integer id){
		System.out.println("執行單個查詢方法");
		return null;
	}
	
	//使用下面這個註解的原因，是因為這個方法會被某個命令執行，所以一定是一個Hystrix的command
	@HystrixCommand
	public List<Member> getMembers(List<Integer> ids){
		List<Member> list = new ArrayList<Member>();
		for(Integer id : ids) {
			System.out.println(id);
			Member m = new Member();
			m.setId(id);
			m.setName("name:"+id);
			list.add(m);
		}
		return list;
	}
}

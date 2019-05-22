package com.troila.lw.cache;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.troila.lw.bean.Member;

@RestController
public class CacheController {

	@Autowired
	private CacheService cacheService;
	
	@RequestMapping(value = "/cacheTest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Member> cacheTest() {
		List<Member> list = new ArrayList<Member>();
		for(int i = 0 ; i < 3 ; i++) {
			//如果我這裡的入參會每次改變的話，那麼就不會用到緩存，否則才會有緩存（很明顯的道理好不好！！）
			//同時需要注意，這個要在一次請求中緩存才會有效（換言之，這個service是被同一個controller中的某個方法多次調用才行）
			list.add(cacheService.cacheMember(1));
		}
		return list;
	}
	
	@RequestMapping(value = "/cancalCache", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String testCancelCache() {
		
		cacheService.setCache(1);
		cacheService.setCache(1);
		cacheService.setCache(1);
		
		//取消緩存的方法，傳入的參數一定要與設置的時候的入參相同，
		cacheService.cancelCache(1);
		System.out.println("########## 分界線 ##########");
		cacheService.setCache(1);
		
		return null;
	}
}

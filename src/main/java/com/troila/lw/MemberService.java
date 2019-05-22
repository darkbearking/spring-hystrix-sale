package com.troila.lw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.troila.lw.bean.Member;

@Service
//當你的某個類中有多個方法，那麼對於每一個方法都要逐個配置@HystrixCommand以及其下的內容的話，就會很繁瑣，我們可以如下通用配置
//需要注意的是，如果你用的是通用配置的默認回退方法，那麼回退方法是沒有參數的，這個與具體方法對應的回退方法的參數完全不同
@DefaultProperties(defaultFallback = "defaultGetMemberFallback")
public class MemberService {

	@Autowired
	private RestTemplate restTemplate;
	
	//如果想用Hystrix，那麼在下面的方法中加一個關於Hystrix的註解即可，因為是當前方法調用member工程的服務
	//之前我們在單機版使用Hystrix的時候，那些配置都寫在了springboot的啟動類或啟動方法中，現在放到@HystrixCommand註解中即可
	@HystrixCommand(
			//當有了通用回退配置的時候，這裡的個性化回退配置會覆蓋通用配置
			//fallbackMethod = "getMemberFallback" , 
			groupKey = "MemberGroup" ,commandKey = "MemberCommandKey"
			, commandProperties = {
					//比如我要設置超時時長，單機版的寫法可以如下，但是整合到spring中的寫法就得間下下一行了
					//@HystrixProperty(name = "hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds" ,value = "2000")
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds" ,value = "1000")
			} 
			, threadPoolProperties = {
					//同樣，對於下面的關於線程池的設置，參數名那一列也不需要按照下面那行的寫法，要按照下下一行的寫法
					//@HystrixProperty(name = "hystrix.command.default.coresize" ,value = "2000")
					@HystrixProperty(name = "coreSize" ,value = "3")
			})
	//補充說明一下，關於@HystrixProperty的name參數。name等號后的東西，與github的wiki中的那些小標題保持一致即可。（這句慢慢體會）
	public Member getMemeber(int id) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Member member = restTemplate.getForObject("http://spring-hystrix-member/member/{id}", Member.class, id);
		return member;
	}
	
	/**
	 * 給Hystrix的某個方法使用的回退方法
	 * 重點在於，這個回退方法的參數一定要與正常訪問方法的參數類型、個數都一致才可。否則會報錯。
	 * @return
	 */
	public Member getMemberFallback(int id) {
		Member member = new Member();
		member.setId(89);
		member.setName("jia");
		member.setMessage("this is an error message!");
		return member;
	}
	
	/**
	 * 給Hystrix使用的通用回退方法
	 * 重點在於，這種通用的回退方法不可以有參數，切切
	 * 同時，通用回退方法會被具體的回退方法覆蓋。舉例來說，我們配置了通用的回退，又在某個類上配置了個性化的回退，以個性化為準
	 * @return
	 */
	public Member defaultGetMemberFallback() {
		Member member = new Member();
		member.setId(89);
		member.setName("jia");
		member.setMessage("this is common error message!");
		return member;
	}
	
	@HystrixCommand()
	public Member cacheMember(Integer id) {
		Member member = restTemplate.getForObject("http://spring-hystrix-member/member/{id}", Member.class, id);
		return member;
	}
}

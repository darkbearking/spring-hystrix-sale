package com.troila.lw.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

/**
 * 建立一個攔截器類，用來通用的對請求使用緩存功能
 * 同時通過註解，告訴springboot，這裡有一個filter
 * @author liwei
 *
 */
//我第一次寫的時候，這裡少了個*，一定得明白這個*是什麼意思
@WebFilter(urlPatterns = "/*" , filterName = "hystrixFilter")
public class MyFilter implements Filter{

	public void destroy() {
	}

	/**
	 * 將緩存功能放到doFilter方法中
	 * 對每一個請求都進行攔截
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
		System.out.println("#########");
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ctx.shutdown();
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}

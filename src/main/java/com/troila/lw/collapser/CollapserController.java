package com.troila.lw.collapser;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.troila.lw.bean.Member;

@RestController
public class CollapserController {

	@Autowired
	private CollapserService collapserService;
	
	/**
	 * 測試合並請求
	 */
	@RequestMapping(value = "/collapser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String testCollapser() throws Exception{
		Future<Member> f1 = collapserService.getMember(1);
		Future<Member> f2 = collapserService.getMember(2);
		Future<Member> f3 = collapserService.getMember(3);
		
		Member m1 = f1.get();
		Member m2 = f2.get();
		Member m3 = f3.get();
		System.out.println("end ?");
		return null;
	}
	
}

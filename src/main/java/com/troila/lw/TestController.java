package com.troila.lw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.troila.lw.bean.Member;

@RestController
public class TestController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = "/router", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Member router() {
		
		return memberService.getMemeber(1);
	}
}

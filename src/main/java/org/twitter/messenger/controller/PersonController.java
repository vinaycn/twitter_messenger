package org.twitter.messenger.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/people")
public class PersonController {
	
	
	@RequestMapping(value="/{myUserId}")
	public String helloMessage(){
		return "Hello World!";
	}
	
	
	
}

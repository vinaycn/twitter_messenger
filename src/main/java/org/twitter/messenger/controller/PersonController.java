package org.twitter.messenger.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins="http://localhost:8080/",maxAge=7000)
@RestController
@RequestMapping("/people")
public class PersonController {
	
	
	@RequestMapping(value="/{myUserId}")
	public String helloMessage(){
		return "Hello World!";
	}
	
	
	
}

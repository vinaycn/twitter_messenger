package org.twitter.messenger.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.twitter.messenger.exceptionhandling.UserNotFoundException;
import org.twitter.messenger.model.Message;
import org.twitter.messenger.modelwrapper.MessageWrapper;
import org.twitter.messenger.service.MessageService;
import org.twitter.messenger.service.PersonService;


@RestController
@RequestMapping("/people/{myId}/messages")
public class MessageController {

	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private PersonService personService;
	
	/**
	 * Endpoint to get all the messages of the users
	 * 
	 * @return list of all messages for the user and its followers
	 */
	@GetMapping
	public ResponseEntity<List<MessageWrapper>> getMessages(@PathVariable("myId") String id,@RequestParam(required=false,defaultValue="") String search){
		if(!personService.validatePerson(Integer.parseInt(id)))
			throw new UserNotFoundException(Integer.parseInt(id));
		
		System.out.println("Serach Term " +search);
		List<MessageWrapper> messageList;
		if(search.isEmpty())
			messageList  = messageService.getMessage(Integer.valueOf(id));
		else 
			messageList  = messageService.getFilteredMessages(Integer.valueOf(id),search);
		
		logger.info("getting all messages for the person with id " +id);
		return new ResponseEntity<List<MessageWrapper>>(messageList,HttpStatus.OK);
	}
	
	/**
	 * Endpoint to post a message by the user
	 * 
	 * @return HTTP status Created if its is successful
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> postMessage(@RequestBody Message message,@PathVariable("myId") String myId){
		if(!personService.validatePerson(Integer.parseInt(myId)))
			throw new UserNotFoundException(Integer.parseInt(myId));
		message.setPersonId(Integer.parseInt(myId));
		messageService.postMessage(message);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	
	
	
}

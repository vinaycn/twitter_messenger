package org.twitter.messenger.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.twitter.messenger.exceptionhandling.UserNotFoundException;
import org.twitter.messenger.model.Person;
import org.twitter.messenger.service.PersonService;


@RestController
@RequestMapping("/people")
public class PersonController {

	
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	
	@Autowired
	private PersonService personService;

	
	/***
	 * 
	 * @return list of all people 
	 */
	@GetMapping
	public ResponseEntity<List<Person>> getPeople() {
		logger.info("get all people");
		return new ResponseEntity<List<Person>>(personService.getPeople(), HttpStatus.OK);
	}

	
	/***
	 * endpoint get person info for the given id
	 * 
	 * @param personId
	 *           
	 * @return person object for the given PersonId
	 */
	@RequestMapping(method=RequestMethod.GET,value = "/{personId}")
	public ResponseEntity<Person> getPerson(@PathVariable("personId") String personId) {
		int id = Integer.parseInt(personId);
		if(!personService.validatePerson(id))
			throw new UserNotFoundException(id);
		logger.info("get person info for the person with id " +personId);
		
		return new ResponseEntity<Person>(personService.getPersonInfo(Integer.valueOf(personId)), HttpStatus.OK);
	}

}

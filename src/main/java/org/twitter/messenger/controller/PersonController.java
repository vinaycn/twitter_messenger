package org.twitter.messenger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.twitter.messenger.model.Person;
import org.twitter.messenger.service.PersonService;


@RestController
@RequestMapping("/people")
public class PersonController {

	@Autowired
	private PersonService personService;

	@GetMapping
	public ResponseEntity<List<Person>> getPeople() {
		return new ResponseEntity<List<Person>>(personService.getPeople(), HttpStatus.OK);
	}

	@RequestMapping(value = "/{personId}")
	public ResponseEntity<Person> getPerson(@PathVariable("personId") String personId) {
		return new ResponseEntity<Person>(personService.getPersonInfo(Integer.valueOf(personId)), HttpStatus.OK);
	}

}

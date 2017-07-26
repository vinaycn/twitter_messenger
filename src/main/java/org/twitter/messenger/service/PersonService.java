package org.twitter.messenger.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.twitter.messenger.controller.PersonController;
import org.twitter.messenger.dao.PersonDaoImpl;
import org.twitter.messenger.model.Person;

@Service
public class PersonService implements IPersonService {

	@Autowired
	private PersonDaoImpl personDaoImpl;

	@Override
	public boolean validatePerson(int personId) {
		if (personDaoImpl.validatePerson(personId) == 0)
			return false;
		else
			return true;

	}

	@Override
	public List<Person> getPeople() {

		List<Person> personList = personDaoImpl.getPeople();

		personList.stream().forEach(
				person -> person.add(linkTo(PersonController.class).slash(person.getPersonId()).withSelfRel()));
		return personList;
	}

}

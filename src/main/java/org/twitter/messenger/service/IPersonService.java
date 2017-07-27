package org.twitter.messenger.service;

import java.util.List;

import org.twitter.messenger.model.Person;

public interface IPersonService {

	public boolean validatePerson(int personId);

	
	public List<Person> getPeople();
	
	
	public Person getPersonInfo(int id);
}

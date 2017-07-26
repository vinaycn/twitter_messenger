package org.twitter.messenger.dao;

import java.util.List;

import org.twitter.messenger.model.Person;

public interface IPersonDao {

	public int validatePerson(int personId);
	
	public List<Person> getPeople();
}

package org.twitter.messenger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.twitter.messenger.dao.PersonDaoImpl;

@Service
public class PersonService implements IPersonService {

	
	@Autowired
	private PersonDaoImpl personDaoImpl;
	
	@Override
	public boolean validatePerson(int personId) {
		if(personDaoImpl.validatePerson(personId)==0)
			return false;
		else return true;
		
	}
	

}

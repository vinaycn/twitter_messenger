package org.twitter.messenger.service;

import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.intThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.twitter.messenger.dao.PersonDaoImpl;
import org.twitter.messenger.exceptionhandling.UserNotFoundException;

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

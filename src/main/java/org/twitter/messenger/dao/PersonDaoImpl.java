package org.twitter.messenger.dao;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class PersonDaoImpl implements IPersonDao{

	
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public int validatePerson(int personId) {
		String checkUser = "SELECT count(people.id) FROM people WHERE people.id = :personId";
		Map<String,Integer> namedParameters = Collections.singletonMap("personId", personId);
		return this.namedParameterJdbcTemplate.queryForObject(checkUser, namedParameters, Integer.class);
	}

}

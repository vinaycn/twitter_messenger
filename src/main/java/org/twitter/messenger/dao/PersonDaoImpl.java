package org.twitter.messenger.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.twitter.messenger.model.Person;
import org.twitter.messenger.utils.PersonRowMapper;
import org.twitter.messenger.utils.FollowersRowMapper;


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

	@Override
	public List<Person> getPeople() {
		String getAllPeople = "SELECT * FROM people";
		return namedParameterJdbcTemplate.query(getAllPeople,new PersonRowMapper());
	}

	@Override
	public Person getPersonInfo(int personId) {
		String getPersonInfo = "SELECT * FROM people WHERE people.id= :personId";
		Map<String,Integer> namedParameters = Collections.singletonMap("personId", personId);
		return namedParameterJdbcTemplate.query(getPersonInfo,namedParameters,new PersonRowMapper()).get(0);
	}
	

}

package org.twitter.messenger.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.twitter.messenger.model.Person;

public class PersonRowMapper implements org.springframework.jdbc.core.RowMapper<Person> {

	@Override
	public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
		Person person = new Person();
		person.setName(rs.getString("name"));
		person.setPersonId(rs.getInt("id"));
		person.setHandle(rs.getString("handle"));
		return person;
		
	}

}

package org.twitter.messenger.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.twitter.messenger.model.Person;
import org.twitter.messenger.modelwrapper.PersonWrapper;

public class FollowingsRowMapper implements RowMapper<Person> {

	@Override
	public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
		Person person = new Person();
		person.setName(rs.getString("name"));
		person.setHandle(rs.getString("handle"));
		person.setPersonId(rs.getInt("person_id"));
		return person;
	}

}

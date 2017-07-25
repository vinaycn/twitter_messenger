package org.twitter.messenger.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.twitter.messenger.model.Person;
import org.twitter.messenger.modelwrapper.PersonWrapper;

public class PersonRowMapper implements RowMapper<PersonWrapper> {

	@Override
	public PersonWrapper mapRow(ResultSet rs, int rowNum) throws SQLException {
		PersonWrapper person = new PersonWrapper();
		person.setName(rs.getString("name"));
		person.setFollowFlag(rs.getString("follow_flag"));
		person.setPersonId(rs.getString("id"));
		person.setHandle(rs.getString("handle"));
		return person;
	}

}

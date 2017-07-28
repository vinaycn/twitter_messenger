package org.twitter.messenger.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.twitter.messenger.modelwrapper.MessageWrapper;

public class MessageRowMapper implements RowMapper<MessageWrapper> {

	@Override
	public MessageWrapper mapRow(ResultSet rs, int rowNum) throws SQLException {
		MessageWrapper messageWrapper = new MessageWrapper();
		messageWrapper.setContent(rs.getString("content"));
		messageWrapper.setName(rs.getString("name"));
		messageWrapper.setPersonId(rs.getInt("person_id"));
		return messageWrapper;
	}

}

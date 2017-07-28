package org.twitter.messenger.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.twitter.messenger.model.Message;
import org.twitter.messenger.modelwrapper.MessageWrapper;
import org.twitter.messenger.utils.MessageRowMapper;

@Repository
public class MessageDaoImpl implements IMessageDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	
	
	/**
	 * 
	 * 
	 * 
	 */
	@Override
	public void addMessage(Message message) {
		String insert = "INSERT INTO messages (person_id,content) VALUES (:personId,:content)";
		Map<String, Object> parameters = new HashMap<>(2);
		parameters.put("content", message.getContent());
		parameters.put("personId", message.getPersonId());
		namedParameterJdbcTemplate.update(insert, parameters);
	}

	
	/***
	 * 
	 * this method makes database call to retrieve the messages of the 
	 * given user and users followers
	 * 
	 * @param personId
	 *          person id
	 * 
	 * @return list of messages posted by the users and user followers
	 */
	@Override
	public List<MessageWrapper> getMessages(int personId) {

	
		String getMessageQuery = "SELECT messages.content, messages.person_id, people.name FROM (messages "
				+ " JOIN people  ON (messages.person_id = people.id)) WHERE"
				+ " (messages.person_id in (SELECT followers.person_id FROM followers WHERE followers.follower_person_id = :personId)"
				+ " OR (messages.person_id = :personId))";

		Map<String,Integer> namedParameters = Collections.singletonMap("personId", personId);
		return namedParameterJdbcTemplate.query(getMessageQuery, namedParameters, new MessageRowMapper());

	}
	
	public List<MessageWrapper> getFilteredMessages(int personId,String searchTerm){
		String getMessageQuery = "SELECT messages.content, messages.person_id, people.name FROM (messages "
				+ " JOIN people  ON (messages.person_id = people.id)) WHERE"
				+ " (messages.person_id in (SELECT followers.follower_person_id FROM followers WHERE followers.person_id = :personId)"
				+ " OR (messages.person_id = :personId)) AND (messages.content LIKE :serachTerm)";

		Map<String, Object> parameters = new HashMap<>(2);
		parameters.put("serachTerm","%"+searchTerm+"%");
		parameters.put("personId", personId);
		return namedParameterJdbcTemplate.query(getMessageQuery, parameters, new MessageRowMapper());
	}
}

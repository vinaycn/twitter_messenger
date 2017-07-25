package org.twitter.messenger.dao;

import java.util.List;

import org.twitter.messenger.model.Message;
import org.twitter.messenger.modelwrapper.MessageWrapper;

public interface IMessageDao {

	public void addMessage(Message message);
	
	
	public List<MessageWrapper> getMessages(int personId);
}

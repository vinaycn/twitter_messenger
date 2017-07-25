package org.twitter.messenger.service;

import java.util.List;

import org.twitter.messenger.model.Message;
import org.twitter.messenger.modelwrapper.MessageWrapper;



public interface IMessageService {

	public void postMessage(Message message);
	
	public List<MessageWrapper> getMessage(int personId);
}

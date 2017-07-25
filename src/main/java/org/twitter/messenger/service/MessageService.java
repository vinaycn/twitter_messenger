package org.twitter.messenger.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.twitter.messenger.controller.PersonController;
import org.twitter.messenger.dao.MessageDaoImpl;
import org.twitter.messenger.model.Message;
import org.twitter.messenger.modelwrapper.MessageWrapper;

@Service
public class MessageService implements IMessageService {

	@Autowired
	private MessageDaoImpl messageDaoImpl;

	@Override
	public void postMessage(Message message) {
		messageDaoImpl.addMessage(message);
	}

	@Override
	public List<MessageWrapper> getMessage(int personId) {
		List<MessageWrapper> messageWrapperList = messageDaoImpl.getMessages(personId);
		for (MessageWrapper messageWrapper : messageWrapperList) {
			Link selfLink = linkTo(PersonController.class).slash(messageWrapper.personId).withSelfRel();
			messageWrapper.add(selfLink);
		}
		return messageWrapperList;
	}

}

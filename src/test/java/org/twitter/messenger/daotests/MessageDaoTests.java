package org.twitter.messenger.daotests;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.twitter.messenger.dao.MessageDaoImpl;
import org.twitter.messenger.modelwrapper.MessageWrapper;



@RunWith(SpringJUnit4ClassRunner.class)
public class MessageDaoTests {

	
	@MockBean
	private MessageDaoImpl messageDaoImpl;
	
	
	@Test
	public void getMessages(){
		List<MessageWrapper>  messageList =messageDaoImpl.getMessages(8);
		Assert.assertEquals(26,messageList.size());
		
	}
}

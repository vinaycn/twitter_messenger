package org.twitter.messenger.daotests;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.twitter.messenger.ChallengeApplication;
import org.twitter.messenger.dao.MessageDaoImpl;
import org.twitter.messenger.modelwrapper.MessageWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=ChallengeApplication.class)
public class MessageDaoTests {

	
	@Autowired
	private MessageDaoImpl messageDaoImpl;
	
	
	 @Test
	    public void testContext() {
	        assertNotNull(messageDaoImpl);
	    }
	
	@Test
	public void getMessages(){
		List<MessageWrapper>  messageList =messageDaoImpl.getMessages(8);
		Assert.assertEquals(39,messageList.size());	
	}
}

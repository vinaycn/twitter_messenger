package org.twitter.messenger.daotests;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.twitter.messenger.ChallengeApplication;
import org.twitter.messenger.dao.SocialDaoImpl;
import org.twitter.messenger.modelwrapper.PersonWrapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ChallengeApplication.class)
public class SocialDaoTests {

	
	@Autowired
	private SocialDaoImpl socialDaoImpl;
	
	@Test
    public void testContext() {
        assertNotNull(socialDaoImpl);
    }
	
	
	@Test
	public void getFollowers(){
	 List<PersonWrapper> followersList= socialDaoImpl.getFollowers(8);
	 Assert.assertEquals(1,followersList.size());
	}
	
	
	@Test
	public void getFollowings(){
		List<PersonWrapper> followersList= socialDaoImpl.getFollowings(1);
		Assert.assertEquals(1,followersList.size());
	}
	
	
	@Test
	public void follow(){
		//personId 2 wants to follow 1
		socialDaoImpl.follow(1,2);
		
	}
	
	
	@Test
	public void unfollow(){
		//personId 2 wants to unfollow 1
		socialDaoImpl.unFollow(1,2);
	}
}

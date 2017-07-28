package org.twitter.messenger.daotests;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.twitter.messenger.ChallengeApplication;
import org.twitter.messenger.dao.SocialDaoImpl;
import org.twitter.messenger.model.Person;
import org.twitter.messenger.modelwrapper.PersonWrapper;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=ChallengeApplication.class)
@Transactional
public class SocialDaoTests {

	
	@Autowired
	private SocialDaoImpl socialDaoImpl;
	
	@Test
    public void testContext() {
        assertNotNull(socialDaoImpl);
    }
	
	
	@Test
	public void getFollowers(){
	 List<PersonWrapper> followersList= socialDaoImpl.getFollowers(2);
	 Assert.assertEquals(3,followersList.size());
	}
	
	
	@Test
	public void getFollowings(){
		List<Person> followingList= socialDaoImpl.getFollowings(3);
		Assert.assertEquals(3,followingList.size());
	}
	
	
	@Test
	public void follow(){
		//personId 1 wants to follow 2
		socialDaoImpl.follow(2,1);
		List<Person> followingList= socialDaoImpl.getFollowings(1);
		Assert.assertEquals(3,followingList.size());
	}
	
	
	@Test
	public void unfollow(){
		//personId 3 wants to unfollow 4
		socialDaoImpl.unFollow(4,3);
		//Followers of 4 should be 0
		List<PersonWrapper> followersList= socialDaoImpl.getFollowers(4);
		Assert.assertEquals(0,followersList.size());
	}
}

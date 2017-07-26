package org.twitter.messenger.daotests;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.twitter.messenger.dao.SocialDaoImpl;
import org.twitter.messenger.modelwrapper.PersonWrapper;
//import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

@RunWith(SpringJUnit4ClassRunner.class)
//@JdbcTest
public class SocialDaoTests {

	
	@MockBean
	private SocialDaoImpl socialDaoImpl;
	
	
	
	
	@Test
	public void getFollowers(){
	 List<PersonWrapper> followersList= socialDaoImpl.getFollowers(1);
	 Assert.assertEquals(1,followersList.size());
	}
	
	
	@Test
	public void getFollowings(){
		
	}
	
	
	@Test
	public void follow(){
		
	}
	
	
	@Test
	public void unfollow(){
		
	}
}

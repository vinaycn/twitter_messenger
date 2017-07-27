package org.twitter.messenger.dao;

import java.util.List;

import org.twitter.messenger.model.Person;
import org.twitter.messenger.modelwrapper.PersonWrapper;

public interface ISocialDao {

    public List<PersonWrapper> getFollowers(int personId);
	
	public List<Person> getFollowings(int personId);
	
	public List<Person> getOtherPeople(int personId);
	
	public void unFollow(int personId,int followerPersonId);
	
	public void follow(int personId,int followerPersonId);
}

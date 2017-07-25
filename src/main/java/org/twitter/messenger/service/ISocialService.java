package org.twitter.messenger.service;

import java.util.List;

import org.twitter.messenger.model.Person;
import org.twitter.messenger.modelwrapper.PersonWrapper;

public interface ISocialService {

	public List<PersonWrapper> getFollowers(int personId);
	
	public List<PersonWrapper> getFollowings(int personId);
	
	public void unFollow(int personId,int followerPersonId);
	
	public void follow(int personId,int followerPersonId);
}

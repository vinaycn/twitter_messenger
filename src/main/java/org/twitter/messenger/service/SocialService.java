package org.twitter.messenger.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.twitter.messenger.controller.PersonController;
import org.twitter.messenger.dao.SocialDaoImpl;
import org.twitter.messenger.exceptionhandling.DuplicateIdException;
import org.twitter.messenger.model.Person;
import org.twitter.messenger.modelwrapper.PersonWrapper;

@Service
public class SocialService implements ISocialService {

	@Autowired
	private SocialDaoImpl socialDaoImpl;

	/***
	 * 
	 * return all followers for the given personId
	 * 
	 * @param personId
	 * 
	 * @return will return list followers for the given personId
	 */
	@Override
	public List<PersonWrapper> getFollowers(int personId) {
		List<PersonWrapper> followersList = socialDaoImpl.getFollowers(personId);
		followersList.stream().forEach(
				person -> person.add(linkTo(PersonController.class).slash(person.getPersonId()).withSelfRel()));
		return followersList;
	}

	/***
	 * 
	 * return all following for the given personId
	 * 
	 * @param personId
	 * 
	 * @return will return list following for the given personId
	 */
	@Override
	public List<Person> getFollowings(int personId) {
		List<Person> followersList = socialDaoImpl.getFollowings(personId);
		followersList.stream().forEach(
				person -> person.add(linkTo(PersonController.class).slash(person.getPersonId()).withSelfRel()));
		return followersList;
	}

	/***
	 * 
	 * unfollow a person
	 * 
	 * @param personId
	 *            personId to unfollow
	 * @param followerPersonId
	 *            followerPersonId who raised unfollow request
	 */
	@Override
	@Transactional
	public void unFollow(int personId, int followerPersonId) {

		if(personId==followerPersonId) throw new DuplicateIdException(personId, followerPersonId);
		socialDaoImpl.unFollow(personId, followerPersonId);
	}

	/***
	 * 
	 * follow a person
	 * 
	 * @param personId
	 *            personId to follow
	 * @param followerPersonId
	 *            followerPersonId who raised follow request
	 */
	@Override
	@Transactional
	public void follow(int personId, int followerPersonId) {
		if(personId==followerPersonId) throw new DuplicateIdException(personId, followerPersonId);
		socialDaoImpl.follow(personId, followerPersonId);
	}

	

}

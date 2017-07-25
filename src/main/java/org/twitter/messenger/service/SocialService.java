package org.twitter.messenger.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.twitter.messenger.controller.PersonController;
import org.twitter.messenger.dao.SocialDaoImpl;
import org.twitter.messenger.model.Person;
import org.twitter.messenger.modelwrapper.PersonWrapper;

@Service
public class SocialService implements ISocialService {

	@Autowired
	private SocialDaoImpl socialDaoImpl;

	@Override
	public List<PersonWrapper> getFollowers(int personId) {
		List<PersonWrapper> followersList = socialDaoImpl.getFollowers(personId);
		followersList.stream().forEach(
				person -> person.add(linkTo(PersonController.class).slash(person.getPersonId()).withSelfRel()));
		return followersList;
	}

	@Override
	public List<PersonWrapper> getFollowings(int personId) {
		List<PersonWrapper> followersList = socialDaoImpl.getFollowings(personId);
		followersList.stream().forEach(
				person -> person.add(linkTo(PersonController.class).slash(person.getPersonId()).withSelfRel()));
		return followersList;
	}

	@Override
	@Transactional
	public void unFollow(int personId, int followerPersonId) {
		socialDaoImpl.follow(personId, followerPersonId);
	}

	@Override
	@Transactional
	public void follow(int personId, int followerPersonId) {
		socialDaoImpl.unFollow(personId, followerPersonId);
	}

}

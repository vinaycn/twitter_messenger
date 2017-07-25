package org.twitter.messenger.controller;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.twitter.messenger.model.Person;
import org.twitter.messenger.modelwrapper.PersonWrapper;
import org.twitter.messenger.service.SocialService;

@RestController
@RequestMapping("/people/{followerPersonId}/")
public class SocialController {

	@Autowired
	private SocialService socialService;

	/**
	 * Endpoint to get all the followers for the user
	 * 
	 * @return the list of followers for the users
	 */
	@GetMapping("/followers")
	public ResponseEntity<List<PersonWrapper>> getFollowers(@PathVariable("followerPersonId") String personId) {
		int id = Integer.valueOf(personId);
		return new ResponseEntity<List<PersonWrapper>>(socialService.getFollowers(id), HttpStatus.OK);
	}

	/**
	 * EndPoint to follow an User
	 * 
	 */
	@RequestMapping(value = "/followers/{personId}", method = RequestMethod.POST)
	public ResponseEntity<Void> addFollower(@PathVariable("personId") String personId,@PathVariable("followerPersonId") String followerPersonId) {
       socialService.follow(Integer.valueOf(personId), Integer.valueOf(followerPersonId));
	   return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	/**
	 * Endpoint to unfollow an user
	 */
	@RequestMapping(value = "/followers/{personId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> unFollow(@PathVariable("personId") String personId,@PathVariable("followerPersonId") String followerPersonId) {
		socialService.unFollow(Integer.valueOf(personId), Integer.valueOf(followerPersonId));
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Endpoint to get all the followings for the user
	 * 
	 * 
	 * @return
	 */
	@GetMapping("/following")
	public ResponseEntity<List<PersonWrapper>> getFollowings(@PathVariable("followerPersonId") String personId) {
		int id = Integer.valueOf(personId);
		return new ResponseEntity<List<PersonWrapper>>(socialService.getFollowings(id), HttpStatus.OK);
	}
}

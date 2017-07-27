package org.twitter.messenger.controller;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/people/{myId}")
public class SocialController {

	@Autowired
	private SocialService socialService;

	/**
	 * Endpoint to get all the followers for the user
	 * 
	 * @param myId
	 *         followers for the given myId
	 * 
	 * @return the list of followers for the users
	 */
	@GetMapping("/followers")
	public ResponseEntity<List<PersonWrapper>> getFollowers(@PathVariable("myId") String personId) {
		int id = Integer.valueOf(personId);
		return new ResponseEntity<List<PersonWrapper>>(socialService.getFollowers(id), HttpStatus.OK);
	}

	/**
	 * EndPoint to follow an User
	 * 
	 *  @param followPersonId
	 *            person id to follow
	 *            
	 *  @param myId
	 *            person id who wants to follow
	 *            
	 *  @return return status created if it is successful
	 *            
	 */
	@RequestMapping(value = "/followers/{followPersonId}", method = RequestMethod.POST)
	public ResponseEntity<Void> addFollower(@PathVariable("followPersonId") String personId,@PathVariable("myId") String followerPersonId) {
       socialService.follow(Integer.valueOf(personId), Integer.valueOf(followerPersonId));
	   return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	/**
	 * EndPoint to unfollow an User
	 * 
	 *  @param personId
	 *            person id to unfollow
	 *            
	 *  @param myId
	 *            person id who wants to unfollow
	 *            
	 *  @return return status NO_CONTENT if it is successful
	 *            
	 */
	@RequestMapping(value = "/followers/{personId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> unFollow(@PathVariable("personId") String personId,@PathVariable("myId") String followerPersonId) {
		socialService.unFollow(Integer.valueOf(personId), Integer.valueOf(followerPersonId));
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	/**
	 * EndPoint to get following of the user
	 * 
	 *  @param myId
	 *           person id to get the followings 
	 *            
	 *  @return return list of the following for the myId
	 *            
	 */
	@GetMapping("/following")
	public ResponseEntity<List<PersonWrapper>> getFollowings(@PathVariable("myId") String personId) {
		int id = Integer.valueOf(personId);
		return new ResponseEntity<List<PersonWrapper>>(socialService.getFollowings(id), HttpStatus.OK);
	}
}

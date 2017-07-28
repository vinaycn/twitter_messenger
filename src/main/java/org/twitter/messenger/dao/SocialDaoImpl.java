package org.twitter.messenger.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.twitter.messenger.exceptionhandling.DuplicateEntryException;
import org.twitter.messenger.model.Person;
import org.twitter.messenger.modelwrapper.PersonWrapper;
import org.twitter.messenger.utils.FollowersRowMapper;
import org.twitter.messenger.utils.FollowingsRowMapper;

@Repository
public class SocialDaoImpl implements ISocialDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	
	/***
	 * Will make database call to retrieve the list of all followers for the person!
	 * 
	 * @param personId
	 *          given personId
	 * 
	 * @return list of followers for the given personId
	 */
	@Override
	public List<PersonWrapper> getFollowers(int personId) {
		// TODO Auto-generated method stub

		String followers = "SELECT followers.follower_person_id, followers.follow_flag, people.name, people.handle FROM (followers "
				+ " JOIN people ON (followers.follower_person_id = people.id)) WHERE (followers.person_id = :personId)";
		Map<String, Object> followerFields = new HashMap<>();
		followerFields.put("personId", personId);
		return namedParameterJdbcTemplate.query(followers, followerFields, new FollowersRowMapper());
	}

	/***
	 * Will make database call to unfollow an User. First follow_flag is updated to 'F' if the 
	 * entry exists in the database for  followerPersonId and unFollowpersonId . And Second the 
	 * entry for unFollowpersonId and followerPersonId will be deleted.
	 * 
	 * @param followerPersonId
	 *          the person id who wants to unfollow
	 * @param unFollowpersonId
	 *          the person id who will be unfollowed
	 * 
	 */
	@Override
	public void unFollow(int unFollowpersonId, int followerPersonId) {
		String unFollowQuery = "UPDATE followers set follow_flag =:followFlag WHERE "
				+ " (followers.person_id = :followerPersonId AND followers.follower_person_id = :unFollowpersonId)";
		Map<String, Object> unFollowFields = new HashMap<>();
		unFollowFields.put("unFollowpersonId", unFollowpersonId);
		unFollowFields.put("followerPersonId", followerPersonId);
		unFollowFields.put("followFlag", 'F');
		namedParameterJdbcTemplate.update(unFollowQuery, unFollowFields);
		// after changing follow_flag delete the entry
		String deleteFollower = "DELETE FROM followers WHERE "
				+ "(followers.person_id = :unFollowpersonId AND followers.follower_person_id = :followerPersonId)";
		namedParameterJdbcTemplate.update(deleteFollower, unFollowFields);
	}

	
	
	/***
	 * Will make database call to follow an User. First follow_flag is updated to 'U' if the 
	 * entry exists in the database for  followerPersonId and unFollowpersonId . And Second the 
	 * entry for followingPersonId and followerPersonId will be added.
	 * 
	 * @param followerPersonId
	 *          the person id who wants to follow
	 * @param followingPersonId
	 *          the person id who will be followed
	 * 
	 */
	@Override
	public void follow(int followingPersonId, int followerPersonId) {

		Map<String, Object> followFields = new HashMap<>();
		followFields.put("followingPersonId", followingPersonId);
		followFields.put("followerPersonId", followerPersonId);

		
		//Check if the user already follow the user
		String checkDuplicates = "SELECT count(followers.id) FROM followers WHERE (followers.person_id = :followingPersonId) "
				+ " AND (followers.follower_person_id = :followerPersonId)";
		int entry = namedParameterJdbcTemplate.queryForObject(checkDuplicates, followFields, Integer.class);

		//if the user follow throw an exception
		if (entry > 0)
			throw new DuplicateEntryException();

		//update the follow flag if the following users follows these user 
		String checkFollower = "UPDATE followers set followers.follow_flag = :followFlag  WHERE (followers.person_id = :followerPersonId"
				+ " AND followers.follower_person_id = :followingPersonId)";

		followFields.put("followFlag", 'U');
		int count = namedParameterJdbcTemplate.update(checkFollower, followFields);
		if (count == 0) {
			followFields.put("followFlag", 'F');
		}
		//insert the entry
		String follow = "INSERT INTO followers (person_id,follower_person_id, follow_flag) VALUES (:followingPersonId,:followerPersonId,:followFlag)";
		namedParameterJdbcTemplate.update(follow, followFields);
	}

	
	/***
	 * Will make database call to retrieve the list of all following for the person!
	 * 
	 * @param personId
	 *          given personId
	 * 
	 * @return list of following for the given personId
	 */
	@Override
	public List<Person> getFollowings(int personId) {
		String followings = "SELECT followers.person_id, people.name, people.handle FROM (followers "
				+ " JOIN people ON (followers.person_id = people.id)) WHERE (followers.follower_person_id = :personId)";
		Map<String, Object> followingsFields = new HashMap<>();
		followingsFields.put("personId", personId);
		return namedParameterJdbcTemplate.query(followings, followingsFields, new FollowingsRowMapper());
	}

}

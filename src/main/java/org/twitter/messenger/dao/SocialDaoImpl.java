package org.twitter.messenger.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.twitter.messenger.modelwrapper.PersonWrapper;
import org.twitter.messenger.utils.FollowersRowMapper;
import org.twitter.messenger.utils.FollowingsRowMapper;

@Repository
public class SocialDaoImpl implements ISocialDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<PersonWrapper> getFollowers(int personId) {
		// TODO Auto-generated method stub

		String followers = "SELECT followers.follower_person_id, followers.follow_flag, people.name, people.handle FROM (followers "
				+ " JOIN people ON (followers.follower_person_id = people.id)) WHERE (followers.person_id = :personId)";
		Map<String, Object> followerFields = new HashMap<>();
		followerFields.put("personId", personId);
		return namedParameterJdbcTemplate.query(followers, followerFields, new FollowersRowMapper());
	}

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

	@Override
	public void follow(int followingPersonId, int followerPersonId) {

		String checkFollower = "UPDATE followers set followers.follow_flag = :followFlag  WHERE (followers.person_id = :followerPersonId"
				+ " AND followers.follower_person_id = :followingPersonId)";
		Map<String, Object> followFields = new HashMap<>();
		followFields.put("followingPersonId", followingPersonId);
		followFields.put("followerPersonId", followerPersonId);
		followFields.put("followFlag", 'U');
		int count = namedParameterJdbcTemplate.update(checkFollower, followFields);
		if (count == 0) {
			followFields.put("followFlag",'F');
		}
		String follow = "INSERT INTO followers (person_id,follower_person_id, follow_flag) VALUES (:followingPersonId,:followerPersonId,:followFlag)";
		namedParameterJdbcTemplate.update(follow, followFields);
	}

	@Override
	public List<PersonWrapper> getFollowings(int personId) {
		String followings = "SELECT followers.person_id, followers.follow_flag, people.name, people.handle FROM (followers "
				+ " JOIN people ON (followers.person_id = people.id)) WHERE (followers.follower_person_id = :personId)";
		Map<String, Object> followingsFields = new HashMap<>();
		followingsFields.put("personId", personId);
		return namedParameterJdbcTemplate.query(followings, followingsFields, new FollowingsRowMapper());
	}

}

package org.twitter.messenger.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.twitter.messenger.modelwrapper.PersonWrapper;
import org.twitter.messenger.utils.PersonRowMapper;

@Repository
public class SocialDaoImpl implements ISocialDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	String getMessageQuery = "SELECT messages.content, messages.person_id, people.name FROM (messages "
			+ " JOIN people  ON (messages.person_id = people.id)) WHERE"
			+ " (messages.person_id in (SELECT followers.follower_person_id FROM followers WHERE followers.person_id = :personId)"
			+ " OR (messages.person_id = :personId))";


	@Override
	public List<PersonWrapper> getFollowers(int personId) {
		// TODO Auto-generated method stub
		
		String followers = "SELECT people.name, people.handle, people.id, followers.follow_flag FROM (people JOIN "
				+ " followers ON (followers.follower_person_id = people.id)) WHERE people.id "
				+ "in (SELECT followers.follower_person_id FROM followers WHERE (followers.person_id = :personId "
				+ " and followers.status_flag = :statusFlag ))";
		Map<String,Object> followerFields = new HashMap<>();
		followerFields.put("personId", personId);
		followerFields.put("statusFlag",'A');
		return namedParameterJdbcTemplate.query(followers, followerFields, new PersonRowMapper());
	}

	

	@Override
	public void unFollow(int personId, int followerPersonId) {
		String unFollowQuery = "UPDATE followers set status_flag = :statusFlag WHERE "
				+ " follower.person_id = :personId and follower.follower_person_id = :followerPersonId";
		Map<String,Object> unFollowFields = new HashMap<>();
		unFollowFields.put("personId",personId);
		unFollowFields.put("followerPersonId",followerPersonId);
		unFollowFields.put("statusFlag",'I');
		namedParameterJdbcTemplate.update(unFollowQuery, unFollowFields);
		//after unfollow update the follow_flag 
		String updateUnFollowTag = "UPDATE followers set follow_flag = :followFlag WHERE "
				+ " follower.person_id = :followerPersonId and  follower.follower_person_id = :personId";
		unFollowFields.put("followFlag",'F');
		namedParameterJdbcTemplate.update(updateUnFollowTag, unFollowFields);
	}

	@Override
	public void follow(int followingPersonId, int followerPersonId) {

		String checkFollow = "Select count(followers.id) FROM followers (:followerPersonId,:followingPersonId,:statusFlag)";
		Map<String,Object> followFields = new HashMap<>();
		followFields.put("followingPersonId", followingPersonId);
		followFields.put("followingPersonId", followingPersonId);
		followFields.put("statusFlag",'A');
		int count = namedParameterJdbcTemplate.queryForObject(checkFollow, followFields, Integer.class);
		char c;
		if(count!=0) c = 'U';
		else c = 'F';
		followFields.put("followFlag",c);
		String follow = "INSERT INTO followers(:followingPersonId,:followerPersonId,:followFlag)";
		namedParameterJdbcTemplate.update(follow, followFields);
	}
	
	
	@Override
	public List<PersonWrapper> getFollowings(int personId) {
		String followings = "SELECT people.name, people.handle, people.id,followers.follow_flag FROM (people JOIN "
				+ " followers ON (followers.person_id = people.id)) WHERE people.id "
				+ "in (SELECT followers.person_id FROM followers WHERE (followers.follower_person_id = :personId "
				+ " and followers.status_flag = :statusFlag ))";
		Map<String,Object> followingsFields = new HashMap<>();
		followingsFields.put("personId", personId);
		followingsFields.put("statusFlag",'A');
		return namedParameterJdbcTemplate.query(followings, followingsFields, new PersonRowMapper());
	}

}

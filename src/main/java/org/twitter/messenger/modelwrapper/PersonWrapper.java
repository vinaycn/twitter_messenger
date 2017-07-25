package org.twitter.messenger.modelwrapper;

import org.springframework.hateoas.ResourceSupport;

public class PersonWrapper extends ResourceSupport {

	private String name;
	private String personId;
	private String followFlag;
	private String handle;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getFollowFlag() {
		return followFlag;
	}

	public void setFollowFlag(String followFlag) {
		this.followFlag = followFlag;
	}

	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

}

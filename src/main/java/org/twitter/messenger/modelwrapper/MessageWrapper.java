package org.twitter.messenger.modelwrapper;

import org.springframework.hateoas.ResourceSupport;

public class MessageWrapper extends ResourceSupport {

	public String content;
	public String name;
	public int personId;

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	

	

}

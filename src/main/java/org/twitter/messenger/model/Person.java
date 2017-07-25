package org.twitter.messenger.model;

import org.springframework.hateoas.ResourceSupport;

public class Person extends ResourceSupport {

	private int personId;
	private String name;
	private String handle;
	
	
	
	public Person() {
		// TODO Auto-generated constructor stub
	}
	
	
	public int getPersonId() {
		return personId;
	}


	public void setPersonId(int personId) {
		this.personId = personId;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getHandle() {
		return handle;
	}


	public void setHandle(String handle) {
		this.handle = handle;
	}
	
	
}

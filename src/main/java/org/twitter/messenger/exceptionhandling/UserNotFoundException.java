package org.twitter.messenger.exceptionhandling;

public class UserNotFoundException extends RuntimeException{

	
	public UserNotFoundException(int personId){
		super("could not find user '" + personId + "'.");
	}
}

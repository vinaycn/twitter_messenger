package org.twitter.messenger.exceptionhandling;

public class DuplicateIdException extends RuntimeException {

	public DuplicateIdException(int id,int id1) {
		System.out.println("follow or unfollow request made for same user id " +id );
	}
}

package org.twitter.messenger.exceptionhandling;

public class DuplicateEntryException  extends RuntimeException{

	
	public DuplicateEntryException() {
		super("Duplicate entry request");
	}
}

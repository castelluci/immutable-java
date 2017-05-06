package org.vince.exceptions;

/**
 * Created by VINCENT on 06/05/2017.
 */
public class ImmutableException extends RuntimeException{
	public ImmutableException(final String message){
		super(message);
	}

	public ImmutableException(final String message, final Throwable cause){
		super(message, cause);
	}
}

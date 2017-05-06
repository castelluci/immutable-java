package org.vince.exceptions;

/**
 * Created by VINCENT on 06/05/2017.
 */
public class ImmutableNotCreatedException extends ImmutableException{
	public ImmutableNotCreatedException(final String message, final Throwable cause){
		super(message, cause);
	}
}

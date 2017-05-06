package org.vince.interceptor;

import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vince.exceptions.ImmutableFieldAccessException;

import java.util.concurrent.Callable;

/**
 * Created by VINCENT on 06/05/2017.
 */
public class ImmutableInterceptor{

	private final static Logger LOGGER = LoggerFactory.getLogger(ImmutableInterceptor.class);

	private ImmutableInterceptor(){}

	public static Object intercept(
			@SuperCall
					Callable<? extends Object> callable) throws ImmutableFieldAccessException{
		LOGGER.error("Tried to call {} while object protected by Immutable wrapper", callable.toString());
		throw new ImmutableFieldAccessException("Tried to update object protected by Immutable wrapper");
	}

}

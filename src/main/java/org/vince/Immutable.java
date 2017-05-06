package org.vince;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vince.cache.ImmutableCache;
import org.vince.exceptions.ImmutableNotCreatedException;
import org.vince.interceptor.ImmutableInterceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by VINCENT on 06/05/2017.
 */
public class Immutable<T extends Object>{

	private final static Logger LOGGER = LoggerFactory.getLogger(Immutable.class);

	private final T value;

	private Immutable(final T value){
		this.value = value;
	}

	public static <T> Immutable<T> from(T object){
		try{
			if(!ImmutableCache.INSTANCE.containsKey(object.getClass())){
				ImmutableCache.INSTANCE.put(object.getClass(), new ByteBuddy().subclass(object.getClass())
																			  .method(ElementMatchers.nameStartsWith(
																					  "set"))
																			  .intercept(MethodDelegation.to(
																					  ImmutableInterceptor.class))
																			  .make()
																			  .load(object.getClass().getClassLoader())
																			  .getLoaded());
			}

			return new Immutable<T>(copyFields(object,
											   (T) ImmutableCache.INSTANCE.get(object.getClass()).newInstance()));
		}catch(Exception e){
			LOGGER.error("Unable to build up an immutable from object {}", object);
			throw new ImmutableNotCreatedException("Unable to build up an immutable object from " + object, e);
		}
	}

	private static <T> T copyFields(final T from, final T to) throws IllegalAccessException{
		final Field[] allFieldsOfWrappedObject = FieldUtils.getAllFields(to.getClass());
		final Field[] allFieldsOfInitialObject = FieldUtils.getAllFields(from.getClass());

		final Map<String, Field> fieldsToSet = new HashMap<String, Field>();
		final Map<String, Field> fieldToGet  = new HashMap<String, Field>();

		for(Field field : allFieldsOfWrappedObject){
			fieldsToSet.put(field.getName().toLowerCase(), field);
		}
		for(Field field : allFieldsOfInitialObject){
			fieldToGet.put(field.getName().toLowerCase(), field);
		}

		for(Map.Entry<String, Field> field : fieldsToSet.entrySet()){
			if(!Modifier.isStatic(field.getValue().getModifiers())){
				field.getValue().setAccessible(true);
				fieldToGet.get(field.getKey()).setAccessible(true);
				field.getValue().set(to, fieldToGet.get(field.getKey()).get(from));
				field.getValue().setAccessible(false);
				fieldToGet.get(field.getKey()).setAccessible(false);
			}
		}

		return to;
	}

	public T get(){
		return value;
	}
}

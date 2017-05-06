package org.vince.cache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

/**
 * Created by VINCENT on 06/05/2017.
 */
public class ImmutableCache{

	private static final CacheManager        cacheManager;
	public static final  Cache<Class, Class> INSTANCE;

	private ImmutableCache(){
	}

	static{
		cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
										  .withCache("immutableClasses",
													 CacheConfigurationBuilder.newCacheConfigurationBuilder(Class
																													.class,
																											Class
																													.class,
																											ResourcePoolsBuilder
																													.heap(10)))
										  .build();
		cacheManager.init();

		INSTANCE = cacheManager.getCache("immutableClasses", Class.class, Class.class);
	}

}

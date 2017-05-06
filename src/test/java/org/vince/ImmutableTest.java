package org.vince;

import org.junit.Assert;
import org.junit.Test;
import org.vince.exceptions.ImmutableFieldAccessException;
import org.vince.exceptions.ImmutableNotCreatedException;
import org.vince.pojo.Pojo;

import java.math.BigDecimal;

/**
 * Created by VINCENT on 06/05/2017.
 */
public class ImmutableTest{

	@Test(expected = ImmutableNotCreatedException.class)
	public void testBuildImmutableWithNullObject(){
		final Immutable<Pojo> pojoImmutable = Immutable.from(null);
	}

	@Test
	public void testBuildImmutable(){
		final Pojo test = new Pojo(1, 1, "test");

		final Immutable<Pojo> pojoImmutable = Immutable.from(test);

		Assert.assertEquals(test.getAge(), pojoImmutable.get().getAge());
		Assert.assertEquals(test.getSize(), pojoImmutable.get().getSize());
		Assert.assertEquals(test.getName(), pojoImmutable.get().getName());

		Assert.assertEquals(test.getTest_1(), pojoImmutable.get().getTest_1());
		Assert.assertEquals(test.getTest_2(), pojoImmutable.get().getTest_2());
		Assert.assertEquals(test.getTest_3(), pojoImmutable.get().getTest_3());
	}

	@Test(expected = ImmutableFieldAccessException.class)
	public void testBuildImmutableAndSetShouldThrowException(){
		final Pojo test = new Pojo(1, 1, "test");

		final Immutable<Pojo> pojoImmutable = Immutable.from(test);

		pojoImmutable.get().setAge(0);
	}

	@Test
	public void benchmarkBuildImmutableVSNormal(){
		final Pojo test = new Pojo(1, 1, "test");

		final long start = System.nanoTime();
		for(int i = 0; i < 1000000; i++){
			Immutable.from(test);
		}
		final long end = System.nanoTime();

		final BigDecimal duration = new BigDecimal(end - start).divide(new BigDecimal(Math.pow(10, 9)));
		System.out.println("Build Immutable : " + duration + "s");

		long start2 = System.nanoTime();
		for(int i = 0; i < 1000000; i++){
			new Pojo(1, 1, "test");
		}
		long end2 = System.nanoTime();

		final BigDecimal duration2 = new BigDecimal(end2 - start2).divide(new BigDecimal(Math.pow(10, 9)));
		System.out.println("Build Normal    : " + duration2 + "s");
	}
}

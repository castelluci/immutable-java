package org.vince.pojo;

/**
 * Created by VINCENT on 06/05/2017.
 */
public class Pojo{

	private int    age;
	private int    size;
	private String name;

	private static       String test_1 = "test_1";
	private final        String test_3 = "test_3";
	private static final String test_2 = "test_2";

	public Pojo(){
	}

	public Pojo(final int age, final int size, final String name){
		this.age = age;
		this.size = size;
		this.name = name;
	}

	public int getAge(){
		return age;
	}

	public void setAge(final int age){
		this.age = age;
	}

	public int getSize(){
		return size;
	}

	public void setSize(final int size){
		this.size = size;
	}

	public String getName(){
		return name;
	}

	public void setName(final String name){
		this.name = name;
	}

	public static String getTest_1(){
		return test_1;
	}

	public static void setTest_1(final String test_1){
		Pojo.test_1 = test_1;
	}

	public String getTest_3(){
		return test_3;
	}

	public static String getTest_2(){
		return test_2;
	}
}
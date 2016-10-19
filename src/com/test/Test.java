package com.test;
public class Test {
	private void test1(Double [][] a) {
//		a.age = 20;
		a[0][0]=111.0;
		System.out.println("test1方法中的age=" + a[0][0]);
	}

	public static void main(String[] args) {  
		Test t = new Test();  
//		A a = new A();  
//		a.getAge()[0][0] = 100; 
		Double [][]a = {{1.0,2.0}, {3.0,4.0,5.0,6.0}};;
		t.test1(a);  
		System.out.println("main方法中的age="+a[0][0]);  
	}
}
//class A {
//
//	public int age[][];
//
//	public int[][] getAge() {
//		return age;
//	}
//
//	public void setAge(int[][] age) {
//		this.age = age;
//	}
//}


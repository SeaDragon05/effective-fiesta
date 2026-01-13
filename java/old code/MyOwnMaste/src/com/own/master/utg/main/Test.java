package com.own.master.utg.main;

public class Test {
	public static void main(String[] args) {
		UTG gen = new UTG();
		gen.setFileDir("input/beemoviescript.txt");
		gen.compileData();
		System.out.println(gen.generate(1));
	}
}

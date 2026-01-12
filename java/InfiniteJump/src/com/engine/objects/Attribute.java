package com.engine.objects;

public class Attribute<T> {
	private String varName;
	private int uniformLocation;
	private T data;
	public Attribute(String n, int l, T d) {
		this.varName = n;
		this.uniformLocation = l;
		this.data = d;
	}
	public String getVarName() {
		return varName;
	}
	public int getUniformLocation() {
		return uniformLocation;
	}
	public T getData() {
		return data;
	}
	public void setData(T d) {
		this.data = d;
	}
}

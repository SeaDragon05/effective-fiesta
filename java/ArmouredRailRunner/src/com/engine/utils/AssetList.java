package com.engine.utils;

import java.util.ArrayList;
import java.util.List;

public class AssetList {
	private List<String> URLList = new ArrayList<String>() {
		private static final long serialVersionUID = -3589446349691243642L;
		@Override
		public String toString() {
			return "URL List";
		}
	};
	private List<Integer> pointers = new ArrayList<Integer>();

	public List<String> getURLList() {
		return URLList;
	}

	public List<Integer> getPointers() {
		return pointers;
	}

}

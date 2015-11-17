package com.shefron.module.json;

import java.util.List;
import java.util.Map;

class BeanA {

	Map<String, Bean> val;
	Bean b;
	List<Bean> c;

	public Map<String, Bean> getVal() {
		return this.val;
	}

	public void setVal(Map<String, Bean> val) {
		this.val = val;
	}

	public void setX(Map<String, Bean> x) {
		this.val = x;
	}

	public String toString() {
		return "BeanA [val=" + this.val + ", b: " + this.b + ", c: " + this.c + "]";
	}

}

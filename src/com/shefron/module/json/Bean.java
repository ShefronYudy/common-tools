package com.shefron.module.json;

import java.util.Date;

class Bean {
	int i;
	boolean b;
	String s;
	float f;
	Date d;
	public void setX(Date d) {
		this.d = d;
	}

	public String toString() {
		return "Bean [i=" + this.i + ", b=" + this.b + ", s=" + this.s + ", f=" + this.f + ", d=" + this.d + "]";
	}

}

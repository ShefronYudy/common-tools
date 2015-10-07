package org.shefron.designpattern.create.prototype;

/**
 * ǳ����
 * 
 * @author a
 * 
 */
public class ShallowPrototype implements Cloneable {

	@Override
	protected Object clone() throws CloneNotSupportedException {
		ShallowPrototype prototype = null;
		try {
			prototype = (ShallowPrototype) super.clone();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prototype;
	}

}

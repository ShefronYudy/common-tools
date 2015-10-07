package org.shefron.designpattern.create.prototype;

import java.util.ArrayList;

/**
 * 深拷贝
 * 
 * @author a
 * 
 */
public class DeepPrototype implements Cloneable {
	// 其中java.util.List未实现Cloneable,不支持克隆因此采用以下声明
	@SuppressWarnings("rawtypes")
	private ArrayList list = new ArrayList();

	@SuppressWarnings("rawtypes")
	@Override
	protected Object clone() throws CloneNotSupportedException {
		DeepPrototype prototype = null;
		try {
			prototype = (DeepPrototype) super.clone();
			prototype.list = (ArrayList) this.list.clone();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prototype;
	}

}

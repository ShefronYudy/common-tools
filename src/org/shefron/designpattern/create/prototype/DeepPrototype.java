package org.shefron.designpattern.create.prototype;

import java.util.ArrayList;

/**
 * ���
 * 
 * @author a
 * 
 */
public class DeepPrototype implements Cloneable {
	// ����java.util.Listδʵ��Cloneable,��֧�ֿ�¡��˲�����������
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

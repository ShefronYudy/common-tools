package org.shefron.designpattern.structure.flightWeight;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author a
 * 
 */
public class ShoeFWFactory {
	// ������
	private List<LeatherShoe> shoeFactory = new ArrayList<LeatherShoe>();

	// ����
	private static class ShoeFWFactoryHandler {
		static final ShoeFWFactory INSTANCE = new ShoeFWFactory();
	}

	public ShoeFWFactory instance() {
		return ShoeFWFactoryHandler.INSTANCE;
	}

	// ˽�й���
	private ShoeFWFactory() {
	}

	/**
	 * ��������
	 * 
	 * @param color
	 *            ��ɫ
	 * @param size
	 *            ����
	 * @param producePlace
	 *            ����
	 * @param position
	 *            ���λ��
	 * @return
	 */
	public LeatherShoe getShoe(Color color, int size, String producePlace,
			String position) {
		for (LeatherShoe shoe : shoeFactory) {
			if (shoe.color.equals(color)
					&& shoe.producePlace.equals(producePlace)
					&& shoe.size == size) {
				shoe.setPosition(position);
				return shoe;
			}
		}
		LeatherShoe leatherShoe = new LeatherShoe(color, size, producePlace,
				position);
		shoeFactory.add(leatherShoe);
		return leatherShoe;
	}
}

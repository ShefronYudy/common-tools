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
	// 共享集合
	private List<LeatherShoe> shoeFactory = new ArrayList<LeatherShoe>();

	// 单例
	private static class ShoeFWFactoryHandler {
		static final ShoeFWFactory INSTANCE = new ShoeFWFactory();
	}

	public ShoeFWFactory instance() {
		return ShoeFWFactoryHandler.INSTANCE;
	}

	// 私有构造
	private ShoeFWFactory() {
	}

	/**
	 * 工厂方法
	 * 
	 * @param color
	 *            颜色
	 * @param size
	 *            尺码
	 * @param producePlace
	 *            产地
	 * @param position
	 *            存放位置
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

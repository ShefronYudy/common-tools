package org.shefron.designpattern.create.builder;

/**
 * 需求分析师
 * 
 * @author a
 * 
 */
public class Analyst {
	private ICoder[] coder = null;

	public Analyst(ICoder... coders) {
		this.coder = coders;
	}

	// 去开发吧
	public void doIt() {
		this.buildModuleA();
		this.buildModuleB();
		this.buildModuleC();
		this.buildModuleCore();
	}

	public void buildModuleA() {
		coder[0].buildModuleA();
	}

	public void buildModuleB() {
		coder[0].buildModuleB();
	}

	public void buildModuleC() {
		coder[0].buildModuleC();
	}

	public void buildModuleCore() {
		coder[1].buildModuleCore();
	}

}

package org.shefron.designpattern.create.builder;

/**
 * Èí¼þ²úÆ·
 * 
 * @author a
 * 
 */
public class Product {

	private String moduleA = null;
	private String moduleB = null;
	private String moduleC = null;
	private String moduleCore = null;

	public String getModuleCore() {
		return moduleCore;
	}

	public void setModuleCore(String moduleCore) {
		this.moduleCore = moduleCore;
	}

	public String getModuleA() {
		return moduleA;
	}

	public void setModuleA(String moduleA) {
		this.moduleA = moduleA;
	}

	public String getModuleB() {
		return moduleB;
	}

	public void setModuleB(String moduleB) {
		this.moduleB = moduleB;
	}

	public String getModuleC() {
		return moduleC;
	}

	public void setModuleC(String moduleC) {
		this.moduleC = moduleC;
	}

	public String toString() {
		return "[moduleA=" + this.moduleA + ",moduleB=" + this.moduleB
				+ ",moduleC=" + this.moduleC + ",moduleCore=" + this.moduleCore
				+ "]";
	}

}

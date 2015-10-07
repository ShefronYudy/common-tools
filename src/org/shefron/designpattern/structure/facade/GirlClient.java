package org.shefron.designpattern.structure.facade;

/**
 * ≈Æ≈Û”—
 * 
 * @author a
 * 
 */
public class GirlClient {
	private BoyFacade myBoy = null;

	public GirlClient() {
		myBoy = new BoyFacade();
	}

	public GirlClient(BoyFacade boy) {
		this.myBoy = boy;
	}

	public void travel() {
		myBoy.travel();
	}

}

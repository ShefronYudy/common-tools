package org.shefron.fc.thread.threadlocal;

import java.util.HashMap;

public class RegisterManager {

	private ThreadLocal<HashMap> localRegister = new ThreadLocal<HashMap>();
	private static RegisterManager manager = new RegisterManager();

	private RegisterManager() {
	}

	public static RegisterManager getRegisterManager() {
		return manager;
	}

	public void startRegister(HashMap infoMap) {
		this.localRegister.set(infoMap);
	}

	public HashMap getRegister() {
		return this.localRegister.get();
	}

}

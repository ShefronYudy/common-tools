package org.shefron.fc.thread;

import java.util.HashSet;
import java.util.Set;

/**
 * select * from iptca_cm_probe_info t; select * from iptca_cm_probe_active_info
 * t;
 * 
 * @author a
 * 
 */
public class ActiveProbe {

	private static ActiveProbe instance = new ActiveProbe();

	private Set<ProbeObj> probeList = new HashSet<ProbeObj>();

	private ActiveProbe() {
		ProbeObj obj = new ProbeObj("10.32.1.101", System.currentTimeMillis());

	}

	public synchronized static ActiveProbe getInstance() {
		if (instance == null) {
			instance = new ActiveProbe();
			return instance;
		}
		return instance;
	}

	private class ProbeObj {
		private String ip = "";
		private long systime = 0l;

		public ProbeObj(String ip, long systime) {
			this.ip = ip;
			this.systime = systime;
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

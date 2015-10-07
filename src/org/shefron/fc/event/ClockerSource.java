package org.shefron.fc.event;

import java.util.Enumeration;
import java.util.Vector;

public class ClockerSource {

	private Vector<ClockerListener> repository = new Vector<ClockerListener>();
	protected ClockerListener listener = null;

	public void addListener(ClockerListener listener) {
		repository.add(listener);
	}

	public void timeUp() {
		Enumeration<ClockerListener> enums = repository.elements();
		while (enums.hasMoreElements()) {
			listener = enums.nextElement();
			listener.tickSound(new ClockerEvent(this));
		}

	}

}

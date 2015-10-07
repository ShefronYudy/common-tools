package org.shefron.fc.event;

import java.util.EventListener;

public interface ClockerListener extends EventListener {

	public void tickSound(ClockerEvent event);

}

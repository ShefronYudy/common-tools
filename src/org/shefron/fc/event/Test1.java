package org.shefron.fc.event;

public class Test1 {

	public static void main(String[] args) {
		ClockerSource source = new ClockerSource();
		source.addListener(new ClockerListener() {

			public void tickSound(ClockerEvent event) {
				System.out.println("ÄÖÖÓÏìÁË");
			}

		});
		source.timeUp();
	}

}

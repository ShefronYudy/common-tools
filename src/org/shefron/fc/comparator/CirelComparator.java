package org.shefron.fc.comparator;

import java.util.Comparator;

public class CirelComparator implements Comparator<CirelModel> {

	public int compare(CirelModel o1, CirelModel o2) {

		return o1.getSearchcode().compareToIgnoreCase(o2.getSearchcode());

	}

}

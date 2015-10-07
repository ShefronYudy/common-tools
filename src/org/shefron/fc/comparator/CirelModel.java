package org.shefron.fc.comparator;

public class CirelModel {

	private String searchcode;
	private String cireltype;
	private String cirelsubtype;
	private String cirelsearchcode;

	public CirelModel(String searchcode, String cireltype, String cirelsubtype,
			String cirelsearchcode) {

		this.searchcode = searchcode;
		this.cireltype = cireltype;
		this.cirelsubtype = cirelsubtype;
		this.cirelsearchcode = cirelsearchcode;

	}

	public String getSearchcode() {
		return searchcode;
	}

	public String getCireltype() {
		return cireltype;
	}

	public String getCirelsubtype() {
		return cirelsubtype;
	}

	public String getCirelsearchcode() {
		return cirelsearchcode;
	}

	public boolean equals(CirelModel oriObj) {

		return (this.searchcode.equals(oriObj.searchcode)
				&& this.cireltype.equals(oriObj.cireltype)
				&& this.cirelsubtype.equals(oriObj.cirelsubtype) && this.cirelsearchcode
				.equals(oriObj.cirelsearchcode));

	}

	public static void main(String[] args) {
		CirelModel cirel1 = new CirelModel("BS-BSP-OSC-000011", "关联",
				"Depends", "AP-CRM-ZZZ-000001");
		CirelModel cirel2 = new CirelModel("BS-BSP-OSC-000011", "关联",
				"Depends", "AP-CRM-ZZZ-000001");

		System.out.println(cirel1.equals(cirel2));

	}

}

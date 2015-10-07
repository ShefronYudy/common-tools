package org.shefron.designpattern.structure.decorator;

public class Button extends Component {

	@Override
	public void operation() {
		click();
	}

	private void click() {
		System.out.println("��ť����ˣ�");
	}

}

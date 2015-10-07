package org.shefron.designpattern.structure.decorator;

public class SoundDecorator extends Decorator {

	public SoundDecorator(Component component) {
		super(component);
	}

	public void operation() {
		super.operation();
		addSoundWhenClick();
	}

	public void addSoundWhenClick() {
		System.out.println("dida !!!");
	}

}

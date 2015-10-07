package org.shefron.designpattern.behaviour.mediator;

//Í¬ÊÂ
public abstract class Colleague {

	public Mediator _mediator = null;

	public Colleague(Mediator mediator) {
		this._mediator = mediator;
	}

	protected abstract void doSth();

}

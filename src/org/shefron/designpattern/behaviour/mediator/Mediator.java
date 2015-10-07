package org.shefron.designpattern.behaviour.mediator;

//中介者:
//改进可各保留一份同事信息（或采用集合）
public abstract class Mediator {

	protected abstract Colleague findZhuguan();

	protected abstract Colleague findZhuanyuan();

	protected abstract Colleague findWenyuan();

	protected abstract Colleague findManager();

}

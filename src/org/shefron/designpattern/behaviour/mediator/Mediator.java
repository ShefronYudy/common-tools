package org.shefron.designpattern.behaviour.mediator;

//�н���:
//�Ľ��ɸ�����һ��ͬ����Ϣ������ü��ϣ�
public abstract class Mediator {

	protected abstract Colleague findZhuguan();

	protected abstract Colleague findZhuanyuan();

	protected abstract Colleague findWenyuan();

	protected abstract Colleague findManager();

}

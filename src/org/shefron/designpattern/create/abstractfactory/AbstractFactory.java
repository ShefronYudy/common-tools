package org.shefron.designpattern.create.abstractfactory;

public abstract class AbstractFactory {

	abstract AbstractProduct createProduct(String name);

	abstract Wall createWall();

	abstract Room createRoom();

	abstract Door createDoor();

}

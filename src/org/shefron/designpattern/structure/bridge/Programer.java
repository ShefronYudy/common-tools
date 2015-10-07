package org.shefron.designpattern.structure.bridge;

/**
 * 桥接模式：程序员想朝底层和web两个方面进行开发
 * 
 * @author a
 * 
 */
public abstract class Programer {

	protected WebProgramer web;

	abstract void readBooks();

}

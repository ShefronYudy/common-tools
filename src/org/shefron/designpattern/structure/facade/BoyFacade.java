package org.shefron.designpattern.structure.facade;

/**
 * ÄÐÅóÓÑ
 * 
 * @author a
 * 
 */
public class BoyFacade {
	private Flight flight = null;
	private Hotel hotel = null;
	private Place place = null;
	private Restourant restourant = null;

	public BoyFacade() {
		this.flight = new Flight();
		this.hotel = new Hotel();
		this.place = new Place();
		this.restourant = new Restourant();
	}

	public void travel() {
		this.flight.flight();
		this.hotel.sleep();
		this.place.play();
		this.restourant.eating();
	}

}

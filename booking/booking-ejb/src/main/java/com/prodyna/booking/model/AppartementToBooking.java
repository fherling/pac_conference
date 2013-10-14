package com.prodyna.booking.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "appTobooking")
public class AppartementToBooking implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3852954718439181616L;

	@Id
	@GeneratedValue
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Appartment getAppartment() {
		return appartment;
	}

	public void setAppartment(Appartment appartment) {
		this.appartment = appartment;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	private Appartment appartment;

	private Booking booking;
	
}

package com.prodyna.booking.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class AppartementToBookingId  implements Serializable{
	
	
	private static final long serialVersionUID = -6911011528172641266L;

	private Appartment appartment;

	private Booking booking;

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public Appartment getAppartment() {
		return appartment;
	}

	public void setAppartment(Appartment appartment) {
		this.appartment = appartment;
	}

}

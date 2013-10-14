package com.prodyna.booking.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;

@Entity
@XmlRootElement
@Table(name = "booking")
@NamedQuery(name="isAvailable", query="select b from Booking b where b.appartment.id = :appartmentid and b.arrival <= :arrival and b.departure >= :departure")
public class Booking implements Serializable{

	private static final long serialVersionUID = -6083610887620986160L;
	@Id
    @GeneratedValue
    private Long id;
	
	@NotNull
//	@Max(value=100, message="Name zu lang")
	private String name;
	
	@Email(message="Email nicht korrekt!")
	private String email;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
//	@NotNull(message="Kein Appartment zur Buchung zugeordnet")
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=Appartment.class)
	@JoinColumn(name="appartment_id", referencedColumnName="id")
	private Appartment appartment;
	
	@NotNull
	@Future
	@javax.persistence.Temporal(TemporalType.DATE)
	private Date arrival;
	
	


	@NotNull
	@Future
	@javax.persistence.Temporal(TemporalType.DATE)
	private Date departure;
	
//	@NotNull
//	@Min(value=1, message= "Mindestanzahl Personen ist 1.")
	private Integer numberOfPerson;
	
	
	public Integer getNumberOfPerson() {
		return numberOfPerson;
	}


	public void setNumberOfPerson(Integer numberOfPerson) {
		this.numberOfPerson = numberOfPerson;
	}
	
	public Appartment getAppartment() {
		return appartment;
	}


	public void setAppartment(Appartment appartment) {
		this.appartment = appartment;
	}


	public Date getArrival() {
		return arrival;
	}


	public void setArrival(Date arriaval) {
		this.arrival = arriaval;
	}


	public Date getDeparture() {
		return departure;
	}


	public void setDeparture(Date departure) {
		this.departure = departure;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getName() {

		return name;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Booking [id=" + id + ", name=" + name + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", appartment=" + appartment
				+ ", arrival=" + arrival + ", departure=" + departure
				+ ", numberOfPerson=" + numberOfPerson + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
//		result = prime * result
//				+ ((appartment == null) ? 0 : appartment.hashCode());
		result = prime * result + ((arrival == null) ? 0 : arrival.hashCode());
		result = prime * result
				+ ((departure == null) ? 0 : departure.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((numberOfPerson == null) ? 0 : numberOfPerson.hashCode());
		result = prime * result
				+ ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Booking other = (Booking) obj;
//		if (appartment == null) {
//			if (other.appartment != null)
//				return false;
//		} else if (!appartment.equals(other.appartment))
//			return false;
		if (arrival == null) {
			if (other.arrival != null)
				return false;
		} else if (!arrival.equals(other.arrival))
			return false;
		if (departure == null) {
			if (other.departure != null)
				return false;
		} else if (!departure.equals(other.departure))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (numberOfPerson == null) {
			if (other.numberOfPerson != null)
				return false;
		} else if (!numberOfPerson.equals(other.numberOfPerson))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		return true;
	}

	
}

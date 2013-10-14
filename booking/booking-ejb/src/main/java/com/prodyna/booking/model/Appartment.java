package com.prodyna.booking.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "appartment", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Appartment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3968548735832113726L;

	@Id
    @GeneratedValue
    private Long id;
	
	@NotNull
	private String name;
	
	@NotNull
	private int capacity;
	
	
//	@OneToMany(fetch=FetchType.LAZY, mappedBy="appartment")
//	private List<Booking> bookings;
	
	
//	public List<Booking> getBookings() {
//		return bookings;
//	}
//
//
//	public void setBookings(List<Booking> bookings) {
//		this.bookings = bookings;
//	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getCapacity() {
		return capacity;
	}


	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}


	@Override
	public String toString() {
		return "Appartment [id=" + id + ", name=" + name + ", capacity="
				+ capacity + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + capacity;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Appartment other = (Appartment) obj;
		if (capacity != other.capacity)
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
		return true;
	}


	
}

package com.prodyna.conference.service.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="room")
@SequenceGenerator(name="room_seq", initialValue=100, allocationSize=10)
public class Room implements Serializable{
	private static final long serialVersionUID = 7389968794081034046L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="room_seq")
	private Long id;
	
	@NotNull
	@NotEmpty
	private String name;
	
	@Min(value=1)
	private int capacity;
	
	@Version
	private Timestamp updateTimeStamp;

	

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

	public Timestamp getUpdateTimeStamp() {
		return updateTimeStamp;
	}

	public void setUpdateTimeStamp(Timestamp updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + capacity;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((updateTimeStamp == null) ? 0 : updateTimeStamp.hashCode());
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
		Room other = (Room) obj;
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
		if (updateTimeStamp == null) {
			if (other.updateTimeStamp != null)
				return false;
		} else if (!updateTimeStamp.equals(other.updateTimeStamp))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", name=" + name + ", capacity=" + capacity
				+ ", updateTimeStamp=" + updateTimeStamp + "]";
	}

}

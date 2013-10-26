package com.prodyna.conference.service.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

/**
 * @author fherling
 * 
 */
@Entity
@SequenceGenerator(name = "speaker_seq", initialValue = 100, allocationSize = 10)
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = { "name", "firstname" }),
		@UniqueConstraint(columnNames = { "email" }) })
@NamedQuery(name = BusinessQueries.GET_ALL_SPEAKERS, query = "select a from com.prodyna.conference.service.model.Speaker a")
public class Speaker extends BaseEntity {
	private static final long serialVersionUID = -4223667756806417808L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "speaker_seq")
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String firstName;

	@NotNull
	private String description;

	@Email
	private String email;

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String desription) {
		this.description = desription;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Speaker other = (Speaker) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
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

	@Override
	public String toString() {
		return "Speaker [id=" + id + ", name=" + name + ", firstName="
				+ firstName + ", description=" + description + ", email=" + email
				+ "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

/**
 * 
 */
package com.prodyna.conference.service.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author fherling
 * 
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "name",
		"start", "duration" }) })
@SequenceGenerator(name = "talk_seq", initialValue = 100, allocationSize = 10)
public class Talk extends BaseEntity {
	private static final long serialVersionUID = -841359591361952722L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "talk_seq")
	private Long id;

	@NotNull
	@Column(name="name")
	private String name;

	@NotNull
	private String description;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date start;

	@Min(value = 1)
	private long duration;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String desription) {
		this.description = desription;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date startTime) {
		this.start = startTime;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public TimeRange calcTimeRange(){
		TimeRange range = new TimeRange(getStart(), getDuration());
		
		return range;
	}

	@Override
	public String toString() {
		return "Talk [id=" + id + ", name=" + name + ", description="
				+ description + ", start=" + start + ", duration=" + duration
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (duration ^ (duration >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
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
		Talk other = (Talk) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (duration != other.duration)
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
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}

}

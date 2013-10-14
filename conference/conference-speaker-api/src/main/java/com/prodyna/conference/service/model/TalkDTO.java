package com.prodyna.conference.service.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.prodyna.conference.service.model.validation.ValidTalk;

@ValidTalk
public class TalkDTO implements Serializable{
	
	private static final long serialVersionUID = 8366348636781334583L;

	private Long id;
	
	@NotNull
	private Set<Speaker> speakers;
	
	@NotNull
	private Room room;
	
	@NotNull
	@NotEmpty
	private String name;
	
	@NotNull
	@NotEmpty
	private String description;

	@Min(value=1)
	private long durationInSeconds;
	
	@NotNull
	private Date start;
	
	@NotNull
	private Date end;
	
	private Timestamp updateTimeStamp;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
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

	public void setDescription(String desc) {
		this.description = desc;
	}

	public Timestamp getUpdateTimeStamp() {
		return updateTimeStamp;
	}

	public void setUpdateTimeStamp(Timestamp updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ (int) (durationInSeconds ^ (durationInSeconds >>> 32));
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		result = prime * result + ((speakers == null) ? 0 : speakers.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
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
		TalkDTO other = (TalkDTO) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (durationInSeconds != other.durationInSeconds)
			return false;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
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
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		if (speakers == null) {
			if (other.speakers != null)
				return false;
		} else if (!speakers.equals(other.speakers))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
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
		return "Talk [id=" + id + ", room=" + room + ", name=" + name
				+ ", description=" + description + ", durationInSeconds="
				+ durationInSeconds + ", start=" + start + ", end=" + end
				+ ", updateTimeStamp=" + updateTimeStamp + "]";
	}

	public long getDurationInSeconds() {
		return durationInSeconds;
	}

	public void setDurationInSeconds(long duration) {
		this.durationInSeconds = duration;
	}

	public Set<Speaker> getSpeakers() {
		return speakers;
	}

	public void setSpeakers(Set<Speaker> speaker) {
		this.speakers = speaker;
	}

}

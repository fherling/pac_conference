package com.prodyna.conference.service.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.prodyna.conference.service.model.validation.ValidTalk;

@ValidTalk
public class TalkDTO implements Serializable{
	
	private static final long serialVersionUID = 8366348636781334583L;

	@NotNull
	private Set<Speaker> speakers = new HashSet<Speaker>();
	
	@NotNull
	private Talk talk;

	
	public TalkDTO(Talk pTalk){
		if( null == pTalk){
			throw new NullPointerException("The pTalk parameter is NULL");
		}
		this.talk = pTalk;
	}
	
	public Talk getTalk(){
		return this.talk;
	}
	
	public Long getId() {
		return talk.getId();
	}

	public Room getRoom() {
		return talk.getRoom();
	}

	public void setRoom(Room room) {
		talk.setRoom(room);
	}

	public String getName() {
		return talk.getName();
	}

	public void setName(String name) {
		talk.setName(name);
	}

	public String getDescription() {
		return talk.getDescription();
	}

	public void setDescription(String desc) {
		talk.setDescription(desc);
	}

	public Timestamp getUpdateTimeStamp() {
		return talk.getUpdateTimeStamp();
	}

	public void setUpdateTimeStamp(Timestamp updateTimeStamp) {
		talk.setUpdateTimeStamp(updateTimeStamp);
	}

	public Date getStart() {
		return talk.getStart();
	}

	public void setStart(Date start) {
		talk.setStart(start);
	}

	public Date getEnd() {
		return talk.getEnd();
	}

	public void setEnd(Date end) {
		talk.setEnd(end);
	}

	public long getDurationInMinutes() {
		return talk.getDurationInMinutes();
	}

	public void setDurationInMinutes(long duration) {
		talk.setDurationInMinutes(duration);
	}

	public Set<Speaker> getSpeakers() {
		return speakers;
	}

	public void setId(Long id) {
		this.talk.setId(id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((talk == null) ? 0 : talk.hashCode());
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
		if (talk == null) {
			if (other.talk != null)
				return false;
		} else if (!talk.equals(other.talk))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TalkDTO [talk=" + talk + "]";
	}

	public void setSpeakers(Set<Speaker> speakers) {
		this.speakers = speakers;
	}
	
	

	

	
}

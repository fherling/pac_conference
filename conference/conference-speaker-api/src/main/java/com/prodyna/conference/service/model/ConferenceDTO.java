package com.prodyna.conference.service.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

public class ConferenceDTO implements Serializable {

	private static final long serialVersionUID = -6398412509636027295L;
	
	@NotNull(message="Conference not set")
	private Conference conference;
	
	
//	@NotNull(message="No talk available for conference")
	
	private Set<TalkDTO> talks = new HashSet<TalkDTO>();
	
	
	public ConferenceDTO(Conference pConference, Set<TalkDTO> pTalks){
		if( null == pConference){
			throw new NullPointerException("Conference parameter is NULL");
		}
		this.conference = pConference;
		
		if( null != pTalks){
			this.talks.addAll(pTalks);
		}
			
	}
	
	public void setConference(Conference pConference){
		this.conference = pConference;
	}
	
	public Conference getConference(){
		return conference;
	}

	public Set<TalkDTO> getTalks() {
		return talks;
	}

	public void setTalks(Set<TalkDTO> talks) {
		this.talks = talks;
	}

	public Long getId() {
		return conference.getId();
	}

	public String getName() {
		return conference.getName();
	}

	public void setName(String name) {
		conference.setName(name);
	}

	public String getDescription() {
		return conference.getDescription();
	}

	public void setDescription(String desc) {
		conference.setDescription(desc);
	}

	public Timestamp getUpdateTimeStamp() {
		return conference.getUpdateTimeStamp();
	}

	public void setUpdateTimeStamp(Timestamp updateTimeStamp) {
		conference.setUpdateTimeStamp(updateTimeStamp);
	}

	public Date getStart() {
		return conference.getStart();
	}

	public void setStart(Date start) {
		conference.setStart(start);
	}

	public Date getEnd() {
		return conference.getEnd();
	}

	public void setEnd(Date end) {
		conference.setEnd(end);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((conference == null) ? 0 : conference.hashCode());
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
		ConferenceDTO other = (ConferenceDTO) obj;
		if (conference == null) {
			if (other.conference != null)
				return false;
		} else if (!conference.equals(other.conference))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ConferenceDTO [conference=" + conference + ", talks=" + talks
				+ "]";
	}
}

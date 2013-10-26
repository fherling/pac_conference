/**
 * 
 */
package com.prodyna.conference.service.model;

import java.util.HashMap;
import java.util.List;

/**
 * @author fherling
 *
 */
public class WizardDTO {
	
	private Conference conference;
	
	private List<Talk> talks;
	
	private HashMap<Talk, List<Speaker>> speakerForTalks = new HashMap<Talk, List<Speaker>>();
	
	private HashMap<Talk, Room> roomForTalk = new HashMap<Talk, Room>();

	public Conference getConference() {
		return conference;
	}

	public void setConference(Conference conference) {
		this.conference = conference;
	}

	public List<Talk> getTalks() {
		return talks;
	}

	public void setTalks(List<Talk> talks) {
		this.talks = talks;
	}

	public HashMap<Talk, List<Speaker>> getSpeakerForTalks() {
		return speakerForTalks;
	}

	public void setSpeakerForTalks(HashMap<Talk, List<Speaker>> speakerForTalks) {
		this.speakerForTalks = speakerForTalks;
	}

	public HashMap<Talk, Room> getRoomForTalk() {
		return roomForTalk;
	}

	public void setRoomForTalk(HashMap<Talk, Room> roomForTalk) {
		this.roomForTalk = roomForTalk;
	}

}

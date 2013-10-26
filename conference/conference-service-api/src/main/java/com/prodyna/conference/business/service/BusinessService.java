package com.prodyna.conference.business.service;

import com.prodyna.conference.service.model.Room;
import com.prodyna.conference.service.model.Speaker;
import com.prodyna.conference.service.model.TimeRange;
import com.prodyna.conference.service.model.WizardDTO;

public interface BusinessService {

	void save(WizardDTO dto);

	boolean isRoomAvailable(Room room, TimeRange range);

	boolean isSpeakerAvailable(Speaker speaker, TimeRange range);
	
}

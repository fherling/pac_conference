/**
 * 
 */
package com.prodyna.conference.service.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 * @author fherling
 *
 */
@Entity
@SequenceGenerator(name="talkroom_seq", initialValue=100, allocationSize=10)
public class TalkRoom {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="talkroom_seq")
	private Long id;

	@ManyToOne
	@JoinColumn(name="room_id", referencedColumnName="id")
	private Room room;
	
	@ManyToOne
	@JoinColumn(name="talk_id", referencedColumnName="id")
	private Talk talk;

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

	public Talk getTalk() {
		return talk;
	}

	public void setTalk(Talk talk) {
		this.talk = talk;
	}
	
}

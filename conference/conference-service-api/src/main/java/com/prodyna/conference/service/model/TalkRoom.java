/**
 * 
 */
package com.prodyna.conference.service.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author fherling
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "talk_id",
"room_id" }) })
@SequenceGenerator(name="talkroom_seq", initialValue=100, allocationSize=10)
public class TalkRoom implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4511334984503173072L;


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

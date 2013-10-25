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
@SequenceGenerator(name="talkspeaker_seq", initialValue=100, allocationSize=10)
public class TalkSpeaker {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="talkspeaker_seq")
	private Long id;

	@ManyToOne
	@JoinColumn(name="speaker_id", referencedColumnName="id")
	private Speaker speaker;
	
	@ManyToOne
	@JoinColumn(name="talk_id", referencedColumnName="id")
	private Talk talk;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Speaker getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Speaker speaker) {
		this.speaker = speaker;
	}

	public Talk getTalk() {
		return talk;
	}

	public void setTalk(Talk talk) {
		this.talk = talk;
	}
	
}

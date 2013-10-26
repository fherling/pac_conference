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
@SequenceGenerator(name="conferencetalk_seq", initialValue=100, allocationSize=10)
public class ConferenceTalk {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="conferencetalk_seq")
	private Long id;

	
	@ManyToOne
	@JoinColumn(name="conference_id", referencedColumnName="id")
	private Conference conference;
	
	@ManyToOne
	@JoinColumn(name="talk_id", referencedColumnName="id")
	private Talk talk;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Conference getConference() {
		return conference;
	}

	public void setConference(Conference conference) {
		this.conference = conference;
	}

	public Talk getTalk() {
		return talk;
	}

	public void setTalk(Talk talk) {
		this.talk = talk;
	}
	
}

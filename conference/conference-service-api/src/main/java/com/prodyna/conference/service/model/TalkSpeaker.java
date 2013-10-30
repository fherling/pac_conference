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
		"speaker_id" }) })
@SequenceGenerator(name = "talkspeaker_seq", initialValue = 100, allocationSize = 10)
public class TalkSpeaker implements Serializable {

	private static final long serialVersionUID = 3998049696462626428L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "talkspeaker_seq")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "speaker_id", referencedColumnName = "id")
	private Speaker speaker;

	@ManyToOne
	@JoinColumn(name = "talk_id", referencedColumnName = "id")
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

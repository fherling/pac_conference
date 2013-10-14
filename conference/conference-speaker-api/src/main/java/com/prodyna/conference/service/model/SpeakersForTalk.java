package com.prodyna.conference.service.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "speakersfortalk")
public class SpeakersForTalk implements Serializable{

	private static final long serialVersionUID = 2625345718390782047L;

	@Id
	private Long id;
	
	@ManyToMany(fetch=FetchType.EAGER,targetEntity=Speaker.class)
	@NotNull
	private Set<Speaker> speakers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Speaker> getSpeakers() {
		return speakers;
	}

	public void setSpeakers(Set<Speaker> speakers) {
		this.speakers = speakers;
	}
	
	
}

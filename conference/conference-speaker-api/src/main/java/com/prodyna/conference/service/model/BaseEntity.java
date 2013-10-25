package com.prodyna.conference.service.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class BaseEntity implements Serializable{

	private static final long serialVersionUID = -6636523044945035652L;
	
	
	private Timestamp insertTimestamp;
	
	
	@Version
	private Timestamp lastUpdateTimestamp;

	public BaseEntity() {
		super();
	}

	public Timestamp getInsertTimestamp() {
		return insertTimestamp;
	}

	public void setInsertTimestamp(Timestamp insertTimestamp) {
		this.insertTimestamp = insertTimestamp;
	}

	public Timestamp getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimestamp(Timestamp lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((insertTimestamp == null) ? 0 : insertTimestamp.hashCode());
		result = prime
				* result
				+ ((lastUpdateTimestamp == null) ? 0 : lastUpdateTimestamp
						.hashCode());
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
		BaseEntity other = (BaseEntity) obj;
		if (insertTimestamp == null) {
			if (other.insertTimestamp != null)
				return false;
		} else if (!insertTimestamp.equals(other.insertTimestamp))
			return false;
		if (lastUpdateTimestamp == null) {
			if (other.lastUpdateTimestamp != null)
				return false;
		} else if (!lastUpdateTimestamp.equals(other.lastUpdateTimestamp))
			return false;
		return true;
	}

}
/**
 * 
 */
package com.prodyna.conference.service.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fherling
 * 
 */
public class AlreadyAssignedException extends RuntimeException {
	private static final long serialVersionUID = 8437646372208110748L;

	private Object ojbToAssign = null;
	private List<Object> assignedTo = new ArrayList<Object>();

	public AlreadyAssignedException(String arg0) {
		super(arg0);
	}

	public AlreadyAssignedException(Object ojbToAssign, List<Object> assignedTo) {

		this.ojbToAssign = ojbToAssign;
		this.assignedTo.addAll(assignedTo); 
		
	}

	@Override
	public String getMessage() {

		if (null == this.ojbToAssign) {
			return super.getMessage();
		} else {
			return buildOutput();
		}
	}

	private String buildOutput() {

		StringBuilder sb = new StringBuilder();

		sb.append(this.ojbToAssign.toString());
		sb.append(" is already assigned to following objects: ");
		boolean first = true;
		if (null != assignedTo && assignedTo.size() > 0) {
			for (Object assigner : this.assignedTo) {
				if (!first) {
					sb.append(", ");
				}
				first = false;
				sb.append(assigner);
			}
		}else{
			sb.append(" Info is not available");
		}

		return sb.toString();
	}

	@Override
	public String getLocalizedMessage() {
		if (null == this.ojbToAssign) {
			return super.getLocalizedMessage();
		} else {
			return buildOutput();
		}
	}

}

package com.prodyna.conference.service.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.prodyna.conference.service.model.Conference;
import com.prodyna.conference.service.model.Talk;

public class ConferenceDateValidator implements ConstraintValidator<ValidConferenceDate, Object> {

	@Override
	public void initialize(final ValidConferenceDate a) {
	}

	@Override
	public boolean isValid(final Object t, final ConstraintValidatorContext cvc) {
		try {

			Conference talk = (Conference) t;

			if( talk.getStart().after(talk.getEnd())){
				return false;
			}
			
			
			/* Validation logic goes here */
		} catch (final Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		// return false, just to make it always fail
		return true;
	}
}

package com.prodyna.conference.service.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.prodyna.conference.service.model.Talk;

public class TalkValidator implements ConstraintValidator<ValidTalk, Object> {

	@Override
	public void initialize(final ValidTalk a) {
	}

	@Override
	public boolean isValid(final Object t, final ConstraintValidatorContext cvc) {
		try {

			Talk talk = (Talk) t;

			if (null == talk.getStart() || null == talk.getEnd()) {
				System.err.println(talk);
				return true;
			}

			if (talk.getStart().after(talk.getEnd())) {
				System.err.println(talk);
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

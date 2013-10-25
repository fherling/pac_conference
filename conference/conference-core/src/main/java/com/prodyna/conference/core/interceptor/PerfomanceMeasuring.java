/**
 * 
 */
package com.prodyna.conference.core.interceptor;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

@InterceptorBinding
@Target({TYPE, METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
/**
 * @author fherling
 *
 */
public @interface PerfomanceMeasuring {

}

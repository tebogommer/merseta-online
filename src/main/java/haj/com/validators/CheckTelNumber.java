package haj.com.validators;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

// TODO: Auto-generated Javadoc
/**
 * The Interface CheckID.
 */
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = TelNumberValidator.class)
@Documented
public @interface CheckTelNumber {
	
	/**
	 * Message.
	 *
	 * @return the string
	 */
	String message() default "Cellphone and Telephione numbers cannot start with 00";

	/**
	 * Groups.
	 *
	 * @return the class[]
	 */
	Class<?>[] groups() default {};

	/**
	 * Payload.
	 *
	 * @return the class<? extends payload>[]
	 */
	Class<? extends Payload>[] payload() default {};

	/**
	 * Value.
	 *
	 * @return the ID mode
	 */
	IDMode value() default IDMode.IDCHECK;
}

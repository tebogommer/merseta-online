/*
 *	Programmer: wesley
 *	Date: 20 Jul 2017
 *	Project: WesleyUtilities
 *	Package: com.wesley.utils.csv
 *	Using JRE: 1.8.0_73
*/
package haj.com.validators.exports;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface CSVAnnotation.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SETMISFieldValidation {

	public Class<?> className() default Object.class;

	public String method() default "";

	public Class<?> paramClass() default Object.class;

	public String message() default "";

	public boolean process() default false;

	public String fieldName() default "";

	public String fieldValue() default "";

}

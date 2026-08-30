/*
 *	Programmer: wesley
 *	Date: 20 Jul 2017
 *	Project: WesleyUtilities
 *	Package: com.wesley.utils.csv
 *	Using JRE: 1.8.0_73
*/
package haj.com.annotations;

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
public @interface CSVAnnotation {

	/**
	 * Maps name to header in csv file.
	 *
	 * @return the string
	 */
	public String name() default "";

	/**
	 * Class name to map field to. e.g(java.util.Date, java.lang.String)
	 *
	 * Can map to classes if <b>com.wesley.utils.csv.CSVAnnotation.process()</b> is
	 * set to true.
	 *
	 * @return the class
	 */
	public Class<?> className() default Object.class;

	/**
	 * Date pattern if className() == java.util.Date
	 *
	 * @return the string
	 */
	public String datePattern() default "dd/MM/yyyy hh:mm";

	/**
	 * Required.
	 *
	 * @return true, if successful
	 */
	public boolean required() default false;

	/**
	 * Process.
	 *
	 * @return true, if successful
	 */
	public boolean process() default false;

	public String lookupField() default "";


	public int length() default 0;

	public String numericPattern() default "%.2f";
}

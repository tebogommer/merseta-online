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
public @interface CSVLookupAnnotation {

	public Class<?> className() default Object.class;

	public String method() default "";
	
	public Class<?> paramClass() default Object.class;
	
	public String lookupField() default "";

}

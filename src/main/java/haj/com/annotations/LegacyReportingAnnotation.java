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
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface CSVAnnotation.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(LegacyReportingAnnotations.class)
public @interface LegacyReportingAnnotation {

	public String name() default "";

	public String query();

	public String key() default "";

	public boolean runAsNative() default false;

	public boolean singleResult() default true;

	public Class<?> returnType() default Integer.class;

	public LegacyReportingParamsAnnotation[] params() default {};
}

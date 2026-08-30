package haj.com.annotations;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TechFiniumColumn {
 
	 String heading() default "";
	 String formula() default "";
	 boolean suppress() default false;
	 boolean textwrap() default false;

}





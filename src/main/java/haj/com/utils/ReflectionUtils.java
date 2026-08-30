package haj.com.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// TODO: Auto-generated Javadoc
/**
 * The Class ReflectionUtils.
 */
public class ReflectionUtils {

	/**
	 * Copy field value.
	 *
	 * @param copyFrom
	 *            the copy from
	 * @param copyTo
	 *            the copy to
	 * @param field
	 *            the field
	 * @param xField
	 *            the x field
	 */
	public static void copyFieldValue(Object copyFrom, Object copyTo, String field, Field xField) {
		try {
			xField.setAccessible(true);
			xField.set(copyTo, getFieldValue(copyFrom, field));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Copy field value.
	 *
	 * @param copyTo
	 *            the copy to
	 * @param copyToField
	 *            the copy to field
	 * @param copyFrom
	 *            the copy from
	 * @param copyFromfield
	 *            the copy fromfield
	 */
	public static void copyFieldValue(Object copyTo, String copyToField, Object copyFrom, String copyFromfield) {
		try {
			Field field = copyTo.getClass().getDeclaredField(copyToField);
			field.setAccessible(true);
			field.set(copyTo, getFieldValue(copyFrom, copyFromfield));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Copy field value.
	 *
	 * @param copyTo
	 *            the copy to
	 * @param copyToField
	 *            the copy to field
	 * @param copyFrom
	 *            the copy from
	 * @param copyFromfield
	 *            the copy fromfield
	 */
	public static void copyFieldValue(Object copyTo, Field copyToField, Object copyFrom, Field copyFromfield) {
		try {
			copyToField.setAccessible(true);
			copyToField.set(copyTo, getFieldValue(copyFrom, copyFromfield));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the field value.
	 *
	 * @param x
	 *            the x
	 * @param field
	 *            the field
	 * @return the field value
	 */
	public static Object getFieldValue(Object x, Field field) {
		try {
			field.setAccessible(true);
			return field.get(x);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the field value.
	 *
	 * @param x
	 *            the x
	 * @param fieldName
	 *            the field name
	 * @return the field value
	 */
	public static Object getFieldValue(Object x, String fieldName) {
		try {
			Field filterField = x.getClass().getDeclaredField(fieldName);
			return getFieldValue(x, filterField);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Sets the field value.
	 *
	 * @param x
	 *            the x
	 * @param field
	 *            the field
	 * @param value
	 *            the value
	 * @return the object
	 */
	public static Object setFieldValue(Object x, Field field, Object value) {
		try {
			field.setAccessible(true);
			field.set(x, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Sets the field value.
	 *
	 * @param x
	 *            the x
	 * @param fieldName
	 *            the field name
	 * @param value
	 *            the value
	 * @return the object
	 */
	public static Object setFieldValue(Object x, String fieldName, Object value) {
		try {
			Field filterField = x.getClass().getDeclaredField(fieldName);
			setFieldValue(x, filterField, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the field method.
	 *
	 * @param x
	 *            the x
	 * @param methodName
	 *            the method name
	 * @param args
	 *            the args
	 * @return the field method
	 */
	public static Object getFieldMethod(Object x, String methodName, Object... args) {
		try {
			Method[] ms = x.getClass().getDeclaredMethods();
			Method m = x.getClass().getDeclaredMethod(methodName);
			return m.invoke(x, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * String to class.
	 *
	 * @param name
	 *            the name
	 * @return the class
	 * @throws Exception
	 *             the exception
	 */
	public static Class<?> stringToClass(String name) throws Exception {
		return Class.forName(name);
	}

	/**
	 * String to class.
	 *
	 * @param packageName
	 *            the package name
	 * @param name
	 *            the name
	 * @return the class
	 * @throws Exception
	 *             the exception
	 */
	public static Class<?> stringToClass(String packageName, String name) throws Exception {
		return stringToClass(packageName + "." + name);
	}

	/**
	 * Gets the annotation on field.
	 *
	 * @param classs
	 *            the classs
	 * @param field
	 *            the field
	 * @param className
	 *            the class name
	 * @return the annotation on field
	 * @throws Exception
	 *             the exception
	 */
	public static Object getAnnotationOnField(Class<? extends Annotation> classs, String field, Class<?> className) throws Exception {
		return className.getDeclaredField(field).getAnnotation(classs);
	}

	/**
	 * Gets the fields with annotations.
	 *
	 * @param fields
	 *            the fields
	 * @param classs
	 *            the classs
	 * @return the fields with annotations
	 */
	public static List<Field> getFieldsWithAnnotations(Field[] fields, Class<? extends Annotation> classs) {
		return Arrays.asList(fields).stream().filter(x -> x.isAnnotationPresent(classs)).collect(Collectors.toList());
	}

	/**
	 * Gets the field type.
	 *
	 * @param field
	 *            the field
	 * @param className
	 *            the class name
	 * @return the field type
	 * @throws Exception
	 *             the exception
	 */
	public static Class<?> getFieldType(String field, String className) throws Exception {
		return getFieldType(field, stringToClass(className));
	}

	/**
	 * Gets the field type.
	 *
	 * @param field
	 *            the field
	 * @param className
	 *            the class name
	 * @return the field type
	 * @throws Exception
	 *             the exception
	 */
	public static Class<?> getFieldType(String field, Class<?> className) throws Exception {
		return className.getDeclaredField(field).getType();
	}

	/**
	 * Object to map.
	 *
	 * @param x
	 *            the x
	 * @return the map
	 */
	public static Map<String, Object> objectToMap(Object x) {
		Map<String, Object> m = new LinkedHashMap<>();
		if (x != null) {
			Field[] fields = x.getClass().getDeclaredFields();
			for (Field field : fields) {
				m.put(field.getName(), getFieldValue(x, field));
			}
		}
		return m;
	}

	/**
	 * Object list to map.
	 *
	 * @param x
	 *            the x
	 * @return the list
	 */
	public static List<Map<String, Object>> objectListToMap(List<Object> x) {
		List<Map<String, Object>> m = new ArrayList<>();
		if (x != null) {
			for (Object object : x) {
				m.add(objectToMap(object));
			}
		}
		return m;
	}

	public static Object getAnnotationOnType(Class<? extends Annotation> classs, Class<?> className) throws Exception {
		return className.getAnnotation(classs);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getAnnotationsOnType(Class<? extends Annotation> classs, Class<?> className) throws Exception {
		return (T) className.getAnnotationsByType(classs);
	}
}

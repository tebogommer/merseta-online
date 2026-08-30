/*
 *	Programmer: wesley
 *	Date: 24 Jul 2017
 *	Project: Utilities
 *	Package: com.wesley.utils.list
 *	Using JRE: 1.8.0_73
*/
package haj.com.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

// TODO: Auto-generated Javadoc
/**
 * The Class ListUtils.
 */
public class ListUtils {

	/**
	 * Filter by field.
	 *
	 * @param <T> the generic type
	 * @param fieldName the field name
	 * @param filterValue the value to filter by
	 * @param obj the obj
	 * @return the list
	 */
	public static <T> List<T> filterByField(String fieldName, Object filterValue, List<T> obj){
		if (obj != null) 
			if (obj.size() > 0)
				return obj.stream().filter(x -> ReflectionUtils.getFieldValue(x, fieldName).equals(filterValue)).collect(Collectors.toList());
		return null;
	}
	
	/**
	 * Filter by method.
	 *
	 * @param <T> the generic type
	 * @param methodName the method name
	 * @param filterValue the filter value
	 * @param obj the obj
	 * @param args the args
	 * @return the list
	 */
	public static <T> List<T> filterByMethod(String methodName, Object filterValue, List<T> obj, Object... args){
		if (obj != null) 
			if (obj.size() > 0)
				return obj.stream().filter(x -> ReflectionUtils.getFieldMethod(x, methodName, args).equals(filterValue)).collect(Collectors.toList());
		return null;
	}

	/**
	 * Distinct list.
	 *
	 * @param <T> the generic type
	 * @param obj the obj
	 * @return the list
	 */
	public static <T> List<T> distinctList(List<T> obj) {
		if (obj != null) 
			if (obj.size() > 0) 
				return obj.stream().distinct().collect(Collectors.toList());		
		return null;
	}

	/**
	 * Maps a list of objects to a list of the mapToClass param. This
	 *
	 * @param <T> the generic type
	 * @param obj the obj
	 * @param mapToClass the map to class
	 * @return the list
	 */
	public static <T> List<?> mapToClass(List<T> obj, final Class<?> mapToClass){
		if (obj != null) 
			if (obj.size() > 0)
				return obj.stream().map(temp -> {return mapObjectField(mapToClass, temp);}).collect(Collectors.toList());
		return null;
	}

	/**
	 * Maps a list of objects to a list of the specified field.
	 *
	 * @param <T> the generic type
	 * @param fieldName the field name
	 * @param obj the obj
	 * @return the list
	 */
	public static <T> List<?> fieldValueToList(String fieldName, List<T> obj){
		if (obj != null) 
			if (obj.size() > 0)
				return obj.stream().map(x -> ReflectionUtils.getFieldValue(x, fieldName)).collect(Collectors.toList());
		return null;
	}

	public static <T> List<?> fiterByObject(Object obj, List<T> objs){
		if (objs != null && obj != null) 
			if (objs.size() > 0)
				return objs.stream().map(x -> (x.equals(obj)?x:null)).collect(Collectors.toList());
		return null;
	}
	
	/**
	 * Map object field.
	 *
	 * @param mapToClass the map to class
	 * @param y the y
	 * @return the object
	 */
	private static Object mapObjectField(Class<?> mapToClass, Object y) {
		Object x = null;
		try {
			x = mapToClass.newInstance();
			Field[] yFields = y.getClass().getDeclaredFields();
			for (Field field : yFields) {
				try {
					Field xField = mapToClass.getDeclaredField(field.getName());
					ReflectionUtils.copyFieldValue(x, xField, y, field);
				} catch (NoSuchFieldException e) {}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return x;
	}
}

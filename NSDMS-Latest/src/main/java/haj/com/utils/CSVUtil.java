/*
 *	Programmer: wesley
 *	Date: 20 Jul 2017
 *	Project: WesleyUtilities
 *	Package: com.wesley.utils.csv
 *	Using JRE: 1.8.0_73
*/
package haj.com.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Transient;

import com.ancientprogramming.fixedformat4j.format.FixedFormatManager;
import com.ancientprogramming.fixedformat4j.format.impl.FixedFormatManagerImpl;

import haj.com.annotations.CSVAnnotation;
import haj.com.annotations.CSVLookupAnnotation;
import haj.com.exceptions.ValueRequiredException;

// TODO: Auto-generated Javadoc
/**
 * The Class CSVUtil.
 */
public class CSVUtil {

	/** The cvs split by. */
	private static String cvsSplitBy = ",";

	/** The default separator. */
	private static final char DEFAULT_SEPARATOR = ',';

	/** The default quote. */
	private static final char DEFAULT_QUOTE = '"';

	/**
	 * Takes CSV flie and turns it into a list of type outputClass.
	 *
	 * @param <T>
	 *            the generic type
	 * @param outputClass
	 *            the desired Object list
	 * @param inputStream
	 *            the input stream of the csv file
	 * @param delimeter
	 *            csv delimeter, default is ','
	 * @return list of objects of type outputClass
	 */
	@SuppressWarnings("unchecked")
	public <T> T getObjects(Class<?> outputClass, InputStream inputStream, String delimeter) {
		List<Object> policies = new ArrayList<Object>();
		if (!delimeter.isEmpty()) cvsSplitBy = delimeter;
		try {
			Map<String, Map<String, Object>> values = readCSV(inputStream);
			for (Entry<String, Map<String, Object>> entry : values.entrySet()) {
				if (!entry.getValue().isEmpty()) {
					Object policy = outputClass.newInstance();
					List<Field> fields = ReflectionUtils.getFieldsWithAnnotations(outputClass.getDeclaredFields(), CSVAnnotation.class);
					for (Field field : fields) {
						field.setAccessible(true);
						Annotation annotation = field.getAnnotation(CSVAnnotation.class);
						CSVAnnotation testerInfo = (CSVAnnotation) annotation;

						if (testerInfo.process()) {
							Object objs = getObjects(testerInfo.className(), inputStream, delimeter, entry);
							getValue(policy, field, testerInfo, objs);
						} else {
							Object objs = entry.getValue().get(testerInfo.name().trim());
							getValue(policy, field, testerInfo, objs);
						}
					}
					policies.add(policy);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T) policies;
	}

	/**
	 * Turns list of objects into csv string.
	 *
	 * @param <T>
	 *            the generic type
	 * @param objects
	 *            the objects
	 * @param delimeter
	 *            the delimeter
	 * @param ignoreTransient
	 *            TODO
	 * @return the string
	 */
	public static <T> String writeCSV(List<T> objects, String delimeter, boolean ignoreTransient) {
		if (!delimeter.isEmpty()) cvsSplitBy = delimeter;
		StringBuilder csvHeadings = new StringBuilder();
		String csv = "";
		try {
			boolean headingsDone = false;
			for (T object : objects) {
				List<Field> fields = ReflectionUtils.getFieldsWithAnnotations(object.getClass().getDeclaredFields(), CSVAnnotation.class);
				fields.addAll(ReflectionUtils.getFieldsWithAnnotations(object.getClass().getSuperclass().getDeclaredFields(), CSVAnnotation.class));
				String line = "";
				for (Field field : fields) {
					field.setAccessible(true);
					Transient transient1 = field.getAnnotation(Transient.class);
					if (!ignoreTransient || transient1 == null) {
						Annotation annotation = field.getAnnotation(CSVAnnotation.class);

						CSVAnnotation testerInfo = (CSVAnnotation) annotation;
						if (!headingsDone && !testerInfo.name().isEmpty()) {
							csvHeadings.append(testerInfo.name() + cvsSplitBy);
						}
						String preFormat = "";
						if (testerInfo.process()) {
							preFormat = getCSVValue(testerInfo.className(), field.get(object), delimeter, csvHeadings);
							line += preFormat + cvsSplitBy;
						} else {
							preFormat = getStringValue(object, field, testerInfo);
							if (preFormat.contains(cvsSplitBy)) line += "\"" + preFormat + "\"" + cvsSplitBy;
							else line += preFormat + cvsSplitBy;
						}

					}

				}
				if (line.length() > 0) csv += line.substring(0, line.length() - 1) + "\n";
				else csv += line;
				if (!headingsDone) {
					headingsDone = true;
					csv = csvHeadings.toString().substring(0, csvHeadings.length() - 1) + "\n" + csv;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (csv.length() > 0) return csv.substring(0, csv.length() - 1);
		else return "";
	}

	public static <T> String writeCSVNoAnnotaions(List<T> objects, String delimeter, boolean ignoreTransient) {
		if (!delimeter.isEmpty()) cvsSplitBy = delimeter;
		String csv = "";
		try {
			for (T object : objects) {
				List<Field> fields = Arrays.asList(object.getClass().getDeclaredFields());
				fields.addAll(Arrays.asList(object.getClass().getSuperclass().getDeclaredFields()));
				String line = "";
				for (Field field : fields) {
					field.setAccessible(true);
					Transient transient1 = field.getAnnotation(Transient.class);
					if (!ignoreTransient || transient1 == null) {
						String preFormat = "";
						preFormat = "" + field.get(object);
						if (preFormat.contains(cvsSplitBy)) line += "\"" + preFormat + "\"" + cvsSplitBy;
						else line += preFormat + cvsSplitBy;
					}
				}
				if (line.length() > 0) csv += line.substring(0, line.length() - 1) + "\n";
				else csv += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (csv.length() > 0) return csv.substring(0, csv.length() - 1);
		else return "";
	}

	/**
	 * Gets the CSV value.
	 *
	 * @param <T>
	 *            the generic type
	 * @param inputClass
	 *            the input class
	 * @param object
	 *            the object
	 * @param delimeter
	 *            the delimeter
	 * @param csvHeadings
	 *            the csv headings
	 * @return the CSV value
	 */
	private static <T> String getCSVValue(Class<?> inputClass, Object object, String delimeter, StringBuilder csvHeadings) {
		if (!delimeter.isEmpty()) cvsSplitBy = delimeter;
		StringBuilder csv = new StringBuilder();
		try {
			boolean headingsDone = false;
			if (object.getClass().equals(inputClass)) {
				List<Field> fields = ReflectionUtils.getFieldsWithAnnotations(inputClass.getDeclaredFields(), CSVAnnotation.class);
				try {
					String line = "";
					for (Field field : fields) {
						field.setAccessible(true);
						Annotation annotation = field.getAnnotation(CSVAnnotation.class);
						CSVAnnotation testerInfo = (CSVAnnotation) annotation;
						if (!headingsDone && !testerInfo.name().isEmpty()) {
							csvHeadings.append(testerInfo.name() + cvsSplitBy);
						}
						String preFormat = "";
						if (testerInfo.process()) {
							preFormat = getCSVValue(testerInfo.className(), field.get(object), delimeter, csvHeadings);
							line += preFormat + cvsSplitBy;
						} else {
							preFormat = getStringValue(object, field, testerInfo);
							if (preFormat.contains(cvsSplitBy)) line += "\"" + preFormat + "\"" + cvsSplitBy;
							else line += preFormat + cvsSplitBy;
						}
					}

					csv.append(line.substring(0, line.length() - 1));
				} catch (ValueRequiredException e) {
					e.printStackTrace();
				}
				if (!headingsDone) {
					headingsDone = true;
				}

			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return csv.toString();
	}

	/**
	 * Gets the objects.
	 *
	 * @param <T>
	 *            the generic type
	 * @param outputClass
	 *            the output class
	 * @param inputStream
	 *            the input stream
	 * @param delimeter
	 *            the delimeter
	 * @param entry
	 *            the entry
	 * @return the objects
	 */
	@SuppressWarnings("unchecked")
	private static <T> T getObjects(Class<?> outputClass, InputStream inputStream, String delimeter, Entry<String, Map<String, Object>> entry) {
		Object policy = null;
		if (!delimeter.isEmpty()) cvsSplitBy = delimeter;
		try {
			policy = outputClass.newInstance();
			List<Field> fields = ReflectionUtils.getFieldsWithAnnotations(outputClass.getDeclaredFields(), CSVAnnotation.class);
			try {
				for (Field field : fields) {
					field.setAccessible(true);
					Annotation annotation = field.getAnnotation(CSVAnnotation.class);
					CSVAnnotation testerInfo = (CSVAnnotation) annotation;
					if (testerInfo.process()) {
						Object objs = getObjects(testerInfo.className(), inputStream, delimeter, entry);
						getValue(policy, field, testerInfo, objs);
					} else {
						Object objs = entry.getValue().get(testerInfo.name());
						getValue(policy, field, testerInfo, objs);
					}
				}
			} catch (ValueRequiredException e) {
				e.printStackTrace();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T) policy;
	}

	/**
	 * Gets the value.
	 *
	 * @param policy
	 *            the policy
	 * @param field
	 *            the field
	 * @param testerInfo
	 *            the tester info
	 * @param objs
	 *            the objs
	 * @return the value
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws ValueRequiredException
	 *             the value required exception
	 */
	private static void getValue(Object policy, Field field, CSVAnnotation testerInfo, Object objs) throws IllegalAccessException, ValueRequiredException {
		try {
			if (objs == null || ("" + objs).isEmpty()) if (testerInfo.required()) throw new ValueRequiredException("Value for field " + field.getName() + " is a required field.");
			else field.set(policy, null);
			else if (testerInfo.className().equals(Date.class)) {
				if (("" + objs).trim().contains("/")) field.set(policy, new SimpleDateFormat("yyyy/MM/dd").parse(("" + objs).trim()));
				else field.set(policy, new SimpleDateFormat(testerInfo.datePattern()).parse(("" + objs).trim()));
			}

			else if (testerInfo.className().equals(java.math.BigDecimal.class)) field.set(policy, java.math.BigDecimal.valueOf(Double.parseDouble(("" + objs).trim())));
			else if (testerInfo.className().equals(Double.class)) field.set(policy, Double.parseDouble(("" + objs).trim()));
			else if (testerInfo.className().equals(Integer.class)) field.set(policy, Integer.parseInt(("" + objs).trim()));
			else if (testerInfo.className().equals(Long.class)) field.set(policy, Long.parseLong(("" + objs).trim()));
			else if (testerInfo.className().equals(String.class)) field.set(policy, ("" + objs).trim());
			else field.set(policy, testerInfo.className().cast(objs));

			if (testerInfo.lookupField() != null && !testerInfo.lookupField().isEmpty()) {
				getLookupValue(policy, field, testerInfo);
			}
		} catch (ParseException e) {
			field.set(policy, null);
		} catch (IllegalArgumentException e) {
			field.set(policy, null);
		}
	}

	public static void getLookupValue(Object policy, Field field, CSVAnnotation testerInfo) throws IllegalAccessException {
		try {

			Field f = policy.getClass().getDeclaredField(testerInfo.lookupField().trim());
			f.setAccessible(true);
			CSVLookupAnnotation annotation = (CSVLookupAnnotation) f.getAnnotation(CSVLookupAnnotation.class);
			Object obj = null;

			if (!annotation.className().isEnum()) {
				obj = annotation.className().newInstance();
				Method dao = obj.getClass().getDeclaredMethod(annotation.method(), annotation.paramClass());
				f.set(policy, dao.invoke(obj, field.get(policy)));
			} else {
				Method dao = annotation.className().getDeclaredMethod(annotation.method(), annotation.paramClass());
				f.set(policy, dao.invoke(field.get(policy)));
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the string value.
	 *
	 * @param policy
	 *            the policy
	 * @param field
	 *            the field
	 * @param testerInfo
	 *            the tester info
	 * @return the string value
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws ValueRequiredException
	 *             the value required exception
	 */
	private static String getStringValue(Object policy, Field field, CSVAnnotation testerInfo) throws IllegalAccessException, ValueRequiredException {
		if (field.get(policy) == null || ("" + field.get(policy)).isEmpty()) if (testerInfo.required()) throw new ValueRequiredException("Value for field " + field.getName() + " is a required field.");
		else {
			String ret = "";
			if (testerInfo.className().equals(Long.class)) {
				ret = "0";
			} else if (testerInfo.className().equals(Integer.class)) {
				ret = "0";
			} else if (testerInfo.className().equals(Double.class)) {
				ret = "0.0";
			} else if (testerInfo.className().equals(BigDecimal.class)) {
				ret = "99999.999";
			} else if (testerInfo.className().equals(Date.class)) {
				ret = "2010-01-31";
			}
			return ret;
		}

		if (testerInfo.className().equals(Date.class)) return new SimpleDateFormat(testerInfo.datePattern()).format((Date) field.get(policy));

		else return "" + field.get(policy);
	}

	private static String getStringValue(Object policy, Field field, String testerInfo) throws IllegalAccessException, ValueRequiredException {
		if (testerInfo.equals(Date.class)) return GenericUtility.sdf6.format((Date) field.get(policy));
		else return "" + field.get(policy);
	}

	private static String getFixedStringValue(Object policy, Field field, CSVAnnotation testerInfo) throws IllegalAccessException, ValueRequiredException {
		if (field.get(policy) == null || ("" + field.get(policy)).isEmpty()) if (testerInfo.required()) throw new ValueRequiredException("Value for field " + field.getName() + " is a required field.");

		String ret = "";

		if (field.get(policy) == null) return GenericUtility.padRight("", testerInfo.length());
		;

		if (testerInfo.className().equals(Date.class)) {

			ret = new SimpleDateFormat(testerInfo.datePattern()).format((Date) field.get(policy));

		} else if (testerInfo.className().equals(Double.class)) {
			ret = String.format(testerInfo.numericPattern(), Double.valueOf("" + field.get(policy)));
		} else if (testerInfo.className().equals(BigDecimal.class)) {
			ret = String.format(testerInfo.numericPattern(), BigDecimal.valueOf(Double.valueOf("" + field.get(policy))));
		} else ret = "" + field.get(policy);

		if (testerInfo.length() > 0) {
			if (ret.length() > testerInfo.length()) {
				ret = ret.substring(0, testerInfo.length());
			} else if (ret.length() != testerInfo.length()) {
				ret = GenericUtility.padRight(ret, testerInfo.length());

			}
		}
		return ret;
	}

	/**
	 * Parses the line.
	 *
	 * @param cvsLine
	 *            the cvs line
	 * @param separators
	 *            the separators
	 * @param customQuote
	 *            the custom quote
	 * @return the list
	 */
	private static List<String> parseLine(String cvsLine, char separators, char customQuote) {
		List<String> result = new ArrayList<String>();
		// if empty, return!
		if (cvsLine == null || cvsLine.isEmpty()) {
			return result;
		}
		if (customQuote == ' ') {
			customQuote = DEFAULT_QUOTE;
		}
		if (separators == ' ') {
			separators = DEFAULT_SEPARATOR;
		}
		StringBuffer curVal = new StringBuffer();
		boolean inQuotes = false;
		boolean doubleQuotesInColumn = false;
		char[] chars = cvsLine.toCharArray();
		for (char ch : chars) {
			if (inQuotes) {
				if (ch == customQuote) {
					inQuotes = false;
					doubleQuotesInColumn = false;
				} else {
					// Fixed : allow "" in custom quote enclosed
					if (ch == '\"') {
						if (!doubleQuotesInColumn) {
							curVal.append(ch);
							doubleQuotesInColumn = true;
						}
					} else {
						curVal.append(ch);
					}

				}
			} else {
				if (ch == customQuote) {
					inQuotes = true;
				} else if (ch == separators) {
					result.add(curVal.toString());
					curVal = new StringBuffer();
				} else if (ch == '\r') {
					// ignore LF characters
					continue;
				} else if (ch == '\n') {
					// the end, break!
					break;
				} else {
					curVal.append(ch);
				}
			}

		}
		result.add(curVal.toString());
		return result;
	}

	/**
	 * Read CSV.
	 *
	 * @param inputStream
	 *            the input stream
	 * @return the map
	 */
	private static Map<String, Map<String, Object>> readCSV(InputStream inputStream) {
		Map<String, Map<String, Object>> values = new LinkedHashMap<String, Map<String, Object>>();
		List<String> headings = new LinkedList<String>();
		BufferedReader br = null;
		String line = "";
		try {
			boolean heading = true;
			int count = 1;
			br = new BufferedReader(new InputStreamReader(inputStream));
			while ((line = br.readLine()) != null) {
				int col = 0;
				if (heading) {
					String[] strings = line.split(cvsSplitBy);
					for (String string : strings) {
						headings.add(string.replaceAll("[^a-zA-Z0-9_.\\-;]+", ""));
					}
					heading = false;
				} else {
					Map<String, Object> csvValue = new HashMap<String, Object>();
					List<String> s = parseLine(line, cvsSplitBy.charAt(0), DEFAULT_QUOTE);
					for (String string : s) {
						try {
							csvValue.put(headings.get(col).trim(), string.trim());
							col++;
						} catch (java.lang.IndexOutOfBoundsException ib) {
							// System.out.println(col + " \t" + string + " \t "+line);
						}

					}
					values.put("object" + count, csvValue);
					count++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return values;
	}

	@SuppressWarnings("unchecked")
	public <T> T readFixedFileLength(Class<?> outputClass, InputStream inputStream) {
		FixedFormatManager manager = new FixedFormatManagerImpl();
		List<Object> policies = new ArrayList<Object>();
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new InputStreamReader(inputStream));
			while ((line = br.readLine()) != null) {
				Object objs = manager.load(outputClass, line);
				policies.add(objs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T) policies;
	}

	public static <T> String writeFixedLength(List<T> objects) {

		StringBuilder csvHeadings = new StringBuilder();
		String csv = "";
		try {
			for (T object : objects) {
				List<Field> fields = ReflectionUtils.getFieldsWithAnnotations(object.getClass().getDeclaredFields(), CSVAnnotation.class);
				fields.addAll(ReflectionUtils.getFieldsWithAnnotations(object.getClass().getSuperclass().getDeclaredFields(), CSVAnnotation.class));
				String line = "";
				for (Field field : fields) {
					field.setAccessible(true);
					Annotation annotation = field.getAnnotation(CSVAnnotation.class);
					CSVAnnotation testerInfo = (CSVAnnotation) annotation;
					String preFormat = "";
					if (testerInfo.process()) {
						preFormat = getCSVValue(testerInfo.className(), field.get(object), "", csvHeadings);
						line += preFormat + cvsSplitBy;
					} else {
						preFormat = getFixedStringValue(object, field, testerInfo);
						//if (preFormat.contains(cvsSplitBy)) line += "\"" + preFormat + "\"";
						//else
							line += preFormat;
					}

				}
				csv += line.substring(0, line.length()) + "\r\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return csv.substring(0, csv.length());
	}
	
	
	public static <T> String writeFixedLength(Object object) {

		StringBuilder csvHeadings = new StringBuilder();
		String csv = "";
		try {
				List<Field> fields = ReflectionUtils.getFieldsWithAnnotations(object.getClass().getDeclaredFields(), CSVAnnotation.class);
				fields.addAll(ReflectionUtils.getFieldsWithAnnotations(object.getClass().getSuperclass().getDeclaredFields(), CSVAnnotation.class));
				String line = "";
				for (Field field : fields) {
					field.setAccessible(true);
					Annotation annotation = field.getAnnotation(CSVAnnotation.class);
					CSVAnnotation testerInfo = (CSVAnnotation) annotation;
					String preFormat = "";
					if (testerInfo.process()) {
						preFormat = getCSVValue(testerInfo.className(), field.get(object), "", csvHeadings);
						line += preFormat + cvsSplitBy;
					} else {
						preFormat = getFixedStringValue(object, field, testerInfo);
//						if (preFormat.contains(cvsSplitBy)) line += "\"" + preFormat + "\"";
//						else 
							line += preFormat;
					}

				}
				csv += line.substring(0, line.length()) + "\r\n";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return csv.substring(0, csv.length());
	}
}

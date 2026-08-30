package haj.com.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.sars.SarsEmployerDetail;


// TODO: Auto-generated Javadoc
/**
 * The Class Initialize.
 *
 * @author Hendrik Strydom
 */
public class Initialize {

	/** The Constant logger. */
	protected static final Log logger = LogFactory.getLog(Initialize.class);

	/**
	 * Method will initialize all char fields to ' '.
	 *
	 * @param obj            Any java object
	 */
	public static void init(Object obj) {
		try {
			Method[] m = obj.getClass().getMethods();
			for (int i = 0; i < m.length; i++) {

				Method method = m[i];

				if (method.getName().startsWith("set")) {
					Class<?>[] pt = method.getParameterTypes();
					for (Class<?> class1 : pt) {
						if (class1.getCanonicalName().startsWith("haj.com.entity")) {
							init(class1);
						}

						if ("char".equals(class1.getCanonicalName())) {
							method.invoke(obj, ' ');
						}
					}

				}
			}
		} catch (Exception e) {
			logger.fatal(e.getMessage() + " " + e.getStackTrace()[0]);
		}
	}

	/**
	 * Inits the object.
	 *
	 * @param obj the obj
	 */
	public static void initObject(Object obj) {
		init(obj);
		initFileds(obj);
		// toDefaultValue(obj);
	}

	/**
	 * Inits the fileds.
	 *
	 * @param obj the obj
	 */
	public static void initFileds(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		// System.out.println(obj.getClass().getDeclaredFields().length);
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				// System.out.println(field.getName());
				if (!field.getName().equals("newBusinessRegisterId")) {
					if (!field.getName().equals("serialVersionUID")) {
						if (!field.getName().equals("id")) {
							if (!field.getName().equals("uid")) {
								if (!field.getName().equals("topCol")) {
									if (!field.getName().equals("leftCol")) {
										if (!field.getName().equals("version")) {
											if (!field.getName().equals("newVersion")) {
												if (field.getModifiers() != 25) {
													if (field.getType().isAssignableFrom(String.class)) {
														field.set(obj, null);
													} else if (field.getType().isAssignableFrom(BigDecimal.class)) {
														field.set(obj, null);
													} else if (field.getType().isAssignableFrom(Character.class)) {
														field.set(obj, ' ');
													} else if (field.getType().isAssignableFrom(Long.class)) {
														field.setLong(obj, 0L);
													} else if (field.getType().isAssignableFrom(Integer.class)) {
														field.set(obj, 0);
													} else if (field.getType().isAssignableFrom(Double.class)) {
														field.set(obj, 0.0);
													} else if (field.getType().isAssignableFrom(Short.class)) {
														field.set(obj, (short) 0);
													} else if (field.getType().isAssignableFrom(Boolean.class)) {
														field.set(obj, false);
													}

													/*
													 * else if (field.getType().
													 * isAssignableFrom(
													 * Date.class)) {
													 * field.set(obj, null); }
													 */

													else {
														//logger.info(field.getType().toString());
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (IllegalArgumentException e) {
			logger.fatal(e);
		} catch (Exception e) {
			logger.fatal(e);
		}
	}

	/**
	 * To default value.
	 *
	 * @param obj the obj
	 */
	public static void toDefaultValue(Object obj) {

		Field[] fields = obj.getClass().getDeclaredFields();
		try {
			for (Field field : fields) {
				if (field.getModifiers() != 25) {
					field.setAccessible(true);
					if (field.getType().isAssignableFrom(String.class)) {
						field.set(obj, "");

					} else if (field.getType().isAssignableFrom(BigDecimal.class)) {
						field.set(obj, BigDecimal.valueOf(0.0d));
					} else if (field.getType().isAssignableFrom(Date.class)) {
						field.set(obj, null);
					} else if (field.getType().isAssignableFrom(char.class)) {
						field.set(obj, ' ');
					} else if (field.getType().isAssignableFrom(long.class)) {
						field.setLong(obj, 0l);
					} else if (field.getType().isAssignableFrom(int.class)) {
						field.set(obj, 0);
					} else if (field.getType().isAssignableFrom(short.class)) {
						field.set(obj, (short) 0);
					} else if (field.getType().isAssignableFrom(boolean.class)) {
						field.set(obj, false);
					} else {
						System.out.println(field.getType().toString());
					}
				}
			}
		} catch (IllegalArgumentException e) {
			logger.fatal(e);
		} catch (IllegalAccessException e) {
			logger.fatal(e);
		}
	}
	
	
	public static void initStringsIfNull(Object obj) {

		Field[] fields = obj.getClass().getDeclaredFields();
		try {
			for (Field field : fields) {
		
				if (field.getModifiers() != 25) {
					field.setAccessible(true);
					if (field.getType().isAssignableFrom(String.class)) {
						if (field.get(obj)==null) field.set(obj, "");
					} 
				}
			}
		} catch (IllegalArgumentException e) {
			logger.fatal(e);
		} catch (IllegalAccessException e) {
			logger.fatal(e);
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		//Attestations er = new Attestations();
		//init(er);
		SarsEmployerDetail obj  =  new SarsEmployerDetail();
		obj.setRefNo("L0001");
		Initialize.initStringsIfNull(obj);
		try {
			// Initialize.toDefaultValue(ins);
		} catch (Exception e) {
			logger.fatal(e);
		}

	}
}

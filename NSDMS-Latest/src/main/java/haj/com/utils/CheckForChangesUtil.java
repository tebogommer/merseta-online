/*-------------------------------------------------------------------*
 * Programmer: wesley    		Date: 18 Oct 2016                    *
 * Project: ingestion										         *
 *-------------------------------------------------------------------*/
package haj.com.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import haj.com.framework.IDataEntity;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

/**
 * The Class CheckForChangesUtil.
 */
public class CheckForChangesUtil implements Serializable {

	private static final NSDMSLogger logger = NSDMSLogger.getLogger(CheckForChangesUtil.class);

	/** The doc service. */
	private static CheckForChangesUtil checkForChangesUtil = null;

	/**
	 * Instance.
	 *
	 * @return the doc service
	 */
	public static synchronized CheckForChangesUtil instance() {
		if (checkForChangesUtil == null) {
			checkForChangesUtil = new CheckForChangesUtil();
		}
		return checkForChangesUtil;
	}

	/**
	 * Finds all the differences between 2 different objects in order to be audited
	 * Old Object needs to be set before comparing the two objects.
	 *
	 * @param someObject
	 *            to be compared to the original
	 * @return the map
	 */
	public Map<String, List<String>> checkForChanges(IDataEntity someObject, IDataEntity oldObject) {
		/*
		 * Map with the field name as key and a list of strings. First string in list is
		 * the old value while second is new value.
		 */
		Map<String, List<String>> changesMade = new HashMap<String, List<String>>();
		try {
			// Check if the 2 objects are the same class so that correct values are compared
			if (oldObject != null) {
				if (oldObject.getClass().equals(someObject.getClass())) {
					for (Field field : someObject.getClass().getDeclaredFields()) {
						for (Field oldfield : oldObject.getClass().getDeclaredFields()) {
							if (field.getName().equals(oldfield.getName()) && !field.getType().getName().equals("java.util.Set") && !field.getType().getName().contains("haj.com.entity")) {
								List<String> values = new ArrayList<String>(2);
								oldfield.setAccessible(true); // Allow access to private fields
								field.setAccessible(true);
								Object value = field.get(someObject);
								Object oldvalue = oldfield.get(oldObject);
								// If the old value was changed create entry in map.
								if (value != null && !value.equals(oldvalue)) {
									values.add("" + ((oldvalue == null) ? "Empty" : oldvalue));
									values.add("" + value);
									changesMade.put(field.getName(), values);
									// If old value was removed add value in map set new value = 'Empty' instead of
									// 'null'
								} else if (value == null && oldvalue != null) {
									values.add("" + oldvalue);
									values.add("Empty");
									changesMade.put(field.getName(), values);
								}
								break;
							}
						}
					}
				}
			}
		} catch (SecurityException e) {
			CheckForChangesUtil.logger.fatal("SecurityException");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			CheckForChangesUtil.logger.fatal("IllegalArgumentException");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			CheckForChangesUtil.logger.fatal("IllegalAccessException");
			e.printStackTrace();
		} catch (NullPointerException e) {
			CheckForChangesUtil.logger.fatal("NullPointerException");
			e.printStackTrace();
		} catch (Exception e) {
			CheckForChangesUtil.logger.fatal("General Exception");
			e.printStackTrace();
		}
		return changesMade;
	}

}

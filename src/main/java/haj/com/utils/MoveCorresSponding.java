package haj.com.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class MoveCorresSponding.
 */
public class MoveCorresSponding {

	/** The logger. */
	private static Log logger = LogFactory.getLog(MoveCorresSponding.class);

	/**
	 * Do moves.
	 *
	 * @param from            DatabaseRow object
	 * @param to            Business Logic Row Object
	 * @return to Object with moved values
	 * @throws Exception the exception
	 */
	public static Object doMoves(Object from, Object to) throws Exception {
		Map<String, String> fmap = new TreeMap<String, String>();
		Map<String, Method> methodMap = new TreeMap<String, Method>();
		List<Field> list = getAllFields(null, to);
		// [] arr = list.toArray(new String[list.size()]);
		Field[] f = list.toArray(new Field[list.size()]);// to.getClass().getDeclaredFields();//to.getClass().getFields();
		for (int i = 0; i < f.length; i++) {
			Field field = f[i];
			fmap.put(field.getName().toUpperCase(), field.getType().getName());
		}
		Method[] s = to.getClass().getMethods();
		for (int i = 0; i < s.length; i++) {
			Method method = s[i];
			if (method.getName().charAt(0) == 's') {
				methodMap.put(method.getName().substring(3).toUpperCase(), method);
			}
		}
		Method[] m = from.getClass().getMethods();
		for (int i = 0; i < m.length; i++) {
			Method method = m[i];
			if (method.getName().charAt(0) == 'g') {
				// if
				// (fmap.containsKey(GeneralUtil.removeUS(method.getName().substring(3).toUpperCase()))){
				if (fmap.containsKey(method.getName().substring(3).toUpperCase())) {
					try {
						// to =
						// moveNow(from,to,GeneralUtil.removeUS(method.getName().substring(3)),(String)fmap.get(GeneralUtil.removeUS(method.getName().substring(3).toUpperCase())),method.invoke(from,null),methodMap);
						to = moveNow(from, to, method.getName().substring(3),
								(String) fmap.get(method.getName().substring(3).toUpperCase()),
								method.invoke(from, null), methodMap);

					} catch (IllegalArgumentException e) {

						e.printStackTrace();
					} catch (IllegalAccessException e) {

						e.printStackTrace();
					} catch (InvocationTargetException e) {

						e.printStackTrace();
					}
				}
			}
		}
		return to;
	}

	/**
	 * Invoke the appropiate setXXX() method on the to Object.
	 *
	 * @param from the from
	 * @param to the to
	 * @param field the field
	 * @param type the type
	 * @param value the value
	 * @param methodMap the method map
	 * @return the object
	 */
	private static Object moveNow(Object from, Object to, String field, String type, Object value, Map methodMap) {
		Object[] o = new Object[1];

		if (methodMap.containsKey(field.toUpperCase())) {
			Method m = (Method) methodMap.get(field.toUpperCase());
			/// String value2 = convertToString(value);
			Object value2 = value;
			o[0] = value2;
			try {
				if (value2 != null) {

					// System.out.println(m.getName()+" " + value.getClass().getName() + " value :" + value2);
					m.invoke(to, o);
				}
			} catch (IllegalArgumentException e) {
				System.out.println(m.getName() + " " + value.getClass().getName());
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				System.out.println(m.getName() + " " + value.getClass().getName());
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				System.out.println(m.getName() + " " + value.getClass().getName());
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return to;
	}

	/**
	 * Convert to string.
	 *
	 * @param value the value
	 * @return the string
	 */
	private static String convertToString(Object value) {
		String ret = null;
		if (value == null)
			ret = "";
		else if (value instanceof BigDecimal)
			ret = ((BigDecimal) value).toString();
		else if (value instanceof BigInteger)
			ret = ((BigInteger) value).toString();
		// else if (value instanceof byte[])
		// query.setBinary(key, (byte[]) value);
		else if (value instanceof Boolean)
			ret = ((Boolean) value).toString();
		// else if (value instanceof Byte)
		// query.setByte(key, (Byte) value);
		// else if (value instanceof Calendar)
		// query.setCalendarDate(key, (Calendar) value);
		else if (value instanceof Character)
			ret = ((Character) value).toString();
		else if (value instanceof Date)
			ret = ((Date) value).toString();
		else if (value instanceof Double)
			ret = "" + ((Double) value).doubleValue();
		else if (value instanceof Float)
			ret = "" + ((Float) value).floatValue();
		else if (value instanceof Integer)
			ret = "" + ((Integer) value).intValue();
		else if (value instanceof Locale)
			ret = ((Locale) value).toString();
		else if (value instanceof Long)
			ret = "" + ((Long) value).longValue();
		else if (value instanceof Short)
			ret = "" + ((Short) value).shortValue();
		else if (value instanceof String)
			ret = ((String) value);
		else if (value instanceof java.sql.Timestamp)
			ret = ((java.sql.Timestamp) value).toString();
		else
			ret = "";
		return ret;
	}

	/**
	 * Gets the all fields.
	 *
	 * @param f the f
	 * @param obj the obj
	 * @return the all fields
	 * @throws Exception the exception
	 */
	public static List<Field> getAllFields(List<Field> f, Object obj) throws Exception {
		// System.out.println(obj.getClass().getCanonicalName());
		Field[] tf = obj.getClass().getDeclaredFields();
		List<Field> fieldList = Arrays.asList(tf);
		if (f == null)
			f = new ArrayList<Field>();
		if (f.size() == 0)
			f.addAll(fieldList);
		else {
			f.addAll(fieldList);
		}

		/*
		 * Field[] ff = obj.getClass().getSuperclass().getFields(); for (Field
		 * field : ff) { System.out.println(field.getName()); } getAllFields(f,
		 * obj.getClass().getSuperclass());
		 */
		return f;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			/*
			 * HomeLoanApprovalReq req = new HomeLoanApprovalReq();
			 * req.setPolicyRefNo(1212121L); req.setAmount((double)100.99);
			 * req.setAccountNo("98676767");
			 * req.setShareOfFundRefNo("2010-01-01");
			 * req.setExtprovider("W001");
			 * 
			 * HomeLoanApprovalReqJSON to = new HomeLoanApprovalReqJSON();
			 * 
			 * List<Field> list = getAllFields(null, to); for (Field field :
			 * list) { System.out.println(field.getName()); }
			 * 
			 * //doMoves(to, req); to = (HomeLoanApprovalReqJSON)doMoves(req,
			 * to);
			 * 
			 * System.out.println(JSONService.objectToString(to));
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

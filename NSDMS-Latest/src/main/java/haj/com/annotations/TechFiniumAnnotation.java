package haj.com.annotations;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import haj.com.exceptions.MraAnnotationException;



public class TechFiniumAnnotation implements Serializable {

	/**
	 * Process the annotations
	 * @param obj
	 * @return
	 * @throws MraAnnotationException
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static  Map<String,Map<String,String>> processBean(Object obj) throws MraAnnotationException,Exception { 
		if (obj==null) throw new MraAnnotationException("The object is null");
		if (obj instanceof List) {
			Object o = null;
			if (((List)obj).size()==0) throw new MraAnnotationException("The list is empty");
			for (Object objT : (List)obj) {
				o = objT;
				break;
			}
			return processBeanAnnotation(null,o);
		}
		else return processBeanAnnotation(null,obj);
		
	}
	
	private static Map<String,Map<String,String>> processBeanAnnotation(Map<String,Map<String,String>> fmap,Object obj) throws Exception {
		if (fmap==null) fmap = new LinkedHashMap<String,Map<String,String>>();
		List<Field> list = getAllFields(null, obj) ; 
		for (Field field : list) {

			
				
			 if (determinDeepLink(field)) {
					TechFiniumColumn annot = field.getAnnotation(TechFiniumColumn.class);
					if (annot.suppress()==true) {
						Map<String,String> m = new HashMap<String,String>();
						m.put("suppress", "true"); 
						fmap.put(field.getName(),m);
					}
					else {
		   		     Object aClass =  (Object)Class.forName(field.getType().getName()).newInstance();
		   		     processBeanAnnotation(fmap, aClass);
					}
 				
			 }
			 else { 
				TechFiniumColumn anno = field.getAnnotation(TechFiniumColumn.class);
				Map<String,String> m = new HashMap<String,String>();
				if (anno==null) {
					m.put("heading", field.getName());
				}
				else {
					if (anno.heading()!=null && anno.heading().trim().length()>0)
						m.put("heading", anno.heading());
					if (anno.formula()!=null && anno.formula().trim().length()>0) 
							m.put("formula", anno.formula());
					if (anno.suppress()==true) 
						m.put("suppress", "true");
					if (anno.textwrap()==true) 
						m.put("textwrap", "true");
				}
				fmap.put(field.getName(),m);
			 }
		   }
		
		return fmap;
			
	}
	
	private static boolean determinDeepLink(Field field) {
		 boolean deepLink = false;
	   	    if (field.getType().isPrimitive()) { }
			else {
				 if (javaClass(field.getType().getName())){ }
				 else {
					 deepLink = true;
				 }
			}
		return deepLink;
		
	}

	/**
	 * Get all fields of an object
	 * @param f
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static List<Field> getAllFields(List<Field> f, Object obj) throws Exception {
		if (obj!=null) {
			Field[] tf = obj.getClass().getDeclaredFields();
 
			List<Field> fieldList = Arrays.asList(tf);
    	
   
			if (f==null) f = new ArrayList<Field>();
			if (f.size() ==0) f.addAll(fieldList);
			else {
				f.addAll(fieldList);
			}
       	 return f;
		}
		else return f;
    }


	/**
	 * Execute Getters on POJO
	 * @param field
	 * @param o
	 * @return
	 */
	 public static Object runGetter(Field field, Object o)
	 {
	     // MZ: Find the correct method
	     for (Method method : o.getClass().getMethods())
	     {
	         if ((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3)))
	         {
	             if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase()))
	             {
	                 // MZ: Method found, run it
	                 try
	                 {
	                	 
	                     //return convertToString(method.invoke(o));
	                	 return method.invoke(o);
	                 }
	                 catch (IllegalAccessException e)
	                 {
	                	 e.printStackTrace();
	                    System.out.println("Could not determine method: " + method.getName());
	                 }
	                 catch (InvocationTargetException e)
	                 {
	                	 System.out.println("Could not determine method: " + method.getName());
	                 }

	             }
	         }
	     }
	     return null;
	 }
	 
	 private static void runGetter(Map<String, Object> fmap, Field field, Object o) throws Exception{
	     for (Method method : o.getClass().getMethods())
	     {
	         if ((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3)))
	         {
	             if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase()))
	             {
	                 // MZ: Method found, run it
	                 try
	                 {
	                	 if (populate(fmap,field,method,o)) 
	                	 {
	                		 Object aClass =  (Object)method.invoke(o);
	                				// Class.forName(field.getType().getName()).newInstance();
	            			 
                			 List<Field> list = getAllFields(null, aClass) ; 
                			 for (Field field2 : list) {
                 				// populate(fmap,field2,method,aClass);
                				 runGetter(fmap, field2, aClass);
                 				 
                			 }
	                	 }
	              
	                 }
	                 catch (IllegalAccessException e)
	                 {
	                	 e.printStackTrace();
	                    System.out.println("Could not determine method: " + method.getName());
	                 }
	                 catch (InvocationTargetException e)
	                 {
	                	 System.out.println("Could not determine method: " + method.getName());
	                 }
	                 catch (Exception e)
	                 {
	                	  System.out.println("MAJOR EXCEPTION method: " + method.getName());
	                 }
	             }
	         }
	     }
		
	}
	 

	 

	private static boolean populate(Map<String, Object> fmap,Field field, Method method, Object o) throws Exception{
   	    boolean deepLink = false;
	   try {	 
   	    if (field.getType().isPrimitive()) {
			fmap.put(field.getName(), method.invoke(o));
		}
		else {
			 //System.out.println("\t=>"+field.getName()+"\t"+javaClass(field.getType().getName()));
			 if (javaClass(field.getType().getName())){
				 if (!field.getType().getName().contains("sun"))
				  {	 
				   fmap.put(field.getName(), method.invoke(o));
				  }
			 }
			 else {
				 deepLink = true;
			 }
			// if (field.getType())
		}
	   } catch (java.lang.IllegalArgumentException e) {
		   
		   System.out.println("\t"+field.getName()+"\t"+field.getType()+ " "+e.getMessage());
	   }
	   
		return deepLink;
	}

	private static boolean systemClass(String name) {
		if (name.toLowerCase().contains("sun") 
				|| name.toLowerCase().contains("annotation") 
				|| name.toLowerCase().contains("enum")
				|| name.toLowerCase().contains("synthetic") 
				|| name.toLowerCase().contains("cachedconstructor")
				|| name.toLowerCase().contains("newinstancecallercache")
				|| name.toLowerCase().contains("name")
				|| name.toLowerCase().contains("allpermdomain")
				|| name.toLowerCase().contains("usecaches")
				) return true;
		return false;
	}

	private static boolean javaClass(String name) {
		if (name.contains("java")) return true;
		return false;
	}

	/**
	 * Get all field values
	 * @param obj
	 * @param headings 
	 * @return map containing field name and value
	 * @throws Exception
	 */
	public static Map<String,Object> getFieldValues(Object obj, Map<String, Map<String, String>> headings) throws Exception {
		 Map<String,Object> fmap = new LinkedHashMap<String,Object>();
		 List<Field> list = getAllFields(null, obj) ; 
		
		 for (Field field : list) {
			// fmap.put(field.getName(), runGetter(field, obj));
			 if (headings.containsKey(field.getName())) {
			   if (headings.get(field.getName()).containsKey("suppress")) {
				   if ("true".equals(headings.get(field.getName()).get("suppress"))) {}
				   else {
					   runGetter(fmap,field, obj);
				   }
					
			   }
			   else {
				   runGetter(fmap,field, obj);
			   }
			  
			 }
		}
		 return fmap;
	 }
	 
	 


	private static boolean isSystemObject(Object value)
	  {
		boolean ret = false; 
	    if (value == null)
	     ret = false;
	    else if (value instanceof BigDecimal)
	      ret =  true;
	    else if (value instanceof BigInteger)
	    	 ret =  true;
	 //   else if (value instanceof byte[])
	 //     query.setBinary(key, (byte[]) value);
	    else if (value instanceof Boolean)
	    	 ret =  true;
	    else if (value instanceof Byte)
	    	 ret =  true;
	   else if (value instanceof Calendar)
		   ret =  true;
	    else if (value instanceof Character)
	    	 ret =  true;
	    else if (value instanceof Date) {
	    	 ret =  true;
	    	//ret =  ((Date) value).toString();
	    }
	    else if (value instanceof Double)
	    	 ret =  true;
	    else if (value instanceof Float)
	    	 ret =  true;
	    else if (value instanceof Integer)
	    	 ret =  true;
	    else if (value instanceof Locale)
	    	 ret =  true;
	    else if (value instanceof Long)
	    	 ret =  true;
	    else if (value instanceof Short)
	    	 ret =  true;
	    else if (value instanceof String)
	    	 ret =  true;
	    
	   return ret;
	  }
}

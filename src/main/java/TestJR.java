import java.util.HashMap;
import java.util.Map;

import haj.com.entity.Company;
import haj.com.entity.Users;

public class TestJR {

	
	private Map<Object,Object> tagMap;
	
	public static TestJR instance(Object... tag) { 
		TestJR j = new TestJR();
	    j.tagMap = new HashMap<Object,Object>();
		j.prepare(tag);
	  return j;
	}
	
	private void prepare(Object[] tags) {
		  if (tags!=null && tags.length>0) {

	         for (int i = 1 ; i < tags.length ; i += 2) {
	           if (tags[i-1]!=null  )  {
	             tagMap.put(tags[i-1], tags[i]);
	           }
	         }
	    }		
	}

	public static void main(String[] args) {
		TestJR.instance("company",new Company(),"user",new Users());
		String x = "<p>Your email is: <b>test@a.com</b> and your password is: <b>IhyPgUV</b></p></p><p>You can change it after you have logged in.";
		System.out.println(x);
		if (x.contains("password is")) {
			String s = x.substring(0,x.indexOf("password is"));
	//		System.out.println(s);
			String s1 = x.substring(x.indexOf("password is"));
			s1 = s1.substring(s1.indexOf("</b>")+4);
	//		System.out.println(s1);
			
			String s2 = s + "password is: <b>******</b>"+s1;
			System.out.println(s2);
		}
		
	}

}

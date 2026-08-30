package haj.com.validators;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class StaticFieldChecker.
 */
public class StaticFieldChecker implements Serializable {

	/**
	 * Check id.
	 *
	 * @param idVal the id val
	 * @return true, if successful
	 */
	public static boolean checkId(String idVal) {
		if (idVal==null) return true;
		if (idVal !=null && idVal.trim().length()==0) return true;
		idVal = idVal.trim();
		if (idVal.length()<13) return false;
		int checkDigit = ((Integer.valueOf(""+ (idVal.charAt(idVal.length() - 1)))).intValue());
		String odd = "0";
		String even = "";
		int evenResult = 0;
		int result;
		for(int c = 1; c <= idVal.length(); c++){
		 if(c % 2 == 0){
		  even += idVal.charAt(c - 1);
		 } else
		   {
		    if(c == idVal.length())
		     {
		      continue;
		     } else
		      {
		       odd = ""+(Integer.valueOf(""+odd).intValue() + Integer.valueOf(""+(idVal.charAt(c - 1))));
		      }
		   }
		}
		String evenS = ""+(Integer.valueOf(even) * 2);
		for(int r = 1; r <= evenS.length(); r++)
		{
		 evenResult += Integer.valueOf(""+evenS.charAt(r - 1));
		}
		result = (Integer.valueOf(odd) + Integer.valueOf(evenResult));
		String resultS = ""+result;
		resultS = ""+(10 - (Integer.valueOf(""+(resultS.charAt(resultS.length() - 1)))).intValue());
		if(resultS.length() > 1)
		{
		  resultS = ""+resultS.charAt(resultS.length() - 1);
		}
		if(Integer.valueOf(resultS) != checkDigit)
		 {
		    return false;
		 } 
		else
		 {
		   return true;
		 }
	}

}

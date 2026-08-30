package haj.com.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// TODO: Auto-generated Javadoc
/**
 * The Class IDValidator.
 */
public class IDValidator implements ConstraintValidator<CheckID, String> {
	
	/** The id mode. */
	private IDMode idMode;

	/* (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	 */
	public void initialize(CheckID constraintAnnotation) {
		this.idMode = constraintAnnotation.value();
	}

	/* (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 */
	public boolean isValid(String object,
			ConstraintValidatorContext constraintContext) {
		if (object == null)
			return true;
		if ("".equals(object.toString())) 
			return true;
		if (idMode == IDMode.IDCHECK)
			return checkId(object.toString());
		else
			return false;
		//	return object.equals(object.toLowerCase());
	}
	

	
	/**
	 * Check id.
	 *
	 * @param idVal the id val
	 * @return true, if successful
	 */
	public boolean checkId(String idVal) {
		idVal = idVal.trim();
		if (idVal.length()<13) return false;
		if (idVal.length()>13) return false;
		idVal = idVal.trim();
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
		     try{	 
		       odd = ""+(Integer.valueOf(""+odd).intValue() + Integer.valueOf(""+(idVal.charAt(c - 1))));
		     }
		     catch (java.lang.NumberFormatException nf) {
		    	 return false;
		     }
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
package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum EmployedUnEmployedEnum.
 */
public enum EmployedUnEmployedEnum {

  /** The Employed. */
  Employed("Employed") {
		public String getRegistrationName() {
			return "employed";
		}
   } , 
  
  /** The Un employed. */
  UnEmployed("Unemployed"){
		public String getRegistrationName() {
			return "un.employed";
		}
   } ;

  /** The display name. */
  private String displayName;

  /**
   * Instantiates a new employed un employed enum.
   *
   * @param displayNameX the display name X
   */
  private EmployedUnEmployedEnum(String displayNameX)
  {
    displayName = displayNameX;
  }


  /**
   * Gets the friendly name.
   *
   * @return the friendly name
   */
  public String getFriendlyName()
  {
    return toString();
  }
  
	/**
	 * Gets the registration name.
	 *
	 * @return the registration name
	 */
	public String getRegistrationName() {
		return displayName;
	}
	
	
	  /**
  	 * Gets the id passport enum by value.
  	 *
  	 * @param value the value
  	 * @return the id passport enum by value
  	 */
  	public static final EmployedUnEmployedEnum getIdPassportEnumByValue(int value)
	  {
	    for(EmployedUnEmployedEnum status : EmployedUnEmployedEnum.values())
	    {
	       if(status.ordinal() == value)
	           return status ;
	    }

	    return null;
	  }
}

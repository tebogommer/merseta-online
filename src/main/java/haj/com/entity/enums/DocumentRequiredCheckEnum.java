package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum DocumentRequiredCheckEnum.
 */
public enum DocumentRequiredCheckEnum {

  /** Atr Deviated Workplace Skills Plan Question 1 */
  atrDeviatedWsp("Atr Deviated Workplace Skills Plan") {
		public String getRegistrationName() {
			return "atr.deviated.wsp";
		}
   };

  /** The display name. */
  private String displayName;

  /**
   * Instantiates a new id passport enum.
   *
   * @param displayNameX the display name X
   */
  private DocumentRequiredCheckEnum(String displayNameX)
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
  	public static final DocumentRequiredCheckEnum getIdPassportEnumByValue(int value)
	  {
	    for(DocumentRequiredCheckEnum status : DocumentRequiredCheckEnum.values())
	    {
	       if(status.ordinal() == value)
	           return status ;
	    }

	    return null;
	  }
}

package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum IdPassportEnum.
 */
public enum NewOrLegacyEnum {

  /** The Rsa id. */
  NewApplication("New Application") {
		public String getRegistrationName() {
			return "new.assessor.mod";
		}
   } , 
  
  /** The Passport. */
  LegacyApplication("Legacy Application"){
		public String getRegistrationName() {
			return "legacy.assessor.mod";
		}
   } ;

  /** The display name. */
  private String displayName;

  /**
   * Instantiates a new id passport enum.
   *
   * @param displayNameX the display name X
   */
  private NewOrLegacyEnum(String displayNameX)
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
  	public static final NewOrLegacyEnum getIdPassportEnumByValue(int value)
	  {
	    for(NewOrLegacyEnum status : NewOrLegacyEnum.values())
	    {
	       if(status.ordinal() == value)
	           return status ;
	    }

	    return null;
	  }
}

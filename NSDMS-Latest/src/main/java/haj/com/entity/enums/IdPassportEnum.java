package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum IdPassportEnum.
 */
public enum IdPassportEnum {

  /** The Rsa id. */
  RsaId("RSA ID") {
		public String getRegistrationName() {
			return "rsa.id.number";
		}
   } , 
  
  /** The Passport. */
  Passport("Passort Number"){
		public String getRegistrationName() {
			return "passport.number";
		}
   } ;

  /** The display name. */
  private String displayName;

  /**
   * Instantiates a new id passport enum.
   *
   * @param displayNameX the display name X
   */
  private IdPassportEnum(String displayNameX)
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
  	public static final IdPassportEnum getIdPassportEnumByValue(int value)
	  {
	    for(IdPassportEnum status : IdPassportEnum.values())
	    {
	       if(status.ordinal() == value)
	           return status ;
	    }

	    return null;
	  }
}

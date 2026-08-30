package haj.com.gpservices;

// TODO: Auto-generated Javadoc
/**
 * The Enum IdPassportEnum.
 */
public enum GPAddressTypeEnum {

 
	POSTAL("POSTAL") {
		public String getGPName() {
			return "POSTAL";
		}
   } , 

  PHYSICAL("PHYSICAL"){
		public String getGPName() {
			return "PHYSICAL";
		}
   } ; 



  /** The display name. */
  private String displayName;

  /**
   * Instantiates a new id passport enum.
   *
   * @param displayNameX the display name X
   */
  private GPAddressTypeEnum(String displayNameX)
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
	public String getGPName() {
		return displayName;
	}
	
	
	  /**
  	 * Gets the id passport enum by value.
  	 *
  	 * @param value the value
  	 * @return the id passport enum by value
  	 */
  	public static final GPAddressTypeEnum getIdPassportEnumByValue(int value)
	  {
	    for(GPAddressTypeEnum status : GPAddressTypeEnum.values())
	    {
	       if(status.ordinal() == value)
	           return status ;
	    }

	    return null;
	  }
}

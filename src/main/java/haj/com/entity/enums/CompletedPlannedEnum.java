package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum CompletedPlannedEnum.
 */
public enum CompletedPlannedEnum {

  /** The Completed. */
  Completed("Completed Training") {
		public String getRegistrationName() {
			return "completed.training";
		}
   } , 
  
  /** The Planned. */
  Planned("Planned Training"){
		public String getRegistrationName() {
			return "planned.training";
		}
   } ;

  /** The display name. */
  private String displayName;

  /**
   * Instantiates a new completed planned enum.
   *
   * @param displayNameX the display name X
   */
  private CompletedPlannedEnum(String displayNameX)
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
  	public static final CompletedPlannedEnum getIdPassportEnumByValue(int value)
	  {
	    for(CompletedPlannedEnum status : CompletedPlannedEnum.values())
	    {
	       if(status.ordinal() == value)
	           return status ;
	    }

	    return null;
	  }
}

package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum PivotNonPivotEnum.
 */
public enum PlannedImplementedEnum {

  /** The Pivotal. */
  Planned("Planned") {
		public String getRegistrationName() {
			return "planned.training";
		}
   } , 
  
  /** The Non pivotal. */
  Implemented("Implemented"){
		public String getRegistrationName() {
			return "implemented.training";
		}
   } ;

  /** The display name. */
  private String displayName;

  /**
   * Instantiates a new pivot non pivot enum.
   *
   * @param displayNameX the display name X
   */
  private PlannedImplementedEnum(String displayNameX)
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
    return displayName;
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
  	 * Gets the pivot non pivot enum by value.
  	 *
  	 * @param value the value
  	 * @return the pivot non pivot enum by value
  	 */
  	public static final PlannedImplementedEnum getPivotNonPivotEnumByValue(int value)
	  {
	    for(PlannedImplementedEnum status : PlannedImplementedEnum.values())
	    {
	       if(status.ordinal() == value)
	           return status ;
	    }

	    return null;
	  }	
}

package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum IdPassportEnum.
 */
public enum WorkplaceApprovalTypeEnum {

  /** The Rsa id. */
  Qualification("Qualification") {
		public String getRegistrationName() {
			return "qualificationId";
		}
   } , 
  
  /** The Passport. */
  Trade("Trade"){
		public String getRegistrationName() {
			return "trade";
		}
   } , 
  
  /** The Passport. */
  Learnership("Learnership"){
		public String getRegistrationName() {
			return "learning.programmes";
		}
   }, 
  
  /** The Passport. */
  SkillsSet("Skills Set"){
		public String getRegistrationName() {
			return "skills.set";
		}
   }, 
  
  /** The Passport. */
  SkillsProgram("Skills Program"){
		public String getRegistrationName() {
			return "skills.program";
		}
   }, 
  
  /** The Passport. */
  UnitStandard("Unit Standard"){
		public String getRegistrationName() {
			return "unit.standard";
		}
   };

  /** The display name. */
  private String displayName;

  /**
   * Instantiates a new id passport enum.
   *
   * @param displayNameX the display name X
   */
  private WorkplaceApprovalTypeEnum(String displayNameX)
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
  	public static final WorkplaceApprovalTypeEnum getWorkplaceApprovalTypeEnumByValue(int value)
	  {
	    for(WorkplaceApprovalTypeEnum status : WorkplaceApprovalTypeEnum.values())
	    {
	       if(status.ordinal() == value)
	           return status ;
	    }

	    return null;
	  }
}

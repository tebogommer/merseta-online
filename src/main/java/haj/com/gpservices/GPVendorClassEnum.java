package haj.com.gpservices;

// TODO: Auto-generated Javadoc
/**
 * The Enum IdPassportEnum.
 */
public enum GPVendorClassEnum {

	/*
ACM - added for JIRA #100	 
AUTO
METAL
MOTOR
NEW TYRE
PLASTICS
SETA
UNKNOWN
Z-SETA
	 */
 
  AUTO("AUTO") {
		public String getGPName() {
			return "AUTO";
		}
   } , 

  METAL("METAL") {
		public String getGPName() {
			return "METAL";
		}
   } , 

  MOTOR("MOTOR") {
		public String getGPName() {
			return "MOTOR";
		}
   } ,   
 
  NEWTYRE("NEW TYRE") {
		public String getGPName() {
			return "NEW TYRE";
		}
   } ,  
  
  PLASTICS("PLASTICS") {
		public String getGPName() {
			return "PLASTICS";
		}
   } ,  
 
  UNKNOWN("UNKNOWN") {
		public String getGPName() {
			return "UNKNOWN";
		}
   } , 
 
  SETA("SETA") {
		public String getGPName() {
			return "SETA";
		}
   } ,   
  
  ZSETA("Z-SETA"){
		public String getGPName() {
			return "Z-SETA";
		}
   } ,   
  
  ACM("ACM"){
		public String getGPName() {
			return "ACM";
		}
   } ; 



  /** The display name. */
  private String displayName;

  /**
   * Instantiates a new id passport enum.
   *
   * @param displayNameX the display name X
   */
  private GPVendorClassEnum(String displayNameX)
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
  	public static final GPVendorClassEnum getIdPassportEnumByValue(int value)
	  {
	    for(GPVendorClassEnum status : GPVendorClassEnum.values())
	    {
	       if(status.ordinal() == value)
	           return status ;
	    }

	    return null;
	  }
}

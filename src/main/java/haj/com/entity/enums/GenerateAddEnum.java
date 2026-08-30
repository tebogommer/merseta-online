package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum IdPassportEnum.
 */
public enum GenerateAddEnum {

  /** Generate Certificate. */
	GenerateCertificate ("Generate Certificate") {
		public String getRegistrationName() {
			return "generate.certificate";
		}
   } , 
  
  /** Add Certificate Details. */
	AddCertificateDetails("Add Certificate Details"){
		public String getRegistrationName() {
			return "add.certificate.details";
		}
   } ;

  /** The display name. */
  private String displayName;

  /**
   * Instantiates a new id passport enum.
   *
   * @param displayNameX the display name X
   */
  private GenerateAddEnum(String displayNameX)
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
  	public static final GenerateAddEnum getIdPassportEnumByValue(int value)
	  {
	    for(GenerateAddEnum status : GenerateAddEnum.values())
	    {
	       if(status.ordinal() == value)
	           return status ;
	    }

	    return null;
	  }
}

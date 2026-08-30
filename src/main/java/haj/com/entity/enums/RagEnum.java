package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum RagEnum.
 */
public enum RagEnum {

	/** The Red N. */
	RedN("Red"){
		public String getRegistrationName() {
			return "red";
		}
   } , 
	
	/** The Amber N. */
	AmberN("Amber"){
		public String getRegistrationName() {
			return "amber";
		}
   } , 
	
	/** The Red. */
	Red("Red - No Customer Detriment"){
		public String getRegistrationName() {
			return "red-no.customer.detriment";
		}
   } , 
	
	/** The Amber. */
	Amber("Amber - No Customer Detriment"){
		public String getRegistrationName() {
			return "amber-no.customer.detriment";
		}
   } , 
	
	/** The Green. */
	Green("Green"){
		public String getRegistrationName() {
			return "green";
		}
   } , 
	
	/** The Red CD. */
	RedCD("Red - Customer Detriment"){
		public String getRegistrationName() {
			return "red-customer.detriment";
		}
   } ,
	
	/** The Amber CD. */
	AmberCD("Amber - Customer Detriment"){
		public String getRegistrationName() {
			return "amber-customer.detriment";
		}
   } , 
	
	/** The na. */
	NA("Not Applicable"){
		public String getRegistrationName() {
			return "not.applicable";
		}
   } ;

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new rag enum.
	 *
	 * @param displayNameX the display name X
	 */
	private RagEnum(String displayNameX) {
		displayName = displayNameX;
	}

	/**
	 * Gets the friendly name.
	 *
	 * @return the friendly name
	 */
	public String getFriendlyName() {
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

}

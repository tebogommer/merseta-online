package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum CompanyUserTypeEnum.
 */
public enum CompanyUserTypeEnum {

	/** The Company. */
	Company("Company") {
		public String getRegistrationName() {
			return "company";
		}
	},

	/** The User. */
	User("User") {
		public String getRegistrationName() {
			return "user.select";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new company user type enum.
	 *
	 * @param displayNameX the display name X
	 */
	private CompanyUserTypeEnum(String displayNameX) {
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

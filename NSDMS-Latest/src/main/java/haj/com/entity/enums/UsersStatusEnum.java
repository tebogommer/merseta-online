package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum UsersStatusEnum.
 */
public enum UsersStatusEnum {

	/** The Active. */
	Active("Active") {
		public String getRegistrationName() {
			return "active";
		}
	},

	/** The In active. */
	InActive("In-Active") {
		public String getRegistrationName() {
			return "in-active";
		}
	},

	/** The Email not confrimed. */
	EmailNotConfrimed("Email Not Confirmed") {
		public String getRegistrationName() {
			return "email.not.confrimed";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new users status enum.
	 *
	 * @param displayNameX the display name X
	 */
	private UsersStatusEnum(String displayNameX) {
		displayName = displayNameX;
	}

	/**
	 * Gets the friendly name.
	 *
	 * @return the friendly name
	 */
	public String getFriendlyName() {
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

}

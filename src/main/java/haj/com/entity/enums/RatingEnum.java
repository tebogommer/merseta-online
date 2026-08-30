package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum RatingEnum.
 */
public enum RatingEnum {

	/** The toalargeextent. */
	TOALARGEEXTENT("To a large extent") {
		public String getRegistrationName() {
			return "1.to.a.large.extent";
		}
	},

	/** The toalimitedexten. */
	TOALIMITEDEXTEN("To a limited extent"){
		public String getRegistrationName() {
			return "2.to.a.limited.extent";
		}
	},
	
	/** The neutral. */
	NEUTRAL("Neutral") {
		public String getRegistrationName() {
			return "3.neutral";
		}
	},
	
	/** The notreally. */
	NOTREALLY("Not really") {
		public String getRegistrationName() {
			return "4.not.really";
		}
	},
	
	/** The notatall. */
	NOTATALL("Not at All") {
		public String getRegistrationName() {
			return "5.not.at.all";
		}
	};
	

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new rating enum.
	 *
	 * @param displayNameX the display name X
	 */
	private RatingEnum(String displayNameX) {
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

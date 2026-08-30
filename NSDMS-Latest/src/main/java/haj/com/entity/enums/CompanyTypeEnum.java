package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum CompanyTypeEnum.
 */
public enum CompanyTypeEnum {

	/** The sdf. */
	SDF("Skills Development Facilitator") {
		public String getRegistrationName() {
			return "skills.development.facilitator";
		}
	},

	/** The company. */
	COMPANY("Company") {
		public String getRegistrationName() {
			return "company";
		}
	},

	/** The tp. */
	TP("Training Provider") {
		public String getRegistrationName() {
			return "training.provider";
		}
	},
	
	/** The am. */
	AM("Assesor Modirator") {
		public String getRegistrationName() {
			return "assessor.moderator";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new company type enum.
	 *
	 * @param displayNameX the display name X
	 */
	private CompanyTypeEnum(String displayNameX) {
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

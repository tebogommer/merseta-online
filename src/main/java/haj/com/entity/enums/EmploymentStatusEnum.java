package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum EmploymentStatusEnum.
 */
public enum EmploymentStatusEnum {

	/** The Employed. */
	Employed("Employed") {
		@Override
		public String getRegistrationName() {
			return "employed";
		}
	},

	/** The Un employed. */
	UnEmployed("Unemployed") {
		@Override
		public String getRegistrationName() {
			return "unemployed";
		}
	},

	/** The Un employed. */
	NoLongerAtCompany("No Longer at Company") {
		@Override
		public String getRegistrationName() {
			return "no.longer.at.company";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new employment status enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private EmploymentStatusEnum(String displayNameX) {
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

	/**
	 * Gets the employment status enum by value.
	 *
	 * @param value
	 *            the value
	 * @return the employment status enum by value
	 */
	public static final EmploymentStatusEnum getEmploymentStatusEnumByValue(int value) {
		for (EmploymentStatusEnum status : EmploymentStatusEnum.values()) {
			if (status.ordinal() == value)
				return status;
		}

		return null;
	}

}

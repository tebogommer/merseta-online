package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum IdPassportEnum.
 */
public enum DateScheduleEnum {

	Verification("Verification of assessment") {
		public String getRegistrationName() {
			return "Verification of assessment";
		}
	},

	Postal("Postal") {
		public String getRegistrationName() {
			return "Postal Address";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new id passport enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private DateScheduleEnum(String displayNameX) {
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

	/**
	 * Gets the id passport enum by value.
	 *
	 * @param value
	 *            the value
	 * @return the id passport enum by value
	 */
	public static final DateScheduleEnum getIdPassportEnumByValue(String value) {
		Integer val = 0;
		try {
			val = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		for (DateScheduleEnum status : DateScheduleEnum.values()) {
			if (status.ordinal() == val) return status;
		}
		return null;
	}
}

package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum IdPassportEnum.
 */
public enum DocumetFileTypeEnum {

	ZIP("zip") {
		public String getRegistrationName() {
			return "zip";
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
	private DocumetFileTypeEnum(String displayNameX) {
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
	public static final DocumetFileTypeEnum getIdPassportEnumByValue(String value) {
		Integer val = 0;
		try {
			val = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		for (DocumetFileTypeEnum status : DocumetFileTypeEnum.values()) {
			if (status.ordinal() == val) return status;
		}
		return null;
	}
}

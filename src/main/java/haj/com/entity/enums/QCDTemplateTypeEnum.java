package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum IdPassportEnum.
 */
public enum QCDTemplateTypeEnum {

	NewDevelopment("New Development") {
	},

	ReAlignment("Re-Alignment") {
	},

	Review("Review") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new id passport enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private QCDTemplateTypeEnum(String displayNameX) {
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
	 * Gets the id passport enum by value.
	 *
	 * @param value
	 *            the value
	 * @return the id passport enum by value
	 */
	public static final QCDTemplateTypeEnum getIdPassportEnumByValue(int value) {
		for (QCDTemplateTypeEnum status : QCDTemplateTypeEnum.values()) {
			if (status.ordinal() == value) return status;
		}

		return null;
	}
}

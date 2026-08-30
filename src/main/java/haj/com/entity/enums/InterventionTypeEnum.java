package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum RatingEnum.
 */
public enum InterventionTypeEnum {

	Apprenticeship("Apprenticeship") {
	},

	Learnership("Learnership") {
	},

	Other("Other") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new rating enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private InterventionTypeEnum(String displayNameX) {
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
}

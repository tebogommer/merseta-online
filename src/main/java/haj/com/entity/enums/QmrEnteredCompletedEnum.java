package haj.com.entity.enums;

/**
 * The Enum QmrEnteredCompletedEnum.
 */
public enum QmrEnteredCompletedEnum {

	Entered("Entered") {
	}, 
	Completed("Completed") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private QmrEnteredCompletedEnum(String displayNameX) {
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

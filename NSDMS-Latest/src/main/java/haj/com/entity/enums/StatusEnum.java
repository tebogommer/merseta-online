package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum AnswerTypeEnum.
 */
public enum StatusEnum {

	NotStarted("Not-Started") {
	},
	Underway("Underway") {
	},
	Completed("Completed") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new wsp type enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private StatusEnum(String displayNameX) {
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

package haj.com.entity.enums;

public enum OpenClosedEnum {

	/**0*/
	Open("Open") {
	},
	/**1*/
	Closed("Closed") {
	};
	
	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new wsp type enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private OpenClosedEnum(String displayNameX) {
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

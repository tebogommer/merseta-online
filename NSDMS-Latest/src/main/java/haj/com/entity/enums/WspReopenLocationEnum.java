package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum IdPassportEnum.
 */
public enum WspReopenLocationEnum {

	Draft("To SDF first process") {
	},
	SdfSignoff("To SDF for sign off") {
	},
	InProgress("To MerSETA first process") {
	},
	DocumentUpload("To SDF to upload document") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new id passport enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private WspReopenLocationEnum(String displayNameX) {
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

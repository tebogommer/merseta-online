package haj.com.entity.enums;

/**
 * The Enum SdpCompanyActionListEnum.
 */
public enum SdpCompanyActionListEnum {

	NewSDP("New SDP Contact Person") {
	},
	UpdateDesignation("Update SDP Contact Person Designation") {
	},
	RemoveSDP("Remove SDP Contact Person") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new age group enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private SdpCompanyActionListEnum(String displayNameX) {
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

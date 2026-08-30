package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum ApprovalEnum.
 */
public enum UrbanRuralEnum {

	/*0*/
	Urban("Urban") {
	},
	/*1*/
	Rural("Rural") {
	};
	
	/*
	 * Urban_Rural_ID 2 Rural Intervention
	 * Urban_Rural_ID 1 Unknown
	 * Urban_Rural_ID 3 Urban Intervention
	 */

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private UrbanRuralEnum(String displayNameX) {
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

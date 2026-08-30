package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum ApprovalEnum.
 */
public enum ModerationEnum {

	Upheld("Upheld") {
	},
	Reject("Reject") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private ModerationEnum(String displayNameX) {
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

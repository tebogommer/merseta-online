package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum ApprovalEnum.
 */
public enum RelationTypeEnum {

	NewsLetter("Newsletter") {
	},
	Survey("Survey") {
	},
	Notice("Notice") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private RelationTypeEnum(String displayNameX) {
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

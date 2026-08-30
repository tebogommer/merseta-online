package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum ApprovalEnum.
 */
public enum LearnerChangeTypeEnum {

	ChangeOfProgramQalification("Change of Qualification") {
	},
	ChangeOfProgramTrade("Change of Trade") {
	},
	ChangeCommencementDate("Change Commencement Date") {
	},
	ChangeNonCreditBearingTitle("Change Non Credit Bearing Title") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private LearnerChangeTypeEnum(String displayNameX) {
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

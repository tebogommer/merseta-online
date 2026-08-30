package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum MeetingTypeEnum.
 */
public enum MeetingTypeEnum {

	SkillsDevelopmentProvider("Skills Development Provider") {
	},
	SkillsDevelopmentProviderWithdrawal("Skills Development Provider Withdrawal") {
	},
	AssessorModeratorRegistrationApproval("Assessor/Moderator Registration Approval") {
	},
	ContractRescissionLearnership("Contract Rescission/Learnership") {
	},
	SkillsProgrammeSetRegistration("Skills Programme/Set Registration") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private MeetingTypeEnum(String displayNameX) {
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

package haj.com.entity.enums;

public enum ActionPlanValidiationTypeEnum {

	/**0*/
	LevyPayments("Levy Payments") {
	},
	/**1*/
	SubmissionWspAtr("Submission Of WSP/ATR") {
	},
	/**2*/
	LearnersEmployedBelowRate("Learners Employed below required level for category") {
	},
	/**3*/
	LearnersPassBelowRate("Learners Pass Rate below required level for category") {
	},
	/**4*/
	TrainingCommittee("Training Committee") {
	};
	
	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new wsp type enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private ActionPlanValidiationTypeEnum(String displayNameX) {
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

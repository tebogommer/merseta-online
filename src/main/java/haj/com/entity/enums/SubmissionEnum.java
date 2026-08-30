package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum ApprovalEnum.
 */
public enum SubmissionEnum {
	Application("Application") {
	},
	NotSubmitted("Not Submitted") {
	},
	Submitted("Submitted") {
	},
	ForReview("Submitted For Review") {
	},
	ForApproval("Submitted For Approval") {
	},
	ForFinalApproval("Submitted For Approval") {
	},
	Completed("Completed") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private SubmissionEnum(String displayNameX) {
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

package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum ApprovalEnum.
 */
public enum LearnerTransferApproval {

	WithCompany("With New Company Representative") {
	},
	WithLearner("With Learner") {
	},
	NormalWorkflow("Following Workflow") {
	},
	WithInitiator("WithInitiator") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private LearnerTransferApproval(String displayNameX) {
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

package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum WspTypeEnum.
 */
public enum TerminationTypeEnum {

	MutualSidedTermination("Mutual Agreement Termination") {
	},
	OneSidedTermination("One-Sided Agreement Termination") {
	},
	SystemIdentifiedExpiryTermination("System Identified Expiry Termination") {
	},
	DeceasedLearnerTermination("Deceased Learner Agreement Termination") {
	},
	TradeTestTermination("System Identified Failed Trade Test Termination") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new wsp type enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private TerminationTypeEnum(String displayNameX) {
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

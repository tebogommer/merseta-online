package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum ApprovalEnum.
 */
public enum NewExistingLearnershipEnum {

	/** New learnership. */
	New("New learnership") {
		@Override
		public String getRegistrationName() {
			return "New Learnership";
		}
	},

	/** Existing Learnership */
	Existing("Learnership to replace an existing learnership") {
		@Override
		public String getRegistrationName() {
			return "in-active";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private NewExistingLearnershipEnum(String displayNameX) {
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

	/**
	 * Gets the registration name.
	 *
	 * @return the registration name
	 */
	public String getRegistrationName() {
		return displayName;
	}

}

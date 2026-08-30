package haj.com.entity.enums;

public enum LostTimeReason {

	AbsentWithoutPermission("Absent without permission") {
	},
	Suspension5("Suspension (maximum 5 days)") {
	},
	Suspension60("Suspension (6 - 30 days)") {
	},
	Exceeding30Days("Exceeding 30 day sick period in the respective apprenticeship year") {
	},
	MaternityLeave("Maternity or paternity leave") {
	},
	SpecialLeave("Special leave") {
	},
	ExtensionOfAgreement("Extension of Agreement ") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private LostTimeReason(String displayNameX) {
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

package haj.com.entity.enums;

public enum ProviderSusActionsEnum {

	/** 0 */
	SuspendDeactivate("Suspend / De-activate Application") {
	},
	/** 1 */
	RemoveSuspension("Remove Suspension") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private ProviderSusActionsEnum(String displayNameX) {
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

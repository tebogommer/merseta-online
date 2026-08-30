package haj.com.entity.enums;

public enum CompanyLearnersTransferRecommendation {

	Transfer_Continues("Transfer continues ") {},
	Transfer_Does_Not_Continue("Transfer does not continue ") {};
	
	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new wsp type enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private CompanyLearnersTransferRecommendation(String displayNameX) {
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
	 * Gets the id passport enum by value.
	 *
	 * @param value
	 *            the value
	 * @return the id passport enum by value
	 */
	public static final CompanyLearnersTransferRecommendation getIdPassportEnumByValue(int value) {
		for (CompanyLearnersTransferRecommendation status : CompanyLearnersTransferRecommendation.values()) {
			if (status.ordinal() == value) return status;
		}

		return null;
	}
}

package haj.com.entity.enums;

public enum ReportPropertiesEnum {

	/** 0 */
	Levy_Analysis_Scheme_Year_Chamber("Levy Analysis By Scheme Year and Chamber") {
	},
	/** 1 */
	Grant_Project_Expenses_Inception("Grants And Project Expenses From Inception") {
	},
	/** 2 */
	MG_Transactions("MG Transactions") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new ReportPropertiesEnum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private ReportPropertiesEnum(String displayNameX) {
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
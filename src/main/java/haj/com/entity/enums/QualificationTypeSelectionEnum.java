package haj.com.entity.enums;

public enum QualificationTypeSelectionEnum {

	/**0*/
	Qualification("Qualification") {
	},
	/**1*/
	SkillsProgram("Skills Program") {
	},
	/**2*/
	SkillsSet("Skills Set") {
	},
	/**3*/
	NonCreditBearingIntervationTitle("Non-Credit Bearing Intervation Title") {
	},
	/**4*/
	UnitStandards("Unit Standard") {
	},
	/**5*/
	Learnership("Learnership") { 
	};
	
	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new wsp type enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private QualificationTypeSelectionEnum(String displayNameX) {
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

package haj.com.entity.enums;

public enum QmrTypeSelectionEnum {

	/** 0 */
	LEARNERSHIP("LEARNERSHIP") {
	},
	/** 1 */
	BURSARIES("BURSARIES") {
	},
	/** 2 */
	INTERNSHIPS("INTERNSHIPS") {
	},
	/** 3 */
	SKILLS_PROGRAMMES("SKILLS PROGRAMMES") {
	},
	/** 4 */
	ARTISAN("ARTISAN") {
	},
	/** 5 */
	CANDIDACY_PROGRAMME("CANDIDACY PROGRAMME") { 
	},
	/** 6 */
	RPL("RECOGNITION OF PRIOR LEARNING (RPL)") { 
	},
	/** 7 */
	TVET_STUDENT("TVET STUDENT") { 
	},
	/** 8 */
	UNIVERSITY_STUDENT_PLACEMENT("UNIVERSITY STUDENT PLACEMENT") { 
	},
	/** 9 */
	LECTURER_DEVELOPMENT_PROGRAMMES("LECTURER DEVELOPMENT PROGRAMMES") { 
	},
	/** 10 */
	AET_PROGRAMMES("AET PROGRAMMES") { 
	},
	/** 11 */
	INDIVIDUAL_UNIT_STANDARD("INDIVIDUAL UNIT STANDARD") { 
	};
	
	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new wsp type enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private QmrTypeSelectionEnum(String displayNameX) {
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

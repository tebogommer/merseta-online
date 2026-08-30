package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum ApprovalEnum.
 */
public enum DhetFileNumberEnum {

	/** 0 */
	NrldFile21("NrldFile21") {
	},
	/** 1 */
	NrldFile22("NrldFile22") {
	},
	/** 2 */
	NrldFile23("NrldFile23") {
	},
	/** 3 */
	NrldFile24("NrldFile24") {
	},
	/** 4 */
	NrldFile25("Approved") {
	},
	/** 5 */
	NrldFile26("Approved") {
	},
	/** 6 */
	NrldFile27("Approved") {
	},
	/** 7 */
	NrldFile28("NrldFile28") {
	},
	/** 8 */
	NrldFile29("NrldFile29") {
	},
	/** 9 */
	NrldFile30("NrldFile30") {
	},
	/** 10 */
	SetmisFile100("SetmisFile100 Provider") {
	},
	/** 11 */
	SetmisFile200("SetmisFile200 Employer") {
	},
	/** 12 */
	SetmisFile304("SetmisFile304 Non NQF Intervention") {
	},
	/** 13 */
	SetmisFile400("SetmisFile400 Persons") {
	},
	/** 14 */
	SetmisFile401("SetmisFile401 Person Designation") {
	},
	/** 15 */
	SetmisFile500("SetmisFile500 Learnership Enrolment") {
	},
	/** 16 */
	SetmisFile501("SetmisFile501 Qualification Enrolment") {
	},
	/** 17 */
	SetmisFile502("SetmisFile502 Non NQF intervention Enrolments") {
	},
	/** 18 */
	SetmisFile503("SetmisFile503 Unit Standard Enrolment") {
	},
	/** 19 */
	SetmisFile505("SetmisFile505 Trade Test") {
	},
	/** 20 */
	SetmisFile506("SetmisFile506 Internship Placement") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private DhetFileNumberEnum(String displayNameX) {
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

package haj.com.entity.enums;

/**
 * The Enum ApprovalEnum.
 */
public enum NsdpReportRunTypeEnum {

	/** 0 */
	GrantsApprovedSmallCompany("Grant Approved For Small Companies") {
	},
	/** 1 */
	GrantsApprovedMediumCompany("Grant Approved For Medium Companies") {
	},
	/** 2 */
	GrantsApprovedLargeCompany("Grant Approved For Large Companies") {
	},
	/** 3 */
	QmrTvetEntered("QMR - TEVT Entered") {
	},
	/** 4 */
	QmrTvetCompleted("QMR - TEVT Completed") {
	},
	/** 5 */
	QmrArpl("QMR - ARPL") {
	},
	/** 6 */
	QmrUnemployedLearnershipsEntered("QMR - Unemployed Learnerships Entered") {
	},
	/** 7 */
	QmrUnemployedLearnershipsCompleted("QMR - Unemployed Learnerships Completed") {
	},
	/** 8 */
	QmrWorkingLearnershipsEntered("QMR - Working Learnerships Entered") {
	},
	/** 9 */
	QmrWorkingLearnershipsCompleted("QMR - Working Learnerships Completed") {
	},
	/** 10 */
	QmrUniversityStudentPlacementEntered("QMR - University Student Placement Entered") {
	},
	/** 11 */
	QmrUniversityStudentPlacementCompleted("QMR - University Student Placement Completed") {
	},
	/** 12 */
	QmrArtisanEntered("QMR - Artisan Entered") {
	},
	/** 13 */
	QmrArtisanCompleted("QMR - Artisan Completed") {
	},
	/** 14 */
	QmrUnemployedInternshipEntered("QMR - Unemployed Internship Entered") {
	},
	/** 15 */
	QmrUnemployedInternshipCompleted("QMR - Unemployed Internship Completed") {
	},
	/** 16 */
	QmrAetProgrammeEntered("QMR - AET Programme Entered") {
	},
	/** 17 */
	QmrAetProgrammeCompleted("QMR - AET Programme Completed") {
	},
	/** 18 */
	QmrUnemployedSkillsProgrammeEntered("QMR - Unemployed Skills Programme Entered") {
	},
	/** 19 */
	QmrUnemployedSkillsProgrammeCompleted("QMR - Unemployed Skills Programme Completed") {
	},
	/** 20 */
	QmrWorkingSkillsProgrammeEntered("QMR - Working Skills Programme Entered") {
	},
	/** 21 */
	QmrWorkingSkillsProgrammeCompleted("QMR - Working Skills Programme Completed") {
	},
	/** 22 */
	QmrCandidacyProgrammeEntered("QMR - Candidacy Programme Entered") {
	},
	/** 23 */
	QmrCandidacyProgrammeCompleted("QMR - Candidacy Programme Completed") {
	},
	/** 24 */
	QmrUnemployedBursaryEnteredNew("QMR - Unemployed Bursary Entered (New Entries)") {
	},
	/** 25 */
	QmrUnemployedBursaryEnteredContinue("QMR - Unemployed Bursary Entered (Continue Entries)") {
	},
	/** 26 */
	QmrUnemployedBursaryCompleted("QMR - Unemployed Bursary Completed") {
	},
	/** 27 */
	QmrWorkingBursaryEnteredNew("QMR - Working Bursary Entered (New Entries)") {
	},
	/** 28 */
	QmrWorkingBursaryEnteredContinue("QMR - Working Bursary Entered (Continue Entries)") {
	},
	/** 29 */
	QmrWorkingBursaryCompleted("QMR - Working Bursary Completed") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private NsdpReportRunTypeEnum(String displayNameX) {
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

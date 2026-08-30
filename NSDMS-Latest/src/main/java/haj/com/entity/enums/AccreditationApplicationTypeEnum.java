package haj.com.entity.enums;

import java.util.ArrayList;
import java.util.List;

public enum AccreditationApplicationTypeEnum {

	/**0*/
	ACCREDITATIONAPPROVAL("Primary Accreditation (accreditation for merSETA scope qualification/s)") {},
	/**1*/
	REACCREDITATIONREAPPROVAL("Re-Accreditation or Re-Approval") {},
	/**2*/
	EXTENSIONOFSCOPE("Extension of Accreditation (merSETA Providers)") {},
	/**3*/
	NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL("Learning Programme Approval (non-merSETA primary provider)") {},
	/**4*/
	QCTOSkillsDevelopmentProvider("QCTO Skills Development Provider") { },
	/**5*/
	QCTOTradeTestCentre("QCTO Trade Test Centre") { },
	/**6*/
	Non_MerSETA_Focused_Provider("Non-merSETA Scope Provider"){},
	/**7*/
	Training_and_Assessment_Site("Training and Assessment OR Assessment Only Site"){},//This is used for configuring self evaluation
	//Assessment_Only_Site("Assessment Only Site"){},
	/**8*/
	EOS_RA_LP(" Extension of Scope, Re-accreditation/Re-approval or Learning Programme Approval"){};//This is used for configuring self evaluation
	
	/*
	 * To Register from login:
	 * (0) ACCREDITATIONAPPROVAL
	 * (3) NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL
	 * (4) QCTOSkillsDevelopmentProvider
	 * (5) QCTOTradeTestCentre
	 * (6) Non_MerSETA_Focused_Provider
	 * 
	 * For re-accrediiation
	 * (1) REACCREDITATIONREAPPROVAL
	 * 
	 */
	
	
	/*
	 * SETMIS
	 * (0) ACCREDITATIONAPPROVAL -> 510
	 * (3) NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL -> 510
	 * (1) REACCREDITATIONREAPPROVAL -> 511
	 * (4) QCTOSkillsDevelopmentProvider -> 501
	 * (5) QCTOTradeTestCentre -> 501
	 * (6) Non_MerSETA_Focused_Provider -> 501
	 * 512 -> De-accreditation 
	 * 514 -> Withdrawn applitaion
	 * 515 -> Rejected Status
	 */
	
	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new wsp type enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private AccreditationApplicationTypeEnum(String displayNameX) {
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
	public static final AccreditationApplicationTypeEnum getIdPassportEnumByValue(int value) {
		for (AccreditationApplicationTypeEnum status : AccreditationApplicationTypeEnum.values()) {
			if (status.ordinal() == value) return status;
		}
		return null;
	}
	
	public static List<AccreditationApplicationTypeEnum> getApplicationSiteAllocation() {
		List<AccreditationApplicationTypeEnum> up = new ArrayList<>();
		up.add(AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL); 
		up.add(AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL);
		return up;
	}
}

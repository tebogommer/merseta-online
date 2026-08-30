package haj.com.entity.enums;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Enum ApprovalEnum.
 */
public enum LearnerStatusEnum {
	
	/**
	 * Ordinal Value: 0
	 */
	Application("Application") {
	},
	/**
	 * Ordinal Value: 1
	 */
	Registered("Registered") {
	},
	/**
	 * Ordinal Value: 2
	 */
	TransferPendingApproval("Transfer Pending Approval") {
	},
	/**
	 * Ordinal Value: 3
	 */
	Terminated("Terminated") {
	},
	/**
	 * Ordinal Value: 4
	 */
	PendingTermination("Pending Termination") {
	},
	/**
	 * Ordinal Value: 5
	 */
	PendingChangeApproval("Pending Change Approval") {
	},
	/**
	 * Ordinal Value: 6
	 */
	Completed("Completed") {
	},
	/**
	 * Ordinal Value: 7
	 */
	PendingTradeTest("Pending Trade Test") {
	},
	/**
	 * Ordinal Value: 8
	 */
	PendingARPLTradeTest("Pending ARPL Trade Test") {
	},
	/**
	 * Ordinal Value: 9
	 */
	PreApproved("Pending Review Approval") {
	},
	/**
	 * Ordinal Value: 10
	 */
	QualificationObtained("Qualification Obtained") {
	},
	/**
	 * Ordinal Value: 11
	 */
	PendingNonMerSetaQualificationApproval("Pending non-merSETA Qualification Approval") {
	},
	/**
	 * Ordinal Value: 12
	 */
	PendingVerificationOfAssesmentApproval("Pending Verification of Assesment Approval") {
	},
	/**
	 * Ordinal Value: 13
	 */
	CompletionLetterApproval("Pending Completion Letter Approval") {
	},
	/**
	 * Ordinal Value: 14
	 */
	Withdrawn("Withdrawn") {
	},
	/**
	 * Ordinal Value: 15
	 */
	Rejected("Rejected") {
	},
	/**
	 * Ordinal Value: 16
	 */
	Achieved("Achieved") {
	},
	/**
	 * Ordinal Value: 17
	 */
	PendingLostTime("Pending Lost Time") {
	};

	/*	Information for SETMIS
		
		Learners appear on reporting --> Ordinal Value: 1, 2, 4, 5, 6, 7, 8, 10, 11, 12, 13
		
		Enrolment_Status_Id 2 Achieved --> Ordinal Value: 6 , 16 
		Enrolment_Status_Id 15 Certificated --> Ordinal Value: 10 
		Enrolment_Status_Id 61 Completed - To Be Assessed --> Ordinal Value:
		Enrolment_Status_Id 10 Discontinued --> Ordinal Value: 3, 14, 15
		Enrolment_Status_Id 3 Enrolled --> Ordinal Value: 1, 2, 4, 5 , 7, 8, 9, 11, 12, 13
		Enrolment_Status_Id 50 Non-Endorsed Achievement --> Ordinal Value: N/A
		Enrolment_Status_Id 31 Waiting for WIL --> Ordinal Value: N/A
		Enrolment_Status_Id 32 WIL in progress --> Ordinal Value: N/A
		
		, case  
    	when cl.learner_status in (6) then SUBSTRING('2', 1, 3) 
    	when cl.learner_status in (10) then SUBSTRING('15', 1, 3) 
    	when cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then SUBSTRING('3', 1, 3) 
    	Else SUBSTRING('', 1, 3) 
    	end as enrolmentStatusId  
	 */

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private LearnerStatusEnum(String displayNameX) {
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
	
	public static List<LearnerStatusEnum> getTranchStatusList() {
		List<LearnerStatusEnum> list = new ArrayList<>();
		list.add(LearnerStatusEnum.Registered);
		list.add(LearnerStatusEnum.TransferPendingApproval);
		list.add(LearnerStatusEnum.PendingChangeApproval);
		list.add(LearnerStatusEnum.PendingTermination);
		list.add(LearnerStatusEnum.Completed);
		list.add(LearnerStatusEnum.PendingTradeTest);
		list.add(LearnerStatusEnum.PendingARPLTradeTest);
		list.add(LearnerStatusEnum.QualificationObtained);
		list.add(LearnerStatusEnum.PendingVerificationOfAssesmentApproval);
		list.add(LearnerStatusEnum.CompletionLetterApproval);
		list.add(LearnerStatusEnum.Achieved);
		list.add(LearnerStatusEnum.Completed);
		list.add(LearnerStatusEnum.QualificationObtained);
		return list;
	}
	
	public static List<LearnerStatusEnum> getLearnerInductionValues() {
		List<LearnerStatusEnum> list = new ArrayList<>();
		list.add(Registered);
		list.add(QualificationObtained);
		return list;
	}
	
	public static List<LearnerStatusEnum> getLearnerCompletedStatusList() {
		List<LearnerStatusEnum> list = new ArrayList<>();
		list.add(LearnerStatusEnum.Completed);
		list.add(LearnerStatusEnum.QualificationObtained);
		return list;
	}
	
	public static List<LearnerStatusEnum> getLearnerStatusForMerge() {
		List<LearnerStatusEnum> list = new ArrayList<>();
		list.add(LearnerStatusEnum.Registered);
		list.add(LearnerStatusEnum.TransferPendingApproval);
		list.add(LearnerStatusEnum.PendingChangeApproval);
		list.add(LearnerStatusEnum.PendingTermination);
		list.add(LearnerStatusEnum.Completed);
		list.add(LearnerStatusEnum.PendingTradeTest);
		list.add(LearnerStatusEnum.PendingARPLTradeTest);
		list.add(LearnerStatusEnum.QualificationObtained);
		list.add(LearnerStatusEnum.PendingVerificationOfAssesmentApproval);
		list.add(LearnerStatusEnum.CompletionLetterApproval);
		list.add(LearnerStatusEnum.Achieved);
		list.add(LearnerStatusEnum.Completed);
		list.add(LearnerStatusEnum.QualificationObtained);
		list.add(LearnerStatusEnum.Terminated);
		return list;
	}

}

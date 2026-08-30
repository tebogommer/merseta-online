package haj.com.entity.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * The Enum ApprovalEnum.
 */
public enum ApprovalEnum {

	/** 0 */
	Approved("Approved") {
	},
	/** 1 */
	Rejected("Rejected") {
	},
	/** 2 */
	WaitingForManager("Pending Manager Approval") {
	},
	/** 3 */
	PendingApproval("Pending Approval") {
	},
	/** 4 */
	PendingSignOff("Pending Sign Off") {
	},
	/** 5 */
	Completed("Completed") {
	},
	/** 6 */
	PendingAcceptingCodeOfConduct("Pending accept code of conduct") {
	},
	/** 7 */
	AwaitingDHET("Awaiting DHET") {
	},
	/** 8 */
	PendingFinalApproval("Pending Final Approval") {
	},
	/** 9 */
	Withdrawn("Withdrawn") {
	},
	/** 10 */
	NA("N/A") {
	},
	/** 11 */
	Recommended("Recommended") {
	},
	/** 12 */
	Appealed("Appealed") {
	},
	/** 13 */
	PendingCommitteeApproval("Pending Committee Approval"){	
	},
	/** 14 */
	ApprovedByETQA("Approved By ETQA Review Committee") {
	},
	/** 15 */
	RejectedByEQTA("Rejected By ETQA Review Commitee") {
	},
	/** 16 */
	HigherAllocationRequest("Requested Higher Allocation") {
	},
	/** 17 */
	AcceptedMAO("Accepted MOA") {
	},
	/** 18 */
	RequestedChange("Requested Change") {
	},
	/** 19 */
	RejectedByMANCO("Rejected By MANCO Review") {
	},
	/** 20 */
	RejectedByLearnerReview("Rejected By Learner Review Committee") {
	},
	/** 21 */
	QualificationObtained("Qualification Obtained") {
	},
	/** 22 */
	Deactivated("Deactivated") {
	},
	/** 23 */
	TerminateProjectWaitingForManager("Project Terminated Manager Approval") {
	},
	/** 24 */
	SuspendProject("Suspend Project") {
	},
	/** 25 */
	ProjectTerminated("Project Terminated") {
	},
	/** 26 */
	PreApproved("Pending Review Approval") {
	},
	/** 27 */
	Uphold("Uphold") {
	},
	/** 28 */
	PendingResubmition("Pending Resubmission") {
	},
	/** 29 */
	AwaitingNAMB("Awaiting NAMB") {
	},
	/** 30 */
	PendingWithdrawal("Pending Withdrawal") {
	},
	/** 31 */
	PendingInverstigation("Pending Investigation") {
	},
	/** 32 */
	PendingChangeApproval("Pending Change Approval") {
	},
	/** 33 */
	NotCompetent("Not Competent") {
	},
	/** 34 */
	DeAccredited("De-Accredited") {
	},
	/** 35 */
	DeRegistered("De-Registered") {
	},
	/** 36 */
	DeActivated("De-activated") {
	},
	/** 37 */
	PendingComplianceIssues("Pending Non-Compliance Issues") {
	},
	/** 38 */
	Expired("Expired") {
	},
	/** 39 */
	CompletedOpen("Completed") {
	},
	/** 40 */
	Suspended("Suspended") {
	},
	/** 41 */
	Duplicate("Duplicate") {
	};


	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private ApprovalEnum(String displayNameX) {
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
	
	// status list for Training Provider Application Where open/used for applications
		public static List<ApprovalEnum> getOpenStatusForTrainingProviderApplications() {
			List<ApprovalEnum> up = new ArrayList<>();
			up.add(WaitingForManager);
			up.add(Completed);
			up.add(PendingApproval);
			up.add(PendingFinalApproval);
			up.add(PendingSignOff);
			up.add(PendingResubmition);
			up.add(PendingComplianceIssues);
			up.add(Approved);
			up.add(DeAccredited);
			up.add(PendingCommitteeApproval);
			up.add(DeAccredited);
			up.add(Suspended);
			return up;
		}
	
	// status list for Training Provider Application Where open/used for legacy applications
	public static List<ApprovalEnum> getOpenStatusForTrainingProviderApplicationsForLegacy() {
		List<ApprovalEnum> up = new ArrayList<>();
		up.add(WaitingForManager);
		up.add(Completed);
		up.add(PendingApproval);
		up.add(PendingFinalApproval);
		up.add(PendingSignOff);
		up.add(PendingResubmition);
		up.add(PendingComplianceIssues);
		up.add(Approved);
		up.add(DeAccredited);
		up.add(PendingCommitteeApproval);
		up.add(DeAccredited);
		up.add(DeRegistered);
		up.add(Suspended);
		return up;
	}
	
	// status list for Workplace monitoring site visit where open
	public static List<ApprovalEnum> getOpenStatusWorkplaceMonitoring() {
		List<ApprovalEnum> up = new ArrayList<>();
		up.add(PendingApproval);
		up.add(PendingFinalApproval);
		up.add(PendingSignOff);
		up.add(PendingResubmition);
		up.add(PendingComplianceIssues);
		return up;
	}

	// status list for active contracts where they can proceed
	public static List<ApprovalEnum> getOpenStatusForActiveContracts() {
		List<ApprovalEnum> up = new ArrayList<>();
		up.add(ApprovalEnum.Approved);
		up.add(AcceptedMAO);
		return up;
	}
	
	// status list for active contracts where they can proceed
	public static List<ApprovalEnum> getStatusForNonComplianceWorkplaceMonitoring() {
		List<ApprovalEnum> up = new ArrayList<>();
		up.add(ApprovalEnum.Approved);
		up.add(ApprovalEnum.PendingComplianceIssues);
		up.add(ApprovalEnum.PendingFinalApproval);
		return up;
	}
	
	// open status list for SDP Re-accrediciation process for providers
	public static List<ApprovalEnum> getOpenStatusForSdpReAccrediciation() {
		List<ApprovalEnum> up = new ArrayList<>();
		up.add(ApprovalEnum.PendingApproval);
		up.add(ApprovalEnum.PendingCommitteeApproval);
		up.add(ApprovalEnum.WaitingForManager);
		return up;
	}
	
	// open status list for Sdp Extension Of Scope process for providers
	public static List<ApprovalEnum> getOpenStatusForSdpExtensionOfScope() {
		List<ApprovalEnum> up = new ArrayList<>();
		up.add(ApprovalEnum.PendingApproval);
		up.add(ApprovalEnum.PendingCommitteeApproval);
		up.add(ApprovalEnum.WaitingForManager);
		up.add(ApprovalEnum.ApprovedByETQA);
		up.add(ApprovalEnum.PendingApproval);
		up.add(ApprovalEnum.PendingCommitteeApproval);
		up.add(ApprovalEnum.ApprovedByETQA);
		up.add(ApprovalEnum.RejectedByEQTA);
		return up;
	}
	
	
	// open status list for Trade Tests
	public static List<ApprovalEnum> getOpenStatusForTradeTests() {
		List<ApprovalEnum> up = new ArrayList<>();
		up.add(ApprovalEnum.PendingApproval);
		up.add(ApprovalEnum.CompletedOpen);
		up.add(ApprovalEnum.WaitingForManager);
		up.add(ApprovalEnum.Recommended);
		up.add(ApprovalEnum.AwaitingNAMB);
		return up;
	}
	
	// completed status list for Trade Tests
	public static List<ApprovalEnum> getClosedStatusForTradeTests() {
		List<ApprovalEnum> up = new ArrayList<>();
		up.add(ApprovalEnum.QualificationObtained);
		up.add(Completed);
		up.add(ApprovalEnum.Approved);
		up.add(ApprovalEnum.Rejected);
		return up;
	}
	
	// all status list for active contracts
	public static List<ApprovalEnum> getAllStatusListForActiveContracts() {
		List<ApprovalEnum> up = new ArrayList<>();
		up.add(ApprovalEnum.Approved); 
		up.add(ApprovalEnum.WaitingForManager); 
		up.add(ApprovalEnum.TerminateProjectWaitingForManager); 
		up.add(ApprovalEnum.ProjectTerminated); 
		up.add(ApprovalEnum.Rejected); 
		up.add(ApprovalEnum.PendingApproval); 
		up.add(ApprovalEnum.Withdrawn); 
		up.add(ApprovalEnum.SuspendProject);
		return up;
	}
	
	// List of suspension List
	public static List<ApprovalEnum> getStatusListForProviderSuspensionPage() {
		List<ApprovalEnum> up = new ArrayList<>();
		up.add(ApprovalEnum.Suspended); 
		up.add(ApprovalEnum.DeAccredited);
		return up;
	}
	
	// List of open status for SdpCompanyActions
	public static List<ApprovalEnum> getOpenStatusListForSdpCompanyActions() {
		List<ApprovalEnum> up = new ArrayList<>();
		up.add(ApprovalEnum.PendingApproval);
		return up;
	}

}

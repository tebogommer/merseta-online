package haj.com.entity.enums;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Enum WspStatusEnum.
 */
public enum WspStatusEnum {

	/**
	 * 0
	 * The Draft. 
	 */
	Draft("Not Submitted-In Progress") {
		public String getRegistrationName() {
			return "draft";
		}
	},
	
	/** 
	 * 1
	 * The Pending sign off. 
	 */
	PendingSignOff("Pending Sign Off") {
		public String getRegistrationName() {
			return "pending.sign.off";
		}
	},
	
	/** 
	 * 2
	 * The Pending. 
	 */
	Pending("Pending Approval") {
		public String getRegistrationName() {
			return "pending";
		}
	},
	
	/** 
	 * 3
	 * The Dispute. 
	 */
	Dispute("Dispute") {
		public String getRegistrationName() {
			return "dispute";
		}
	},
	
	/** 
	 * 4
	 * The Approved. 
	 */
	Approved("Approved") {
		public String getRegistrationName() {
			return "approved";
		}
	},
	
	/** 
	 * 5
	 * The Rejected. 
	 */
	Rejected("Rejected") {
		public String getRegistrationName() {
			return "rejected";
		}
	},
	/**
	 * 6
	 */
	ManagerApproved("Reviewed Pending Approval") {
		public String getRegistrationName() {
			return "approved";
		}
	},
	/**
	 * 7
	 */
	ManagerRejected("Reviewed Pending Final Decision") {
		public String getRegistrationName() {
			return "approved";
		}
	},
	/**
	 * 8
	 */
	SubmittedAfterDeadline("Received For Record Purposes") {
		public String getRegistrationName() {
			return "submittedafterdeadline";
		}
	},
	/**
	 * 9
	 */
	OpenedForReview("Opened For Review") {
		public String getRegistrationName() {
			return "openedforreview";
		}
	},
	
	/** 
	 * 10
	 * The Pending. 
	 */
	NA("NA") {
		public String getRegistrationName() {
			return "na";
		}
	},
	/**
	 * 11
	 */
	Withdrawn("Withdrawn") {
		public String getRegistrationName() {
			return "withdrawn";
		}
	},
	/**
	 * 12
	 * Change request
	 */
	MancoApproval("Awaiting MANCO Approval") {
		public String getRegistrationName() {
			return "withdrawn";
		}
	},
	/**
	 * 13
	 */
	BoardReview("Awaiting Board Review") {
		public String getRegistrationName() {
			return "boardreview";
		}
	},
	ProjectReview("Pending Approval") {
		public String getRegistrationName() {
			return "withdrawn";
		}
	},
	AwaitingContract("Awaiting Contract") {
		public String getRegistrationName() {
			return "withdrawn";
		}
	},
	PendingManagerReview("Pending Manager Review") {
		public String getRegistrationName() {
			return "withdrawn";
		}
	},
	PendingCEOApproval("Pending CEO Approval") {
		public String getRegistrationName() {
			return "withdrawn";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new wsp status enum.
	 *
	 * @param displayNameX the display name X
	 */
	private WspStatusEnum(String displayNameX) {
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
	 * Gets the registration name.
	 *
	 * @return the registration name
	 */
	public String getRegistrationName() {
		return displayName;
	}
	
	// list of WspStatusEnum where submitted to the SETA 
	public static List<WspStatusEnum> getWspStatusEnumSubmittedList() {
		List<WspStatusEnum> up = new ArrayList<>();
		up.add(WspStatusEnum.Pending);
		up.add(WspStatusEnum.Approved);
		up.add(WspStatusEnum.Rejected);
		up.add(WspStatusEnum.MancoApproval);
		return up;
	}
	
	public static List<WspStatusEnum> getWspStatusEnumApprovedList() {
		List<WspStatusEnum> up = new ArrayList<>();
		up.add(WspStatusEnum.Approved);
		return up;
	}
	
	public static List<WspStatusEnum> getWspStatusSspReportList() {
		List<WspStatusEnum> up = new ArrayList<>();
		up.add(WspStatusEnum.Draft);
		up.add(WspStatusEnum.PendingSignOff);
		up.add(WspStatusEnum.Pending);
		up.add(WspStatusEnum.Dispute);
		up.add(WspStatusEnum.Approved);
		up.add(WspStatusEnum.Rejected);
		up.add(WspStatusEnum.ManagerApproved);
		up.add(WspStatusEnum.ManagerRejected);
		up.add(WspStatusEnum.SubmittedAfterDeadline);
		up.add(WspStatusEnum.OpenedForReview);
		up.add(WspStatusEnum.NA);
		up.add(WspStatusEnum.Withdrawn);
		up.add(WspStatusEnum.MancoApproval);
		return up;
	}
}

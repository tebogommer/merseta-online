package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum SiteVisitReportStatusEnum.
 */
public enum SiteVisitReportStatusEnum {

	/** The Draft. */
	Draft("Not Submitted-In Progress") {
		public String getRegistrationName() {
			return "draft";
		}
	},
	
	/** The Pending sign off. */
	PendingSignOff("Pending Sign Off") {
		public String getRegistrationName() {
			return "pending.sign.off";
		}
	},
	
	/** The Pending. */
	Pending("Pending Approval") {
		public String getRegistrationName() {
			return "pending";
		}
	},
	
	/** The Pending Final Approval;. */
	PendingFinalApproval("Pending Final Approval") {
		public String getRegistrationName() {
			return "pending.final.approval";
		}
	},
	
	/** The Dispute. */
	Dispute("Dispute") {
		public String getRegistrationName() {
			return "dispute";
		}
	},
	
	/** The Approved. */
	Approved("Approved") {
		public String getRegistrationName() {
			return "approved";
		}
	},
	
	/** The Rejected. */
	Rejected("Rejected") {
		public String getRegistrationName() {
			return "rejected";
		}
	},	
	ManagerApproved("Reviewed Pending Approval") {
		public String getRegistrationName() {
			return "approved";
		}
	},	
	ManagerRejected("Reviewed Pending Final Decision") {
		public String getRegistrationName() {
			return "approved";
		}
	},	
	SubmittedAfterDeadline("Received For Record Purposes") {
		public String getRegistrationName() {
			return "submittedafterdeadline";
		}
	},	
	OpenedForReview("Opened For Review") {
		public String getRegistrationName() {
			return "openedforreview";
		}
	},
	
	/** The Pending. */
	NA("NA") {
		public String getRegistrationName() {
			return "na";
		}
	},	
	Withdrawn("Withdrawn") {
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
	private SiteVisitReportStatusEnum(String displayNameX) {
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
}

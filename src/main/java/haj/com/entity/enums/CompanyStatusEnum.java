package haj.com.entity.enums;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Enum CompanyStatusEnum.
 */
public enum CompanyStatusEnum {

	/** The Pending. 0 */
	Pending("Awaiting merSETA Approval") {
		@Override
		public String getRegistrationName() {
			return "awaiting.approval";
		}
	},

	/** The Active. 1 */
	Active("Active") {
		@Override
		public String getRegistrationName() {
			return "active";
		}
	},

	/** The In active. 2 */
	InActive("In-Active") {
		@Override
		public String getRegistrationName() {
			return "in-active";
		}
	},

	/** The Rejected. 3 */
	Rejected("Rejected") {
		@Override
		public String getRegistrationName() {
			return "rejected";
		}
	},

	/** The Approved. 4 */
	Approved("Approved") {
		@Override
		public String getRegistrationName() {
			return "approved";
		}
	},
	/**
	 * 5
	 */
	PendingChangeApproval("Pending Change Approval") {
		@Override
		public String getRegistrationName() {
			return "pending.change.approval";
		}
	},

	/**
	 * 6
	 */
	NonMersetaCompany("Non-merSETA Company") {
		@Override
		public String getRegistrationName() {
			return "nonmersetacompany";
		}
	},
	
	/**
	 * 7
	 */
	DeRegistered("Deregistered") {
		@Override
		public String getRegistrationName() {
			return "deregistered";
		}
	},

	/**
	 * 8
	 */
	PendingReplacement("Pending Replacement") {
		@Override
		public String getRegistrationName() {
			return "pendingreplacement";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new company status enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private CompanyStatusEnum(String displayNameX) {
		displayName = displayNameX;
	}

	/**
	 * Gets the friendly name.
	 *
	 * @return the friendly name
	 */
	public String getFriendlyName() {
		return toString();
	}

	/**
	 * Gets the registration name.
	 *
	 * @return the registration name
	 */
	public String getRegistrationName() {
		return displayName;
	}
	
	// status list for Training Provider Application Where open/used for legacy applications
	public static List<CompanyStatusEnum> getIgnoreStatusList() {
		List<CompanyStatusEnum> up = new ArrayList<>();
		up.add(InActive);
		return up;
	}
}

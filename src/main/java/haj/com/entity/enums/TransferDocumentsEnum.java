package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum ApprovalEnum.
 */
public enum TransferDocumentsEnum {

	/** 0 - Learner Requested Transfer */
	LearnerRequestedTransferDocuments("LearnerRequestedTransferDocuments") {
	},
	/** 1 - Provider / Employer Requested Transfer */
	ProviderWorkplaceRequestedTransferDocuments("ProviderWorkplaceRequestedTransferDocuments") {
	},
	/** 2 - Transferring to new Provider */
	NewProviderTransferDocuments("NewProviderTransferDocuments") {
	},
	/** 3 - Transferring to new work place / employer */
	NewWorkplaceTransferDocuments("NewWorkplaceTransferDocuments") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private TransferDocumentsEnum(String displayNameX) {
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

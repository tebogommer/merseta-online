package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum ApprovalEnum.
 */
public enum BulkApprovalEnum {

	FinalApproved("Final Approved"),
	FinalRejected("Final Rejected"),
	FinalToBulkAction("Rejected To Bulk Assignment"),
	FinalToSdf("Reject To SDF"),
	Withdrawn("Withdrawn");

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private BulkApprovalEnum(String displayNameX) {
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

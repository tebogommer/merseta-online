package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum ApprovalEnum.
 */
public enum DiscretionalWithdrawalAppealEnum {

	EconomicDecline("Economic Decline") {
	},
	CompanyClosingDown("Company Closing Down") {
	},
	ChangeSkillsPlanningRequirements("Change in Skills Planning Requirements") {
	},
	RequestForNumbersAppliedFor("Request for numbers applied for") {
	},
	ReduceTheAllocatedNumber("Reduce the allocated number") {
	},
	MoaThirtyDayRule("MoA not returned within 30 business days after allocation") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private DiscretionalWithdrawalAppealEnum(String displayNameX) {
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

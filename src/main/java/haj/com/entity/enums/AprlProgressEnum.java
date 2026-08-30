package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum ApprovalEnum.
 */
public enum AprlProgressEnum {

	/* 0 - Initiator 1 - Fill in application */
	WithInitiatorOne("WithInitiatorOne") {
	}, 
	/* 1 - Region Client Services Administrator - Review and approve */
	WithMersetaOne("WithMersetaOne") {
	},
	/* 2 - Initiator 2 - Set date for documents  */
	WithInitiatorTwo("WithInitiatorTwo") {
	},
	/* 3 - Region Client Services Administrator - Approve Date */
	WithMersetaTwo("WithMersetaTwo") {
	},
	/* 4 - Region Client Services Administrator - Approves docs on site  */
	WithMersetaThree("WithMersetaThree") {
	},
	/* 5 - Region Quality Assuror - Approves or Rejects or Final Rejects */
	WithMersetaFour("WithMersetaFour") {
	},
	/* 6 - With Trade Test Center for a bit */
	WithTestCenterOne("WithTestCenterOne") {
	},
	/* 7 - Client Services (Region) Coordinator */
	WithMersetaFive("WithMersetaFive") {
	},
	/* 8 - MerSETA 6 (Administrator) */
	WithMersetaSix("WithMersetaSix") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private AprlProgressEnum(String displayNameX) {
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

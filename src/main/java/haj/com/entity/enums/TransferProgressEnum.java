package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum ApprovalEnum.
 */
public enum TransferProgressEnum {

	/* 0 - Initiator 1  */
	WithInitiatorOne("WithInitiatorOne") {
	}, 
	/* 1 - With New Company Rep */
	WithNewCompanyRepOne("WithNewCompanyRepOne") {
	},
	/* 2 - With Learner One  */
	WithLearnerOne("WithLearnerOne") {
	},
	/* 3 - With CLO to do site visit */
	WithCloSiteVisit("WithCloSiteVisit") {
	},
	/* 4 - With Initiator Two to upload  */
	WithInitiatorTwo("WithInitiatorTwo") {
	},
	/* 5 - With MerSETA Admin to Approve  */
	WithMersetaOne("WithMersetaOne") {
	},
	/* 6 - With merSETA Coordinator to final approve */
	WithMersetaTwo("WithMersetaTwo") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private TransferProgressEnum(String displayNameX) {
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
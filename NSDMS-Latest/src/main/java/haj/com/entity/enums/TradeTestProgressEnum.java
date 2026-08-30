package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum ApprovalEnum.
 */
public enum TradeTestProgressEnum {

	// with initiator
	WithInitiator("WithInitiator") {
	}, 
	// with admin
	WithMersetaOne("WithMersetaOne") {
	},
	// with test center
	WithTestCenter("WithTestCenter") {
	},
	// with Coordinator
	WithMersetaTwo("WithMersetaTwo") {
	},
	// with Admin
	WithMersetaThree("WithMersetaThree") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private TradeTestProgressEnum(String displayNameX) {
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

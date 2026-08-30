package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum SetmisDisabilityEnum.
 */
public enum SetmisDisabilityEnum {

	Seeing("Seeing Rating") {
	},
	Hearing("Hearing Rating") {
	},
	Walking("Walking Rating") {
	},
	Remembering("Remembering Rating") {
	},
	Communicating("Communicating Rating") {
	},
	Self_Care("Self_Care Rating"){		
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new SetmisDisabilityEnum.
	 *
	 * @param displayNameX the display name X
	 */
	private SetmisDisabilityEnum(String displayNameX) {
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
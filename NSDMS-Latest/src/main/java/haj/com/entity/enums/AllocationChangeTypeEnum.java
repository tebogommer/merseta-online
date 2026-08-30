package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum AgeGroupEnum.
 */
public enum AllocationChangeTypeEnum {

	RequestChange("Request Change") {
	},

	RequestHigherAllocation("Request Higher Allocation") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new age group enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private AllocationChangeTypeEnum(String displayNameX) {
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

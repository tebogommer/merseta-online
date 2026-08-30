package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum TrainingProviderFilterEnum.
 */
public enum TrainingProviderFilterEnum {

	/** All Data */
	NO_FILTER("All Training Data") {
	},

	/** Before Expire */
	EXPIRE_FILTER("Expiring Within 12 Months") {
	},
	
	/** More than 1 year, no monitoring visit */
	NO_VISIT_FILTER("No Monitoring Visit Within The First Year") {
	},

	/** Not been visited twice between year 2- 4. */
	TWO_IN_FIVE_FILTER("Twice In A 5-cycle Year") {
	
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new age group enum.
	 *
	 * @param displayNameX the display name X
	 */
	private TrainingProviderFilterEnum(String displayNameX) {
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

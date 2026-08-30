package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum PastFutureEnum.
 */
public enum PastFutureEnum {
	
	/** The Past. */
	/* 0 */
	Past("Past") {
		public String getRegistrationName() {
			return "past";
		}
	},
	
	/** The Future. */
	/* 1 */
	Future("Future") {
		public String getRegistrationName() {
			return "future";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new past future enum.
	 *
	 * @param displayNameX the display name X
	 */
	private PastFutureEnum(String displayNameX) {
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

}

package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum PivotNonPivotEnum.
 */
public enum PivotNonPivotEnum {

	/** The Pivotal. */
	Pivotal("Pivotal") {
		public String getRegistrationName() {
			return "pivotal";
		}
	},

	/** The Non pivotal. */
	NonPivotal("Non Pivotal") {
		public String getRegistrationName() {
			return "nonpivotal";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new pivot non pivot enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private PivotNonPivotEnum(String displayNameX) {
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

	/**
	 * Gets the registration name.
	 *
	 * @return the registration name
	 */
	public String getRegistrationName() {
		return displayName;
	}

	/**
	 * Gets the pivot non pivot enum by value.
	 *
	 * @param value
	 *            the value
	 * @return the pivot non pivot enum by value
	 */
	public static final PivotNonPivotEnum getPivotNonPivotEnumByValue(int value) {
		for (PivotNonPivotEnum status : PivotNonPivotEnum.values()) {
			if (status.ordinal() == value)
				return status;
		}

		return null;
	}
}

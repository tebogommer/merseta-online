package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum PivotNonPivotEnum.
 */
public enum GrantTypeEnum {

	GrantAmount("Basic Grant") {
		public String getRegistrationName() {
			return "";
		}
	},
	DisabilityGrantAmount("Disability Grant") {
		public String getRegistrationName() {
			return "";
		}
	},
	RuralGrantAmount("Rural Grant") {
		public String getRegistrationName() {
			return "";
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
	private GrantTypeEnum(String displayNameX) {
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
	public static final GrantTypeEnum getPivotNonPivotEnumByValue(int value) {
		for (GrantTypeEnum status : GrantTypeEnum.values()) {
			if (status.ordinal() == value)
				return status;
		}

		return null;
	}
}

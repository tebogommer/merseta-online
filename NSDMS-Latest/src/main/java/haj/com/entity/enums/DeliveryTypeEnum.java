package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum IdPassportEnum.
 */
public enum DeliveryTypeEnum {

	Silver("Silver") {
		public String getRegistrationName() {
			return "silver";
		}
	},

	Gold("Gold") {
		public String getRegistrationName() {
			return "gold";
		}
	},

	Platinum("Platinum") {
		public String getRegistrationName() {
			return "platinum";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new id passport enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private DeliveryTypeEnum(String displayNameX) {
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

	/**
	 * Gets the id passport enum by value.
	 *
	 * @param value
	 *            the value
	 * @return the id passport enum by value
	 */
	public static final DeliveryTypeEnum getIdPassportEnumByValue(String value) {
		Integer val = 0;
		try {
			val = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		for (DeliveryTypeEnum status : DeliveryTypeEnum.values()) {
			if (status.ordinal() == val) return status;
		}
		return null;
	}
}

package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum WspTypeEnum.
 */
public enum RejectReasonsEnum {

	/** The Mandatory. */
	AdditionalInformationSupplied("Additional Information Supplied") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new wsp type enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private RejectReasonsEnum(String displayNameX) {
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
	 * Gets the id passport enum by value.
	 *
	 * @param value
	 *            the value
	 * @return the id passport enum by value
	 */
	public static final RejectReasonsEnum getIdPassportEnumByValue(int value) {
		for (RejectReasonsEnum status : RejectReasonsEnum.values()) {
			if (status.ordinal() == value) return status;
		}

		return null;
	}
}

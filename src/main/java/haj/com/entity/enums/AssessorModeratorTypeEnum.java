package haj.com.entity.enums;

public enum AssessorModeratorTypeEnum {

	/** 0 */
	MerSETA_AM("merSETA") {
	},
	/** 1 */
	TTC_AM("TTC Assessor / Moderator") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new wsp type enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private AssessorModeratorTypeEnum(String displayNameX) {
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
	public static final AssessorModeratorTypeEnum getIdPassportEnumByValue(int value) {
		for (AssessorModeratorTypeEnum status : AssessorModeratorTypeEnum.values()) {
			if (status.ordinal() == value)
				return status;
		}
		return null;
	}
}

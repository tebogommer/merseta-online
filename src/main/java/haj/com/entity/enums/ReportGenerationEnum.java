package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum ReportGenerationEnum.
 */
public enum ReportGenerationEnum {

	AllocationForecast("DG Allocation Forecast Report - Approved Verifications") {
	},
	AllocationForecastAllVerifications("DG Allocation Forecast Report - All Verifications") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new id passport enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private ReportGenerationEnum(String displayNameX) {
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
	public static final ReportGenerationEnum getIdPassportEnumByValue(int value) {
		for (ReportGenerationEnum status : ReportGenerationEnum.values()) {
			if (status.ordinal() == value) return status;
		}

		return null;
	}
}

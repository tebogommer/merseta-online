package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum IdPassportEnum.
 */
public enum WorkplaceSurveyType {

	LEARNERMONITORINGSURVEY("Learner Monitoring Survey") {
	},
	DISCRETIONARYGRANTMONITORING("Discretionary Grant Monitoring") {
	},
	OTHER("Other") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new id passport enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private WorkplaceSurveyType(String displayNameX) {
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

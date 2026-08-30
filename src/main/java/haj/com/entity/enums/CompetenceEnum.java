package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum CompletedPlannedEnum.
 */
public enum CompetenceEnum {

	Competent("Competent") {
	},
	NotYetCompetent("Not Yet Competent") {
	},
	AbsentCancelled("Absent / Cancelled") {
	};

	/** The display name. */
	private String displayName;

	private CompetenceEnum(String displayNameX) {
		displayName = displayNameX;
	}

	public String getFriendlyName() {
		return displayName;
	}

}

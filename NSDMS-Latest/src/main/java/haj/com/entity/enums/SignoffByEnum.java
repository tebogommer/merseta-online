package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum CompletedPlannedEnum.
 */
public enum SignoffByEnum {

	merSETA("merSETA Employee") {
	},
	sdf("SDF") {
	},
	sdp("SDP") {
	},
	gaurdian("Legal Guardian") {
	},
	Learner("Learner") {
	},
	Completed("Completed Signoffs") {
	},
	User("User") {
	};

	/** The display name. */
	private String displayName;

	private SignoffByEnum(String displayNameX) {
		displayName = displayNameX;
	}

	public String getFriendlyName() {
		return displayName;
	}

}

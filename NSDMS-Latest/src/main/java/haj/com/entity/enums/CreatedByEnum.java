package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum CompletedPlannedEnum.
 */
public enum CreatedByEnum {

	merSETA("merSETA Employee") {
	},
	sdf("SDF") {
	},
	sdp("SDP") {
	};

	/** The display name. */
	private String displayName;

	private CreatedByEnum(String displayNameX) {
		displayName = displayNameX;
	}

	public String getFriendlyName() {
		return displayName;
	}

}

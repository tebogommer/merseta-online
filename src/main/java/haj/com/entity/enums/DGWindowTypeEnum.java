package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum IdPassportEnum.
 */
public enum DGWindowTypeEnum {

	Standard("Standard") {
	},
	Pivitol("Additional PIVOTAL") {
	},

	Project("Additional Project") {
	};

	private String displayName;

	private DGWindowTypeEnum(String displayNameX) {
		displayName = displayNameX;
	}

	public String getFriendlyName() {
		return toString();
	}

	public String getRegistrationName() {
		return displayName;
	}

}

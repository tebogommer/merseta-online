package haj.com.entity.enums;

public enum SmeTypeEnum {

	Assessor("Assessor") {
	},
	Facilitator("Facilitator") {
	};

	private String displayName;

	private SmeTypeEnum(String displayNameX) {
		displayName = displayNameX;
	}

	public String getFriendlyName() {
		return displayName;
	}
}

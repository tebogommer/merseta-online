package haj.com.entity.enums;

public enum SDPApplicationType {

	LevyPayingEntity("Levy Paying Entity") {
	},
	NonLevyPayingEntity("Non-Levy Paying Entity") {
	};

	private String displayName;

	private SDPApplicationType(String displayNameX) {
		displayName = displayNameX;
	}

	public String getFriendlyName() {
		return displayName;
	}
}

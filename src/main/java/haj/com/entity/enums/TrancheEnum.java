package haj.com.entity.enums;

public enum TrancheEnum {

	TRANCHE_ONE("Tranche One") {
	},

	TRANCHE_TWO("Tranche Two") {
	},

	TRANCHE_THREE("Tranche Three") {
	},

	TRANCHE_FOUR("Tranche Four") {
	},

	RECOVERABLE_AMOUNT("Recoverable Amount ") {
	};

	private String displayName;

	private TrancheEnum(String displayNameX) {
		displayName = displayNameX;
	}

	public String getFriendlyName() {
		return displayName;
	}

}

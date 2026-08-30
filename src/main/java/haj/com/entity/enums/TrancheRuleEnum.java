package haj.com.entity.enums;

public enum TrancheRuleEnum {

	APPRENTICES_RULE("Apprentices Rule") {
	},
	LEARNERSHIP_RULE("Learnership Rule") {
	},
	NA("N/A") {
	};
	
	private String displayName;
	
	private TrancheRuleEnum(String displayNameX) {
		displayName = displayNameX;
	}
	public String getFriendlyName() {
		return displayName;
	}
}

package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum CompletedPlannedEnum.
 */
public enum CompanySizeEnum {

	Small("Small Company") {
	},
	Medium("Medium Company") {
	},
	Large("Large Company") {
	};

	/** The display name. */
	private String displayName;

	private CompanySizeEnum(String displayNameX) {
		displayName = displayNameX;
	}

	public String getFriendlyName() {
		return displayName;
	}

}

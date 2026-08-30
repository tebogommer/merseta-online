package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum WspTypeEnum.
 */
public enum SkillsTypeEnum {

	/** The Mandatory. */
	SkillsSet("Skills Set") {
	},

	/** The Discretionary. */
	SkillsProgram("Skills Programmes") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new wsp type enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private SkillsTypeEnum(String displayNameX) {
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

	/**
	 * Gets the id passport enum by value.
	 *
	 * @param value
	 *            the value
	 * @return the id passport enum by value
	 */
	public static final SkillsTypeEnum getIdPassportEnumByValue(int value) {
		for (SkillsTypeEnum status : SkillsTypeEnum.values()) {
			if (status.ordinal() == value) return status;
		}

		return null;
	}
}

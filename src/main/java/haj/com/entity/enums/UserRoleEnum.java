package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum WspTypeEnum.
 */
public enum UserRoleEnum {

	ClientRelationsManager("Client Relations Manager") {
	},
	ClientLiaisonOfficer("Client Liaison Officer") {
	};
	
	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new wsp type enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private UserRoleEnum(String displayNameX) {
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
	public static final UserRoleEnum getIdPassportEnumByValue(int value) {
		for (UserRoleEnum status : UserRoleEnum.values()) {
			if (status.ordinal() == value) return status;
		}

		return null;
	}
}

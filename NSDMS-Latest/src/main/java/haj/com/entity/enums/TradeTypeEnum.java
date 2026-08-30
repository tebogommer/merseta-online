package haj.com.entity.enums;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Enum UserPermissionEnum.
 */
public enum TradeTypeEnum {

	LEARNERSHIP("LEARNERSHIP") {
	},
	CBMT("Trade CBMT") {
	},
	METAL("Trade Metal") {
	},
	MOTOR("Motor Time Based") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new user permission enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private TradeTypeEnum(String displayNameX) {
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
	 * Gets the registration name.
	 *
	 * @return the registration name
	 */
	public String getRegistrationName() {
		return displayName;
	}

}

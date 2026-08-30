package haj.com.entity.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * The Enum QmrEquityEnum.
 */
public enum QmrGenderEnum {

	/* Black / African */
	MALE("MALE") {
	},
	/* White */
	FEMALE("FEMALE") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private QmrGenderEnum(String displayNameX) {
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
	
	public static List<QmrGenderEnum> getQmrGenderList() {
		List<QmrGenderEnum> results = new ArrayList<>();
		results.add(QmrGenderEnum.MALE);
		results.add(QmrGenderEnum.FEMALE);
		return results;
	}

}

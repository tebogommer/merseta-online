package haj.com.entity.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * The Enum QmrEquityEnum.
 */
public enum QmrEquityEnum {

	/* Black / African */
	AFRICAN("AFRICAN") {
	},
	/* White */
	WHITE("WHITE") {
	},
	/* Coloured */
	COLOURED("COLOURED") {
	},
	/* Indian */
	INDIAN("INDIAN") {
	},
	/* Asian */
	ASIAN("ASIAN") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private QmrEquityEnum(String displayNameX) {
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
	
	public static List<QmrEquityEnum> getQmrEquityList() {
		List<QmrEquityEnum> results = new ArrayList<>();
		results.add(QmrEquityEnum.AFRICAN);
		results.add(QmrEquityEnum.WHITE);
		results.add(QmrEquityEnum.COLOURED);
		results.add(QmrEquityEnum.INDIAN);
		return results;
	}

}

package haj.com.entity.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * The Enum FinYearQuartersEnum.
 */
public enum FinYearQuartersEnum {

	QuarterOne("Quarter 1") {
	},
	QuarterTwo("Quarter 2") {
	},
	QuarterThree("Quarter 3") {
	},
	QuarterFour("Quarter 4") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new FinYearQuarters Enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private FinYearQuartersEnum(String displayNameX) {
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

	public static List<FinYearQuartersEnum> getQuatersBasedOnPassed(FinYearQuartersEnum quater) {
		List<FinYearQuartersEnum> results = new ArrayList<>();
		if (quater != null) {
			switch (quater) {
			case QuarterOne:
				results.add(FinYearQuartersEnum.QuarterOne);
				break;
			case QuarterTwo:
				results.add(FinYearQuartersEnum.QuarterOne);
				results.add(FinYearQuartersEnum.QuarterTwo);
				break;
			case QuarterThree:
				results.add(FinYearQuartersEnum.QuarterOne);
				results.add(FinYearQuartersEnum.QuarterTwo);
				results.add(FinYearQuartersEnum.QuarterThree);
				break;
			case QuarterFour:
				results.add(FinYearQuartersEnum.QuarterOne);
				results.add(FinYearQuartersEnum.QuarterTwo);
				results.add(FinYearQuartersEnum.QuarterThree);
				results.add(FinYearQuartersEnum.QuarterFour);
				break;
			default:
				break;
			}
		}
		return results;
	}

}

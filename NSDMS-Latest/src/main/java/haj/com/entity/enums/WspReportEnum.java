package haj.com.entity.enums;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Enum WspReportEnum.
 */
public enum WspReportEnum {
	/** The wsp. */
	EMPLOYMENTDATA("Employment Data") {
	},
	/** The wsp. */
	WSP("WSP") {
	},

	/** The pivitolplandg. */
	PIVITOLPLANDG("Pivotal Plan DG") {
	},

	/** The atr. */
	ATR("ATR") {
	},

	/** The pivitoltrainingreport. */
	PIVITOLTRAININGREPORT("Pivitol Training Report") {
	},

	/** The trainingdonewithotheretas. */
	TRAININGDONEWITHOTHERETAS("Training done with other SETAs") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new wsp report enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private WspReportEnum(String displayNameX) {
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
	
	public static List<WspReportEnum> getWspReportAtrSspReportingList() {
		List<WspReportEnum> up = new ArrayList<>();
		up.add(WspReportEnum.ATR);
		return up;
	}
	
	public static List<WspReportEnum> getWspReportWspSspReportingList() {
		List<WspReportEnum> up = new ArrayList<>();
		up.add(WspReportEnum.WSP);
		return up;
	}

}

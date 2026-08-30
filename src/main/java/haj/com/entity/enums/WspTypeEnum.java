package haj.com.entity.enums;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Enum WspTypeEnum.
 */
public enum WspTypeEnum {

	/** The Mandatory. */
	Mandatory("Mandatory") {
		public String getRegistrationName() {
			return "mandatory.grant";
		}
	},

	/** The Discretionary. */
	Discretionary("Discretionary") {
		public String getRegistrationName() {
			return "discretionary.grant";
		}
	},

	/** The Both. */
	Both("Mandatory and Discretionary") {
		public String getRegistrationName() {
			return "mandatory.grant.discretionary.grant";
		}
	},

	/** The Inter-SETA Mandatory Grant wsp */
	IntersetaTransferMandGrant("Inter-SETA Transfer Mandatory Grant") {
		public String getRegistrationName() {
			return "inter.seta.transfer.mand.grant";
		}
	},

	/** Legacy Linked Company Grant Application */
	LegacyLinkedGrantApplication("Legacy Linked Company Grant Application") {
		public String getRegistrationName() {
			return "legacy.linked.company.grant.application";
		}
	},
	AdditionalFunding("Additional Funding") {
		public String getRegistrationName() {
			return "additional.funding";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new wsp type enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private WspTypeEnum(String displayNameX) {
		displayName = displayNameX;
	}

	/**
	 * Gets the friendly name.
	 *
	 * @return the friendly name
	 */
	public String getFriendlyName() {
		return toString();
	}

	/**
	 * Gets the registration name.
	 *
	 * @return the registration name
	 */
	public String getRegistrationName() {
		return displayName;
	}

	/**
	 * Gets the id passport enum by value.
	 *
	 * @param value
	 *            the value
	 * @return the id passport enum by value
	 */
	public static final WspTypeEnum getIdPassportEnumByValue(int value) {
		for (WspTypeEnum status : WspTypeEnum.values()) {
			if (status.ordinal() == value)
				return status;
		}

		return null;
	}
	
	public static List<WspTypeEnum> getNsdpReportWspTypeList() {
		List<WspTypeEnum> up = new ArrayList<>();
		up.add(WspTypeEnum.Mandatory);
		up.add(WspTypeEnum.Both);
		up.add(WspTypeEnum.IntersetaTransferMandGrant);
		up.add(WspTypeEnum.LegacyLinkedGrantApplication);
		return up;
	}
	
	public static List<WspTypeEnum> getStandardFundingTypes() {
		List<WspTypeEnum> up = new ArrayList<>();
		up.add(WspTypeEnum.Mandatory);
		up.add(WspTypeEnum.Discretionary);
		up.add(WspTypeEnum.Both);
		up.add(WspTypeEnum.IntersetaTransferMandGrant);
		up.add(WspTypeEnum.LegacyLinkedGrantApplication);
		return up;
	}
}

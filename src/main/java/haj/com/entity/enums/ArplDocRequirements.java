package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum WspTypeEnum.
 */
public enum ArplDocRequirements {

	PermitVISA("PermitVISA") {
	},
	TradeTestResults("TradeTestResults") {
	},
	CollectionAcknowledgment("CollectionAcknowledgment") {
	},
	ToolKit("ToolKit") {
	};

	private String displayName;

	private ArplDocRequirements(String displayNameX) {
		displayName = displayNameX;
	}

	public String getFriendlyName() {
		return displayName;
	}

	public static final ArplDocRequirements getIdPassportEnumByValue(int value) {
		for (ArplDocRequirements status : ArplDocRequirements.values()) {
			if (status.ordinal() == value)
				return status;
		}

		return null;
	}
}

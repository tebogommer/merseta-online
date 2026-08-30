package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum WspTypeEnum.
 */
public enum WpaDocRequirements {

	ProofAgreement("Proof Of Agreement") {
	},
	
	ScopeEvidence("Scope Evidence") {
	};

	private String displayName;

	private WpaDocRequirements(String displayNameX) {
		displayName = displayNameX;
	}

	public String getFriendlyName() {
		return displayName;
	}

	public static final WpaDocRequirements getIdPassportEnumByValue(int value) {
		for (WpaDocRequirements status : WpaDocRequirements.values()) {
			if (status.ordinal() == value)
				return status;
		}
		return null;
	}
}

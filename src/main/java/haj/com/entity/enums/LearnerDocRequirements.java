package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum WspTypeEnum.
 */
public enum LearnerDocRequirements {

	Bursary("Bursary") {
	},
	
	Agreement("Agreement Form") {
	},
	
	LateSubmissionLetter("Late submission letter") {
	},
	
	ProofOfDisability("Proof of disability") {
	};

	private String displayName;

	private LearnerDocRequirements(String displayNameX) {
		displayName = displayNameX;
	}

	public String getFriendlyName() {
		return displayName;
	}

	public static final LearnerDocRequirements getIdPassportEnumByValue(int value) {
		for (LearnerDocRequirements status : LearnerDocRequirements.values()) {
			if (status.ordinal() == value)
				return status;
		}

		return null;
	}
}

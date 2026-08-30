package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum WspTypeEnum.
 */
public enum WspDocRequirements {

	SignOffPage("SignOffPage") {
	},
	Dispute("Dispute") {
	},
	TrainingCommittee("Training Committee") {
	},
	Deviation("Deviation Doc") {
	},
	TrainingMotivation("TrainingMotivation") {
	},
	NonNQFTrainingExplanation("Non-NQF Training Explanation") {
	},
	AppealForm("Grant Appplication Appeal") {
	},
	InterSetaTransferDoc("Inter Seta Transfer Doc") {
	},
	ManualSubmissionDocument("Manual Submission Document") {
	};

	private String displayName;

	private WspDocRequirements(String displayNameX) {
		displayName = displayNameX;
	}

	public String getFriendlyName() {
		return displayName;
	}

	public static final WspDocRequirements getIdPassportEnumByValue(int value) {
		for (WspDocRequirements status : WspDocRequirements.values()) {
			if (status.ordinal() == value)
				return status;
		}

		return null;
	}
}

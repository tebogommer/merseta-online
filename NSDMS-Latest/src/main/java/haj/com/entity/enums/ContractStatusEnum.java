package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum IdPassportEnum.
 */
public enum ContractStatusEnum {

	OnHold("On Hold") {
	},
	OnHoldAA("On Hold - AA") {
	},
	OnHoldL("On Hold - L") {
	},
	Closed("Closed") {
	},
	InProgress("In Progress") {
	},
	SuspendProgress("Suspend Project"){
	},
	Terminated("Terminated") {
	},
	Withdrawn("Withdrawn") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new id passport enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private ContractStatusEnum(String displayNameX) {
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
	 * Gets the id passport enum by value.
	 *
	 * @param value
	 *            the value
	 * @return the id passport enum by value
	 */
	public static final ContractStatusEnum getIdPassportEnumByValue(int value) {
		for (ContractStatusEnum status : ContractStatusEnum.values()) {
			if (status.ordinal() == value) return status;
		}

		return null;
	}
}

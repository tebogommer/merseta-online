package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum IdPassportEnum.
 */
public enum CeritificateCollectionEnum {

	PendingSubmission("Pending Submission") {
		public String getRegistrationName() {
			return "Pending Submission";
		}
	},

	PendingApproval("Pending Date Approval") {
		public String getRegistrationName() {
			return "Pending Date Approval";
		}
	},
	
	Collected("Collected") {
		public String getRegistrationName() {
			return "Collected";
		}
	},

	PendingCollection("Pending Collection") {
		public String getRegistrationName() {
			return "Pending Collection";
		}
	},

	CollectionRejected("Collection Rejected") {
		public String getRegistrationName() {
			return "Collection Rejected";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new id passport enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private CeritificateCollectionEnum(String displayNameX) {
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
	public static final CeritificateCollectionEnum getIdPassportEnumByValue(String value) {
		Integer val = 0;
		try {
			val = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		for (CeritificateCollectionEnum status : CeritificateCollectionEnum.values()) {
			if (status.ordinal() == val) return status;
		}
		return null;
	}
}

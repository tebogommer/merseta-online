package haj.com.entity.enums;

public enum MailTagsEnum {

	CompanyName("Company Name") {
		@Override
		public String getTagName() {
			return "#COMPANY-NAME#";
		}
	},

	FirstName("First Name") {
		@Override
		public String getTagName() {
			return "#FIRST-NAME#";
		}
	},

	LastName("Last Name") {
		@Override
		public String getTagName() {
			return "#LAST-NAME#";
		}
	},
	Task("Task text") {
		@Override
		public String getTagName() {
			return "#TASK-TEXT#";
		}
	},
	SDLNumber("SDL/N Number") {
		@Override
		public String getTagName() {
			return "#SDL-NUMBER#";
		}
	},
	Date("Current Date") {
		@Override
		public String getTagName() {
			return "#DATE#";
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
	private MailTagsEnum(String displayNameX) {
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
	public String getTagName() {
		return displayName;
	}

}

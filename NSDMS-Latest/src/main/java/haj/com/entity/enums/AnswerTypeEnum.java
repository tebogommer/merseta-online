package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum AnswerTypeEnum.
 */
public enum AnswerTypeEnum {

	/** The Yes no. */
	RadioButtons("Radio Buttons") {
		@Override
		public String getDisplayParams() {
			return "ui-g-12 ui-md-4";
		}

		@Override
		public String getDisplayParamsQuestion() {
			return "ui-g-12 ui-md-8";
		}
	},
	/** The Text. */
	Text("Text") {
		@Override
		public String getDisplayParams() {
			return "ui-g-12 ui-md-4";
		}

		@Override
		public String getDisplayParamsQuestion() {
			return "ui-g-12 ui-md-8";
		}
	},
	/** The Date. */
	Date("Date") {
		@Override
		public String getDisplayParams() {
			return "ui-g-12 ui-md-4";
		}

		@Override
		public String getDisplayParamsQuestion() {
			return "ui-g-12 ui-md-8";
		}
	},
	/** The Text area. */
	TextArea("Text Area") {
		@Override
		public String getDisplayParams() {
			return "ui-g-12";
		}

		@Override
		public String getDisplayParamsQuestion() {
			return "ui-g-12";
		}
	},
	/** The Drop down. */
	DropDown("Drop Down") {
		@Override
		public String getDisplayParams() {
			return "ui-g-12 ui-md-4";
		}

		@Override
		public String getDisplayParamsQuestion() {
			return "ui-g-12 ui-md-8";
		}
	},
	/** The Drop down. */
	None("None") {
		@Override
		public String getDisplayParams() {
			return "ui-g-12";
		}

		@Override
		public String getDisplayParamsQuestion() {
			return "ui-g-12";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new answer type enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private AnswerTypeEnum(String displayNameX) {
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

	public String getDisplayParams() {
		return displayName;
	}

	public String getDisplayParamsQuestion() {
		return displayName;
	}
}

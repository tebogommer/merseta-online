package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum DocumentTrackerEnum.
 */
public enum DocumentTrackerEnum {

	/** The Upload. */
	Upload("Uploaded") {
		@Override
		public String getRegistrationName() {
			return "uploaded";
		}
	},

	/** The Downloaded. */
	Downloaded("Downloaded") {
		@Override
		public String getRegistrationName() {
			return "downloaded";
		}
	},

	/** The Upload version. */
	UploadVersion("Uploaded a new version") {
		@Override
		public String getRegistrationName() {
			return "uploaded.a.new.version";
		}
	},

	/** The Viewed. */
	Viewed("Viewed"){
		@Override
		public String getRegistrationName() {
			return "viewed";
		}
	},

	/** The Viewed content. */
	ViewedContent("Viewed Content") {
		@Override
		public String getRegistrationName() {
			return "viewed.content";
		}
	},

	/** The Change type. */
	ChangeType("Changed document type") {
		@Override
		public String getRegistrationName() {
			return "changed.document.type";
		}
	},

	/** The Check out. */
	CheckOut("Checked document out") {
		@Override
		public String getRegistrationName() {
			return "changed.document.out";
		}
	},

	/** The Check in. */
	CheckIn("Checked document in")  {
		@Override
		public String getRegistrationName() {
			return "changed.document.in";
		}
	},

	/** The Change status. */
	ChangeStatus("Change document status")  {
		@Override
		public String getRegistrationName() {
			return "changed.document.status";
		}
	},

	/** The Form. */
	Form("Form Completed")  {
		@Override
		public String getRegistrationName() {
			return "form.complete";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new document tracker enum.
	 *
	 * @param displayNameX the display name X
	 */
	private DocumentTrackerEnum(String displayNameX) {
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
	 * Gets the registration name.
	 *
	 * @return the registration name
	 */
	public String getRegistrationName() {
		return displayName;
	}
}

package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum SectionEnum.
 */
public enum SectionEnum {

//	Title("Title") {
//		public String getFriendlyNameDetail() {
//			return "Title at the top of the Page";
//		}
/** The Section. */
//	},
	Section("Text") {
		public String getFriendlyNameDetail() {
			return "Any section of text.";
		}
	},
	
	/** The Questions. */
	Questions("Questions") {
		public String getFriendlyNameDetail() {
			return "Section of questions.";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new section enum.
	 *
	 * @param displayNameX the display name X
	 */
	private SectionEnum(String displayNameX) {
		displayName = displayNameX;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return displayName;
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
	 * Gets the friendly name detail.
	 *
	 * @return the friendly name detail
	 */
	public String getFriendlyNameDetail() {
		return toString();
	}
}

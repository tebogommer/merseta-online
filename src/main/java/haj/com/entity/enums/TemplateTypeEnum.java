package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum TemplateTypeEnum.
 */
public enum TemplateTypeEnum {
	
	/** The Chapter. */
	Chapter("Chapter") {
		public String getTemplateTypeName() {
			return "chapter";
		}
	},
	
	/** The Document. */
	Document("Document") {
		public String getTemplateTypeName() {
			return "document";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new template type enum.
	 *
	 * @param displayNameX the display name X
	 */
	private TemplateTypeEnum(String displayNameX) {
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
	 * Gets the template type name.
	 *
	 * @return the template type name
	 */
	public String getTemplateTypeName() {
		return displayName;
	}

}

package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum CompanyRegOrSDLEnum.
 */
public enum ProviderNameRegisTrationNumberEnum {

	Name("Provider Name") {
		@Override
		public String getRegistrationName() {
			return "company.reg.number";
		}
	},

	ProviderNumber("SDL Number") {
		@Override
		public String getRegistrationName() {
			return "sdl.number";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new company reg or SDL enum.
	 *
	 * @param displayNameX the display name X
	 */
	private ProviderNameRegisTrationNumberEnum(String displayNameX) {
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

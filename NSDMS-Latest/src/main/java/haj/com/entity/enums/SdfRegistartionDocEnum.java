package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum WspTypeEnum.
 */
public enum SdfRegistartionDocEnum {

	AppointmentLetter("Appointmen tLetter") {
	},
	
	RegistrationCertificate("Registration Certificate") {
	};

	private String displayName;

	private SdfRegistartionDocEnum(String displayNameX) {
		displayName = displayNameX;
	}

	public String getFriendlyName() {
		return displayName;
	}
}

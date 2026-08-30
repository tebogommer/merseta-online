package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum ApprovalEnum.
 */
public enum ArplReportingEnum {

	WithInitiator("verification required") {
	}, 
	WithMersetaOne("validation required") {
	},
	WithMersetaTwo("evidence collation required") {
	},
	WithTestCenter("NAMB distribution required") {
	},
	AwaitingCert("waiting certificate") {
	},
	WaitingDis("waiting distribution") {
	},
	NambRecived("certificates received from NAMB") {
	},
	CertificatesDis("certificates distributed") {
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private ArplReportingEnum(String displayNameX) {
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

}

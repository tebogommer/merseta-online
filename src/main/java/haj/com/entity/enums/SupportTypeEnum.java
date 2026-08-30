package haj.com.entity.enums;

public enum SupportTypeEnum {
	 ApplicationSupport("Application Support"),
	 TechnicalSupport("Technical Support");
	
	private String displayName;

	
	private SupportTypeEnum(String displayName) {
		this.displayName = displayName;
	}

	public String getFriendlyName() {
		return displayName;
	}
	

}

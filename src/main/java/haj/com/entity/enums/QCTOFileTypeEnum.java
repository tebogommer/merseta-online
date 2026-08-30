package haj.com.entity.enums;

public enum QCTOFileTypeEnum {

	File1("Learner Qualification Achievement Status (Knowledge, Practical and Work Experience) ") {
	},
	File2("Learner Qualification Achievement Status (EISA)") {
	};

	private QCTOFileTypeEnum(String displayName) {
		this.displayName = displayName;
	}

	private String displayName;

	public String getFriendlyName() {
		return displayName;
	}

}

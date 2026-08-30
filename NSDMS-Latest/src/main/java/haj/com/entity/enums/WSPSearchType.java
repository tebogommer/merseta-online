package haj.com.entity.enums;

public enum WSPSearchType {

	searchByCompany("Search By Company") {
	},
	searchByFinancialYear("Search By DHET Scheme Year") {
	},
	searchByYearRange("Search By DHET Scheme Year Range") {
	};

	private String displayName;

	private WSPSearchType(String displayNameX) {
		displayName = displayNameX;
	}

	public String getFriendlyName() {
		return displayName;
	}
}

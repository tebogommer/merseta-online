public class testSubString {
	public static void main(String[] args) {
		/**
		 * Occupation Cat: OFO_2 (Professionals)
		 */	
		// 2015-131102-1
		// 2015-232116
		String test = "2015-131102-1";
		String majorGroup = test.substring(5, 6); // Major group would be 2
		System.out.println("majorGroup: " + majorGroup);
		String minorGroup = test.substring(5, 8); // Minor Group: 232
		System.out.println("minorGroup: " + minorGroup);
		String subMinorGroup = test.substring(5, 7);// Sub Minor group: 23
		System.out.println("subMinorGroup: " + subMinorGroup);
		String unitGroup = test.substring(5, 9); // unitGroup: 2321
		System.out.println("unitGroup: " + unitGroup);
		String ofoCodeFromSpecial = test.substring(0, test.lastIndexOf("-")).trim();
		System.out.println(ofoCodeFromSpecial);
		//url.substring(url.lastIndexOf("/") + 1, url.indexOf("?"))
	}
}
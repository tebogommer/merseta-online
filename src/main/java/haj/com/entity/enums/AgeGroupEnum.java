package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum AgeGroupEnum.
 */
public enum AgeGroupEnum {

	/** The eighteen twentyfour. */
	EIGHTEEN_TWENTYFOUR("18-24") {
		public int getMiddleAge() {
			return 21;
		}
	},

	/** The twentyfive thirtyfour. */
	TWENTYFIVE_THIRTYFOUR("25-34") {
		public int getMiddleAge() {
			return 29;
		}
	},
	
	/** The thirtyfive fourtyfour. */
	THIRTYFIVE_FOURTYFOUR("35-44") {
		public int getMiddleAge() {
			return 39;
		}
	},

	/** The fourtyfive. */
	FOURTYFIVE("45-54") {
		public int getMiddleAge() {
			return 49;
		}
	},
	
	/** The fiftyfive sixtyfour. */
	FIFTYFIVE_SIXTYFOUR("55-64") {
		public int getMiddleAge() {
			return 59;
		}
	},

	/** The morethan sixtyfive. */
	MORETHAN_SIXTYFIVE(">64") {
		public int getMiddleAge() {
			return 66;
		}
	};

	/** The display name. */
	private String displayName;
	
	/** The middle age. */
	private int middleAge;

	/**
	 * Instantiates a new age group enum.
	 *
	 * @param displayNameX the display name X
	 */
	private AgeGroupEnum(String displayNameX) {
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
	 * Gets the middle age.
	 *
	 * @return the middle age
	 */
	public int getMiddleAge() {
		return middleAge;
	}

}

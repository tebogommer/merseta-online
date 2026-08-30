package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum SectionEnum.
 */
public enum HighestEducationEnum {

	BelowMetric("Below Matric/Standard 10 ") {
		public String getFriendlyNameDetail() {
			return "Below Matric/Standard 10 ";
		}
	},
	
	/** The Questions. */
	AboveMetric("Above Matric/Standard 10") {
		public String getFriendlyNameDetail() {
			return "Above Matric/Standard 10";
		}
	},
	
	/** The N1. */
	N1("N1") {
		public String getFriendlyNameDetail() {
			return "N1";
		}
	},
	
	/** The N2. */
	N2("N2") {
		public String getFriendlyNameDetail() {
			return "N2";
		}
	},
	
	/** The N3. */
	N3("N3") {
		public String getFriendlyNameDetail() {
			return "N3";
		}
	},
	
	/** The N4. */
	N4("N4") {
		public String getFriendlyNameDetail() {
			return "N4";
		}
	},
	
	/** The N5. */
	N5("N5") {
		public String getFriendlyNameDetail() {
			return "N5";
		}
	},
	
	/** The N6. */
	N6("N6") {
		public String getFriendlyNameDetail() {
			return "N6";
		}
	},
	
	/** The N6. */
	NCVLevel2("NCV Level 2") {
		public String getFriendlyNameDetail() {
			return "NCV Level 2";
		}
	},
	
	/** The N6. */
	NCVLevel3("NCV Level 3") {
		public String getFriendlyNameDetail() {
			return "NCV Level 3";
		}
	},
	
	/** The N6. */
	NCVLevel4("NCV Level 4") {
		public String getFriendlyNameDetail() {
			return "NCV Level 4";
		}
	},
	
	/** The N6. */
	S1("S1") {
		public String getFriendlyNameDetail() {
			return "S1";
		}
	},
	
	/** The N6. */
	S2("S2") {
		public String getFriendlyNameDetail() {
			return "S2";
		}
	},
	
	/** The N6. */
	S3("S3") {
		public String getFriendlyNameDetail() {
			return "S3";
		}
	},
	
	/** Grade 12 / Std 10. */
	Grade12to10("Grade 12 / Std 10") {
		public String getFriendlyNameDetail() {
			return "S3";
		}
	},
	
	/** Grade 11 / Std 9. */
	Grade11toStd9("Grade 11 / Std 9") {
		public String getFriendlyNameDetail() {
			return "S3";
		}
	},
	
	/** Grade 10 / Std 8. */
	Grade10toStd8("Grade 10 / Std 8") {
		public String getFriendlyNameDetail() {
			return "S3";
		}
	},
	
	/**Grade 9 / Std 7. */
	Grade9toStd7("Grade 9 / Std 7") {
		public String getFriendlyNameDetail() {
			return "S3";
		}
	},
	
	/**Grade 8  / Std 6. */
	Grade8toStd6("Grade 8  / Std 6") {
		public String getFriendlyNameDetail() {
			return "S3";
		}
	},
	
	/**Below grade 7/ Std 5. */
	BelowGrade7Std5("Below grade 7/ Std 5") {
		public String getFriendlyNameDetail() {
			return "S3";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new section enum.
	 *
	 * @param displayNameX the display name X
	 */
	private HighestEducationEnum(String displayNameX) {
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

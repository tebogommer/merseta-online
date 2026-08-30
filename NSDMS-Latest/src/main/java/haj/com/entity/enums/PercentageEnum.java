package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum WspTypeEnum.
 */
public enum PercentageEnum {

	Ten("10%") {
		@Override
		public Long getPercentage() {
			return 10l;
		}
	},

	Twenty("20%") {
		@Override
		public Long getPercentage() {
			return 20l;
		}
	},
	Thirty("30%") {
		@Override
		public Long getPercentage() {
			return 30l;
		}
	},

	Fourty("40%") {
		@Override
		public Long getPercentage() {
			return 40l;
		}
	},

	Fifty("50%") {
		@Override
		public Long getPercentage() {
			return 50l;
		}
	},
	Sixty("60%") {
		@Override
		public Long getPercentage() {
			return 60l;
		}
	},

	Seventy("70%") {
		@Override
		public Long getPercentage() {
			return 70l;
		}
	},

	Eighty("80%") {
		@Override
		public Long getPercentage() {
			return 80l;
		}
	},
	Nighty("90%") {
		@Override
		public Long getPercentage() {
			return 90l;
		}
	},

	Hundred("100%") {
		@Override
		public Long getPercentage() {
			return 100l;
		}
	};

	/** The display name. */
	private String displayName;
	private Long id;

	/**
	 * Instantiates a new wsp type enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private PercentageEnum(String displayNameX) {
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
	public Long getPercentage() {
		return id;
	}

	/**
	 * Gets the id passport enum by value.
	 *
	 * @param value
	 *            the value
	 * @return the id passport enum by value
	 */
	public static final PercentageEnum getIdPassportEnumByValue(int value) {
		for (PercentageEnum status : PercentageEnum.values()) {
			if (status.ordinal() == value) return status;
		}

		return null;
	}
	
	/**
	 * Returns boolean value base on YesNoEnum FriendlyName
	 * 
	 * */
	public  boolean getYesNoBoolean()
	{
		boolean var=true;
		if(getFriendlyName().equalsIgnoreCase("No"))
		{
			var=false;
		}
		
		return var;
		
	}
}

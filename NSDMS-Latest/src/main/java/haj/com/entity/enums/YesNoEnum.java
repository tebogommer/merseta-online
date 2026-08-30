package haj.com.entity.enums;

import haj.com.constants.HAJConstants;

// TODO: Auto-generated Javadoc
/**
 * The Enum WspTypeEnum.
 */
public enum YesNoEnum {

	Yes("Yes") {
		@Override
		public Long getYesNoLookupId() {
			return HAJConstants.YES_ID;
		}
	},

	No("No") {
		@Override
		public Long getYesNoLookupId() {
			return HAJConstants.NO_ID;
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
	private YesNoEnum(String displayNameX) {
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
	public Long getYesNoLookupId() {
		return id;
	}

	/**
	 * Gets the id passport enum by value.
	 *
	 * @param value
	 *            the value
	 * @return the id passport enum by value
	 */
	public static final YesNoEnum getIdPassportEnumByValue(int value) {
		for (YesNoEnum status : YesNoEnum.values()) {
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

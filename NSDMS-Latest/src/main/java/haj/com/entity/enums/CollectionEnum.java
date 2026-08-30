package haj.com.entity.enums;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Enum IdPassportEnum.
 */
public enum CollectionEnum {

	Courier("Courier") {
		@Override
		public String getRegistrationName() {
			return "Courier";
		}
		@Override
		public String getRgb() {
			return "rgba(204, 133, 31)";
		}
	},

	PersonalCollection("Personal Collection") {
		@Override
		public String getRegistrationName() {
			return "Personal Collection";
		}
		@Override
		public String getRgb() {
			return "rgba(0, 84, 164)";
		}
	},

	RegisteredMail("Registered Mail") {
		@Override
		public String getRegistrationName() {
			return "Registered Mail";
		}
		@Override
		public String getRgb() {
			return "rgba(238, 49, 39)";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new id passport enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private CollectionEnum(String displayNameX) {
		displayName = displayNameX;
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
	 * Gets the registration name.
	 *
	 * @return the registration name
	 */
	public String getRegistrationName() {
		return displayName;
	}
	
	public String getRgb() {
		return displayName;
	}

	/**
	 * Gets the id passport enum by value.
	 *
	 * @param value
	 *            the value
	 * @return the id passport enum by value
	 */
	public static final CollectionEnum getIdPassportEnumByValue(String value) {
		Integer val = 0;
		try {
			val = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		for (CollectionEnum status : CollectionEnum.values()) {
			if (status.ordinal() == val) return status;
		}
		return null;
	}
	
	public static List<CollectionEnum> getAllValues() {
		List<CollectionEnum> up = new ArrayList<>();
		up.add(CollectionEnum.Courier);
		up.add(CollectionEnum.PersonalCollection);
		up.add(CollectionEnum.RegisteredMail);
		return up;
	}
}

package haj.com.entity.enums;

import haj.com.constants.HAJConstants;

// TODO: Auto-generated Javadoc
/**
 * The Enum LearnerCompletenceEnum.
 */
public enum LearnerCompletenceEnum {

	Knowledge("in:Knowledge") {
		@Override
		public String getFriendlyName() {
			return "Knowledge";
		}
	},

	Practical("Practical") {
		@Override
		public String getFriendlyName() {
			return "Practical";
		}
	},
	Workplace("Workplace") {
		@Override
		public String getFriendlyName() {
			return "Workplace";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new LearnerCompletenceEnum type enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private LearnerCompletenceEnum(String displayNameX) {
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

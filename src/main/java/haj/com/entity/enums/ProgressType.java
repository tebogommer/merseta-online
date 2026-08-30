package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum CompanyRegOrSDLEnum.
 */
public enum ProgressType {

	CORE("Core") {
		@Override
		public TradeTypeEnum getInterventionType() {
			return TradeTypeEnum.LEARNERSHIP;
		}
	},
	FUNDEMENTAL("Fundemental") {
		@Override
		public TradeTypeEnum getInterventionType() {
			return TradeTypeEnum.LEARNERSHIP;
		}
	},
	ELECTIVES("Electives") {
		@Override
		public TradeTypeEnum getInterventionType() {
			return TradeTypeEnum.LEARNERSHIP;
		}
	},
	OVERAll_PROGRESS("Overall Progress") {
		@Override
		public TradeTypeEnum getInterventionType() {
			return TradeTypeEnum.LEARNERSHIP;
		}
	},
	TRADE("Trade") {
		@Override
		public TradeTypeEnum getInterventionType() {
			return TradeTypeEnum.MOTOR;
		}
	},
	LEVEL_1("Level 1") {
		@Override
		public TradeTypeEnum getInterventionType() {
			return TradeTypeEnum.CBMT;
		}
	},
	LEVEL_2("Level 2") {
		@Override
		public TradeTypeEnum getInterventionType() {
			return TradeTypeEnum.CBMT;
		}
	},
	LEVEL_3("Level 3") {
		@Override
		public TradeTypeEnum getInterventionType() {
			return TradeTypeEnum.CBMT;
		}
	},
	LEVEL_4("Level 4") {
		@Override
		public TradeTypeEnum getInterventionType() {
			return TradeTypeEnum.CBMT;
		}
	},
	PHASE_1("Phase 1") {
		@Override
		public TradeTypeEnum getInterventionType() {
			return TradeTypeEnum.METAL;
		}
	},
	PHASE_2("Phase 2") {
		@Override
		public TradeTypeEnum getInterventionType() {
			return TradeTypeEnum.METAL;
		}
	},
	PHASE_3("Phase 3") {
		@Override
		public TradeTypeEnum getInterventionType() {
			return TradeTypeEnum.METAL;
		}
	},
	PHASE_4("Phase 4") {
		@Override
		public TradeTypeEnum getInterventionType() {
			return TradeTypeEnum.METAL;
		}
	};

	/** The display name. */
	private String displayName;
	private TradeTypeEnum interventionTypeEnum;

	/**
	 * Instantiates a new company reg or SDL enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private ProgressType(String displayNameX) {
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

	public TradeTypeEnum getInterventionType() {
		return interventionTypeEnum;
	}

}

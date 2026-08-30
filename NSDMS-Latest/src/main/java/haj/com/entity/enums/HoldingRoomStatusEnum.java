package haj.com.entity.enums;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Enum WspStatusEnum.
 */
public enum HoldingRoomStatusEnum {

	RECOMMENDED("Recommended"), NOT_RECOMMENDED("Not recommended"), RECOMMENDED_SUBJECT_TO_PROPOSAL_AMENDMENTS("Recommended subject to proposal amendments"), RE_ASSIGNED_WITH_MER_SETA("Re-assigned with merseta"), WITHDRAWAL_BY_THE_APPLICANT("Withdrawal by the applicant");

	private String displayName;

	private HoldingRoomStatusEnum(String displayNameX) {
		displayName = displayNameX;
	}

	public String getFriendlyName() {
		return displayName;
	}
}

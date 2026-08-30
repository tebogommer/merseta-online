package haj.com.entity.enums;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Enum ApprovalEnum.
 */
public enum TradeTestProgressReportingEnum {

	AllData("All Data") {
		@Override
		public String getDisplayNameReport() {
			return "All RPL Data";
		}
		@Override
		public String getDisplayNameGraph() {
			return "All RPL Data";
		}
		@Override
		public String getInfo() {
			return "All RPL Data";
		}
		@Override
		public String getRgb() {
			return "rgba(255, 99, 132, 0.2)";
		}
		@Override
		public String getRgbBorder() {
			return "rgba(255, 99, 132, 1)";
		}
	},WithInitiator("WithInitiator") {
		@Override
		public String getDisplayNameReport() {
			return "Application Stage";
		}
		@Override
		public String getDisplayNameGraph() {
			return "WithInitiatorOne";
		}
		@Override
		public String getInfo() {
			return "Initiator filling the application for submission / resubmission";
		}
		@Override
		public String getRgb() {
			return "rgba(255, 99, 132, 0.2)";
		}
		@Override
		public String getRgbBorder() {
			return "rgba(255, 99, 132, 1)";
		}
	}, 
	WithMersetaOne("WithMersetaOne") {
		@Override
		public String getDisplayNameReport() {
			return "Application Review";
		}
		@Override
		public String getDisplayNameGraph() {
			return "WithMersetaOne";
		}
		@Override
		public String getInfo() {
			return "Region client services administrator review application";
		}
		@Override
		public String getRgb() {
			return "rgba(255, 159, 64, 0.2)";
		}
		@Override
		public String getRgbBorder() {
			return "rgba(255, 159, 64, 1)";
		}
	},
	WithTestCenterProvideDate("WithTestCenterProvideDate") {
		@Override
		public String getDisplayNameReport() {
			return "TTC - Test Dates";
		}
		
		@Override
		public String getDisplayNameGraph() {
			return "WithTestCenterProvideDate";
		}
		
		@Override
		public String getInfo() {
			return "With test Centre to provide dates of learner tests";
		}
		
		@Override
		public String getRgb() {
			return "rgba(238, 49, 36, 0.2)";
		}
		@Override
		public String getRgbBorder() {
			return "rgba(238, 49, 36, 1)";
		}
	},
	WithTestCenterProvideResults("WithTestCenterProvideResults") {
		@Override
		public String getDisplayNameReport() {
			return "TTC - Provide Results";
		}	
		@Override
		public String getDisplayNameGraph() {
			return "WithTestCenterProvideResults";
		}
		@Override
		public String getInfo() {
			return "With TTC for learner to take test and provide results";
		}
		@Override
		public String getRgb() {
			return "rgba(176, 110, 14, 0.2)";
		}
		@Override
		public String getRgbBorder() {
			return "rgba(176, 110, 14, 1)";
		}
	},
	WithTestCenterAlterationAfterSubmission("WithTestCenterAlterationAfterSubmission") {
		@Override
		public String getDisplayNameReport() {
			return "TTC - Alterations/Amendmnets";
		}
		@Override
		public String getDisplayNameGraph() {
			return "WithTestCenterAlterationAfterSubmission";
		}
		@Override
		public String getInfo() {
			return "With TTC for alterations after submission";
		}
		@Override
		public String getRgb() {
			return "rgba(0, 84, 164, 0.2)";
		}
		@Override
		public String getRgbBorder() {
			return "rgba(0, 84, 164, 1)";
		}
	},
	WithMersetaTwo("WithMersetaTwo") {
		@Override
		public String getDisplayNameReport() {
			return "TTC Results Review";
		}
		
		@Override
		public String getDisplayNameGraph() {
			return "WithMersetaFive";
		}
		
		@Override
		public String getInfo() {
			return "With Coordinator to review and sign off";
		}
		
		@Override
		public String getRgb() {
			return "rgba(114, 205, 244, 0.2)";
		}
		@Override
		public String getRgbBorder() {
			return "rgba(114, 205, 244, 1)";
		}
	},
	
	WithMersetaThree("WithMersetaThree") {
		@Override
		public String getDisplayNameReport() {
			return "NAMB Date Submission";
		}
		
		@Override
		public String getDisplayNameGraph() {
			return "WithMersetaSix";
		}
		
		@Override
		public String getInfo() {
			return "With Central Admin to provide NAMB date submission";
		}
		
		@Override
		public String getRgb() {
			return "rgba(102, 102, 102, 0.2)";
		}
		@Override
		public String getRgbBorder() {
			return "rgba(102, 102, 102, 1)";
		}
		
	},
	AwaitingNambDesc("AwaitingNambDesc") {
		@Override
		public String getDisplayNameReport() {
			return "Awaiting NAMB Decision";
		}
		@Override
		public String getDisplayNameGraph() {
			return "AwaitingNambDesc";
		}
		@Override
		public String getInfo() {
			return "In holding area awaiting NAMB decision";
		}
		@Override
		public String getRgb() {
			return "rgba(87, 43, 0, 0.2)";
		}
		@Override
		public String getRgbBorder() {
			return "rgba(87, 43, 0, 1)";
		}
	},
	ApprovedByNamb("ApprovedByNamb") {
		@Override
		public String getDisplayNameReport() {
			return "NAMB Approval";
		}
		@Override
		public String getDisplayNameGraph() {
			return "ApprovedByNamb";
		}
		@Override
		public String getInfo() {
			return "RPL approved by NAMB awaiting certificate distribution";
		}
		@Override
		public String getRgb() {
			return "rgba(204, 133, 0, 0.2)";
		}
		@Override
		public String getRgbBorder() {
			return "rgba(204, 133, 0, 1)";
		}
	},
	CertificateDistribution("CertificateDistribution") {
		@Override
		public String getDisplayNameReport() {
			return "Certificate Distributed";
		}
		
		@Override
		public String getDisplayNameGraph() {
			return "CertificateDistribution";
		}
		
		@Override
		public String getInfo() {
			return "Certificate Distributed";
		}
		
		@Override
		public String getRgb() {
			return "rgba(125, 112, 8, 0.2)";
		}
		@Override
		public String getRgbBorder() {
			return "rgba(125, 112, 8, 1)";
		}
		
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new approval enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private TradeTestProgressReportingEnum(String displayNameX) {
		displayName = displayNameX;
	}

	public String getRgbBorder() {
		return displayName;
	}

	public String getRgb() {
		return displayName;
	}

	public String getInfo() {
		return displayName;
	}

	public String getDisplayNameGraph() {
		return displayName;
	}

	public String getDisplayNameReport() {
		return displayName;
	}

	/**
	 * Gets the friendly name.
	 *
	 * @return the friendly name
	 */
	public String getFriendlyName() {
		return displayName;
	}
	
	public static List<TradeTestProgressReportingEnum> getAllValues() {
		List<TradeTestProgressReportingEnum> up = new ArrayList<>();
		up.add(TradeTestProgressReportingEnum.AllData);
		up.add(TradeTestProgressReportingEnum.WithInitiator);
		up.add(TradeTestProgressReportingEnum.WithMersetaOne);
		up.add(TradeTestProgressReportingEnum.WithTestCenterProvideDate);
		up.add(TradeTestProgressReportingEnum.WithTestCenterProvideResults);
		up.add(TradeTestProgressReportingEnum.WithTestCenterAlterationAfterSubmission);
		up.add(TradeTestProgressReportingEnum.WithMersetaTwo);
		up.add(TradeTestProgressReportingEnum.WithMersetaThree);
		up.add(TradeTestProgressReportingEnum.AwaitingNambDesc);
		up.add(TradeTestProgressReportingEnum.ApprovedByNamb);
		up.add(TradeTestProgressReportingEnum.CertificateDistribution);
		return up;
	}
	
	public static List<TradeTestProgressReportingEnum> getGraphValues() {
		List<TradeTestProgressReportingEnum> up = new ArrayList<>();
		up.add(TradeTestProgressReportingEnum.WithInitiator);
		up.add(TradeTestProgressReportingEnum.WithMersetaOne);
		up.add(TradeTestProgressReportingEnum.WithTestCenterProvideDate);
		up.add(TradeTestProgressReportingEnum.WithTestCenterProvideResults);
		up.add(TradeTestProgressReportingEnum.WithTestCenterAlterationAfterSubmission);
		up.add(TradeTestProgressReportingEnum.WithMersetaTwo);
		up.add(TradeTestProgressReportingEnum.WithMersetaThree);
		up.add(TradeTestProgressReportingEnum.AwaitingNambDesc);
		up.add(TradeTestProgressReportingEnum.ApprovedByNamb);
		up.add(TradeTestProgressReportingEnum.CertificateDistribution);
		return up;
	}

}
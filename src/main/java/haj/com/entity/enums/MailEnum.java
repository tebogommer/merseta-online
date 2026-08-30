package haj.com.entity.enums;

public enum MailEnum {
	 SdfForApproval("New SDF for approval"),
	 SdfApproved("SDF has been Approved"),	 
	 WSPRequiresReview("WSP requires review"),	 
	 WSPRequiresSignOff("WSP requires signoff"),	 
	 WSPRejectedReview("WSP rejected review before sign off and submission"),	 
	 WSPSubmittedAndSignedReview("WSP submitted and signed off please review"),	 
	 TPNewRequiresReview("New Training Provider for review"),
	 TPNewRequiresApproval("New Training Provider for approval"),
	 TPRejected("Training Provider has been rejected"),
	 Task("Text for task...will be taken from the Task configuration"),
	 AMForApproval("New Assessor and Moderator for approval"),
	 TPForApproval("New Training provider for approval"),
	 AMRejectedReview("Assessor Moderator Task was rejected please review"),
	 MGSubmission("Mandatory grant submission"),
	DGSubmission("Discretionary grant submission"),
	InterSetaTransfer("Inter-SETA Transfer approval"),
	LevyFileUploadNotification("Notification to CEO, COO, CFO of successful levy upload"),	 
	WSPRejectedReviewDocument("WSP documents rejected"),
	SDPExtensionOfScopeRejectted("Training Provider Extension Of Scope has been rejected"),
	SDPExtensionOfScopeApproval("Training Provider Extension Of Scope has been Approval");
	private String displayName;

	
	private MailEnum(String displayName) {
		this.displayName = displayName;
	}

	public String getFriendlyName() {
		return displayName;
	}
	

}

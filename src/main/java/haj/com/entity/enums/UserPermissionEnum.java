package haj.com.entity.enums;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Enum UserPermissionEnum.
 */
public enum UserPermissionEnum {

	/** The Approve. */
	Approve("Approve") {
		public String getRegistrationName() {
			return "approve";
		}
	},

	/** The Edit. */
	Edit("Edit"){
		public String getRegistrationName() {
			return "edit";
		}
	},
	
	/** The View. */
	View("View") {
		public String getRegistrationName() {
			return "view";
		}
	},
	
	/** The Upload. */
	Upload("Upload") {
		public String getRegistrationName() {
			return "upload.doc.permission";
		}
	},
	
	/** The Edit upload. */
	EditUpload("Edit And Upload") {
		public String getRegistrationName() {
			return "edit.upload";
		}
	},
	
	
	
	/** The Final approval. */
	FinalApproval("Final Approval") {
		public String getRegistrationName() {
			return "final.approval";
		}
	},
	
	/** The Final Upload approval. */
	FinalUploadApproval("Final Upload Approval") {
		public String getRegistrationName() {
			return "final.upload.approval";
		}
	},
	
	/** The Final Doc Upload. */
	FinalUpload("Final Upload") {
		public String getRegistrationName() {
			return "final.doc.upload";
		}
	},
	
	/** The Final Doc Upload. */
	FinalEdit("Final Edit") {
		public String getRegistrationName() {
			return "final.edit";
		}
	},
	
	/** The Final Doc Upload. */
	UploadCSV("Upload CSV") {
		public String getRegistrationName() {
			return "upload.csv";
		}
	},
	
	/** The Final Doc Upload. */
	FinalView("Final View") {
		public String getRegistrationName() {
			return "final.view";
		}
	},
	
	/** The Final Doc Upload. */
	SignOff("Sign Off") {
		public String getRegistrationName() {
			return "sign.off";
			
		}
	},
	
	/** The upload and approve. */
	UploadApprove("Upload And Approve") {
		public String getRegistrationName() {
			return "upload.approve";
		}
	},
	
	/** Final Edit and upload. */
	FinalEditUpload("Final Edit And Upload") {
		public String getRegistrationName() {
			return "final.edit.upload";
		}
	};
	

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new user permission enum.
	 *
	 * @param displayNameX the display name X
	 */
	private UserPermissionEnum(String displayNameX) {
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
	public String getRegistrationName() {
		return displayName;
	}
	
	public static List<UserPermissionEnum> getEditPermissions() {
		List<UserPermissionEnum> up = new ArrayList<>();
		up.add(Edit);
		up.add(EditUpload);
		up.add(FinalEdit);
		up.add(FinalEditUpload);
		return up;
	}
	
	public static List<UserPermissionEnum> getUploadPermissions() {
		List<UserPermissionEnum> up = new ArrayList<>();
		up.add(EditUpload);
		up.add(Upload);
		up.add(FinalUpload);
		up.add(FinalUploadApproval);
		up.add(UploadApprove);
		up.add(UploadCSV);
		up.add(FinalEditUpload);
		return up;
	}
	
	public static List<UserPermissionEnum> getCompleteTaskPermissions() {
		List<UserPermissionEnum> up = new ArrayList<>();
		up.add(Edit);
		up.add(EditUpload);
		up.add(SignOff);
		up.add(View);
		up.add(Upload);
		up.add(UploadCSV);
		return up;
	}
	
	public static List<UserPermissionEnum> getApproveTaskPermissions() {
		List<UserPermissionEnum> up = new ArrayList<>();
		up.add(Approve);
		up.add(UploadApprove);
		return up;
	}
	
	public static List<UserPermissionEnum> getFinalApproveTaskPermissions() {
		List<UserPermissionEnum> up = new ArrayList<>();
		up.add(FinalApproval);
		up.add(FinalEdit);
		up.add(FinalEditUpload);
		up.add(FinalUpload);
		up.add(FinalUploadApproval);
		up.add(FinalView);
		return up;
	}
}

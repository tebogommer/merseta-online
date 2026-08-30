package haj.com.entity.enums;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Enum TaskStatusEnum.
 */
public enum TaskStatusEnum {

	/** 0 The Not started. */
	NotStarted("Not Yet Started") {
		public String getRegistrationName() {
			return "notstarted";
		}
	},
	
	/** 1 The Underway. */
	Underway("Underway") {
		public String getRegistrationName() {
			return "underway";
		}
	},
	
	/** 2 The Completed. */
	Completed("Completed") {
		public String getRegistrationName() {
			return "completed";
		}
	},

	/** 3 The Closed. */
	Closed("Closed") {
		public String getRegistrationName() {
			return "closed";
		}
	},

	/** 4 The Overdue. */
	Overdue("Overdue") {
		public String getRegistrationName() {
			return "overdue";
		}
	},

	/** The All. */
	/* 5 For Reporting */
	All("All") {
		public String getRegistrationName() {
			return "all";
		}
	},

	/* 6 */
	ERROR("ERROR") {
		public String getRegistrationName() {
			return "error";
		}
	},

	/* 7 */
	NotYetSubmitted("Not Yet submitted") {
		public String getRegistrationName() {
			return "notYetSubmitted";
		}
	};

	/** The display name. */
	private String displayName;

	/**
	 * Instantiates a new task status enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private TaskStatusEnum(String displayNameX) {
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
	
	// complete status list for tasks status not in error
	public static List<TaskStatusEnum> getNotInErrorStatusList() {
		List<TaskStatusEnum> up = new ArrayList<>();
		up.add(TaskStatusEnum.NotStarted);
		up.add(TaskStatusEnum.Underway);
		up.add(TaskStatusEnum.Completed);
		return up;
	}
	
	// complete status list for tasks open
	public static List<TaskStatusEnum> getTaskStatusOpen() {
		List<TaskStatusEnum> up = new ArrayList<>();
		up.add(TaskStatusEnum.NotStarted);
		up.add(TaskStatusEnum.Underway);
		return up;
	}
}

package haj.com.entity.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum TaskStatusEnum.
 */
public enum TaskStatusAndTypeEnum {

	EXTERNAL_OPEN_TASKS("External Open Tasks") {
		public String getRegistrationName() {
			return "externalOpenTasks";
		}
	},

	EXTERNAL_UNDERWAY_TASKS("External Underway Tasks") {
		public String getRegistrationName() {
			return "externalUnderwayTasks";
		}
	},

	EXTERNAL_COMPLETED_TASKS("External Completed Tasks") {
		public String getRegistrationName() {
			return "externalCompletedTasks";
		}
	},

	EMPLOYEES_OPEN_TASKS("Employees Open Tasks") {
		public String getRegistrationName() {
			return "employeesOpenTasks";
		}
	},

	EMPLOYEES_UNDERWAY_TASKS("Employees Underway Tasks") {
		public String getRegistrationName() {
			return "employeesUnderwayTasks";
		}
	},

	EMPLOYEES_COMPLETED_TASKS("Employees Completed Tasks") {
		public String getRegistrationName() {
			return "employeesCompletedTasks";
		}
	},

	ALL_OPEN_TASKS("All Open Tasks") {
		public String getRegistrationName() {
			return "allOpenTasks";
		}
	},

	ALL_UNDERWAY_TASKS("All Underway Tasks") {
		public String getRegistrationName() {
			return "allUnderwayTasks";
		}
	},

	ALL_COMPLETED_TASKS("All Completed Tasks") {
		public String getRegistrationName() {
			return "all Completed Tasks";
		}
	};

	private String displayName;

	/**
	 * Instantiates a new task status and type enum.
	 *
	 * @param displayNameX
	 *            the display name X
	 */
	private TaskStatusAndTypeEnum(String displayNameX) {
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
}

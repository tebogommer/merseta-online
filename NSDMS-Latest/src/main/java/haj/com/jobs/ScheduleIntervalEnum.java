package haj.com.jobs;

public enum ScheduleIntervalEnum {

	OnceOff("Once Off"), Daily("Daily"), Weekly("Weekly"), Monthly("Monthly"), Yearly("Yearly"), Quaterly("Quaterly");

	private String displayName;

	private ScheduleIntervalEnum(String displayNameX) {
		displayName = displayNameX;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return displayName;
	}

	public String getFriendlyName() {
		return toString();
	}
}

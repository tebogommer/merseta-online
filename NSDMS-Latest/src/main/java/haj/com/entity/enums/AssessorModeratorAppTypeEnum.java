package haj.com.entity.enums;

import java.util.ArrayList;
import java.util.List;

public enum AssessorModeratorAppTypeEnum {

	/**0*/
	NewAssessorRegistration("Assessor Registration")
	{
		@Override
		public String getRegistrationName() {
			return "Assessor Registration";
		}
	},
	/**1*/
	NewModeratorRegistration("Moderator Registration")
	{
		@Override
		public String getRegistrationName() {
			return "Moderator Registration";
		}
	},
	/**2*/
	NewAssessorAndNewModerator("Assessor and Moderator Registration")
	{
		@Override
		public String getRegistrationName() {
			return "Assessor and Moderator Registration";
		}
	},
	/*NewAssessorAndModeratorReRegistration ("New Assessor and Moderator Re-Registration")
	{
		@Override
		public String getRegistrationName() {
			return "New Assessor and Moderator Re-Registration";
		}
	},*/
	/**3*/
	AssessorExtensionOfScope("Assessor Extension of Scope")
	{
		
		@Override
		public String getRegistrationName() {
			return "Assessor Extension of Scope";
		}
	},
	/**4*/
	AssessorReRegistration("Assessor Re-Registration")
	{
		@Override
		public String getRegistrationName() {
			
			return "Assessor Re-Registration";
		}
	},
	/**5*/
	ModeratorExtensionOfScope("Moderator Extension of Scope")
	{
		@Override
		public String getRegistrationName() {
			return "Moderator Extension of Scope";
		}
		
	},
	/**6*/
	ModeratorReRegistration("Moderator Re-Registration")
	{
		@Override
		public String getRegistrationName() {
			
			return "Moderator Re-Registration";
		}
	};
	
	/*
	 * Assessor Reg types:
	 * (0) NewAssessorRegistration
	 * (4) AssessorReRegistration
	 * 
	 */
	
	/*
	 * Mod Reg Types
	 * (1) NewModeratorRegistration
	 * (6) ModeratorReRegistration
	 */
	
	private AssessorModeratorAppTypeEnum(String displayName)
	{
		this.displayName = displayName;
	}

	private String displayName;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getRegistrationName() {
		return displayName;
	}
	
	
	public static List<AssessorModeratorAppTypeEnum> getAssessorValues() {
		List<AssessorModeratorAppTypeEnum> list = new ArrayList<>();
		list.add(NewAssessorRegistration);
		list.add(AssessorExtensionOfScope);
		list.add(AssessorReRegistration);
		return list;
	}
	
	public static List<AssessorModeratorAppTypeEnum> getAssessorValuesTTC() {
		List<AssessorModeratorAppTypeEnum> list = new ArrayList<>();
		list.add(NewAssessorRegistration);
		list.add(AssessorReRegistration);
		return list;
	}
	
	public static List<AssessorModeratorAppTypeEnum> getModeratorValues() {
		List<AssessorModeratorAppTypeEnum> list = new ArrayList<>();
		list.add(NewModeratorRegistration);
		list.add(ModeratorExtensionOfScope);
		list.add(ModeratorReRegistration);
		return list;
	}
	
	public static List<AssessorModeratorAppTypeEnum> getModeratorValuesTTC() {
		List<AssessorModeratorAppTypeEnum> list = new ArrayList<>();
		list.add(NewModeratorRegistration);
		list.add(ModeratorReRegistration);
		return list;
	}
	
}

package haj.com.entity.enums;

import java.util.ArrayList;
import java.util.List;

public enum AssessorModType {

	Assessor("Assessor") {
	},
	Moderator("Moderator") {
	},
	AssessorAndModerator("Assessor And Moderator"){
	};

	private String displayName;

	private AssessorModType(String displayNameX) {
		displayName = displayNameX;
	}

	public String getFriendlyName() {
		return displayName;
	}
	

	public static List<AssessorModType> getAssessorValues() {
		List<AssessorModType> list = new ArrayList<>();
		list.add(Assessor);
		list.add(AssessorAndModerator);
		return list;
	}
	
	public static List<AssessorModType> getModeratorValues() {
		List<AssessorModType> list = new ArrayList<>();
		list.add(Moderator);
		list.add(AssessorAndModerator);
		return list;
	}
}

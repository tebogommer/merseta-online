
package haj.com.bean;

public class ModularTrainingBean {

	private String description;

	private String module;
	
	private String mentor;

	private String apprentice;

	private String mentor1;

	private String apprentice1;

	public ModularTrainingBean(String description, String module, String mentor, String apprentice, String mentor1,
			String apprentice1) {
		super();
		this.description = description;
		this.module = module;
		this.mentor = mentor;
		this.apprentice = apprentice;
		this.mentor1 = mentor1;
		this.apprentice1 = apprentice1;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getMentor() {
		return mentor;
	}

	public void setMentor(String mentor) {
		this.mentor = mentor;
	}

	public String getApprentice() {
		return apprentice;
	}

	public void setApprentice(String apprentice) {
		this.apprentice = apprentice;
	}

	public String getMentor1() {
		return mentor1;
	}

	public void setMentor1(String mentor1) {
		this.mentor1 = mentor1;
	}

	public String getApprentice1() {
		return apprentice1;
	}

	public void setApprentice1(String apprentice1) {
		this.apprentice1 = apprentice1;
	}

}

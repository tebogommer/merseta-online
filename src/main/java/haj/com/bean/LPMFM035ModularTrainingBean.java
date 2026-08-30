package haj.com.bean;

public class LPMFM035ModularTrainingBean {
	
	private String description;
	private String module;
	private String mentor;
	private String apprentice;
	private String mentor2;
	private String apprentice2;
	
	
	
	public LPMFM035ModularTrainingBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LPMFM035ModularTrainingBean(String description, String module, String mentor, String apprentice,
			String mentor2, String apprentice2) {
		super();
		this.description = description;
		this.module = module;
		this.mentor = mentor;
		this.apprentice = apprentice;
		this.mentor2 = mentor2;
		this.apprentice2 = apprentice2;
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
	public String getMentor2() {
		return mentor2;
	}
	public void setMentor2(String mentor2) {
		this.mentor2 = mentor2;
	}
	public String getApprentice2() {
		return apprentice2;
	}
	public void setApprentice2(String apprentice2) {
		this.apprentice2 = apprentice2;
	}
	
	

}

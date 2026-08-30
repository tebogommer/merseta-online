package haj.com.bean;

public class InterventionBean {
	private String name;
	private boolean selected;
	
	public InterventionBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InterventionBean(String name, boolean selected) {
		super();
		this.name = name;
		this.selected = selected;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}

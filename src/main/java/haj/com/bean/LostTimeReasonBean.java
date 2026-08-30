package haj.com.bean;

public class LostTimeReasonBean 
{
	private String reason;
	private boolean selected;
	
	
	public LostTimeReasonBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LostTimeReasonBean(String reason, boolean selected) {
		super();
		this.reason = reason;
		this.selected = selected;
	}
	
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

}

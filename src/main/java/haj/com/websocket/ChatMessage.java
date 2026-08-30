package haj.com.websocket;

public class ChatMessage {
	private Boolean employee;
    private String message;
    private Long departmentId;
    private Long userId;



	public void setMessage(String v) {
		this.message = v;
	}

	public String getMessage() {
		return this.message;
	}

	public Boolean getEmployee() {
		return employee;
	}

	public void setEmployee(Boolean employee) {
		this.employee = employee;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
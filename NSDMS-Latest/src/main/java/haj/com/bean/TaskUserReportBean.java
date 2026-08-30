
package haj.com.bean;
import java.math.BigDecimal;

// TODO: Auto-generated Javadoc
/**
 * The Class tasksReportBean.
 */
public class TaskUserReportBean {

	private String firstName;
	private String lastName;
	private String emailAddress;
	private BigDecimal completedTasks;
	private BigDecimal underwayTasks;
	private BigDecimal notstartedTasks;

	public TaskUserReportBean() {
		super();
	}

	/** Getters and setters */
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public BigDecimal getCompletedTasks() {
		return completedTasks;
	}

	public void setCompletedTasks(BigDecimal completedTasks) {
		this.completedTasks = completedTasks;
	}

	public BigDecimal getUnderwayTasks() {
		return underwayTasks;
	}

	public void setUnderwayTasks(BigDecimal underwayTasks) {
		this.underwayTasks = underwayTasks;
	}

	public BigDecimal getNotstartedTasks() {
		return notstartedTasks;
	}

	public void setNotstartedTasks(BigDecimal notstartedTasks) {
		this.notstartedTasks = notstartedTasks;
	}

}

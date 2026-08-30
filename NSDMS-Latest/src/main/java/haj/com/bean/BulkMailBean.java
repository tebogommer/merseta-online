
package haj.com.bean;

import haj.com.entity.Users;
import haj.com.entity.enums.AgeGroupEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.lookup.DisabilityStatus;
import haj.com.entity.lookup.Equity;
import haj.com.entity.lookup.Gender;

// TODO: Auto-generated Javadoc
/**
 * The Class DiscretionaryGrantBean.
 */
public class BulkMailBean {

	private Users user;
	private String emailContents;
		
	public BulkMailBean() {
		super();
	}

	public BulkMailBean(Users user, String emailContents) {
		super();
		this.user = user;
		this.emailContents = emailContents;
	}



	/** Getters and setters */
	public Users getUser() {
		return user;
	}
	
	public void setUser(Users user) {
		this.user = user;
	}
	
	public String getEmailContents() {
		return emailContents;
	}
	
	public void setEmailContents(String emailContents) {
		this.emailContents = emailContents;
	}

}
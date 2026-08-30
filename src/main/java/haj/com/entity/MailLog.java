package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import haj.com.framework.IDataEntity;


// TODO: Auto-generated Javadoc
/**
 * The Class MailLog.
 */
@Entity
@Table(name = "mail_log")
public class MailLog implements IDataEntity
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** The user. */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", nullable=true)
	private Users user;
	
	/** The email. */
	@Column(name="email", length=300)
	private String email;
	
    /** The create date. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_date", length=19)
    private Date createDate;

    /** The subject. */
    @Column(name="subject", length=500)
    private String subject;
  
    /** The body. */
    @Column(name="body", columnDefinition="LONGTEXT")
    private String body;
    
    /** The send date. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="send_date", length=19)
    private Date sendDate;
    
    /** The failed. */
    @Column(name="failed")
    private Boolean failed;
     
    /** The error msg. */
    @Column(name="error_msg", columnDefinition="LONGTEXT")
    private String errorMsg; 
    
    
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="bug_report_id", nullable=true)
	private BugReport bugReport;
    
	@Transient
	private Doc doc;
	
	@Transient
	private List<Doc> docList;
	
	/**
	 * Instantiates a new mail log.
	 */
	public MailLog() {
		super();
	}
    

	

	/**
	 * Instantiates a new mail log.
	 *
	 * @param email the email
	 * @param subject the subject
	 * @param body the body
	 */
	public MailLog(String email, String subject, String body) {
		super();
		this.email = email;
		this.subject = subject;
		this.body = body;
	}




	/**
	 * Instantiates a new mail log.
	 *
	 * @param user the user
	 * @param subject the subject
	 * @param body the body
	 */
	public MailLog(Users user, String subject, String body) {
		super();
		this.user = user;
		this.subject = subject;
		this.body = body;
		this.createDate = new Date();
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MailLog other = (MailLog) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public Users getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(Users user) {
		this.user = user;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the creates the date
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate the new creates the date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the subject.
	 *
	 * @param subject the new subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Gets the body.
	 *
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Sets the body.
	 *
	 * @param body the new body
	 */
	public void setBody(String body) {
		this.body = body;
	}



	/**
	 * Gets the send date.
	 *
	 * @return the send date
	 */
	public Date getSendDate() {
		return sendDate;
	}



	/**
	 * Sets the send date.
	 *
	 * @param sendDate the new send date
	 */
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}



	/**
	 * Gets the failed.
	 *
	 * @return the failed
	 */
	public Boolean getFailed() {
		return failed;
	}



	/**
	 * Sets the failed.
	 *
	 * @param failed the new failed
	 */
	public void setFailed(Boolean failed) {
		this.failed = failed;
	}



	/**
	 * Gets the error msg.
	 *
	 * @return the error msg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}



	/**
	 * Sets the error msg.
	 *
	 * @param errorMsg the new error msg
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}



	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}



	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}




	public BugReport getBugReport() {
		return bugReport;
	}




	public void setBugReport(BugReport bugReport) {
		this.bugReport = bugReport;
	}




	public Doc getDoc() {
		return doc;
	}




	public void setDoc(Doc doc) {
		this.doc = doc;
	}




	public List<Doc> getDocList() {
		return docList;
	}




	public void setDocList(List<Doc> docList) {
		this.docList = docList;
	}


}

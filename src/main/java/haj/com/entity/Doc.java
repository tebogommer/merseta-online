package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.DocumentRequiredCheckEnum;
import haj.com.entity.lookup.Modules;
import haj.com.entity.lookup.ModulesTitle;
import haj.com.framework.IDataEntity;
import haj.com.sars.SarsLeviesPaid;

// TODO: Auto-generated Javadoc
/**
 * The Class Doc.
 */
@Entity
@Table(name = "doc")
public class Doc implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** The create date. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** The data. */
	@Transient
	private byte[] data;

	/** The users. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", insertable = true, updatable = true, nullable = true)
	private Users users;

	/** The for users. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "for_user_id", insertable = true, updatable = true, nullable = true)
	private Users forUsers;

	/** The config doc. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "config_doc_id", insertable = true, updatable = true, nullable = true)
	private ConfigDoc configDoc;

	/** The extension. */
	@Column(name = "extension", nullable = true, length = 20)
	private String extension;

	/** The original fname. */
	@Column(name = "original_fname", nullable = true, length = 500)
	private String originalFname;

	/** The content type. */
	@Column(name = "content_type", nullable = true, length = 200)
	private String contentType;

	/** The new fname. */
	@Column(name = "new_fname", nullable = true, length = 200)
	private String newFname;

	/** The doc. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "original_doc_id", insertable = true, updatable = true, nullable = true)
	private Doc doc;

	/** The version no. */
	@Column(name = "version_no")
	private Integer versionNo;

	/** The small file name. */
	@Transient
	private String smallFileName;

	/** The doc verions. */
	@Transient
	private List<Doc> docVerions;

	/** The note. */
	@Column(name = "note", columnDefinition = "LONGTEXT")
	private String note;

	/** The company. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", insertable = true, updatable = true, nullable = true)
	private Company company;

	/** The wsp. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wsp_id", insertable = true, updatable = true, nullable = true)
	private Wsp wsp;

	/** The hosting company templates. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hosting_company_templates_id", insertable = true, updatable = true, nullable = true)
	private HostingCompanyTemplates hostingCompanyTemplates;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "banking_details_id", insertable = true, updatable = true, nullable = true)
	private BankingDetails bankingDetails;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "extension_request_id", insertable = true, updatable = true, nullable = true)
	private ExtensionRequest extensionRequest;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sars_levies_paid_id", insertable = true, updatable = true, nullable = true)
	private SarsLeviesPaid bankStatement;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dg_verification_id", insertable = true, updatable = true, nullable = true)
	private DgVerification dgVerification;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modules_id", insertable = true, updatable = true, nullable = true)
	private Modules modules;

	@Transient
	private boolean required;

	/** The document required check enum. */
	@Enumerated
	@Column(name = "document_required_check_enum")
	private DocumentRequiredCheckEnum documentRequiredCheckEnum;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mail_log_id", insertable = true, updatable = true, nullable = true)
	private MailLog mailLog;

	@Enumerated
	@Column(name = "approval_status")
	private ApprovalEnum approvalStatus;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modules_titles_id", nullable = true)
	private ModulesTitle modulesTitle;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "change_reason_id", nullable = true)
	private ChangeReason changeReason;

	/** target key for task. */
	@Column(name = "target_key", nullable = true)
	private Long targetKey;

	/** target class for task. */
	@Column(name = "target_class", nullable = true)
	private String targetClass;
	
	/** target key for task temp */
	@Column(name = "target_key_temp", nullable = true)
	private Long targetKeyTemp;

	/** target class for task temp */
	@Column(name = "target_class_temp", nullable = true)
	private String targetClassTemp;
	
	@Transient
	private boolean enableReupload;
	
	@Transient
	private byte[] fileContent;

	/**
	 * Instantiates a new doc.
	 */
	public Doc() {
		super();
	}

	public Doc(Long id, Date createDate, Users users, String extension, String originalFname, String newFname, Integer versionNo, String note) {
		super();
		this.id = id;
		this.createDate = createDate;
		this.users = users;
		this.extension = extension;
		this.originalFname = originalFname;
		this.newFname = newFname;
		this.versionNo = versionNo;
		this.note = note;
	}

	/**
	 * Instantiates a new doc.
	 *
	 * @param configDoc
	 *            the config doc
	 * @param company
	 *            the company
	 */
	public Doc(ConfigDoc configDoc, Company company) {
		super();
		this.configDoc = configDoc;
		this.company = company;
	}

	public Doc(ConfigDoc configDoc) {
		super();
		this.configDoc = configDoc;
	}

	/**
	 * Instantiates a new doc.
	 *
	 * @param configDoc
	 *            the config doc
	 * @param wsp
	 *            the wsp
	 */
	public Doc(ConfigDoc configDoc, Wsp wsp) {
		super();
		this.configDoc = configDoc;
		this.wsp = wsp;
	}

	public Doc(ConfigDoc configDoc, BankingDetails bankingDetails) {
		super();
		this.configDoc = configDoc;
		this.bankingDetails = bankingDetails;
	}

	public Doc(ConfigDoc configDoc, DgVerification dgVerification) {
		super();
		this.configDoc = configDoc;
		this.dgVerification = dgVerification;
	}

	public Doc(ConfigDoc configDoc, ExtensionRequest extensionRequest) {
		super();
		this.configDoc = configDoc;
		this.extensionRequest = extensionRequest;
	}

	public Doc(ConfigDoc configDoc, Long targetKey, String targetClass) {
		super();
		this.configDoc = configDoc;
		this.targetKey = targetKey;
		this.targetClass = targetClass;
	}

	public Doc(Modules modules) {
		super();
		this.modules = modules;
	}

	/**
	 * Instantiates a new doc.
	 *
	 * @param configDoc
	 *            the config doc
	 * @param user
	 *            the user
	 */
	public Doc(ConfigDoc configDoc, Users user) {
		super();
		this.configDoc = configDoc;
		this.users = user;
	}

	/**
	 * Instantiates a new doc.
	 *
	 * @param forUsers
	 *            the for users
	 * @param configDoc
	 *            the config doc
	 */
	public Doc(Users forUsers, ConfigDoc configDoc) {
		super();
		this.forUsers = forUsers;
		this.configDoc = configDoc;
	}

	/**
	 * Instantiates a new doc.
	 *
	 * @param id
	 *            the id
	 * @param configDoc
	 *            the config doc
	 * @param company
	 *            the company
	 */
	public Doc(Long id, ConfigDoc configDoc, Company company) {
		super();
		this.id = id;
		this.configDoc = configDoc;
		this.company = company;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Doc other = (Doc) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@JsonIgnore
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the creates the date
	 */
	@JsonIgnore
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate
	 *            the new creates the date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	@JsonIgnore
	public Users getUsers() {
		return users;
	}

	/**
	 * Sets the users.
	 *
	 * @param users
	 *            the new users
	 */
	public void setUsers(Users users) {
		this.users = users;
	}

	/**
	 * Gets the content type.
	 *
	 * @return the content type
	 */
	@JsonIgnore
	public String getContentType() {
		return contentType;
	}

	/**
	 * Sets the content type.
	 *
	 * @param contentType
	 *            the new content type
	 */
	@JsonIgnore
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * Gets the extension.
	 *
	 * @return the extension
	 */
	@JsonIgnore
	public String getExtension() {
		return extension;
	}

	/**
	 * Sets the extension.
	 *
	 * @param extension
	 *            the new extension
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * Gets the original fname.
	 *
	 * @return the original fname
	 */
	@JsonIgnore
	public String getOriginalFname() {
		return originalFname;
	}

	/**
	 * Sets the original fname.
	 *
	 * @param originalFname
	 *            the new original fname
	 */
	public void setOriginalFname(String originalFname) {
		this.originalFname = originalFname;
	}

	/**
	 * Gets the new fname.
	 *
	 * @return the new fname
	 */
	public String getNewFname() {
		return newFname;
	}

	/**
	 * Sets the new fname.
	 *
	 * @param newFname
	 *            the new new fname
	 */
	public void setNewFname(String newFname) {
		this.newFname = newFname;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data
	 *            the new data
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * Gets the small file name.
	 *
	 * @return the small file name
	 */
	public String getSmallFileName() {
		return smallFileName;
	}

	/**
	 * Sets the small file name.
	 *
	 * @param smallFileName
	 *            the new small file name
	 */
	public void setSmallFileName(String smallFileName) {
		this.smallFileName = smallFileName;
	}

	/**
	 * Gets the config doc.
	 *
	 * @return the config doc
	 */
	public ConfigDoc getConfigDoc() {
		return configDoc;
	}

	/**
	 * Sets the config doc.
	 *
	 * @param configDoc
	 *            the new config doc
	 */
	public void setConfigDoc(ConfigDoc configDoc) {
		this.configDoc = configDoc;
	}

	/**
	 * Gets the doc.
	 *
	 * @return the doc
	 */
	public Doc getDoc() {
		return doc;
	}

	/**
	 * Sets the doc.
	 *
	 * @param doc
	 *            the new doc
	 */
	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	/**
	 * Gets the doc verions.
	 *
	 * @return the doc verions
	 */
	public List<Doc> getDocVerions() {
		return docVerions;
	}

	/**
	 * Sets the doc verions.
	 *
	 * @param docVerions
	 *            the new doc verions
	 */
	public void setDocVerions(List<Doc> docVerions) {
		this.docVerions = docVerions;
	}

	/**
	 * Gets the version no.
	 *
	 * @return the version no
	 */
	public Integer getVersionNo() {
		return versionNo;
	}

	/**
	 * Sets the version no.
	 *
	 * @param versionNo
	 *            the new version no
	 */
	public void setVersionNo(Integer versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * Gets the note.
	 *
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Sets the note.
	 *
	 * @param note
	 *            the new note
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * Gets the company.
	 *
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 *
	 * @param company
	 *            the new company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Gets the for users.
	 *
	 * @return the for users
	 */
	public Users getForUsers() {
		return forUsers;
	}

	/**
	 * Sets the for users.
	 *
	 * @param forUsers
	 *            the new for users
	 */
	public void setForUsers(Users forUsers) {
		this.forUsers = forUsers;
	}

	/**
	 * Gets the wsp.
	 *
	 * @return the wsp
	 */
	public Wsp getWsp() {
		return wsp;
	}

	/**
	 * Sets the wsp.
	 *
	 * @param wsp
	 *            the new wsp
	 */
	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	/**
	 * Gets the hosting company templates.
	 *
	 * @return the hosting company templates
	 */
	public HostingCompanyTemplates getHostingCompanyTemplates() {
		return hostingCompanyTemplates;
	}

	/**
	 * Sets the hosting company templates.
	 *
	 * @param hostingCompanyTemplates
	 *            the new hosting company templates
	 */
	public void setHostingCompanyTemplates(HostingCompanyTemplates hostingCompanyTemplates) {
		this.hostingCompanyTemplates = hostingCompanyTemplates;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public BankingDetails getBankingDetails() {
		return bankingDetails;
	}

	public void setBankingDetails(BankingDetails bankingDetails) {
		this.bankingDetails = bankingDetails;
	}

	public DocumentRequiredCheckEnum getDocumentRequiredCheckEnum() {
		return documentRequiredCheckEnum;
	}

	public void setDocumentRequiredCheckEnum(DocumentRequiredCheckEnum documentRequiredCheckEnum) {
		this.documentRequiredCheckEnum = documentRequiredCheckEnum;
	}

	public ExtensionRequest getExtensionRequest() {
		return extensionRequest;
	}

	public void setExtensionRequest(ExtensionRequest extensionRequest) {
		this.extensionRequest = extensionRequest;
	}

	public SarsLeviesPaid getBankStatement() {
		return bankStatement;
	}

	public void setBankStatement(SarsLeviesPaid bankStatement) {
		this.bankStatement = bankStatement;
	}

	public DgVerification getDgVerification() {
		return dgVerification;
	}

	public void setDgVerification(DgVerification dgVerification) {
		this.dgVerification = dgVerification;
	}

	public MailLog getMailLog() {
		return mailLog;
	}

	public void setMailLog(MailLog mailLog) {
		this.mailLog = mailLog;
	}

	public ApprovalEnum getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(ApprovalEnum approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Modules getModules() {
		return modules;
	}

	public void setModules(Modules modules) {
		this.modules = modules;
	}

	public ModulesTitle getModulesTitle() {
		return modulesTitle;
	}

	public void setModulesTitle(ModulesTitle modulesTitle) {
		this.modulesTitle = modulesTitle;
	}

	public ChangeReason getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(ChangeReason changeReason) {
		this.changeReason = changeReason;
	}

	public Long getTargetKey() {
		return targetKey;
	}

	public void setTargetKey(Long targetKey) {
		this.targetKey = targetKey;
	}

	public String getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}

	public boolean isEnableReupload() {
		return enableReupload;
	}

	public void setEnableReupload(boolean enableReupload) {
		this.enableReupload = enableReupload;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public Long getTargetKeyTemp() {
		return targetKeyTemp;
	}

	public void setTargetKeyTemp(Long targetKeyTemp) {
		this.targetKeyTemp = targetKeyTemp;
	}

	public String getTargetClassTemp() {
		return targetClassTemp;
	}

	public void setTargetClassTemp(String targetClassTemp) {
		this.targetClassTemp = targetClassTemp;
	}
}

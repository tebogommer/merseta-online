package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import haj.com.framework.IDataEntity;

//TODO: Auto-generated Javadoc
/**
 * ChangeReason.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "change_reason")
public class ChangeReason implements IDataEntity {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The id.
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** target key for task. */
	@Column(name = "target_key", nullable = true)
	private Long targetKey;

	/** target class for task. */
	@Column(name = "target_class", nullable = true)
	private String targetClass;

	/** The Change Reason description */
	@Column(name = "description", length = 1000)
	private String description;

	/** date task created. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Transient
	private Doc doc;

	@Transient
	private List<Doc> docs;

	public ChangeReason(Long id, Long targetKey, String targetClass, String description, Date createDate) {
		super();
		this.id = id;
		this.targetKey = targetKey;
		this.targetClass = targetClass;
		this.description = description;
		this.createDate = createDate;
	}

	public ChangeReason() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}
}

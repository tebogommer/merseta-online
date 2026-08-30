package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * frequently asked questions.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "frequently_asked_questions")
public class FrequentlyAskedQuestions implements IDataEntity
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of Blank. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	/** Section. */
    @Column(name = "question_section", columnDefinition="LONGTEXT")
	private String questionSection;
	
    /** Question. */
    @Column(name = "question_asked", columnDefinition="LONGTEXT")
	private String questionAsked;
    
    /** Answer. */
    @Column(name = "question_answer", columnDefinition="LONGTEXT")
	private String questionAnswer;
    
	

	/** Is the question still relevant. */
	@Column(name = "question_active", columnDefinition = "BIT default false")
	private Boolean questionActive;;    
    

/*	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="company_id", nullable=true)
	private Company company;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
	private Set<OrderItems> orderItemses = new HashSet<OrderItems>(0);
	*/
	
	/** Note. */
	@Column(name="note", columnDefinition="LONGTEXT")
    private String note;

   

    
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
		FrequentlyAskedQuestions other = (FrequentlyAskedQuestions) obj;
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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
	/**
	 * Gets the question.
	 *
	 * @return the question
	 */

	public String getQuestionAsked() {
		return questionAsked;
	}
	
	/**
	 * Sets the question.
	 *
	 * @param questionAsked the question to set
	 */

	public void setQuestionAsked(String questionAsked) {
		this.questionAsked = questionAsked;
	}
	
	/**
	 * Gets the answer.
	 *
	 * @return the answer
	 */

	public String getQuestionAnswer() {
		return questionAnswer;
	}
	
	/**
	 * Sets the answer.
	 *
	 * @param questionAnswer the answer to set
	 */

	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}
	
	/**
	 * Gets the active status.
	 *
	 * @return the active status
	 */

	public Boolean getQuestionActive() {
		return questionActive;
	}
	
	/**
	 * Sets the active status.
	 *
	 * @param questionActive the status to set
	 */

	public void setQuestionActive(Boolean questionActive) {
		this.questionActive = questionActive;
	}

	public String getQuestionSection() {
		return questionSection;
	}
	
	public String getQuestionSectionNoHTML() {
		return questionSection.replaceAll("<p>", "").replaceAll("<p/>", "");
	}

	public void setQuestionSection(String questionSection) {
		this.questionSection = questionSection;
	}

}

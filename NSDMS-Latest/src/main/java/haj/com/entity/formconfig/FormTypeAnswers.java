package haj.com.entity.formconfig;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class SectionQuestionAnswers.
 */
@Entity
@Table(name = "form_type_answers")
public class FormTypeAnswers implements IDataEntity, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Column(name = "answerDesc", columnDefinition = "LONGTEXT")
	private String answerDesc;

	@Column(name = "points")
	private Long points;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "form_section_questions_id")
	@Fetch(FetchMode.JOIN)
	private FormSectionQuestions formSectionQuestions;

	@Column(name = "order_pos")
	private Integer orderPos;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		FormTypeAnswers other = (FormTypeAnswers) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public FormTypeAnswers clone() {
		FormTypeAnswers clone = null;
		try {
			clone = (FormTypeAnswers) super.clone();
			clone.setId(null);
		} catch (CloneNotSupportedException e) {
			// should never happen
		}
		return clone;
	}

	public FormTypeAnswers clone(FormSectionQuestions formSectionQuestions) {
		FormTypeAnswers clone = null;
		try {
			clone = (FormTypeAnswers) super.clone();
			clone.setFormSectionQuestions(formSectionQuestions);
			clone.setId(null);
		} catch (CloneNotSupportedException e) {
			// should never happen
		}
		return clone;
	}

	public FormTypeAnswers() {
		super();
		this.createDate = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getAnswerDesc() {
		return answerDesc;
	}

	public void setAnswerDesc(String answerDesc) {
		this.answerDesc = answerDesc;
	}

	public Long getPoints() {
		return points;
	}

	public void setPoints(Long points) {
		this.points = points;
	}

	public FormSectionQuestions getFormSectionQuestions() {
		return formSectionQuestions;
	}

	public void setFormSectionQuestions(FormSectionQuestions formSectionQuestions) {
		this.formSectionQuestions = formSectionQuestions;
	}

	public Integer getOrderPos() {
		return orderPos;
	}

	public void setOrderPos(Integer orderPos) {
		this.orderPos = orderPos;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}

package haj.com.entity.formconfig;

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

import haj.com.entity.enums.AnswerTypeEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.framework.IDataEntity;

/**
 * The Class FormSectionQuestions.
 */
@Entity
@Table(name = "form_section_questions")
public class FormSectionQuestions implements IDataEntity, Cloneable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Enumerated
	@Column(name = "answer_type")
	private AnswerTypeEnum answerType;

	@Column(name = "question", columnDefinition = "LONGTEXT")
	private String question;

	@Column(name = "question_desc", columnDefinition = "LONGTEXT")
	private String questionDesc;

	@Column(name = "text_answer", columnDefinition = "LONGTEXT")
	private String textAnswer;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "form_type_sections_id")
	private FormTypeSections formTypeSections;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "form_section_questions_id")
	private FormSectionQuestions formSectionQuestions;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "selected_answer")
	private FormTypeAnswers selectedAnswer;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "show_on_parent_answers_id")
	private FormTypeAnswers showOnParentAnswers;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_answer", length = 19)
	private Date dateAnswer;

	@Enumerated
	@Column(name = "yes_no_enum")
	private YesNoEnum yesNoEnum;

	private boolean required;

	@Column(name = "order_pos")
	private Integer orderPos;

	@Column(name = "used_editor_for_question")
	private Boolean usedEditorForQuestion;

	@Column(name = "display_question")
	private Boolean displayQuestion;

	@Column(name = "multiple_selection_points", nullable = true)
	private Long multipleSelectionPoints;

	@Column(name = "failed_multiple_selection")
	private Boolean failedMultipleSelection;

	@Transient
	private List<String> multipleAnswerSelection;
	
	@Column(name = "marks")
	private Integer marks;

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
		FormSectionQuestions other = (FormSectionQuestions) obj;
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
	public FormSectionQuestions clone() {
		FormSectionQuestions clone = null;
		try {
			clone = (FormSectionQuestions) super.clone();
			clone.setId(null);
		} catch (CloneNotSupportedException e) {
			// should never happen
		}
		return clone;

	}

	public FormSectionQuestions clone(FormTypeSections formTypeSections) {
		FormSectionQuestions clone = null;
		try {
			clone = (FormSectionQuestions) super.clone();
			clone.setFormTypeSections(formTypeSections);
			clone.setId(null);
		} catch (CloneNotSupportedException e) {
			// should never happen
		}
		return clone;

	}

	public FormSectionQuestions clone(FormSectionQuestions formTypeSections) {
		FormSectionQuestions clone = null;
		try {
			clone = (FormSectionQuestions) super.clone();
			clone.setFormSectionQuestions(formTypeSections);
			clone.setId(null);
		} catch (CloneNotSupportedException e) {
			// should never happen
		}
		return clone;

	}

	/**
	 * Instantiates a new form section questions.
	 */
	public FormSectionQuestions() {
		super();
		this.createDate = new Date();
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
	 * Gets the answer type.
	 *
	 * @return the answer type
	 */
	public AnswerTypeEnum getAnswerType() {
		return answerType;
	}

	/**
	 * Sets the answer type.
	 *
	 * @param answerType
	 *            the new answer type
	 */
	public void setAnswerType(AnswerTypeEnum answerType) {
		this.answerType = answerType;
	}

	/**
	 * Gets the question.
	 *
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * Sets the question.
	 *
	 * @param question
	 *            the new question
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * Gets the question desc.
	 *
	 * @return the question desc
	 */
	public String getQuestionDesc() {
		return questionDesc;
	}

	/**
	 * Sets the question desc.
	 *
	 * @param questionDesc
	 *            the new question desc
	 */
	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}

	/**
	 * Gets the form type sections.
	 *
	 * @return the form type sections
	 */
	public FormTypeSections getFormTypeSections() {
		return formTypeSections;
	}

	/**
	 * Sets the form type sections.
	 *
	 * @param formTypeSections
	 *            the new form type sections
	 */
	public void setFormTypeSections(FormTypeSections formTypeSections) {
		this.formTypeSections = formTypeSections;
	}

	/**
	 * Gets the form section questions.
	 *
	 * @return the form section questions
	 */
	public FormSectionQuestions getFormSectionQuestions() {
		return formSectionQuestions;
	}

	/**
	 * Sets the form section questions.
	 *
	 * @param formSectionQuestions
	 *            the new form section questions
	 */
	public void setFormSectionQuestions(FormSectionQuestions formSectionQuestions) {
		this.formSectionQuestions = formSectionQuestions;
	}

	/**
	 * Gets the selected answer.
	 *
	 * @return the selected answer
	 */
	public FormTypeAnswers getSelectedAnswer() {
		return selectedAnswer;
	}

	/**
	 * Sets the selected answer.
	 *
	 * @param selectedAnswer
	 *            the new selected answer
	 */
	public void setSelectedAnswer(FormTypeAnswers selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}

	/**
	 * Gets the show on parent answers.
	 *
	 * @return the show on parent answers
	 */
	public FormTypeAnswers getShowOnParentAnswers() {
		return showOnParentAnswers;
	}

	/**
	 * Sets the show on parent answers.
	 *
	 * @param showOnParentAnswers
	 *            the new show on parent answers
	 */
	public void setShowOnParentAnswers(FormTypeAnswers showOnParentAnswers) {
		this.showOnParentAnswers = showOnParentAnswers;
	}

	/**
	 * Checks if is required.
	 *
	 * @return true, if is required
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * Sets the required.
	 *
	 * @param required
	 *            the new required
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

	/**
	 * Gets the order pos.
	 *
	 * @return the order pos
	 */
	public Integer getOrderPos() {
		return orderPos;
	}

	/**
	 * Sets the order pos.
	 *
	 * @param orderPos
	 *            the new order pos
	 */
	public void setOrderPos(Integer orderPos) {
		this.orderPos = orderPos;
	}

	/**
	 * Gets the text answer.
	 *
	 * @return the text answer
	 */
	public String getTextAnswer() {
		return textAnswer;
	}

	/**
	 * Sets the text answer.
	 *
	 * @param textAnswer
	 *            the new text answer
	 */
	public void setTextAnswer(String textAnswer) {
		this.textAnswer = textAnswer;
	}

	public Boolean getUsedEditorForQuestion() {
		return usedEditorForQuestion;
	}

	public void setUsedEditorForQuestion(Boolean usedEditorForQuestion) {
		this.usedEditorForQuestion = usedEditorForQuestion;
	}

	public Boolean getDisplayQuestion() {
		return displayQuestion;
	}

	public void setDisplayQuestion(Boolean displayQuestion) {
		this.displayQuestion = displayQuestion;
	}

	public Long getMultipleSelectionPoints() {
		return multipleSelectionPoints;
	}

	public void setMultipleSelectionPoints(Long multipleSelectionPoints) {
		this.multipleSelectionPoints = multipleSelectionPoints;
	}

	public List<String> getMultipleAnswerSelection() {
		return multipleAnswerSelection;
	}

	public void setMultipleAnswerSelection(List<String> multipleAnswerSelection) {
		this.multipleAnswerSelection = multipleAnswerSelection;
	}

	public Boolean getFailedMultipleSelection() {
		return failedMultipleSelection;
	}

	public void setFailedMultipleSelection(Boolean failedMultipleSelection) {
		this.failedMultipleSelection = failedMultipleSelection;
	}

	public YesNoEnum getYesNoEnum() {
		return yesNoEnum;
	}

	public void setYesNoEnum(YesNoEnum yesNoEnum) {
		this.yesNoEnum = yesNoEnum;
	}

	public Date getDateAnswer() {
		return dateAnswer;
	}

	public void setDateAnswer(Date dateAnswer) {
		this.dateAnswer = dateAnswer;
	}

	public Integer getMarks() {
		return marks;
	}

	public void setMarks(Integer marks) {
		this.marks = marks;
	}

}

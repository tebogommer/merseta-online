package haj.com.bean;

import java.util.ArrayList;

import haj.com.entity.enums.AnswerTypeEnum;

public class FormSectionQuestionsBean 
{
	private String question;
	private Integer marks;
	private boolean containTitle;
	private AnswerTypeEnum answerType;
	private String questionNum;
	
	public FormSectionQuestionsBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public FormSectionQuestionsBean(String question, Integer marks, AnswerTypeEnum answerType) {
		super();
		this.question = question;
		this.marks = marks;
		this.answerType = answerType;
	}
	
	
	
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public AnswerTypeEnum getAnswerType() {
		return answerType;
	}
	public void setAnswerType(AnswerTypeEnum answerType) {
		this.answerType = answerType;
	}
	public Integer getMarks() {
		return marks;
	}
	public void setMarks(Integer marks) {
		this.marks = marks;
	}

	/**
	 * @return the containTitle
	 */
	public boolean isContainTitle() {
		return containTitle;
	}

	/**
	 * @param containTitle the containTitle to set
	 */
	public void setContainTitle(boolean containTitle) {
		this.containTitle = containTitle;
	}

	/**
	 * @return the questionNum
	 */
	public String getQuestionNum() {
		return questionNum;
	}

	/**
	 * @param questionNum the questionNum to set
	 */
	public void setQuestionNum(String questionNum) {
		this.questionNum = questionNum;
	}
		

}

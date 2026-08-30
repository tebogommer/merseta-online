package haj.com.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import haj.com.entity.enums.QmrEquityEnum;
import haj.com.entity.enums.QmrGenderEnum;
import haj.com.entity.enums.QmrReportType;

/**
 * The Class QmrSummaryBean.
 * Used for counts in native sql quiries for each quarter
 */
public class QmrSummaryBean implements Serializable {

	private String description;
	private QmrReportType qmrReportType;
	private QmrGenderEnum qmrGenderEnum;
	private QmrEquityEnum qmrEquityEnum;
	private Integer quarterOneCount;
	private Integer quarterTwoCount;
	private Integer quarterThreeCount;
	private Integer quarterFourCount;
	private Integer total;
	
	public QmrSummaryBean() {
		super();
		quarterOneCount = 0;
		quarterTwoCount = 0;
		quarterThreeCount = 0;
		quarterFourCount = 0;
		total = 0;
	}

	public QmrSummaryBean(String description, QmrReportType qmrReportType) {
		super();
		this.description = description;
		this.qmrReportType = qmrReportType;
		quarterOneCount = 0;
		quarterTwoCount = 0;
		quarterThreeCount = 0;
		quarterFourCount = 0;
		total = 0;
	}

	public QmrSummaryBean(String description, QmrReportType qmrReportType, QmrGenderEnum qmrGenderEnum, QmrEquityEnum qmrEquityEnum) {
		super();
		this.description = description;
		this.qmrReportType = qmrReportType;
		this.qmrGenderEnum = qmrGenderEnum;
		this.qmrEquityEnum = qmrEquityEnum;
		quarterOneCount = 0;
		quarterTwoCount = 0;
		quarterThreeCount = 0;
		quarterFourCount = 0;
		total = 0;
	}

	/* getters and setters */
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getQuarterOneCount() {
		return quarterOneCount;
	}
	public void setQuarterOneCount(Integer quarterOneCount) {
		this.quarterOneCount = quarterOneCount;
	}
	public Integer getQuarterTwoCount() {
		return quarterTwoCount;
	}
	public void setQuarterTwoCount(Integer quarterTwoCount) {
		this.quarterTwoCount = quarterTwoCount;
	}
	public Integer getQuarterThreeCount() {
		return quarterThreeCount;
	}
	public void setQuarterThreeCount(Integer quarterThreeCount) {
		this.quarterThreeCount = quarterThreeCount;
	}
	public Integer getQuarterFourCount() {
		return quarterFourCount;
	}
	public void setQuarterFourCount(Integer quarterFourCount) {
		this.quarterFourCount = quarterFourCount;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}

	public QmrReportType getQmrReportType() {
		return qmrReportType;
	}

	public void setQmrReportType(QmrReportType qmrReportType) {
		this.qmrReportType = qmrReportType;
	}

	public QmrGenderEnum getQmrGenderEnum() {
		return qmrGenderEnum;
	}

	public void setQmrGenderEnum(QmrGenderEnum qmrGenderEnum) {
		this.qmrGenderEnum = qmrGenderEnum;
	}

	public QmrEquityEnum getQmrEquityEnum() {
		return qmrEquityEnum;
	}

	public void setQmrEquityEnum(QmrEquityEnum qmrEquityEnum) {
		this.qmrEquityEnum = qmrEquityEnum;
	}
}

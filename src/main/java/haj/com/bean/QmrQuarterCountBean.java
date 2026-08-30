package haj.com.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import haj.com.entity.enums.QmrEquityEnum;
import haj.com.entity.enums.QmrReportType;

/**
 * The Class QmrQuarterCountBean.
 * Used for counts in native sql quiries for the quarter
 */
public class QmrQuarterCountBean implements Serializable {

	private String description;
	private QmrReportType qmrReportType;
	private QmrEquityEnum qmrEquityEnum;
	private Integer maleCount;
	private Integer femaleCount;
	private Integer total;
	
	public QmrQuarterCountBean() {
		super();
		maleCount = 0;
		femaleCount = 0;
		total = 0;
	}
	
	public QmrQuarterCountBean(String description, QmrReportType qmrReportType) {
		super();
		this.description = description;
		this.qmrReportType = qmrReportType;
		maleCount = 0;
		femaleCount = 0;
		total = 0;
	}

	public QmrQuarterCountBean(String description, QmrReportType qmrReportType, QmrEquityEnum qmrEquityEnum) {
		super();
		this.description = description;
		this.qmrReportType = qmrReportType;
		this.qmrEquityEnum = qmrEquityEnum;
		maleCount = 0;
		femaleCount = 0;
		total = 0;
	}
	
	/* Getters and Setters */
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getMaleCount() {
		return maleCount;
	}
	public void setMaleCount(Integer maleCount) {
		this.maleCount = maleCount;
	}
	public Integer getFemaleCount() {
		return femaleCount;
	}
	public void setFemaleCount(Integer femaleCount) {
		this.femaleCount = femaleCount;
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
	public QmrEquityEnum getQmrEquityEnum() {
		return qmrEquityEnum;
	}
	public void setQmrEquityEnum(QmrEquityEnum qmrEquityEnum) {
		this.qmrEquityEnum = qmrEquityEnum;
	}
	
}
